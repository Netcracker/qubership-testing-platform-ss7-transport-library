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

package org.qubership.automation.ss7lib.encode.cap;

import org.qubership.automation.ss7lib.model.CapMessage;
import org.qubership.automation.ss7lib.model.sub.cap.CAPInvokeIDPojo;
import org.qubership.automation.ss7lib.model.sub.cap.CAPOpCodePojo;
import org.qubership.automation.ss7lib.model.sub.cap.CapInvoke;
import org.qubership.automation.ss7lib.model.sub.cap.message.*;

import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageApplyChargingArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageConnectArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageRequestReportBCSMEventArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.InitialDetectionPoint;
import org.qubership.automation.ss7lib.model.sub.cap.message.LegID;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.PartyNumber;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.ServiceKey;
import org.qubership.automation.ss7lib.model.type.EventType;
import org.qubership.automation.ss7lib.model.type.MonitorMode;
import org.junit.Test;

import static org.qubership.automation.ss7lib.encode.EncoderUtils.reverse;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CAPEncoderTest {

    //    @Test/*TODO Actualise the test*/
    @Deprecated
    public void testEncodeIDP() {
        CapMessage pojo = new CapMessage();
        CapInvoke capInvokePojo = new CapInvoke();
        CAPInvokeIDPojo capInvokeIDPojo = new CAPInvokeIDPojo();
        capInvokeIDPojo.setStringBytes("0");
        capInvokePojo.setInvokeID(capInvokeIDPojo);
        CAPOpCodePojo capOpCodePojo = new CAPOpCodePojo();
        capOpCodePojo.setStringBytes("0");
        capInvokePojo.setOpCode(capOpCodePojo);
        InitialDetectionPoint messageIDP = new InitialDetectionPoint();
        ServiceKey serviceKey = new ServiceKey();
        serviceKey.setStringBytes("3");
        messageIDP.setServiceKey(serviceKey);
        PartyNumber callingPartyNumber = new PartyNumber();
        callingPartyNumber.setStringBytes("8415514124045503");
        messageIDP.setCallingPartyNumber(callingPartyNumber);
        InitialDetectionPoint.CallingPartysCategory callingPartysCategory = new InitialDetectionPoint.CallingPartysCategory();
        callingPartysCategory.setStringBytes("10");
        messageIDP.setCallingPartysCategory(callingPartysCategory);
        InitialDetectionPoint.IPSSPCapabilities ipsspCapabilities = new InitialDetectionPoint.IPSSPCapabilities();
        ipsspCapabilities.setStringBytes("00");
        messageIDP.setIpsspCapabilities(ipsspCapabilities);
        InitialDetectionPoint.LocationNumber locationNumber = new InitialDetectionPoint.LocationNumber();
        locationNumber.setStringBytes("03131534089364");
        messageIDP.setLocationNumber(locationNumber);
        InitialDetectionPoint.HighLayerCompatibility highLayerCompatibility = new InitialDetectionPoint.HighLayerCompatibility();
        highLayerCompatibility.setStringBytes("9181");
        messageIDP.setHighLayerCompatibility(highLayerCompatibility);
        InitialDetectionPoint.BearerCapability bearerCapability = new InitialDetectionPoint.BearerCapability();
        bearerCapability.setStringBytes("8090a3");
        messageIDP.setBearerCapability(bearerCapability);
        InitialDetectionPoint.EventTypeBCSM eventTypeBCSM = new InitialDetectionPoint.EventTypeBCSM();
        eventTypeBCSM.setStringBytes("2");
        messageIDP.setEventTypeBCSM(eventTypeBCSM);
        InitialDetectionPoint.IMSI imsi = new InitialDetectionPoint.IMSI();
        imsi.setStringBytes("302510200239586");
        messageIDP.setImsi(imsi);
        InitialDetectionPoint.LocationInformation locationInformation = new InitialDetectionPoint.LocationInformation();
        InitialDetectionPoint.LocationInformation.AgeOfLocationInformation ageOfLocationInformation = new InitialDetectionPoint.LocationInformation.AgeOfLocationInformation();
        ageOfLocationInformation.setStringBytes("0");
        locationInformation.setAgeOfLocationInformation(ageOfLocationInformation);
        InitialDetectionPoint.LocationInformation.VlrNumber vlrNumber = new InitialDetectionPoint.LocationInformation.VlrNumber();
        vlrNumber.setStringBytes("915141240415f4");
        locationInformation.setVlrNumber(vlrNumber);
        InitialDetectionPoint.LocationInformation.CellGlobalIdOrServiceAreaIdOrLAI cellGlobalIdOrServiceAreaIdOrLAI = new InitialDetectionPoint.LocationInformation.CellGlobalIdOrServiceAreaIdOrLAI();
        cellGlobalIdOrServiceAreaIdOrLAI.setStringBytes("0302151b58369c");
        locationInformation.setCellGlobalIdOrServiceAreaIdOrLAI(cellGlobalIdOrServiceAreaIdOrLAI);
        messageIDP.setLocationInformation(locationInformation);
        InitialDetectionPoint.ExtBasicServiceCode extBasicServiceCode = new InitialDetectionPoint.ExtBasicServiceCode();
        extBasicServiceCode.setStringBytes("17");
        messageIDP.setExtBasicServiceCode(extBasicServiceCode);
        InitialDetectionPoint.CallReferenceNumber callReferenceNumber = new InitialDetectionPoint.CallReferenceNumber();
        callReferenceNumber.setStringBytes("400441310b4200");
        messageIDP.setCallReferenceNumber(callReferenceNumber);
        InitialDetectionPoint.MscAddress mscAddress = new InitialDetectionPoint.MscAddress();
        mscAddress.setStringBytes("915141240415f4");
        messageIDP.setMscAddress(mscAddress);
        InitialDetectionPoint.CalledPartyBCDNumber calledPartyBCDNumber = new InitialDetectionPoint.CalledPartyBCDNumber();
        calledPartyBCDNumber.setStringBytes("8114f1");
        messageIDP.setCalledPartyBCDNumber(calledPartyBCDNumber);
        InitialDetectionPoint.TimeAndTimezone timeAndTimezone = new InitialDetectionPoint.TimeAndTimezone();
        timeAndTimezone.setStringBytes("027130107074020a");
        messageIDP.setTimeAndTimezone(timeAndTimezone);
        capInvokePojo.setCapMessagePojo(messageIDP);
        pojo.setInvoke(capInvokePojo);

        byte[] cap = new CAPEncoder().encode(pojo);

        byte[] correctMessage = new byte[]{(byte) 0xa1, (byte) 0x81, (byte) 0x82, 0x02, 0x01, 0x00, 0x02, 0x01, 0x00, 0x30, 0x7a, (byte) 0x80, 0x01, 0x03, (byte) 0x83, 0x08,
                (byte) 0x84, 0x15, 0x51, 0x41, 0x24, 0x04, 0x55, 0x03, (byte) 0x85, 0x01, 0x0a, (byte) 0x88, 0x01, 0x00, (byte) 0x8a, 0x07,
                0x03, 0x13, 0x15, 0x34, 0x08, (byte) 0x93, 0x64, (byte) 0x97, 0x02, (byte) 0x91, (byte) 0x81, (byte) 0xbb, 0x05, (byte) 0x80, 0x03, (byte) 0x80,
                (byte) 0x90, (byte) 0xa3, (byte) 0x9c, 0x01, 0x02, (byte) 0x9f, 0x32, 0x08, 0x03, 0x52, 0x01, 0x02, 0x20, (byte) 0x93, (byte) 0x85, (byte) 0xf6,
                (byte) 0xbf, 0x34, 0x17, 0x02, 0x01, 0x00, (byte) 0x81, 0x07, (byte) 0x91, 0x51, 0x41, 0x24, 0x04, 0x15, (byte) 0xf4, (byte) 0xa3,
                0x09, (byte) 0x80, 0x07, 0x03, 0x02, 0x15, 0x1b, 0x58, 0x36, (byte) 0x9c, (byte) 0xbf, 0x35, 0x03, (byte) 0x83, 0x01, 0x11,
                (byte) 0x9f, 0x36, 0x07, 0x40, 0x04, 0x41, 0x31, 0x0b, 0x42, 0x00, (byte) 0x9f, 0x37, 0x07, (byte) 0x91, 0x51, 0x41,
                0x24, 0x04, 0x15, (byte) 0xf4, (byte) 0x9f, 0x38, 0x03, (byte) 0x81, 0x14, (byte) 0xf1, (byte) 0x9f, 0x39, 0x08, 0x02, 0x71, 0x30,
                0x10, 0x70, 0x74, 0x02, 0x0a};

        assertArrayEquals(correctMessage, cap);

    }

    //    @Test/*TODO Actualise the test*/
    @Deprecated
    public void testEncodeRRB() {
        CapMessage pojo = new CapMessage();
        CapInvoke capInvokePojo = new CapInvoke();
        CAPInvokeIDPojo capInvokeIDPojo = new CAPInvokeIDPojo();
        capInvokeIDPojo.setStringBytes("1");
        capInvokePojo.setInvokeID(capInvokeIDPojo);
        CAPOpCodePojo capOpCodePojo = new CAPOpCodePojo();
        capOpCodePojo.setStringBytes("23");
        capInvokePojo.setOpCode(capOpCodePojo);
        CAPMessageRequestReportBCSMEventArg messageRRB = new CAPMessageRequestReportBCSMEventArg();
        CAPMessageRequestReportBCSMEventArg.BSCMEvent bscmEvent0 = new CAPMessageRequestReportBCSMEventArg.BSCMEvent();
        bscmEvent0.setEventType(EventType.O_ANSWER);
        bscmEvent0.setMonitorMode(MonitorMode.NOTIFY_AND_CONTINUE);
        messageRRB.getBscmEventList().add(bscmEvent0);
        CAPMessageRequestReportBCSMEventArg.BSCMEvent bscmEvent1 = new CAPMessageRequestReportBCSMEventArg.BSCMEvent();
        bscmEvent1.setEventType(EventType.O_DISCONNECT);
        bscmEvent1.setMonitorMode(MonitorMode.INTERRUPTED);
        LegID legID1 = new LegID();
        legID1.setId((byte) 1);
        bscmEvent1.setLegID(legID1);
        messageRRB.getBscmEventList().add(bscmEvent1);
        CAPMessageRequestReportBCSMEventArg.BSCMEvent bscmEvent2 = new CAPMessageRequestReportBCSMEventArg.BSCMEvent();
        bscmEvent2.setEventType(EventType.O_DISCONNECT);
        bscmEvent2.setMonitorMode(MonitorMode.INTERRUPTED);
        LegID legID2 = new LegID();
        legID2.setId((byte) 2);
        bscmEvent2.setLegID(legID2);
        messageRRB.getBscmEventList().add(bscmEvent2);
        CAPMessageRequestReportBCSMEventArg.BSCMEvent bscmEvent3 = new CAPMessageRequestReportBCSMEventArg.BSCMEvent();
        bscmEvent3.setEventType(EventType.O_ROUTE_SELECT_FAILURE);
        bscmEvent3.setMonitorMode(MonitorMode.NOTIFY_AND_CONTINUE);
        messageRRB.getBscmEventList().add(bscmEvent3);
        CAPMessageRequestReportBCSMEventArg.BSCMEvent bscmEvent4 = new CAPMessageRequestReportBCSMEventArg.BSCMEvent();
        bscmEvent4.setEventType(EventType.O_CALL_PARTY_BUSY);
        bscmEvent4.setMonitorMode(MonitorMode.NOTIFY_AND_CONTINUE);
        messageRRB.getBscmEventList().add(bscmEvent4);
        CAPMessageRequestReportBCSMEventArg.BSCMEvent bscmEvent5 = new CAPMessageRequestReportBCSMEventArg.BSCMEvent();
        bscmEvent5.setEventType(EventType.O_ABANDON);
        bscmEvent5.setMonitorMode(MonitorMode.NOTIFY_AND_CONTINUE);
        messageRRB.getBscmEventList().add(bscmEvent5);
        capInvokePojo.setCapMessagePojo(messageRRB);
        pojo.setInvoke(capInvokePojo);

        byte[] cap = new CAPEncoder().encode(pojo);
        byte[] correctMessage =
                new byte[]{(byte) 0xa1, 0x44, 0x02, 0x01, 0x01, 0x02, 0x01, 0x17, 0x30, 0x3c, (byte) 0xa0, 0x3a, 0x30, 0x06, (byte) 0x80, 0x01,
                        0x07, (byte) 0x81, 0x01, 0x01, 0x30, 0x0b, (byte) 0x80, 0x01, 0x09, (byte) 0x81, 0x01, 0x00, (byte) 0xa2, 0x03, (byte) 0x80, 0x01,
                        0x01, 0x30, 0x0b, (byte) 0x80, 0x01, 0x09, (byte) 0x81, 0x01, 0x00, (byte) 0xa2, 0x03, (byte) 0x80, 0x01, 0x02, 0x30, 0x06,
                        (byte) 0x80, 0x01, 0x04, (byte) 0x81, 0x01, 0x01, 0x30, 0x06, (byte) 0x80, 0x01, 0x05, (byte) 0x81, 0x01, 0x01, 0x30, 0x06,
                        (byte) 0x80, 0x01, 0x0a, (byte) 0x81, 0x01, 0x01};
        assertArrayEquals(correctMessage, cap);
    }


    @Test
    public void testEncodeAC() {
        CapMessage pojo = new CapMessage();
        CapInvoke capInvokePojo = new CapInvoke();
        CAPInvokeIDPojo capInvokeIDPojo = new CAPInvokeIDPojo();
        capInvokeIDPojo.setStringBytes("2");
        capInvokePojo.setInvokeID(capInvokeIDPojo);
        CAPOpCodePojo capOpCodePojo = new CAPOpCodePojo();
        capOpCodePojo.setStringBytes("35");
        capInvokePojo.setOpCode(capOpCodePojo);
        CAPMessageApplyChargingArg messageAC = new CAPMessageApplyChargingArg();
        CAPMessageApplyChargingArg.AChBillingChargingCharacteristics aChBillingChargingCharacteristics = new CAPMessageApplyChargingArg.AChBillingChargingCharacteristics();
        aChBillingChargingCharacteristics.setStringBytes("a00480020708");
        messageAC.setaChBillingChargingCharacteristics(aChBillingChargingCharacteristics);
        CAPMessageApplyChargingArg.PartyToCharge partyToCharge = new CAPMessageApplyChargingArg.PartyToCharge();
        partyToCharge.setStringBytes("1");
        messageAC.setPartyToCharge(partyToCharge);
        capInvokePojo.setCapMessagePojo(messageAC);
        pojo.setInvoke(capInvokePojo);

        byte[] cap = new CAPEncoder().encode(pojo);

        byte[] correctMessage = new byte[]{(byte) 0xa1, 0x15, 0x02, 0x01, 0x02, 0x02, 0x01, 0x23, 0x30, 0x0d, (byte) 0x80, 0x06, (byte) 0xa0, 0x04, (byte) 0x80, 0x02,
                0x07, 0x08, (byte) 0xa2, 0x03, (byte) 0x80, 0x01, 0x01};

        assertArrayEquals(correctMessage, cap);

    }


    @Test
    public void testEncodeConnect() {
        CapMessage pojo = new CapMessage();
        CapInvoke capInvokePojo = new CapInvoke();
        CAPInvokeIDPojo capInvokeIDPojo = new CAPInvokeIDPojo();
        capInvokeIDPojo.setStringBytes("3");
        capInvokePojo.setInvokeID(capInvokeIDPojo);
        CAPOpCodePojo capOpCodePojo = new CAPOpCodePojo();
        capOpCodePojo.setStringBytes("20");
        capInvokePojo.setOpCode(capOpCodePojo);
        CAPMessageConnectArg messageConnect = new CAPMessageConnectArg();

        CAPMessageConnectArg.DestinationRoutingAddress destinationRoutingAddress = new CAPMessageConnectArg.DestinationRoutingAddress();
        destinationRoutingAddress.setStringBytes("8490514183403309");
        messageConnect.getDestinationRoutingAddressList().add(destinationRoutingAddress);

        capInvokePojo.setCapMessagePojo(messageConnect);
        pojo.setInvoke(capInvokePojo);

        byte[] cap = new CAPEncoder().encode(pojo);

        byte[] correctMessage = new byte[]{(byte) 0xa1, 0x14, 0x02, 0x01, 0x03, 0x02, 0x01, 0x14, 0x30,
                0x0c, (byte) 0xa0, 0x0a, 0x04, 0x08, (byte) 0x84, (byte) 0x90,
                0x51, 0x41, (byte) 0x83, 0x40, 0x33, 0x09};

        assertArrayEquals(correctMessage, cap);
    }

    @Test
    public void convertIMSI() {
        String stringBytes = "302510200239586";
        String s = reverse(stringBytes);
        assertEquals("03520102209385f6", s);
    }


}
