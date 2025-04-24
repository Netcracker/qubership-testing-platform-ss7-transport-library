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

package org.qubership.automation.ss7lib.model.sub.tcap;

public class ApplicationContextName {

    private final byte mainType  = (byte) 0xa1;
    private byte totalLength;
    private final byte messageType = 0x06;
    private byte messageLength;
    private String message;


    public byte getMainType() {
        return mainType;
    }

    public byte getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(byte totalLength) {
        this.totalLength = totalLength;
    }

    public byte getMessageType() {
        return messageType;
    }

    public byte getMessageLength() {
        return messageLength;
    }

    public void setMessageLength(byte messageLength) {
        this.messageLength = messageLength;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApplicationContextName{"
                + "mainType=" + mainType
                + ", totalLength=" + totalLength
                + ", messageType=" + messageType
                + ", messageLength=" + messageLength
                + ", message='" + message + '\'' + '}';
    }
}
