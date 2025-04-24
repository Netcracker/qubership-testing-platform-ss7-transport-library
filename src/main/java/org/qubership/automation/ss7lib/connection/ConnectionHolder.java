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

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.qubership.automation.ss7lib.decode.Utils;
import org.qubership.automation.ss7lib.proxy.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;
import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;
import com.sun.nio.sctp.SctpServerChannel;

public class ConnectionHolder {

    private static final ConnectionHolder SS_7_CONNECTION = new ConnectionHolder();
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionHolder.class);
    private static final String HOSTS = Config.getString("ss7.listen.hosts");
    private SctpChannel socketChannel;
    private final MessageProcessor messageProcessor = new MessageProcessor();
    private final ConnectionProcessor connectionProcessor = new ConnectionProcessor();
    private MessageInfo messageInfo;
    private Thread thread;
    private SctpServerChannel serverChannel;
    private boolean isReady = false;

    public static ConnectionHolder getInstance() {
        return SS_7_CONNECTION;
    }

    public void acceptConnection(int port) throws IOException {
        List<String> hosts = Splitter.on(',').splitToList(HOSTS);
        LOGGER.info("Start listening addresses: {}", hosts);
        /*SCTP Server
        Create a socket address in the current machine at port */
        Iterator<String> iterator = hosts.iterator();
        if (!iterator.hasNext()) {
            throw new IllegalStateException("No ip addresses to bind is specified");
        }
        SocketAddress serverSocketAddress = new InetSocketAddress(iterator.next(), port);
        LOGGER.info("Create and bind for sctp address");
        /*Open a server channel
        Bind the channel's socket to the server in the current machine at port */
        bindAddresses(iterator, serverSocketAddress);
        Set<SocketAddress> addresses = serverChannel.getAllLocalAddresses();
        for (SocketAddress address : addresses) {
            LOGGER.info("Started listening: {}", address.toString());
        }

        socketChannel = serverChannel.accept();
        if (socketChannel == null) {
            LOGGER.info("Timeout: nobody connected =(");
            return;
        }
        LOGGER.info("Remote address: {}. Association '{}'",
                socketChannel.getRemoteAddresses(),
                socketChannel.association());
    }

    private void bindAddresses(Iterator<String> hosts, SocketAddress serverSocketAddress) throws IOException {
        if (serverChannel == null) {
            serverChannel = SctpServerChannel.open().bind(serverSocketAddress);
            while (hosts.hasNext()) {
                InetAddress inetAddress = Inet4Address.getByName(hosts.next());
                LOGGER.info("Trying to bind address: {}", inetAddress);
                serverChannel.bindAddress(inetAddress);
            }
        }
        LOGGER.info("address bind process finished successfully");
    }

    public Thread runMainLoop() {
        thread = new Thread(() -> {
            LOGGER.info("Start listening requests from tango");
            try {
                receiveAndProcessMessages();
            } catch (Throwable e) {
                LOGGER.error("Unable to receive message", e);
            }
        });
        thread.setName("SCTP-Thread");
        thread.setUncaughtExceptionHandler((t, e) -> LOGGER.error("SCTP Thread is dead, please restart it.", e));
        thread.start();
        LOGGER.info("Request listener is successfully started");
        return thread;
    }

    public void stopMainLoop() {
        thread.interrupt();
        if (thread != null) {
            LOGGER.info("Wait answer before stop MainLoop, 60sec");
            try {
                thread.join(500);//wait thread answer x seconds
            } catch (Exception e) {
                LOGGER.error("Failed interrupt thread", e);
            }
        }
    }

    private void receiveAndProcessMessages() {
        while (!Thread.interrupted()) {
            try {
                LOGGER.info("start receiveAndProcessMessages");
                ByteBuffer buffer = ByteBuffer.allocate(640);
                MessageInfo messageInfo = socketChannel.receive(buffer, null, null);
                if (this.messageInfo == null) {
                    this.messageInfo = messageInfo;
                }
                buffer.flip();
                ByteBuffer data = Utils.subBuffer(buffer.limit(), buffer);
                Utils.logTrace("Received message:\n{}", Utils.class, data.array());
                if (isReady) {
                    messageProcessor.process(data);
                } else {
                    connectionProcessor.prepareConnection(data);
                }
            } catch (BufferUnderflowException ex) {
                LOGGER.info("Failed processing SS7 call", ex);
                break;
            } catch (Exception e) {
                LOGGER.info("Failed processing SS7 call", e);
            }
        }
    }


    public void send(ByteBuffer buffer) {
        try {
            if (socketChannel == null) {
                throw new IllegalStateException("Connection is not established yet");
            }
            socketChannel.send(buffer, messageInfo);
        } catch (IOException e) {
            LOGGER.error("Unable to send byte data: \n" + Utils.getAsHex(buffer.array()), e);
        }
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public boolean isConnected() {
        return !Objects.isNull(socketChannel);
    }
}
