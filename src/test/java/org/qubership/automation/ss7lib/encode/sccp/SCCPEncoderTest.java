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

package org.qubership.automation.ss7lib.encode.sccp;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;
import org.qubership.automation.ss7lib.model.SccpMessage;
import org.qubership.automation.ss7lib.model.sub.sccp.AddressIndicator;
import org.qubership.automation.ss7lib.model.sub.sccp.CallPartyAddress;
import org.qubership.automation.ss7lib.model.sub.sccp.GlobalTitle;
import org.qubership.automation.ss7lib.model.type.MessageType;
import org.qubership.automation.ss7lib.model.type.SubSystemNumber;

@SuppressWarnings("checkstyle:MagicNumber")
public class SCCPEncoderTest {

    /**
     * Test encoding of SCCP Message.
     */
    @Test
    public void testSccpMessageEncode() {
        SccpMessage pojo = new SccpMessage();
        pojo.setMessageType(MessageType.UNIT_DATA);
        pojo.setClazz((byte) 1);
        pojo.setMessageHandling((byte) 8);
        pojo.setPointerToFirstMandatoryVariable((byte) 3);
        pojo.setPointerToSecondMandatoryVariable((byte) 12);
        pojo.setPointerToThirdMandatoryVariable((byte) 21);

        CallPartyAddress calledPartyAddress = fillCallPartyAddress();
        GlobalTitle globalTitlePojoForCalled = new GlobalTitle();
        globalTitlePojoForCalled.setTranslationType((byte) 10);
        globalTitlePojoForCalled.setCallPartyDigits(
                String.valueOf(new char[]{'1', '5', '1', '4', '4', '2', '4', '0', '5', '3', '1', '0'}));
        calledPartyAddress.setGlobalTitle(globalTitlePojoForCalled);
        pojo.setCalledPartyAddress(calledPartyAddress);

        CallPartyAddress callingPartyAddress = fillCallPartyAddress();
        GlobalTitle globalTitlePojoForCalling = new GlobalTitle();
        globalTitlePojoForCalling.setTranslationType((byte) 10);
        globalTitlePojoForCalling.setCallPartyDigits(
                String.valueOf(new char[]{'1', '5', '1', '4', '4', '2', '4', '0', '5', '1', '4', '0'}));
        callingPartyAddress.setGlobalTitle(globalTitlePojoForCalling);
        pojo.setCallingPartyAddress(callingPartyAddress);

        byte[] sccp = new SCCPEncoder().encode(pojo);
        byte[] correctMessage = {
                0x09, (byte) 0x81, 0x03, 0x0c, 0x15, 0x09, (byte) 0x89, (byte) 0x92,
                0x0a, 0x51, 0x41, 0x24, 0x04, 0x35, 0x01, 0x09,
                (byte) 0x89, (byte) 0x92, 0x0a, 0x51, 0x41, 0x24, 0x04, 0x15,
                0x04};

        assertArrayEquals(correctMessage, sccp);
    }

    private CallPartyAddress fillCallPartyAddress() {
        CallPartyAddress callPartyAddress = new CallPartyAddress();
        AddressIndicator addressIndicator = new AddressIndicator();
        addressIndicator.setNationalIndicator((byte) 1);
        addressIndicator.setRoutingIndicator((byte) 0);
        addressIndicator.setGlobalTitleIndicator((byte) 2);
        addressIndicator.setPointCodeIndicator((byte) 0);
        addressIndicator.setSubSystemNumberIndicator((byte) 1);
        callPartyAddress.setAddressIndicator(addressIndicator);
        callPartyAddress.setSubSystemNumber(SubSystemNumber.CAP);
        // callPartyAddress.setLength((byte) 9);
        return callPartyAddress;
    }
}
