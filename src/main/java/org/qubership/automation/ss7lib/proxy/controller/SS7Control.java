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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.qubership.automation.ss7lib.annotation.RestController;
import org.qubership.automation.ss7lib.proxy.service.ExecutionService;
import org.qubership.automation.ss7lib.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController("/execute")
public class SS7Control extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SS7Control.class);
    private static final ExecutionService EXECUTION_SERVICE = new ExecutionService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String body = IOUtils.toString(req.getReader());
            LOGGER.debug("Request body: " + body);
            String execute = EXECUTION_SERVICE.execute(body);
            LOGGER.info(execute);
            resp.getWriter().write(execute);
        } catch (Exception e) {
            LOGGER.error("Can't read request body", e);
            e.printStackTrace(resp.getWriter());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
