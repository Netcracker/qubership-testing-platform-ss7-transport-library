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

package org.qubership.automation.ss7lib.parse.parser.cap;

import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.IDP_CALLED_PARTY_NUMBER_VALUE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.IDP_CALLING_PARTY_NUMBER_VALUE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.IDP_LOCATION_NUMBER;
import static org.qubership.automation.ss7lib.parse.scenario.Pattern.BINARY_PATTERN;
import static org.qubership.automation.ss7lib.parse.scenario.Pattern.HEX_PATTERN;

import org.qubership.automation.ss7lib.model.sub.cap.message.idp.PartyNumber;
import org.qubership.automation.ss7lib.parse.parser.PartParser;

public class PartyNumberParser extends PartParser<PartyNumber> {

    /**
     * Constant String before odd/even indicator value.
     */
    private static final String ODD_EVEN_INDICATOR = "Odd/even indicator";

    /**
     * Constant String before odd/even indicator value.
     */
    private static final String NATURE_OF_ADDRESS_INDICATOR = "Nature of address indicator";

    /**
     * Constant String before network information indicator value.
     */
    private static final String NI_INDICATOR = "NI indicator";

    /**
     * Constant String before numbering plan indicator value.
     */
    private static final String NUMBERING_PLAN_INDICATOR = "Numbering plan Indicator";

    /**
     * Constant String before Address presentation restricted indicator value.
     */
    private static final String RESTRICTION_INDICATOR = "Address presentation restricted indicator";

    /**
     * Constant String before Screening indicator value.
     */
    private static final String SCREEN_INDICATOR = "Screening indicator";

    /**
     * Parse String value into PartyNumber object.
     *
     * @param value String value to parse
     * @param partyNumber object to parse into.
     */
    @Override
    public void parse(final String value, final PartyNumber partyNumber) {
        String trimmedValue = value.trim();
        if (applicable(value, IDP_CALLING_PARTY_NUMBER_VALUE)) {
            partyNumber.setNumber(getValue(IDP_CALLING_PARTY_NUMBER_VALUE, trimmedValue, HEX_PATTERN));
            return;
        }
        if (applicable(value, IDP_CALLED_PARTY_NUMBER_VALUE)) {
            partyNumber.setNumber(getValue(IDP_CALLED_PARTY_NUMBER_VALUE, trimmedValue, HEX_PATTERN));
            return;
        }
        if (applicable(value, IDP_LOCATION_NUMBER)) {
            partyNumber.setNumber(getValue(IDP_LOCATION_NUMBER, trimmedValue, HEX_PATTERN));
            return;
        }
        if (applicable(value, ODD_EVEN_INDICATOR)) {
            partyNumber.setOddIndicator(parseBinary(trimmedValue, ODD_EVEN_INDICATOR, BINARY_PATTERN));
            return;
        }
        if (applicable(value, NATURE_OF_ADDRESS_INDICATOR)) {
            partyNumber.setNatureAddressIndicator(
                    parseBinary(value, NATURE_OF_ADDRESS_INDICATOR, BINARY_PATTERN)
            );
            return;
        }
        if (applicable(value, NI_INDICATOR)) {
            partyNumber.setNetworkInformationIndicator(parseBinary(value, NI_INDICATOR, BINARY_PATTERN));
            return;
        }
        if (applicable(value, NUMBERING_PLAN_INDICATOR)) {
            partyNumber.setNumberingPlanIndicator(
                    parseBinary(value, NUMBERING_PLAN_INDICATOR, BINARY_PATTERN)
            );
            return;
        }
        if (applicable(value, RESTRICTION_INDICATOR)) {
            partyNumber.setAddressPresentationIndicator(
                    parseBinary(value, RESTRICTION_INDICATOR, BINARY_PATTERN)
            );
            return;
        }
        if (applicable(value, SCREEN_INDICATOR)) {
            partyNumber.setScreeningIndicator(parseBinary(value, SCREEN_INDICATOR, BINARY_PATTERN));
        }
    }

}
