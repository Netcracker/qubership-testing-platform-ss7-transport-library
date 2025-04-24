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

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.qubership.automation.ss7lib.model.AbstractMessage;

import com.google.common.primitives.Bytes;

public abstract class AbstractEncoder<T extends AbstractMessage> implements Encoder {

    protected byte[] convertListToArray(@Nonnull List<Byte> bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(bytes.size());
        bytes.forEach(buffer::put);
        return buffer.array();
    }


    protected byte convertStringBinaryToByte(String binaryString) {
        return (byte) Integer.parseInt(binaryString, 2);
    }

    protected byte[] intToBytes(int number) {
        return ByteBuffer.allocate(4).putInt(number).array();
    }

    protected byte[] shortToBytes(short number) {
        return ByteBuffer.allocate(2).putShort(number).array();
    }

    protected byte[] byteToBytes(byte number) {
        return ByteBuffer.allocate(1).put(number).array();
    }

    protected List<Byte> asList(byte[] bytes) {
        return Bytes.asList(bytes);
    }

    protected void validateLengthMessage(ArrayList<Byte> bytes, byte length) {
        if (length > 0x79 || length < 0) {
            bytes.add((byte) 0x81);
        }
    }
}

