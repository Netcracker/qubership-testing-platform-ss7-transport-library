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

package org.qubership.automation.ss7lib.connection;

import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.M3UA_ASPSM_ASPUP;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.M3UA_ASPSM_BEAT;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.M3UA_ASPSM_MSG;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.M3UA_ASPTM_ASPAC;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.M3UA_ASPTM_MSG;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.M3UA_MGMT_MSG;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.M3UA_SSNM_DAUD;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.M3UA_SSNM_MSG;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionProcessor.class);
    private static final ByteBuffer M3UA_ACK_MESSAGE = ByteBuffer.wrap(new byte[]{
            0x01, 0x00, 0x02, 0x02, /*version, reserved, msgClass, msgType*/
            0x00, 0x00, 0x00, 0x18, /*length*/
            0x02, 0x00, /*Parameter Tag: Network appearance (512)*/
            0x00, 0x08, /*length*/ 0x00, 0x00, 0x00, 0x08, /*Network appearance: 0*/
            0x00, 0x12, /*Parameter Tag: Affected point code (18)*/
            0x00, 0x08, /*Parameter length: 8*/ 0x00, /*Mask: 0*/
            0x00, 0x03, 0x20 /*Affected point code: (800)*/});

    public void prepareConnection(final ByteBuffer data) {
        LOGGER.info("Buf limit {} left={} pos={}", data.limit(), data.remaining(), data.position());
        byte version = data.get();  // position 0
        data.get();                 // pass reserved value; position 1
        byte msgClass = data.get(); // data[M3UA_MSG_CLASS_IDX]; position 2
        byte msgType = data.get();  // data[M3UA_MSG_TYPE_IDX]; position 3
        int msgLen = data.getInt(); // data[M3UA_LENGTH_IDX]; position 4

        switch (msgClass) {
            case M3UA_SSNM_MSG:
                LOGGER.info("M3UA SSNM is received, type={}", msgType);
                if (msgType == M3UA_SSNM_DAUD) {
                    // we shall send DAVA
                    ConnectionHolder.getInstance().send(M3UA_ACK_MESSAGE);
                    ConnectionHolder.getInstance().setReady(true);
                }
                break;
            case M3UA_MGMT_MSG:
                LOGGER.info("M3UA MGMT is received, type={}", msgType);
                if (msgType == M3UA_ASPSM_ASPUP) {
                    short tag = data.getShort();
                    short length = data.getShort();
                    short type = data.getShort();
                    short info = data.getShort();
                    if (info == 3) {
                        // we shall send UP Ack back
                        ConnectionHolder.getInstance().send(M3UA_ACK_MESSAGE);
                    }
                }
                break;
            case M3UA_ASPSM_MSG:
                LOGGER.info("M3UA ASPSM is received, type={}", msgType);
                if (msgType == M3UA_ASPSM_ASPUP) {
                    // we shall send UP Ack back
                    ByteBuffer message = ByteBuffer.wrap(new byte[]{0x01, 0x00, 0x03, 0x04, 0x00, 0x00, 0x00, 0x08});
                    ConnectionHolder.getInstance().send(message);
                }
                if (msgType == M3UA_ASPSM_BEAT) {
                    LOGGER.info("BEAT is received");
                    // we shall send UP Ack back
                }
                break;
            case M3UA_ASPTM_MSG:
                LOGGER.info("M3UA ASPTM is received, type={}", msgType);
                if (msgType == M3UA_ASPTM_ASPAC) {
                    ByteBuffer message = ByteBuffer.wrap(new byte[]{0x01, 0x00, 0x04, 0x03, 0x00, 0x00, 0x00, 0x08});
                    ConnectionHolder.getInstance().send(message);
                }
                break;
            default:
                LOGGER.info("Unknown msg is received (class={}, type={})", msgClass, msgType);
        }
    }

}
