/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
 * JPQLのaggregate_expressionを表す抽象クラスです．
 * 
 * @author koichik
 */
public class AbstractAggregateExpression implements AggregateExpression {

    // instance fields
    /** 関数名 */
    protected final String functionName;

    /** DISTINCT の場合は<code>true</code>，それ以外の場合は<code>false</code> */
    protected final boolean distinct;

    /** 集計対象の式 */
    protected final Criterion expression;

    /**
     * インスタンスを構築します。
     * 
     * @param functionName
     *            関数名
     * @param distinct
     *            DISTINCT の場合は<code>true</code>，それ以外の場合は<code>false</code>
     * @param expression
     *            集計対象の式
     */
    public AbstractAggregateExpression(final String functionName,
            final boolean distinct, final Criterion expression) {
        this.functionName = functionName;
        this.distinct = distinct;
        this.expression = expression;
    }

    public void evaluate(final CriteriaContext context) {
        context.append(functionName).append("(");
        if (distinct) {
            context.append("DISTINCT ");
        }
        expression.evaluate(context);
        context.append(")");
    }

}
