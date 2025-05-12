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

import lombok.Getter;
import lombok.Setter;

public class SccpMessage extends AbstractMessage {

    /**
     * Flag if the message is of ITUProtocol (true) or not.
     */
    @Setter
    @Getter
    private boolean isITUProtocol;

    /**
     * MessageType (enum) of the message.
     */
    @Setter
    @Getter
    private MessageType messageType;

    /**
     * Byte pointer to the 1st mandatory variable.
     */
    @Setter
    @Getter
    private byte pointerToFirstMandatoryVariable;

    /**
     * Byte pointer to the 2nd mandatory variable.
     */
    @Setter
    @Getter
    private byte pointerToSecondMandatoryVariable;

    /**
     * Byte pointer to the 3rd mandatory variable.
     */
    @Setter
    @Getter
    private byte pointerToThirdMandatoryVariable;

    /**
     * CallPartyAddress message part about called party.
     */
    @Setter
    @Getter
    private CallPartyAddress calledPartyAddress;

    /**
     * CallPartyAddress message part about calling party.
     */
    @Setter
    @Getter
    private CallPartyAddress callingPartyAddress;

    /**
     * Byte technical info about class.
     */
    @Setter
    @Getter
    private byte clazz;

    /**
     * Byte technical info about handling.
     */
    @Setter
    @Getter
    private byte messageHandling;

    /**
     * Make and return String representation of this object.
     *
     * @return String representation of this object.
     */
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
