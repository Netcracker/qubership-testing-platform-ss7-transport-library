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

package org.qubership.automation.ss7lib.proxy.service;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.qubership.automation.ss7lib.connection.ConnectionHolder;
import org.qubership.automation.ss7lib.decode.Utils;
import org.qubership.automation.ss7lib.encode.FullMessageEncoder;
import org.qubership.automation.ss7lib.model.FullMessage;
import org.qubership.automation.ss7lib.model.TcapMessage;
import org.qubership.automation.ss7lib.model.sub.tcap.Transaction;
import org.qubership.automation.ss7lib.parse.MessageParser;
import org.qubership.automation.ss7lib.proxy.response.ResponseHandler;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

public class ExecutionService {


    private static final String ERROR = "error";
    private static final String EMPTY = "";

    public String execute(String body) {
        MessageParser messageParser = new MessageParser();
        List<FullMessage> messages = messageParser.parse(body);
        String id = getId(messages.iterator().next().getTcap());

        ByteBuffer request = encodeMessages(messages);

        ConnectionHolder.getInstance().send(request);
        String response = ResponseHandler.getResponse(id);
        if (Strings.isNullOrEmpty(response)) {
            response = ResponseHandler.getResponse(ERROR);
        }
        LoggerFactory.getLogger(ExecutionService.class).warn("Response: \n{}", response);
        return response;
    }

    private ByteBuffer encodeMessages(List<FullMessage> messages) {
        AtomicInteger size = new AtomicInteger(0);
        List<ByteBuffer> requests = messages.stream().map(message -> {
            ByteBuffer request = FullMessageEncoder.encode(message);
            request.flip();
            size.set(size.get() + request.capacity());
            return request;
        }).collect(Collectors.toList());
        ByteBuffer buffer = ByteBuffer.allocate(size.get());
        requests.forEach(request -> buffer.put(Utils.calculateSize(request)));
        buffer.flip();
        return buffer;
    }

    private String getId(TcapMessage tcapMessage) {
        Transaction otid = tcapMessage.getSourceTransaction();
        Transaction dtid = tcapMessage.getDestinationTransaction();
        if (otid == null && dtid != null) {
            return dtid.getId();
        }
        if (otid != null) {
            return otid.getId();
        }
        return EMPTY;
    }

}
