/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.bpmn.definition;

import org.junit.Test;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.GenericServiceTask;
import org.kie.workbench.common.stunner.bpmn.definition.property.service.GenericServiceTaskExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.simulation.SimulationSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.TaskType;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.TaskTypes;
import org.kie.workbench.common.stunner.bpmn.definition.property.variables.AdvancedData;

import static junit.framework.TestCase.assertEquals;

public class GenericServiceTaskTest {

    @Test
    public void setAndGetExecutionSet() {
        GenericServiceTask genericServiceTask = new GenericServiceTask("Service Task",
                                                                       "",
                                                                       new GenericServiceTaskExecutionSet(),
                                                                       new SimulationSet(),
                                                                       new TaskType(TaskTypes.SERVICE_TASK),
                                                                       new AdvancedData());

        assertEquals(new GenericServiceTaskExecutionSet(), genericServiceTask.getExecutionSet());
        GenericServiceTaskExecutionSet set = new GenericServiceTaskExecutionSet();
        genericServiceTask.setExecutionSet(set);

        assertEquals(set, genericServiceTask.getExecutionSet());
    }

    @Test
    public void testHashCode() {
        GenericServiceTask a = new GenericServiceTask();
        GenericServiceTask b = new GenericServiceTask();

        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equals() {
        GenericServiceTask a = new GenericServiceTask();
        GenericServiceTask b = new GenericServiceTask();
        assertEquals(a, b);
    }
}