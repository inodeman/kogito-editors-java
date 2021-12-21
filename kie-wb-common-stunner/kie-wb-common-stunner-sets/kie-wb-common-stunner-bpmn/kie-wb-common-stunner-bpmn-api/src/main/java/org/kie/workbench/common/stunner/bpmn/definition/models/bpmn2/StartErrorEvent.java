/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
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

import javax.validation.Valid;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.adf.definitions.annotations.FieldParam;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormDefinition;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormField;
import org.kie.workbench.common.forms.adf.definitions.settings.FieldPolicy;
import org.kie.workbench.common.stunner.bpmn.definition.property.dataio.DataIOSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.event.error.InterruptingErrorEventExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.variables.AdvancedData;
import org.kie.workbench.common.stunner.core.definition.annotation.Definition;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.definition.annotation.morph.Morph;
import org.kie.workbench.common.stunner.core.util.HashUtil;

import static org.kie.workbench.common.forms.adf.engine.shared.formGeneration.processing.fields.fieldInitializers.nestedForms.SubFormFieldInitializer.COLLAPSIBLE_CONTAINER;
import static org.kie.workbench.common.forms.adf.engine.shared.formGeneration.processing.fields.fieldInitializers.nestedForms.SubFormFieldInitializer.FIELD_CONTAINER_PARAM;

@Portable
@Bindable
@Definition
@Morph(base = StartEvent.class)
@FormDefinition(
        startElement = "name",
        policy = FieldPolicy.ONLY_MARKED,
        defaultFieldSettings = {@FieldParam(name = FIELD_CONTAINER_PARAM, value = COLLAPSIBLE_CONTAINER)}
)
public class StartErrorEvent extends StartEvent {

    @Property
    @FormField(afterElement = "documentation")
    @Valid
    protected InterruptingErrorEventExecutionSet executionSet;

    @Property
    @FormField(afterElement = "executionSet")
    @Valid
    protected DataIOSet dataIOSet;

    public StartErrorEvent() {
        this("",
             "",
             new AdvancedData(),
             new DataIOSet(),
             new InterruptingErrorEventExecutionSet());
    }

    public StartErrorEvent(final @MapsTo("name") String name,
                           final @MapsTo("documentation") String documentation,
                           final @MapsTo("advancedData") AdvancedData advancedData,
                           final @MapsTo("dataIOSet") DataIOSet dataIOSet,
                           final @MapsTo("executionSet") InterruptingErrorEventExecutionSet executionSet) {
        super(name,
              documentation,
              advancedData);
        this.dataIOSet = dataIOSet;
        this.executionSet = executionSet;
    }

    public InterruptingErrorEventExecutionSet getExecutionSet() {
        return executionSet;
    }

    public void setExecutionSet(final InterruptingErrorEventExecutionSet executionSet) {
        this.executionSet = executionSet;
    }

    public DataIOSet getDataIOSet() {
        return dataIOSet;
    }

    public void setDataIOSet(DataIOSet dataIOSet) {
        this.dataIOSet = dataIOSet;
    }

    @Override
    public boolean hasOutputVars() {
        return true;
    }

    @Override
    public boolean isSingleOutputVar() {
        return true;
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(super.hashCode(),
                                         dataIOSet.hashCode(),
                                         executionSet.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof StartErrorEvent) {
            StartErrorEvent other = (StartErrorEvent) o;
            return super.equals(other) &&
                    dataIOSet.equals(other.dataIOSet) &&
                    executionSet.equals(other.executionSet);
        }
        return false;
    }
}
