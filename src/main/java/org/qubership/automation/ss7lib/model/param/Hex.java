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

package org.qubership.automation.ss7lib.model.param;

public class Hex extends Number {

    /**
     * String representation of hex number.
     */
    private final String hex;

    /**
     * Constructor.
     *
     * @param hex - String representation of hex number.
     */
    public Hex(String hex) {
        this.hex = hex;
    }

    /**
     * Get integer parsed from the String value with radix = 16.
     *
     * @return integer parsed from the String value with radix = 16.
     */
    @Override
    public int intValue() {
        return Integer.parseInt(hex, 16);
    }

    /**
     * Get long parsed from the String value with radix = 16.
     *
     * @return long parsed from the String value with radix = 16.
     */
    @Override
    public long longValue() {
        return Long.parseLong(hex, 16);
    }

    /**
     * Get float parsed from the String value.
     *
     * @return float parsed from the String value.
     */
    @Override
    public float floatValue() {
        return Float.parseFloat(hex);
    }

    /**
     * Get double parsed from the String value.
     *
     * @return double parsed from the String value.
     */
    @Override
    public double doubleValue() {
        return Double.parseDouble(hex);
    }

    /**
     * Simply getter of hex field.
     *
     * @return String hex field.
     */
    public String stringHex() {
        return hex;
    }
}
