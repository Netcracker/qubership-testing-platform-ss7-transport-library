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

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MiscCallInfo {

    /**
     * Type of the message.
     */
    private byte messageType;

    /**
     * Get flag byte.
     *
     * @return byte - currently always returns (byte) 0xa4.
     */
    public byte getFlag() {
        return (byte) 0xa4;
    }
}
