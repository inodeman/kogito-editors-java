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

import static org.kie.workbench.common.dmn.client.editors.expressions.types.ExpressionType.RELATION;

@JsType
public class RelationProps extends ExpressionProps{
    public final Column[] columns;
    public final String[][] rows;

    public RelationProps(final String name, final String dataType, final Column[] columns, final String[][] rows) {
        super(name, dataType, RELATION.getText());
        this.columns = columns;
        this.rows = rows;
    }
}
