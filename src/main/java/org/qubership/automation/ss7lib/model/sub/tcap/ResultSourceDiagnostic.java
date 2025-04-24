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

import java.util.List;

import org.qubership.automation.ss7lib.model.AbstractMessage;
import org.qubership.automation.ss7lib.model.type.dialog.DialogServiceUser;

import com.google.common.collect.Lists;

public class ResultSourceDiagnostic extends AbstractMessage {
    private byte id;
    private List<DialogServiceUser> dialogServiceUser = Lists.newLinkedList();

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public List<DialogServiceUser> getDialogServiceUser() {
        return dialogServiceUser;
    }

    public void setDialogServiceUser(List<DialogServiceUser> dialogServiceUser) {
        this.dialogServiceUser = dialogServiceUser;
    }

    public byte getFirstFlag() {
        return (byte) 0xa3;
    }

    public byte getSecondFlag() {
        return (byte) 0xa1;
    }

    @Override
    public String toString() {
        return "ResultSourceDiagnostic{" + "id=" + id + ", dialogServiceUser=" + dialogServiceUser + '}';
    }
}
