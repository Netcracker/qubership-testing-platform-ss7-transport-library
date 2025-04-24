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

package org.qubership.automation.ss7lib.parse.scenario;

import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.AGE_OF_LOCATION_INFORMATION;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.APPLICATION_CONTEXT_NAME;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.A_CH_BILLING_CHARGING_CHARACTERISTICS;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.BEARER_CAP;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.BEARER_CAPABILITY;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CALLED_PARTY_BCDNUMBER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CALLED_PARTY_NUMBER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CALLING_PARTYS_CATEGORY;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CALL_PARTY_DIGITS;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CALL_REFERENCE_NUMBER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CELL_GLOBAL_ID_OR_SERVICE_AREA_ID_FIXED_LENGTH;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CIC_SELECTION_TYPE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CLASS;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.DIALOGUE_SERVICE_USER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.DPC;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.EVENT_SPECIFIC_INFORMATION_BCSM;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.EVENT_TYPE_BCSM;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.EXT_TELESERVICE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.GLOBAL_TITLE_INDICATOR;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.HIGH_LAYER_COMPATIBILITY;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.IDP_CALLING_PARTY_NUMBER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.IDP_CALLING_PARTY_NUMBER_VALUE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.IMSI;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.I_PSSPCAPABILITIES;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.LOCAL;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.LOCATION_NUMBER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.MESSAGE_CLASS;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.MESSAGE_HANDLING;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.MESSAGE_LENGTH;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.MESSAGE_TYPE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.MONITOR_MODE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.MP;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.MSC_ADDRESS;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.NATIONAL_INDICATOR;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.NETWORK_APPEARANCE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.NI;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.OID;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.OPC;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.PADDING;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.PARAMETER_LENGTH;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.PARAMETER_TAG;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.POINTER_TO_FIRST_MANDATORY_VARIABLE_PARAMETER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.POINTER_TO_SECOND_MANDATORY_VARIABLE_PARAMETER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.POINTER_TO_THIRD_MANDATORY_VARIABLE_PARAMETER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.POINT_CODE_INDICATOR;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.PRESENT;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.PROTOCOL_VERSION;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.RECEIVING_SIDE_ID;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.RESERVED;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.RESERVED_FOR_NATIONAL_USE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.RESULT;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.RESULT_SOURCE_DIAGNOSTIC;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.ROUTING_CONTEXT;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.ROUTING_INDICATOR;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.SENDING_SIDE_ID;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.SERVICE_KEY;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.SI;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.SLS;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.SUB_SYSTEM_NUMBER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.SUB_SYSTEM_NUMBER_INDICATOR;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.TID;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.TIME_AND_TIMEZONE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.TRANSLATION_TYPE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.VERSION;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.VLR_NUMBER;
import static org.qubership.automation.ss7lib.parse.scenario.Pattern.HEX_PATTERN;
import static org.qubership.automation.ss7lib.parse.scenario.Pattern.HEX_PATTERN_IN_BRACKETS;
import static org.qubership.automation.ss7lib.parse.scenario.Pattern.INTEGER_THROUGH_POINT;
import static org.qubership.automation.ss7lib.parse.scenario.Pattern.NUMERIC_VALUE;

import java.util.Map;
import java.util.Objects;

import org.qubership.automation.ss7lib.model.CapMessage;
import org.qubership.automation.ss7lib.model.M3uaMessage;
import org.qubership.automation.ss7lib.model.SccpMessage;
import org.qubership.automation.ss7lib.model.TcapMessage;
import org.qubership.automation.ss7lib.model.sub.cap.CAPInvokeIDPojo;
import org.qubership.automation.ss7lib.model.sub.cap.CAPOpCodePojo;
import org.qubership.automation.ss7lib.model.sub.cap.CapInvoke;
import org.qubership.automation.ss7lib.model.sub.cap.message.BSCMEvent;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageApplyChargingArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageConnectArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageEventReportBCSMArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageRequestReportBCSMEventArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.InitialDetectionPoint;
import org.qubership.automation.ss7lib.model.sub.cap.message.LegID;
import org.qubership.automation.ss7lib.model.sub.m3ua.NetworkAppearance;
import org.qubership.automation.ss7lib.model.sub.m3ua.ProtocolData;
import org.qubership.automation.ss7lib.model.sub.m3ua.RoutingContext;
import org.qubership.automation.ss7lib.model.sub.sccp.AddressIndicator;
import org.qubership.automation.ss7lib.model.sub.sccp.CallPartyAddress;
import org.qubership.automation.ss7lib.model.sub.sccp.GlobalTitle;
import org.qubership.automation.ss7lib.model.sub.tcap.Dialogue;
import org.qubership.automation.ss7lib.model.sub.tcap.ResultSourceDiagnostic;
import org.qubership.automation.ss7lib.model.sub.tcap.Transaction;

import com.google.common.collect.Maps;

public class ScenarioManager {

    private static ScenarioManager INSTANCE = new ScenarioManager();

    private final Map<Class, Map<String, String>> scenario = Maps.newHashMap();

    private final Map<String, String> m3uaScenario = Maps.newHashMap();
    private final Map<String, String> sccpScenario = Maps.newHashMap();
    private final Map<String, String> tcapScenario = Maps.newHashMap();
    private final Map<String, String> capScenario = Maps.newHashMap();

    private final Map<String, String> networkAppearanceTags = Maps.newHashMap();
    private final Map<String, String> routingContextTags = Maps.newHashMap();
    private final Map<String, String> protocolDataTags = Maps.newHashMap();

    private final Map<String, String> callPartyAddress = Maps.newHashMap();
    private final Map<String, String> addressIndicator = Maps.newHashMap();
    private final Map<String, String> globalTitleForCall = Maps.newHashMap();

    private final Map<String, String> transaction = Maps.newHashMap();
    private final Map<String, String> dialogue = Maps.newHashMap();
    private final Map<String, String> resultSourceDiagnostic = Maps.newHashMap();

    private final Map<String, String> invoke = Maps.newHashMap();
    private final Map<String, String> invokeId = Maps.newHashMap();
    private final Map<String, String> opCode = Maps.newHashMap();
    private final Map<String, String> idp = Maps.newHashMap();
    private final Map<String, String> bearerCapability = Maps.newHashMap();
    private final Map<String, String> locationInformation = Maps.newHashMap();
    private final Map<String, String> extBasicServiceCode = Maps.newHashMap();
    private final Map<String, String> cellGlobalIdOrServiceAreaIdOrLAI = Maps.newHashMap();
    private final Map<String, String> connectArg = Maps.newHashMap();
    private final Map<String, String> destinationRoutingAddress = Maps.newHashMap();
    private final Map<String, String> requestReportBCSMEventArg = Maps.newHashMap();
    private final Map<String, String> bSCMEvent = Maps.newHashMap();
    private final Map<String, String> legID = Maps.newHashMap();
    private final Map<String, String> applyChargingArg = Maps.newHashMap();
    private final Map<String, String> aChBillingChargingCharacteristics = Maps.newHashMap();
    private final Map<String, String> partyToCharge = Maps.newHashMap();


    private ScenarioManager() {
        scenario.put(M3uaMessage.class, m3uaScenario);
        scenario.put(NetworkAppearance.class, networkAppearanceTags);
        scenario.put(RoutingContext.class, routingContextTags);
        scenario.put(ProtocolData.class, protocolDataTags);

        scenario.put(SccpMessage.class, sccpScenario);
        scenario.put(CallPartyAddress.class, callPartyAddress);
        scenario.put(GlobalTitle.class, globalTitleForCall);
        scenario.put(AddressIndicator.class, addressIndicator);

        scenario.put(TcapMessage.class, tcapScenario);
        scenario.put(Transaction.class, transaction);
        scenario.put(Dialogue.class, dialogue);
        scenario.put(ResultSourceDiagnostic.class, resultSourceDiagnostic);

        scenario.put(CapMessage.class, capScenario);
        scenario.put(CapInvoke.class, invoke);
        scenario.put(CAPInvokeIDPojo.class, invokeId);
        scenario.put(CAPOpCodePojo.class, opCode);
        scenario.put(InitialDetectionPoint.class, idp);
        scenario.put(InitialDetectionPoint.BearerCapability.class, bearerCapability);
        scenario.put(InitialDetectionPoint.LocationInformation.class, locationInformation);
        scenario.put(InitialDetectionPoint.LocationInformation.CellGlobalIdOrServiceAreaIdOrLAI.class, cellGlobalIdOrServiceAreaIdOrLAI);
        scenario.put(InitialDetectionPoint.ExtBasicServiceCode.class, extBasicServiceCode);
        scenario.put(CAPMessageConnectArg.class, connectArg);
        scenario.put(CAPMessageConnectArg.DestinationRoutingAddress.class, destinationRoutingAddress);
        scenario.put(CAPMessageRequestReportBCSMEventArg.class, requestReportBCSMEventArg);
        scenario.put(CAPMessageEventReportBCSMArg.class, requestReportBCSMEventArg);
        scenario.put(CAPMessageRequestReportBCSMEventArg.BSCMEvent.class, bSCMEvent);
        scenario.put(BSCMEvent.class, bSCMEvent);
        scenario.put(LegID.class, legID);
        scenario.put(CAPMessageApplyChargingArg.class, applyChargingArg);
        scenario.put(CAPMessageApplyChargingArg.AChBillingChargingCharacteristics.class, aChBillingChargingCharacteristics);
        scenario.put(CAPMessageApplyChargingArg.PartyToCharge.class, partyToCharge);

        /*M3UA*/
        m3uaScenario.put(VERSION, NUMERIC_VALUE);
        m3uaScenario.put(RESERVED, NUMERIC_VALUE);
        m3uaScenario.put(MESSAGE_CLASS, NUMERIC_VALUE);
        m3uaScenario.put(MESSAGE_TYPE, NUMERIC_VALUE);
        m3uaScenario.put(MESSAGE_LENGTH, NUMERIC_VALUE);

        networkAppearanceTags.put(PARAMETER_TAG, NUMERIC_VALUE);
        networkAppearanceTags.put(PARAMETER_LENGTH, NUMERIC_VALUE);
        networkAppearanceTags.put(NETWORK_APPEARANCE, NUMERIC_VALUE);

        routingContextTags.put(PARAMETER_TAG, NUMERIC_VALUE);
        routingContextTags.put(PARAMETER_LENGTH, NUMERIC_VALUE);
        routingContextTags.put(ROUTING_CONTEXT, NUMERIC_VALUE);

        protocolDataTags.put(PARAMETER_TAG, NUMERIC_VALUE);
        protocolDataTags.put(PARAMETER_LENGTH, NUMERIC_VALUE);
        protocolDataTags.put(OPC, NUMERIC_VALUE);
        protocolDataTags.put(DPC, NUMERIC_VALUE);
        protocolDataTags.put(SI, NUMERIC_VALUE);
        protocolDataTags.put(NI, NUMERIC_VALUE);
        protocolDataTags.put(MP, NUMERIC_VALUE);
        protocolDataTags.put(SLS, NUMERIC_VALUE);
        protocolDataTags.put(PADDING, NUMERIC_VALUE);

        /*SCCP*/
        sccpScenario.put(MESSAGE_TYPE, HEX_PATTERN_IN_BRACKETS);
        sccpScenario.put(CLASS, HEX_PATTERN_IN_BRACKETS);
        sccpScenario.put(MESSAGE_HANDLING, HEX_PATTERN_IN_BRACKETS);
        sccpScenario.put(POINTER_TO_FIRST_MANDATORY_VARIABLE_PARAMETER, NUMERIC_VALUE);
        sccpScenario.put(POINTER_TO_SECOND_MANDATORY_VARIABLE_PARAMETER, NUMERIC_VALUE);
        sccpScenario.put(POINTER_TO_THIRD_MANDATORY_VARIABLE_PARAMETER, NUMERIC_VALUE);

        callPartyAddress.put(SUB_SYSTEM_NUMBER, NUMERIC_VALUE);

        addressIndicator.put(NATIONAL_INDICATOR, HEX_PATTERN_IN_BRACKETS);
        addressIndicator.put(RESERVED_FOR_NATIONAL_USE, HEX_PATTERN_IN_BRACKETS);
        addressIndicator.put(ROUTING_INDICATOR, HEX_PATTERN_IN_BRACKETS);
        addressIndicator.put(GLOBAL_TITLE_INDICATOR, HEX_PATTERN_IN_BRACKETS);
        addressIndicator.put(POINT_CODE_INDICATOR, HEX_PATTERN_IN_BRACKETS);
        addressIndicator.put(SUB_SYSTEM_NUMBER_INDICATOR, HEX_PATTERN_IN_BRACKETS);

        globalTitleForCall.put(TRANSLATION_TYPE, HEX_PATTERN_IN_BRACKETS);
        globalTitleForCall.put(CALL_PARTY_DIGITS, HEX_PATTERN);

        /*TCAP*/
        tcapScenario.put(OID, INTEGER_THROUGH_POINT);
        transaction.put(TID, HEX_PATTERN);
        dialogue.put(PADDING, HEX_PATTERN);
        dialogue.put(PROTOCOL_VERSION, HEX_PATTERN);
        dialogue.put(APPLICATION_CONTEXT_NAME, INTEGER_THROUGH_POINT);
        dialogue.put(RESULT, NUMERIC_VALUE);
        dialogue.put(RESULT_SOURCE_DIAGNOSTIC, NUMERIC_VALUE);
        resultSourceDiagnostic.put(DIALOGUE_SERVICE_USER, NUMERIC_VALUE);

        /*CAP*/
        invokeId.put(PRESENT, NUMERIC_VALUE);
        opCode.put(LOCAL, NUMERIC_VALUE);
        idp.put(SERVICE_KEY, NUMERIC_VALUE);
        idp.put(IDP_CALLING_PARTY_NUMBER, HEX_PATTERN);
        idp.put(IDP_CALLING_PARTY_NUMBER_VALUE, NUMERIC_VALUE);
        idp.put(CALLING_PARTYS_CATEGORY, NUMERIC_VALUE);
        idp.put(I_PSSPCAPABILITIES, NUMERIC_VALUE);
        idp.put(LOCATION_NUMBER, HEX_PATTERN);
        idp.put(HIGH_LAYER_COMPATIBILITY, HEX_PATTERN);
        idp.put(BEARER_CAPABILITY, NUMERIC_VALUE);
        idp.put(EVENT_TYPE_BCSM, NUMERIC_VALUE);
        idp.put(IMSI, NUMERIC_VALUE);
        idp.put(CALL_REFERENCE_NUMBER, HEX_PATTERN);
        idp.put(MSC_ADDRESS, HEX_PATTERN);
        idp.put(CALLED_PARTY_BCDNUMBER, HEX_PATTERN);
        idp.put(TIME_AND_TIMEZONE, HEX_PATTERN);
        idp.put(CIC_SELECTION_TYPE, NUMERIC_VALUE);
        bearerCapability.put(BEARER_CAP, HEX_PATTERN);
        locationInformation.put(AGE_OF_LOCATION_INFORMATION, NUMERIC_VALUE);
        locationInformation.put(VLR_NUMBER, HEX_PATTERN);
        cellGlobalIdOrServiceAreaIdOrLAI.put(CELL_GLOBAL_ID_OR_SERVICE_AREA_ID_FIXED_LENGTH, HEX_PATTERN);
        extBasicServiceCode.put(EXT_TELESERVICE, NUMERIC_VALUE);
        destinationRoutingAddress.put(CALLED_PARTY_NUMBER, HEX_PATTERN);
        bSCMEvent.put(EVENT_TYPE_BCSM, NUMERIC_VALUE);
        bSCMEvent.put(MONITOR_MODE, NUMERIC_VALUE);
        bSCMEvent.put(EVENT_SPECIFIC_INFORMATION_BCSM, NUMERIC_VALUE);
        legID.put(SENDING_SIDE_ID, NUMERIC_VALUE);
        legID.put(RECEIVING_SIDE_ID, NUMERIC_VALUE);
        applyChargingArg.put(A_CH_BILLING_CHARGING_CHARACTERISTICS, HEX_PATTERN);
        partyToCharge.put(SENDING_SIDE_ID, NUMERIC_VALUE);

    }

    public static ScenarioManager getInstance() {
        return INSTANCE;
    }

    public Map<String, String> getScenario(Class clazz) {
        Map<String, String> map = scenario.get(clazz);
        if (Objects.isNull(map)) {
            throw new IllegalArgumentException("scenario for " + clazz.getCanonicalName() + " is null ");
        }
        return map;
    }

    public static String get(Class clazz, String key) {
        return getInstance().getScenario(clazz).get(key);
    }
}
