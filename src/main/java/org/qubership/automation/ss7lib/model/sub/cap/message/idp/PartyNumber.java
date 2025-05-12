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

import lombok.Getter;
import lombok.Setter;

public class PartyNumber extends AbstractParamPojo implements Flag {

    public static final byte CALLING_PARTY_NUMBER_FLAG = (byte) 0x83;
    public static final byte CALLED_PARTY_NUMBER_FLAG = (byte) 0x82;

    /**
     * String number of the party.
     */
    @Setter
    @Getter
    private String number;

    /**
     * Odd/even indicator field.
     */
    @Setter
    @Getter
    private byte oddIndicator;

    /**
     * Nature of Address indicator field.
     */
    @Setter
    @Getter
    private byte natureAddressIndicator;

    /**
     * Network Information indicator field.
     */
    @Setter
    @Getter
    private byte networkInformationIndicator;

    /**
     * Numbering Plan indicator field.
     */
    @Setter
    @Getter
    private byte numberingPlanIndicator;

    /**
     * Address Presentation restricted indicator field.
     */
    @Setter
    @Getter
    private byte addressPresentationIndicator;

    /**
     * Screening Indicator field.
     */
    @Setter
    @Getter
    private byte screeningIndicator;

    /**
     * Get flag byte.
     *
     * @return byte value of flag; throw UnsupportedOperationException for this exact class objects.
     */
    public byte getFlag() {
        throw new UnsupportedOperationException("Please use getCalledPartNumberFlag/getCallingPartyNumberFlag instead");
    }

    /**
     * Check if this is hex object (true or false).
     *
     * @return true if it's hex, otherwise false; always true currently.
     */
    @Override
    public boolean isHEX() {
        return true;
    }

}
