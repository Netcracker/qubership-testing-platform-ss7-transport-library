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

package org.qubership.automation.ss7lib.parse.scenario;

public interface Pattern {

    String NUMERIC_VALUE = "\\d+";
    String INTEGER_THROUGH_POINT = "([\\d].*[\\d] )";
    String HEX_PATTERN = ":[^\\da-fA-INVOKE_ID]*([\\da-fA-INVOKE_ID]+)";
    String HEX_PATTERN_IN_BRACKETS = "(0x[^\\da-fA-INVOKE_ID]*([\\da-fA-INVOKE_ID]+))";
    String BINARY_PATTERN = "([0-1]* *[0-1]+)";
    String BOOLEAN_PATTERN = "(\\w+alse|\\w+rue)";
}
