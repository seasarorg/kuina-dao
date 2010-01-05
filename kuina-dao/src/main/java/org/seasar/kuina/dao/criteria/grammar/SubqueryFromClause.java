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
package org.seasar.kuina.dao.criteria.grammar;

import org.seasar.kuina.dao.criteria.Criterion;
import org.seasar.kuina.dao.criteria.IdentificationVarialbleVisitor;

/**
 * JPQLのsubquery_from_clauseを表します．
 * <p>
 * JPQLの詳細はJPA仕様書「4.14 BNF」を参照してください．
 * </p>
 * 
 * <pre>
 * subquery_from_clause ::=
 *     FROM subselect_identification_variable_declaration
 *         {, subselect_identification_variable_declaration}*
 * </pre>
 * 
 * @author koichik
 */
public interface SubqueryFromClause extends Criterion {

    /**
     * subselect_identification_variable_declarationを追加します．
     * 
     * @param declarations
     *            subselect_identification_variable_declarationの並び
     * @return このインスタンス自身
     */
    SubqueryFromClause add(
            SubselectIdentificationVariableDeclaration... declarations);

    /**
     * collection_member_declarationを追加します．
     * 
     * @param declarations
     *            collection_member_declarationの並び
     * @return このインスタンス自身
     */
    SubqueryFromClause add(CollectionMemberDeclaration... declarations);

    /**
     * subselect_identification_variable_declarationまたはcollection_member_declarationを一つも持っていない場合に<code>true</code>を，
     * そうでない場合に<code>false</code>を返します．
     * 
     * @return subselect_identification_variable_declarationまたはcollection_member_declarationを一つも持っていない場合に<code>true</code>
     */
    boolean isEmpty();

    /**
     * 保持しているsubselect_identification_variable_declarationおよびcollection_member_declarationの数を返します．
     * 
     * @return 保持しているsubselect_identification_variable_declarationおよびcollection_member_declarationの数
     */
    int size();

    /**
     * インデックスで指定された位置のsubselect_identification_variable_declarationを返します．
     * <p>
     * インデックスで指定された位置の宣言がsubselect_identification_variable_declarationではなく，
     * collection_member_declarationだった場合は<code>ClassCastException</code>がスローされます．
     * </p>
     * 
     * @param index
     *            インデックス
     * @return subselect_identification_variable_declaration
     * @throws ClassCastException
     *             インデックスで指定された位置の宣言がsubselect_identification_variable_declarationではなかった場合にスローされます
     */
    SubselectIdentificationVariableDeclaration getIdentificationVariableDeclaration(
            int index);

    /**
     * インデックスで指定された位置のidentification_variableを返します．
     * <p>
     * インデックスで指定された位置の宣言がsubselect_identification_variable_declarationではなく，
     * collection_member_declarationだった場合は<code>ClassCastException</code>がスローされます．
     * </p>
     * 
     * @param index
     *            インデックス
     * @return identification_variable
     * @throws ClassCastException
     *             インデックスで指定された位置の宣言がsubselect_identification_variable_declarationではなかった場合にスローされます
     */
    IdentificationVariable getIdentificationVariable(int index);

    /**
     * ビジタを受け入れます．
     * 
     * @param visitor
     *            ビジタ
     */
    void accept(IdentificationVarialbleVisitor visitor);

}
