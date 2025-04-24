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

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import org.qubership.automation.ss7lib.model.AbstractMessage;
import org.qubership.automation.ss7lib.parse.MessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public abstract class AbstractParser<T extends AbstractMessage> extends StringParser implements Parser<T> {
    private static final Pattern SPACES = Pattern.compile("^\\s+");
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractParser.class);

    @Override
    public T parse(T pojo, Iterator<String> iterator, MessageParser.StringCache currentString, String start,
                   String... end) {
        Level level;
        String previousString = currentString.get();
        List<String> parents = Lists.newArrayList();
        parents.add(start);
        while (iterator.hasNext()) {
            currentString.set(iterator.next());
            if (currentString.get().trim().isEmpty()) {
                continue;
            }
            LOGGER.debug("Input line: {}", currentString.get());
            for (String s : end) {
                if (currentString.get().contains(s)) {
                    return pojo;
                }
            }

            level = levelDefinition(previousString, currentString.get());
            if (level.equals(Level.DOWN)) {
                parents.add(previousString);
            } else if (level.equals(Level.UP)) {
                int i = 0;
                while (i >= 0 && iterator.hasNext()) {
                    if (parents.size() > 0) {
                        parents.remove(parents.size() - 1);
                    }
                    if (parents.size() > 0) {
                        i = stringLevel(parents.get(parents.size() - 1)) - stringLevel(currentString.get());
                    }
                    if (parents.size() == 0) {
                        break;
                    }
                }
            }
            parse(pojo, currentString.get().trim(), parents);
            previousString = currentString.get();
        }
        return pojo;
    }

    abstract void parse(T pojo, String value, List<String> parents);

    protected Level levelDefinition(String previousString, String currentString) {
        if (stringLevel(previousString) < stringLevel(currentString)) {
            return Level.DOWN;
        } else if (stringLevel(previousString) == stringLevel(currentString)) {
            return Level.SAME;
        } else if (stringLevel(previousString) > stringLevel(currentString)) {
            return Level.UP;
        }
        throw new IllegalArgumentException("Level message is not correct");
    }

    protected Level levelDefinition(List<String> values, int valuesNumber) {
        if (valuesNumber == 0) {
            return Level.SAME;
        }
        return levelDefinition(values.get(valuesNumber - 1), values.get(valuesNumber));
    }

    protected int stringLevel(String source) {
        Matcher matcher = SPACES.matcher(source);
        int level = 0;
        if (matcher.find()) {
            level = matcher.end();
        }
        return level;
    }


    protected boolean contain(String value, String property) {
        return value.toUpperCase().contains(property.toUpperCase());
    }

    protected List<Byte> splitHexStringBytes(@Nonnull String bytes) {
        List<Byte> result = Lists.newArrayList();
        String[] split = bytes.split("(?<=\\G.{2})");
        for (String s : split) {
            result.add((byte) Integer.parseInt(s, 16));
        }
        return result;
    }

    protected byte splitHexStringByte(@Nonnull String bytes) {
        String[] split = bytes.split("(?<=\\G.{2})");
        for (String s : split) {
            return (byte) Integer.parseInt(s, 16);
        }
        return 0;
    }


}
