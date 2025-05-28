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

package org.qubership.automation.ss7lib.encode;

import static com.google.common.primitives.Bytes.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

import org.qubership.automation.ss7lib.model.sub.Flag;
import org.qubership.automation.ss7lib.model.sub.Flags;
import org.qubership.automation.ss7lib.model.sub.cap.message.InitialDetectionPoint;
import org.qubership.automation.ss7lib.model.sub.cap.param.AbstractParamPojo;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class EncoderUtils {

    /**
     * Reverse characters in each pair of characters of source String.
     *
     * @param stringBytes - String to process
     * @return String filled with source String characters in reverse order in each pair.
     */
    public static String reverse(String stringBytes) {
        if (stringBytes.length() % 2 != 0) {
            stringBytes = stringBytes + "f";
        }
        String[] split = stringBytes.split("(?<=\\G.{2})");
        StringBuilder buffer = new StringBuilder();
        for (String s : split) {
            s = new StringBuffer(s).reverse().toString();
            buffer.append(s);
        }
        return buffer.toString();
    }

    /**
     * Convert String representation of Hex or Decimal integers into List of Bytes.
     *
     * @param bytes - String to process
     * @param isHex - flag if String bytes is Hex integers representation (true) or Decimals.
     * @return List of Bytes result of conversion.
     */
    public static List<Byte> splitStringBytes(final @Nonnull String bytes, final boolean isHex) {
        List<Byte> result = Lists.newArrayList();
        int radix = isHex ? 16 : 10;
        String[] split = bytes.split("(?<=\\G.{2})");
        for (String s : split) {
            result.add((byte) Integer.parseInt(s, radix));
        }
        return result;
    }

    /**
     * Fill List of Bytes parameter with Flag/Flags of AbstractParamPojo parameter,
     * then add encoding results.
     *
     * @param bytes List of Bytes to fill
     * @param pojo AbstractParamPojo object to process.
     */
    public static void encodePojoFlagParam(final @Nonnull List<Byte> bytes, final @Nonnull AbstractParamPojo pojo) {
        addFlag(bytes, pojo);
        encodeParam(bytes, pojo);
    }

    /**
     * In case AbstractParamPojo parameter is Flag or Flags, fill List of Bytes parameter
     * with their values; otherwise do nothing.
     *
     * @param bytes List of Bytes to fill
     * @param pojo AbstractParamPojo object to process.
     */
    public static void addFlag(final @Nonnull List<Byte> bytes, final @Nonnull AbstractParamPojo pojo) {
        if (pojo instanceof Flag) {
            bytes.add(((Flag) pojo).getFlag());
        } else if (pojo instanceof Flags) {
            bytes.addAll(asList(((Flags) pojo).getFlags()));
        }
    }

    /**
     * Encode AbstractParamPojo parameter and fill List of Bytes parameter with encoded bytes.
     *
     * @param bytes List of Bytes to fill with encoding result
     * @param pojo AbstractParamPojo object to encode.
     */
    public static void encodeParam(final @Nonnull List<Byte> bytes, final @Nonnull AbstractParamPojo pojo) {
        ArrayList<Byte> subBytes = Lists.newArrayList();
        if (!Strings.isNullOrEmpty(pojo.getStringBytes())) {
            subBytes.addAll(EncoderUtils.splitStringBytes(pojo.getStringBytes(), pojo.isHEX()));
        }
        if (pojo.getMessageLength() != 0) {
            bytes.add(pojo.getMessageLength());
        } else {
            bytes.add((byte) subBytes.size());
            pojo.setMessageLength((byte) subBytes.size());
        }
        bytes.addAll(subBytes);
    }

    /**
     * Encode AbstractParamPojo parameter and fill List of Bytes parameter with encoded bytes.
     *
     * @param bytes List of Bytes to fill with encoding result
     * @param pojo InitialDetectionPoint parameter (currently unused)
     * @param param AbstractParamPojo object to encode.
     */
    public static void encodeParameter(final List<Byte> bytes,
                                       final InitialDetectionPoint pojo,
                                       final AbstractParamPojo param) {
        if (Objects.nonNull(param)) {
            encodePojoFlagParam(bytes, param);
        }
    }

}
