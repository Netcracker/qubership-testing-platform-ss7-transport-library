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

import static org.junit.Assert.assertArrayEquals;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.junit.Test;
import org.qubership.automation.ss7lib.convert.Converter;
import org.qubership.automation.ss7lib.model.TcapMessage;
import org.qubership.automation.ss7lib.model.sub.tcap.ApplicationContextName;
import org.qubership.automation.ss7lib.model.sub.tcap.Dialogue;
import org.qubership.automation.ss7lib.model.sub.tcap.Oid;
import org.qubership.automation.ss7lib.model.sub.tcap.Transaction;
import org.qubership.automation.ss7lib.model.type.TCAPType;
import org.qubership.automation.ss7lib.model.type.TransactionID;
import org.qubership.automation.ss7lib.model.type.dialog.DialogueType;

import com.google.common.collect.Lists;

@SuppressWarnings("checkstyle:MagicNumber")
public class TCAPEncoderTest {

    /**
     * Test encoding of TCAP message.
     */
    @Test
    public void testTcapMessageEncode() {
        TcapMessage pojo = new TcapMessage();
        pojo.setType(TCAPType.BEGIN);
        Transaction transaction = new Transaction();
        transaction.setTransactionID(TransactionID.SOURCE);
        transaction.setId("f90f0108");
        pojo.setSourceTransaction(transaction);
        Oid oidPojo = new Oid();
        oidPojo.setOid("0.0.17.773.1.1.1");
        pojo.setOid(oidPojo);
        Dialogue dialoguePojo = new Dialogue();
        dialoguePojo.setDialogueType(DialogueType.REQUEST);
        dialoguePojo.setPadding((byte) 0x07);
        dialoguePojo.setProtocolVersion((byte) 0x80);
        ApplicationContextName applicationContextName = new ApplicationContextName();
        applicationContextName.setMessage("0.4.0.0.1.0.50.1");
        dialoguePojo.setApplicationContextName(applicationContextName);
        pojo.setDialogue(dialoguePojo);

        byte[] tcap = new TCAPEncoder().encode(pojo);
        byte[] correctMessage = {
                (byte) 40/*0xb1/-79 with CAP*/, 0x62, /*(byte) 0x81,  with CAP*/ (byte) 38/* 0xae with CAP*/, 0x48,
                0x04, (byte) 0xf9, 0x0f, 0x01,
                0x08, 0x6b, 0x1e, 0x28, 0x1c, 0x06, 0x07, 0x00,
                0x11, (byte) 0x86, 0x05, 0x01, 0x01, 0x01, (byte) 0xa0, 0x11,
                0x60, 0x0f, (byte) 0x80, 0x02, 0x07, (byte) 0x80, (byte) 0xa1, 0x09,
                0x06, 0x07, 0x04, 0x00, 0x00, 0x01, 0x00, 0x32,
                0x01};

        assertArrayEquals(correctMessage, tcap);
    }

    /**
     * Test converter.
     */
    @Test
    public void testConverter() {
        byte[] array = ByteBuffer.allocate(2).putShort(Short.parseShort("773")).array();
        String[] split = Converter.bytesToHex(array).split("");
        if (split.length == 4) {
            byte firstByte = 0;
            firstByte = (byte) (firstByte << 4 | 8);
            firstByte = (byte) (firstByte | Byte.parseByte(split[0]));
            System.out.println(Integer.toBinaryString(firstByte));

            byte secondByte = (byte) (Byte.parseByte(split[1]) << 1);
            System.out.println(Integer.toBinaryString(secondByte));

            byte thirdByte = Byte.parseByte(split[2]);
            System.out.println(Integer.toBinaryString(thirdByte));

            byte fourthByte = Byte.parseByte(split[3]);

            byte first = (byte) (firstByte << 4 | secondByte);
            byte second = (byte) (thirdByte << 4 | fourthByte);

            System.out.println(Converter.bytesToHex(first));
            System.out.println(Converter.bytesToHex(second));
        }
        System.out.println(Converter.bytesToHex(array));
    }

    /**
     * Test encoding of Transaction.
     */
    @Test
    public void testEncodeTransaction() {
        TCAPEncoder encoder = new TCAPEncoder();
        Transaction transaction = new Transaction();
        transaction.setTransactionID(TransactionID.SOURCE);
        transaction.setId("0a");
        ArrayList<Byte> bytes = Lists.newArrayList();
        encoder.encodeTransaction(bytes, transaction);
        byte[] expected = {0x48/*TxType*/, 0x4/*TxLength*/, 0x0, 0x0, 0x0, 0xa /*Expected TxId*/};
        ByteBuffer result = ByteBuffer.allocate(bytes.size());
        bytes.forEach(result::put);
        assertArrayEquals(expected, result.array());
    }
}
