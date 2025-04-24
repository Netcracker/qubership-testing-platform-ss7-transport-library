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

package org.qubership.automation.ss7lib.proxy.response;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.qubership.automation.ss7lib.proxy.config.Config;
import org.qubership.automation.ss7lib.proxy.service.StripedProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class ResponseHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseHandler.class);


    private static final Cache<String, String> MESSAGE_HOLDER = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES).build();

    public static void setResponse(String tx, String message) {
        LOGGER.info("Put message: {}", tx);
        MESSAGE_HOLDER.put(tx, message);
        Lock lock = StripedProvider.getLock(tx);
        synchronized (lock) {
            lock.notify();
        }
    }

    public static String getResponse(String tx) {
        LOGGER.info("Response for message by id: " + tx);
        final Lock lock = StripedProvider.getLock(tx);
        try {
            if (!"error".equals(tx)) {
                synchronized (lock) {
                    LOGGER.info("LockTx: {}", tx);
                    lock.wait(TimeUnit.SECONDS.toMillis(Config.getLong("ss7.response.time.sec")));
                }
            }
        } catch (InterruptedException e) {
            LOGGER.error("Can't wait response, thread is interrupted", e);
        }
        LOGGER.info("Get message: {}", tx);
        String builder = MESSAGE_HOLDER.getIfPresent(tx);
        if (builder == null) {
            LOGGER.error("Response is not for: " + tx);
            return "";
        }
        destroy(tx);
        return builder;
    }

    private static void destroy(String tx) {
        MESSAGE_HOLDER.invalidate(tx);
    }

}
