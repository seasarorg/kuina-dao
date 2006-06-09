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
package org.seasar.kuina.dao.criteria.impl.grammar.conditional;

import java.util.List;

import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.Criterion;
import org.seasar.kuina.dao.criteria.grammar.ConditionalExpression;
import org.seasar.kuina.dao.criteria.grammar.ConditionalPrimary;

/**
 * 
 * @author koichik
 */
public abstract class AbstractConditionalExpression implements
        ConditionalPrimary {
    protected final String operator;

    protected final List<Criterion> expressions = CollectionsUtil
            .newArrayList();

    /**
     * インスタンスを構築します。
     */
    public AbstractConditionalExpression(final String operator) {
        this.operator = operator;
    }

    public AbstractConditionalExpression(final String operator,
            final ConditionalExpression... expressions) {
        this(operator);
        add(expressions);
    }

    public AbstractConditionalExpression add(
            final ConditionalExpression... expressions) {
        for (final Criterion expression : expressions) {
            this.expressions.add(expression);
        }
        return this;
    }

    /**
     * @see org.seasar.kuina.dao.criteria.Criterion#evaluate(org.seasar.kuina.dao.criteria.CriteriaContext)
     */
    public void evaluate(final CriteriaContext context) {
        final int size = expressions.size();
        assert size > 0;

        if (size > 1) {
            context.append("(");
        }
        for (final Criterion expression : expressions) {
            expression.evaluate(context);
            context.append(" ").append(operator).append(" ");
        }
        context.cutBack(operator.length() + 2);
        if (size > 1) {
            context.append(")");
        }
    }

}
