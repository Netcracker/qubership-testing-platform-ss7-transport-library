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

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.qubership.automation.ss7lib.model.AbstractMessage;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class AbstractParserTest {

    private  String message =   "\n" +
            "MTP 3 User Adaptation Layer\n" +
            "   Version: Release 1 (1)\n"+
            "   Reserved: 0x00\n"+
            "   Message class: Transfer messages (1)\n"+
            "   Message Type: Payload data (DATA) (1)\n"+
            "   Message length: 244\n"+
            "   Network appearance (8)\n"+
            "       Parameter length: 8\n"+
            "       Parameter Tag: Network appearance (512)\n"+
            "       Network appearance: 8\n"+
            "   Routing context (1 context)\n"+
            "       Parameter Tag: Routing context (6)\n"+
            "       Parameter length: 8\n"+
            "       Routing context: 162";


    @Test
    public void levelDefinition() {
        AbstractParser parser = new AbstractParser() {
            @Override
            void parse(AbstractMessage pojo, String value, List parents) {

            }
            @Override
            public Level levelDefinition(String previousString, String currentString) {
                return super.levelDefinition(previousString, currentString);
            }
        };

        List<String> values = Lists.newArrayList(Splitter.on("\n").splitToList(message));
        List<Level> levels = Lists.newArrayList();
        List<Level> currentLevel = Lists.newArrayList();

        currentLevel.add(Level.SAME);
        currentLevel.add(Level.DOWN);
        currentLevel.add(Level.SAME);
        currentLevel.add(Level.SAME);
        currentLevel.add(Level.SAME);
        currentLevel.add(Level.SAME);
        currentLevel.add(Level.SAME);
        currentLevel.add(Level.DOWN);
        currentLevel.add(Level.SAME);
        currentLevel.add(Level.SAME);
        currentLevel.add(Level.UP);
        currentLevel.add(Level.DOWN);
        currentLevel.add(Level.SAME);
        currentLevel.add(Level.SAME);

        for (int i = 1; i < values.size(); i++) {
            levels.add(parser.levelDefinition(values.get(i - 1), values.get(i)));
        }

        assertArrayEquals(currentLevel.toArray(), levels.toArray());

    }
}
