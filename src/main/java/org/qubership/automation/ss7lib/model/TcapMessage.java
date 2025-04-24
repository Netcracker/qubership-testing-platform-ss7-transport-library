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

public class TcapMessage extends AbstractMessage {
    private byte totalLength;
    private TCAPType type;
    private Transaction sourceTransaction;
    private Transaction destinationTransaction;
    private Oid oid;
    private Dialogue dialogue;
    private final byte flagStartCap = 0x6c;
    private List<CapMessage> capMessages = Lists.newLinkedList();

    public byte getFlagStartCap() {
        return flagStartCap;
    }

    public byte getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(byte totalLength) {
        this.totalLength = totalLength;
    }

    public TCAPType getType() {
        return type;
    }

    public void setType(TCAPType type) {
        this.type = type;
    }

    public Transaction getSourceTransaction() {
        return sourceTransaction;
    }

    public void setSourceTransaction(Transaction source) {
        this.sourceTransaction = source;
    }

    public Transaction getDestinationTransaction() {
        return destinationTransaction;
    }

    public void setDestinationTransaction(Transaction destination) {
        this.destinationTransaction = destination;
    }

    public Oid getOid() {
        return oid;
    }

    public void setOid(Oid oid) {
        this.oid = oid;
    }

    public Dialogue getDialogue() {
        return dialogue;
    }

    public void setDialogue(Dialogue dialogue) {
        this.dialogue = dialogue;
    }

    public List<CapMessage> getCapMessages() {
        return capMessages;
    }

    public void setCapMessages(List<CapMessage> capMessages) {
        this.capMessages = capMessages;
    }

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
