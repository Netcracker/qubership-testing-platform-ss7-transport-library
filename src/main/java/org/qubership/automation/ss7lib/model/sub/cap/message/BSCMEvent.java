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

package org.qubership.automation.ss7lib.model.sub.cap.message;

import org.qubership.automation.ss7lib.model.pojo.MiscCallInfo;
import org.qubership.automation.ss7lib.model.pojo.SpecificInformation;
import org.qubership.automation.ss7lib.model.type.EventType;
import org.qubership.automation.ss7lib.model.type.MonitorMode;

import lombok.Getter;
import lombok.Setter;

@Getter
public class BSCMEvent {

    /**
     * Start Flag byte, always = (byte) 0x30.
     */
    private final transient byte startFlag = (byte) 0x30;

    /**
     * Length byte.
     */
    @Setter
    private transient byte length;

    /**
     * After Length Flag byte, always = (byte) 0x80.
     */
    private final transient byte afterLengthFlag = (byte) 0x80;

    /**
     * Event Type value field.
     */
    @Setter
    private EventType eventType;

    /**
     * Monitor Mode value field.
     */
    @Setter
    private MonitorMode monitorMode;

    /**
     *LegID object field.
     */
    @Setter
    private LegID legID;

    /**
     * SpecificInformation object field.
     */
    @Setter
    private SpecificInformation specificInformation;

    /**
     * Flag if the Call is forwarded (true) or not.
     */
    @Setter
    private boolean isCallForwarded;

    /**
     * MiscCallInfo object field.
     */
    @Setter
    private MiscCallInfo miscCallInfo;

    /**
     * Make and return String representation of this object.
     *
     * @return String representation of this object.
     */
    @Override
    public String toString() {
        return "BSCMEvent{"
                + "startFlag=" + startFlag
                + ", length=" + length
                + ", afterLengthFlag=" + afterLengthFlag
                + ", eventType=" + eventType
                + ", monitorMode=" + monitorMode
                + ", legID=" + legID + '}';
    }

}
