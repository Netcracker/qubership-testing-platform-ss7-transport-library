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

package org.qubership.automation.ss7lib.decode.tcap.cap;

import java.nio.ByteBuffer;

import org.qubership.automation.ss7lib.convert.Converter;
import org.qubership.automation.ss7lib.decode.Utils;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageApplyChargingArg;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplyChargingArgDecoder implements CapDecoder<CAPMessageApplyChargingArg> {

    /**
     * Parse CAPMessageApplyChargingArg message from ByteBuffer.
     *
     * @param buffer ByteBuffer to parse
     * @return CAPMessageApplyChargingArg parsed from the buffer.
     */
    @Override
    public CAPMessageApplyChargingArg decode(final ByteBuffer buffer) {
        log.info("Start parsing ApplyChargingArg");
        if (!buffer.hasRemaining() || buffer.get() != 0x30) {
            return null;
        }
        byte length = buffer.get();
        ByteBuffer chargingBuffer = Utils.subBuffer(length, buffer);
        byte flag = chargingBuffer.get();
        log.debug("Apply charge flag: {}", flag);
        CAPMessageApplyChargingArg arg = new CAPMessageApplyChargingArg();
        decodeAch(arg, chargingBuffer);
        decodePartyToCharge(arg, chargingBuffer);
        log.info("Finished parsing ApplyChargingArg: {}", arg);
        return arg;
    }

    private void decodePartyToCharge(final CAPMessageApplyChargingArg arg, final ByteBuffer chargingBuffer) {
        if (!chargingBuffer.hasRemaining() || chargingBuffer.get() != (byte) 0xa2) {
            return;
        }
        log.debug("Party to charge length: {}", chargingBuffer.get());
        log.debug("Party to charge flag: {}", chargingBuffer.get());
        log.debug("Party to charge  value length: {}", chargingBuffer.get());
        CAPMessageApplyChargingArg.PartyToCharge partyToCharge = new CAPMessageApplyChargingArg.PartyToCharge();
        arg.setPartyToCharge(partyToCharge);
        partyToCharge.setStringBytes(Converter.bytesToHex(chargingBuffer.get()));
    }

    private void decodeAch(final CAPMessageApplyChargingArg arg, final ByteBuffer chargingBuffer) {
        byte valueLength = chargingBuffer.get();
        log.debug("Characteristic length: {}", valueLength);

        ByteBuffer valueData = Utils.subBuffer(valueLength, chargingBuffer);

        CAPMessageApplyChargingArg.AChBillingChargingCharacteristics ach =
                new CAPMessageApplyChargingArg.AChBillingChargingCharacteristics();

        arg.setaChBillingChargingCharacteristics(ach);
        ach.setStringBytes(Converter.bytesToHex(valueData.array()));
    }
}
