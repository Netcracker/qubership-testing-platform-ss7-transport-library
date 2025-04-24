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

import org.qubership.automation.ss7lib.model.M3uaMessage;
import org.qubership.automation.ss7lib.model.sub.m3ua.NetworkAppearance;
import org.qubership.automation.ss7lib.model.sub.m3ua.ProtocolData;
import org.qubership.automation.ss7lib.model.sub.m3ua.RoutingContext;
import org.qubership.automation.ss7lib.model.type.MessageClass;
import org.qubership.automation.ss7lib.model.type.MessageType;
import org.qubership.automation.ss7lib.model.type.ParameterTag;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class M3UAEncoderTest {

    @Test
    public void testEncode() {
        M3uaMessage pojo = new M3uaMessage();
        pojo.setVersion((byte) 1);
        pojo.setReserved((byte) 0);
        pojo.setMessageClass(MessageClass.TRANSFER_MESSAGE);
        pojo.setMessageType(MessageType.PAYLOAD);
        pojo.setMessageLength(108);
        NetworkAppearance networkAppearancePojo = new NetworkAppearance();
        networkAppearancePojo.setParameterTag(ParameterTag.NETWORK);
        networkAppearancePojo.setParameterLength((short) 8);
        networkAppearancePojo.setNetworkAppearance(8);
        pojo.setNetworkAppearance(networkAppearancePojo);
        RoutingContext routingContextPojo = new RoutingContext();
        routingContextPojo.setParameterTag(ParameterTag.ROUTING);
        routingContextPojo.setParameterLength((short) 8);
        routingContextPojo.setRoutingContext(162);
        pojo.setRoutingContext(routingContextPojo);
        ProtocolData protocolDataPojo = new ProtocolData();
        protocolDataPojo.setParameterTag(ParameterTag.DATA);
        protocolDataPojo.setParameterLength((short) 83);
        protocolDataPojo.setOpc(96274);
        protocolDataPojo.setDpc(96418);
        protocolDataPojo.setSi((byte) 3);
        protocolDataPojo.setNi((byte) 2);
        protocolDataPojo.setMp((byte) 0);
        protocolDataPojo.setSls((byte) 21);
        pojo.setProtocolData(protocolDataPojo);


        byte[] m3ua = new M3UAEncoder().encode(pojo);
        byte[] correctMessage =    {0x01, 0x00, 0x01, 0x01, 0x00, 0x00, 0x00, 0x6c, 0x02, 0x00, 0x00, 0x08, 0x00, 0x00,
                                    0x00, 0x08, 0x00, 0x06, 0x00, 0x08, 0x00, 0x00, 0x00, (byte) 0xa2, 0x02, 0x10, 0x00, 0x53,
                                    0x00, 0x01, 0x78, 0x12, 0x00, 0x01, 0x78, (byte) 0xa2, 0x03, 0x02, 0x00, 0x15};

        assertArrayEquals(correctMessage, m3ua);
    }

}
