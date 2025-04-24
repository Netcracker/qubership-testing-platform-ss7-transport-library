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

package org.qubership.automation.ss7lib.parse.parser.cap;

import static org.qubership.automation.ss7lib.parse.scenario.Pattern.HEX_PATTERN;

import java.util.Objects;
import java.util.Set;

import javax.annotation.Nonnull;

import org.qubership.automation.ss7lib.model.sub.cap.message.idp.InitialDpArgExtension;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.extention.Descriptor;
import org.qubership.automation.ss7lib.model.sub.cap.message.idp.extention.Extension;
import org.qubership.automation.ss7lib.parse.parser.PartParser;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitialDpArgExtensionParser extends PartParser<InitialDpArgExtension> {


    private Set<Class<? extends Extension>> extensions;
    private Logger logger = LoggerFactory.getLogger(InitialDpArgExtensionParser.class);

    {
        extensions = new Reflections(
                "org.qubership.automation.ss7lib.model.sub.cap.message.idp.extention"
        ).getSubTypesOf(Extension.class);
    }

    @Override
    public void parse(String value, InitialDpArgExtension capMessage) {
        if (value.contains("gmscAddress")) {
            capMessage.setGmscAddress(getValue("gmscAddress", value, HEX_PATTERN));
            return;
        }
        Extension extension = defineExtension(value);
        capMessage.getExtensions().add(extension);
    }

    @Nonnull
    private Extension defineExtension(String value) {
        Extension extension = null;
        for (Class<? extends Extension> ex : extensions) {
            Descriptor annotation = ex.getAnnotation(Descriptor.class);
            if (value.equals(annotation.value())) {
                logger.info("Detected extension: '{}' for value '{}'", ex.getSimpleName(), value);
                try {
                    extension = ex.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    logger.error("Can't create new extension with class " + ex.getSimpleName(), e);
                }
                break;
            }
        }
        Objects.requireNonNull(extension, "Can't find extension for input: " + value);
        return extension;
    }
}
