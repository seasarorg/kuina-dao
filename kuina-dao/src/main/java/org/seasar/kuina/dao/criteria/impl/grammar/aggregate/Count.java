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
package org.seasar.kuina.dao.criteria.impl.grammar.aggregate;

import org.seasar.kuina.dao.criteria.grammar.IdentificationVariable;
import org.seasar.kuina.dao.criteria.grammar.PathExpression;

/**
 * JPQLのCOUNT関数を表現するクラスです．
 * 
 * @author koichik
 */
public class Count extends AbstractAggregateExpression {

    /**
     * インスタンスを構築します。
     * 
     * @param identificationVariable
     *            COUNT関数に提供されるidentification_variable
     */
    public Count(final IdentificationVariable identificationVariable) {
        this(false, identificationVariable);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param distinct
     *            DISTINCT の場合は<code>true</code>，それ以外の場合は<code>false</code>
     * @param identificationVariable
     *            COUNT関数に提供されるidentification_variable
     */
    public Count(final boolean distinct,
            final IdentificationVariable identificationVariable) {
        super("COUNT", distinct, identificationVariable);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param pathExpression
     *            COUNT関数に適用されるpath_expression
     */
    public Count(final PathExpression pathExpression) {
        this(false, pathExpression);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param distinct
     *            DISTINCT の場合は<code>true</code>，それ以外の場合は<code>false</code>
     * @param pathExpression
     *            COUNT関数に適用されるpath_expression
     */
    public Count(final boolean distinct, final PathExpression pathExpression) {
        super("COUNT", distinct, pathExpression);
    }

}
