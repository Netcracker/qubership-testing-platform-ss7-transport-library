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

public class EncoderUtils {

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

    public static List<Byte> splitStringBytes(@Nonnull String bytes, boolean isHex) {
        List<Byte> result = Lists.newArrayList();
        int radix = isHex ? 16 : 10;
        String[] split = bytes.split("(?<=\\G.{2})");
        for (String s : split) {
            result.add((byte) Integer.parseInt(s, radix));
        }
        return result;
    }

    public static void encodePojoFlagParam(@Nonnull List<Byte> bytes, @Nonnull AbstractParamPojo pojo) {
        addFlag(bytes, pojo);
        encodeParam(bytes, pojo);
    }

    public static void addFlag(@Nonnull List<Byte> bytes, @Nonnull AbstractParamPojo pojo) {
        if (pojo instanceof Flag) {
            bytes.add(((Flag) pojo).getFlag());
        } else if (pojo instanceof Flags) {
            bytes.addAll(asList(((Flags) pojo).getFlags()));
        }
    }

    public static void encodeParam(@Nonnull List<Byte> bytes, @Nonnull AbstractParamPojo pojo) {
        ArrayList<Byte> sub_bytes = Lists.newArrayList();
        if (!Strings.isNullOrEmpty(pojo.getStringBytes())) {
            sub_bytes.addAll(EncoderUtils.splitStringBytes(pojo.getStringBytes(), pojo.isHEX()));
        }
        if (pojo.getMessageLength() != 0) {
            bytes.add(pojo.getMessageLength());
        } else {
            bytes.add((byte) sub_bytes.size());
            pojo.setMessageLength((byte) sub_bytes.size());
        }
        bytes.addAll(sub_bytes);
    }

    public static void encodeParameter(List<Byte> bytes, InitialDetectionPoint pojo, AbstractParamPojo param) {
        if (Objects.nonNull(param)) {
            encodePojoFlagParam(bytes, param);
        }
    }

}
