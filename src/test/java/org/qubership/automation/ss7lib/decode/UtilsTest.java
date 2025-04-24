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

package org.qubership.automation.ss7lib.decode;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void testCalculateMessageLength() {
        byte[] bytes = new byte[]{1, 0, 0, 10, 1, 2, 3, 4, 2, 5, 1};
        ByteBuffer byteBuffer = Utils.calculateSize(ByteBuffer.wrap(bytes));
        byteBuffer.position(4);
        assertEquals(bytes.length, byteBuffer.getInt());
    }

    @Test
    public void testGet() {
        String val = "01 00 01 01 00 00 00 64 02 00 00 08 00 00 00 08\n" +
                "00 06 00 08 00 00 00 a2 02 10 00 db 00 00 03 20\n" +
                "00 00 01 7f 03 02 00 15 09 81 03 0c 15 09 8a 92\n" +
                "0a 51 41 24 04 35 01 09 8a 92 0a 51 41 24 04 15\n" +
                "04 22 65 20 48 04 f9 0f 01 08 49 04 89 00 10 01\n" +
                "6c 12 a1 10 02 01 01 02 01 18 30 08 80 01 07 a3\n" +
                "03 81 01 02";
        System.out.println(val.split("\\s+").length);
    }
}
