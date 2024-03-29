/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
 * 単項前置演算子を表すクラスです．
 * 
 * @author koichik
 */
public class AbstractUnaryPrefixOperator implements Criterion {

    // instance fields
    /** 演算子 */
    protected final String operator;

    /** オペランド */
    protected final Criterion operand;

    /**
     * インスタンスを構築します。
     * 
     * @param operator
     *            演算子
     * @param operand
     *            オペランド
     */
    public AbstractUnaryPrefixOperator(final String operator,
            final Criterion operand) {
        this.operator = operator;
        this.operand = operand;
    }

    public void evaluate(final CriteriaContext context) {
        context.append(operator);
        operand.evaluate(context);
    }

}
