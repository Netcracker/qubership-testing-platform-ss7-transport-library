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

package org.qubership.automation.ss7lib.model.sub.cap.message;

import java.util.Arrays;

import org.qubership.automation.ss7lib.model.sub.Flag;
import org.qubership.automation.ss7lib.model.sub.Flags;
import org.qubership.automation.ss7lib.model.sub.cap.param.AbstractParamPojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CAPMessageApplyChargingArg extends CAPMessagePojo {

    /**
     * ApplyCharging BillingChargingCharacteristics object field.
     */
    private AChBillingChargingCharacteristics aChBillingChargingCharacteristics;

    /**
     * PartyToCharge object field.
     */
    private PartyToCharge partyToCharge;

    public static class AChBillingChargingCharacteristics extends AbstractParamPojo implements Flag {

        /**
         * Start Flag byte; always = (byte) 0x80.
         */
        private final byte startFlag = (byte) 0x80;

        /**
         * Time field; set from String stringBytes parameter value.
         */
        private short time = 0;

        /**
         * Set stringBytes and time fields from String stringBytes parameter.
         *
         * @param stringBytes String bytes representation.
         */
        @Override
        public void setStringBytes(final String stringBytes) {
            String s = stringBytes.substring(stringBytes.length() - 4);
            time = Short.parseShort(s, 16);
            super.setStringBytes(stringBytes);
        }

        /**
         * Getter for startFlag field.
         *
         * @return byte startFlag field value.
         */
        public byte getFlag() {
            return startFlag;
        }

        /**
         * Get boolean isHex flag.
         *
         * @return boolean isHex flag; always true currently.
         */
        @Override
        public boolean isHEX() {
            return true;
        }

        /**
         * Make and return String representation of this object.
         *
         * @return String representation of this object.
         */
        @Override
        public String toString() {
            return "AChBillingChargingCharacteristics{"
                    + "startFlag=" + startFlag
                    + ", value=" + super.getStringBytes() + '}';
        }
    }

    public static class PartyToCharge extends AbstractParamPojo implements Flags {

        /**
         * Start Flag byte[]; always = {(byte) 0xa2, (byte) 0x03, (byte) 0x80}.
         */
        private final byte[] startFlag = {(byte) 0xa2, (byte) 0x03, (byte) 0x80};

        /**
         * Get flags byte[].
         *
         * @return byte[] startFlag.
         */
        public byte[] getFlags() {
            return startFlag;
        }

        /**
         * Make and return String representation of this object.
         *
         * @return String representation of this object.
         */
        @Override
        public String toString() {
            return "PartyToCharge{" + "startFlag=" + Arrays.toString(startFlag)
                    + ", value=" + super.getStringBytes() + '}';
        }
    }

    /**
     * Make and return String representation of this object.
     *
     * @return String representation of this object.
     */
    @Override
    public String toString() {
        return "CAPMessageApplyChargingArg{"
                + "aChBillingChargingCharacteristics=" + aChBillingChargingCharacteristics
                + ", partyToCharge=" + partyToCharge + '}';
    }
}
