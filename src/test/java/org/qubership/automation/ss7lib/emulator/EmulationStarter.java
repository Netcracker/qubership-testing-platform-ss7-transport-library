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

package org.qubership.automation.ss7lib.emulator;

import org.qubership.automation.ss7lib.proxy.ProxyServer;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class EmulationStarter {
    @Test
    public void start() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.execute(() -> {
            try {
                ProxyServer.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        pool.execute(() -> {
            try {
                new TangoEmulator().testStartEmulator();
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
        while (!Thread.interrupted()) {
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(50));
        }
    }
}
