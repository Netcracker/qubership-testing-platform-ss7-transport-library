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

package org.qubership.automation.ss7lib.parse.parser;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

public class StringParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringParser.class);


    /**
     * This parameter parse values by regexp, which provided by parameters #patterns.
     * Patterns must be provided in priority, because will be returned first found.
     *
     * @param property name of property which will be displayed in log, that this property failed or found
     * @param value    input to parse
     * @param patterns to parse input value
     * @return first value, which find for pattern
     */
    public String getValue(String property, String value, String... patterns) {
        if (Objects.isNull(patterns)) {
            throw new IllegalArgumentException("Pattern to parse  for property " + property + " is null");
        } else if (Objects.isNull(value)) {
            throw new IllegalArgumentException("Value to parse  for property " + property + " is null");
        } else if (Objects.isNull(property)) {
            throw new IllegalArgumentException("Property to parse is null");
        }
        String result = getVal(value, patterns);
        if (!Strings.isNullOrEmpty(result)) {
            LOGGER.info("Parse param : {} , value: {}", property, result);
            return result;
        }
        throw new IllegalArgumentException(String.format("Unable to parse property '%s', from input '%s' by patterns '%s'",
                property, value, Arrays.toString(patterns)));
    }

    @Nullable
    private String getVal(String value, String[] patterns) {
        for (String pattern : patterns) {
            Pattern compile = Pattern.compile(pattern);
            Matcher matcher = compile.matcher(value);
            if (matcher.find()) {
                String substring = value.substring(matcher.start(), matcher.end());
                String s = substring.replaceAll(":\\s+", "");
                return s.replaceAll("0x", "").replaceAll(" ", "");
            }
        }
        return null;
    }
}
