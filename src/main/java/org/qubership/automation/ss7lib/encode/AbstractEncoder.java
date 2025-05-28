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

    /**
     * Create and fill byte[] from List of Bytes.
     *
     * @param bytes - List of Bytes to process
     * @return byte[] created and filled from List of bytes.
     */
    protected byte[] convertListToArray(@Nonnull final List<Byte> bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(bytes.size());
        bytes.forEach(buffer::put);
        return buffer.array();
    }

    /**
     * Parse String representation of binary integer, then get byte from it.
     *
     * @param binaryString - String representation of binary integer
     * @return byte cast from integer parsed from binaryString.
     */
    protected byte convertStringBinaryToByte(final String binaryString) {
        return (byte) Integer.parseInt(binaryString, 2);
    }

    /**
     * Create and fill byte[] from int number.
     *
     * @param number - int number to process
     * @return byte[] created and filled from int number.
     */
    protected byte[] intToBytes(final int number) {
        return ByteBuffer.allocate(4).putInt(number).array();
    }

    /**
     * Create and fill byte[] from short number.
     *
     * @param number - short number to process
     * @return byte[] created and filled from short number.
     */
    protected byte[] shortToBytes(final short number) {
        return ByteBuffer.allocate(2).putShort(number).array();
    }

    /**
     * Create and fill byte[] from byte number.
     *
     * @param number - byte number to process
     * @return byte[] created and filled from byte number.
     */
    protected byte[] byteToBytes(final byte number) {
        return ByteBuffer.allocate(1).put(number).array();
    }

    /**
     * Create and fill List of Bytes from byte[].
     *
     * @param bytes - byte[] to process
     * @return List of Bytes created and filled from byte[] bytes.
     */
    protected List<Byte> asList(final byte[] bytes) {
        return Bytes.asList(bytes);
    }

    /**
     * Validate length parameter; in some conditions add (byte) 0x81 to bytes array.
     *
     * @param bytes ArrayList of Bytes to add extra byte in case incorrect length
     * @param length byte length value to validate.
     */
    protected void validateLengthMessage(final ArrayList<Byte> bytes, final byte length) {
        if (length > 0x79 || length < 0) {
            bytes.add((byte) 0x81);
        }
    }
}

