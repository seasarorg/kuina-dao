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

import org.seasar.kuina.dao.criteria.grammar.SimpleArithmeticExpression;

/**
 * JPQLの2項<code>+</code>演算子を表すクラスです．
 * 
 * @author koichik
 */
public class Addition extends AbstractBinaryOperator implements
        SimpleArithmeticExpression {

    /**
     * インスタンスを構築します。
     * 
     * @param lhs
     *            左辺を表すsimple_arithmetic_expression
     * @param rhs
     *            右辺を表すsimple_arithmetic_expression
     */
    public Addition(final SimpleArithmeticExpression lhs,
            final SimpleArithmeticExpression rhs) {
        super(" + ", lhs, rhs);
    }

}
