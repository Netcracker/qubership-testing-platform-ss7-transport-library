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

package org.qubership.automation.ss7lib.model.type;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TCAPTypeTest {

    /**
     * Test of getting of BEGIN TCAPType code.
     */
    @Test
    public void testGetCodeByBegin() {
        byte code = TCAPType.BEGIN.getCode();
        assertEquals(98, code);
    }

    /**
     * Test of getting of CONTINUE TCAPType code.
     */
    @Test
    public void testGetCodeByContinue() {
        byte code = TCAPType.CONTINUE.getCode();
        assertEquals(101, code);
    }

    /**
     * Test of getting of END TCAPType code.
     */
    @Test
    public void testGetCodeByEnd() {
        byte code = TCAPType.END.getCode();
        assertEquals(100, code);
    }
}
