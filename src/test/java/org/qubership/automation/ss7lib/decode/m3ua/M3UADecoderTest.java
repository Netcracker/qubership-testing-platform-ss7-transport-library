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

import org.qubership.automation.ss7lib.DumpReader;
import org.qubership.automation.ss7lib.model.M3uaMessage;
import org.qubership.automation.ss7lib.model.sub.m3ua.NetworkAppearance;
import org.qubership.automation.ss7lib.model.sub.m3ua.ProtocolData;
import org.qubership.automation.ss7lib.model.sub.m3ua.RoutingContext;
import org.qubership.automation.ss7lib.model.type.MessageClass;
import org.qubership.automation.ss7lib.model.type.MessageType;
import org.qubership.automation.ss7lib.model.type.ParameterTag;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public class M3UADecoderTest {

    private final DumpReader reader = new DumpReader();
    private ByteBuffer trace;

    @Before
    public void setUp() throws Exception {
        trace = DumpReader.getHexTrace("/test_data/InitialDp.hexdump");
    }

    @Test
    public void testDecodeM3UaHasFields() {
        M3uaDecoder decoder = new M3uaDecoder();
        M3uaMessage m3ua = decoder.decode(trace);
        assertEquals(1, m3ua.getVersion());
        assertEquals(0, m3ua.getReserved());
        assertEquals(MessageClass.TRANSFER_MESSAGE, m3ua.getMessageClass());
        assertEquals(MessageType.PAYLOAD, m3ua.getMessageType());
        assertEquals(240, m3ua.getMessageLength());
        NetworkAppearance networkAppearance = m3ua.getNetworkAppearance();
        assertEquals(ParameterTag.NETWORK, networkAppearance.getParameterTag());
        assertEquals(8, networkAppearance.getParameterLength());
        assertEquals(8, networkAppearance.getParameterLength());
        assertEquals(8, networkAppearance.getNetworkAppearance());
        ProtocolData protocolData = m3ua.getProtocolData();
        assertEquals(ParameterTag.DATA, protocolData.getParameterTag());
        assertEquals(223, protocolData.getParameterLength());
        assertEquals(96274, protocolData.getOpc());
        assertEquals(96404, protocolData.getDpc());
        assertEquals(3, protocolData.getSi());
        assertEquals(2, protocolData.getNi());
        assertEquals(0, protocolData.getMp());
        assertEquals(19, protocolData.getSls());
    }

    @Test
    public void testDecodeM3UaHasFieldsWithRoutingContext() throws IOException, URISyntaxException {
        trace = DumpReader.getHexTrace("/test_data/requestReportBCSVMEvent.hexdump");
        M3uaDecoder decoder = new M3uaDecoder();
        M3uaMessage m3ua = decoder.decode(trace);
        RoutingContext routingContext = m3ua.getRoutingContext();

        assertEquals(ParameterTag.ROUTING, routingContext.getParameterTag());
        assertEquals(8, routingContext.getParameterLength());
        assertEquals(96274, routingContext.getRoutingContext());

        ProtocolData protocolData = m3ua.getProtocolData();
        assertEquals(ParameterTag.DATA, protocolData.getParameterTag());
        assertEquals(96274, protocolData.getDpc());
        assertEquals(5, protocolData.getSls());
    }
}
