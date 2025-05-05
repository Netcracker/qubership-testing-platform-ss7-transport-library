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

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

import org.qubership.automation.ss7lib.convert.Converter;
import org.slf4j.LoggerFactory;

public class Utils {
    public static ByteBuffer subBuffer(final int length, final ByteBuffer buffer) {
        byte[] dst = new byte[length];
        buffer.get(dst);
        return ByteBuffer.wrap(dst);
    }

    public static ByteBuffer subBuffer(final ByteBuffer buffer) {
        byte[] dst = new byte[buffer.limit()];
        buffer.get(dst);
        return ByteBuffer.wrap(dst);
    }

    public static void logTrace(final String message, final List<Byte> list, final Class clazz) {
        StringBuilder builder = new StringBuilder();
        Iterator<Byte> iterator = list.iterator();
        int counter = 0;
        while (iterator.hasNext()) {
            Byte next = iterator.next();
            counter = buildMessage(builder, counter, next, iterator.hasNext());
            counter++;
        }
        logResult(message, clazz, builder);
    }

    public static void logTrace(final String message, final Class clazz, final byte... result) {
        StringBuilder builder = getMessageAsHex(result);
        logResult(message, clazz, builder);
    }

    private static StringBuilder getMessageAsHex(final byte[] result) {
        StringBuilder builder = new StringBuilder();
        int counter = 0;
        for (int index = 0; index < result.length; index++) {
            Byte next = result[index];
            counter = buildMessage(builder, counter, next, index < result.length - 1);
            counter++;
        }
        return builder;
    }

    public static String getAsHex(final byte[] result) {
        return getMessageAsHex(result).toString();
    }

    private static int buildMessage(final StringBuilder builder,
                                    final int counter,
                                    final Byte next,
                                    final boolean endCondition) {
        int localCounter = counter;
        if (counter == 8) {
            builder.append(' ');
        } else if (counter == 16) {
            localCounter = 0;
            builder.append('\n');
        }
        builder.append(Converter.bytesToHex(next));
        if (endCondition) {
            builder.append(' ');
        }
        return localCounter;
    }

    private static void logResult(final String message, final Class clazz, final StringBuilder builder) {
        LoggerFactory.getLogger(clazz).info(message, builder.toString());
    }

    public static ByteBuffer calculateSize(final ByteBuffer buffer) {
        byte[] array = buffer.array();
        ByteBuffer result = ByteBuffer.allocate(array.length); /*chunks*/
        boolean isSizeSet = false;
        for (int index = 0; index < array.length; index++) {
            if (index < 4 || index > 7) {
                result.put(array[index]);
                continue;
            }
            if (!isSizeSet) {
                isSizeSet = true;
                result.putInt(array.length);
            }
        }
        Utils.logTrace("Message to send:\n{}", Utils.class, result.array());
        result.flip();
        return result;
    }
}
