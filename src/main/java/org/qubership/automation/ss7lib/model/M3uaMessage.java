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

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class M3uaMessage extends AbstractMessage {

    /**
     * MessageType (enum) message field.
     */
    private MessageType messageType;

    /**
     * MessageClass technical info.
     */
    private MessageClass messageClass;

    /**
     * NetworkAppearance message part.
     */
    private NetworkAppearance networkAppearance;

    /**
     * RoutingContext message part.
     */
    private RoutingContext routingContext;

    /**
     * ProtocolData message part.
     */
    private ProtocolData protocolData;

    /**
     * Byte containing version number.
     */
    private byte version;

    /**
     * Byte containing 'reserved' flag.
     */
    private byte reserved;

    /**
     * Standard (enum) message field.
     */
    private Standard standard = Standard.ITU;

    /**
     * Make and return String representation of this object.
     *
     * @return String representation of this object.
     */
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

}
