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

import org.qubership.automation.ss7lib.decode.Utils;
import org.qubership.automation.ss7lib.encode.AbstractEncoder;
import org.qubership.automation.ss7lib.model.AbstractMessage;
import org.qubership.automation.ss7lib.model.M3uaMessage;
import org.qubership.automation.ss7lib.model.sub.m3ua.NetworkAppearance;
import org.qubership.automation.ss7lib.model.sub.m3ua.ProtocolData;
import org.qubership.automation.ss7lib.model.sub.m3ua.RoutingContext;
import org.qubership.automation.ss7lib.model.type.Standard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class M3UAEncoder extends AbstractEncoder<M3uaMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(M3UAEncoder.class);


    @Override
    public byte[] encode(@Nonnull AbstractMessage abstractMessage) {
        M3uaMessage pojo = (M3uaMessage) abstractMessage;
        LOGGER.info("Start M3UA message encode: {}", pojo);
        LOGGER.info("Encode message: {}", pojo);
        ArrayList<Byte> bytes = Lists.newArrayList();
        LOGGER.debug("Version: {}", pojo.getVersion());
        bytes.add(pojo.getVersion());
        LOGGER.debug("Reserved: {}", pojo.getReserved());
        bytes.add(pojo.getReserved());
        LOGGER.debug("MessageClass: {}", pojo.getMessageClass());
        bytes.add(pojo.getMessageClass().getId());
        LOGGER.debug("MessageType: {}", pojo.getMessageType());
        bytes.add(pojo.getMessageType().getId());
        LOGGER.debug("MessageLength: {}", pojo.getMessageLength());
        bytes.addAll(asList(intToBytes(pojo.getMessageLength().intValue())));
        encodeSUB(bytes, pojo);
        byte[] result = convertListToArray(bytes);
        Utils.logTrace("Finish M3UA message encode: \n{}", getClass(), result);
        return result;
    }

    private void encodeSUB(@Nonnull ArrayList<Byte> bytes, @Nonnull M3uaMessage pojo) {
        if (Objects.nonNull(pojo.getNetworkAppearance())) {
            LOGGER.debug("Start NetworkAppearance for M3UA: ");
            encodeNA(bytes, pojo.getNetworkAppearance());
        }
        if (Objects.nonNull(pojo.getRoutingContext())) {
            LOGGER.debug("Start RoutingContext for M3UA: ");
            encodeRC(bytes, pojo.getRoutingContext());
        }
        if (Objects.nonNull(pojo.getProtocolData())) {
            LOGGER.debug("Start ProtocolData for M3UA: ");
            encodePD(bytes, pojo.getProtocolData(), pojo.getStandard());
        }
    }

    private void encodeNA(@Nonnull ArrayList<Byte> bytes, @Nonnull NetworkAppearance pojo) {
        LOGGER.debug("ParameterTag: {}", pojo.getParameterTag());
        bytes.addAll(asList(pojo.getParameterTag().getCode()));
        LOGGER.debug("ParameterLength: {}", pojo.getParameterLength());
        bytes.addAll(asList(shortToBytes(pojo.getParameterLength())));
        LOGGER.debug("NetworkAppearance: {}", pojo.getNetworkAppearance());
        bytes.addAll(asList(intToBytes(pojo.getNetworkAppearance())));
    }

    private void encodeRC(@Nonnull ArrayList<Byte> bytes, @Nonnull RoutingContext pojo) {
        LOGGER.debug("ParameterTag: {}", pojo.getParameterTag());
        bytes.addAll(asList(pojo.getParameterTag().getCode()));
        LOGGER.debug("ParameterLength: {}", pojo.getParameterLength());
        bytes.addAll(asList(shortToBytes(pojo.getParameterLength())));
        LOGGER.debug("RoutingContext: {}", pojo.getRoutingContext());
        bytes.addAll(asList(intToBytes(pojo.getRoutingContext())));
    }

    private void encodePD(@Nonnull ArrayList<Byte> bytes, @Nonnull ProtocolData pojo, Standard standard) {
        LOGGER.debug("ParameterTag: {}", pojo.getParameterTag());
        bytes.addAll(asList((pojo.getParameterTag().getCode())));
        LOGGER.debug("ParameterLength: {}", pojo.getParameterLength());
        bytes.addAll(asList(shortToBytes(pojo.getParameterLength())));
        LOGGER.debug("Opc: {}", pojo.getOpc());
        bytes.addAll(asList(standard.encode(pojo.getOpc())));//new byte[]{0, 0, 3, 32}
        LOGGER.debug("Dpc: {}", pojo.getDpc());
        bytes.addAll(asList(standard.encode(pojo.getDpc())));//new byte[]{0, 0, 1, 127}
        LOGGER.debug("Si: {}", pojo.getSi());
        bytes.add(pojo.getSi());
        LOGGER.debug("Ni: {}", pojo.getNi());
        bytes.add(pojo.getNi());
        LOGGER.debug("Mp: {}", pojo.getMp());
        bytes.add(pojo.getMp());
        LOGGER.debug("Sls: {}", pojo.getSls());
        bytes.add(pojo.getSls());
    }
}
