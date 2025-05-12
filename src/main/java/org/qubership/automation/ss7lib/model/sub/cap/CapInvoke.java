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

import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessagePojo;

import lombok.Setter;

@Setter
public class CapInvoke {

    /**
     * CAP Invoke ID object.
     */
    @lombok.Getter
    private CAPInvokeIDPojo invokeID;

    /**
     * CAP OpCode object.
     */
    @lombok.Getter
    private CAPOpCodePojo opCode;

    /**
     * CAP message object.
     */
    private CAPMessagePojo capMessagePojo;

    /**
     * Get capMessagePojo field cast to T (subclass of CAPMessagePojo).
     *
     * @param <T> subclass of CAPMessagePojo
     * @return capMessagePojo field cast to T (subclass of CAPMessagePojo).
     */
    public <T extends CAPMessagePojo> T getCapMessagePojo() {
        return (T) capMessagePojo;
    }

    /**
     * Make and return String representation of this object.
     *
     * @return String representation of this object.
     */
    @Override
    public String toString() {
        return "CapInvoke{"
                + "invokeID=" + invokeID + ", opCode=" + opCode + ", capMessagePojo=" + capMessagePojo + '}';
    }
}
