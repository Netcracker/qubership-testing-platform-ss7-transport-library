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

import com.google.common.collect.Lists;
import com.google.common.primitives.Bytes;
import org.qubership.automation.ss7lib.model.AbstractMessage;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AbstractEncoderTest {

    private AbstractEncoder encoder = new AbstractEncoder(){
        @Override
        public byte[] encode(@Nonnull AbstractMessage pojo) {
            return new byte[0];
        }

        @Override
        public byte[] convertListToArray(@Nonnull List list) {
            return super.convertListToArray(list);
        }

        @Override
        public byte convertStringBinaryToByte(String binaryString) {
            return super.convertStringBinaryToByte(binaryString);
        }

        @Override
        public byte[] intToBytes(int number) {
            return super.intToBytes(number);
        }

        @Override
        protected byte[] shortToBytes(short number) {
            return super.shortToBytes(number);
        }

        @Override
        protected byte[] byteToBytes(byte number) {
            return super.byteToBytes(number);
        }
    };

    @Test
    public void testConvertListToArray() {
        ArrayList<Byte> list = Lists.newArrayList();
        byte[] bytes = {1, 0, 1, 2, 1};
        list.addAll(Bytes.asList(bytes));
        byte[] listToArray = encoder.convertListToArray(list);
        for (int i = 0; i<bytes.length;i++){
            assertEquals(bytes[i], listToArray[i]);
        }
    }


    @Test
    public void testConvertStringBinaryToByte() {
        String s = "10000001";
        byte aByte = encoder.convertStringBinaryToByte(s);
        assertEquals((byte)129, aByte);
    }

    @Test
    public void testIntToBytes() {
        byte[] bytes = encoder.intToBytes(333);
        assertArrayEquals(new byte[]{0,0,1,77}, bytes);
    }

    @Test
    public void testShortToBytes() {
        byte[] bytes = encoder.shortToBytes((short) 333);
        assertArrayEquals(new byte[]{1,77}, bytes);
    }

    @Test
    public void testByteToBytes() {
        byte[] bytes = encoder.byteToBytes((byte) 129);
        assertArrayEquals(new byte[]{(byte) 129}, bytes);
    }
}
