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

import org.seasar.kuina.dao.criteria.grammar.StringExpression;

/**
 * JPQLのNOT LIKEを表すクラスです．
 * 
 * @author koichik
 */
public class NotLike extends AbstractLike {

    /**
     * インスタンスを構築します。
     * 
     * @param string
     *            テスト対象を表すstring_expression
     * @param pattern
     *            パターンを表すstring_expression
     */
    public NotLike(final StringExpression string, final StringExpression pattern) {
        super(true, string, pattern, null);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param string
     *            テスト対象を表すstring_expression
     * @param pattern
     *            パターンを表すstring_expression
     * @param escape
     *            エスケープ文字を表すstring_expression
     */
    public NotLike(final StringExpression string,
            final StringExpression pattern, final StringExpression escape) {
        super(true, string, pattern, escape);
    }

}
