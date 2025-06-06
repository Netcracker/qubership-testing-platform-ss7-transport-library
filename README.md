# Qubership Testing Platform SS7 Transport Library

## Purpose
SS7 Transport Library is designed to send and receive messages via SS7 protocol.
It is used by Qubership Testing Platform ITF-Executor Service.

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

