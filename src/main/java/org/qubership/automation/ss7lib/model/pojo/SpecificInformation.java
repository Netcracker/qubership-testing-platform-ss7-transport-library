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

package org.qubership.automation.ss7lib.model.pojo;

import org.qubership.automation.ss7lib.model.type.SpecificInfo;

public class SpecificInformation {
    private SpecificInfo type;
    private String releaseCause;

    public void setType(SpecificInfo type) {
        this.type = type;
    }

    public SpecificInfo getType() {
        return type;
    }

    public void setReleaseCause(String releaseCause) {
        this.releaseCause = releaseCause;
    }

    public String getReleaseCause() {
        return releaseCause;
    }
}
