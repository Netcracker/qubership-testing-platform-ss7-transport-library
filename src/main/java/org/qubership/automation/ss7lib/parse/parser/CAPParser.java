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

import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.AGE_OF_LOCATION_INFORMATION;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.APPLY_CHARGING_ARG;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.APPLY_CHARGING_REPORT_ARG;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.A_CH_BILLING_CHARGING_CHARACTERISTICS;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.BCSMEVENT;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.BEARER_CAP;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.BEARER_CAPABILITY;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CALLED_PARTY_BCDNUMBER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CALLED_PARTY_NUMBER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CALLING_PARTYS_CATEGORY;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CALL_REFERENCE_NUMBER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CELL_GLOBAL_ID_OR_SERVICE_AREA_ID_FIXED_LENGTH;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CELL_GLOBAL_ID_OR_SERVICE_AREA_ID_OR_LAI;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CIC_SELECTION_TYPE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CONNECT_ARG;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.DESTINATION_ROUTING_ADDRESS;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.EVENT_REPORT_BCSM_ARG;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.EVENT_SPECIFIC_INFORMATION_BCSM;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.EVENT_TYPE_BCSM;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.EXT_BASIC_SERVICE_CODE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.EXT_TELESERVICE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.HIGH_LAYER_COMPATIBILITY;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.IDP_CALLED_PARTY_NUMBER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.IDP_CALLING_PARTY_NUMBER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.IMSI;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.INITIAL_DP_ARG;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.INITIAL_DP_ARG_EXTENSION;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.INVOKE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.INVOKE_ID;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.I_PSSPCAPABILITIES;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.LEG_ID;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.LOCAL;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.LOCATION_INFORMATION;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.LOCATION_NUMBER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.MONITOR_MODE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.MSC_ADDRESS;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.OPCODE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.PARTY_TO_CHARGE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.PRESENT;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.RECEIVING_SIDE_ID;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.REQUEST_REPORT_BCSMEVENT_ARG;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.SENDING_SIDE_ID;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.SERVICE_KEY;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.TIME_AND_TIMEZONE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.VLR_NUMBER;
import static org.qubership.automation.ss7lib.parse.scenario.Pattern.HEX_PATTERN;
import static org.qubership.automation.ss7lib.parse.scenario.Pattern.NUMERIC_VALUE;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.qubership.automation.ss7lib.convert.Converter;
import org.qubership.automation.ss7lib.model.CapMessage;
import org.qubership.automation.ss7lib.model.pojo.MiscCallInfo;
import org.qubership.automation.ss7lib.model.pojo.SpecificInformation;
import org.qubership.automation.ss7lib.model.sub.cap.CAPInvokeIDPojo;
import org.qubership.automation.ss7lib.model.sub.cap.CAPOpCodePojo;
import org.qubership.automation.ss7lib.model.sub.cap.CapInvoke;
import org.qubership.automation.ss7lib.model.sub.cap.message.ApplyChargingReportArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.BSCMEvent;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageApplyChargingArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageConnectArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageEventReportBCSMArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageRequestReportBCSMEventArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.InitialDetectionPoint;
import org.qubership.automation.ss7lib.model.sub.cap.message.LegID;
import org.qubership.automation.ss7lib.model.sub.cap.message.RedirectionInformation;
import org.qubership.automation.ss7lib.model.sub.cap.message.RedirectionPartyId;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.InitialDpArgExtension;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.PartyNumber;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.SelectionType;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.ServiceKey;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.extention.Extension;
import org.qubership.automation.ss7lib.model.type.EnumProvider;
import org.qubership.automation.ss7lib.model.type.EventType;
import org.qubership.automation.ss7lib.model.type.MonitorMode;
import org.qubership.automation.ss7lib.parse.parser.cap.InitialDpArgExtensionParser;
import org.qubership.automation.ss7lib.parse.parser.cap.PartyNumberParser;
import org.qubership.automation.ss7lib.parse.scenario.ScenarioManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

public class CAPParser extends AbstractParser<CapMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CAPParser.class);
    private final BCDParser bcdParser = new BCDParser();
    private PartyNumberParser partyNumberParser = new PartyNumberParser();
    private InitialDpArgExtensionParser initialDpArgExtensionParser = new InitialDpArgExtensionParser();

    @Override
    void parse(CapMessage pojo, String value, List<String> parents) {
        if (Strings.isNullOrEmpty(value)) {
            return;
        }
        String parent = parents.get(parents.size() - 1).trim();
        if (parent.contains(INVOKE)) {
            if (Objects.isNull(pojo.getInvoke())) {
                pojo.setInvoke(new CapInvoke());
            }
        }
        if (parent.contains(INVOKE_ID)) {
            if (Objects.isNull(pojo.getInvoke().getCapMessagePojo())) {
                pojo.getInvoke().setInvokeID(new CAPInvokeIDPojo());
            }
            Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getInvoke().getInvokeID().getClass());
            if (contain(value, PRESENT)) {
                pojo.getInvoke().getInvokeID().setStringBytes(getValue(PRESENT, value, scenario.get(PRESENT)));
            }
        } else if (parent.contains(OPCODE)) {
            if (Objects.isNull(pojo.getInvoke().getOpCode())) {
                pojo.getInvoke().setOpCode(new CAPOpCodePojo());
            }
            Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getInvoke().getOpCode().getClass());
            if (contain(value, LOCAL)) {
                pojo.getInvoke().getOpCode().setStringBytes(getValue(LOCAL, value, scenario.get(LOCAL)));
            }
        } else if (parent.contains(INITIAL_DP_ARG)) {
            if (Objects.isNull(pojo.getInvoke().getCapMessagePojo())) {
                pojo.getInvoke().setCapMessagePojo(new InitialDetectionPoint());
            }
            parseMessageIDP(pojo.getInvoke().getCapMessagePojo(), value, parent);
        } else if (parent.contains(REQUEST_REPORT_BCSMEVENT_ARG)) {
            if (Objects.isNull(pojo.getInvoke().getCapMessagePojo())) {
                pojo.getInvoke().setCapMessagePojo(new CAPMessageRequestReportBCSMEventArg());
            }
            parseMessageRequestReportBCSMEventArg(pojo.getInvoke().getCapMessagePojo(), value, parent);
        } else if (parent.contains(APPLY_CHARGING_ARG)) {
            if (Objects.isNull(pojo.getInvoke().getCapMessagePojo())) {
                pojo.getInvoke().setCapMessagePojo(new CAPMessageApplyChargingArg());
            }
            parseMessageApplyChargingArg(pojo.getInvoke().getCapMessagePojo(), value, parent);
        } else if (parent.contains(CONNECT_ARG)) {
            if (Objects.isNull(pojo.getInvoke().getCapMessagePojo())) {
                pojo.getInvoke().setCapMessagePojo(new CAPMessageConnectArg());
            }
            parseMessageConnectArg(pojo.getInvoke().getCapMessagePojo(), value, parent);
        } else if (parent.trim().equals(EVENT_REPORT_BCSM_ARG)) {
            CAPMessageEventReportBCSMArg bcsmEventArg = pojo.getInvoke().getCapMessagePojo();
            if (Objects.isNull(bcsmEventArg)) {
                bcsmEventArg = new CAPMessageEventReportBCSMArg();
                pojo.getInvoke().setCapMessagePojo(bcsmEventArg);

            }
            BSCMEvent bcsmEvent;
            if (bcsmEventArg.getBscmEventList().size() > 0) {
                bcsmEvent = bcsmEventArg.getBscmEventList().getLast();
            } else {
                bcsmEvent = new BSCMEvent();
                bcsmEventArg.getBscmEventList().add(bcsmEvent);
            }
            parseEventBCSMEvent(bcsmEvent, value);

        } else if (parent.trim().contains(APPLY_CHARGING_REPORT_ARG)) {
            ApplyChargingReportArg applyChargingArg = new ApplyChargingReportArg();
            if (Objects.isNull(pojo.getInvoke().getCapMessagePojo())) {
                pojo.getInvoke().setCapMessagePojo(applyChargingArg);
            }
            applyChargingArg.setValue(parseAcr(parent));
        } else if (parent.contains(LOCATION_INFORMATION)) {
            if (pojo.getInvoke().getCapMessagePojo() instanceof InitialDetectionPoint) {
                if (Objects.isNull(((InitialDetectionPoint) pojo.getInvoke().getCapMessagePojo()).getLocationInformation())) {
                    ((InitialDetectionPoint) pojo.getInvoke().getCapMessagePojo()).setLocationInformation(new InitialDetectionPoint.LocationInformation());
                }
            }
            parseLocationinformation(((InitialDetectionPoint) pojo.getInvoke().getCapMessagePojo()).getLocationInformation(), value, parent);
        } else if (parent.contains(CELL_GLOBAL_ID_OR_SERVICE_AREA_ID_OR_LAI)) {
            if (pojo.getInvoke().getCapMessagePojo() instanceof InitialDetectionPoint) {
                if (Objects.isNull(((InitialDetectionPoint) pojo.getInvoke().getCapMessagePojo()).getLocationInformation().getCellGlobalIdOrServiceAreaIdOrLAI())) {
                    ((InitialDetectionPoint) pojo.getInvoke().getCapMessagePojo()).getLocationInformation().setCellGlobalIdOrServiceAreaIdOrLAI(
                            new InitialDetectionPoint.LocationInformation.CellGlobalIdOrServiceAreaIdOrLAI());
                }
            }
            parseCellGlobalIdOrServiceAreaIdOrLAI(((InitialDetectionPoint) pojo.getInvoke().getCapMessagePojo()).getLocationInformation().getCellGlobalIdOrServiceAreaIdOrLAI(),
                    value, parent);
        } else if (parent.contains(EXT_BASIC_SERVICE_CODE)) {
            if (pojo.getInvoke().getCapMessagePojo() instanceof InitialDetectionPoint) {
                if (Objects.isNull(((InitialDetectionPoint) pojo.getInvoke().getCapMessagePojo()).getExtBasicServiceCode())) {
                    ((InitialDetectionPoint) pojo.getInvoke().getCapMessagePojo()).setExtBasicServiceCode(new InitialDetectionPoint.ExtBasicServiceCode());
                }
            }
            parseExtBasicServiceCode(((InitialDetectionPoint) pojo.getInvoke().getCapMessagePojo()).getExtBasicServiceCode(), value, parent);
        } else if (parent.contains(BEARER_CAPABILITY)) {
            InitialDetectionPoint initialDetectionPoint = pojo.getInvoke().getCapMessagePojo();
            if (pojo.getInvoke().getCapMessagePojo() instanceof InitialDetectionPoint) {
                if (Objects.isNull((initialDetectionPoint).getBearerCapability())) {
                    (initialDetectionPoint).setBearerCapability(new InitialDetectionPoint.BearerCapability());
                }
            }
            parseBearerCapability(initialDetectionPoint.getBearerCapability(), value, parent);
        } else if (parent.contains(BCSMEVENT)) {
            if (pojo.getInvoke().getCapMessagePojo() instanceof CAPMessageRequestReportBCSMEventArg) {
                CAPMessageRequestReportBCSMEventArg capMessagePojo = pojo.getInvoke().getCapMessagePojo();
                LinkedList<CAPMessageRequestReportBCSMEventArg.BSCMEvent> bscmEventList = capMessagePojo.getBscmEventList();
                CAPMessageRequestReportBCSMEventArg.BSCMEvent bscmEvent;
                if (contain(value, EVENT_TYPE_BCSM)) {
                    bscmEvent = new CAPMessageRequestReportBCSMEventArg.BSCMEvent();
                    bscmEventList.add(bscmEvent);
                } else {
                    bscmEvent = bscmEventList.getLast();
                }
                parseBCSMEvent(bscmEvent, value, parent);
            }
        } else if (parent.contains(LEG_ID)) {
            if (pojo.getInvoke().getCapMessagePojo() instanceof CAPMessageRequestReportBCSMEventArg) {
                CAPMessageRequestReportBCSMEventArg messagePojo = pojo.getInvoke().getCapMessagePojo();
                LinkedList<CAPMessageRequestReportBCSMEventArg.BSCMEvent> bscmEventList = messagePojo.getBscmEventList();
                CAPMessageRequestReportBCSMEventArg.BSCMEvent bscmEvent = bscmEventList.getLast();
                if (Objects.isNull(bscmEvent.getLegID())) {
                    bscmEvent.setLegID(new LegID());
                }
                parseLegID(bscmEvent.getLegID(), value, parent);
            }
            if (pojo.getInvoke().getCapMessagePojo() instanceof CAPMessageEventReportBCSMArg) {
                CAPMessageEventReportBCSMArg messagePojo = pojo.getInvoke().getCapMessagePojo();
                LinkedList<BSCMEvent> bscmEventList = messagePojo.getBscmEventList();
                BSCMEvent bscmEvent = bscmEventList.getLast();
                if (Objects.isNull(bscmEvent.getLegID())) {
                    bscmEvent.setLegID(new LegID());
                }
                parseLegID(bscmEvent.getLegID(), value, parent);
            }
        } else if (parent.contains(PARTY_TO_CHARGE)) {
            if (pojo.getInvoke().getCapMessagePojo() instanceof CAPMessageApplyChargingArg) {
                if (Objects.isNull(((CAPMessageApplyChargingArg) pojo.getInvoke().getCapMessagePojo()).getPartyToCharge())) {
                    ((CAPMessageApplyChargingArg) pojo.getInvoke().getCapMessagePojo()).setPartyToCharge(new CAPMessageApplyChargingArg.PartyToCharge());
                }
                parsePartyToCharge(((CAPMessageApplyChargingArg) pojo.getInvoke().getCapMessagePojo()).getPartyToCharge(), value, parent);
            }
        } else if (parent.contains(DESTINATION_ROUTING_ADDRESS) && !Strings.isNullOrEmpty(value)) {
            if (pojo.getInvoke().getCapMessagePojo() instanceof CAPMessageConnectArg) {
                ((CAPMessageConnectArg) pojo.getInvoke().getCapMessagePojo()).getDestinationRoutingAddressList().add(new CAPMessageConnectArg.DestinationRoutingAddress());
                parseDestinationRoutingAddress(((CAPMessageConnectArg) pojo.getInvoke().getCapMessagePojo()).getDestinationRoutingAddressList().get(
                        ((CAPMessageConnectArg) pojo.getInvoke().getCapMessagePojo()).getDestinationRoutingAddressList().size() - 1
                ), value, parent);
            }
        } else if ("oDisconnectSpecificInfo".equals(parent) || "tBusySpecificInfo".equals(parent) || "tDisconnectSpecificInfo".equals(parent)) {
            if (pojo.getInvoke().getCapMessagePojo() instanceof CAPMessageEventReportBCSMArg) {
                CAPMessageEventReportBCSMArg message = pojo.getInvoke().getCapMessagePojo();
                BSCMEvent event = message.getBscmEventList().getLast();
                if ("callForwarded".equals(value)) {
                    event.setCallForwarded(true);
                    return;
                }
                event.getSpecificInformation().setReleaseCause(parseReleaseCause(value));
            }
        } else if (parent.contains(IDP_CALLING_PARTY_NUMBER)) {
            InitialDetectionPoint idp = pojo.getInvoke().getCapMessagePojo();
            partyNumberParser.parse(value, idp.getCallingPartyNumber());
        } else if (parent.contains(IDP_CALLED_PARTY_NUMBER)) {
            InitialDetectionPoint idp = pojo.getInvoke().getCapMessagePojo();
            partyNumberParser.parse(value, idp.getCalledPartyNumber());
        } else if (parent.contains(INITIAL_DP_ARG_EXTENSION)) {
            InitialDetectionPoint idp = pojo.getInvoke().getCapMessagePojo();
            initialDpArgExtensionParser.parse(value, idp.getInitialDpArgExtension());
        } else if (parent.contains("naCarrierInformation")) {
            InitialDetectionPoint idp = pojo.getInvoke().getCapMessagePojo();
            Extension extension = ((LinkedList<Extension>) idp.getInitialDpArgExtension().getExtensions()).getLast();
            SelectionType selectionType = EnumProvider.of(
                    getVal(value, CIC_SELECTION_TYPE, ScenarioManager.get(InitialDetectionPoint.class, CIC_SELECTION_TYPE)),
                    SelectionType.class
            );
            extension.setCicSelectionType(selectionType);
        } else if (parent.contains("locationNumber")) {
            InitialDetectionPoint idp = pojo.getInvoke().getCapMessagePojo();
            InitialDetectionPoint.LocationInformation locationInformation = idp.getLocationInformation();
            if (locationInformation == null) {
                return;
            }

            locationInformation.setLocationNumber(parseLocationNumber(value, locationInformation.getLocationNumber()));
        } else if (contain(parent, CALLED_PARTY_BCDNUMBER)) {
            bcdParser.parseBCD(pojo.getInvoke().getCapMessagePojo(), value);
        } else if (contain(parent, "miscCallInfo")) {
            if (pojo.getInvoke().getCapMessagePojo() instanceof CAPMessageEventReportBCSMArg) {
                CAPMessageEventReportBCSMArg erb = pojo.getInvoke().getCapMessagePojo();
                BSCMEvent event = erb.getBscmEventList().getLast();
                MiscCallInfo callInfo = new MiscCallInfo();
                event.setMiscCallInfo(callInfo);
                callInfo.setMessageType(getVal(value, "messageType", NUMERIC_VALUE));

            }
        }
    }

    private InitialDetectionPoint.LocationNumber parseLocationNumber(
            String value, InitialDetectionPoint.LocationNumber locationNumber) {
        if (locationNumber == null) {
            locationNumber = new InitialDetectionPoint.LocationNumber();
            locationNumber.setPartyNumber(new PartyNumber());
        }
        partyNumberParser.parse(value, locationNumber.getPartyNumber());
        return locationNumber;
    }

    private String parseReleaseCause(String value) {
        return value.split(":")[1].trim();
    }

    private byte[] parseAcr(String parent) {
        //ApplyChargingReportArg: a00ba003810101a104800200b4
        String index = parent.split("\\s")[1];
        return Converter.hexToBytes(index);
    }


    private void parseMessageIDP(InitialDetectionPoint idp, String value, String parent) {
        Map<String, String> scenario = ScenarioManager.getInstance().getScenario(idp.getClass());
        if (contain(value, SERVICE_KEY)) {
            idp.setServiceKey(new ServiceKey());
            idp.getServiceKey().setStringBytes(getValue(SERVICE_KEY, value, scenario.get(SERVICE_KEY)));
        } else if (contain(value, IDP_CALLING_PARTY_NUMBER)) {
            idp.setCallingPartyNumber(new PartyNumber());
        } else if (contain(value, IDP_CALLED_PARTY_NUMBER)) {
            idp.setCalledPartyNumber(new PartyNumber());
        } else if (contain(value, CALLING_PARTYS_CATEGORY)) {
            idp.setCallingPartysCategory(new InitialDetectionPoint.CallingPartysCategory());
            idp.getCallingPartysCategory().setStringBytes(getValue(CALLING_PARTYS_CATEGORY, value, scenario.get(CALLING_PARTYS_CATEGORY)));
        } else if (contain(value, I_PSSPCAPABILITIES)) {
            idp.setIpsspCapabilities(new InitialDetectionPoint.IPSSPCapabilities());
            idp.getIpsspCapabilities().setStringBytes(getValue(I_PSSPCAPABILITIES, value, scenario.get(I_PSSPCAPABILITIES)));
        } else if (contain(value, LOCATION_NUMBER)) {
            idp.setLocationNumber(new InitialDetectionPoint.LocationNumber());
            idp.getLocationNumber().setStringBytes(getValue(LOCATION_NUMBER, value, scenario.get(LOCATION_NUMBER)));
        } else if (contain(value, HIGH_LAYER_COMPATIBILITY)) {
            idp.setHighLayerCompatibility(new InitialDetectionPoint.HighLayerCompatibility());
            idp.getHighLayerCompatibility().setStringBytes(getValue(HIGH_LAYER_COMPATIBILITY, value, scenario.get(HIGH_LAYER_COMPATIBILITY)));
        } else if (contain(value, EVENT_TYPE_BCSM)) {
            idp.setEventTypeBCSM(new InitialDetectionPoint.EventTypeBCSM());
            idp.getEventTypeBCSM().setStringBytes(getValue(EVENT_TYPE_BCSM, value, scenario.get(EVENT_TYPE_BCSM)));
        } else if (contain(value, IMSI)) {
            idp.setImsi(new InitialDetectionPoint.IMSI());
            idp.getImsi().setStringBytes(getValue(IMSI, value, scenario.get(IMSI)));
        } else if (contain(value, CALL_REFERENCE_NUMBER)) {
            idp.setCallReferenceNumber(new InitialDetectionPoint.CallReferenceNumber());
            idp.getCallReferenceNumber().setStringBytes(getValue(CALL_REFERENCE_NUMBER, value, scenario.get(CALL_REFERENCE_NUMBER)));
        } else if (contain(value, MSC_ADDRESS)) {
            idp.setMscAddress(new InitialDetectionPoint.MscAddress());
            idp.getMscAddress().setStringBytes(getValue(MSC_ADDRESS, value, scenario.get(MSC_ADDRESS)));
        } else if (contain(value, CALLED_PARTY_BCDNUMBER)) {
            idp.setCalledPartyBCDNumber(new InitialDetectionPoint.CalledPartyBCDNumber());
//            idp.getCalledPartyBCDNumber().setStringBytes(getValue(CALLED_PARTY_BCDNUMBER, value, scenario.get(CALLED_PARTY_BCDNUMBER)));

        } else if (contain(value, TIME_AND_TIMEZONE)) {
            idp.setTimeAndTimezone(new InitialDetectionPoint.TimeAndTimezone());
            idp.getTimeAndTimezone().setStringBytes(getValue(TIME_AND_TIMEZONE, value, scenario.get(TIME_AND_TIMEZONE)));
        } else if (contain(value, INITIAL_DP_ARG_EXTENSION)) {
            idp.setInitialDpArgExtension(new InitialDpArgExtension());
        } else if (contain(value, "callForwarding")) {
            idp.setCallForwardingSSPending(true);
        } else if (contain(value, "redirectingPartyID")) {
            RedirectionPartyId partyId = new RedirectionPartyId();
            partyId.setStringBytes(getValue("redirectingPartyID", value, HEX_PATTERN).substring(0, 4));
            idp.setRedirectingPartyId(partyId);
        } else if (contain(value, "Redirecting Number")) {
            RedirectionNumber redirectionNumber = new RedirectionNumber();
            redirectionNumber.setStringBytes(getValue("Redirecting Number", value, NUMERIC_VALUE));
            idp.setRedirectingNumber(redirectionNumber);
        } else if (contain(value, "redirectionInformation")) {
            RedirectionInformation redirectionInformation = new RedirectionInformation();
            redirectionInformation.setStringBytes(getValue("redirectionInformation", value, NUMERIC_VALUE));
            idp.setRedirectionInformation(redirectionInformation);
        } else if (contain(value, "Original Called Number")) {
            RedirectionNumber redirectionNumber = new RedirectionNumber();
            redirectionNumber.setStringBytes(getValue("Original Called Number", value, NUMERIC_VALUE));
            idp.setOriginalCalledNumber(redirectionNumber);
        } else if (contain(value, "originalCalledPartyID")) {
            RedirectionPartyId partyId = new RedirectionPartyId();
            partyId.setStringBytes(getValue("originalCalledPartyID", value, HEX_PATTERN).substring(0, 4));
            idp.setOriginalCalledPartyId(partyId);
        }
    }

    private void parseMessageRequestReportBCSMEventArg(CAPMessageRequestReportBCSMEventArg pojo, String value, String parent) {
//        Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getClass());
    }

    private void parseMessageApplyChargingArg(CAPMessageApplyChargingArg pojo, String value, String parent) {
        Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getClass());
        if (contain(value, A_CH_BILLING_CHARGING_CHARACTERISTICS)) {
            pojo.setaChBillingChargingCharacteristics(new CAPMessageApplyChargingArg.AChBillingChargingCharacteristics());
            pojo.getaChBillingChargingCharacteristics().setStringBytes(getValue(A_CH_BILLING_CHARGING_CHARACTERISTICS, value, scenario.get(A_CH_BILLING_CHARGING_CHARACTERISTICS)));
        }
    }

    private void parseMessageConnectArg(CAPMessageConnectArg pojo, String value, String parent) {
//        Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getClass());

    }

    private void parseLocationinformation(InitialDetectionPoint.LocationInformation pojo, String value, String parent) {
        Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getClass());
        if (contain(value, AGE_OF_LOCATION_INFORMATION)) {
            pojo.setAgeOfLocationInformation(new InitialDetectionPoint.LocationInformation.AgeOfLocationInformation());
            pojo.getAgeOfLocationInformation().setStringBytes(getValue(AGE_OF_LOCATION_INFORMATION, value, scenario.get(AGE_OF_LOCATION_INFORMATION)));
        } else if (contain(value, VLR_NUMBER)) {
            pojo.setVlrNumber(new InitialDetectionPoint.LocationInformation.VlrNumber());
            pojo.getVlrNumber().setStringBytes(getValue(VLR_NUMBER, value, scenario.get(VLR_NUMBER)));
        }
    }

    private void parseCellGlobalIdOrServiceAreaIdOrLAI(InitialDetectionPoint.LocationInformation.CellGlobalIdOrServiceAreaIdOrLAI pojo, String value, String parent) {
        Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getClass());
        if (contain(value, CELL_GLOBAL_ID_OR_SERVICE_AREA_ID_FIXED_LENGTH)) {
            pojo.setStringBytes(getValue(CELL_GLOBAL_ID_OR_SERVICE_AREA_ID_FIXED_LENGTH, value, scenario.get(CELL_GLOBAL_ID_OR_SERVICE_AREA_ID_FIXED_LENGTH)));
        }
    }

    private void parseExtBasicServiceCode(InitialDetectionPoint.ExtBasicServiceCode pojo, String value, String parent) {
        Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getClass());
        if (contain(value, EXT_TELESERVICE)) {
            pojo.setStringBytes(getValue(EXT_TELESERVICE, value, scenario.get(EXT_TELESERVICE)));
        }
    }

    private void parseBearerCapability(InitialDetectionPoint.BearerCapability pojo, String value, String parent) {
        Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getClass());
        if (contain(value, BEARER_CAP)) {
            pojo.setStringBytes(getValue(BEARER_CAP, value, scenario.get(BEARER_CAP)));
        }
    }

    private void parseBCSMEvent(CAPMessageRequestReportBCSMEventArg.BSCMEvent pojo, String value, String parent) {
        Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getClass());
        if (contain(value, EVENT_TYPE_BCSM)) {
            pojo.setEventType(EnumProvider.of(getValue(value, scenario, EVENT_TYPE_BCSM), EventType.class));
        } else if (contain(value, MONITOR_MODE)) {
            pojo.setMonitorMode(EnumProvider.of(getValue(value, scenario, MONITOR_MODE), MonitorMode.class));
        }
    }


    private void parseEventBCSMEvent(BSCMEvent event, String value) {
        Map<String, String> scenario = ScenarioManager.getInstance().getScenario(event.getClass());
        if (contain(value, EVENT_TYPE_BCSM)) {
            event.setEventType(EnumProvider.of(getValue(value, scenario, EVENT_TYPE_BCSM), EventType.class));
        } else if (contain(value, MONITOR_MODE)) {
            event.setMonitorMode(EnumProvider.of(getValue(value, scenario, MONITOR_MODE), MonitorMode.class));
        } else if (contain(value, EVENT_SPECIFIC_INFORMATION_BCSM)) {
            SpecificInformation specificInformation = new SpecificInformation();
            event.setSpecificInformation(specificInformation);
        }
    }

    private byte getValue(String value, Map<String, String> scenario, String propertyName) {
        String pattern = scenario.get(propertyName);
        return getVal(value, propertyName, pattern);
    }

    private byte getVal(String value, String propertyName, String pattern) {
        return Byte.parseByte(getValue(propertyName, value, pattern));
    }

    private void parseLegID(LegID pojo, String value, String parent) {
        Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getClass());
        if (contain(value, SENDING_SIDE_ID)) {
            pojo.setId(getVal(value, SENDING_SIDE_ID, scenario.get(SENDING_SIDE_ID)));
        } else if (contain(value, RECEIVING_SIDE_ID)) {
            pojo.setId(getVal(value, RECEIVING_SIDE_ID, scenario.get(RECEIVING_SIDE_ID)));
        }
    }

    private void parsePartyToCharge(CAPMessageApplyChargingArg.PartyToCharge pojo, String value, String parent) {
        Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getClass());
        if (contain(value, SENDING_SIDE_ID)) {
            pojo.setStringBytes(getValue(SENDING_SIDE_ID, value, scenario.get(SENDING_SIDE_ID)));
        }
    }

    private void parseDestinationRoutingAddress(CAPMessageConnectArg.DestinationRoutingAddress pojo, String value, String parent) {
        Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getClass());
        if (contain(value, CALLED_PARTY_NUMBER)) {
            pojo.setStringBytes(getValue(CALLED_PARTY_NUMBER, value, scenario.get(CALLED_PARTY_NUMBER)));
        }
    }
}
