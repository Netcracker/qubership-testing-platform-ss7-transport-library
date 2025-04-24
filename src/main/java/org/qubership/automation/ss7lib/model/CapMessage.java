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

package org.qubership.automation.ss7lib.model;

import org.qubership.automation.ss7lib.model.sub.cap.CapInvoke;

public class CapMessage extends AbstractMessage {
    private final transient byte startFlag = (byte) 0xa1;
    private byte capMessageLength;
    private CapInvoke invoke;

    public byte getStartFlag() {
        return startFlag;
    }

    public byte getCapMessageLength() {
        return capMessageLength;
    }

    public void setCapMessageLength(byte capMessageLength) {
        this.capMessageLength = capMessageLength;
    }

    public CapInvoke getInvoke() {
        return invoke;
    }

    public void setInvoke(CapInvoke invoke) {
        this.invoke = invoke;
    }

    @Override
    public String toString() {
        return "CapMessage{"
                + "startFlag=" + startFlag + ", capMessageLength=" + capMessageLength + ", invoke=" + invoke + '}';
    }
}
