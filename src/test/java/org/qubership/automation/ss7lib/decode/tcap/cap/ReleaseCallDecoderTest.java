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

import org.qubership.automation.ss7lib.DumpReader;
import org.qubership.automation.ss7lib.model.sub.cap.message.CapMessageReleaseCallArg;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public class ReleaseCallDecoderTest {
    @Test
    public void testDecodeReleaseCall() {
        ByteBuffer trace = DumpReader.getTraceFromString(16, "\\s", "04 02 83 9f");
        CapMessageReleaseCallArg message = CapDecoderFactory.decode(ReleaseCallDecoder.class, trace);
        assertEquals("839f", message.getCallSegments().getStringBytes());
    }

    @Test
    public void testDecodeReleaseCall4Negative() {
        ByteBuffer trace = DumpReader.getTraceFromString(16, "\\s", "04 02 80 a9");
        CapMessageReleaseCallArg message = CapDecoderFactory.decode(ReleaseCallDecoder.class, trace);
        assertEquals("80a9", message.getCallSegments().getStringBytes());
    }

}
