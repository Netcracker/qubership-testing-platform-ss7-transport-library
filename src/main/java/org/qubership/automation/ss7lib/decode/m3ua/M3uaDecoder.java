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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class M3uaDecoder implements Decoder<M3uaMessage> {

    /**
     * Decode M3UA part from buffer.
     *
     * @param buffer - ByteBuffer parameter to decode
     * @return M3uaMessage decoded from ByteBuffer buffer.
     */
    @Override
    public M3uaMessage decode(final ByteBuffer buffer) {
        log.info("Starting parsing of M3ua part: {}", buffer);
        log.debug("parameters: {}", buffer.array());
        M3uaMessage message = new M3uaMessage();
        message.setVersion(buffer.get());
        message.setReserved(buffer.get());
        message.setMessageClass(EnumProvider.of(buffer.get(), MessageClass.class));
        message.setMessageType(EnumProvider.of(buffer.get(), MessageType.class));
        message.setMessageLength(buffer.getInt());
        log.debug("M3ua headers: {}", message);
        message.setNetworkAppearance(parseNetworkAppearance(buffer));
        message.setRoutingContext(parseRoutingContext(buffer));
        message.setProtocolData(parseProtocolData(buffer));
        log.info("M3ua part successfully parsed. Content: {}", message);
        return message;
    }

    private RoutingContext parseRoutingContext(final ByteBuffer buffer) {
        log.info("Staring parsing of Routing Context");
        RoutingContext routingContext = new RoutingContext();
        short tagId = buffer.getShort();
        if (tagId != 6) {
            log.info("Routing Context is not present in the message. Tag Id: {}", tagId);
            buffer.position(buffer.position() - 2);
            return null;
        }
        routingContext.setParameterTag(ParameterTag.of(tagId));
        routingContext.setParameterLength(buffer.getShort());
        routingContext.setRoutingContext(buffer.getInt());
        log.info("Routing Context successfully parsed: {}", routingContext);
        return routingContext;
    }

    private ProtocolData parseProtocolData(final ByteBuffer buffer) {
        log.info("Starting parsing Protocol Data");
        ProtocolData protocolData = new ProtocolData();
        protocolData.setParameterTag(ParameterTag.of(buffer.getShort()));
        protocolData.setParameterLength(buffer.getShort());
        protocolData.setOpc(buffer.getInt());
        protocolData.setDpc(buffer.getInt());
        protocolData.setSi(buffer.get());
        protocolData.setNi(buffer.get());
        protocolData.setMp(buffer.get());
        protocolData.setSls(buffer.get());
        log.info("Protocol Data successfully parsed: {}", protocolData);
        return protocolData;
    }

    private NetworkAppearance parseNetworkAppearance(final ByteBuffer buffer) {
        NetworkAppearance networkAppearance = new NetworkAppearance();
        networkAppearance.setParameterTag(ParameterTag.of(buffer.getShort()));
        networkAppearance.setParameterLength(buffer.getShort());
        networkAppearance.setNetworkAppearance(buffer.getInt());
        return networkAppearance;
    }
}
