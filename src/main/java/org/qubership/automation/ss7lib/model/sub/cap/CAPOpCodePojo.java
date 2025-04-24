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

package org.qubership.automation.ss7lib.model.sub.cap;

import org.qubership.automation.ss7lib.model.sub.Flag;
import org.qubership.automation.ss7lib.model.sub.cap.param.AbstractParamPojo;
import org.qubership.automation.ss7lib.model.type.OpCodeType;

public class CAPOpCodePojo extends AbstractParamPojo implements Flag {

    private final byte flag = (byte) 0x02;
    private OpCodeType opCodeType;

    public byte getFlag() {
        return flag;
    }

    public OpCodeType getOpCodeType() {
        return opCodeType;
    }

    public void setOpCodeType(OpCodeType opCodeType) {
        this.opCodeType = opCodeType;
    }

    @Override
    public String toString() {
        return "CAPOpCodePojo{" + "flag=" + flag + ", opCodeType=" + opCodeType + '}';
    }
}
