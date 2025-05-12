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

import static org.qubership.automation.ss7lib.encode.EncoderUtils.encodePojoFlagParam;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

import org.qubership.automation.ss7lib.convert.Converter;
import org.qubership.automation.ss7lib.decode.Utils;
import org.qubership.automation.ss7lib.encode.AbstractEncoder;
import org.qubership.automation.ss7lib.model.AbstractMessage;
import org.qubership.automation.ss7lib.model.CapMessage;
import org.qubership.automation.ss7lib.model.pojo.MiscCallInfo;
import org.qubership.automation.ss7lib.model.sub.cap.CapInvoke;
import org.qubership.automation.ss7lib.model.sub.cap.message.ApplyChargingReportArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.BSCMEvent;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageApplyChargingArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageConnectArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageEventReportBCSMArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessagePojo;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageRequestReportBCSMEventArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.InitialDetectionPoint;
import org.qubership.automation.ss7lib.model.sub.cap.message.LegID;
import org.qubership.automation.ss7lib.model.type.EventType;
import org.qubership.automation.ss7lib.model.type.MonitorMode;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CAPEncoder extends AbstractEncoder<CapMessage> {

    /**
     * InitialIdpEncoder object.
     */
    private final InitialIdpEncoder initialIdpEncoder = new InitialIdpEncoder();

    /**
     * Encode AbstractMessage; place result into a new byte[].
     *
     * @param pojo - AbstractMessage to encode
     * @return byte[] filled with encoding result.
     */
    @Override
    public byte[] encode(@Nonnull final AbstractMessage pojo) {
        CapMessage capMessage = (CapMessage) pojo;
        log.info("Start encode of CAP message: {}", pojo);
        ArrayList<Byte> bytes = Lists.newArrayList();
        ArrayList<Byte> subBytes = Lists.newArrayList();

        bytes.add(capMessage.getStartFlag());
        if (Objects.nonNull(capMessage.getInvoke())) {
            encodeInvoke(subBytes, capMessage.getInvoke());
        }
        validateLengthMessage(bytes, (byte) subBytes.size());
        bytes.add((byte) subBytes.size());
        bytes.addAll(subBytes);
        byte[] result = convertListToArray(bytes);
        Utils.logTrace("Finish CAP message encode: \n{}", getClass(), result);
        return result;
    }

    private void encodeInvoke(@Nonnull final ArrayList<Byte> bytes,
                              @Nonnull final CapInvoke capMessage) {
        if (Objects.nonNull(capMessage.getInvokeID())) {
            encodePojoFlagParam(bytes, capMessage.getInvokeID());
            Utils.logTrace("Invoke id: {}", bytes, getClass());
        }
        if (Objects.nonNull(capMessage.getOpCode())) {
            encodePojoFlagParam(bytes, capMessage.getOpCode());
            Utils.logTrace("Op code: {}", bytes, getClass());
        }
        if (Objects.nonNull(capMessage.getCapMessagePojo())) {
            encodeMessage(bytes, capMessage.getCapMessagePojo());
            Utils.logTrace("Message: {}", bytes, getClass());
        }
    }

    private void encodeMessage(@Nonnull final ArrayList<Byte> bytes,
                               @Nonnull final CAPMessagePojo capMessage) {
        bytes.add(capMessage.getStartFlag());
        ArrayList<Byte> message = Lists.newArrayList();
        Utils.logTrace("Message flag: \n{}", bytes, getClass());
        if (capMessage instanceof InitialDetectionPoint) {
            encodeIDP(message, (InitialDetectionPoint) capMessage);
        } else if (capMessage instanceof CAPMessageRequestReportBCSMEventArg) {
            encodeRRB(message, (CAPMessageRequestReportBCSMEventArg) capMessage);
        } else if (capMessage instanceof CAPMessageEventReportBCSMArg) {
            encodeERB(message, (CAPMessageEventReportBCSMArg) capMessage);
        } else if (capMessage instanceof ApplyChargingReportArg) {
            encodeAcr(message, (ApplyChargingReportArg) capMessage);
        } else if (capMessage instanceof CAPMessageApplyChargingArg) {
            encodeAC(message, (CAPMessageApplyChargingArg) capMessage);
        } else if (capMessage instanceof CAPMessageConnectArg) {
            encodeConnect(message, (CAPMessageConnectArg) capMessage);
        }
        Utils.logTrace("Message: \n{}", message, getClass());

        byte size = (byte) message.size();
        if (Byte.toUnsignedInt(size) > 0x80) {
            bytes.add((byte) 0x81);
        }
        bytes.add(size);
        bytes.addAll(message);
    }

    private void encodeAcr(final ArrayList<Byte> message, final ApplyChargingReportArg capMessage) {
        for (byte b : capMessage.getValue()) {
            message.add(b);
        }
    }

    private void encodeConnect(@Nonnull final ArrayList<Byte> bytes,
                               @Nonnull final CAPMessageConnectArg capMessage) {
        bytes.add(capMessage.getFlag());
        ArrayList<Byte> subBytes = Lists.newArrayList();
        for (CAPMessageConnectArg.DestinationRoutingAddress address : capMessage.getDestinationRoutingAddressList()) {
            encodePojoFlagParam(subBytes, address);
        }
        bytes.add((byte) subBytes.size());
        bytes.addAll(subBytes);
    }

    private void encodeAC(@Nonnull final ArrayList<Byte> bytes,
                          @Nonnull final CAPMessageApplyChargingArg capMessage) {
        if (Objects.nonNull(capMessage.getAChBillingChargingCharacteristics())) {
            encodePojoFlagParam(bytes, capMessage.getAChBillingChargingCharacteristics());
        }
        if (Objects.nonNull(capMessage.getPartyToCharge())) {
            encodePojoFlagParam(bytes, capMessage.getPartyToCharge());
        }
    }

    private void encodeIDP(@Nonnull final ArrayList<Byte> bytes,
                           @Nonnull final InitialDetectionPoint pojo) {
        bytes.addAll(initialIdpEncoder.encodeToArray(pojo));
    }


    private void encodeERB(final List<Byte> message, final CAPMessageEventReportBCSMArg erb) {
        erb.getBscmEventList().forEach(event -> {
            message.add(event.getAfterLengthFlag());
            message.add((byte) 0x1); // length of ERB.type
            message.add(event.getEventType().getId());
            if (event.getSpecificInformation() != null) {
                encodeSpecificInformation(message, event, event.getEventType());
            }

            //region lgeId
            LegID legID = event.getLegID();
            message.add((byte) 0xa3);
            message.add((byte) 0x03);
            message.add((byte) 0x81);
            message.add((byte) 0x01); /*length of reservation ID*/
            message.add(legID.getId());
            //endregion
            encodeMiscInfo(event, message);
        });
    }

    private void encodeMiscInfo(final BSCMEvent event, final List<Byte> message) {
        MiscCallInfo miscCallInfo = event.getMiscCallInfo();
        if (Objects.isNull(miscCallInfo)) {
            return;
        }
        message.add(miscCallInfo.getFlag());
        List<Byte> bytes = Lists.newLinkedList();
        bytes.add((byte) 0x80); /*messageTypeFlag*/
        bytes.add((byte) 0x1); /*messageTypeLength*/
        bytes.add(miscCallInfo.getMessageType());
        message.add((byte) bytes.size());
        message.addAll(bytes);
    }

    private void encodeSpecificInformation(final List<Byte> message,
                                           final BSCMEvent event,
                                           final EventType eventType) {
        message.addAll(asList(eventType.getFlags()));
        LinkedList<Byte> list = new LinkedList<>();
        list.add((byte) 0x80); //disconnect flag
        byte[] bytes = Converter.hexToBytes(event.getSpecificInformation().getReleaseCause());
        list.add((byte) bytes.length);
        list.addAll(asList(bytes));
        if (event.isCallForwarded()) {
            list.addAll(asList(new byte[]{(byte) 0x9f, 0x32, 0})); /*CallForwarded flag*/
        }
        message.add((byte) (list.size()));
        message.addAll(list);
    }

    private void encodeRRB(@Nonnull final ArrayList<Byte> bytes,
                           @Nonnull final CAPMessageRequestReportBCSMEventArg pojo) {
        bytes.add(pojo.getFlag());
        ArrayList<Byte> subBytes = Lists.newArrayList();
        for (CAPMessageRequestReportBCSMEventArg.BSCMEvent bscmEvent : pojo.getBscmEventList()) {
            encodeBSCMEvent(subBytes, bscmEvent);
        }
        bytes.add((byte) subBytes.size());
        bytes.addAll(subBytes);
    }

    private void encodeBSCMEvent(@Nonnull final ArrayList<Byte> bytes,
                                 @Nonnull final CAPMessageRequestReportBCSMEventArg.BSCMEvent bscmEvent) {
        bytes.add(bscmEvent.getStartFlag());
        ArrayList<Byte> subBytes = Lists.newArrayList();

        subBytes.add(bscmEvent.getAfterLengthFlag());
        subBytes.add(bscmEvent.getEventType().getLength());
        subBytes.add(bscmEvent.getEventType().getId());
        MonitorMode monitorMode = bscmEvent.getMonitorMode();
        if (Objects.nonNull(monitorMode)) {
            subBytes.addAll(asList(monitorMode.getCode()));
        }
        if (Objects.nonNull(bscmEvent.getLegID())) {
            subBytes.addAll(asList(bscmEvent.getLegID().getStartFlag()));
            subBytes.add(bscmEvent.getLegID().getLength());
            subBytes.add(bscmEvent.getLegID().getId());
        }
        bytes.add((byte) subBytes.size());
        bytes.addAll(subBytes);
    }

}
