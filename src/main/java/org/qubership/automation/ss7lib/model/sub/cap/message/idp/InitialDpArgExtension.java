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

package org.qubership.automation.ss7lib.model.sub.cap.message.idp;

import java.util.LinkedList;
import java.util.List;

import org.qubership.automation.ss7lib.model.sub.cap.message.idp.extention.Extension;

public class InitialDpArgExtension {
    private List<Extension> extensions = new LinkedList<>();
    private String gmscAddress;

    public void add(Extension extension) {
        this.extensions.add(extension);
    }

    public List<Extension> getExtensions() {
        return extensions;
    }

    public String getGmscAddress() {
        return gmscAddress;
    }

    public void setGmscAddress(String gmscAddress) {
        this.gmscAddress = gmscAddress;
    }

    public byte getFlag() {
        return (byte) 0xa0;
    }
}
