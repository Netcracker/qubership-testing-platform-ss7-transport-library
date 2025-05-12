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

package org.qubership.automation.ss7lib.model.sub.cap.message;

import java.util.List;

import org.qubership.automation.ss7lib.model.sub.Flag;
import org.qubership.automation.ss7lib.model.sub.cap.param.AbstractParamPojo;

import com.google.common.collect.Lists;

public class CAPMessageConnectArg extends  CAPMessagePojo {

    /**
     * Start Flag byte; always = (byte) 0xa0.
     */
    private final byte startFlag = (byte) 0xa0;

    /**
     * Message Length byte.
     */
    @lombok.Setter
    @lombok.Getter
    private byte length;

    /**
     * List of DestinationRoutingAddress elements.
     */
    @lombok.Getter
    private final List<DestinationRoutingAddress> destinationRoutingAddressList = Lists.newArrayList();

    /**
     * Getter for flag field.
     *
     * @return byte startFlag value.
     */
    public byte getFlag() {
        return startFlag;
    }

    public static class DestinationRoutingAddress extends AbstractParamPojo implements Flag {

        /**
         * Start Flag byte; always = 0x04.
         */
        private final byte flag = 0x04;

        /**
         * Getter for flag field.
         *
         * @return byte flag field value.
         */
        @Override
        public byte getFlag() {
            return flag;
        }

        /**
         * Get boolean isHex flag.
         *
         * @return boolean isHex flag; always true currently.
         */
        @Override
        public boolean isHEX() {
            return true;
        }
    }
}
