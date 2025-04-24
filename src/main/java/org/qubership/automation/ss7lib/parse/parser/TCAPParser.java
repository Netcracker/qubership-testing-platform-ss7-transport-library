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

package org.qubership.automation.ss7lib.parse.parser;

import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.APPLICATION_CONTEXT_NAME;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.BEGIN;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.CONTINUE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.DESTINATION_TRANSACTION_ID;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.DIALOGUE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.DIALOGUE_REQUEST;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.DIALOGUE_RESPONSE;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.DIALOGUE_SERVICE_USER;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.END;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.OID;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.PADDING;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.PROTOCOL_VERSION;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.RESULT;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.RESULT_SOURCE_DIAGNOSTIC;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.SOURCE_TRANSACTION_ID;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.TID;
import static org.qubership.automation.ss7lib.model.SS7PropertiesPojo.TRANSACTION_ID;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.qubership.automation.ss7lib.model.TcapMessage;
import org.qubership.automation.ss7lib.model.sub.tcap.ApplicationContextName;
import org.qubership.automation.ss7lib.model.sub.tcap.Dialogue;
import org.qubership.automation.ss7lib.model.sub.tcap.Oid;
import org.qubership.automation.ss7lib.model.sub.tcap.ResultSourceDiagnostic;
import org.qubership.automation.ss7lib.model.sub.tcap.Transaction;
import org.qubership.automation.ss7lib.model.type.EnumProvider;
import org.qubership.automation.ss7lib.model.type.TCAPType;
import org.qubership.automation.ss7lib.model.type.TransactionID;
import org.qubership.automation.ss7lib.model.type.dialog.DialogServiceUser;
import org.qubership.automation.ss7lib.model.type.dialog.DialogueType;
import org.qubership.automation.ss7lib.model.type.dialog.Result;
import org.qubership.automation.ss7lib.parse.scenario.ScenarioManager;

public class TCAPParser extends AbstractParser<TcapMessage> {
    @Override
    void parse(TcapMessage pojo, String value, List<String> parents) {
        String parent = parents.get(parents.size() - 1);
        if (parent.contains(BEGIN) || parent.contains(CONTINUE) || parent.contains(END)) {
            if (parent.contains(BEGIN)) {
                pojo.setType(TCAPType.BEGIN);
            } else if (parent.contains(CONTINUE)) {
                pojo.setType(TCAPType.CONTINUE);
            } else if (parent.contains(END)) {
                pojo.setType(TCAPType.END);
            }
            Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getClass());
            if (contain(value, OID)) {
                Oid oid = new Oid();
                oid.setOid(getValue(OID, value.trim(), scenario.get(OID)));
                pojo.setOid(oid);
            }
        } else if (parent.contains(TRANSACTION_ID)) {
            if (parent.contains(SOURCE_TRANSACTION_ID)) {
                if (Objects.isNull(pojo.getSourceTransaction())) {
                    pojo.setSourceTransaction(new Transaction());
                    pojo.getSourceTransaction().setTransactionID(TransactionID.SOURCE);
                }

                if (contain(value, TID)) {
                    parseTransactionID(pojo.getSourceTransaction(), value);
                }
            } else if (parent.contains(DESTINATION_TRANSACTION_ID)) {
                if (Objects.isNull(pojo.getDestinationTransaction())) {
                    pojo.setDestinationTransaction(new Transaction());
                    pojo.getDestinationTransaction().setTransactionID(TransactionID.DESTINATION);
                }
                if (contain(value, TID)) {
                    parseTransactionID(pojo.getDestinationTransaction(), value);
                }
            }

        } else if (parent.contains(RESULT_SOURCE_DIAGNOSTIC)) {
            Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getDialogue().getResultSourceDiagnostic().getClass());

            if (contain(value, DIALOGUE_SERVICE_USER)) {
                pojo.getDialogue().getResultSourceDiagnostic().getDialogServiceUser().add(EnumProvider.of(Byte.parseByte(getValue(DIALOGUE_SERVICE_USER, value.trim(), scenario.get(DIALOGUE_SERVICE_USER))), DialogServiceUser.class));
            }
        } else if (parent.contains(DIALOGUE)) {
            if (Objects.isNull(pojo.getDialogue())) {
                pojo.setDialogue(new Dialogue());
                if (parent.contains(DIALOGUE_RESPONSE)) {
                    pojo.getDialogue().setDialogueType(DialogueType.RESPONSE);
                } else if (parent.contains(DIALOGUE_REQUEST)) {
                    pojo.getDialogue().setDialogueType(DialogueType.REQUEST);
                }
            }
            Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getDialogue().getClass());
            if (contain(value, PADDING)) {
                pojo.getDialogue().setPadding((byte) Integer.parseInt(getValue(PADDING, value.trim(), scenario.get(PADDING)), 16));
            } else if (contain(value, PROTOCOL_VERSION)) {
                pojo.getDialogue().setProtocolVersion((byte) Integer.parseInt(getValue(PROTOCOL_VERSION, value.trim(), scenario.get(PROTOCOL_VERSION)), 16));
            } else if (contain(value, APPLICATION_CONTEXT_NAME)) {
                if (Objects.isNull(pojo.getDialogue().getApplicationContextName())) {
                    pojo.getDialogue().setApplicationContextName(new ApplicationContextName());
                }
                pojo.getDialogue().getApplicationContextName().setMessage(getValue(APPLICATION_CONTEXT_NAME, value.trim(), scenario.get(APPLICATION_CONTEXT_NAME)));
            } else if (contain(value, RESULT_SOURCE_DIAGNOSTIC)) {
                if (Objects.isNull(pojo.getDialogue().getResultSourceDiagnostic())) {
                    pojo.getDialogue().setResultSourceDiagnostic(new ResultSourceDiagnostic());
                }
                pojo.getDialogue().getResultSourceDiagnostic().setId(Byte.parseByte(getValue(RESULT_SOURCE_DIAGNOSTIC, value.trim(), scenario.get(RESULT_SOURCE_DIAGNOSTIC))));
            } else if (contain(value, RESULT)) {
                pojo.getDialogue().setResult(EnumProvider.of(Byte.parseByte(getValue(RESULT, value.trim(), scenario.get(RESULT))), Result.class));
            }
        }
    }

    private void parseTransactionID(Transaction pojo, String value) {
        Map<String, String> scenario = ScenarioManager.getInstance().getScenario(pojo.getClass());
        if (contain(value, TID)) {
            pojo.setId(getValue(TID, value.trim(), scenario.get(TID)));
        }
    }
}
