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

package org.qubership.automation.ss7lib.model.type;

import java.nio.ByteBuffer;

import org.qubership.automation.ss7lib.decode.tcap.cap.ApplyChargingArgDecoder;
import org.qubership.automation.ss7lib.decode.tcap.cap.CapDecoderFactory;
import org.qubership.automation.ss7lib.decode.tcap.cap.EventRequestBcsmDecoder;
import org.qubership.automation.ss7lib.decode.tcap.cap.ReleaseCallDecoder;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessagePojo;
import org.qubership.automation.ss7lib.model.sub.cap.message.CAPMessageRequestReportBCSMEventArg;
import org.qubership.automation.ss7lib.model.sub.cap.message.InitialDetectionPoint;

public enum OpCodeType implements CommonEnum<CAPMessagePojo> {
    INITIAL_DP((byte) 0) {
        @Override
        public InitialDetectionPoint _decode(ByteBuffer buffer) {
            return null;
        }
    },
    ASSIST_REQUEST_INSTRUCTIONS((byte) 16) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    ESTABLISH_TEMPORARY_CONNECTION((byte) 17) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    DISCONNECT_FORWARD_CONNECTION((byte) 18) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    D_FCWITH_ARGUMENT((byte) 86) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    CONNECT_TO_RESOURCE((byte) 19) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    CONNECT((byte) 20) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    RELEASE_CALL((byte) 22) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return CapDecoderFactory.decode(ReleaseCallDecoder.class, buffer);
        }
    },
    REQUEST_REPORT_BCSMEVENT((byte) 23) {
        @Override
        public CAPMessageRequestReportBCSMEventArg _decode(ByteBuffer buffer) {
            return CapDecoderFactory.decode(EventRequestBcsmDecoder.class, buffer);
        }
    },
    EVENT_REPORT_BCSM((byte) 24) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    COLLECT_INFORMATION((byte) 27) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    CONTINUE((byte) 31) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    INITIATE_CALL_ATTEMPT((byte) 32) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    RESET_TIMER((byte) 33) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    FURNISH_CHARGING_INFORMATION((byte) 34) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    APPLY_CHARGING((byte) 35) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return CapDecoderFactory.decode(ApplyChargingArgDecoder.class, buffer);
        }
    },
    APPLY_CHARGING_REPORT((byte) 36) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    CALL_GAP((byte) 41) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    CALL_INFORMATION_REPORT((byte) 44) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    CALL_INFORMATION_REQUEST((byte) 45) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    SEND_CHARGING_INFORMATION((byte) 46) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    PLAY_ANNOUNCEMENT((byte) 47) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    PROMPT_AND_COLLECT_USER_INFORMATION((byte) 48) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    SPECIALIZED_RESOURCE_REPORT((byte) 49) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    CANCEL((byte) 53) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    ACTIVITY_TEST((byte) 55) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    CONTINUE_WITH_ARGUMENT((byte) 88) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    DISCONNECT_LEG((byte) 90) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    MOVE_LEG((byte) 93) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    SPLIT_LEG((byte) 95) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    ENTITY_RELEASED((byte) 96) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    PLAY_TONE((byte) 97) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    INITIAL_DPSMS((byte) 60) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    FURNISH_CHARGING_INFORMATION_SMS((byte) 61) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    CONNECT_SMS((byte) 62) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    REQUEST_REPORT_SMSEVENT((byte) 63) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    EVENT_REPORT_SMS((byte) 64) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    CONTINUE_SMS((byte) 65) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    RELEASE_SMS((byte) 66) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    RESET_TIMER_SMS((byte) 67) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    ACTIVITY_TEST_GPRS((byte) 70) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    APPLY_CHARGING_GPRS((byte) 71) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    APPLY_CHARGING_REPORT_GPRS((byte) 72) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    CANCEL_GPRS((byte) 73) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    CONNECT_GPRS((byte) 74) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    CONTINUE_GPRS((byte) 75) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    ENTITY_RELEASED_GPRS((byte) 76) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    FURNISH_CHARGING_INFORMATION_GPRS((byte) 77) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    INITIAL_DPGPRS((byte) 78) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    RELEASE_GPRS((byte) 79) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    EVENT_REPORT_GPRS((byte) 80) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    REQUEST_REPORT_GPRSEVENT((byte) 81) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    RESET_TIMER_GPRS((byte) 82) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    },
    SEND_CHARGING_INFORMATION_GPRS((byte) 83) {
        @Override
        public CAPMessagePojo _decode(ByteBuffer buffer) {
            return null;
        }
    };

    private final byte id;

    OpCodeType(byte id) {
        this.id = id;
    }


    public <T extends CAPMessagePojo> T decode(ByteBuffer buffer) {
        return (T) _decode(buffer);
    }

    @Override
    public byte getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return asPrintable();
    }
}
