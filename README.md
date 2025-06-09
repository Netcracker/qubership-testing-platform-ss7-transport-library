# Qubership Testing Platform SS7 Transport Library

## Purpose
SS7 Transport Library is designed to send and receive messages via SS7 protocol.
It is used by Qubership Testing Platform ITF-Executor Service.

## Functionality description

- The library provides **SS7 Outbound Synchronous** Transport implementation only. 
- So, it establishes connection according configuration settings, then sends messages of special format, waits a response, receives it and parses.
- Messages can be configured 2 formats
  - binary format (just how it is sent via SS7 protocol),
  - and text format like Wireshark text representation of SS7 protocol messages. In that case, text message is encoded by the library into binary (byte[]) before sending
- Responses are decoded back from binary format into text format
- Encoders and Decoders handle all message layers: 
  - M3UA (MTP 3 User Adaptation Layer)
  - SCCP (Signalling Connection Control Part)
  - TCAP (Transaction Capabilities Application Part)
    - CAP (Capabilities Application Part)

## SS7 Transport configuration properties

- Server port
- Server hostname or IP-address
- Is proxy? (true/false)
- Connection port of proxy application
- Connection hostname or IP-address of proxy application

## Local build

In IntelliJ IDEA, one can select 'github' Profile in Maven Settings menu on the right, then expand Lifecycle dropdown of ss7-lib module, then select 'clean' and 'install' options and click 'Run Maven Build' green arrow button on the top.

Or, one can execute the command:
```bash
mvn -P github clean install
```

## How to add dependency into a service
```xml
    <!-- Change version number if necessary -->
    <dependency>
        <groupId>org.qubership.automation</groupId>
        <artifactId>ss7-lib</artifactId>
        <version>1.0.6-SNAPSHOT</version>
    </dependency>
```

In Qubership Testing Platform ITF-Executor Service, there is special transport module - **mockingbird-transport-ss7** - which uses this artifact.
In this module, there is a class SS7OutboundTransport, which contains:
- SS7 Transport Configuration Parameters Descriptions (see above for transport configuration properties list),
- sendReceiveSync() method as a main entry point, performing:
  - request sending (connection is established if necessary),
  - and waiting a response,
- extra service methods.
