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

package org.qubership.automation.ss7lib.decode.tcap;

import java.nio.ByteBuffer;

import org.qubership.automation.ss7lib.convert.Converter;
import org.qubership.automation.ss7lib.decode.Decoder;
import org.qubership.automation.ss7lib.decode.Utils;
import org.qubership.automation.ss7lib.model.CapMessage;
import org.qubership.automation.ss7lib.model.TcapMessage;
import org.qubership.automation.ss7lib.model.sub.cap.CAPInvokeIDPojo;
import org.qubership.automation.ss7lib.model.sub.cap.CAPOpCodePojo;
import org.qubership.automation.ss7lib.model.sub.cap.CapInvoke;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessagePojo;
import org.qubership.automation.ss7lib.model.sub.tcap.ApplicationContextName;
import org.qubership.automation.ss7lib.model.sub.tcap.Dialogue;
import org.qubership.automation.ss7lib.model.sub.tcap.Oid;
import org.qubership.automation.ss7lib.model.sub.tcap.ResultSourceDiagnostic;
import org.qubership.automation.ss7lib.model.sub.tcap.Transaction;
import org.qubership.automation.ss7lib.model.type.EnumProvider;
import org.qubership.automation.ss7lib.model.type.OpCodeType;
import org.qubership.automation.ss7lib.model.type.TCAPType;
import org.qubership.automation.ss7lib.model.type.TransactionID;
import org.qubership.automation.ss7lib.model.type.dialog.DialogServiceUser;
import org.qubership.automation.ss7lib.model.type.dialog.DialogueType;
import org.qubership.automation.ss7lib.model.type.dialog.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcapDecoder implements Decoder<TcapMessage> {

    /**
     * Parse TCAP part from the source ByteBuffer buffer.
     *
     * @param buffer - ByteBuffer to process
     * @return TcapMessage decoded from the source buffer.
     */
    @Override
    public TcapMessage decode(final ByteBuffer buffer) {
        log.info("Starting parsing of tcap part");
        TcapMessage message = new TcapMessage();
        message.setTotalLength(buffer.get());
        message.setType(TCAPType.of(buffer.get()));
        byte messageLongerThan = buffer.get(); //flag which says is message longer that 0x80
        log.info("Flag: message longer than 0x80: {}", messageLongerThan);
        if (messageLongerThan != (byte) 0x81) {
            buffer.position(buffer.position() - 1);
        }
        message.setMessageLength(buffer.get());
        log.debug("Tcap headers: {}", message);
        parseTransactions(buffer, message);
        log.info("Tcap part parsing finished: {}", message);
        message.setOid(parseOid(buffer));
        message.setDialogue(parseDialog(buffer));
        log.info("Tcap message: {}\nStart parsing CAP layer", message);
        parseCap(message, buffer);
        log.info("Cap parsing finished. Full message: {}", message);
        return message;
    }

    private void parseCap(final TcapMessage message, final ByteBuffer buffer) {
        boolean isBufferHasRemaining = buffer.hasRemaining();
        byte flag = isBufferHasRemaining ? buffer.get() : 0x0;
        if (!isBufferHasRemaining || flag != 0x6c) {
            log.warn("There is no CAP layer. Flag is: {}", Converter.bytesToHex(flag));
            return;
        }
        int capLayerLength = getLength(buffer);
        log.debug("Cap layer length: {}", capLayerLength);
        ByteBuffer capLayer = Utils.subBuffer(capLayerLength, buffer);
        while (capLayer.hasRemaining()) {
            log.debug("Cap flag: {}", capLayer.get());
            int capLength = Byte.toUnsignedInt(capLayer.get());
            log.debug("Cap length: {}", capLength);
            ByteBuffer entry = Utils.subBuffer(capLength, capLayer);
            byte invokeLength = entry.get();
            log.debug("Invoke length: {}", invokeLength);
            CapMessage capMessage = new CapMessage();
            capMessage.setCapMessageLength((byte) capLength);
            capMessage.setInvoke(parseInvoke(entry));
            message.getCapMessages().add(capMessage);
        }
    }

    private int getLength(final ByteBuffer buffer) {
        int length = Byte.toUnsignedInt(buffer.get());
        if (length == 129) { //avoid flag 0x81
            length = Byte.toUnsignedInt(buffer.get());
        }
        return length;
    }

    private CapInvoke parseInvoke(final ByteBuffer entry) {
        CapInvoke invoke = new CapInvoke();
        log.debug("Present length: {}", entry.get());
        byte presentValue = entry.get();
        log.debug("Present value: {}", presentValue);
        CAPInvokeIDPojo invokeID = new CAPInvokeIDPojo();
        invokeID.setStringBytes(String.valueOf(presentValue));
        invoke.setInvokeID(invokeID);

        log.debug("OpCode total length: {}", entry.get());
        log.debug("OpCode length: {}", entry.get());
        byte opCode = entry.get();
        log.debug("OpCode value: {}", opCode);
        CAPOpCodePojo opCodePojo = new CAPOpCodePojo();
        OpCodeType opCodeType = EnumProvider.of(opCode, OpCodeType.class);
        opCodePojo.setOpCodeType(opCodeType);
        invoke.setOpCode(opCodePojo);
        CAPMessagePojo capMessagePojo = opCodeType.decode(entry);
        invoke.setCapMessagePojo(capMessagePojo);
        return invoke;
    }

    private Dialogue parseDialog(final ByteBuffer buffer) {
        byte[] header = new byte[2];
        buffer.get(header);
        byte type = header[0]; // [-96, 29, 97, 27] -
        logDebugArray("Dialog header {}", header);
        if (type != Dialogue.DIALOGUE_FLAG) {
            log.info("No dialog is present in message. Record type: {}", type);
            buffer.position(buffer.position() - 2);
            return null;
        }
        Dialogue dialogue = new Dialogue();
        dialogue.setDialogueType(EnumProvider.of(buffer.get(), DialogueType.class));
        dialogue.setTotalLength(buffer.get());
        dialogue.setMessageLength(buffer.get());
        logDebugArray("Length of dialogue padding: {}", buffer.get());
        dialogue.setPadding(buffer.get());
        dialogue.setProtocolVersion(buffer.get());
        parseApplicationContextName(buffer, dialogue);
        parseResult(buffer, dialogue);
        paresSourceDiagnostic(buffer, dialogue);
        return dialogue;
    }

    private void paresSourceDiagnostic(final ByteBuffer buffer, final Dialogue dialogue) {
        byte[] dst = new byte[4];
        buffer.get(dst);
        if (dst[0] != (byte) 0xa3) {
            buffer.position(buffer.position() - 4);
        }
        ResultSourceDiagnostic sourceDiagnostic = new ResultSourceDiagnostic();
        dialogue.setResultSourceDiagnostic(sourceDiagnostic);
        byte length = buffer.get();
        sourceDiagnostic.setMessageLength(length);
        byte[] services = new byte[length];
        buffer.get(services);
        ByteBuffer servicesBuffer = ByteBuffer.wrap(services);
        byte countOfServices = servicesBuffer.get();
        logDebugArray("Count of services: {}", countOfServices);
        while (servicesBuffer.hasRemaining()) {
            DialogServiceUser serviceUser = EnumProvider.of(servicesBuffer.get(), DialogServiceUser.class);
            log.info("User service parsed: {}", serviceUser.asPrintable());
            sourceDiagnostic.getDialogServiceUser().add(serviceUser);
        }
    }

    private void parseResult(final ByteBuffer buffer, final Dialogue dialogue) {
        byte dialogDiagnosticFlag = buffer.get();
        if (dialogDiagnosticFlag != (byte) 0xa2) {
            buffer.position(buffer.position() - 1);
            return;
        }
        byte length = buffer.get();
        byte[] dst = new byte[length];
        buffer.get(dst);
        dialogue.setResult(EnumProvider.of(dst[dst.length - 1], Result.class));
    }

    private void parseApplicationContextName(final ByteBuffer buffer, final Dialogue dialogue) {
        ApplicationContextName applicationContextName = new ApplicationContextName();
        dialogue.setApplicationContextName(applicationContextName);
        buffer.get(); // Main type;
        buffer.get(); // Length;
        buffer.get(); // AppContextType type;
        byte messageLength = buffer.get();
        applicationContextName.setMessageLength(messageLength);
        byte[] source = new byte[messageLength];
        buffer.get(source);
        byte[] destination = new byte[8];
        System.arraycopy(source, 0, destination, 8 - source.length, source.length);
        applicationContextName.setMessage(Converter.bytesToDottedString(destination));
    }

    private Oid parseOid(final ByteBuffer buffer) {
        byte oidFlag = buffer.get();
        if (oidFlag != 0x6b) {
            log.info("No OID is present in message. Flag is: {}", oidFlag);
            buffer.position(buffer.position() - 1);
            return null;
        }
        resolvePrefix(buffer);
        Oid oid = new Oid();
        ByteBuffer oidBuffer = Utils.subBuffer(7, buffer);
        logDebugArray("Oid bytes: {}", oidBuffer.array());
        StringBuilder result = decodeTwoFirstNodes(oidBuffer);
        decodeOtherParams(oidBuffer, result);
        oid.setOid(result.toString());
        return oid;
    }

    private void logDebugArray(final String format, final byte... array) {
        if (log.isDebugEnabled()) {
            log.debug(format, Converter.bytesToHex(array));
        }
    }

    private void decodeOtherParams(final ByteBuffer oidBuffer, final StringBuilder result) {
        result.append('.').append(Byte.toUnsignedInt(oidBuffer.get())).append('.')
                .append(getMultipleData(oidBuffer));
        parseLastData(oidBuffer, result);
    }

    private void parseLastData(final ByteBuffer oidBuffer, final StringBuilder result) {
        do {
            result.append('.').append(Byte.toUnsignedInt(oidBuffer.get()));
        } while (oidBuffer.hasRemaining());
    }

    private String getMultipleData(final ByteBuffer buffer) {
        byte firstPart = buffer.get();
        byte secondPart = buffer.get();
        StringBuilder builder = new StringBuilder();
        parsePart(builder, firstPart >> 3);
        parsePart(builder, firstPart >> 1);
        parsePart(builder, secondPart >> 4);
        parsePart(builder, secondPart);
        return String.valueOf(Integer.parseInt(builder.toString(), 16));
    }

    private void parsePart(final StringBuilder builder, final int input) {
        byte result = (byte) (input & 0xf);
        builder.append(Converter.byteToHex(result));
    }

    /*
            Read more about encoding
            https://msdn.microsoft.com/en-us/library/bb540809(v=vs.85).aspx
     */
    private StringBuilder decodeTwoFirstNodes(final ByteBuffer oidValue) {
        StringBuilder result = new StringBuilder();
        Converter.DividedByte dividedByte = Converter.divideByteOnTwo(oidValue.get());
        byte firstTwoNodes = (byte) ((dividedByte.getLeft() / 40) - dividedByte.getRight());
        Converter.DividedByte firstNodes = Converter.divideByteOnTwo(firstTwoNodes);
        result.append(firstNodes.getLeft()).append('.').append(firstNodes.getRight());
        return result;
    }

    private void resolvePrefix(final ByteBuffer buffer) {
        buffer.position(buffer.position() - 1);
        byte[] dst = new byte[6];
        buffer.get(dst);
        log.info("Oid prefix: {}", Converter.bytesToHex(dst));
    }

    private void parseTransactions(final ByteBuffer buffer, final TcapMessage message) {
        Transaction transaction = parseTransactionId(buffer);
        setTx(message, transaction);
        transaction = parseTransactionId(buffer);
        setTx(message, transaction);
    }

    private void setTx(final TcapMessage message, final Transaction transaction) {
        if (transaction == null) {
            return;
        }
        if (transaction.getTransactionID().getCode() == TransactionID.SOURCE.getCode()) {
            message.setSourceTransaction(transaction);
        } else {
            message.setDestinationTransaction(transaction);
        }
    }

    private Transaction parseTransactionId(final ByteBuffer buffer) {
        byte type = buffer.get();
        if (!TransactionID.isTx(type)) {
            buffer.position(buffer.position() - 1);
            return null;
        }
        byte length = buffer.get();
        byte[] txId = new byte[length];
        buffer.get(txId);
        Transaction transaction = new Transaction();
        transaction.setTransactionID(TransactionID.of(type));
        transaction.setLength(length);
        transaction.setId(Converter.bytesToHex(txId));
        log.info("Decoded transaction: {}", transaction);
        return transaction;
    }
}
