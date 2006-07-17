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
package org.seasar.kuina.dao.internal.command;

import org.seasar.kuina.dao.OrderbySpec;
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.OrderbyItemImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.PathExpressionImpl;
import org.seasar.kuina.dao.internal.Command;

/**
 * 
 * @author koichik
 */
public abstract class AbstractCommand implements Command {

    protected void appendOrderbyClause(final SelectStatement selectStatement,
            final Object orderbySpec) {
        if (orderbySpec instanceof String) {
            selectStatement.orderby(String.class.cast(orderbySpec));
        } else if (orderbySpec instanceof String[]) {
            for (final String path : String[].class.cast(orderbySpec)) {
                selectStatement.orderby(path);
            }
        } else if (orderbySpec instanceof OrderbySpec) {
            final OrderbySpec spec = OrderbySpec.class.cast(orderbySpec);
            selectStatement.orderby(new OrderbyItemImpl(new PathExpressionImpl(
                    spec.getPathExpression()), spec.getOrderingSpec()));
        } else if (orderbySpec instanceof OrderbySpec[]) {
            for (final OrderbySpec spec : OrderbySpec[].class.cast(orderbySpec)) {
                selectStatement.orderby(new OrderbyItemImpl(
                        new PathExpressionImpl(spec.getPathExpression()), spec
                                .getOrderingSpec()));
            }
        }
    }

}
