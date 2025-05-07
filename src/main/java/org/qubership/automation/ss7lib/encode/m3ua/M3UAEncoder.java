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

package org.qubership.automation.ss7lib.encode.m3ua;

import java.util.ArrayList;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.qubership.automation.ss7lib.decode.Utils;
import org.qubership.automation.ss7lib.encode.AbstractEncoder;
import org.qubership.automation.ss7lib.model.AbstractMessage;
import org.qubership.automation.ss7lib.model.M3uaMessage;
import org.qubership.automation.ss7lib.model.sub.m3ua.NetworkAppearance;
import org.qubership.automation.ss7lib.model.sub.m3ua.ProtocolData;
import org.qubership.automation.ss7lib.model.sub.m3ua.RoutingContext;
import org.qubership.automation.ss7lib.model.type.ParameterTag;
import org.qubership.automation.ss7lib.model.type.Standard;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class M3UAEncoder extends AbstractEncoder<M3uaMessage> {

    /**
     * Encode message as M3uaMessage into byte[].
     *
     * @param abstractMessage message to encode
     * @return byte[] result of encoding of source message as M3uaMessage.
     */
    @Override
    public byte[] encode(@Nonnull final AbstractMessage abstractMessage) {
        M3uaMessage pojo = (M3uaMessage) abstractMessage;
        log.info("Start M3UA message encode: {}", pojo);
        ArrayList<Byte> bytes = Lists.newArrayList();
        log.debug("Version: {}", pojo.getVersion());
        bytes.add(pojo.getVersion());
        log.debug("Reserved: {}", pojo.getReserved());
        bytes.add(pojo.getReserved());
        log.debug("MessageClass: {}", pojo.getMessageClass());
        bytes.add(pojo.getMessageClass().getId());
        log.debug("MessageType: {}", pojo.getMessageType());
        bytes.add(pojo.getMessageType().getId());
        log.debug("MessageLength: {}", pojo.getMessageLength());
        bytes.addAll(asList(intToBytes(pojo.getMessageLength().intValue())));
        encodeSUB(bytes, pojo);
        byte[] result = convertListToArray(bytes);
        Utils.logTrace("Finish M3UA message encode: \n{}", getClass(), result);
        return result;
    }

    private void encodeSUB(@Nonnull final ArrayList<Byte> bytes,
                           @Nonnull final M3uaMessage pojo) {
        if (Objects.nonNull(pojo.getNetworkAppearance())) {
            log.debug("Start NetworkAppearance for M3UA: ");
            encodeNetworkAppearance(bytes, pojo.getNetworkAppearance());
        }
        if (Objects.nonNull(pojo.getRoutingContext())) {
            log.debug("Start RoutingContext for M3UA: ");
            encodeRoutingContext(bytes, pojo.getRoutingContext());
        }
        if (Objects.nonNull(pojo.getProtocolData())) {
            log.debug("Start ProtocolData for M3UA: ");
            encodeProtocolData(bytes, pojo.getProtocolData(), pojo.getStandard());
        }
    }

    private void encodeCommonPartOfM3UA(@Nonnull final ArrayList<Byte> bytes,
                                        final ParameterTag parameterTag,
                                        final short parameterLength,
                                        final int extraProperty,
                                        @Nullable final String subClassName) {
        log.debug("ParameterTag: {}, ParameterLength: {}", parameterTag, parameterLength);
        bytes.addAll(asList(parameterTag.getCode()));
        bytes.addAll(asList(shortToBytes(parameterLength)));
        if (subClassName != null) {
            log.debug("{}: {}", subClassName, extraProperty);
            bytes.addAll(asList(intToBytes(extraProperty)));
        }
    }

    private void encodeNetworkAppearance(@Nonnull final ArrayList<Byte> bytes,
                                         @Nonnull final NetworkAppearance pojo) {
        encodeCommonPartOfM3UA(bytes, pojo.getParameterTag(), pojo.getParameterLength(),
                pojo.getNetworkAppearance(), "NetworkAppearance");
    }

    private void encodeRoutingContext(@Nonnull final ArrayList<Byte> bytes,
                                      @Nonnull final RoutingContext pojo) {
        encodeCommonPartOfM3UA(bytes, pojo.getParameterTag(), pojo.getParameterLength(),
                pojo.getRoutingContext(), "RoutingContext");
    }

    private void encodeProtocolData(@Nonnull final ArrayList<Byte> bytes,
                                    @Nonnull final ProtocolData pojo,
                                    final Standard standard) {
        encodeCommonPartOfM3UA(bytes, pojo.getParameterTag(), pojo.getParameterLength(),
                0, null);
        bytes.addAll(asList(standard.encode(pojo.getOpc()))); //new byte[]{0, 0, 3, 32}
        bytes.addAll(asList(standard.encode(pojo.getDpc()))); //new byte[]{0, 0, 1, 127}
        bytes.add(pojo.getSi());
        bytes.add(pojo.getNi());
        bytes.add(pojo.getMp());
        bytes.add(pojo.getSls());
        log.debug("Opc: {}, Dpc: {}, Si: {}, Ni: {}, Mp: {}, Sls: {}", pojo.getOpc(), pojo.getDpc(), pojo.getSi(),
                pojo.getNi(), pojo.getMp(), pojo.getSls());
    }
}
