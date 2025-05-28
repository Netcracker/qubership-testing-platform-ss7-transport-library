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

package org.qubership.automation.ss7lib.encode.cap;

import java.nio.ByteBuffer;
import java.util.List;

import org.qubership.automation.ss7lib.decode.Utils;
import org.qubership.automation.ss7lib.encode.PartEncoder;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.PartyNumber;

public class PartyNumberEncoder implements PartEncoder<PartyNumber> {

    /**
     * Encode PartyNumber into ByteBuffer.
     *
     * @param partyNumber PartyNumber object to be encoded
     * @return ByteBuffer containing the result of encoding.
     */
    @Override
    public ByteBuffer encode(final PartyNumber partyNumber) {
        ByteBuffer buffer = ByteBuffer.allocate(20); /*8 - is calling party number value and 1 - is reserved for length*/
        fillOddAndNatureIndicator(partyNumber, buffer);
        fillOtherIndicators(partyNumber, buffer);
        encodeNumber(partyNumber, buffer);
        buffer.flip();
        ByteBuffer tmp = Utils.subBuffer(buffer);
        ByteBuffer result = ByteBuffer.allocate(tmp.limit() + 1);
        result.put((byte) tmp.limit()).put(tmp);
        return result;
    }

    /**
     * Encode PartyNumber into List of Bytes.
     *
     * @param messagePart PartyNumber object to be encoded
     * @return List of Bytes array containing the result of encoding;
     * currently UnsupportedOperationException is thrown instead.
     */
    @Override
    public List<Byte> encodeToArray(final PartyNumber messagePart) {
        throw new UnsupportedOperationException();
    }

    private void encodeNumber(final PartyNumber partyNumber, final ByteBuffer buffer) {
        String number = partyNumber.getNumber();
        char[] chars = number.toCharArray();
        for (int index = 0; index < chars.length; index = index + 2) {
            byte second = parseByte(chars[index]);
            if (chars.length > index + 1) {
                byte val = (byte) (parseByte(chars[index + 1]) & 0xf);
                val = (byte) (val << 4);
                val |= (byte) (second & 0xf);
                buffer.put(val);
            } else {
                buffer.put((byte) (second & 0xf));
            }
        }
    }

    private byte parseByte(final char aChar) {
        return Byte.parseByte(Character.toString(aChar), 16);
    }

    private void fillOtherIndicators(final PartyNumber partyNumber, final ByteBuffer buffer) {
        byte networkInformationIndicator = partyNumber.getNetworkInformationIndicator();
        byte planIndicator = partyNumber.getNumberingPlanIndicator();
        byte presentationIndicator = partyNumber.getAddressPresentationIndicator();
        byte screeningIndicator = partyNumber.getScreeningIndicator();
        byte result = 0x0;
        result |= (byte) (networkInformationIndicator << 7);
        result |= (byte) ((planIndicator & 7) << 4);
        result |= (byte) ((presentationIndicator & 3) << 2);
        result |= (byte) (screeningIndicator & 3);
        buffer.put((byte) (result & 0xff));
    }

    private void fillOddAndNatureIndicator(final PartyNumber partyNumber, final ByteBuffer result) {
        byte oddIndicator = partyNumber.getOddIndicator();
        byte natureAddressIndicator = partyNumber.getNatureAddressIndicator();
        byte oddAndNature = (byte) (((oddIndicator << 7) | natureAddressIndicator) & 0xff);
        result.put(oddAndNature);
    }
}
