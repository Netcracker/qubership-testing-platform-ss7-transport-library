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

package org.qubership.automation.ss7lib.model.sub.sccp;

import java.util.Objects;

public class AddressIndicator {
    private byte nationalIndicator;
    private byte routingIndicator;
    private byte globalTitleIndicator;
    private byte pointCodeIndicator;
    private byte subSystemNumberIndicator;

    public byte getNationalIndicator() {
        return nationalIndicator;
    }

    public void setNationalIndicator(byte nationalIndicator) {
        this.nationalIndicator = nationalIndicator;
    }

    public byte getRoutingIndicator() {
        return routingIndicator;
    }

    public void setRoutingIndicator(byte routingIndicator) {
        this.routingIndicator = routingIndicator;
    }

    public byte getGlobalTitleIndicator() {
        return globalTitleIndicator;
    }

    public void setGlobalTitleIndicator(byte globalTitleIndicator) {
        this.globalTitleIndicator = globalTitleIndicator;
    }

    public byte getPointCodeIndicator() {
        return pointCodeIndicator;
    }

    public void setPointCodeIndicator(byte pointCodeIndicator) {
        this.pointCodeIndicator = pointCodeIndicator;
    }

    public byte getSubSystemNumberIndicator() {
        return subSystemNumberIndicator;
    }

    public void setSubSystemNumberIndicator(byte subSystemNumberIndicator) {
        this.subSystemNumberIndicator = subSystemNumberIndicator;
    }

    @Override
    public String toString() {
        return "AddressIndicator{"
                + "nationalIndicator=" + nationalIndicator
                + ", routingIndicator=" + routingIndicator
                + ", globalTitleIndicator=" + globalTitleIndicator
                + ", pointCodeIndicator=" + pointCodeIndicator
                + ", subSystemNumberIndicator=" + subSystemNumberIndicator + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddressIndicator that = (AddressIndicator) o;
        return nationalIndicator == that.nationalIndicator
                && routingIndicator == that.routingIndicator
                && globalTitleIndicator == that.globalTitleIndicator
                && pointCodeIndicator == that.pointCodeIndicator
                && subSystemNumberIndicator == that.subSystemNumberIndicator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nationalIndicator, routingIndicator, globalTitleIndicator, pointCodeIndicator,
                subSystemNumberIndicator);
    }
}
