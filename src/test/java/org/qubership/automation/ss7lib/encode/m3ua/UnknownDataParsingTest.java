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

package org.qubership.automation.ss7lib.encode.m3ua;

import org.qubership.automation.ss7lib.DumpReader;
import org.qubership.automation.ss7lib.decode.m3ua.M3uaDecoder;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

public class UnknownDataParsingTest {
    @Test
    public void testParseUnknownDecData() throws IOException, URISyntaxException {
        ByteBuffer trace = DumpReader.getTrace("/test_data/unkown.data");
        M3uaDecoder decoder = new M3uaDecoder();
        decoder.decode(trace);

    }
}
