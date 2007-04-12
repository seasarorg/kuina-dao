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
package org.seasar.kuina.dao.criteria.impl.grammar.operator;

import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.Criterion;
import org.seasar.kuina.dao.criteria.grammar.BetweenExpression;

/**
 * JPQLのbetween_expressionを表す抽象クラスです．
 * 
 * @author koichik
 */
public class AbstractBetween implements BetweenExpression {

    // instance fields
    /** 演算子 */
    protected final String operator;

    /** テスト対象 */
    protected final Criterion operand;

    /** 範囲の下限 */
    protected final Criterion from;

    /** 範囲の上限 */
    protected final Criterion to;

    /**
     * インスタンスを構築します。
     * 
     * @param operator
     *            演算子
     * @param operand
     *            テスト対象
     * @param from
     *            範囲の下限
     * @param to
     *            範囲の上限
     */
    public AbstractBetween(final String operator, final Criterion operand,
            final Criterion from, final Criterion to) {
        this.operator = operator;
        this.operand = operand;
        this.from = from;
        this.to = to;
    }

    public void evaluate(final CriteriaContext context) {
        context.append("(");
        operand.evaluate(context);
        context.append(operator);
        from.evaluate(context);
        context.append(" AND ");
        to.evaluate(context);
        context.append(")");
    }

}
