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

package org.qubership.automation.ss7lib.parse.parser;

import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.DPC;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.MESSAGE_CLASS;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.MESSAGE_LENGTH;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.MESSAGE_TYPE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.MP;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.MTP_3_USER_ADAPTATION_LAYER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.NETWORK_APPEARANCE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.NI;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.OPC;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.PARAMETER_LENGTH;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.PARAMETER_TAG;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.PROTOCOL_DATA;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.RESERVED;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.ROUTING_CONTEXT;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.SI;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.SLS;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.VERSION;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.qubership.automation.ss7lib.model.M3uaMessage;
import org.qubership.automation.ss7lib.model.sub.m3ua.NetworkAppearance;
import org.qubership.automation.ss7lib.model.sub.m3ua.ProtocolData;
import org.qubership.automation.ss7lib.model.sub.m3ua.RoutingContext;
import org.qubership.automation.ss7lib.model.type.EnumProvider;
import org.qubership.automation.ss7lib.model.type.MessageClass;
import org.qubership.automation.ss7lib.model.type.MessageType;
import org.qubership.automation.ss7lib.model.type.ParameterTag;
import org.qubership.automation.ss7lib.parse.scenario.ScenarioManager;

public class M3UAParser extends AbstractParser<M3uaMessage> {

    protected void parse(M3uaMessage pojo, String value, List<String> parents) {
        String parent = parents.get(parents.size() - 1);

        if (parent.contains(MTP_3_USER_ADAPTATION_LAYER)) {
            Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getClass());
            if (contain(value, VERSION)) {
                pojo.setVersion(Byte.parseByte(getValue(VERSION, value.trim(), scenario.get(VERSION))));
            } else if (contain(value, RESERVED)) {
                pojo.setReserved(Byte.parseByte(getValue(RESERVED, value.trim(), scenario.get(RESERVED))));
            } else if (contain(value, MESSAGE_CLASS)) {
                pojo.setMessageClass(EnumProvider.of(Byte.parseByte(getValue(MESSAGE_CLASS, value.trim(), scenario.get(MESSAGE_CLASS))), MessageClass.class));
            } else if (contain(value, MESSAGE_TYPE)) {
                pojo.setMessageType(EnumProvider.of(Byte.parseByte(getValue(MESSAGE_CLASS, value.trim(), scenario.get(MESSAGE_CLASS))), MessageType.class));
            } else if (contain(value, MESSAGE_LENGTH)) {
                pojo.setMessageLength(Integer.parseInt(getValue(RESERVED, value.trim(), scenario.get(RESERVED))));
            }
        } else if (parent.contains(NETWORK_APPEARANCE)) {
            if (Objects.isNull(pojo.getNetworkAppearance())) {
                pojo.setNetworkAppearance(new NetworkAppearance());
            }
            Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getNetworkAppearance().getClass());
            if (contain(value, PARAMETER_TAG)) {
                pojo.getNetworkAppearance().setParameterTag(ParameterTag.of(Short.parseShort(getValue(PARAMETER_TAG, value.trim(), scenario.get(PARAMETER_TAG)))));
            } else if (contain(value, PARAMETER_LENGTH)) {
                pojo.getNetworkAppearance().setParameterLength(Short.parseShort(getValue(PARAMETER_LENGTH, value.trim(), scenario.get(PARAMETER_LENGTH))));
            } else if (contain(value, NETWORK_APPEARANCE)) {
                pojo.getNetworkAppearance().setNetworkAppearance(Integer.parseInt(getValue(NETWORK_APPEARANCE, value.trim(), scenario.get(NETWORK_APPEARANCE))));
            }
        } else if (parent.contains(ROUTING_CONTEXT)) {
            if (Objects.isNull(pojo.getRoutingContext())) {
                pojo.setRoutingContext(new RoutingContext());
            }
            Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getRoutingContext().getClass());
            if (contain(value, PARAMETER_TAG)) {
                pojo.getRoutingContext().setParameterTag(ParameterTag.of(Short.parseShort(getValue(PARAMETER_TAG, value.trim(), scenario.get(PARAMETER_TAG)))));
            } else if (contain(value, PARAMETER_LENGTH)) {
                pojo.getRoutingContext().setParameterLength(Short.parseShort(getValue(PARAMETER_LENGTH, value.trim(), scenario.get(PARAMETER_LENGTH))));
            } else if (contain(value, ROUTING_CONTEXT)) {
                pojo.getRoutingContext().setRoutingContext(Integer.parseInt(getValue(ROUTING_CONTEXT, value.trim(), scenario.get(ROUTING_CONTEXT))));
            }
        } else if (parent.contains(PROTOCOL_DATA)) {
            if (Objects.isNull(pojo.getProtocolData())) {
                pojo.setProtocolData(new ProtocolData());
            }
            Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getProtocolData().getClass());
            if (contain(value, PARAMETER_TAG)) {
                pojo.getProtocolData().setParameterTag(ParameterTag.of(Short.parseShort(getValue(PARAMETER_TAG, value.trim(), scenario.get(PARAMETER_TAG)))));
            } else if (contain(value, PARAMETER_LENGTH)) {
                pojo.getProtocolData().setParameterLength(Short.parseShort(getValue(PARAMETER_LENGTH, value.trim(), scenario.get(PARAMETER_LENGTH))));
            } else if (contain(value, OPC)) {
                pojo.getProtocolData().setOpc(parsePointCode(value.trim(), OPC));
            } else if (contain(value, DPC)) {
                pojo.getProtocolData().setDpc(parsePointCode(value, DPC));
            } else if (contain(value, SI)) {
                pojo.getProtocolData().setSi(Byte.parseByte(getValue(SI, value.trim(), scenario.get(SI))));
            } else if (contain(value, NI)) {
                pojo.getProtocolData().setNi(Byte.parseByte(getValue(NI, value.trim(), scenario.get(NI))));
            } else if (contain(value, MP)) {
                pojo.getProtocolData().setMp(Byte.parseByte(getValue(MP, value.trim(), scenario.get(MP))));
            } else if (contain(value, SLS)) {
                pojo.getProtocolData().setSls(Byte.parseByte(getValue(SLS, value.trim(), scenario.get(SLS))));
            }
        }
    }

    private int parsePointCode(String value, String key) {
        String result = getValue(key, value.trim(), "(\\d+-\\d+-\\d+)", "(\\d+)");
        String[] split = result.split("-");
        if (split.length == 1) {
            return Integer.parseInt(result);
        }
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put((byte) 0);
        Stream.of(split).map(String::trim).forEach(val -> buffer.put((byte) Integer.parseInt(val)));
        buffer.flip();
        return buffer.getInt();
    }
}
