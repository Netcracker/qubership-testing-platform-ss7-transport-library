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

package org.qubership.automation.ss7lib;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class DumpReader {
    public static ByteBuffer getTrace(String tracePath) throws URISyntaxException, IOException {
        return _getTrace(tracePath, 10, ",");
    }

    public static ByteBuffer getHexTrace(String path) throws URISyntaxException, IOException {
        return _getTrace(path, 16, "(\\s)");
    }

    public static String getMessage(String path) throws URISyntaxException, IOException{
        List<String> strings = Files.readAllLines(Paths.get(DumpReader.class.getResource(path).toURI()));
        StringBuilder builder = new StringBuilder();
        for (String string :strings) {
            builder.append(string + "\n");
        }
        return builder.toString();
    }

    private static ByteBuffer _getTrace(String tracePath, int radix, String splitterator) throws IOException, URISyntaxException {
        byte[] array = Files.readAllBytes(Paths.get(DumpReader.class.getResource(tracePath).toURI()));
        String value = new String(array);
        return getTraceFromString(radix, splitterator, value);
    }

    public static ByteBuffer getTraceFromString(int radix, String splitterator, String value) {
        Object[] objects = Stream.of(value.split(splitterator))
                .filter(s -> !s.trim().isEmpty())
                .map(s -> (byte) Integer.parseInt(s.trim(), radix)).toArray();
        return getByteBuffer(objects);
    }

    private static ByteBuffer getByteBuffer(Object[] objects) {
        byte[] bytes = new byte[objects.length];
        for (int index = 0; index < objects.length; index++) {
            Byte aByte = (Byte) objects[index];
            bytes[index] = aByte;
        }
        return ByteBuffer.wrap(bytes);
    }

}
