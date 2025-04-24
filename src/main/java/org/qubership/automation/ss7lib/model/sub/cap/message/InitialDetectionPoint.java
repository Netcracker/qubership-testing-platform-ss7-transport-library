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

import org.qubership.automation.ss7lib.model.sub.Flag;
import org.qubership.automation.ss7lib.model.sub.Flags;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.InitialDpArgExtension;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.PartyNumber;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.ServiceKey;
import org.qubership.automation.ss7lib.model.sub.cap.param.AbstractParamPojo;
import org.qubership.automation.ss7lib.parse.parser.RedirectionNumber;

public class InitialDetectionPoint extends CAPMessagePojo {

    private ServiceKey serviceKey;
    private PartyNumber callingPartyNumber;
    private PartyNumber calledPartyNumber;
    private CallingPartysCategory callingPartysCategory;
    private IPSSPCapabilities ipsspCapabilities;
    private LocationNumber locationNumber;
    private HighLayerCompatibility highLayerCompatibility;
    private BearerCapability bearerCapability;
    private EventTypeBCSM eventTypeBCSM;
    private IMSI imsi;
    private LocationInformation locationInformation;
    private ExtBasicServiceCode extBasicServiceCode;
    private CallReferenceNumber callReferenceNumber;
    private MscAddress mscAddress;
    private CalledPartyBCDNumber calledPartyBCDNumber;
    private TimeAndTimezone timeAndTimezone;
    private InitialDpArgExtension initialDpArgExtension;
    private boolean callForwardingSSPending = false;
    private RedirectionPartyId redirectingPartyId;
    private RedirectionNumber redirectionNumber;
    private RedirectionPartyId originalCalledPartyId;
    private RedirectionNumber originalCalledNumber;
    private RedirectionInformation redirectionInformation;

    public ServiceKey getServiceKey() {
        return serviceKey;
    }

    public void setServiceKey(ServiceKey serviceKey) {
        this.serviceKey = serviceKey;
    }

    public PartyNumber getCalledPartyNumber() {
        return calledPartyNumber;
    }

    public void setCalledPartyNumber(PartyNumber calledPartyNumber) {
        this.calledPartyNumber = calledPartyNumber;
    }

    public PartyNumber getCallingPartyNumber() {
        return callingPartyNumber;
    }

    public void setCallingPartyNumber(PartyNumber callingPartyNumber) {
        this.callingPartyNumber = callingPartyNumber;
    }

    public CallingPartysCategory getCallingPartysCategory() {
        return callingPartysCategory;
    }

    public void setCallingPartysCategory(CallingPartysCategory callingPartysCategory) {
        this.callingPartysCategory = callingPartysCategory;
    }

    public IPSSPCapabilities getIpsspCapabilities() {
        return ipsspCapabilities;
    }

    public void setIpsspCapabilities(IPSSPCapabilities ipsspCapabilities) {
        this.ipsspCapabilities = ipsspCapabilities;
    }

    public LocationNumber getLocationNumber() {
        return locationNumber;
    }

    public void setLocationNumber(LocationNumber locationNumber) {
        this.locationNumber = locationNumber;
    }

    public HighLayerCompatibility getHighLayerCompatibility() {
        return highLayerCompatibility;
    }

    public void setHighLayerCompatibility(HighLayerCompatibility highLayerCompatibility) {
        this.highLayerCompatibility = highLayerCompatibility;
    }

    public BearerCapability getBearerCapability() {
        return bearerCapability;
    }

    public void setBearerCapability(BearerCapability bearerCapability) {
        this.bearerCapability = bearerCapability;
    }

    public EventTypeBCSM getEventTypeBCSM() {
        return eventTypeBCSM;
    }

    public void setEventTypeBCSM(EventTypeBCSM eventTypeBCSM) {
        this.eventTypeBCSM = eventTypeBCSM;
    }

    public IMSI getImsi() {
        return imsi;
    }

    public void setImsi(IMSI imsi) {
        this.imsi = imsi;
    }

    public LocationInformation getLocationInformation() {
        return locationInformation;
    }

    public void setLocationInformation(LocationInformation locationInformation) {
        this.locationInformation = locationInformation;
    }

    public boolean isCallForwardingSSPending() {
        return callForwardingSSPending;
    }

    public void setCallForwardingSSPending(boolean callForwardingSSPending) {
        this.callForwardingSSPending = callForwardingSSPending;
    }

    public ExtBasicServiceCode getExtBasicServiceCode() {
        return extBasicServiceCode;
    }

    public void setExtBasicServiceCode(ExtBasicServiceCode extBasicServiceCode) {
        this.extBasicServiceCode = extBasicServiceCode;
    }

    public CallReferenceNumber getCallReferenceNumber() {
        return callReferenceNumber;
    }

    public void setCallReferenceNumber(CallReferenceNumber callReferenceNumber) {
        this.callReferenceNumber = callReferenceNumber;
    }

    public MscAddress getMscAddress() {
        return mscAddress;
    }

    public void setMscAddress(MscAddress mscAddress) {
        this.mscAddress = mscAddress;
    }

    public CalledPartyBCDNumber getCalledPartyBCDNumber() {
        return calledPartyBCDNumber;
    }

    public void setCalledPartyBCDNumber(CalledPartyBCDNumber calledPartyBCDNumber) {
        this.calledPartyBCDNumber = calledPartyBCDNumber;
    }

    public TimeAndTimezone getTimeAndTimezone() {
        return timeAndTimezone;
    }

    public void setTimeAndTimezone(TimeAndTimezone timeAndTimezone) {
        this.timeAndTimezone = timeAndTimezone;
    }

    public InitialDpArgExtension getInitialDpArgExtension() {
        return initialDpArgExtension;
    }

    public void setInitialDpArgExtension(InitialDpArgExtension initialDpArgExtension) {
        this.initialDpArgExtension = initialDpArgExtension;
    }

    public void setRedirectingPartyId(RedirectionPartyId redirectingPartyId) {
        this.redirectingPartyId = redirectingPartyId;
    }

    public RedirectionPartyId getRedirectingPartyId() {
        return redirectingPartyId;
    }

    public void setRedirectingNumber(RedirectionNumber redirectionNumber) {
        this.redirectionNumber = redirectionNumber;
    }

    public RedirectionNumber getRedirectionNumber() {
        return redirectionNumber;
    }

    public RedirectionInformation getRedirectionInformation() {
        return redirectionInformation;
    }

    public RedirectionPartyId getOriginalCalledPartyId() {
        return originalCalledPartyId;
    }

    public void setOriginalCalledPartyId(RedirectionPartyId originalCalledPartyId) {
        this.originalCalledPartyId = originalCalledPartyId;
    }

    public RedirectionNumber getOriginalCalledNumber() {
        return originalCalledNumber;
    }

    public void setOriginalCalledNumber(RedirectionNumber originalCalledNumber) {
        this.originalCalledNumber = originalCalledNumber;
    }

    public void setRedirectionInformation(RedirectionInformation redirectionInformation) {
        this.redirectionInformation = redirectionInformation;
    }

    public static class CallingPartysCategory extends AbstractParamPojo implements Flag {

        private final transient byte startFlag = (byte) 0x85;

        public byte getFlag() {
            return startFlag;
        }

    }

    public static class IPSSPCapabilities extends AbstractParamPojo implements Flag {

        private final transient byte startFlag = (byte) 0x88;

        public byte getFlag() {
            return startFlag;
        }

    }

    public static class LocationNumber extends AbstractParamPojo implements Flag {

        private final transient byte startFlag = (byte) 0x8a;

        public byte getFlag() {
            return startFlag;
        }

        private PartyNumber partyNumber;

        public PartyNumber getPartyNumber() {
            return partyNumber;
        }

        public void setPartyNumber(PartyNumber partyNumber) {
            this.partyNumber = partyNumber;
        }

        @Override
        public boolean isHEX() {
            return true;
        }

    }

    public static class HighLayerCompatibility extends AbstractParamPojo implements Flag {

        private final transient byte startFlag = (byte) 0x97;

        public byte getFlag() {
            return startFlag;
        }

        @Override
        public boolean isHEX() {
            return true;
        }

    }

    public static class BearerCapability extends AbstractParamPojo implements Flags {

        private final transient byte[] startFlag = {(byte) 0xbb, (byte) 0x05, (byte) 0x80};

        public byte[] getFlags() {
            return startFlag;
        }

        @Override
        public boolean isHEX() {
            return true;
        }

    }

    public static class EventTypeBCSM extends AbstractParamPojo implements Flag {

        private final transient byte startFlag = (byte) 0x9c;

        public byte getFlag() {
            return startFlag;
        }

    }

    public static class IMSI extends AbstractParamPojo implements Flags {

        private final transient byte[] startFlag = {(byte) 0x9f, (byte) 0x32};

        public byte[] getFlags() {
            return startFlag;
        }

        @Override
        public boolean isHEX() {
            return true;
        }

    }

    public static class LocationInformation extends AbstractParamPojo implements Flags {
        private final transient byte[] startFlag = {(byte) 0xbf, (byte) 0x34};
        private AgeOfLocationInformation ageOfLocationInformation;
        private VlrNumber vlrNumber;
        private LocationNumber locationNumber;

        public LocationNumber getLocationNumber() {
            return locationNumber;
        }

        public void setLocationNumber(LocationNumber locationNumber) {
            this.locationNumber = locationNumber;
        }

        private CellGlobalIdOrServiceAreaIdOrLAI cellGlobalIdOrServiceAreaIdOrLAI;


        public byte[] getFlags() {
            return startFlag;
        }

        public AgeOfLocationInformation getAgeOfLocationInformation() {
            return ageOfLocationInformation;
        }

        public void setAgeOfLocationInformation(AgeOfLocationInformation ageOfLocationInformation) {
            this.ageOfLocationInformation = ageOfLocationInformation;
        }

        public VlrNumber getVlrNumber() {
            return vlrNumber;
        }

        public void setVlrNumber(VlrNumber vlrNumber) {
            this.vlrNumber = vlrNumber;
        }

        public CellGlobalIdOrServiceAreaIdOrLAI getCellGlobalIdOrServiceAreaIdOrLAI() {
            return cellGlobalIdOrServiceAreaIdOrLAI;
        }

        public void setCellGlobalIdOrServiceAreaIdOrLAI(CellGlobalIdOrServiceAreaIdOrLAI cellGlobalIdOrServiceAreaIdOrLAI) {
            this.cellGlobalIdOrServiceAreaIdOrLAI = cellGlobalIdOrServiceAreaIdOrLAI;
        }

        public static class AgeOfLocationInformation extends AbstractParamPojo implements Flag {

            private final transient byte startFlag = (byte) 0x02;

            public byte getFlag() {
                return startFlag;
            }

        }

        public static class VlrNumber extends AbstractParamPojo implements Flag {

            private final transient byte startFlag = (byte) 0x81;

            public byte getFlag() {
                return startFlag;
            }

            @Override
            public boolean isHEX() {
                return true;
            }

        }

        public static class CellGlobalIdOrServiceAreaIdOrLAI extends AbstractParamPojo implements Flags {

            private final transient  byte[] startFlag = {(byte) 0xa3, (byte) 0x09, (byte) 0x80};

            public byte[] getFlags() {
                return startFlag;
            }

            @Override
            public boolean isHEX() {
                return true;
            }

        }

    }

    public static class ExtBasicServiceCode extends AbstractParamPojo implements Flags {

        private final transient  byte[] startFlag = {(byte) 0xbf, (byte) 0x35, (byte) 0x03, (byte) 0x83};

        public byte[] getFlags() {
            return startFlag;
        }

    }

    public static class CallReferenceNumber extends AbstractParamPojo implements Flags {

        private final transient  byte[] startFlag = {(byte) 0x9f, (byte) 0x36};

        public byte[] getFlags() {
            return startFlag;
        }

        @Override
        public boolean isHEX() {
            return true;
        }

    }

    public static class MscAddress extends AbstractParamPojo implements Flags {

        private final transient  byte[] startFlag = {(byte) 0x9f, (byte) 0x37};

        public byte[] getFlags() {
            return startFlag;
        }

        @Override
        public boolean isHEX() {
            return true;
        }

    }

    public static class CalledPartyBCDNumber extends AbstractParamPojo implements Flags {

        private final transient  byte[] startFlag = {(byte) 0x9f, (byte) 0x38};

        @Override
        public boolean isHEX() {
            return true;
        }

        public byte[] getFlags() {
            return startFlag;
        }

    }

    public static class TimeAndTimezone extends AbstractParamPojo implements Flags {

        private final transient  byte[] startFlag = {(byte) 0x9f, (byte) 0x39};

        public byte[] getFlags() {
            return startFlag;
        }

        @Override
        public boolean isHEX() {
            return true;
        }

    }
}
