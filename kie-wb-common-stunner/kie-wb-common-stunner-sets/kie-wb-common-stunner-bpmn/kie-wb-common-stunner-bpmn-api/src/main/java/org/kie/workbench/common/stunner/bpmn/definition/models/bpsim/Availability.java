/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.workbench.common.stunner.bpmn.definition.models.bpsim;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

public class Availability {

    @XmlElement(name = "FloatingParameter")
    private FloatingParameter floatingParameter;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public FloatingParameter getFloatingParameter() {
        return floatingParameter;
    }

    public void setFloatingParameter(FloatingParameter floatingParameter) {
        this.floatingParameter = floatingParameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Availability)) {
            return false;
        }
        Availability that = (Availability) o;
        return Objects.equals(getFloatingParameter(), that.getFloatingParameter());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFloatingParameter());
    }
}
