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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public static class CallingPartysCategory extends AbstractParamPojo implements Flag {

        /**
         * Start Flag byte, always = (byte) 0x85.
         */
        private final transient byte startFlag = (byte) 0x85;

        /**
         * Get flags byte.
         *
         * @return byte value of startFlag field.
         */
        public byte getFlag() {
            return startFlag;
        }

    }

    public static class IPSSPCapabilities extends AbstractParamPojo implements Flag {

        /**
         * Start Flag byte, always = (byte) 0x88.
         */
        private final transient byte startFlag = (byte) 0x88;

        /**
         * Get flags byte.
         *
         * @return byte value of startFlag field.
         */
        public byte getFlag() {
            return startFlag;
        }

    }

    public static class LocationNumber extends AbstractParamPojo implements Flag {

        /**
         * Start Flag byte, always = (byte) 0x8a.
         */
        private final transient byte startFlag = (byte) 0x8a;

        /**
         * Get flags byte.
         *
         * @return byte value of startFlag field.
         */
        public byte getFlag() {
            return startFlag;
        }

        /**
         * PartyNumber object field.
         */
        @Setter
        @Getter
        private PartyNumber partyNumber;

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

    public static class HighLayerCompatibility extends AbstractParamPojo implements Flag {

        /**
         * Start Flag byte, always = (byte) 0x97.
         */
        private final transient byte startFlag = (byte) 0x97;

        /**
         * Get flags byte.
         *
         * @return byte value of startFlag field.
         */
        public byte getFlag() {
            return startFlag;
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

    public static class BearerCapability extends AbstractParamPojo implements Flags {

        /**
         * Start Flag byte[], always = {(byte) 0xbb, (byte) 0x05, (byte) 0x80}.
         */
        private final transient byte[] startFlag = {(byte) 0xbb, (byte) 0x05, (byte) 0x80};

        /**
         * Get flags byte[].
         *
         * @return byte[] value of startFlag field.
         */
        public byte[] getFlags() {
            return startFlag;
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

    public static class EventTypeBCSM extends AbstractParamPojo implements Flag {

        /**
         * Start Flag byte, always = (byte) 0x9c.
         */
        private final transient byte startFlag = (byte) 0x9c;

        /**
         * Get flags byte.
         *
         * @return byte value of startFlag field.
         */
        public byte getFlag() {
            return startFlag;
        }

    }

    public static class IMSI extends AbstractParamPojo implements Flags {

        /**
         * Start Flag byte[], always = {(byte) 0x9f, (byte) 0x32}.
         */
        private final transient byte[] startFlag = {(byte) 0x9f, (byte) 0x32};

        /**
         * Get flags byte[].
         *
         * @return byte[] value of startFlag field.
         */
        public byte[] getFlags() {
            return startFlag;
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

    public static class LocationInformation extends AbstractParamPojo implements Flags {

        /**
         * Start Flag byte[], always = {(byte) 0xbf, (byte) 0x34}.
         */
        private final transient byte[] startFlag = {(byte) 0xbf, (byte) 0x34};

        /**
         * AgeOfLocationInformation object field.
         */
        @Setter
        @Getter
        private AgeOfLocationInformation ageOfLocationInformation;

        /**
         * VlrNumber object field.
         */
        @Setter
        @Getter
        private VlrNumber vlrNumber;

        /**
         * LocationNumber object field.
         */
        @Setter
        @Getter
        private LocationNumber locationNumber;

        /**
         * CellGlobalIdOrServiceAreaIdOrLAI object field.
         */
        @Setter
        @Getter
        private CellGlobalIdOrServiceAreaIdOrLAI cellGlobalIdOrServiceAreaIdOrLAI;

        /**
         * Get flags byte[].
         *
         * @return byte[] value of startFlag field.
         */
        public byte[] getFlags() {
            return startFlag;
        }

        public static class AgeOfLocationInformation extends AbstractParamPojo implements Flag {

            private final transient byte startFlag = (byte) 0x02;

            /**
             * Get flags byte.
             *
             * @return byte value of startFlag field.
             */
            public byte getFlag() {
                return startFlag;
            }

        }

        public static class VlrNumber extends AbstractParamPojo implements Flag {

            /**
             * Start Flag byte, always = (byte) 0x81.
             */
            private final transient byte startFlag = (byte) 0x81;

            /**
             * Get flags byte.
             *
             * @return byte value of startFlag field.
             */
            public byte getFlag() {
                return startFlag;
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

        public static class CellGlobalIdOrServiceAreaIdOrLAI extends AbstractParamPojo implements Flags {

            /**
             * Start Flag byte[], always = {(byte) 0xa3, (byte) 0x09, (byte) 0x80}.
             */
            private final transient  byte[] startFlag = {(byte) 0xa3, (byte) 0x09, (byte) 0x80};

            /**
             * Get flags byte[].
             *
             * @return byte[] value of startFlag field.
             */
            public byte[] getFlags() {
                return startFlag;
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

    }

    public static class ExtBasicServiceCode extends AbstractParamPojo implements Flags {

        /**
         * Start Flag byte[], always = {(byte) 0xbf, (byte) 0x35, (byte) 0x03, (byte) 0x83}.
         */
        private final transient  byte[] startFlag = {(byte) 0xbf, (byte) 0x35, (byte) 0x03, (byte) 0x83};

        /**
         * Get flags byte[].
         *
         * @return byte[] value of startFlag field.
         */
        public byte[] getFlags() {
            return startFlag;
        }

    }

    public static class CallReferenceNumber extends AbstractParamPojo implements Flags {

        /**
         * Start Flag byte[], always = {(byte) 0x9f, (byte) 0x36}.
         */
        private final transient  byte[] startFlag = {(byte) 0x9f, (byte) 0x36};

        /**
         * Get flags byte[].
         *
         * @return byte[] value of startFlag field.
         */
        public byte[] getFlags() {
            return startFlag;
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

    public static class MscAddress extends AbstractParamPojo implements Flags {

        /**
         * Start Flag byte[], always = {(byte) 0x9f, (byte) 0x37}.
         */
        private final transient  byte[] startFlag = {(byte) 0x9f, (byte) 0x37};

        /**
         * Get flags byte[].
         *
         * @return byte[] value of startFlag field.
         */
        public byte[] getFlags() {
            return startFlag;
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

    public static class CalledPartyBCDNumber extends AbstractParamPojo implements Flags {

        /**
         * Start Flag byte[], always = {(byte) 0x9f, (byte) 0x38}.
         */
        private final transient  byte[] startFlag = {(byte) 0x9f, (byte) 0x38};

        /**
         * Check if this is hex object (true or false).
         *
         * @return true if it's hex, otherwise false; always true currently.
         */
        @Override
        public boolean isHEX() {
            return true;
        }

        /**
         * Get flags byte[].
         *
         * @return byte[] value of startFlag field.
         */
        public byte[] getFlags() {
            return startFlag;
        }

    }

    public static class TimeAndTimezone extends AbstractParamPojo implements Flags {

        /**
         * Start Flag byte[], always = {(byte) 0x9f, (byte) 0x39}.
         */
        private final transient  byte[] startFlag = {(byte) 0x9f, (byte) 0x39};

        /**
         * Get flags byte[].
         *
         * @return byte[] value of startFlag field.
         */
        public byte[] getFlags() {
            return startFlag;
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
}
