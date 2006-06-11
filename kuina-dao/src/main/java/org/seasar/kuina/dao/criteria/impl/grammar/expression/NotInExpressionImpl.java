/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.kuina.dao.criteria.impl.grammar.expression;

import org.seasar.kuina.dao.criteria.grammar.InItem;
import org.seasar.kuina.dao.criteria.grammar.PathExpression;

/**
 * 
 * @author koichik
 */
public class NotInExpressionImpl extends AbstractInExpression {

    /**
     * インスタンスを構築します。
     */
    public NotInExpressionImpl(final String pathExpression,
            final InItem... inItems) {
        this(new PathExpressionImpl(pathExpression), inItems);
    }

    /**
     * インスタンスを構築します。
     */
    public NotInExpressionImpl(final PathExpression pathExpression,
            final InItem... inItems) {
        super(" NOT IN ", pathExpression, inItems);
    }

}
