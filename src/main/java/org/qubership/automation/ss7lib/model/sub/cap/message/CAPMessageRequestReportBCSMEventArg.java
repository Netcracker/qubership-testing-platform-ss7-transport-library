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

import java.util.LinkedList;

import org.qubership.automation.ss7lib.model.type.EventType;
import org.qubership.automation.ss7lib.model.type.MonitorMode;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

public class CAPMessageRequestReportBCSMEventArg extends CAPMessagePojo {

    /**
     * Start Flag byte; always = (byte) 0xa0.
     */
    private final transient byte startFlag = (byte) 0xa0;

    /**
     * Message Length byte.
     */
    @Setter
    @Getter
    private byte length;

    /**
     * LinkedList of BSCMEvent elements.
     */
    @Getter
    private final LinkedList<BSCMEvent> bscmEventList = Lists.newLinkedList();

    /**
     * Get byte flag value.
     *
     * @return byte startFlag value.
     */
    public byte getFlag() {
        return startFlag;
    }

    public static class BSCMEvent {

        /**
         * Start Flag byte; always = (byte) 0x30.
         */
        @Getter
        private final transient byte startFlag = (byte) 0x30;

        /**
         * Message Length byte.
         */
        @Setter
        @Getter
        private transient byte length;

        /**
         * After Length Flag byte.
         */
        @Getter
        private final transient byte afterLengthFlag = (byte) 0x80;

        /**
         * Event Type value field.
         */
        @Setter
        @Getter
        private EventType eventType;

        /**
         * Monitor mode value field.
         */
        @Setter
        @Getter
        private MonitorMode monitorMode;

        /**
         * LegID value field.
         */
        @Setter
        @Getter
        private LegID legID;

        /**
         * Make and return String representation of this object.
         *
         * @return String representation of this object.
         */
        @Override
        public String toString() {
            return "BSCMEvent{"
                    + "startFlag=" + startFlag + ", length=" + length + ", afterLengthFlag=" + afterLengthFlag
                    + ", eventType=" + eventType + ", monitorMode=" + monitorMode + ", legID=" + legID + '}';
        }
    }
}
