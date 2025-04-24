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

public enum EventType implements CommonEnum {
    O_NO_ANSWER((byte) 6) {
        @Override
        public byte[] getFlags() {
            return new byte[0];
        }
    },
    O_ANSWER((byte) 7) {
        @Override
        public byte[] getFlags() {
            throw new UnsupportedOperationException();
        }
    },
    O_DISCONNECT((byte) 9) {
        @Override
        public byte[] getFlags() {
            return new byte[]{(byte) 0xa2, (byte) 0x06, (byte) 0xa7,};
        }
    },

    O_ROUTE_SELECT_FAILURE((byte) 4) {
        @Override
        public byte[] getFlags() {
            throw new UnsupportedOperationException("Not implemented yet");
        }
    },

    O_CALL_PARTY_BUSY((byte) 5) {
        @Override
        public byte[] getFlags() {
            throw new UnsupportedOperationException("Not implemented yet");
        }
    },

    O_ABANDON((byte) 10) {
        @Override
        public byte[] getFlags() {
            throw new UnsupportedOperationException("Not implemented yet");
        }
    },

    T_TERMINATION_ATTEMPT_AUTH((byte) 12) {
        @Override
        public byte[] getFlags() {
            throw new UnsupportedOperationException("Not implemented yet");
        }
    },

    T_CALLED_PARTY_BUSY((byte) 13) {
        @Override
        public byte[] getFlags() {
            return new byte[]{(byte) 0xa2, (byte) 0x09, (byte) 0xa8};
        }
    },

    T_NO_ANSWER((byte) 14) {
        @Override
        public byte[] getFlags() {
            throw new UnsupportedOperationException();
        }
    },

    T_ANSWER((byte) 15) {
        @Override
        public byte[] getFlags() {
            throw new UnsupportedOperationException();
        }
    },

    T_MID_CALL((byte) 16) {
        @Override
        public byte[] getFlags() {
            throw new UnsupportedOperationException();
        }
    },

    T_DISCONNECT((byte) 17) {
        @Override
        public byte[] getFlags() {
            return new byte[]{(byte) 0xa2, (byte) 0x06, (byte) 0xac};
        }
    },

    T_ABANDON((byte) 18) {
        @Override
        public byte[] getFlags() {
            throw new UnsupportedOperationException("Not implemented yet");
        }
    };

    private final byte id;

    EventType(byte id) {
        this.id = id;
    }

    public byte getId() {
        return id;
    }

    public byte getLength() {
        return (byte) 0x01;
    }

    public abstract byte[] getFlags();

    @Override
    public String toString() {
        return asPrintable();
    }

}
