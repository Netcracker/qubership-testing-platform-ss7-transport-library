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

package org.qubership.automation.ss7lib.parse;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.qubership.automation.ss7lib.DumpReader;
import org.qubership.automation.ss7lib.decode.Utils;
import org.qubership.automation.ss7lib.encode.Encoder;
import org.qubership.automation.ss7lib.encode.EncoderFactory;
import org.qubership.automation.ss7lib.encode.FullMessageEncoder;
import org.qubership.automation.ss7lib.model.AbstractMessage;
import org.qubership.automation.ss7lib.model.CapMessage;
import org.qubership.automation.ss7lib.model.FullMessage;
import org.qubership.automation.ss7lib.model.SccpMessage;
import org.qubership.automation.ss7lib.model.TcapMessage;
import org.qubership.automation.ss7lib.model.sub.cap.message.InitialDetectionPoint;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.extention.Extension;
import org.qubership.automation.ss7lib.model.sub.sccp.AddressIndicator;
import org.qubership.automation.ss7lib.model.sub.sccp.CallPartyAddress;

import com.google.common.primitives.Bytes;

public class MessageParserTest {

    private String message2 = "";
    private String message3 = "";

    @Before
    public void prepareMessage2() throws Exception {
        message2 = DumpReader.getMessage("/test_data/FullMessage2.stringdump");
    }

    @Test
    @Deprecated
    public void parseTest2() {
        MessageParser messageParser = new MessageParser();
        FullMessage pojoList = messageParser.parse(message2).iterator().next();
        ByteBuffer buffer = FullMessageEncoder.encode(pojoList);
        byte[] correctMessage = new byte[]{
                0x01, 0x00, 0x01, 0x01, 0x00, 0x00, 0x00, (byte) 0xec,
                0x02, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x08,
                0x02, 0x10, 0x00, (byte) 0xda, 0x00, 0x01, 0x78, 0x12,
                0x00, 0x01, 0x78, 0x70, 0x03, 0x02, 0x00, 0x1a,
                0x09, 0x01, 0x03, 0x0c, 0x15, 0x09, (byte) 0x89, (byte) 0x92,
                0x0a, 0x51, 0x41, 0x24, 0x04, 0x15, 0x04, 0x09,
                (byte) 0x89, (byte) 0x92, 0x0a, 0x51, 0x41, 0x24, 0x04, 0x35,
                0x01, (byte) 0xb0, 0x65, (byte) 0x81, (byte) 0xad, 0x48, 0x04, 0x58,
                0x1f, 0x00, 0x1c, 0x49, 0x04, (byte) 0xf9, 0x0f, 0x01,
                0x08, 0x6b, 0x2a, 0x28, 0x28, 0x06, 0x07, 0x00,
                0x11, (byte) 0x86, 0x05, 0x01, 0x01, 0x01, (byte) 0xa0, 0x1d,
                0x61, 0x1b, (byte) 0x80, 0x02, 0x07, (byte) 0x80, (byte) 0xa1, 0x09,
                0x06, 0x07, 0x04, 0x00, 0x00, 0x01, 0x00, 0x32,
                0x01, (byte) 0xa2, 0x03, 0x02, 0x01, 0x00, (byte) 0xa3, 0x05,
                (byte) 0xa1, 0x03, 0x02, 0x01, 0x00, 0x6c, 0x73, (byte) 0xa1,
                0x44, 0x02, 0x01, 0x01, 0x02, 0x01, 0x17, 0x30,
                0x3c, (byte) 0xa0, 0x3a, 0x30, 0x06, (byte) 0x80, 0x01, 0x07,
                (byte) 0x81, 0x01, 0x01, 0x30, 0x0b, (byte) 0x80, 0x01, 0x09,
                (byte) 0x81, 0x01, 0x00, (byte) 0xa2, 0x03, (byte) 0x81, 0x01, 0x01,
                0x30, 0x0b, (byte) 0x80, 0x01, 0x09, (byte) 0x81, 0x01, 0x00,
                (byte) 0xa2, 0x03, (byte) 0x81, 0x01, 0x02, 0x30, 0x06, (byte) 0x80,
                0x01, 0x04, (byte) 0x81, 0x01, 0x01, 0x30, 0x06, (byte) 0x80,
                0x01, 0x05, (byte) 0x81, 0x01, 0x01, 0x30, 0x06, (byte) 0x80,
                0x01, 0x0a, (byte) 0x81, 0x01, 0x01, (byte) 0xa1, 0x15, 0x02,
                0x01, 0x02, 0x02, 0x01, 0x23, 0x30, 0x0d, (byte) 0x80,
                0x06, (byte) 0xa0, 0x04, (byte) 0x80, 0x02, 0x07, 0x08, (byte) 0xa2,
                0x03, (byte) 0x80, 0x01, 0x01, (byte) 0xa1, 0x14, 0x02, 0x01,
                0x03, 0x02, 0x01, 0x14, 0x30, 0x0c, (byte) 0xa0, 0x0a,
                0x04, 0x08, (byte) 0x84, (byte) 0x90, 0x51, 0x41, (byte) 0x83, 0x40,
                0x33, 0x09};

        assertArrayEquals(correctMessage, buffer.array());
    }

    @Before
    public void prepareMessage3() throws Exception {
        message3 = DumpReader.getMessage("/test_data/FullMessage3.stringdump");
    }

    @Test
    @Deprecated
    public void parseTest3() {
        MessageParser messageParser = new MessageParser();
        FullMessage pojoList = messageParser.parse(message3).iterator().next();
        ByteBuffer buffer = FullMessageEncoder.encode(pojoList);
        byte[] correctMessage = new byte[]{
                0x01, 0x00, 0x01, 0x01, 0x00, 0x00, 0x00, (byte) 0xd7,
                0x02, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x00,
                0x02, 0x10, 0x00, (byte) 0xc7, 0x00, 0x00, 0x03, 0x20,
                0x00, 0x00, 0x01, 0x7f, 0x03, 0x02, 0x00, 0x15,
                0x09, (byte) 0x81, 0x03, 0x0c, 0x15, 0x09, (byte) 0x8a, (byte) 0x92,
                0x0a, 0x51, 0x41, 0x24, 0x04, 0x35, 0x04, 0x09,
                (byte) 0x8a, (byte) 0x92, 0x0a, 0x51, 0x41, 0x24, 0x04, 0x15,
                0x04, (byte) 0x9d, 0x62, (byte) 0x81, (byte) 0x9a, 0x48, 0x04, 0x00,
                0x00, 0x00, 0x07, 0x6b, 0x1e, 0x28, 0x1c, 0x06,
                0x07, 0x00, 0x11, (byte) 0x86, 0x05, 0x01, 0x01, 0x01,
                (byte) 0xa0, 0x11, 0x60, 0x0f, (byte) 0x80, 0x02, 0x07, (byte) 0x80,
                (byte) 0xa1, 0x09, 0x06, 0x07, 0x04, 0x00, 0x00, 0x01,
                0x00, 0x32, 0x01, 0x6c, 0x72, (byte) 0xa1, 0x70, 0x02,
                0x01, 0x00, 0x02, 0x01, 0x00, 0x30, 0x68, (byte) 0x80,
                0x01, 0x03, (byte) 0x83, 0x07, 0x04, 0x15, 0x21, 0x00,
                0x70, (byte) 0x99, (byte) 0x93, (byte) 0x97, 0x02, (byte) 0x91, (byte) 0x81, (byte) 0xbb,
                0x05, (byte) 0x80, 0x03, (byte) 0x80, (byte) 0x90, (byte) 0xa3, (byte) 0x9c, 0x01,
                0x02, (byte) 0x9f, 0x32, 0x08, 0x03, 0x52, 0x01, 0x02,
                0x20, 0x04, 0x50, (byte) 0xf3, (byte) 0xbf, 0x34, 0x17, 0x02,
                0x01, 0x00, (byte) 0x81, 0x07, (byte) 0x91, 0x51, 0x41, 0x24,
                0x04, 0x15, (byte) 0xf4, (byte) 0xa3, 0x09, (byte) 0x80, 0x07, 0x03,
                0x02, 0x15, 0x1b, 0x58, 0x36, (byte) 0x9c, (byte) 0x9f, 0x36,
                0x07, 0x04, 0x40, 0x14, 0x23, (byte) 0xb0, (byte) 0xd7, 0x00,
                (byte) 0x9f, 0x37, 0x07, (byte) 0x91, 0x51, 0x41, 0x24, 0x04,
                0x15, (byte) 0xf4, (byte) 0x9f, 0x38, 0x07, (byte) 0x91, 0x31, (byte) 0x90,
                0x29, (byte) 0x97, (byte) 0x84, 0x65, (byte) 0x9f, 0x39, 0x08, 0x02,
                0x71, 0x40, 0x30, 0x61, 0x15, 0x34, 0x69
        };

        assertArrayEquals(correctMessage, buffer.array());
    }

    private <T extends AbstractMessage> void encode(List<AbstractMessage> pojoList, List<Byte> byteList) {
        for (AbstractMessage pojo : pojoList) {
            Encoder encoder = EncoderFactory.getEncoder(pojo.getClass());
            byte[] data = encoder.encode((T) pojo);
            byteList.addAll(Bytes.asList(data));
        }
    }

    @Test
    public void testParseEventReportBCSM() throws IOException, URISyntaxException {
        String message = DumpReader.getMessage("/test_data/eventReportBCSM.stringdump");
        FullMessage pojoList = new MessageParser().parse(message).iterator().next();
        ByteBuffer buffer = FullMessageEncoder.encode(pojoList);
        Utils.logTrace("Erb message: \n{}", getClass(), buffer.array());
    }
    @Test
    public void givenEventReportBCSM_with7digitDestinationId_whenWeSendIt_thenSs7AddsAdditionalK0BeforeDestinationId() throws IOException, URISyntaxException {
        String message = DumpReader.getMessage("/test_data/givenEventReportBCSM_with7digitDestinationId.stringdump");
        FullMessage pojoList = new MessageParser().parse(message).iterator().next();
        ByteBuffer buffer = FullMessageEncoder.encode(pojoList);
        Utils.logTrace("Erb message: \n{}", getClass(), buffer.array());
    }    

        @Test
    public void testParseEventReportBCSM2() throws IOException, URISyntaxException {
        String message = DumpReader.getMessage("/test_data/eventReportBCSM_1.stringdump");
        FullMessage pojoList = new MessageParser().parse(message).iterator().next();
        ByteBuffer buffer = FullMessageEncoder.encode(pojoList);
        Utils.logTrace("Erb message: \n{}", getClass(), buffer.array());
    }
    
    @Test
    public void testParsingApplyCharging() throws IOException, URISyntaxException {
        String message = DumpReader.getMessage("/test_data/bugfix/applyCharginReport.textdump");
        FullMessage pojoList = new MessageParser().parse(message).iterator().next();
        ByteBuffer buffer = FullMessageEncoder.encode(pojoList);
        SccpMessage sccp = pojoList.getSccp();
        CallPartyAddress partyAddress = sccp.getCalledPartyAddress();
        AddressIndicator indicator = partyAddress.getAddressIndicator();
        assertEquals(1, indicator.getSubSystemNumberIndicator());

        Utils.logTrace("Acr message: \n{}", getClass(), buffer.array());
    }

    @Test
    public void testParseDisconnect() throws IOException, URISyntaxException {
        String message = DumpReader.getMessage("/test_data/bugfix/bscmEventDisconnect.textdump");
        FullMessage pojoList = new MessageParser().parse(message).iterator().next();
        ByteBuffer buffer = FullMessageEncoder.encode(pojoList);
        Utils.logTrace("ErbDisconnect message: \n{}", getClass(), buffer.array());
    }

    @Test
    public void testParseAnswer() throws IOException, URISyntaxException {
        String message = DumpReader.getMessage("/test_data/bugfix/bscmEventOAnwser.textdump");
        FullMessage pojoList = new MessageParser().parse(message).iterator().next();
        ByteBuffer buffer = FullMessageEncoder.encode(pojoList);
        Utils.logTrace("ErbDisconnect message: \n{}", getClass(), buffer.array());
    }
    
    @Test
    public void testParseInitialIdp() throws IOException, URISyntaxException {
        String message = DumpReader.getMessage("/test_data/bugfix/initialIDP.textdump");
        FullMessage pojoList = new MessageParser().parse(message).iterator().next();
        ByteBuffer buffer = FullMessageEncoder.encode(pojoList);
        Utils.logTrace("InitialIdp message: \n{}", getClass(), buffer.array());
    }

    @Test
    public void givenInitDP_andCallingPartyHas10Digit_whenWeSendIt_thenCallingPartybInCapContains10Digit() throws IOException, URISyntaxException {
        String message = DumpReader.getMessage("/test_data/bugfix/givenInitDP_andCallingPartyHas10Digit.textdump");
        FullMessage pojoList = new MessageParser().parse(message).iterator().next();
        ByteBuffer buffer = FullMessageEncoder.encode(pojoList);
        Utils.logTrace("InitialIdp message: \n{}", getClass(), buffer.array());
    }
    
    @Test
    public void testParseCallWaitingIDP() throws IOException, URISyntaxException {
        String message = DumpReader.getMessage("/test_data/bugfix/callwaitingIDP.textdump");
        FullMessage pojoList = new MessageParser().parse(message).iterator().next();
        ByteBuffer buffer = FullMessageEncoder.encode(pojoList);
        Utils.logTrace("InitialIdp message: \n{}", getClass(), buffer.array());
    }

    @Test
    public void testEncodeMessage() {
        byte[] b = new byte[]{1, 5, 1, 4, 4, 2, 4, 0, 5, 4, 0};
        ByteBuffer buffer = ByteBuffer.allocate(b.length);
        for (int i = 0; i < b.length; i = i + 2) {
            int secondPart = b[i];
            if (b.length > i + 1) {
                int val = b[i + 1] & 0xf;
                val = val << 4;
                val |= (byte) (secondPart & 0xf);
                buffer.put((byte) val);
            } else {
                buffer.put((byte) (b[i] & 0xf));
            }
        }
        buffer.flip();
        byte[] arr = Utils.subBuffer(buffer.limit(), buffer).array();
        Utils.logTrace("Source {}", getClass(), b);
        Utils.logTrace("Target {}", getClass(), arr);
        for (byte item : arr) {
            System.out.println(Integer.toBinaryString(item));
        }
    }

    @Test
    public void testParseInitialDpCallForwarding() throws IOException, URISyntaxException {
        MessageParser parser = new MessageParser();
        FullMessage fullMessage = parser.parse(
                DumpReader.getMessage("/test_data/bugfix/initialIdpCallForwarding.textdump")
        ).iterator().next();
        TcapMessage tcap = fullMessage.getTcap();
        List<CapMessage> capMessages = tcap.getCapMessages();
        CapMessage capMessage = capMessages.get(0);
        InitialDetectionPoint invoke = capMessage.getInvoke().getCapMessagePojo();
        assertNotNull(invoke.getCalledPartyNumber());
        assertNotNull(invoke.getCallingPartyNumber());
        assertNotNull(invoke.getCallingPartysCategory());
        assertEquals("12", invoke.getEventTypeBCSM().getStringBytes());
        Extension extension = invoke.getInitialDpArgExtension().getExtensions().iterator().next();
        assertEquals(0x1, extension.getCicSelectionType().getId());

    }

    @Test(timeout = 3000)
    public void testParseHangs() throws IOException, URISyntaxException {
        MessageParser parser = new MessageParser();
        parser.parse(DumpReader.getMessage("/test_data/bugfix/hangs.textdump"));
        parser.parse(DumpReader.getMessage("/test_data/bugfix/IsITUStandard.textdump"));
    }
}
