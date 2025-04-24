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

package org.qubership.automation.ss7lib.model.sub.m3ua;

public class ProtocolData extends AbstractSubM3UAPojo {
    private int opc;
    private int dpc;
    private byte si;
    private byte ni;
    private byte mp;
    private byte sls;

    public int getOpc() {
        return opc;
    }

    public void setOpc(int opc) {
        this.opc = opc;
    }

    public int getDpc() {
        return dpc;
    }

    public void setDpc(int dpc) {
        this.dpc = dpc;
    }

    public byte getSi() {
        return si;
    }

    public void setSi(byte si) {
        this.si = si;
    }

    public byte getNi() {
        return ni;
    }

    public void setNi(byte ni) {
        this.ni = ni;
    }

    public byte getMp() {
        return mp;
    }

    public void setMp(byte mp) {
        this.mp = mp;
    }

    public byte getSls() {
        return sls;
    }

    public void setSls(byte sls) {
        this.sls = sls;
    }

    @Override
    public String toString() {
        return "ProtocolData{" + "opc=" + opc
                + ", dpc=" + dpc + ", si=" + si + ", ni=" + ni + ", mp=" + mp + ", sls=" + sls + super.toString() + '}';
    }
}
