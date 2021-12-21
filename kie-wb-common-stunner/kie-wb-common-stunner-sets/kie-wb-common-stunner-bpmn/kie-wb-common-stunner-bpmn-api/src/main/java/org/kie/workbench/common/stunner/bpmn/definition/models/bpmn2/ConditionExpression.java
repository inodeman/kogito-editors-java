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
package org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlCData;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.kie.workbench.common.stunner.core.util.HashUtil;

@XmlRootElement(name = "conditionExpression", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class ConditionExpression {

    @XmlAttribute
    private String type = "bpmn2:tFormalExpression";

    @XmlAttribute
    private String language;

    @XmlValue
    @XmlCData
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConditionExpression)) {
            return false;
        }
        ConditionExpression that = (ConditionExpression) o;
        return Objects.equals(type, that.type)
                && Objects.equals(language, that.language)
                && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(type),
                                         Objects.hashCode(language),
                                         Objects.hashCode(value));
    }
}
