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

package org.kie.workbench.common.dmn.client.editors.expressions.jsinterop.props;

import jsinterop.annotations.JsType;

import static org.kie.workbench.common.dmn.client.editors.expressions.types.ExpressionType.LIST;

@JsType
public class ListProps extends ExpressionProps {
    public final ExpressionProps[] items;
    public final Double width;

    public ListProps(final String name, final String dataType, final ExpressionProps[] items, final Double width) {
        super(name, dataType, LIST.getText());
        this.items = items;
        this.width = width;
    }
}
