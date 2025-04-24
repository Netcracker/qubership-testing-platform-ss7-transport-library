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

package org.qubership.automation.ss7lib.model;


import org.qubership.automation.ss7lib.model.sub.m3ua.NetworkAppearance;
import org.qubership.automation.ss7lib.model.sub.m3ua.ProtocolData;
import org.qubership.automation.ss7lib.model.sub.m3ua.RoutingContext;
import org.qubership.automation.ss7lib.model.type.MessageClass;
import org.qubership.automation.ss7lib.model.type.MessageType;
import org.qubership.automation.ss7lib.model.type.Standard;

public class M3uaMessage extends AbstractMessage {
    private MessageType messageType;
    private MessageClass messageClass;
    private NetworkAppearance networkAppearance;
    private RoutingContext routingContext;
    private ProtocolData protocolData;
    private byte version;
    private byte reserved;
    private Standard standard = Standard.ITU;

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageClass getMessageClass() {
        return messageClass;
    }

    public void setMessageClass(MessageClass messageClass) {
        this.messageClass = messageClass;
    }

    public NetworkAppearance getNetworkAppearance() {
        return networkAppearance;
    }

    public void setNetworkAppearance(NetworkAppearance networkAppearance) {
        this.networkAppearance = networkAppearance;
    }

    public RoutingContext getRoutingContext() {
        return routingContext;
    }

    public void setRoutingContext(RoutingContext routingContext) {
        this.routingContext = routingContext;
    }

    public ProtocolData getProtocolData() {
        return protocolData;
    }

    public void setProtocolData(ProtocolData protocolData) {
        this.protocolData = protocolData;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getReserved() {
        return reserved;
    }

    public void setReserved(byte reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "M3uaMessage{"
                + "messageType=" + messageType
                + ", messageClass=" + messageClass
                + ", networkAppearance=" + networkAppearance
                + ", routingContext=" + routingContext
                + ", protocolData=" + protocolData
                + ", version=" + version
                + ", reserved=" + reserved
                + '}';
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }
}
