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

package org.qubership.automation.ss7lib.parse;

import java.util.Map;
import java.util.Objects;

import org.qubership.automation.ss7lib.model.AbstractMessage;
import org.qubership.automation.ss7lib.model.CapMessage;
import org.qubership.automation.ss7lib.model.M3uaMessage;
import org.qubership.automation.ss7lib.model.SccpMessage;
import org.qubership.automation.ss7lib.model.TcapMessage;
import org.qubership.automation.ss7lib.parse.parser.CAPParser;
import org.qubership.automation.ss7lib.parse.parser.M3UAParser;
import org.qubership.automation.ss7lib.parse.parser.Parser;
import org.qubership.automation.ss7lib.parse.parser.SCCParser;
import org.qubership.automation.ss7lib.parse.parser.TCAPParser;

import com.google.common.collect.Maps;

public class ParserFactory {

    private static ParserFactory INSTANCE = new ParserFactory();

    private Map<Class<? extends AbstractMessage>, Parser> map = Maps.newHashMap();


    private ParserFactory() {
        map.put(M3uaMessage.class, new M3UAParser());
        map.put(SccpMessage.class, new SCCParser());
        map.put(TcapMessage.class, new TCAPParser());
        map.put(CapMessage.class, new CAPParser());
    }

    public static ParserFactory getInstance() {
        return INSTANCE;
    }

    public static <T extends AbstractMessage> Parser<T> parserFor(Class<T> clazz) {
        return getInstance().getParser(clazz);
    }

    public <T extends AbstractMessage> Parser<T> getParser(Class<T> clazz) {
        Parser parser = map.get(clazz);
        if (Objects.isNull(parser)) {
            throw new IllegalArgumentException("Parser for " + clazz.getName() + " is null");

        }
        return parser;
    }
}
