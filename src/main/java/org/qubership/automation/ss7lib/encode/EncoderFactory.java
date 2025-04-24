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

import java.util.Map;
import java.util.Objects;

import org.qubership.automation.ss7lib.encode.cap.CAPEncoder;
import org.qubership.automation.ss7lib.encode.m3ua.M3UAEncoder;
import org.qubership.automation.ss7lib.encode.sccp.SCCPEncoder;
import org.qubership.automation.ss7lib.encode.tcap.TCAPEncoder;
import org.qubership.automation.ss7lib.model.AbstractMessage;
import org.qubership.automation.ss7lib.model.CapMessage;
import org.qubership.automation.ss7lib.model.M3uaMessage;
import org.qubership.automation.ss7lib.model.SccpMessage;
import org.qubership.automation.ss7lib.model.TcapMessage;

import com.google.common.collect.Maps;

public class EncoderFactory {


    private static final Map<Class<? extends AbstractMessage>, Encoder> map = Maps.newHashMap();


    static {
        map.put(M3uaMessage.class, new M3UAEncoder());
        map.put(SccpMessage.class, new SCCPEncoder());
        map.put(TcapMessage.class, new TCAPEncoder());
        map.put(CapMessage.class, new CAPEncoder());
    }

    private EncoderFactory() {
    }


    public static <T extends Encoder> T getEncoder(Class<? extends AbstractMessage> clazz) {
        Encoder encoder = map.get(clazz);
        if (Objects.isNull(encoder)) {
            throw new IllegalArgumentException("Encoder for " + clazz.getName() + " is not found");
        }
        return (T) encoder;
    }

    public static <T extends AbstractMessage> byte[] encode(T message) {
        return getEncoder(message.getClass()).encode(message);
    }

}
