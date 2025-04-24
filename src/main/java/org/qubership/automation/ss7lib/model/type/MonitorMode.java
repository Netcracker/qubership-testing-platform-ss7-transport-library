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

public enum MonitorMode implements CommonEnum {
    INTERRUPTED((byte) 0), NOTIFY_AND_CONTINUE((byte) 1);

    private final byte id;

    MonitorMode(byte id) {
        this.id = id;
    }

    public byte[] getCode() {
        return new byte[]{(byte) 0x81, (byte) 0x01, id};
    }

    @Override
    public byte getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return asPrintable();
    }
}
