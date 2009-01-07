/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import org.seasar.kuina.dao.criteria.grammar.FunctionReturningStrings;
import org.seasar.kuina.dao.criteria.grammar.SimpleArithmeticExpression;
import org.seasar.kuina.dao.criteria.grammar.StringPrimary;

/**
 * JPQLのSUBSTRING関数を表すクラスです．
 * 
 * @author koichik
 */
public class Substring extends AbstractFunction implements
        FunctionReturningStrings {

    /**
     * インスタンスを構築します。
     * 
     * @param source
     *            部分文字列を求める対象文字列を表すstring_primary
     * @param start
     *            部分文字列の開始位置を表すsimple_arithmetic_expression
     * @param length
     *            部分文字列の長さを表すsimple_arithmetic_expression
     */
    public Substring(final StringPrimary source,
            final SimpleArithmeticExpression start,
            final SimpleArithmeticExpression length) {
        super("SUBSTRING", source, start, length);
    }

}
