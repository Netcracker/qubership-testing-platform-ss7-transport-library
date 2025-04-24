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

package org.qubership.automation.ss7lib.parse.parser.cap;

import org.qubership.automation.ss7lib.model.CapMessage;
import org.qubership.automation.ss7lib.model.sub.cap.CapInvoke;
import org.qubership.automation.ss7lib.model.sub.cap.message.InitialDetectionPoint;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.PartyNumber;
import org.qubership.automation.ss7lib.parse.parser.PartParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PartyNumberParserTest {
    private static final String INPUT = "callingPartyNumber: 8415514124044500\n" +
            "    1... .... = Odd/even indicator: odd number of address signals\n" +
            "    .000 0100 = Nature of address indicator: international number (4)\n" +
            "    0... .... = NI indicator: complete\n" +
            "    .001 .... = Numbering plan indicator: ISDN (Telephony) numbering plan ITU-T E.164 (1)\n" +
            "    .... 01.. = Address presentation restricted indicator: presentation restricted (1)\n" +
            "    .... ..01 = Screening indicator: user provided, verified and passed (1)\n" +
            "    Calling Party Number: 15144240540\n";
    private PartParser parser;
    private CapMessage capMessage;
    private PartyNumber partyNumber;

    @Before
    public void setUp() throws Exception {
        parser = new PartyNumberParser();
        capMessage = new CapMessage();
        CapInvoke invoke = new CapInvoke();
        capMessage.setInvoke(invoke);
        InitialDetectionPoint idp = new InitialDetectionPoint();
        partyNumber = new PartyNumber();
        idp.setCallingPartyNumber(partyNumber);
        invoke.setCapMessagePojo(idp);
    }

    @Test
    public void testParseOddIndicator() {
        for (String input : INPUT.split("\n")) {
            parser.parse(input, partyNumber);
        }
        assertEquals(0x1, partyNumber.getOddIndicator());
    }
    @Test
    public void testParseNatureAddressIndicator() {
        for (String input : INPUT.split("\n")) {
            parser.parse(input, partyNumber);
        }
        assertEquals(0x4, partyNumber.getNatureAddressIndicator());
    }
    @Test
    public void testParseNetworkInformationIndicator() {
        for (String input : INPUT.split("\n")) {
            parser.parse(input, partyNumber);
        }
        assertEquals(0x0, partyNumber.getNetworkInformationIndicator());
    }
    @Test
    public void testParseNumberingPlanIndicator() {
        for (String input : INPUT.split("\n")) {
            parser.parse(input, partyNumber);
        }
        assertEquals(0x1, partyNumber.getNumberingPlanIndicator());
    }
    @Test
    public void testParseAddressPresentationIndicator() {
        for (String input : INPUT.split("\n")) {
            parser.parse(input, partyNumber);
        }
        assertEquals(0x1, partyNumber.getAddressPresentationIndicator());
    }
    @Test
    public void testParseScreeningIndicator() {
        for (String input : INPUT.split("\n")) {
            parser.parse(input, partyNumber);
        }
        assertEquals(0x1, partyNumber.getScreeningIndicator());
    }
    @Test
    public void testParseNumber() {
        for (String input : INPUT.split("\n")) {
            parser.parse(input, partyNumber);
        }
        assertEquals("15144240540", partyNumber.getNumber());
    }
}
