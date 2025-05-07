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

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SCCPEncoder extends AbstractEncoder<SccpMessage> {

    /**
     * Encode SCCP Message; place result into a new byte[].
     *
     * @param abstractMessage - AbstractMessage to encode
     * @return a new byte[] filled with encoding result.
     */
    @Override
    public byte[] encode(@Nonnull final AbstractMessage abstractMessage) {
        SccpMessage pojo = (SccpMessage) abstractMessage;
        log.info("Start encode of SCCP message: {}", pojo);
        ArrayList<Byte> bytes = Lists.newArrayList();
        log.debug("MessageType: {}", pojo.getMessageType());
        bytes.add(pojo.getMessageType().getId());
        log.debug("MessageHandling: {}, Class: {}", pojo.getMessageHandling(), pojo.getClazz());
        bytes.add(compoundParametersToBinaryString(pojo.getMessageHandling(), pojo.getClazz()));
        log.debug("PointerToFirstMandatoryVariable: {}", pojo.getPointerToFirstMandatoryVariable());
        bytes.add(pojo.getPointerToFirstMandatoryVariable());
        log.debug("PointerToSecondMandatoryVariable: {}", pojo.getPointerToSecondMandatoryVariable());
        bytes.add(pojo.getPointerToSecondMandatoryVariable());
        log.debug("PointerToThirdMandatoryVariable: {}", pojo.getPointerToThirdMandatoryVariable());
        bytes.add(pojo.getPointerToThirdMandatoryVariable());
        if (Objects.nonNull(pojo.getCalledPartyAddress())) {
            log.debug("Start CalledPartyAddress for SCCP: ");
            encodeCall(bytes, pojo.getCalledPartyAddress(), pojo.isITUProtocol());
        }
        if (Objects.nonNull(pojo.getCallingPartyAddress())) {
            log.debug("Start CallingPartyAddress for SCCP: ");
            encodeCall(bytes, pojo.getCallingPartyAddress(), pojo.isITUProtocol());
        }
        byte[] result = convertListToArray(bytes);
        Utils.logTrace("Finish SCCP message encode: \n{}", getClass(), result);
        return result;
    }

    private void encodeCall(@Nonnull final ArrayList<Byte> bytes,
                            @Nonnull final CallPartyAddress pojo,
                            final boolean isITUProtocol) {
        ArrayList<Byte> subBytes = Lists.newArrayList();
        if (Objects.nonNull(pojo.getAddressIndicator())) {
            log.debug("Start AddressIndicator: {}", pojo.getAddressIndicator());
            encodeAddressIndicator(subBytes, pojo.getAddressIndicator(), isITUProtocol);
        }
        log.debug("SubSystemNumber: {}", pojo.getSubSystemNumber());
        subBytes.add(pojo.getSubSystemNumber().getCode());
        if (Objects.nonNull(pojo.getGlobalTitle())) {
            log.debug("Start GlobalTitle for CalledPartyAddress foe SCCP: ");
            encodeGlobalTitle(subBytes, pojo.getGlobalTitle());
        }
        pojo.setLength((byte) subBytes.size());
        log.debug("Length: {}", pojo.getLength());
        bytes.add(pojo.getLength());
        bytes.addAll(subBytes);
    }

    private void encodeAddressIndicator(@Nonnull final ArrayList<Byte> bytes,
                                        @Nonnull final AddressIndicator pojo,
                                        final boolean isITUProtocol) {
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
        Utils.logTrace("Address indicator: {}", getClass(), result);
        bytes.add(result);
    }

    private void encodeGlobalTitle(@Nonnull final ArrayList<Byte> bytes,
                                   @Nonnull final GlobalTitle pojo) {
        log.debug("TranslationType: {}, CallPartyDigits: {}", pojo.getTranslationType(), pojo.getCallPartyDigits());
        bytes.add(pojo.getTranslationType());
        bytes.addAll(splitStringBytes(reverse(String.valueOf(pojo.getCallPartyDigits())), true));
    }

    private byte compoundParametersToBinaryString(final Byte... pieceOfBinary) {
        byte result = 0;
        for (byte piece : pieceOfBinary) {
            result = (byte) (result << 4 | piece);
        }
        return result;
    }

}
