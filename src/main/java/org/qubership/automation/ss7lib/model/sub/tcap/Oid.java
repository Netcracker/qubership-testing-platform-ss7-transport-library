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

package org.qubership.automation.ss7lib.model.sub.tcap;

public class Oid {

    private final transient  byte firstFlag = 0x6b;
    private final transient  byte secondFlag = 0x28;
    private final transient  byte thirdFlag = 0x06;
    private String oid;

    public byte getFirstFlag() {
        return firstFlag;
    }

    public byte getSecondFlag() {
        return secondFlag;
    }

    public byte getThirdFlag() {
        return thirdFlag;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @Override
    public String toString() {
        return "Oid{" + "firstFlag=" + firstFlag
                + ", secondFlag=" + secondFlag + ", thirdFlag=" + thirdFlag + ", oid='" + oid + '\'' + '}';
    }
}
