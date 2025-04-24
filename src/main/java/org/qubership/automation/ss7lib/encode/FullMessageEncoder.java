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

package org.qubership.automation.ss7lib.encode;

import java.nio.ByteBuffer;

import org.qubership.automation.ss7lib.model.FullMessage;
import org.qubership.automation.ss7lib.model.M3uaMessage;

public class FullMessageEncoder {

    public static ByteBuffer encode(FullMessage message) {
        byte[] tcapData = EncoderFactory.encode(message.getTcap());
        byte[] sccpData = EncoderFactory.encode(message.getSccp());
        M3uaMessage m3ua = prepareM3ua(message, tcapData, sccpData);
        byte[] m3uaData = EncoderFactory.encode(m3ua);
        ByteBuffer result = ByteBuffer.allocate(tcapData.length + m3uaData.length + sccpData.length);
        result.put(m3uaData).put(sccpData).put(tcapData).flip();
        return result;
    }

    private static M3uaMessage prepareM3ua(FullMessage message, byte[] tcapData, byte[] sccpData) {
        M3uaMessage m3ua = message.getM3ua();
        m3ua.getProtocolData().setParameterLength((short) (16 + sccpData.length + tcapData.length));
        return m3ua;
    }

}
