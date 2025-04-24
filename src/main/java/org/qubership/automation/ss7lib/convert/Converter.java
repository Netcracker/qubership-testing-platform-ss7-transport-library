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

package org.qubership.automation.ss7lib.convert;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Converter.class);

    public static String bytesToCallPartyDigits(byte[] bcd) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Convert value: {}", Arrays.toString(bcd));
        }
        StringBuilder sb = new StringBuilder();
        for (byte aBcd : bcd) {
            sb.append(bytesToCallPartyDigits(aBcd));
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result: {}", sb);
        }
        return sb.toString();
    }

    private static String bytesToCallPartyDigits(byte bcd) {
        StringBuilder sb = new StringBuilder();
        byte high = (byte) (bcd & 0xf0);
        high >>>= (byte) 4;
        high = (byte) (high & 0x0f);
        byte low = (byte) (bcd & 0x0f);
        sb.append(low); //It's not mistake, to keep ZERO, we need revers value. [0x70] -> [07]
        sb.append(high);
        return sb.toString();
    }

    public static String bytesToHex(byte... input) {
        StringBuilder builder = new StringBuilder();
        for (byte b : input) {
            String hex = Integer.toHexString(b & 0xff);
            builder.append(hex.length() == 1 ? '0' + hex : hex);
        }
        return builder.toString();
    }

    public static String byteToHex(byte b) {
        int i = b & 0xFF;
        return Integer.toHexString(i);
    }

    public static DividedByte divideByteOnTwo(byte byteToDivide) {
        DividedByte dividedByte = new DividedByte();
        byte left = (byte) (byteToDivide >> 4 & 0xf);
        byte right = (byte) (byteToDivide & 0xf);
        dividedByte.setLeft(left);
        dividedByte.setRight(right);
        return dividedByte;
    }

    public static String bytesToDottedString(byte[] dottedValue) {
        StringBuilder builder = new StringBuilder(dottedValue.length);
        for (int index = 0; index < dottedValue.length; index++) {
            builder.append(dottedValue[index]);
            if (index < dottedValue.length - 1) {
                builder.append('.');
            }
        }
        return builder.toString();
    }

    public static class DividedByte {
        private byte left;
        private byte right;

        public byte getLeft() {
            return left;
        }

        public void setLeft(byte left) {
            this.left = left;
        }

        public byte getRight() {
            return right;
        }

        public void setRight(byte right) {
            this.right = right;
        }
    }

    public static byte[] hexToBytes(final String message) {
        int len = message.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {

            data[i / 2] = (byte) ((Character.digit(message.charAt(i), 16) << 4)
                    + Character.digit(message.charAt(i + 1), 16));

        }
        return data;
    }

}
