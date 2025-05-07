/*
 * # Copyright 2024-2025 NetCracker Technology Corporation
 * #
 * # Licensed under the Apache License, Version 2.0 (the "License");
 * # you may not use this file except in compliance with the License.
 * # You may obtain a copy of the License at
 * #
 * #      http://www.apache.org/licenses/LICENSE-2.0
 * #
 * # Unless required by applicable law or agreed to in writing, software
 * # distributed under the License is distributed on an "AS IS" BASIS,
 * # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * # See the License for the specific language governing permissions and
 * # limitations under the License.
 *
 */

package org.qubership.automation.ss7lib.encode.tcap;

import static org.qubership.automation.ss7lib.encode.EncoderUtils.splitStringBytes;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

import org.qubership.automation.ss7lib.convert.Converter;
import org.qubership.automation.ss7lib.decode.Utils;
import org.qubership.automation.ss7lib.encode.AbstractEncoder;
import org.qubership.automation.ss7lib.encode.cap.CAPEncoder;
import org.qubership.automation.ss7lib.model.AbstractMessage;
import org.qubership.automation.ss7lib.model.CapMessage;
import org.qubership.automation.ss7lib.model.TcapMessage;
import org.qubership.automation.ss7lib.model.sub.tcap.ApplicationContextName;
import org.qubership.automation.ss7lib.model.sub.tcap.Dialogue;
import org.qubership.automation.ss7lib.model.sub.tcap.Oid;
import org.qubership.automation.ss7lib.model.sub.tcap.ResultSourceDiagnostic;
import org.qubership.automation.ss7lib.model.sub.tcap.Transaction;
import org.qubership.automation.ss7lib.model.type.TCAPType;
import org.qubership.automation.ss7lib.model.type.dialog.DialogServiceUser;
import org.qubership.automation.ss7lib.model.type.dialog.Result;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TCAPEncoder extends AbstractEncoder<TcapMessage> {

    /**
     * Encode TcapMessage; place result into a new byte[].
     *
     * @param abstractMessage - TcapMessage to encode
     * @return a new byte[] filled with encoding result.
     */
    @Override
    public byte[] encode(@Nonnull final AbstractMessage abstractMessage) {
        TcapMessage tcapMessage = (TcapMessage) abstractMessage;
        log.info("Start encode of TCAP message: {}", tcapMessage);
        ArrayList<Byte> bytes = Lists.newArrayList();
        encodeTotalMessage(bytes, tcapMessage);
        byte[] result = convertListToArray(bytes);
        Utils.logTrace("Finish TCAP message encode:\n{}", getClass(), result);
        return result;
    }

    private void encodeTotalMessage(@Nonnull final ArrayList<Byte> bytes, @Nonnull final TcapMessage pojo) {
        ArrayList<Byte> subBytes = Lists.newArrayList();

        subBytes.add(pojo.getType().getCode());
        encodeMessage(subBytes, pojo);
        pojo.setTotalLength((byte) subBytes.size());
        bytes.add(pojo.getTotalLength());
        bytes.addAll(subBytes);
    }

    private void encodeMessage(@Nonnull final ArrayList<Byte> bytes, @Nonnull final TcapMessage pojo) {
        ArrayList<Byte> subBytes = Lists.newArrayList();

        if (Objects.nonNull(pojo.getSourceTransaction())) {
            encodeTransaction(subBytes, pojo.getSourceTransaction());
        }
        if (Objects.nonNull(pojo.getDestinationTransaction())) {
            encodeTransaction(subBytes, pojo.getDestinationTransaction());
        }
        if (Objects.nonNull(pojo.getOid())) {
            encodeOid(subBytes, pojo.getOid(), pojo.getType());
        }
        if (Objects.nonNull(pojo.getDialogue())) {
            encodeDialogue(subBytes, pojo.getDialogue());
        }
        if (!pojo.getCapMessages().isEmpty()) {
            subBytes.add(pojo.getFlagStartCap());
            ArrayList<Byte> capBytes = Lists.newArrayList();
            for (CapMessage capMessage : pojo.getCapMessages()) {
                byte[] encode = new CAPEncoder().encode(capMessage);
                capBytes.addAll(asList(encode));
            }
            validateLengthMessage(subBytes, (byte) capBytes.size());
            subBytes.add((byte) capBytes.size());
            subBytes.addAll(capBytes);
        }
        pojo.setMessageLength((byte) subBytes.size());
        validateLengthMessage(bytes, pojo.getMessageLength().byteValue());
        bytes.add(pojo.getMessageLength().byteValue());
        bytes.addAll(subBytes);
    }

    public void encodeTransaction(@Nonnull final ArrayList<Byte> bytes, @Nonnull final Transaction transaction) {
        bytes.add(transaction.getTransactionID().getCode());
        ArrayList<Byte> subBytes = Lists.newArrayList();
        List<Byte> tx = splitStringBytes(transaction.getId(), transaction.isHEX());
        int size = tx.size();
        if (size < 4) {
            for (int index = 0; index < 4 - size; index++) {
                tx.add(0, (byte) 0x0);
            }
        }
        subBytes.addAll(tx);
        transaction.setLength((byte) subBytes.size());
        bytes.add(transaction.getLength());
        bytes.addAll(subBytes);
    }

    private void encodeDialogue(@Nonnull final ArrayList<Byte> bytes, @Nonnull final Dialogue pojo) {
        bytes.add(pojo.getMainType());
        ArrayList<Byte> subBytes = Lists.newArrayList();

        encodeDialogueMessage(subBytes, pojo);
        pojo.setTotalLength((byte) subBytes.size());
        bytes.add(pojo.getTotalLength());
        bytes.addAll(subBytes);
    }

    private void encodeDialogueMessage(@Nonnull final ArrayList<Byte> bytes, @Nonnull final Dialogue pojo) {
        bytes.add(pojo.getDialogueType().getId());
        ArrayList<Byte> subBytes = Lists.newArrayList();

        subBytes.addAll(asList(pojo.getFlag()));
        subBytes.add(pojo.getPadding());
        subBytes.add(pojo.getProtocolVersion());
        if (pojo.getApplicationContextName() != null) {
            encodeApplicationContext(subBytes, pojo.getApplicationContextName());
        }
        if (Objects.nonNull(pojo.getResult())) {
            encodeResult(subBytes, pojo.getResult());
        }
        if (Objects.nonNull(pojo.getResultSourceDiagnostic())) {
            encodeResultSourceDiagnostic(subBytes, pojo.getResultSourceDiagnostic());
        }
        pojo.setMessageLength((byte) subBytes.size());
        bytes.add(pojo.getMessageLength());
        bytes.addAll(subBytes);
    }

    private void encodeResultSourceDiagnostic(final ArrayList<Byte> bytes, final ResultSourceDiagnostic pojo) {
        bytes.add(pojo.getFirstFlag());
        ArrayList<Byte> subBytes = Lists.newArrayList();
        encodeResultSourceDiagnosticMessage(subBytes, pojo);
        bytes.add((byte) subBytes.size());
        bytes.addAll(subBytes);
    }

    private void encodeResultSourceDiagnosticMessage(final ArrayList<Byte> bytes,
                                                     final ResultSourceDiagnostic pojo) {
        bytes.add(pojo.getSecondFlag());
        ArrayList<Byte> subBytes = Lists.newArrayList();
        subBytes.add(pojo.getId());
        encodeDialogueService(subBytes, pojo.getDialogServiceUser());
        bytes.add((byte) ((byte) subBytes.size() + 1));
        bytes.add((byte) subBytes.size());
        bytes.addAll(subBytes);
    }

    private void encodeDialogueService(final ArrayList<Byte> bytes, final List<DialogServiceUser> pojos) {
        for (DialogServiceUser pojo : pojos) {
            bytes.add(pojo.getId());
        }
    }

    private void encodeResult(final ArrayList<Byte> bytes, final Result pojo) {
        bytes.add(pojo.getFirstFlag());
        bytes.add((byte) 0x03);
        bytes.add(pojo.getSecondFlag());
        bytes.add((byte) 0x01);
        bytes.add(pojo.getId());
    }

    private void encodeApplicationContext(@Nonnull final ArrayList<Byte> bytes,
                                          @Nonnull final ApplicationContextName pojo) {
        bytes.add(pojo.getMainType());
        ArrayList<Byte> subBytes = Lists.newArrayList();

        subBytes.add(pojo.getMessageType());

        List<Byte> message = splitApplicationContextMessage(pojo.getMessage());
        pojo.setMessageLength((byte) message.size());
        subBytes.add(pojo.getMessageLength());
        subBytes.addAll(message);

        pojo.setTotalLength((byte) subBytes.size());
        bytes.add(pojo.getTotalLength());
        bytes.addAll(subBytes);
    }

    private List<Byte> splitApplicationContextMessage(final String message) {
        List<Byte> result = Lists.newArrayList();
        String string = message.replaceFirst("0", "");
        String[] split = string.split("\\.");
        for (String s : split) {
            if (!Strings.isNullOrEmpty(s)) {
                result.add((byte) Integer.parseInt(s, 10));
            }
        }
        return result;
    }

    private void encodeOid(@Nonnull final ArrayList<Byte> bytes,
                           @Nonnull final Oid pojo,
                           final TCAPType type) {
        bytes.add(pojo.getFirstFlag());
        bytes.add(type.getFirstFlag());
        bytes.add(pojo.getSecondFlag());
        bytes.add(type.getSecondFlag());
        bytes.add(pojo.getThirdFlag());

        ArrayList<Byte> oid = Lists.newArrayList();
        String string = pojo.getOid().replaceFirst("0\\.", "");
        String[] split = string.split("\\.");
        if (split.length != 0) {
            for (int i = 0; i < split.length; i++) {
                if (i == 2) {
                    encodeOidNumber(oid, split[i]);
                } else {
                    oid.add((byte) Integer.parseInt(split[i], 10));
                }
            }
        }
        bytes.add((byte) oid.size());
        bytes.addAll(oid);
    }

    private void encodeOidNumber(final ArrayList<Byte> bytes, final String s) {
        byte[] array = ByteBuffer.allocate(2).putShort(Short.parseShort(s)).array();

        String[] split = Converter.bytesToHex(array).split("");
        if (split.length == 4) {
            byte firstByte = 0;
            firstByte = (byte) (firstByte << 4 | 8);
            firstByte = (byte) (firstByte | Byte.parseByte(split[0]));

            byte secondByte = (byte) (Byte.parseByte(split[1]) << 1);
            byte thirdByte = Byte.parseByte(split[2]);
            byte fourthByte = Byte.parseByte(split[3]);

            bytes.add((byte) (firstByte << 4 | secondByte));
            bytes.add((byte) (thirdByte << 4 | fourthByte));
        }
    }

}
