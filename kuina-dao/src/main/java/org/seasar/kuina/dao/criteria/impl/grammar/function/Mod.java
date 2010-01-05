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
package org.seasar.kuina.dao.criteria.impl.grammar.function;

import org.seasar.kuina.dao.criteria.grammar.FunctionReturningNumerics;
import org.seasar.kuina.dao.criteria.grammar.SimpleArithmeticExpression;

/**
 * JPQLのMOD関数を表すクラスです．
 * 
 * @author koichik
 */
public class Mod extends AbstractFunction implements FunctionReturningNumerics {

    /**
     * インスタンスを構築します。
     * 
     * @param divided
     *            除算される値を表すsimple_arithmetic_expression
     * @param divisor
     *            除算する値を表すsimple_arithmetic_expression
     */
    public Mod(final SimpleArithmeticExpression divided,
            final SimpleArithmeticExpression divisor) {
        super("MOD", divided, divisor);
    }

}
