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

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorMessage extends AbstractMessage {

    /**
     * Error message combined with the stacktrace.
     */
    private String error;

    /**
     * Constructor from an Exception.
     *
     * @param exception Exception to be encapsulated into a new Message.
     */
    public ErrorMessage(final Exception exception) {
        this.error = exception.getMessage();
        this.error += '\n' + getStackTrace(exception);
    }

    private String getStackTrace(final Exception exception) {
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            stringBuilder.append(stackTraceElement).append('\n');
        }
        return stringBuilder.toString();
    }

}
