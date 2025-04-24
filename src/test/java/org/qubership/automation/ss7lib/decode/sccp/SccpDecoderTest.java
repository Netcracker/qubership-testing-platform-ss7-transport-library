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

package org.qubership.automation.ss7lib.decode.sccp;

import org.qubership.automation.ss7lib.DumpReader;
import org.qubership.automation.ss7lib.decode.m3ua.M3uaDecoder;
import org.qubership.automation.ss7lib.model.SccpMessage;
import org.qubership.automation.ss7lib.model.sub.sccp.AddressIndicator;
import org.qubership.automation.ss7lib.model.sub.sccp.CallPartyAddress;
import org.qubership.automation.ss7lib.model.sub.sccp.GlobalTitle;
import org.qubership.automation.ss7lib.model.type.MessageType;
import org.qubership.automation.ss7lib.model.type.SubSystemNumber;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SccpDecoderTest {

    @Test
    public void testParseSccpMessagePart() throws IOException, URISyntaxException {
        ByteBuffer trace = getTrace("/test_data/requestReportBCSVMEvent.hexdump");
        SccpDecoder decoder = new SccpDecoder();
        SccpMessage sccpMessage = decoder.decode(trace);
        assertEquals(MessageType.UNIT_DATA, sccpMessage.getMessageType());
        assertEquals(1, sccpMessage.getClazz());
        assertEquals(8, sccpMessage.getMessageHandling());
        assertEquals(3, sccpMessage.getPointerToFirstMandatoryVariable());
        assertEquals(12, sccpMessage.getPointerToSecondMandatoryVariable());
        assertEquals(21, sccpMessage.getPointerToThirdMandatoryVariable());

        char[] calledNumber = {'1', '5', '1', '4', '4', '2', '4', '0', '5', '1', '4', '0'};
        verifyPartyAddress(calledNumber, sccpMessage.getCalledPartyAddress());

        char[] callingNumber = {'1', '5', '1', '4', '4', '2', '4', '0', '5', '2', '7', '0'};
        verifyPartyAddress(callingNumber, sccpMessage.getCallingPartyAddress());
    }

    private void verifyPartyAddress(char[] number, CallPartyAddress partyAddress) {
        AddressIndicator expected = new AddressIndicator();
        expected.setNationalIndicator((byte) 1);
        expected.setRoutingIndicator((byte) 0);
        expected.setGlobalTitleIndicator((byte) 2);
        expected.setPointCodeIndicator((byte) 0);
        expected.setSubSystemNumberIndicator((byte) 1);
        assertEquals(expected, partyAddress.getAddressIndicator());
        assertEquals(SubSystemNumber.CAP, partyAddress.getSubSystemNumber());
        GlobalTitle globalTitle = partyAddress.getGlobalTitle();
        assertEquals(0x0a, globalTitle.getTranslationType());
        assertArrayEquals(
                number,
                globalTitle.getCallPartyDigits().toCharArray()
        );
    }

    private ByteBuffer getTrace(String path) throws IOException, URISyntaxException {
        ByteBuffer hexTrace = DumpReader.getHexTrace(path);
        M3uaDecoder decoder = new M3uaDecoder();
        decoder.decode(hexTrace);
        return hexTrace;
    }
}
