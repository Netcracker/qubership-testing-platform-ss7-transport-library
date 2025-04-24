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

package org.qubership.automation.ss7lib.proxy;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.servlet.Servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.qubership.automation.ss7lib.annotation.RestController;
import org.qubership.automation.ss7lib.connection.ConnectionHolder;
import org.qubership.automation.ss7lib.proxy.config.Config;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyServer.class);

    public static void main(String[] args) throws Exception {
        LOGGER.info("Welcome to SS7 proxy for ITF");
        int tangoPort = Config.getInt("ss7.tango.port", 2906);
        LOGGER.info("Listening tango port: {}", tangoPort);
        int port = Config.getInt("ss7.server.port", 7766);
        LOGGER.info("Start http server at port: " + port);
        Server server = new Server(port);
        try {
            ServletHandler handler = new ServletHandler();
            registerControllers(handler);
            server.setHandler(handler);
            server.start();
            LOGGER.info("Waiting for tango connection");
            ConnectionHolder.getInstance().acceptConnection(tangoPort);
            LOGGER.info("Tango has been connected");
            Thread thread = ConnectionHolder.getInstance().runMainLoop();
            thread.join();
            server.join();
        } catch (Exception e) {
            LOGGER.error("Proxy server is not started", e);
            server.stop();
        }
    }

    private static void registerControllers(ServletHandler handler) {
        Reflections reflections = new Reflections("org.qubership.automation.ss7lib.proxy.controller");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(RestController.class);
        classes.forEach(clazz -> {
            for (Annotation annotation : clazz.getAnnotations()) {
                if (annotation instanceof RestController) {
                    RestController restController = (RestController) annotation;
                    handler.addServletWithMapping(clazz.asSubclass(Servlet.class), restController.value());
                    LOGGER.info("Servlet [{}] registered at '{}'", clazz.getSimpleName(), restController.value());
                    break;
                }
            }
        });
    }
}
