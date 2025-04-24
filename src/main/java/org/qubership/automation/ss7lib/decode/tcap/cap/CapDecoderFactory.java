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
import java.util.Map;

import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessagePojo;

import com.google.common.collect.Maps;

public class CapDecoderFactory {
    private static final Map<Class<? extends CapDecoder>, CapDecoder> CAP_DECODER_MAP = Maps.newHashMap();

    public static <T extends CAPMessagePojo> T decode(Class<? extends CapDecoder> clazz, ByteBuffer buffer) {
        CapDecoder capDecoder = CAP_DECODER_MAP.computeIfAbsent(clazz, klass -> {
            try {
                return klass.asSubclass(CapDecoder.class).getConstructor().newInstance();
            } catch (Exception e) {
                throw new IllegalArgumentException("Can't create CapDecoder from class: " + klass.getSimpleName());
            }
        });
        return (T) capDecoder.decode(buffer);
    }
}
