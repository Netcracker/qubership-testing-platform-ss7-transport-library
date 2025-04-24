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

import org.qubership.automation.ss7lib.model.sub.NumberFormat;
import org.qubership.automation.ss7lib.model.type.TransactionID;

public class Transaction implements NumberFormat {
    private TransactionID transactionID;
    private byte length;
    private String id;

    public TransactionID getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(TransactionID transactionID) {
        this.transactionID = transactionID;
    }

    public byte getLength() {
        return length;
    }

    public void setLength(byte length) {
        this.length = length;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        StringBuilder idBuilder = new StringBuilder(id);
        while (idBuilder.length() < 8) {
            idBuilder.insert(0, "0");
        }
        id = idBuilder.toString();
        this.id = id;//.replaceFirst("^0+", ""); /*corrected it because otherwise there was an abortion on tango*/
    }

    @Override
    public boolean isHEX() {
        return true;
    }

    @Override
    public String toString() {
        return "Transaction{" + "transactionID=" + transactionID + ", length=" + length + ", id='" + id + '\'' + '}';
    }
}
