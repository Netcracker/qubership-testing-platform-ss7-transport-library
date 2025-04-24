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

public enum TCAPType {
    UNKNOWN((byte) 0x0) {
        @Override
        public byte getFirstFlag() {
            return 0;
        }

        @Override
        public byte getSecondFlag() {
            return 0;
        }
    },
    UNIDIRECTIONAL((byte) 0x61) {
        @Override
        public byte getFirstFlag() {
            return 0;
        }

        @Override
        public byte getSecondFlag() {
            return 0;
        }
    },//97
    BEGIN((byte) 0x62) {
        @Override
        public byte getFirstFlag() {
            return 30;
        }

        @Override
        public byte getSecondFlag() {
            return 28;
        }
    },//98
    END((byte) 0x64) {
        @Override
        public byte getFirstFlag() {
            return 42;
        }

        @Override
        public byte getSecondFlag() {
            return 40;
        }
    },//100
    CONTINUE((byte) 0x65) {
        @Override
        public byte getFirstFlag() {
            return 42;
        }

        @Override
        public byte getSecondFlag() {
            return 40;
        }
    },//101
    ABORT((byte) 0x67) {
        @Override
        public byte getFirstFlag() {
            return 0;
        }

        @Override
        public byte getSecondFlag() {
            return 0;
        }
    };//103

    private final byte id;

    TCAPType(byte id) {
        this.id = id;
    }

    public static TCAPType of(byte id) {
        for (TCAPType tcapType : values()) {
            if (tcapType.getCode() == id) {
                return tcapType;
            }
        }
        throw new IllegalArgumentException("Unknown tcap type with id: " + id
                + ". Expected values: " + Arrays.toString(values()));
    }

    public byte getCode() {
        return id;
    }

    public abstract byte getFirstFlag();

    public abstract byte getSecondFlag();

}
