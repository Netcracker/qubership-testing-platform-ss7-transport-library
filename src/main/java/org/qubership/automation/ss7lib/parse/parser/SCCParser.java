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

import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.ADDRESS_INDICATOR;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CALLED_PARTY_ADDRESS;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CALLED_PARTY_DIGITS;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CALLING_PARTY_ADDRESS;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CALLING_PARTY_DIGITS;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CALL_PARTY_DIGITS;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CLASS;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.GLOBAL_TITLE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.GLOBAL_TITLE_INDICATOR;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.MESSAGE_HANDLING;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.MESSAGE_TYPE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.NATIONAL_INDICATOR;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.POINTER_TO_FIRST_MANDATORY_VARIABLE_PARAMETER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.POINTER_TO_SECOND_MANDATORY_VARIABLE_PARAMETER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.POINTER_TO_THIRD_MANDATORY_VARIABLE_PARAMETER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.POINT_CODE_INDICATOR;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.RESERVED_FOR_NATIONAL_USE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.ROUTING_INDICATOR;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.SIGNALLING_CONNECTION_CONTROL_PART;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.SUB_SYSTEM_NUMBER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.SUB_SYSTEM_NUMBER_INDICATOR;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.TRANSLATION_TYPE;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.qubership.automation.ss7lib.model.SccpMessage;
import org.qubership.automation.ss7lib.model.sub.sccp.AddressIndicator;
import org.qubership.automation.ss7lib.model.sub.sccp.CallPartyAddress;
import org.qubership.automation.ss7lib.model.sub.sccp.GlobalTitle;
import org.qubership.automation.ss7lib.model.type.EnumProvider;
import org.qubership.automation.ss7lib.model.type.MessageType;
import org.qubership.automation.ss7lib.model.type.SubSystemNumber;
import org.qubership.automation.ss7lib.parse.scenario.ScenarioManager;


public class SCCParser extends AbstractParser<SccpMessage> {

    @Override
    void parse(SccpMessage pojo, String value, List<String> parents) {
        String parent = parents.get(parents.size() - 1);
        if (parent.contains(SIGNALLING_CONNECTION_CONTROL_PART)) {
            Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getClass());
            if (contain(value, MESSAGE_TYPE)) {
                pojo.setMessageType(EnumProvider.of(parseField(value, scenario, MESSAGE_TYPE), MessageType.class));
            } else if (contain(value, CLASS)) {
                pojo.setClazz(parseField(value, scenario, CLASS));
            } else if (contain(value, MESSAGE_HANDLING)) {
                pojo.setMessageHandling(parseField(value, scenario, MESSAGE_HANDLING));
            } else if (contain(value, POINTER_TO_FIRST_MANDATORY_VARIABLE_PARAMETER)) {
                pojo.setPointerToFirstMandatoryVariable(parseField(value, scenario, POINTER_TO_FIRST_MANDATORY_VARIABLE_PARAMETER));
            } else if (contain(value, POINTER_TO_SECOND_MANDATORY_VARIABLE_PARAMETER)) {
                pojo.setPointerToSecondMandatoryVariable(parseField(value, scenario, POINTER_TO_SECOND_MANDATORY_VARIABLE_PARAMETER));
            } else if (contain(value, POINTER_TO_THIRD_MANDATORY_VARIABLE_PARAMETER)) {
                pojo.setPointerToThirdMandatoryVariable(parseField(value, scenario, POINTER_TO_THIRD_MANDATORY_VARIABLE_PARAMETER));
            }
        } else if (parent.contains(CALLED_PARTY_ADDRESS)) {
            if (Objects.isNull(pojo.getCalledPartyAddress())) {
                pojo.setCalledPartyAddress(new CallPartyAddress());
            }
            Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getCalledPartyAddress().getClass());
            if (contain(value, SUB_SYSTEM_NUMBER)) {
                pojo.getCalledPartyAddress().setSubSystemNumber(SubSystemNumber.of((byte) Integer.parseInt(getValue(SUB_SYSTEM_NUMBER, value.trim(), scenario.get(SUB_SYSTEM_NUMBER)))));
            }
        } else if (parent.contains(CALLING_PARTY_ADDRESS)) {
            if (Objects.isNull(pojo.getCallingPartyAddress())) {
                pojo.setCallingPartyAddress(new CallPartyAddress());
            }
            Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getCallingPartyAddress().getClass());
            if (contain(value, SUB_SYSTEM_NUMBER)) {
                pojo.getCallingPartyAddress().setSubSystemNumber(SubSystemNumber.of((byte) Integer.parseInt(getValue(SUB_SYSTEM_NUMBER, value.trim(), scenario.get(SUB_SYSTEM_NUMBER)))));
            }
        } else if (parent.contains(ADDRESS_INDICATOR)) {
            parent = parents.get(parents.size() - 2);
            if (parent.contains(CALLED_PARTY_ADDRESS)) {
                parseAddressIndicator(pojo.getCalledPartyAddress(), value);
            } else if (parent.contains(CALLING_PARTY_ADDRESS)) {
                parseAddressIndicator(pojo.getCallingPartyAddress(), value);
            }
        } else if (parent.contains(GLOBAL_TITLE)) {
            parent = parents.get(parents.size() - 2);
            if (parent.contains(CALLED_PARTY_ADDRESS)) {
                parseGlobalTittle(pojo.getCalledPartyAddress(), value);
            } else if (parent.contains(CALLING_PARTY_ADDRESS)) {
                parseGlobalTittle(pojo.getCallingPartyAddress(), value);
            }
        }

    }


    private void parseAddressIndicator(CallPartyAddress partyAddress, String value) {
        AddressIndicator indicator = partyAddress.getAddressIndicator();
        if (Objects.isNull(indicator)) {
            indicator = new AddressIndicator();
            partyAddress.setAddressIndicator(indicator);
        }
        Map<String, String> scenario = ScenarioManager.getInstance().getScenario(indicator.getClass());
        if (contain(value, NATIONAL_INDICATOR)) {
            indicator.setNationalIndicator(parseField(value, scenario, NATIONAL_INDICATOR));
        } else if (contain(value, RESERVED_FOR_NATIONAL_USE)) {//IT's not copy/past mistake setNationalIndicator
            indicator.setNationalIndicator(parseField(value, scenario, RESERVED_FOR_NATIONAL_USE));
        } else if (contain(value, ROUTING_INDICATOR)) {
            indicator.setRoutingIndicator(parseField(value, scenario, ROUTING_INDICATOR));
        } else if (contain(value, GLOBAL_TITLE_INDICATOR)) {
            indicator.setGlobalTitleIndicator(parseField(value, scenario, GLOBAL_TITLE_INDICATOR));
        } else if (contain(value, POINT_CODE_INDICATOR)) {
            indicator.setPointCodeIndicator(parseField(value, scenario, POINT_CODE_INDICATOR));
        } else if (contain(value, SUB_SYSTEM_NUMBER_INDICATOR)) {
            indicator.setSubSystemNumberIndicator(parseField(value, scenario, SUB_SYSTEM_NUMBER_INDICATOR));
        }
    }

    private byte parseField(String value, Map<String, String> scenario, String fieldName) {
        return Byte.parseByte(getValue(fieldName, value.trim(), scenario.get(fieldName)));
    }

    private void parseGlobalTittle(CallPartyAddress pojo, String value) {
        if (Objects.isNull(pojo.getGlobalTitle())) {
            pojo.setGlobalTitle(new GlobalTitle());
        }
        Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getGlobalTitle().getClass());
        if (contain(value, TRANSLATION_TYPE)) {
            pojo.getGlobalTitle().setTranslationType(splitHexStringByte(getValue(TRANSLATION_TYPE, value.trim(), scenario.get(TRANSLATION_TYPE))));
        } else if (contain(value, CALLED_PARTY_DIGITS)) {
            pojo.getGlobalTitle().setCallPartyDigits(getValue(CALLED_PARTY_DIGITS, value.trim(), scenario.get(CALL_PARTY_DIGITS)));
        } else if (contain(value, CALLING_PARTY_DIGITS)) {
            pojo.getGlobalTitle().setCallPartyDigits(getValue(CALLING_PARTY_DIGITS, value.trim(), scenario.get(CALL_PARTY_DIGITS)));
        }

    }
}
