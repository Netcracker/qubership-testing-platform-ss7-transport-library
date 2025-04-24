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

import org.qubership.automation.ss7lib.model.sub.sccp.CallPartyAddress;
import org.qubership.automation.ss7lib.model.type.MessageType;

public class SccpMessage extends AbstractMessage {
    private boolean isITUProtocol;
    private MessageType messageType;
    private byte pointerToFirstMandatoryVariable;
    private byte pointerToSecondMandatoryVariable;
    private byte pointerToThirdMandatoryVariable;
    private CallPartyAddress calledPartyAddress;
    private CallPartyAddress callingPartyAddress;
    private byte clazz;
    private byte messageHandling;


    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public byte getPointerToFirstMandatoryVariable() {
        return pointerToFirstMandatoryVariable;
    }

    public void setPointerToFirstMandatoryVariable(byte pointerToFirstMandatoryVariable) {
        this.pointerToFirstMandatoryVariable = pointerToFirstMandatoryVariable;
    }

    public byte getPointerToSecondMandatoryVariable() {
        return pointerToSecondMandatoryVariable;
    }

    public void setPointerToSecondMandatoryVariable(byte pointerToSecondMandatoryVariable) {
        this.pointerToSecondMandatoryVariable = pointerToSecondMandatoryVariable;
    }

    public byte getPointerToThirdMandatoryVariable() {
        return pointerToThirdMandatoryVariable;
    }

    public void setPointerToThirdMandatoryVariable(byte pointerToThirdMandatoryVariable) {
        this.pointerToThirdMandatoryVariable = pointerToThirdMandatoryVariable;
    }

    public CallPartyAddress getCalledPartyAddress() {
        return calledPartyAddress;
    }

    public void setCalledPartyAddress(CallPartyAddress calledPartyAddress) {
        this.calledPartyAddress = calledPartyAddress;
    }

    public CallPartyAddress getCallingPartyAddress() {
        return callingPartyAddress;
    }

    public void setCallingPartyAddress(CallPartyAddress callingPartyAddress) {
        this.callingPartyAddress = callingPartyAddress;
    }

    public void setClazz(byte clazz) {
        this.clazz = clazz;
    }

    public byte getClazz() {
        return clazz;
    }

    public void setMessageHandling(byte messageHandling) {
        this.messageHandling = messageHandling;
    }


    public byte getMessageHandling() {
        return messageHandling;
    }


    public boolean isITUProtocol() {
        return isITUProtocol;
    }

    public void setITUProtocol(boolean ITUProtocol) {
        isITUProtocol = ITUProtocol;
    }

    @Override
    public String toString() {
        return "SccpMessage{"
                + "messageType=" + messageType
                + ", pointerToFirstMandatoryVariable=" + pointerToFirstMandatoryVariable
                + ", pointerToSecondMandatoryVariable=" + pointerToSecondMandatoryVariable
                + ", pointerToThirdMandatoryVariable=" + pointerToThirdMandatoryVariable
                + ", calledPartyAddress=" + calledPartyAddress + ", callingPartyAddress=" + callingPartyAddress
                + ", clazz=" + clazz + ", messageHandling=" + messageHandling + '}';
    }
}
