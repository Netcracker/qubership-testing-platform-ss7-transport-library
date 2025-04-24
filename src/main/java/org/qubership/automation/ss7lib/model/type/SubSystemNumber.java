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

import java.util.Arrays;

public enum SubSystemNumber {
    CAP((byte) 146);

    private final byte typeId;

    SubSystemNumber(byte typeId) {
        this.typeId = typeId;
    }

    public byte getCode() {
        return typeId;
    }

    public static SubSystemNumber of(byte typeId) {
        for (SubSystemNumber subSystemNumber : values()) {
            if (subSystemNumber.getCode() == typeId) {
                return subSystemNumber;
            }
        }
        throw new IllegalArgumentException("Unknown subsystem number with id: " + typeId
                + ". Expected values: " + Arrays.toString(values()));
    }

    @Override
    public String toString() {
        return name() + '[' + this.typeId + ']';
    }
}
