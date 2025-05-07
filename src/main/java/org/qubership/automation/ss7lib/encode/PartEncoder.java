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
import java.util.List;

public interface PartEncoder<T> {

    /**
     * Encode messagePart into a new ByteBuffer object.
     *
     * @param messagePart object to be encoded
     * @return a new ByteBuffer with encoding result.
     */
    ByteBuffer encode(T messagePart);

    /**
     * Encode messagePart into a new List<Byte> object.
     *
     * @param messagePart object to be encoded
     * @return a new List<Byte> with encoding result.
     */
    List<Byte> encodeToArray(T messagePart);
}
