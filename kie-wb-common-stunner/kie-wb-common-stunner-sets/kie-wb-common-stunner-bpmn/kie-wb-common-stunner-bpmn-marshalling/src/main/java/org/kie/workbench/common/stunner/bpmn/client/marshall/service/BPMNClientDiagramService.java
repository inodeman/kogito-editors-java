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

package org.kie.workbench.common.stunner.bpmn.client.marshall.service;

import java.util.Collection;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import elemental2.promise.Promise;
import org.kie.workbench.common.stunner.bpmn.client.workitem.WorkItemDefinitionClientService;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNDiagram;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNViewDefinition;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Definitions;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Process;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.StartEvent;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmndi.BpmnShape;
import org.kie.workbench.common.stunner.bpmn.factory.BPMNDiagramFactory;
import org.kie.workbench.common.stunner.bpmn.workitem.WorkItemDefinition;
import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.api.FactoryManager;
import org.kie.workbench.common.stunner.core.client.api.ShapeManager;
import org.kie.workbench.common.stunner.core.client.service.ClientRuntimeError;
import org.kie.workbench.common.stunner.core.client.service.ServiceCallback;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.diagram.DiagramImpl;
import org.kie.workbench.common.stunner.core.diagram.DiagramParsingException;
import org.kie.workbench.common.stunner.core.diagram.Metadata;
import org.kie.workbench.common.stunner.core.diagram.MetadataImpl;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.definition.Definition;
import org.kie.workbench.common.stunner.core.graph.content.view.ViewImpl;
import org.kie.workbench.common.stunner.core.graph.impl.NodeImpl;
import org.kie.workbench.common.stunner.core.graph.util.GraphUtils;
import org.kie.workbench.common.stunner.kogito.client.service.AbstractKogitoClientDiagramService;
import org.uberfire.client.promise.Promises;

import static org.kie.workbench.common.stunner.bpmn.util.XmlUtils.createValidId;

@ApplicationScoped
public class BPMNClientDiagramService extends AbstractKogitoClientDiagramService {

    static final String DEFAULT_PACKAGE = "com.example";
    static final String NO_DIAGRAM_MESSAGE = "No BPMN Diagram can be found.";

    private final DefinitionManager definitionManager;
    private final BPMNClientMarshalling marshalling;
    private final FactoryManager factoryManager;
    private final BPMNDiagramFactory diagramFactory;
    private final ShapeManager shapeManager;
    private final Promises promises;
    private final WorkItemDefinitionClientService widService;

    //CDI proxy
    protected BPMNClientDiagramService() {
        this(null, null, null, null, null, null, null);
    }

    @Inject
    public BPMNClientDiagramService(final DefinitionManager definitionManager,
                                    final BPMNClientMarshalling marshalling,
                                    final FactoryManager factoryManager,
                                    final BPMNDiagramFactory diagramFactory,
                                    final ShapeManager shapeManager,
                                    final Promises promises,
                                    final WorkItemDefinitionClientService widService) {
        this.definitionManager = definitionManager;
        this.marshalling = marshalling;
        this.factoryManager = factoryManager;
        this.diagramFactory = diagramFactory;
        this.shapeManager = shapeManager;
        this.promises = promises;
        this.widService = widService;
    }

    @Override
    public void transform(final String xml,
                          final ServiceCallback<Diagram> callback) {
        doTransform(DEFAULT_DIAGRAM_ID, xml, callback);
    }

    @Override
    public void transform(final String fileName,
                          final String xml,
                          final ServiceCallback<Diagram> callback) {
        doTransform(createDiagramTitleFromFilePath(fileName), xml, callback);
    }

    private void doTransform(final String fileName,
                             final String xml,
                             final ServiceCallback<Diagram> callback) {
        final Metadata metadata = createMetadata();
        widService
                .call(metadata)
                .then(wid -> {
                    Diagram diagram = doTransform(fileName, xml);
                    callback.onSuccess(diagram);
                    return promises.resolve();
                })
                .catch_((Promise.CatchOnRejectedCallbackFn<Collection<WorkItemDefinition>>) error -> {
                    callback.onError(new ClientRuntimeError(new DiagramParsingException(metadata, xml)));
                    return promises.resolve();
                });
    }

    private Diagram doTransform(final String fileName,
                                final String xml) {

        if (Objects.isNull(xml) || xml.isEmpty()) {
            return createNewDiagram(fileName);
        }
        return parse(fileName, xml);
    }

    public Promise<String> transform(final Diagram diagram) {
        String raw = marshalling.marshall(convert(diagram));
        return promises.resolve(raw);
    }

    private void updateDiagramSet(Node<Definition<BPMNDiagram>, ?> diagramNode, String name) {
        final BPMNDiagram diagramSet = diagramNode.getContent().getDefinition();

        if (diagramSet.getPackageName() == null ||
                diagramSet.getName().isEmpty()) {
            diagramSet.setName(name);
        }

        if (diagramSet.getPackageName() == null ||
                diagramSet.getId().isEmpty()) {
            diagramSet.setId(createValidId(name));
        }

        if (diagramSet.getPackageName() == null ||
                diagramSet.getPackageName().isEmpty()) {
            diagramSet.setPackageName(DEFAULT_PACKAGE);
        }
    }

    private Diagram createNewDiagram(String fileName) {
        final String title = createDiagramTitleFromFilePath(fileName);
        final String defSetId = BPMNClientMarshalling.getDefinitionSetId();
        final Metadata metadata = createMetadata();
        metadata.setTitle(title);
        final Diagram diagram = factoryManager.newDiagram(title,
                                                          defSetId,
                                                          metadata);

        final Node<Definition<BPMNDiagram>, ?> diagramNode = GraphUtils.getFirstNode((Graph<?, Node>) diagram.getGraph(), Process.class);

        updateDiagramSet(diagramNode, fileName);
        updateClientMetadata(diagram);
        return diagram;
    }

    @SuppressWarnings("unchecked")
    private Diagram parse(final String fileName, final String raw) {
        final Metadata metadata = createMetadata();
        final String title = createDiagramTitleFromFilePath(fileName);
        final String defSetId = BPMNClientMarshalling.getDefinitionSetId();
        metadata.setTitle(title);

        final Definitions definitions = marshalling.unmarshall(raw);
        final Diagram diagram = factoryManager.newDiagram(title,
                                                          defSetId,
                                                          metadata);

        final Node<Definition<BPMNDiagram>, ?> diagramNode = GraphUtils.getFirstNode((Graph<?, Node>) diagram.getGraph(), Process.class);

        diagramNode.getContent().setDefinition(definitions.getProcess());


        for (StartEvent event : definitions.getProcess().getStartEvents()) {
            for (BpmnShape shape : definitions.getBpmnDiagram().getBpmnPlane().getBpmnShapes()) {
                if (shape.getBpmnElement().equals(event.getId())) {
                    NodeImpl<ViewImpl<BPMNViewDefinition>> node = new NodeImpl<>(event.getId());
                    node.getContent().setDefinition(event);
                    diagram.getGraph().addNode((Node) event);
                }
            }
        }

        updateDiagramSet(diagramNode, fileName);
        updateClientMetadata(diagram);

        return diagram;
    }

    private Metadata createMetadata() {
        return new MetadataImpl.MetadataImplBuilder(BPMNClientMarshalling.getDefinitionSetId(),
                                                    definitionManager)
                .build();
    }

    private void updateClientMetadata(final Diagram diagram) {
        if (null != diagram) {
            final Metadata metadata = diagram.getMetadata();
            if (Objects.nonNull(metadata)) {
                final String sId = shapeManager.getDefaultShapeSet(metadata.getDefinitionSetId()).getId();
                metadata.setShapeSetId(sId);
            }
        }
    }

    private DiagramImpl convert(final Diagram diagram) {
        return new DiagramImpl(diagram.getName(),
                               diagram.getGraph(),
                               diagram.getMetadata());
    }
}
