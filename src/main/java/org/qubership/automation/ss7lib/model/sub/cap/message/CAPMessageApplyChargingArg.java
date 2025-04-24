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

public class CAPMessageApplyChargingArg extends CAPMessagePojo {


    private AChBillingChargingCharacteristics aChBillingChargingCharacteristics;
    private PartyToCharge partyToCharge;

    public AChBillingChargingCharacteristics getaChBillingChargingCharacteristics() {
        return aChBillingChargingCharacteristics;
    }

    public void setaChBillingChargingCharacteristics(
            AChBillingChargingCharacteristics aChBillingChargingCharacteristics) {
        this.aChBillingChargingCharacteristics = aChBillingChargingCharacteristics;
    }

    public PartyToCharge getPartyToCharge() {
        return partyToCharge;
    }

    public void setPartyToCharge(PartyToCharge partyToCharge) {
        this.partyToCharge = partyToCharge;
    }

    public static class AChBillingChargingCharacteristics extends AbstractParamPojo implements Flag {
        private final byte startFlag = (byte) 0x80;
        private short time = 0;

        @Override
        public void setStringBytes(String stringBytes) {
            String s = stringBytes.substring(stringBytes.length() - 4);
            time = Short.parseShort(s, 16);
            super.setStringBytes(stringBytes);
        }

        public byte getFlag() {
            return startFlag;
        }

        @Override
        public boolean isHEX() {
            return true;
        }

        @Override
        public String toString() {
            return "AChBillingChargingCharacteristics{"
                    + "startFlag=" + startFlag
                    + ", value=" + super.getStringBytes() + '}';
        }
    }

    public static class PartyToCharge extends AbstractParamPojo implements Flags {
        private final byte[] startFlag = {(byte) 0xa2, (byte) 0x03, (byte) 0x80};


        public byte[] getFlags() {
            return startFlag;
        }

        @Override
        public String toString() {
            return "PartyToCharge{" + "startFlag=" + Arrays.toString(startFlag)
                    + ", value=" + super.getStringBytes() + '}';
        }
    }

    @Override
    public String toString() {
        return "CAPMessageApplyChargingArg{"
                + "aChBillingChargingCharacteristics=" + aChBillingChargingCharacteristics
                + ", partyToCharge=" + partyToCharge + '}';
    }
}
