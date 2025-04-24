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

package org.qubership.automation.ss7lib.proxy.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

public class Config {
    private static final LinkedHashMap<Object, Object> PROPERTIES = new LinkedHashMap();

    static {
        init();
    }


    private static void init() {
        PROPERTIES.put("ss7.tango.port", 2906);
        PROPERTIES.put("ss7.server.port", 7766);
        PROPERTIES.put("ss7.response.time.sec", 5L);
        PROPERTIES.put("ss7.listen.hosts", "127.0.0.1");
        String config = System.getProperty("ss7.config");
        loadFromFile(config);
        Properties properties = System.getProperties();
        properties.forEach((key, value) -> {
            if (key != null && key.toString().startsWith("ss7")) {
                PROPERTIES.put(key, value);
            }
        });
    }

    private static void loadFromFile(String config) {
        if (Strings.isNullOrEmpty(config)) {
            return;
        }
        File file = new File(config);
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Properties properties = new Properties();
                properties.load(reader);
                PROPERTIES.putAll(properties);
            } catch (IOException e) {
                LoggerFactory.getLogger(Config.class).error("Can't read config file: " + config, e);
            }
        }
    }

    public static long getLong(String key) {
        return getLong(key, 0L);
    }

    public static long getLong(String key, long defValue) {
        String string = getString(key);
        if (Strings.isNullOrEmpty(string)) {
            return defValue;
        }
        return Long.parseLong(string);
    }

    public static int getInt(String key, int defaultValue) {
        String string = getString(key);
        if (Strings.isNullOrEmpty(string)) {
            return defaultValue;
        }
        return Integer.parseInt(string);
    }

    public static Map<Object, Object> getAll() {
        return Collections.unmodifiableMap(PROPERTIES);
    }

    public static String getString(String key) {
        Object o = PROPERTIES.get(key);
        if (o == null) {
            return "";
        }
        return o.toString().trim();
    }
}
