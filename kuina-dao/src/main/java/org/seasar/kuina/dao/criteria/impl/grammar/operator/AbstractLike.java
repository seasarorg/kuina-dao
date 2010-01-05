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
import org.seasar.kuina.dao.criteria.grammar.LikeExpression;
import org.seasar.kuina.dao.criteria.grammar.StringExpression;

/**
 * JPQLのlike_expressionを表すクラスです．
 * 
 * @author koichik
 */
public class AbstractLike implements LikeExpression {

    // instance fields
    /** NOT LIKEの場合に<code>true</code>，それ以外の場合に<code>false</code> */
    protected final boolean not;

    /** テスト対象 */
    protected final StringExpression string;

    /** パターン */
    protected final StringExpression pattern;

    /** エスケープ文字 */
    protected final StringExpression escape;

    /**
     * インスタンスを構築します。
     * 
     * @param not
     *            NOT LIKEの場合に<code>true</code>，それ以外の場合に<code>false</code>
     * @param string
     *            テスト対象を表すstring_expression
     * @param pattern
     *            パターンを表すstring_expression
     * @param escape
     *            エスケープ文字を表すstring_expression
     */
    public AbstractLike(final boolean not, final StringExpression string,
            final StringExpression pattern, final StringExpression escape) {
        this.not = not;
        this.string = string;
        this.pattern = pattern;
        this.escape = escape;
    }

    public void evaluate(final CriteriaContext context) {
        context.append("(");
        string.evaluate(context);
        if (not) {
            context.append(" NOT");
        }
        context.append(" LIKE ");
        pattern.evaluate(context);
        if (escape != null) {
            context.append(" ESCAPE ");
            escape.evaluate(context);
        }
        context.append(")");
    }

}
