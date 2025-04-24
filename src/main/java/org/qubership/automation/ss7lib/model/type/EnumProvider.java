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

import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class EnumProvider {
    public static <T extends CommonEnum> T of(byte id, Class<T> clazz) {
        T[] array;
        try {
            Method method = clazz.getMethod("values");
            method.setAccessible(true);
            array = (T[]) method.invoke(clazz);
            for (T enm : array) {
                if (enm.getId() == id) {
                    return enm;
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Unable to enum from class: " + clazz.getSimpleName(), e);
        }
        throw new IllegalArgumentException("Unknown source dialog type with id: " + id
                + ". Expected values: " + Arrays.toString(array));

    }
}
