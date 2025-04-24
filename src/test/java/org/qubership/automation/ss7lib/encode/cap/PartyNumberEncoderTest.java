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

package org.qubership.automation.ss7lib.encode.cap;

import org.qubership.automation.ss7lib.encode.PartEncoder;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.PartyNumber;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class PartyNumberEncoderTest {

    private PartEncoder<PartyNumber> encoder;
    private PartyNumber partyNumber;

    @Before
    public void setUp() throws Exception {
        encoder = new PartyNumberEncoder();
        partyNumber = new PartyNumber();
        partyNumber.setOddIndicator((byte) 1);
        partyNumber.setNatureAddressIndicator((byte) 4);
        partyNumber.setNetworkInformationIndicator((byte) 0);
        partyNumber.setNumberingPlanIndicator((byte) 1);
        partyNumber.setAddressPresentationIndicator((byte) 1);
        partyNumber.setScreeningIndicator((byte) 1);
        partyNumber.setNumber("15144240540");
    }

    @Test
    public void testEncodeLength() {
        ByteBuffer encode = encoder.encode(partyNumber);
        assertEquals((byte) 0x08, encode.get(0));
    }

    @Test
    public void testEncodeOddAndNatureAddressIndicator() {
        ByteBuffer encode = encoder.encode(partyNumber);
        assertEquals((byte) 0x84, encode.get(1));
    }

    @Test
    public void testEncodeScreeningNiNumberingPresentationIndicator() {
        ByteBuffer encode = encoder.encode(partyNumber);
        assertEquals((byte) 0x15, encode.get(2));
    }

    @Test
    public void given11DigitNumber_WhenWeEncodeIt2byte_ThenItHasCorrectBytes() {
        partyNumber.setNumber("15144240540");
        ByteBuffer encode = encoder.encode(partyNumber);
        byte[] bytes = new byte[6];
        encode.position(3); /*Skip indicators*/
        encode.get(bytes, 0, 6);
        assertArrayEquals(new byte[]{0x51, 0x41, 0x24, 0x04, 0x45, 0x00}, bytes);
    }

    @Test
    public void given10DigitNumber_WhenWeEncodeIt2byte_ThenItHasCorrectBytes() {
        partyNumber.setNumber("15144240544");
        ByteBuffer encode = encoder.encode(partyNumber);
        byte[] bytes = new byte[5];
        encode.position(3); /*Skip indicators*/
        encode.get(bytes, 0, 5);
        assertArrayEquals(new byte[]{0x51, 0x41, 0x24, 0x04, 0x45}, bytes);
    }

}
