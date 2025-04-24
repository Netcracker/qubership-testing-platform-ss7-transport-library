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

import static org.qubership.automation.ss7lib.convert.Converter.bytesToCallPartyDigits;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.qubership.automation.ss7lib.decode.Decoder;
import org.qubership.automation.ss7lib.model.SccpMessage;
import org.qubership.automation.ss7lib.model.sub.sccp.AddressIndicator;
import org.qubership.automation.ss7lib.model.sub.sccp.CallPartyAddress;
import org.qubership.automation.ss7lib.model.sub.sccp.GlobalTitle;
import org.qubership.automation.ss7lib.model.type.EnumProvider;
import org.qubership.automation.ss7lib.model.type.MessageType;
import org.qubership.automation.ss7lib.model.type.SubSystemNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SccpDecoder implements Decoder<SccpMessage> {

    private static final int ADDR_INDICATOR_AND_SUBSYSTEM_NUM_SIZE = 2;
    private static final Logger LOGGER = LoggerFactory.getLogger(SccpDecoder.class);

    @Override
    public SccpMessage decode(ByteBuffer buffer) {
        LOGGER.info("Start parsing sccp message part");
        SccpMessage sccpMessage = new SccpMessage();
        sccpMessage.setMessageType(EnumProvider.of(buffer.get(), MessageType.class));
        byte classAndHandling = buffer.get();
        sccpMessage.setClazz(parseClass(classAndHandling));
        sccpMessage.setMessageHandling(parseHandling(classAndHandling));
        sccpMessage.setPointerToFirstMandatoryVariable(buffer.get());
        sccpMessage.setPointerToSecondMandatoryVariable(buffer.get());
        sccpMessage.setPointerToThirdMandatoryVariable(buffer.get());
        LOGGER.debug("Sccp headers: {}", sccpMessage);
        sccpMessage.setCalledPartyAddress(parseAddress(buffer));
        sccpMessage.setCallingPartyAddress(parseAddress(buffer));
        return sccpMessage;
    }

    private byte parseHandling(byte classAndHandling) {
        return (byte) ((byte) (classAndHandling >> 4) & 15);
    }

    private byte parseClass(byte classAndHandling) {
        return (byte) (classAndHandling & 15);
    }

    private CallPartyAddress parseAddress(ByteBuffer buffer) {
        CallPartyAddress partyAddress = new CallPartyAddress();
        byte addressSize = buffer.get();
        AddressIndicator indicator = parseAddressIndicator(buffer);
        partyAddress.setAddressIndicator(indicator);
        partyAddress.setSubSystemNumber(SubSystemNumber.of(buffer.get()));
        GlobalTitle globalTitle = new GlobalTitle();
        parseGlobalTitle(globalTitle, buffer, addressSize);
        partyAddress.setGlobalTitle(globalTitle);
        return partyAddress;
    }

    private void parseGlobalTitle(GlobalTitle globalTitle, ByteBuffer buffer, byte addressSize) {
        byte[] data = new byte[addressSize - ADDR_INDICATOR_AND_SUBSYSTEM_NUM_SIZE];
        buffer.get(data);
        globalTitle.setTranslationType(data[0]);
        byte[] partyAddress = Arrays.copyOfRange(data, 1, data.length);
        char[] chars = bytesToCallPartyDigits(partyAddress).toCharArray();
        globalTitle.setCallPartyDigits(String.valueOf(chars));
    }

    private AddressIndicator parseAddressIndicator(ByteBuffer buffer) {
        AddressIndicator indicator = new AddressIndicator();
        byte addressIndicator = buffer.get();
        indicator.setNationalIndicator((byte) ((byte) (addressIndicator & 128) >> 7 & 1));
        indicator.setRoutingIndicator((byte) ((byte) (addressIndicator & 64) >> 6 & 1));
        indicator.setGlobalTitleIndicator((byte) ((byte) (addressIndicator & 60) >> 2 & 7));
        indicator.setPointCodeIndicator((byte) ((byte) (addressIndicator & 2) >> 1 & 1));
        indicator.setSubSystemNumberIndicator((byte) (addressIndicator & 1));
        return indicator;
    }

}
