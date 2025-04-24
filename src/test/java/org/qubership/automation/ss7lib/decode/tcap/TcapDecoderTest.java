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

package org.qubership.automation.ss7lib.decode.tcap;

import org.qubership.automation.ss7lib.DumpReader;
import org.qubership.automation.ss7lib.decode.m3ua.M3uaDecoder;
import org.qubership.automation.ss7lib.decode.sccp.SccpDecoder;
import org.qubership.automation.ss7lib.model.CapMessage;
import org.qubership.automation.ss7lib.model.TcapMessage;
import org.qubership.automation.ss7lib.model.sub.cap.CapInvoke;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageRequestReportBCSMEventArg;
import org.qubership.automation.ss7lib.model.sub.tcap.Dialogue;
import org.qubership.automation.ss7lib.model.sub.tcap.Transaction;
import org.qubership.automation.ss7lib.model.type.EventType;
import org.qubership.automation.ss7lib.model.type.MonitorMode;
import org.qubership.automation.ss7lib.model.type.OpCodeType;
import org.qubership.automation.ss7lib.model.type.dialog.DialogServiceUser;
import org.qubership.automation.ss7lib.model.type.dialog.DialogueType;
import org.qubership.automation.ss7lib.model.type.dialog.Result;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TcapDecoderTest {
    @Test
    public void testDecodeTcap() throws IOException, URISyntaxException {
        ByteBuffer trace = getTrace("/test_data/requestReportBCSVMEvent.hexdump");
        TcapDecoder decoder = new TcapDecoder();
        TcapMessage message = decoder.decode(trace);
        assertEquals(-123, message.getTotalLength());
        validateTransaction("45001001", message.getSourceTransaction());
        validateTransaction("bdd90019", message.getDestinationTransaction());
        assertEquals("0.0.17.773.1.1.1", message.getOid().getOid());
        Dialogue dialogue = message.getDialogue();
        assertEquals(DialogueType.RESPONSE, dialogue.getDialogueType());
        assertEquals(7, dialogue.getPadding());
        assertEquals(-128, dialogue.getProtocolVersion());
        assertEquals(Result.ACCEPT, dialogue.getResult());
        assertTrue(dialogue.getResultSourceDiagnostic().getDialogServiceUser().contains(DialogServiceUser.NULL));
        assertEquals("0.4.0.0.1.0.50.1", dialogue.getApplicationContextName().getMessage());
        validateCap(message.getCapMessages());
    }

   
    @Test
    public void givenReportBCSM_and7digitInDestinationId_whenWeGetThisMessage_ThenDestinationIdHas0BeforeNumber() throws IOException, URISyntaxException {
        ByteBuffer trace = getTrace("/test_data/givenReportBCSM_and7digitInDestinationId.hexdump");
        TcapDecoder decoder = new TcapDecoder();
        TcapMessage message = decoder.decode(trace);
        validateTransaction("02001001", message.getDestinationTransaction());    }
    
    private void validateCap(List<CapMessage> capMessages) {
        assertEquals(2, capMessages.size());
        Iterator<CapMessage> iterator = capMessages.iterator();
        CapMessage firstCap = iterator.next();
        CapInvoke invoke = firstCap.getInvoke();
        assertEquals("1", invoke.getInvokeID().getStringBytes());
        assertEquals(OpCodeType.REQUEST_REPORT_BCSMEVENT, invoke.getOpCode().getOpCodeType());
        List<CAPMessageRequestReportBCSMEventArg.BSCMEvent> eventList = ((CAPMessageRequestReportBCSMEventArg) invoke.getCapMessagePojo()).getBscmEventList();
        assertEquals(4, eventList.size());
        CAPMessageRequestReportBCSMEventArg.BSCMEvent bscmEvent = eventList.get(eventList.size() - 1);
        assertEquals(EventType.T_ABANDON, bscmEvent.getEventType());
        assertEquals(MonitorMode.NOTIFY_AND_CONTINUE, bscmEvent.getMonitorMode());
        assertEquals(1, bscmEvent.getLegID().getId());
    }

    private void validateTransaction(String expectedTxId, Transaction transaction) {
        assertEquals(expectedTxId, transaction.getId());
        assertEquals(4, transaction.getLength());
    }

    private ByteBuffer getTrace(String path) throws IOException, URISyntaxException {
        ByteBuffer hexTrace = DumpReader.getHexTrace(path);
        M3uaDecoder m3uaDecoder = new M3uaDecoder();
        SccpDecoder sccpDecoder = new SccpDecoder();
        m3uaDecoder.decode(hexTrace);
        sccpDecoder.decode(hexTrace);
        return hexTrace;
    }


    @Test
    public void testParsingApplyCharging() throws IOException, URISyntaxException {
        ByteBuffer trace = getTrace("/test_data/ApplyCharging.hexdump");
        TcapDecoder decoder = new TcapDecoder();
        TcapMessage message = decoder.decode(trace);
        assertEquals(1, message.getCapMessages().size());
    }

    @Test
    public void testParsingReleaseCall() throws IOException, URISyntaxException {
        ByteBuffer trace = getTrace("/test_data/releaseCall.hexdump");
        TcapDecoder decoder = new TcapDecoder();
        TcapMessage message = decoder.decode(trace);
        assertEquals(1, message.getCapMessages().size());
    }
    
    @Test
    public void testParsingReleaseCall4Negative() throws IOException, URISyntaxException {
        ByteBuffer trace = getTrace("/test_data/releaseCallTerminate.hexdump");
        TcapDecoder decoder = new TcapDecoder();
        TcapMessage message = decoder.decode(trace);
        assertEquals(1, message.getCapMessages().size());
    }
    
    @Test
    public void testParsingRrbAndApplyCharging() throws IOException, URISyntaxException {
        ByteBuffer trace = getTrace("/test_data/rrb_and_apply_charging.hexdump");
        TcapDecoder decoder = new TcapDecoder();
        TcapMessage message = decoder.decode(trace);
        assertEquals(3, message.getCapMessages().size());
    }

    @Test
    public void testParseRequestReportBscmContinue() throws IOException, URISyntaxException {
        ByteBuffer trace = getTrace("/test_data/bugfix/requestReportBcsmIEventContinue.hexdump");
        TcapDecoder decoder = new TcapDecoder();
        TcapMessage decode = decoder.decode(trace);
        assertEquals("49001001", decode.getSourceTransaction().getId());
        assertEquals("f90f0108", decode.getDestinationTransaction().getId());
    }

    @Test
    public void testDecodeErbUnderflowError() throws IOException, URISyntaxException {
        ByteBuffer trace = getTrace("/test_data/bugfix/erb.decoding.textdump");
        TcapDecoder decoder = new TcapDecoder();
        TcapMessage decode = decoder.decode(trace);
        assertEquals("11001001", decode.getSourceTransaction().getId());
        assertEquals("00004935", decode.getDestinationTransaction().getId());
    }

    @Test
    public void testDecodeRequestReportBCSMEvent() throws IOException, URISyntaxException {
        ByteBuffer trace = getTrace("/test_data/bugfix/requestReportBCSMEvent.decoding.textdump");
        TcapDecoder decoder = new TcapDecoder();
        TcapMessage decode = decoder.decode(trace);
        assertEquals("07001001", decode.getSourceTransaction().getId());
    }


}
