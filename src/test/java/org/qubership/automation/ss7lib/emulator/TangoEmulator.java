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

package org.qubership.automation.ss7lib.emulator;

import org.qubership.automation.ss7lib.DumpReader;
import org.qubership.automation.ss7lib.decode.Utils;
import com.sun.nio.sctp.*;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

public class TangoEmulator {

    @Test
    public void testStartEmulator() throws IOException, URISyntaxException {
        InetSocketAddress address = new InetSocketAddress("localhost", 2905);
        SctpChannel channel = SctpChannel.open();
        channel.bind(address);
        InetSocketAddress outAddress = new InetSocketAddress("localhost", 2906);
        channel.connect(outAddress);
        ByteBuffer buffer = ByteBuffer.allocate(50);
        buffer.put(3, (byte) 0x2).put(4, (byte) 0x3);
        MessageInfo messageInfo = MessageInfo.createOutgoing(outAddress, 0);
        channel.send(buffer, messageInfo);
        ByteBuffer trace = DumpReader.getHexTrace("/test_data/requestReportBCSVMEvent.hexdump");
        while (!Thread.interrupted()) {
            ByteBuffer response = ByteBuffer.allocate(512);
            channel.receive(response, null, null);
            response.flip();
            Utils.logTrace("Incoming data: {}", getClass(),
                    Utils.subBuffer(response.limit(), response).array());
            channel.send(trace, messageInfo);
        }
    }
}
