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

public class Binary extends Number {

    /**
     * String representation of binary number.
     */
    private final String binary;

    /**
     * Constructor.
     *
     * @param binaryString - String representation of binary number.
     */
    public Binary(final String binaryString) {
        this.binary = binaryString;
    }

    /**
     * Get integer parsed from the String value with radix = 2.
     *
     * @return integer parsed from the String value with radix = 2.
     */
    @Override
    public int intValue() {
        return Integer.parseInt(binary, 2);
    }

    /**
     * Get long parsed from the String value with radix = 2.
     *
     * @return long parsed from the String value with radix = 2.
     */
    @Override
    public long longValue() {
        return Long.parseLong(binary, 2);
    }

    /**
     * Get float parsed from the String value.
     *
     * @return float parsed from the String value.
     */
    @Override
    public float floatValue() {
        return Float.parseFloat(binary);
    }

    /**
     * Get double parsed from the String value.
     *
     * @return double parsed from the String value.
     */
    @Override
    public double doubleValue() {
        return Double.parseDouble(binary);
    }

    /**
     * Simply getter of binary field.
     *
     * @return String binary field.
     */
    public String stringBinary() {
        return binary;
    }
}
