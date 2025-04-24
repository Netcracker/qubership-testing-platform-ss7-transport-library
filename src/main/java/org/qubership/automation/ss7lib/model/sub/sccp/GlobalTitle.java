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

public class GlobalTitle {
    private byte translationType;
    private String callPartyDigits;

    public byte getTranslationType() {
        return translationType;
    }

    public void setTranslationType(byte translationType) {
        this.translationType = translationType;
    }

    public String getCallPartyDigits() {
        return callPartyDigits;
    }

    public void setCallPartyDigits(String callPartyDigits) {
        this.callPartyDigits = callPartyDigits;
    }

    @Override
    public String toString() {
        return "GlobalTitle{"
                + "translationType=" + translationType
                + ", callPartyDigits=" + callPartyDigits + '}';
    }
}
