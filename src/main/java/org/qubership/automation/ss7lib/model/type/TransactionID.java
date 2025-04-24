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

public enum TransactionID {
    SOURCE((byte) 0x48), DESTINATION((byte) 0x49);

    private final byte id;


    TransactionID(byte id) {
        this.id = id;
    }

    public static TransactionID of(byte type) {
        TransactionID txType = getTx(type);
        if (txType != null) {
            return txType;
        }
        throw new IllegalArgumentException("Unknown transaction type with id: " + type
                + ". Expected values: " + Arrays.toString(values()));
    }

    public static boolean isTx(byte type) {
        return getTx(type) != null;
    }

    private static TransactionID getTx(byte type) {
        for (TransactionID txType : values()) {
            if (txType.getCode() == type) {
                return txType;
            }
        }
        return null;
    }


    public byte getCode() {
        return id;
    }

    @Override
    public String toString() {
        return name() + '[' + id + ']';
    }
}
