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

package org.qubership.automation.ss7lib.model;

public interface SS7PropertiesPojo {

    String MTP_3_USER_ADAPTATION_LAYER = "MTP 3 User Adaptation Layer";
    String SIGNALLING_CONNECTION_CONTROL_PART = "Signalling Connection Control Part";
    String TRANSACTION_CAPABILITIES_APPLICATION_PART = "Transaction Capabilities Application Part";
    String CAMEL = "Camel";


    String VERSION = "Version";
    String RESERVED = "Reserved";
    String MESSAGE_LENGTH = "Message length";
    String MESSAGE_CLASS = "Message class";
    String MESSAGE_TYPE = "Message type";
    String NETWORK_APPEARANCE = "Network appearance";
    String PARAMETER_TAG = "Parameter tag";
    String PARAMETER_LENGTH = "Parameter length";
    String ROUTING_CONTEXT = "Routing context";
    String PROTOCOL_DATA = "Protocol data";
    String OPC = "OPC";
    String DPC = "DPC";
    String SI = "SI";
    String NI = "NI";
    String MP = "MP";
    String SLS = "SLS";
    String PADDING = "Padding";
    String ANSI_STANDARD = "ANSI_STANDARD";
    String ITU_STANDARD = "ITU_STANDARD";

    String CLASS = "Class";
    String MESSAGE_HANDLING = "Message handling";
    String POINTER_TO_FIRST_MANDATORY_VARIABLE_PARAMETER = "Pointer to first Mandatory Variable parameter";
    String POINTER_TO_SECOND_MANDATORY_VARIABLE_PARAMETER = "Pointer to second Mandatory Variable parameter";
    String POINTER_TO_THIRD_MANDATORY_VARIABLE_PARAMETER = "Pointer to third Mandatory Variable parameter";
    String CALLED_PARTY_ADDRESS = "Called Party address";
    String CALLING_PARTY_ADDRESS = "Calling Party address";
    String ADDRESS_INDICATOR = "Address Indicator";
    String NATIONAL_INDICATOR = "National Indicator";
    String RESERVED_FOR_NATIONAL_USE = "Reserved for national use";
    String ROUTING_INDICATOR = "Routing Indicator";
    String GLOBAL_TITLE_INDICATOR = "Global Title Indicator";
    String POINT_CODE_INDICATOR = "Point Code Indicator";
    String SUB_SYSTEM_NUMBER_INDICATOR = "SubSystem Number Indicator";
    String SUB_SYSTEM_NUMBER = "SubSystem Number";
    String GLOBAL_TITLE = "Global Title";
    String TRANSLATION_TYPE = "Translation Type";
    String CALLED_PARTY_DIGITS = "Called Party Digits";
    String CALLING_PARTY_DIGITS = "Calling Party Digits";
    String CALL_PARTY_DIGITS = "Call Party Digits";

    String BEGIN = "begin";
    String CONTINUE = "continue";
    String END = "end";
    String SOURCE_TRANSACTION_ID = "Source Transaction ID";
    String DESTINATION_TRANSACTION_ID = "Destination Transaction ID";
    String TRANSACTION_ID = "Transaction ID";
    String OID = "oid";
    String DIALOGUE_RESPONSE = "dialogueResponse";
    String DIALOGUE_REQUEST = "dialogueRequest";
    String DIALOGUE = "dialogue";
    String COMPONENTS = "components";
    String TID = "tid";
    String PROTOCOL_VERSION = "protocol-version:";
    String APPLICATION_CONTEXT_NAME = "application-context-name";
    String RESULT = "result";
    String RESULT_SOURCE_DIAGNOSTIC = "result-source-diagnostic";
    String DIALOGUE_SERVICE_USER = "dialogue-service-user";

    String INVOKE = "invoke";
    String INVOKE_ID = "invokeId";
    String PRESENT = "present";
    String OPCODE = "opcode";
    String LOCAL = "local";
    String INITIAL_DP_ARG = "InitialDPArg";
    String SERVICE_KEY = "serviceKey";
    String IDP_CALLED_PARTY_NUMBER = "calledPartyNumber";
    String IDP_CALLED_PARTY_NUMBER_VALUE = "Called Party Number";
    String IDP_CALLING_PARTY_NUMBER = "callingPartyNumber";
    String IDP_CALLING_PARTY_NUMBER_VALUE = "Calling Party Number";
    String IDP_LOCATION_NUMBER = "Address digits";
    String CALLING_PARTYS_CATEGORY = "callingPartysCategory";
    String I_PSSPCAPABILITIES = "iPSSPCapabilities";
    String LOCATION_NUMBER = "locationNumber";
    String HIGH_LAYER_COMPATIBILITY = "highLayerCompatibility";
    String BEARER_CAPABILITY = "bearerCapability";
    String BEARER_CAP = "bearerCap";
    String EVENT_TYPE_BCSM = "eventTypeBCSM";
    String IMSI = "IMSI";
    String LOCATION_INFORMATION = "locationInformation";
    String AGE_OF_LOCATION_INFORMATION = "ageOfLocationInformation";
    String VLR_NUMBER = "vlr-number";
    String CELL_GLOBAL_ID_OR_SERVICE_AREA_ID_OR_LAI = "cellGlobalIdOrServiceAreaIdOrLAI";
    String CELL_GLOBAL_ID_OR_SERVICE_AREA_ID_FIXED_LENGTH = "cellGlobalIdOrServiceAreaIdFixedLength";
    String EXT_BASIC_SERVICE_CODE = "ext-basicServiceCode";
    String EXT_TELESERVICE = "ext-Teleservice";
    String CALL_REFERENCE_NUMBER = "callReferenceNumber";
    String MSC_ADDRESS = "mscAddress";
    String CALLED_PARTY_BCDNUMBER = "calledPartyBCDNumber";
    String TIME_AND_TIMEZONE = "timeAndTimezone";
    String REQUEST_REPORT_BCSMEVENT_ARG = "RequestReportBCSMEventArg";
    String BCSM_EVENTS = "bcsmEvents";
    String BCSMEVENT = "BCSMEvent";
    //                String EVENT_TYPE_BCSM1 = "eventTypeBCSM";
    String MONITOR_MODE = "monitorMode";
    String EVENT_SPECIFIC_INFORMATION_BCSM = "eventSpecificInformationBCSM";
    String LEG_ID = "legID";
    String SENDING_SIDE_ID = "sendingSideID";
    String RECEIVING_SIDE_ID = "receivingSideID";
    String CALL_RESULT = "CallResult";
    String RELEASE_CAUSE = "releaseCause";
    String APPLY_CHARGING_ARG = "ApplyChargingArg";
    String A_CH_BILLING_CHARGING_CHARACTERISTICS = "aChBillingChargingCharacteristics";
    String PARTY_TO_CHARGE = "partyToCharge";
    //            String SENDING_SIDE_ID1 = "sendingSideID";
    String CONNECT_ARG = "ConnectArg";
    String DESTINATION_ROUTING_ADDRESS = "destinationRoutingAddress";
    String CALLED_PARTY_NUMBER = "CalledPartyNumber";
    String EVENT_REPORT_BCSM_ARG = "EventReportBCSMArg";
    String APPLY_CHARGING_REPORT_ARG = "ApplyChargingReportArg";
    String INITIAL_DP_ARG_EXTENSION = "initialDPArgExtension";
    String CIC_SELECTION_TYPE = "naCICSelectionType";


    int M3UA_MSG_CLASS_IDX = 2;
    int M3UA_MSG_TYPE_IDX = 3;
    int M3UA_LENGTH_IDX = 4;

    int M3UA_HEADER_LEN = 8;

    byte M3UA_MGMT_MSG = 0;
    byte M3UA_TRANSFER_MSG = 1;
    byte M3UA_SSNM_MSG = 2;
    byte M3UA_ASPSM_MSG = 3;
    byte M3UA_ASPTM_MSG = 4;

    byte M3UA_MGMT_ERROR = 0;
    byte M3UA_MGMT_NOTIFY = 1;

    byte M3UA_TRANSFER_DATA = 1;

    byte M3UA_SSNM_DAVA = 2;
    byte M3UA_SSNM_DAUD = 3;

    byte M3UA_ASPSM_ASPUP = 1;
    byte M3UA_ASPSM_ASPDN = 2;
    byte M3UA_ASPSM_BEAT = 3;
    byte M3UA_ASPSM_ASPUP_ACK = 4;
    byte M3UA_ASPSM_BEAT_ACK = 6;

    byte M3UA_ASPTM_ASPAC = 1;
    byte M3UA_ASPTM_ASPIA = 2;
    byte M3UA_ASPTM_ASPAC_ACK = 3;
    byte M3UA_ASPTM_ASPIA_ACK = 4;

    short M3UA_TAG_INFO_STRING = 4;
    short M3UA_TAG_ROUTING_CONTEXT = 6;
    short M3UA_TAG_TRAFFIC_MODE_TYPE = 11;
    short M3UA_TAG_AFFECTED_PC = 0x012;
    short M3UA_TAG_NETWORK_APPEARANCE = 0x200;
    short M3UA_TAG_PROTOCOL_DATA = 528;
}
