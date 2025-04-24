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

public class CAPMessageRequestReportBCSMEventArg extends CAPMessagePojo {
    private final transient byte startFlag = (byte) 0xa0;
    private byte length;

    private final LinkedList<BSCMEvent> bscmEventList = Lists.newLinkedList();


    public byte getFlag() {
        return startFlag;
    }

    public byte getLength() {
        return length;
    }

    public void setLength(byte length) {
        this.length = length;
    }

    public LinkedList<BSCMEvent> getBscmEventList() {
        return bscmEventList;
    }

    public static class BSCMEvent {
        private final transient byte startFlag = (byte) 0x30;
        private transient byte length;
        private final transient byte afterLengthFlag = (byte) 0x80;
        private EventType eventType;
        private MonitorMode monitorMode;
        private LegID legID;

        public byte getStartFlag() {
            return startFlag;
        }

        public byte getLength() {
            return length;
        }

        public void setLength(byte length) {
            this.length = length;
        }

        public byte getAfterLengthFlag() {
            return afterLengthFlag;
        }

        public EventType getEventType() {
            return eventType;
        }

        public void setEventType(EventType eventType) {
            this.eventType = eventType;
        }

        public MonitorMode getMonitorMode() {
            return monitorMode;
        }

        public void setMonitorMode(MonitorMode monitorMode) {
            this.monitorMode = monitorMode;
        }

        public LegID getLegID() {
            return legID;
        }

        public void setLegID(LegID legID) {
            this.legID = legID;
        }

        @Override
        public String toString() {
            return "BSCMEvent{"
                    + "startFlag=" + startFlag + ", length=" + length + ", afterLengthFlag=" + afterLengthFlag
                    + ", eventType=" + eventType + ", monitorMode=" + monitorMode + ", legID=" + legID + '}';
        }
    }
}
