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

package org.qubership.automation.ss7lib.decode;

import org.qubership.automation.ss7lib.decode.m3ua.M3UADecoderTest;
import org.qubership.automation.ss7lib.decode.sccp.SccpDecoderTest;
import org.qubership.automation.ss7lib.decode.tcap.TcapDecoderTest;
import org.qubership.automation.ss7lib.decode.tcap.cap.ApplyChargingArgDecoderTest;
import org.qubership.automation.ss7lib.decode.tcap.cap.ReleaseCallDecoderTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Suite.SuiteClasses({
        ApplyChargingArgDecoderTest.class,
        ReleaseCallDecoderTest.class,
        TcapDecoderTest.class,
        SccpDecoderTest.class,
        M3UADecoderTest.class,
})
@RunWith(Suite.class)
public class DecodeScope {
}
