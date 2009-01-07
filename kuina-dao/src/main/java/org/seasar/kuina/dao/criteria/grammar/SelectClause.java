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
package org.seasar.kuina.dao.criteria.grammar;

import org.seasar.kuina.dao.criteria.Criterion;

/**
 * JPQLのselect_clauseを表します．
 * <p>
 * JPQLの詳細はJPA仕様書「4.14 BNF」を参照してください．
 * </p>
 * 
 * <pre>
 * select_clause ::=
 *     SELECT [DISTINCT] select_expression {, select_expression}*
 * </pre>
 * 
 * @author koichik
 */
public interface SelectClause extends Criterion {

    /**
     * 問い合わせがDISTINCTの場合は<code>true</code>を，それ以外の場合は<code>false</code>を設定します．
     * 
     * @param distinct
     *            問い合わせがDISTINCTの場合は<code>true</code>
     * @return このインスタンス自身
     */
    SelectClause setDistinct(boolean distinct);

    /**
     * select_expressionを追加します．
     * 
     * @param selectExpressions
     *            select_expressionの並び
     * @return このインスタンス自身
     */
    SelectClause add(SelectExpression... selectExpressions);

    /**
     * select_expressionを一つも持っていなければ<code>true</code>を，それ以外の場合は<code>false</code>を返します．
     * 
     * @return select_expressionを一つも持っていなければ<code>true</code>
     */
    boolean isEmpty();

}
