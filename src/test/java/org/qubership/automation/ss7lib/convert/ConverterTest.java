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

package org.qubership.automation.ss7lib.convert;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConverterTest {
    @Test
    public void testConvertValueToHex() {
        String actual = Converter.bytesToHex(new byte[]{0x44, 0x00, 0x10, 0x01});
        assertEquals("44001001", actual);
    }

    @Test
    public void testDivideByteOnBits() {
        Converter.DividedByte dividedByte = Converter.divideByteOnTwo((byte) 0x86);
        assertEquals(0x8, dividedByte.getLeft());
        assertEquals(0x6, dividedByte.getRight());
    }
}
