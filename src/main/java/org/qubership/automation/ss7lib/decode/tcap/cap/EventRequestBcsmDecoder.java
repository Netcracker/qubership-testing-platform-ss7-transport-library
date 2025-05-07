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

package org.qubership.automation.ss7lib.decode.tcap.cap;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageRequestReportBCSMEventArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.LegID;
import org.qubership.automation.ss7lib.model.type.EnumProvider;
import org.qubership.automation.ss7lib.model.type.EventType;
import org.qubership.automation.ss7lib.model.type.MonitorMode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventRequestBcsmDecoder implements CapDecoder<CAPMessageRequestReportBCSMEventArg> {

    /**
     * Parse CAPMessageRequestReportBCSMEventArg message from ByteBuffer.
     *
     * @param buffer ByteBuffer to parse
     * @return CAPMessageRequestReportBCSMEventArg parsed from the buffer.
     */
    @Override
    public CAPMessageRequestReportBCSMEventArg decode(final ByteBuffer buffer) {
        if (!buffer.hasRemaining()) {
            return null;
        }
        byte flag = buffer.get();
        log.debug("Event flag: {}", flag);
        byte messageLength = buffer.get();
        log.debug("Total message length: {}", messageLength);
        CAPMessageRequestReportBCSMEventArg eventArg = new CAPMessageRequestReportBCSMEventArg();
        eventArg.setMessageLength(messageLength);
        byte secondFlag = buffer.get();
        byte length = buffer.get();
        log.debug("Message length: {}", length);
        eventArg.setLength(length);
        parseEvents(eventArg, buffer);
        return eventArg;
    }

    private void parseEvents(final CAPMessageRequestReportBCSMEventArg eventArg, final ByteBuffer buffer) {
        byte[] dst = new byte[eventArg.getLength()];
        buffer.get(dst);
        ByteBuffer eventsBuffer = ByteBuffer.wrap(dst);
        while (eventsBuffer.hasRemaining()) {
            byte eventFlag = eventsBuffer.get();
            byte eventLength = eventsBuffer.get();
            byte[] eventData = new byte[eventLength];
            log.debug("Event flag/length : {}/{}", eventFlag, eventLength);
            eventsBuffer.get(eventData);
            log.debug("Event data: {}", Arrays.toString(eventData));
            ByteBuffer wrap = ByteBuffer.wrap(eventData);
            byte id = getEventId(wrap);
            CAPMessageRequestReportBCSMEventArg.BSCMEvent event = new CAPMessageRequestReportBCSMEventArg.BSCMEvent();
            event.setEventType(EnumProvider.of(id, EventType.class));
            id = getEventId(wrap);
            event.setMonitorMode(EnumProvider.of(id, MonitorMode.class));
            event.setLength(eventLength);
            event.setLegID(parseLegId(wrap));
            eventArg.getBscmEventList().add(event);
            log.info("Found event: {}", event);
        }
    }

    private LegID parseLegId(final ByteBuffer buffer) {
        byte flag = buffer.get();
        byte totalLength = buffer.get();
        byte magicFlag = buffer.get();
        byte legLength = buffer.get();
        LegID legID = new LegID();
        legID.setId(buffer.get());
        return legID;
    }

    private byte getEventId(final ByteBuffer wrap) {
        byte flag = wrap.get();
        byte length = wrap.get();
        return wrap.get();
    }
}
