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

import java.util.Arrays;

import org.qubership.automation.ss7lib.model.type.dialog.DialogueType;
import org.qubership.automation.ss7lib.model.type.dialog.Result;

public class Dialogue {

    public static final byte DIALOGUE_FLAG = (byte) 0xa0;
    private byte totalLength;
    private DialogueType dialogueType;
    private byte messageLength;
    private final byte[] flag = new byte[]{(byte) 0x80, 0x02};//All messages have the flag 80 02
    private byte padding;
    private byte protocolVersion;
    private ApplicationContextName applicationContextName;
    private Result result;
    private ResultSourceDiagnostic resultSourceDiagnostic;

    public byte getMainType() {
        return DIALOGUE_FLAG;
    }

    public byte getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(byte totalLength) {
        this.totalLength = totalLength;
    }

    public DialogueType getDialogueType() {
        return dialogueType;
    }

    public void setDialogueType(DialogueType dialogueType) {
        this.dialogueType = dialogueType;
    }

    public byte getMessageLength() {
        return messageLength;
    }

    public void setMessageLength(byte messageLength) {
        this.messageLength = messageLength;
    }

    public byte[] getFlag() {
        return flag;
    }

    public byte getPadding() {
        return padding;
    }

    public void setPadding(byte padding) {
        this.padding = padding;
    }

    public byte getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(byte protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public ApplicationContextName getApplicationContextName() {
        return applicationContextName;
    }

    public void setApplicationContextName(ApplicationContextName applicationContextName) {
        this.applicationContextName = applicationContextName;
    }


    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void setResultSourceDiagnostic(ResultSourceDiagnostic resultSourceDiagnostic) {
        this.resultSourceDiagnostic = resultSourceDiagnostic;
    }

    public ResultSourceDiagnostic getResultSourceDiagnostic() {
        return resultSourceDiagnostic;
    }

    @Override
    public String toString() {
        return "Dialogue{"
                + "totalLength=" + totalLength
                + ", dialogueType=" + dialogueType
                + ", messageLength=" + messageLength
                + ", flag=" + Arrays.toString(flag)
                + ", padding=" + padding
                + ", protocolVersion=" + protocolVersion
                + ", applicationContextName=" + applicationContextName
                + ", result=" + result
                + ", resultSourceDiagnostic=" + resultSourceDiagnostic + '}';
    }
}
