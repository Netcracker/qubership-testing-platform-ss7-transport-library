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

package org.qubership.automation.ss7lib.decode;

import java.nio.ByteBuffer;

import org.qubership.automation.ss7lib.decode.m3ua.M3uaDecoder;
import org.qubership.automation.ss7lib.decode.sccp.SccpDecoder;
import org.qubership.automation.ss7lib.decode.tcap.TcapDecoder;
import org.qubership.automation.ss7lib.model.ErrorMessage;
import org.qubership.automation.ss7lib.model.FullMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class DecoderFactory {

    /**
     * Decoder of M3UA part of a message.
     */
    private static final M3uaDecoder M3UA_DECODER = new M3uaDecoder();

    /**
     * Decoder of SCCP part of a message.
     */
    private static final SccpDecoder SCCP_DECODER = new SccpDecoder();

    /**
     * Decoder of TCAP part of a message.
     */
    private static final TcapDecoder TCAP_DECODER = new TcapDecoder();

    /**
     * Decode ByteBuffer into FullMessage object.
     *
     * @param buffer - ByteBuffer to decode FullMessage from
     * @return FullMessage object created and filled.
     */
    public static FullMessage decode(final ByteBuffer buffer) {
        FullMessage message = new FullMessage();
        try {
            message.setM3ua(M3UA_DECODER.decode(buffer));
            message.setSccp(SCCP_DECODER.decode(buffer));
            message.setTcap(TCAP_DECODER.decode(buffer));
        } catch (Exception e) {
            log.error("Failed parsing data", e);
            message.setErrorMessage(new ErrorMessage(e));
        }
        return message;
    }
}
