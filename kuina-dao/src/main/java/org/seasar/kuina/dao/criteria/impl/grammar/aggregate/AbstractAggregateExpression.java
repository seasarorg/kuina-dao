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
package org.seasar.kuina.dao.criteria.impl.grammar.aggregate;

import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.Criterion;
import org.seasar.kuina.dao.criteria.grammar.AggregateExpression;

/**
 * 
 * @author koichik
 */
public class AbstractAggregateExpression implements AggregateExpression {
    protected final String operator;

    protected final boolean distinct;

    protected final Criterion expression;

    /**
     * インスタンスを構築します。
     */
    public AbstractAggregateExpression(final String operator,
            final boolean distinct, final Criterion expression) {
        this.operator = operator;
        this.distinct = distinct;
        this.expression = expression;
    }

    public void evaluate(final CriteriaContext context) {
        context.append(operator).append("(");
        if (distinct) {
            context.append("DISTINCT ");
        }
        expression.evaluate(context);
        context.append(")");
    }

}
