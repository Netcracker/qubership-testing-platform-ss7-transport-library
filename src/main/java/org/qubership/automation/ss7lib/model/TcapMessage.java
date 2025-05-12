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

import java.util.List;

import org.qubership.automation.ss7lib.model.sub.tcap.Dialogue;
import org.qubership.automation.ss7lib.model.sub.tcap.Oid;
import org.qubership.automation.ss7lib.model.sub.tcap.Transaction;
import org.qubership.automation.ss7lib.model.type.TCAPType;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TcapMessage extends AbstractMessage {

    /**
     * Byte containing total message length.
     */
    @Setter
    private byte totalLength;

    /**
     * TCAP type of the message.
     */
    @Setter
    private TCAPType type;

    /**
     * Transaction on the source side.
     */
    @Setter
    private Transaction sourceTransaction;

    /**
     * Transaction on the destination side.
     */
    @Setter
    private Transaction destinationTransaction;

    /**
     * Object identifier.
     */
    @Setter
    private Oid oid;

    /**
     * Dialogue message part.
     */
    @Setter
    private Dialogue dialogue;

    /**
     * Flag 'start CAP' currently is always = 0x6c.
     */
    private final byte flagStartCap = 0x6c;

    /**
     * List of CAP messages.
     */
    @Setter
    private List<CapMessage> capMessages = Lists.newLinkedList();

    /**
     * Make and return String representation of this object.
     *
     * @return String representation of this object.
     */
    @Override
    public String toString() {
        return "TcapMessage{"
                + "totalLength=" + totalLength
                + ", type=" + type
                + ", messageLength=" + getMessageLength()
                + ", sourceTransaction=" + sourceTransaction
                + ", destinationTransaction=" + destinationTransaction
                + ", oid=" + oid
                + ", dialogue=" + dialogue
                + ", capMessages=" + capMessages
                + '}';
    }
}
