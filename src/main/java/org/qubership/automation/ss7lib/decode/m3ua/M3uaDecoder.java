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

package org.qubership.automation.ss7lib.decode.m3ua;

import java.nio.ByteBuffer;

import org.qubership.automation.ss7lib.decode.Decoder;
import org.qubership.automation.ss7lib.model.M3uaMessage;
import org.qubership.automation.ss7lib.model.sub.m3ua.NetworkAppearance;
import org.qubership.automation.ss7lib.model.sub.m3ua.ProtocolData;
import org.qubership.automation.ss7lib.model.sub.m3ua.RoutingContext;
import org.qubership.automation.ss7lib.model.type.EnumProvider;
import org.qubership.automation.ss7lib.model.type.MessageClass;
import org.qubership.automation.ss7lib.model.type.MessageType;
import org.qubership.automation.ss7lib.model.type.ParameterTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class M3uaDecoder implements Decoder<M3uaMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(M3uaDecoder.class);

    @Override
    public M3uaMessage decode(ByteBuffer buffer) {
        LOGGER.info("Staring parsing M3ua part", buffer);
        LOGGER.debug("parameters: {}", buffer.array());
        M3uaMessage message = new M3uaMessage();
        message.setVersion(buffer.get());
        message.setReserved(buffer.get());
        message.setMessageClass(EnumProvider.of(buffer.get(), MessageClass.class));
        message.setMessageType(EnumProvider.of(buffer.get(), MessageType.class));
        message.setMessageLength(buffer.getInt());
        LOGGER.debug("M3ua headers: {}", message);
        message.setNetworkAppearance(parseNetworkAppearance(buffer));
        message.setRoutingContext(parseRoutingContext(buffer));
        message.setProtocolData(parseProtocolData(buffer));
        LOGGER.info("M3ua part successfully parsed. Content: {}", message);
        return message;
    }

    private RoutingContext parseRoutingContext(ByteBuffer buffer) {
        LOGGER.info("Staring parsing Routing Context");
        RoutingContext routingContext = new RoutingContext();
        short tagId = buffer.getShort();
        if (tagId != 6) {
            LOGGER.info("Routing Context is not present in message. Tag Id: {}", tagId);
            buffer.position(buffer.position() - 2);
            return null;
        }
        routingContext.setParameterTag(ParameterTag.of(tagId));
        routingContext.setParameterLength(buffer.getShort());
        routingContext.setRoutingContext(buffer.getInt());
        LOGGER.info("Routing Context successfully prased: {}", routingContext);
        return routingContext;
    }

    private ProtocolData parseProtocolData(ByteBuffer buffer) {
        LOGGER.info("Staring parsing Protocol Data");
        ProtocolData protocolData = new ProtocolData();
        protocolData.setParameterTag(ParameterTag.of(buffer.getShort()));
        protocolData.setParameterLength(buffer.getShort());
        protocolData.setOpc(buffer.getInt());
        protocolData.setDpc(buffer.getInt());
        protocolData.setSi(buffer.get());
        protocolData.setNi(buffer.get());
        protocolData.setMp(buffer.get());
        protocolData.setSls(buffer.get());
        LOGGER.info("Protocol Data successfully parsedL :{}");
        return protocolData;
    }

    private NetworkAppearance parseNetworkAppearance(ByteBuffer buffer) {
        NetworkAppearance networkAppearance = new NetworkAppearance();
        networkAppearance.setParameterTag(ParameterTag.of(buffer.getShort()));
        networkAppearance.setParameterLength(buffer.getShort());
        networkAppearance.setNetworkAppearance(buffer.getInt());
        return networkAppearance;
    }
}
