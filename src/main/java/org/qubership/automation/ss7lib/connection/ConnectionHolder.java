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

import com.google.common.base.Splitter;
import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;
import com.sun.nio.sctp.SctpServerChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionHolder {

    /**
     * Self-link to use outside via getInstance() method.
     */
    private static final ConnectionHolder SS_7_CONNECTION = new ConnectionHolder();

    /**
     * Hosts list to listen (names/addresses are split by ',').
     */
    private static final String HOSTS = Config.getString("ss7.listen.hosts");

    /**
     * Size of buffer allocated to read received message.
     */
    private static final int BUFFER_ALLOCATION_SIZE = 640;

    /**
     * Timeout how long to wait interrupted thread completion.
     */
    private static final long THREAD_INTERRUPT_WAIT_TIME_MILLIS = 500L;

    /**
     * SCTP channel.
     */
    private SctpChannel socketChannel;

    /**
     * Processor which performs decoding of received message and serializing it to json.
     */
    private final MessageProcessor messageProcessor = new MessageProcessor();

    /**
     * Processor sending proper response according to the type of received message.
     */
    private final ConnectionProcessor connectionProcessor = new ConnectionProcessor();

    /**
     * Object containing ancillary information about the received message.
     */
    private MessageInfo messageInfo;

    /**
     * Thread which is created and started to execute main loop listening messages.
     */
    private Thread thread;

    /**
     * Channel to SCTP server.
     */
    private SctpServerChannel serverChannel;

    /**
     * Flag if Connection Holder instance is ready (true) or not.
     */
    @lombok.Setter
    @lombok.Getter
    private boolean isReady = false;

    /**
     * Get the instance of ConnectionHolder.
     *
     * @return instance of ConnectionHolder.
     */
    public static ConnectionHolder getInstance() {
        return SS_7_CONNECTION;
    }

    /**
     * Establish connection on port given.
     *
     * @param port port number to listen
     * @throws IOException if exception is faced while connection establishing.
     */
    public void acceptConnection(final int port) throws IOException {
        List<String> hosts = Splitter.on(',').splitToList(HOSTS);
        log.info("Start listening addresses: {}", hosts);
        /* SCTP Server: Create a socket address in the current machine at port */
        Iterator<String> iterator = hosts.iterator();
        if (!iterator.hasNext()) {
            throw new IllegalStateException("No ip addresses to bind is specified");
        }
        SocketAddress serverSocketAddress = new InetSocketAddress(iterator.next(), port);
        log.info("Create and bind for sctp address");
        /* Open a server channel; Bind the channel's socket to the server in the current machine at port */
        bindAddresses(iterator, serverSocketAddress);
        Set<SocketAddress> addresses = serverChannel.getAllLocalAddresses();
        for (SocketAddress address : addresses) {
            log.info("Started listening: {}", address.toString());
        }

        socketChannel = serverChannel.accept();
        if (socketChannel == null) {
            log.info("Timeout: nobody connected =(");
            return;
        }
        log.info("Remote address: {}. Association '{}'",
                socketChannel.getRemoteAddresses(),
                socketChannel.association());
    }

    private void bindAddresses(final Iterator<String> hosts,
                               final SocketAddress serverSocketAddress) throws IOException {
        if (serverChannel == null) {
            serverChannel = SctpServerChannel.open().bind(serverSocketAddress);
            while (hosts.hasNext()) {
                InetAddress inetAddress = Inet4Address.getByName(hosts.next());
                log.info("Trying to bind address: {}", inetAddress);
                serverChannel.bindAddress(inetAddress);
            }
        }
        log.info("address bind process finished successfully");
    }

    /**
     * Create and start thread of requests listener.
     *
     * @return thread of requests listener.
     */
    public Thread runMainLoop() {
        thread = new Thread(() -> {
            log.info("Start listening requests from tango");
            try {
                receiveAndProcessMessages();
            } catch (Throwable e) {
                log.error("Unable to receive message", e);
            }
        });
        thread.setName("SCTP-Thread");
        thread.setUncaughtExceptionHandler((t, e) -> log.error("SCTP Thread is dead, please restart it.", e));
        thread.start();
        log.info("Request listener is successfully started");
        return thread;
    }

    /**
     * Interrupt thread which executes main listener loop.
     */
    public void stopMainLoop() {
        thread.interrupt();
        if (thread != null) {
            log.info("Wait answer before stop MainLoop, {} millis", THREAD_INTERRUPT_WAIT_TIME_MILLIS);
            try {
                thread.join(THREAD_INTERRUPT_WAIT_TIME_MILLIS); // wait thread answer
            } catch (Exception e) {
                log.error("Failed to interrupt thread", e);
            }
        }
    }

    private void receiveAndProcessMessages() {
        while (!Thread.interrupted()) {
            try {
                log.info("start receiveAndProcessMessages");
                ByteBuffer buffer = ByteBuffer.allocate(BUFFER_ALLOCATION_SIZE);
                MessageInfo messageReceived = socketChannel.receive(buffer, null, null);
                if (this.messageInfo == null) {
                    this.messageInfo = messageReceived;
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
                log.info("Failed processing SS7 call", ex);
                break;
            } catch (Exception e) {
                log.info("Failed processing SS7 call", e);
            }
        }
    }

    /**
     * Send byte buffer via the socketChannel.
     *
     * @param buffer - byte buffer to send.
     */
    public void send(final ByteBuffer buffer) {
        try {
            if (socketChannel == null) {
                throw new IllegalStateException("Connection is not established yet");
            }
            socketChannel.send(buffer, messageInfo);
        } catch (IOException e) {
            log.error("Unable to send byte data: \n{}", Utils.getAsHex(buffer.array()), e);
        }
    }

    /**
     * Check if this socketChannel is connected.
     *
     * @return true if this socketChannel is not null, otherwise false.
     */
    public boolean isConnected() {
        return !Objects.isNull(socketChannel);
    }
}
