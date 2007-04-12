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
package org.seasar.kuina.dao.criteria.impl.grammar.expression;

import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.grammar.AllOrAnyExpression;
import org.seasar.kuina.dao.criteria.grammar.Subquery;

/**
 * JPQLのall_or_any_expressionを表す抽象クラスです．
 * 
 * @author koichik
 */
public abstract class AbstractAllOrAnyExpression implements AllOrAnyExpression {

    // instance fields
    /** 演算子 */
    protected final String operator;

    /** subquery */
    protected Subquery subquery;

    /**
     * インスタンスを構築します。
     * 
     * @param operator
     *            演算子
     */
    public AbstractAllOrAnyExpression(final String operator) {
        this.operator = operator;
    }

    public AllOrAnyExpression setSubquery(final Subquery subquery) {
        this.subquery = subquery;
        return this;
    }

    public void evaluate(final CriteriaContext context) {
        context.append(operator);
        subquery.evaluate(context);
    }

}
