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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventRequestBcsmDecoder implements CapDecoder<CAPMessageRequestReportBCSMEventArg> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventRequestBcsmDecoder.class);

    @Override
    public CAPMessageRequestReportBCSMEventArg decode(ByteBuffer buffer) {
        if (!buffer.hasRemaining()) {
            return null;
        }
        CAPMessageRequestReportBCSMEventArg eventArg = new CAPMessageRequestReportBCSMEventArg();
        byte flag = buffer.get();
        LOGGER.debug("Event bcsm flag: {}", flag);
        byte messageLength = buffer.get();
        eventArg.setMessageLength(messageLength);
        LOGGER.debug("Total length bcsm : {}", messageLength);
        byte secondFlag = buffer.get();
        byte length = buffer.get();
        LOGGER.debug("Message length bcsm : {}", length);
        eventArg.setLength(length);
        parseEvents(eventArg, buffer);
        return eventArg;
    }

    private void parseEvents(CAPMessageRequestReportBCSMEventArg eventArg, ByteBuffer buffer) {
        byte[] dst = new byte[eventArg.getLength()];
        buffer.get(dst);
        ByteBuffer eventsBuffer = ByteBuffer.wrap(dst);
        while (eventsBuffer.hasRemaining()) {
            CAPMessageRequestReportBCSMEventArg.BSCMEvent event = new CAPMessageRequestReportBCSMEventArg.BSCMEvent();
            byte eventFlag = eventsBuffer.get();
            byte eventLength = eventsBuffer.get();
            byte[] eventData = new byte[eventLength];
            LOGGER.debug("Event flag/length : {}/{}", eventFlag, eventLength);
            eventsBuffer.get(eventData);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Event data: {}", Arrays.toString(eventData));
            }
            ByteBuffer wrap = ByteBuffer.wrap(eventData);
            byte id = getEventId(wrap);
            event.setEventType(EnumProvider.of(id, EventType.class));
            id = getEventId(wrap);
            event.setMonitorMode(EnumProvider.of(id, MonitorMode.class));
            event.setLength(eventLength);
            event.setLegID(parseLegId(wrap));
            eventArg.getBscmEventList().add(event);
            LOGGER.info("Found event: {}", event);
        }
    }

    private LegID parseLegId(ByteBuffer buffer) {
        LegID legID = new LegID();
        byte flag = buffer.get();
        byte totalLength = buffer.get();
        byte magicFlag = buffer.get();
        byte legLength = buffer.get();
        legID.setId(buffer.get());
        return legID;
    }

    private byte getEventId(ByteBuffer wrap) {
        byte flag = wrap.get();
        byte length = wrap.get();
        return wrap.get() /*EventType*/;
    }
}
