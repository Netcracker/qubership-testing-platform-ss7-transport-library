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

package org.qubership.automation.ss7lib.parse.parser;

import static org.qubership.automation.ss7lib.parse.scenario.Pattern.BINARY_PATTERN;
import static org.qubership.automation.ss7lib.parse.scenario.Pattern.NUMERIC_VALUE;

import org.qubership.automation.ss7lib.encode.EncoderUtils;
import org.qubership.automation.ss7lib.model.sub.cap.message.InitialDetectionPoint;

public class BCDParser extends StringParser {
    private BcdNumber bcdNumber = new BcdNumber();

    void parseBCD(InitialDetectionPoint idp, String value) {
        if (value.contains("Extension")) {
            bcdNumber.setExtention(getValue("Extention", value, BINARY_PATTERN));
            return;
        }
        if (value.contains("Type of number")) {
            bcdNumber.setType(getValue("Type of Number", value, BINARY_PATTERN));
            return;
        }
        if (value.contains("Numbering plan")) {
            bcdNumber.setNumberPlan(getValue("Number plan", value, BINARY_PATTERN));
            return;
        }
        if (value.contains("Called Party BCD")) {
            bcdNumber.setNumber(getValue("Called Party BCD", value, NUMERIC_VALUE));
            idp.getCalledPartyBCDNumber().setStringBytes(build());
        }
    }

    private String build() {
        String result = "";
        byte first = (byte) ((Byte.parseByte(bcdNumber.getExtention(), 2) << 3) & 0x8);
        first |= Byte.parseByte(bcdNumber.getType(), 2) & 0x7;
        byte second = ((Byte.parseByte(bcdNumber.getNumberPlan(), 2)));
        byte prefix = (byte) (first << 4);
        prefix |= second;
        result += Integer.toHexString(Byte.toUnsignedInt(prefix)) + EncoderUtils.reverse(bcdNumber.getNumber());
        return result;
    }

    private class BcdNumber {
        private String extention;
        private String type;
        private String numberPlan;
        private String number;

        public String getExtention() {
            return extention;
        }

        public void setExtention(String extention) {
            this.extention = extention;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNumberPlan() {
            return numberPlan;
        }

        public void setNumberPlan(String numberPlan) {
            this.numberPlan = numberPlan;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }

}
