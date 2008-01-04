/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
 * JPQLのIN演算子を使用したin_expressionを表すクラスです．
 * 
 * @author koichik
 */
public class InExpressionImpl extends AbstractInExpression {

    /**
     * インスタンスを構築します。
     * 
     * @param pathExpression
     *            path_expressionを表す文字列
     */
    public InExpressionImpl(final String pathExpression) {
        super(" IN ", new PathExpressionImpl(pathExpression));
    }

    /**
     * インスタンスを構築します。
     * 
     * @param pathExpression
     *            path_expressionを表す文字列
     * @param inItems
     *            in_itemsの並び
     */
    public InExpressionImpl(final String pathExpression,
            final InItem... inItems) {
        super(" IN ", new PathExpressionImpl(pathExpression), inItems);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param pathExpression
     *            path_expression
     */
    public InExpressionImpl(final PathExpression pathExpression) {
        super(" IN ", pathExpression);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param pathExpression
     *            path_expression
     * @param inItems
     *            in_itemsの並び
     */
    public InExpressionImpl(final PathExpression pathExpression,
            final InItem... inItems) {
        super(" IN ", pathExpression, inItems);
    }

}
