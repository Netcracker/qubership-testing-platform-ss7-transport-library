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

import static com.google.common.primitives.Bytes.asList;
import static org.qubership.automation.ss7lib.convert.Converter.hexToBytes;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import org.qubership.automation.ss7lib.encode.PartEncoder;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.InitialDpArgExtension;

import com.google.common.collect.Lists;

public class InitialDPExtensionEncoder implements PartEncoder<InitialDpArgExtension> {

    /**
     * Encode InitialDpArgExtension into ByteBuffer.
     *
     * @param messagePart object to be encoded
     * @return ByteBuffer containing the result of encoding; currently UnsupportedOperationException is thrown instead.
     */
    @Override
    public ByteBuffer encode(final InitialDpArgExtension messagePart) {
        throw new UnsupportedOperationException("use #encodeToArray");
    }

    /**
     * Encode InitialDpArgExtension into List of Bytes.
     *
     * @param argExtension object to be encoded
     * @return List of Bytes array containing the result of encoding.
     */
    @Override
    public List<Byte> encodeToArray(final InitialDpArgExtension argExtension) {
        List<Byte> bytes = Lists.newLinkedList();
        encodeExtensions(argExtension, bytes);
        encodeGmscAddress(argExtension, bytes);
        LinkedList<Byte> result = Lists.newLinkedList();
        result.add((byte) (bytes.size() + 1));
        result.add(argExtension.getFlag()); /*initialArgsExtension*/
        result.addAll(bytes);
        return result;
    }

    private void encodeGmscAddress(final InitialDpArgExtension argExtension, final List<Byte> bytes) {
        String gmscAddress = argExtension.getGmscAddress();
        if (gmscAddress == null) {
            return;
        }
        bytes.add((byte) 0x81); /*Gmsc address flag*/
        List<Byte> encodedGmscAddress = asList(hexToBytes(gmscAddress));
        bytes.add((byte) encodedGmscAddress.size());
        bytes.addAll(encodedGmscAddress);
    }

    private void encodeExtensions(final InitialDpArgExtension argExtension, final List<Byte> bytes) {
        List<Byte> data = Lists.newLinkedList();
        argExtension.getExtensions().forEach(extension -> {
            data.add(extension.getFlag());
            data.add((byte) 0x1); /*length*/
            data.add(extension.getCicSelectionType().getId());
        });
        bytes.add((byte) data.size());
        bytes.addAll(data);
    }
}
