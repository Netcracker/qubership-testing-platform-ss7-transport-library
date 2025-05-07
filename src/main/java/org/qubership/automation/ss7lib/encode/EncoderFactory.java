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

public final class EncoderFactory {

    /**
     * Map of encoders.
     */
    private static final Map<Class<? extends AbstractMessage>, Encoder> ENCODER_MAP = Maps.newHashMap();

    static {
        ENCODER_MAP.put(M3uaMessage.class, new M3UAEncoder());
        ENCODER_MAP.put(SccpMessage.class, new SCCPEncoder());
        ENCODER_MAP.put(TcapMessage.class, new TCAPEncoder());
        ENCODER_MAP.put(CapMessage.class, new CAPEncoder());
    }

    private EncoderFactory() {
    }

    /**
     * Get encoder for Class (extending AbstractMessage).
     *
     * @param clazz - Class of encoder to search
     * @param <T> - subclass of Encoder to be returned
     * @return Object of Encoder's subclass.
     */
    public static <T extends Encoder> T getEncoder(final Class<? extends AbstractMessage> clazz) {
        Encoder encoder = ENCODER_MAP.get(clazz);
        if (Objects.isNull(encoder)) {
            throw new IllegalArgumentException("Encoder for " + clazz.getName() + " is not found");
        }
        return (T) encoder;
    }

    /**
     * Encode message into byte[] via the corresponding Encoder.
     *
     * @param message - object of T (subclass of AbstractMessage) to be encoded
     * @param <T> - subclass of AbstractMessage
     * @return byte[] result of encoding.
     */
    public static <T extends AbstractMessage> byte[] encode(final T message) {
        return getEncoder(message.getClass()).encode(message);
    }

}
