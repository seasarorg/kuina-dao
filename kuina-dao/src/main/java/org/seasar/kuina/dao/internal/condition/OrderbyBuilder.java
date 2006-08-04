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
package org.seasar.kuina.dao.internal.condition;

import org.seasar.kuina.dao.OrderbySpec;
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.OrderbyItemImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.PathExpressionImpl;

/**
 * 
 * @author koichik
 */
public class OrderbyBuilder implements ConditionalExpressionBuilder {

    public void appendCondition(final SelectStatement statement,
            final Object value) {
        if (value == null) {
            return;
        }

        if (value instanceof String) {
            final String orderbySpec = String.class.cast(value);
            statement.orderby(orderbySpec);
        } else if (value instanceof String[]) {
            final String[] orderbySpecs = String[].class.cast(value);
            statement.orderby(orderbySpecs);
        } else if (value instanceof OrderbySpec) {
            final OrderbySpec orderbySpec = OrderbySpec.class.cast(value);
            statement.orderby(new OrderbyItemImpl(new PathExpressionImpl(
                    orderbySpec.getPathExpression()), orderbySpec
                    .getOrderingSpec()));
        } else if (value instanceof OrderbySpec[]) {
            final OrderbySpec[] orderbySpecs = OrderbySpec[].class.cast(value);
            for (final OrderbySpec orderbySpec : orderbySpecs) {
                statement.orderby(new OrderbyItemImpl(new PathExpressionImpl(
                        orderbySpec.getPathExpression()), orderbySpec
                        .getOrderingSpec()));
            }
        }
    }

}
