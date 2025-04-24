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

import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.ANSI_STANDARD;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CAMEL;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.COMPONENTS;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.ITU_STANDARD;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.MTP_3_USER_ADAPTATION_LAYER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.SIGNALLING_CONNECTION_CONTROL_PART;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.TRANSACTION_CAPABILITIES_APPLICATION_PART;
import static org.qubership.automation.ss7lib.parse.ParserFactory.parserFor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.qubership.automation.ss7lib.model.CapMessage;
import org.qubership.automation.ss7lib.model.FullMessage;
import org.qubership.automation.ss7lib.model.M3uaMessage;
import org.qubership.automation.ss7lib.model.SccpMessage;
import org.qubership.automation.ss7lib.model.TcapMessage;
import org.qubership.automation.ss7lib.model.type.Standard;
import org.qubership.automation.ss7lib.parse.parser.Parser;
import org.qubership.automation.ss7lib.parse.parser.StringParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class MessageParser extends StringParser {

    private LinkedList<FullMessage> messages = new LinkedList<>();
    private Logger logger = LoggerFactory.getLogger(MessageParser.class);

    public List<FullMessage> parse(String strMessage) {
        for (String message : strMessage.split("(?=MTP 3 User Adaptation Layer)")) {
            parseMessage(message);
        }
        return messages;
    }

    private void parseMessage(String strMessage) {
        List<String> values = Lists.newArrayList(Splitter.on('\n').split(strMessage));

        Iterator<String> iterator = values.iterator();

        TcapMessage tcapMessage = null;
        StringCache currentString = new StringCache();
        boolean isITUProtocol = false;

        while (iterator.hasNext()) {
            currentString.set(iterator.next());
            if (currentString.get().contains(MTP_3_USER_ADAPTATION_LAYER)) {
                messages.add(new FullMessage());
                logger.info("Parsing new one message");
                M3uaMessage m3uaMessage = new M3uaMessage();

                getMessage().setM3ua(
                        parserFor(M3uaMessage.class).parse(
                                m3uaMessage, iterator, currentString,
                                MTP_3_USER_ADAPTATION_LAYER, ANSI_STANDARD,
                                SIGNALLING_CONNECTION_CONTROL_PART, ITU_STANDARD
                        ));

            }
            if (currentString.get().contains(ITU_STANDARD)) {
                isITUProtocol = true;
                getMessage().getM3ua().setStandard(Standard.ITU);
            }
            logger.info("Message type itu: {}", isITUProtocol);
            if (currentString.get().contains(SIGNALLING_CONNECTION_CONTROL_PART)) {
                SccpMessage sccpMessage = new SccpMessage();
                sccpMessage.setITUProtocol(isITUProtocol);

                getMessage().setSccp(
                        parserFor(SccpMessage.class)
                                .parse(
                                        sccpMessage, iterator, currentString,
                                        SIGNALLING_CONNECTION_CONTROL_PART, TRANSACTION_CAPABILITIES_APPLICATION_PART)
                );
            }
            if (Objects.isNull(tcapMessage)) {
                tcapMessage = new TcapMessage();
            }
            if (currentString.get().contains(TRANSACTION_CAPABILITIES_APPLICATION_PART)) {
                parserFor(TcapMessage.class).parse(tcapMessage, iterator, currentString, TRANSACTION_CAPABILITIES_APPLICATION_PART, CAMEL, COMPONENTS);
                getMessage().setTcap(tcapMessage);
            }
            if (currentString.get().contains(CAMEL)) {
                Parser<CapMessage> parser = parserFor(CapMessage.class);
                while (iterator.hasNext()) {
                    if (currentString.get().contains(CAMEL)) {
                        CapMessage capMessage = new CapMessage();
                        parser.parse(capMessage, iterator, currentString, CAMEL, CAMEL, COMPONENTS);
                        tcapMessage.getCapMessages().add(capMessage);
                    }
                }
            }
        }
    }

    private FullMessage getMessage() {
        return messages.getLast();
    }


    public static class StringCache {
        private String string;

        public String get() {
            return string;
        }

        public void set(String string) {
            this.string = string;
        }
    }

}
