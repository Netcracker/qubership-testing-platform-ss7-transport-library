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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageClassTest {

    /**
     * Test of MessageClass.MANAGEMENT encoding.
     */
    @Test
    public void testGetCodeByManagement() {
        byte byt = MessageClass.MANAGEMENT.getId();
        byte correctMessage = (byte) 0;
        assertEquals(correctMessage, byt);
    }

    /**
     * Test of MessageClass.TRANSFER_MESSAGE encoding.
     */
    @Test
    public void testGetCodeByTransfer() {
        byte byt = MessageClass.TRANSFER_MESSAGE.getId();
        byte correctMessage = (byte) 1;
        assertEquals(correctMessage, byt);
    }

    /**
     * Test of MessageClass decoding.
     */
    @Test
    public void testGetMessageClassFromByte() {
        MessageClass messageClass = EnumProvider.of((byte) 0x0, MessageClass.class);
        assertEquals(MessageClass.MANAGEMENT, messageClass);
    }

    /**
     * Test of behavior in case illegal arguments provided.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionIfIllegalArgProvided() {
        EnumProvider.of((byte) 0x2, MessageClass.class);
    }
}
