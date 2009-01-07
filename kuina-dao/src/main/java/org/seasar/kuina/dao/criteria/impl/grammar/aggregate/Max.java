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
package org.seasar.kuina.dao.criteria.impl.grammar.aggregate;

import org.seasar.kuina.dao.criteria.grammar.PathExpression;

/**
 * JPQLのMAX関数を表現するクラスです．
 * 
 * @author koichik
 */
public class Max extends AbstractAggregateExpression {

    /**
     * インスタンスを構築します。
     * 
     * @param pathExpression
     *            MAX関数に適用されるpath_expression
     */
    public Max(final PathExpression pathExpression) {
        this(false, pathExpression);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param distinct
     *            DISTINCT の場合は<code>true</code>，それ以外の場合は<code>false</code>
     * @param pathExpression
     *            MAX関数に適用されるpath_expression
     */
    public Max(final boolean distinct, final PathExpression pathExpression) {
        super("MAX", distinct, pathExpression);
    }

}
