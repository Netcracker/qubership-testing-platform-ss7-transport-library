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

import static org.qubership.automation.ss7lib.encode.EncoderUtils.reverse;
import static org.qubership.automation.ss7lib.encode.EncoderUtils.splitStringBytes;

import java.util.ArrayList;
import java.util.Objects;

import javax.annotation.Nonnull;

import org.qubership.automation.ss7lib.decode.Utils;
import org.qubership.automation.ss7lib.encode.AbstractEncoder;
import org.qubership.automation.ss7lib.model.AbstractMessage;
import org.qubership.automation.ss7lib.model.SccpMessage;
import org.qubership.automation.ss7lib.model.sub.sccp.AddressIndicator;
import org.qubership.automation.ss7lib.model.sub.sccp.CallPartyAddress;
import org.qubership.automation.ss7lib.model.sub.sccp.GlobalTitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class SCCPEncoder extends AbstractEncoder<SccpMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SCCPEncoder.class);


    @Override
    public byte[] encode(@Nonnull AbstractMessage abstractMessage) {
        SccpMessage pojo = (SccpMessage) abstractMessage;
        LOGGER.info("Start SCCP encode");
        LOGGER.info("Encode message: {}", pojo);
        ArrayList<Byte> bytes = Lists.newArrayList();
        LOGGER.debug("MessageType: {}", pojo.getMessageType());
        bytes.add(pojo.getMessageType().getId());
        LOGGER.debug("MessageHandling: {}", pojo.getMessageHandling());
        LOGGER.debug("Class: {}", pojo.getClazz());
        bytes.add(compoundParametrsToBinaryString(pojo.getMessageHandling(), pojo.getClazz()));
        LOGGER.debug("PointerToFirstMandatoryVariable: {}", pojo.getPointerToFirstMandatoryVariable());
        bytes.add(pojo.getPointerToFirstMandatoryVariable());
        LOGGER.debug("PointerToSecondMandatoryVariable: {}", pojo.getPointerToSecondMandatoryVariable());
        bytes.add(pojo.getPointerToSecondMandatoryVariable());
        LOGGER.debug("PointerToThirdMandatoryVariable: {}", pojo.getPointerToThirdMandatoryVariable());
        bytes.add(pojo.getPointerToThirdMandatoryVariable());
        if (Objects.nonNull(pojo.getCalledPartyAddress())) {
            LOGGER.debug("Start CalledPartyAddress for SCCP: ");
            encodeCall(bytes, pojo.getCalledPartyAddress(), pojo.isITUProtocol());
        }
        if (Objects.nonNull(pojo.getCallingPartyAddress())) {
            LOGGER.debug("Start CallingPartyAddress for SCCP: ");
            encodeCall(bytes, pojo.getCallingPartyAddress(), pojo.isITUProtocol());
        }
        byte[] result = convertListToArray(bytes);
        Utils.logTrace("Finish SCCP message encode: \n{}", getClass(), result);
        return result;
    }

    private void encodeCall(@Nonnull ArrayList<Byte> bytes, @Nonnull CallPartyAddress pojo, boolean isITUProtocol) {
        ArrayList<Byte> sub_bytes = Lists.newArrayList();
        if (Objects.nonNull(pojo.getAddressIndicator())) {
            LOGGER.debug("Start AddressIndicator: {}", pojo.getAddressIndicator());
            encodeAI(sub_bytes, pojo.getAddressIndicator(), isITUProtocol);
        }
        LOGGER.debug("SubSystemNumber: {}", pojo.getSubSystemNumber());
        sub_bytes.add(pojo.getSubSystemNumber().getCode());
        if (Objects.nonNull(pojo.getGlobalTitle())) {
            LOGGER.debug("Start GlobalTitle for CalledPartyAddress foe SCCP: ");
            encodeGT(sub_bytes, pojo.getGlobalTitle());
        }
        pojo.setLength((byte) sub_bytes.size());
        LOGGER.debug("Length: {}", pojo.getLength());
        bytes.add(pojo.getLength());
        bytes.addAll(sub_bytes);
    }

    private void encodeAI(@Nonnull ArrayList<Byte> bytes, @Nonnull AddressIndicator pojo, boolean isITUProtocol) {
        byte result = (byte) 0;
        result = (byte) (result << 1 | pojo.getNationalIndicator());
        result = (byte) (result << 1 | pojo.getRoutingIndicator());
        result = (byte) (result << 4 | pojo.getGlobalTitleIndicator());
        if (isITUProtocol) {
            result = (byte) (result << 1 | pojo.getSubSystemNumberIndicator());
            result = (byte) (result << 1 | pojo.getPointCodeIndicator());
        } else {
            result = (byte) (result << 1 | pojo.getPointCodeIndicator());
            result = (byte) (result << 1 | pojo.getSubSystemNumberIndicator());
        }
        Utils.logTrace("Address idicator: {}", getClass(), result);
        bytes.add(result);
    }

    private void encodeGT(@Nonnull ArrayList<Byte> bytes, @Nonnull GlobalTitle pojo) {
        LOGGER.debug("TranslationType: {}", pojo.getTranslationType());
        bytes.add(pojo.getTranslationType());
        LOGGER.debug("CallPartyDigits: {}", pojo.getCallPartyDigits());
        bytes.addAll(splitStringBytes(reverse(String.valueOf(pojo.getCallPartyDigits())), true));
    }

    private byte compoundParametrsToBinaryString(Byte... pieceOfBinary) {
        byte result = 0;
        for (byte piece : pieceOfBinary) {
            result = (byte) (result << 4 | piece);
        }
        return result;
    }


}
