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

package com.ait.lienzo.client.widget.panel.impl;

import java.util.EnumSet;

import com.ait.lienzo.client.widget.panel.LienzoPanel;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;

public class LienzoPanelDragLimitEventDetail extends LienzoPanelEventDetail {

    public enum LimitDirections {
        LEFT,
        RIGHT,
        TOP,
        DOWN
    }

    private EnumSet<LimitDirections> limitDirection;

    public static LienzoPanelDragLimitEventDetail getDragLimitDetail(Event event) {
        return (LienzoPanelDragLimitEventDetail) ((CustomEvent) event).detail;
    }

    public LienzoPanelDragLimitEventDetail(LienzoPanel panel, EnumSet<LimitDirections> limitDirection) {
        super(panel);
        this.limitDirection = limitDirection;
    }

    public EnumSet<LimitDirections> getLimitDirection() {
        return limitDirection;
    }
}
