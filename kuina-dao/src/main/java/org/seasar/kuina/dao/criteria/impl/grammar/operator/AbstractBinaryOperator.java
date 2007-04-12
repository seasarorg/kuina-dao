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

/**
 * 二項演算子を表す抽象クラスです．
 * 
 * @author koichik
 */
public class AbstractBinaryOperator implements Criterion {

    // instance fields
    /** 演算子 */
    protected final String operator;

    /** 左辺 */
    protected final Criterion lhs;

    /** 右辺 */
    protected final Criterion rhs;

    /**
     * インスタンスを構築します。
     * 
     * @param operator
     *            演算子
     * @param lhs
     *            左辺
     * @param rhs
     *            右辺
     */
    public AbstractBinaryOperator(final String operator, final Criterion lhs,
            final Criterion rhs) {
        this.operator = operator;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public void evaluate(final CriteriaContext context) {
        context.append("(");
        lhs.evaluate(context);
        context.append(operator);
        rhs.evaluate(context);
        context.append(")");
    }

}
