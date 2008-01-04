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
package org.seasar.kuina.dao.criteria.impl.grammar.function;

import org.seasar.kuina.dao.criteria.grammar.FunctionReturningNumerics;
import org.seasar.kuina.dao.criteria.grammar.SimpleArithmeticExpression;
import org.seasar.kuina.dao.criteria.grammar.StringPrimary;

/**
 * JPQLのLOCATE関数を表すクラスです．
 * 
 * @author koichik
 */
public class Locate extends AbstractFunction implements
        FunctionReturningNumerics {

    /**
     * インスタンスを構築します。
     * 
     * @param located
     *            走査対象の文字列を表すstring_primary
     * @param searched
     *            走査する文字列を表すstring_primary
     */
    public Locate(final StringPrimary located, final StringPrimary searched) {
        super("LOCATE", located, searched);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param located
     *            走査対象の文字列を表すstring_primary
     * @param searched
     *            走査する文字列を表すstring_primary
     * @param start
     *            捜査を開始する位置を表すsimple_arithmetic_expression
     */
    public Locate(final StringPrimary located, final StringPrimary searched,
            final SimpleArithmeticExpression start) {
        super("LOCATE", located, searched, start);
    }

}
