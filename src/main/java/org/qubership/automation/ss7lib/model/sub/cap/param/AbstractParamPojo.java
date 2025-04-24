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

package org.qubership.automation.ss7lib.model.sub.cap.param;

import org.qubership.automation.ss7lib.model.sub.NumberFormat;

public abstract class AbstractParamPojo implements NumberFormat {
    private byte messageLength;
    private String stringBytes;


    public byte getMessageLength() {
        return messageLength;
    }

    public void setMessageLength(byte messageLength) {
        this.messageLength = messageLength;
    }

    public String getStringBytes() {
        return stringBytes;
    }

    public void setStringBytes(String stringBytes) {
        this.stringBytes = stringBytes;
    }
}
