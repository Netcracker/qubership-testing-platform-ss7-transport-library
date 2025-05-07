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

import org.qubership.automation.ss7lib.convert.Converter;
import org.qubership.automation.ss7lib.decode.Utils;
import org.qubership.automation.ss7lib.model.sub.cap.message.CapMessageReleaseCallArg;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReleaseCallDecoder implements CapDecoder<CapMessageReleaseCallArg> {

    /**
     * Parse CapMessageReleaseCallArg message from ByteBuffer.
     *
     * @param buffer ByteBuffer to parse
     * @return CapMessageReleaseCallArg parsed from the buffer.
     */
    @Override
    public CapMessageReleaseCallArg decode(final ByteBuffer buffer) {
        boolean isBufferHasRemaining = buffer.hasRemaining();
        byte flag = isBufferHasRemaining ? buffer.get() : 0x0;
        if (!isBufferHasRemaining || flag != 0x4) {
            log.warn("No release call data. Flag is: {}", Converter.bytesToHex(flag));
            return null;
        }
        byte releaseCallLength = buffer.get();
        log.info("Starting parsing Release Call");
        log.debug("Release call data length: {}", releaseCallLength);
        ByteBuffer byteBuffer = Utils.subBuffer(releaseCallLength, buffer);
        CapMessageReleaseCallArg callArg = new CapMessageReleaseCallArg();
        CapMessageReleaseCallArg.AllCallSegments callSegments = new CapMessageReleaseCallArg.AllCallSegments();
        callArg.setCallSegments(callSegments);
        callSegments.setMessageLength(releaseCallLength);
        callSegments.setStringBytes(Converter.bytesToHex(byteBuffer.array()));
        log.info("Release call parsing finished: {}", callArg);
        return callArg;
    }
}
