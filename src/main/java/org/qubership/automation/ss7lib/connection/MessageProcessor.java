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

import java.nio.ByteBuffer;

import org.qubership.automation.ss7lib.decode.DecoderFactory;
import org.qubership.automation.ss7lib.model.FullMessage;
import org.qubership.automation.ss7lib.proxy.response.ResponseHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageProcessor {

    /**
     * Decode buffer, then apply gson.toJson to decoded message, then set response of ResponseHandler to the result.
     *
     * @param buffer - byte buffer to decode and process via Gson.
     */
    public void process(final ByteBuffer buffer) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        log.info("Start decoding message");
        FullMessage message = DecoderFactory.decode(buffer);
        String tx = "error";
        if (message.getTcap() != null) {
            tx = message.getTcap().getDestinationTransaction().getId();
        }
        ResponseHandler.setResponse(tx, gson.toJson(message));
    }
}
