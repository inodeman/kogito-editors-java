/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.stunner.bpmn.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.kie.workbench.common.stunner.bpmn.definition.property.background.BackgroundSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.dimensions.CircleDimensionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.font.FontSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.general.BPMNGeneralSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.simulation.CatchEventAttributes;
import org.kie.workbench.common.stunner.core.definition.annotation.PropertySet;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Category;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Labels;
import org.kie.workbench.common.stunner.core.definition.annotation.morph.MorphBase;
import org.kie.workbench.common.stunner.core.definition.builder.Builder;

import static org.kie.workbench.common.stunner.basicset.util.FieldDefLabelConstants.*;

import java.util.HashSet;
import java.util.Set;

@MorphBase( defaultType = StartNoneEvent.class, targets = { BaseEndEvent.class } )
public abstract class BaseStartEvent implements BPMNDefinition {

    @Category
    public static final transient String category = Categories.EVENTS;

    @PropertySet
    @FieldDef( label = FIELDDEF_GENERAL_SETTINGS, position = 1 )
    protected BPMNGeneralSet general;

    @PropertySet
    //@FieldDef( label = FIELDDEF_BACKGROUND_SETTINGS, position = 2 )
    protected BackgroundSet backgroundSet;

    @PropertySet
    //@FieldDef( label = FIELDDEF_FONT_SETTINGS )
    protected FontSet fontSet;

    @PropertySet
    //@FieldDef( label = FIELDDEF_CATCH_EVENT_ATTRIBUTES )
    protected CatchEventAttributes catchEventAttributes;

    @PropertySet
    //@FieldDef( label = FIELDDEF_SHAPE_DIMENSIONS, position = 3 )
    private CircleDimensionSet dimensionsSet;

    @Labels
    protected final Set<String> labels = new HashSet<String>() {{
        add( "all" );
        add( "Startevents_all" );
        add( "sequence_start" );
        add( "choreography_sequence_start" );
        add( "to_task_event" );
        add( "from_task_event" );
        add( "fromtoall" );
        add( "StartEventsMorph" );
    }};

    @NonPortable
    static abstract class BaseStartEventBuilder<T extends BaseStartEvent> implements Builder<T> {

        public static final transient String COLOR = "#cae294";
        public static final Double BORDER_SIZE = 1d;
        public static final String BORDER_COLOR = "#000000";
        public static final Double RADIUS = 15d;

    }

    public BaseStartEvent() {
    }

    public BaseStartEvent( @MapsTo( "general" ) BPMNGeneralSet general,
                           @MapsTo( "backgroundSet" ) BackgroundSet backgroundSet,
                           @MapsTo( "fontSet" ) FontSet fontSet,
                           @MapsTo( "catchEventAttributes" ) CatchEventAttributes catchEventAttributes,
                           @MapsTo( "dimensionsSet" ) CircleDimensionSet dimensionsSet ) {
        this.general = general;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
        this.catchEventAttributes = catchEventAttributes;
        this.dimensionsSet = dimensionsSet;
    }

    public String getCategory() {
        return category;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public BPMNGeneralSet getGeneral() {
        return general;
    }

    public BackgroundSet getBackgroundSet() {
        return backgroundSet;
    }

    public FontSet getFontSet() {
        return fontSet;
    }

    public CatchEventAttributes getCatchEventAttributes() {
        return catchEventAttributes;
    }

    public void setGeneral( BPMNGeneralSet general ) {
        this.general = general;
    }

    public void setBackgroundSet( BackgroundSet backgroundSet ) {
        this.backgroundSet = backgroundSet;
    }

    public void setFontSet( FontSet fontSet ) {
        this.fontSet = fontSet;
    }

    public void setCatchEventAttributes( CatchEventAttributes catchEventAttributes ) {
        this.catchEventAttributes = catchEventAttributes;
    }

    public CircleDimensionSet getDimensionsSet() {
        return dimensionsSet;
    }

    public void setDimensionsSet( CircleDimensionSet dimensionsSet ) {
        this.dimensionsSet = dimensionsSet;
    }
}
