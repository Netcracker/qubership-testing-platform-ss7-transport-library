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

package org.qubership.automation.ss7lib.proxy.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.qubership.automation.ss7lib.annotation.RestController;
import org.qubership.automation.ss7lib.connection.ConnectionHolder;
import org.qubership.automation.ss7lib.proxy.config.Config;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

@RestController("/")
public class SS7WelcomePage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        print(writer, () -> {
            print(writer, () -> {
                boolean connected = ConnectionHolder.getInstance().isConnected();
                String state = connected ? "established" : "not connected";
                return "Connection State: " + state;
            }, "h4");
            print(writer, () -> "Config", "h4");
            print(writer, () -> {
                print(writer, () -> {
                    print(writer, () -> "Key", "th");
                    print(writer, () -> "Value", "th");
                    return "";
                }, "tr");
                Config.getAll().forEach((key, value) -> {
                    print(writer, () -> {
                        print(writer, () -> key, "td");
                        print(writer, () -> value, "td");
                        return "";
                    }, "tr");
                });
                return "";
            }, "table", "border=1");
            return "";
        }, "body");
        writer.flush();
    }

    private void print(PrintWriter writer, Callable value, final String tag) {
        print(writer, value, tag, null);
    }

    private void print(PrintWriter writer, Callable value, final String tag, final String args) {
        if (Strings.isNullOrEmpty(args)) {
            writer.println('<' + tag + '>');
        } else {
            writer.println('<' + tag + ' ' + args + '>');
        }
        try {
            writer.println(value.call());
        } catch (Exception e) {
            LoggerFactory.getLogger(SS7WelcomePage.class).error("Can't print tag: " + tag, e);
        }
        writer.println("</" + tag + '>');
    }

    private interface Callable {
        Object call();
    }

}
