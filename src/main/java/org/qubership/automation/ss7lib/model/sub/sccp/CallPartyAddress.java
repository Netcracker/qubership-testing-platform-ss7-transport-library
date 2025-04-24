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

package org.qubership.automation.ss7lib.model.sub.sccp;

import org.qubership.automation.ss7lib.model.type.SubSystemNumber;

public class CallPartyAddress {
    private AddressIndicator addressIndicator;
    private SubSystemNumber subSystemNumber;
    private GlobalTitle globalTitle;
    private byte length;


    public AddressIndicator getAddressIndicator() {
        return addressIndicator;
    }

    public void setAddressIndicator(AddressIndicator addressIndicator) {
        this.addressIndicator = addressIndicator;
    }

    public SubSystemNumber getSubSystemNumber() {
        return subSystemNumber;
    }

    public void setSubSystemNumber(SubSystemNumber subSystemNumber) {
        this.subSystemNumber = subSystemNumber;
    }

    public GlobalTitle getGlobalTitle() {
        return globalTitle;
    }

    public void setGlobalTitle(GlobalTitle globalTitle) {
        this.globalTitle = globalTitle;
    }

    public byte getLength() {
        return length;
    }

    public void setLength(byte length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "CallPartyAddress{"
                + "addressIndicator='" + addressIndicator + '\''
                + ", subSystemNumber=" + subSystemNumber
                + ", globalTitle=" + globalTitle
                + ", length=" + length + '}';
    }
}
