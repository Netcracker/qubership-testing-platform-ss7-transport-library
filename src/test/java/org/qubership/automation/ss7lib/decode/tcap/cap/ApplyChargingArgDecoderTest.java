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

import org.qubership.automation.ss7lib.DumpReader;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageApplyChargingArg;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public class ApplyChargingArgDecoderTest {
    @Test
    public void testDecodeApplyChargingArgs() throws IOException, URISyntaxException {
        ByteBuffer trace = DumpReader.getHexTrace("/test_data/ApplyChargingArg.hexdump");
        CAPMessageApplyChargingArg chargingArg = CapDecoderFactory.decode(ApplyChargingArgDecoder.class, trace);
        assertEquals("a00480020708", chargingArg.getAChBillingChargingCharacteristics().getStringBytes());
        assertEquals("02", chargingArg.getPartyToCharge().getStringBytes());
    }
}
