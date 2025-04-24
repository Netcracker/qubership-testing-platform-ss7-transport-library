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

package org.qubership.automation.ss7lib.model.sub.cap.message.idp;

import org.qubership.automation.ss7lib.model.sub.Flag;
import org.qubership.automation.ss7lib.model.sub.cap.param.AbstractParamPojo;

public class PartyNumber extends AbstractParamPojo implements Flag {
    public static final transient byte CALLING_PARTY_NUMBER_FLAG = (byte) 0x83;
    public static final transient byte CALLED_PARTY_NUMBER_FLAG = (byte) 0x82;
    private String number;
    private byte oddIndicator;
    private byte natureAddressIndicator;
    private byte networkInformationIndicator;
    private byte numberingPlanIndicator;
    private byte addressPresentationIndicator;

    private byte screeningIndicator;

    public byte getFlag() {
        throw new UnsupportedOperationException("User getCalledPartNumberFlag/getCallingPartyNumberFlag");
    }

    @Override
    public boolean isHEX() {
        return true;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setOddIndicator(byte oddIndicator) {
        this.oddIndicator = oddIndicator;
    }

    public byte getOddIndicator() {
        return oddIndicator;
    }

    public void setNatureAddressIndicator(byte natureAddressIndicator) {
        this.natureAddressIndicator = natureAddressIndicator;
    }

    public byte getNatureAddressIndicator() {
        return natureAddressIndicator;
    }

    public void setNetworkInformationIndicator(byte networkInformationIndicator) {
        this.networkInformationIndicator = networkInformationIndicator;
    }

    public byte getNetworkInformationIndicator() {
        return networkInformationIndicator;
    }

    public byte getNumberingPlanIndicator() {
        return numberingPlanIndicator;
    }

    public void setNumberingPlanIndicator(byte numberingPlanIndicator) {
        this.numberingPlanIndicator = numberingPlanIndicator;
    }

    public byte getAddressPresentationIndicator() {
        return addressPresentationIndicator;
    }

    public void setAddressPresentationIndicator(byte addressPresentationIndicator) {
        this.addressPresentationIndicator = addressPresentationIndicator;
    }

    public byte getScreeningIndicator() {
        return screeningIndicator;
    }

    public void setScreeningIndicator(byte screeningIndicator) {
        this.screeningIndicator = screeningIndicator;
    }

}
