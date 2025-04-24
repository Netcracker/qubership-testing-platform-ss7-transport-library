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

import static com.google.common.primitives.Bytes.asList;
import static org.qubership.automation.ss7lib.encode.EncoderUtils.encodeParameter;
import static org.qubership.automation.ss7lib.encode.EncoderUtils.encodePojoFlagParam;
import static org.qubership.automation.ss7lib.encode.EncoderUtils.reverse;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.qubership.automation.ss7lib.encode.EncoderUtils;
import org.qubership.automation.ss7lib.encode.PartEncoder;
import org.qubership.automation.ss7lib.model.sub.cap.message.InitialDetectionPoint;
import org.qubership.automation.ss7lib.model.sub.cap.message.RedirectionPartyId;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.InitialDpArgExtension;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.PartyNumber;
import org.qubership.automation.ss7lib.parse.parser.RedirectionNumber;

import com.google.common.collect.Lists;

public class InitialIdpEncoder implements PartEncoder<InitialDetectionPoint> {
    private final PartyNumberEncoder partyNumberEncoder = new PartyNumberEncoder();


    private void encodePartNumber(List<Byte> bytes, PartyNumber callPartyNumber, byte callPartyNumberFlag) {
        if (Objects.nonNull(callPartyNumber)) {
            ByteBuffer buffer = partyNumberEncoder.encode(callPartyNumber);
            bytes.add(callPartyNumberFlag);
            for (byte b : buffer.array()) {
                bytes.add(b);
            }
        }
    }

    @Override
    public ByteBuffer encode(InitialDetectionPoint pojo) {
        throw new UnsupportedOperationException("Only #encodeToArray is supported");
    }

    @Override
    public List<Byte> encodeToArray(InitialDetectionPoint idp) {
        List<Byte> bytes = new LinkedList<>();
        encodeParameter(bytes, idp, idp.getServiceKey());

        encodePartNumber(bytes, idp.getCalledPartyNumber(), PartyNumber.CALLED_PARTY_NUMBER_FLAG);
        encodePartNumber(bytes, idp.getCallingPartyNumber(), PartyNumber.CALLING_PARTY_NUMBER_FLAG);

        encodeParameter(bytes, idp, idp.getCallingPartysCategory());
        encodeParameter(bytes, idp, idp.getIpsspCapabilities());
        encodeOriginalRedirection(bytes, idp);
        encodeParameter(bytes, idp, idp.getLocationNumber());
        encodeParameter(bytes, idp, idp.getHighLayerCompatibility());
        encodeParameter(bytes, idp, idp.getBearerCapability());
        encodeParameter(bytes, idp, idp.getEventTypeBCSM());
        encodeRedirection(bytes, idp);

        if (Objects.nonNull(idp.getImsi())) {
            idp.getImsi().setStringBytes(reverse(idp.getImsi().getStringBytes()));
            encodePojoFlagParam(bytes, idp.getImsi());
        }

        InitialDetectionPoint.LocationInformation locationInformation = idp.getLocationInformation();
        if (Objects.nonNull(locationInformation)) {
            ArrayList<Byte> sub_bytes = Lists.newArrayList();
            encodeParameter(sub_bytes, idp, locationInformation.getAgeOfLocationInformation());
            encodeParameter(sub_bytes, idp, locationInformation.getVlrNumber());
            encodePartyNumber(locationInformation, sub_bytes);
            encodeParameter(sub_bytes, idp, locationInformation.getCellGlobalIdOrServiceAreaIdOrLAI());
            /*LocationNumber encoding*/
            locationInformation.setMessageLength((byte) sub_bytes.size());
            encodePojoFlagParam(bytes, locationInformation);
            bytes.addAll(sub_bytes);
        }

        encodeParameter(bytes, idp, idp.getExtBasicServiceCode());
        encodeParameter(bytes, idp, idp.getCallReferenceNumber());
        encodeParameter(bytes, idp, idp.getMscAddress());
        encodeParameter(bytes, idp, idp.getCalledPartyBCDNumber());
        encodeParameter(bytes, idp, idp.getTimeAndTimezone());
        if (idp.isCallForwardingSSPending()) {
            bytes.addAll(asList(new byte[]{(byte) 0x9f, 0x3a, 0x0})); /*CallForwarding marker*/
        }
        InitialDpArgExtension extension = idp.getInitialDpArgExtension();
        if (extension != null) {
            encodeExtension(extension, bytes);
        }
        return bytes;
    }

    private void encodeOriginalRedirection(List<Byte> bytes, InitialDetectionPoint idp) {
        RedirectionPartyId calledPartyId = idp.getOriginalCalledPartyId();
        if (calledPartyId == null) {
            return;
        }
        LinkedList<Byte> redirection = new LinkedList<>(
                EncoderUtils.splitStringBytes(calledPartyId.getStringBytes(), calledPartyId.isHEX())
        );
        RedirectionNumber number = idp.getOriginalCalledNumber();
        redirection.addAll(
                EncoderUtils.splitStringBytes(reverse(number.getStringBytes()), number.isHEX())
        );
        bytes.add((byte) 0x8c);
        bytes.add((byte) redirection.size());
        bytes.addAll(redirection);
    }

    private void encodeRedirection(List<Byte> bytes, InitialDetectionPoint idp) {
        RedirectionPartyId redirectingPartyId = idp.getRedirectingPartyId();
        if (redirectingPartyId == null) {
            return;
        }
        LinkedList<Byte> redirection = new LinkedList<>(
                EncoderUtils.splitStringBytes(redirectingPartyId.getStringBytes(), redirectingPartyId.isHEX())
        );
        RedirectionNumber redirectionNumber = idp.getRedirectionNumber();
        redirection.addAll(
                EncoderUtils.splitStringBytes(reverse(redirectionNumber.getStringBytes()), redirectionNumber.isHEX())
        );
        bytes.add(redirectingPartyId.getFlag());
        bytes.add((byte) redirection.size());
        bytes.addAll(redirection);
        encodeParameter(bytes, idp, idp.getRedirectionInformation());
    }

    private void encodePartyNumber(InitialDetectionPoint.LocationInformation locationInformation,
                                   ArrayList<Byte> sub_bytes) {
        if (locationInformation.getLocationNumber() == null) {
            return;
        }
        /*LocationNumber encoding*/
        sub_bytes.add((byte) 0x82); /*LocationNumber flag*/
        ByteBuffer locationNumber = partyNumberEncoder.encode(locationInformation.getLocationNumber()
                .getPartyNumber());
        sub_bytes.addAll(asList(locationNumber.array()));
    }

    private void encodeExtension(InitialDpArgExtension extension, List<Byte> bytes) {
        InitialDPExtensionEncoder encoder = new InitialDPExtensionEncoder();
        bytes.addAll(asList((byte) 0xbf, (byte) 0x3b)); /*Extension flag*/
        bytes.addAll(encoder.encodeToArray(extension));
    }

}
