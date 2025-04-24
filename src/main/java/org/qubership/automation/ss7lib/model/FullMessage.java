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

package org.qubership.automation.ss7lib.model;

public class FullMessage {
    private M3uaMessage m3ua;
    private SccpMessage sccp;
    private TcapMessage tcap;
    private ErrorMessage errorMessage;

    public void setM3ua(M3uaMessage m3ua) {
        this.m3ua = m3ua;
    }

    public void setSccp(SccpMessage sccp) {
        this.sccp = sccp;
    }

    public SccpMessage getSccp() {
        return sccp;
    }

    public void setTcap(TcapMessage tcap) {
        this.tcap = tcap;
    }

    public TcapMessage getTcap() {
        return tcap;
    }

    public M3uaMessage getM3ua() {
        return m3ua;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
