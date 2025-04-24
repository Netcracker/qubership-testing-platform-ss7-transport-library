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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class TCAPEncoder extends AbstractEncoder<TcapMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TCAPEncoder.class);


    @Override
    public byte[] encode(@Nonnull AbstractMessage abstractMessage) {
        TcapMessage tcapMessage = (TcapMessage) abstractMessage;
        LOGGER.info("Start TCAP encode");
        LOGGER.info("Encode message: {}", tcapMessage);
        ArrayList<Byte> bytes = Lists.newArrayList();
        encodeTotalMessage(bytes, tcapMessage);
        byte[] result = convertListToArray(bytes);
        Utils.logTrace("Finish TCAP message encode:\n{}", getClass(), result);
        return result;
    }

    private void encodeTotalMessage(@Nonnull ArrayList<Byte> bytes, @Nonnull TcapMessage pojo) {
        ArrayList<Byte> sub_bytes = Lists.newArrayList();

        sub_bytes.add(pojo.getType().getCode());
        encodeMessage(sub_bytes, pojo);
        pojo.setTotalLength((byte) sub_bytes.size());
        bytes.add(pojo.getTotalLength());
        bytes.addAll(sub_bytes);
    }

    private void encodeMessage(@Nonnull ArrayList<Byte> bytes, @Nonnull TcapMessage pojo) {
        ArrayList<Byte> sub_bytes = Lists.newArrayList();

        if (Objects.nonNull(pojo.getSourceTransaction())) {
            encodeTransaction(sub_bytes, pojo.getSourceTransaction());
        }
        if (Objects.nonNull(pojo.getDestinationTransaction())) {
            encodeTransaction(sub_bytes, pojo.getDestinationTransaction());
        }
        if (Objects.nonNull(pojo.getOid())) {
            encodeOid(sub_bytes, pojo.getOid(), pojo.getType());
        }
        if (Objects.nonNull(pojo.getDialogue())) {
            encodeDialogue(sub_bytes, pojo.getDialogue());
        }
        if (!pojo.getCapMessages().isEmpty()) {
            sub_bytes.add(pojo.getFlagStartCap());

            ArrayList<Byte> cap_bytes = Lists.newArrayList();
            for (CapMessage capMessage : pojo.getCapMessages()) {
                byte[] encode = new CAPEncoder().encode(capMessage);
                cap_bytes.addAll(asList(encode));
            }
            validateLengthMessage(sub_bytes, (byte) cap_bytes.size());
            sub_bytes.add((byte) cap_bytes.size());
            sub_bytes.addAll(cap_bytes);
        }
        pojo.setMessageLength((byte) sub_bytes.size());
        validateLengthMessage(bytes, pojo.getMessageLength().byteValue());
        bytes.add(pojo.getMessageLength().byteValue());
        bytes.addAll(sub_bytes);
    }

    public void encodeTransaction(@Nonnull ArrayList<Byte> bytes, @Nonnull Transaction transaction) {
        bytes.add(transaction.getTransactionID().getCode());
        ArrayList<Byte> sub_bytes = Lists.newArrayList();
        List<Byte> tx = splitStringBytes(transaction.getId(), transaction.isHEX());
        int size = tx.size();
        if (size < 4) {
            for (int index = 0; index < 4 - size; index++) {
                tx.add(0, (byte) 0x0);
            }
        }
        sub_bytes.addAll(tx);
        transaction.setLength((byte) sub_bytes.size());
        bytes.add(transaction.getLength());
        bytes.addAll(sub_bytes);
    }

    private void encodeDialogue(@Nonnull ArrayList<Byte> bytes, @Nonnull Dialogue pojo) {
        bytes.add(pojo.getMainType());
        ArrayList<Byte> sub_bytes = Lists.newArrayList();

        encodeDialogueMessage(sub_bytes, pojo);
        pojo.setTotalLength((byte) sub_bytes.size());
        bytes.add(pojo.getTotalLength());
        bytes.addAll(sub_bytes);

    }

    private void encodeDialogueMessage(@Nonnull ArrayList<Byte> bytes, @Nonnull Dialogue pojo) {
        bytes.add(pojo.getDialogueType().getId());
        ArrayList<Byte> sub_bytes = Lists.newArrayList();

        sub_bytes.addAll(asList(pojo.getFlag()));
        sub_bytes.add(pojo.getPadding());
        sub_bytes.add(pojo.getProtocolVersion());
        if (pojo.getApplicationContextName() != null) {
            encodeApplicationContext(sub_bytes, pojo.getApplicationContextName());
        }
        if (Objects.nonNull(pojo.getResult())) {
            encodeResult(sub_bytes, pojo.getResult());
        }
        if (Objects.nonNull(pojo.getResultSourceDiagnostic())) {
            encodeResultSourceDiagnostic(sub_bytes, pojo.getResultSourceDiagnostic());
        }
        pojo.setMessageLength((byte) sub_bytes.size());
        bytes.add(pojo.getMessageLength());
        bytes.addAll(sub_bytes);
    }

    private void encodeResultSourceDiagnostic(ArrayList<Byte> bytes, ResultSourceDiagnostic pojo) {
        bytes.add(pojo.getFirstFlag());
        ArrayList<Byte> sub_bytes = Lists.newArrayList();
        encodeResultSourceDiagnosticMessage(sub_bytes, pojo);
        bytes.add((byte) sub_bytes.size());
        bytes.addAll(sub_bytes);
    }

    private void encodeResultSourceDiagnosticMessage(ArrayList<Byte> bytes, ResultSourceDiagnostic pojo) {
        bytes.add(pojo.getSecondFlag());
        ArrayList<Byte> sub_bytes = Lists.newArrayList();
        sub_bytes.add(pojo.getId());
        encodeDialogueService(sub_bytes, pojo.getDialogServiceUser());
        bytes.add((byte) ((byte) sub_bytes.size() + 1));
        bytes.add((byte) sub_bytes.size());
        bytes.addAll(sub_bytes);
    }

    private void encodeDialogueService(ArrayList<Byte> bytes, List<DialogServiceUser> pojos) {
        for (DialogServiceUser pojo : pojos) {
            bytes.add(pojo.getId());
        }
    }

    private void encodeResult(ArrayList<Byte> bytes, Result pojo) {
        bytes.add(pojo.getFirstFlag());
        bytes.add((byte) 0x03);
        bytes.add(pojo.getSecondFlag());
        bytes.add((byte) 0x01);
        bytes.add(pojo.getId());
    }

    private void encodeApplicationContext(@Nonnull ArrayList<Byte> bytes, @Nonnull ApplicationContextName pojo) {
        bytes.add(pojo.getMainType());
        ArrayList<Byte> sub_bytes = Lists.newArrayList();

        sub_bytes.add(pojo.getMessageType());

        List<Byte> message = splitApplicationContextMessage(pojo.getMessage());
        pojo.setMessageLength((byte) message.size());
        sub_bytes.add(pojo.getMessageLength());
        sub_bytes.addAll(message);

        pojo.setTotalLength((byte) sub_bytes.size());
        bytes.add(pojo.getTotalLength());
        bytes.addAll(sub_bytes);
    }

    private List<Byte> splitApplicationContextMessage(String message) {
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

    private void encodeOid(@Nonnull ArrayList<Byte> bytes, @Nonnull Oid pojo, TCAPType type) {
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
//        bytes.addAll(Lists.newArrayList((byte)0x00,(byte)0x11,(byte)0x86,(byte)0x05,(byte)0x01,(byte)0x01,(byte)0x01)); //pojo.getOid()
    }

    private void encodeOidNumber(ArrayList<Byte> bytes, String s) {
        byte[] array = ByteBuffer.allocate(2).putShort(Short.parseShort(s)).array();

        String[] split = Converter.bytesToHex(array).split("");
        if (split.length == 4) {
            byte firstByte = 0;
            firstByte = (byte) (firstByte << 4 | 8);
            firstByte = (byte) (firstByte | Byte.parseByte(split[0]));

            byte secondByte = 0;
            secondByte = (byte) (Byte.parseByte(split[1]) << 1);

            byte thirdByte = 0;
            thirdByte = Byte.parseByte(split[2]);

            byte fourthByte = 0;
            fourthByte = Byte.parseByte(split[3]);

            bytes.add((byte) (firstByte << 4 | secondByte));
            bytes.add((byte) (thirdByte << 4 | fourthByte));
        }


    }

}
