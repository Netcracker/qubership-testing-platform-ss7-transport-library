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

package org.qubership.automation.ss7lib.connection;

import com.google.common.base.Stopwatch;
import org.qubership.automation.ss7lib.DumpReader;
import org.qubership.automation.ss7lib.proxy.response.ResponseHandler;
import org.hamcrest.core.StringContains;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MessageProcessorTest {
    @Test
    public void testProcessData() throws IOException, URISyntaxException, InterruptedException {
        ByteBuffer trace = DumpReader.getHexTrace("/test_data/requestReportBCSVMEvent.hexdump");
        MessageProcessor processor = new MessageProcessor();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(2));
            } catch (InterruptedException e) {
                LoggerFactory.getLogger(MessageProcessorTest.class).error("Can't wait", e);
            }
            processor.process(trace);
        });
        Stopwatch timer = Stopwatch.createStarted();
        thread.start();
        assertThat(ResponseHandler.getResponse("bdd90019"), StringContains.containsString("bdd90019"));
        timer.stop();
        LoggerFactory.getLogger(MessageProcessor.class).info("Message processed in: {}", timer);
        assertTrue(timer.elapsed(TimeUnit.SECONDS) >= 2);

    }
}
