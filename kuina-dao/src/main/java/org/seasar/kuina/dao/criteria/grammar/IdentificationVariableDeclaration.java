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
 * JPQLのidentification_variable_declarationを表します．
 * <p>
 * JPQLの詳細はJPA仕様書「4.14 BNF」を参照してください．
 * </p>
 * 
 * <pre>
 * identification_variable_declaration ::=
 *     range_variable_declaration { join | fetch_join }*
 * </pre>
 * 
 * @author koichik
 */
public interface IdentificationVariableDeclaration extends Criterion,
        SubselectIdentificationVariableDeclaration {

    /**
     * join_association_path_expressionを内部結合します．
     * 
     * @param associationPathSpec
     *            join_association_path_expression
     * @return このインスタンス自身
     */
    IdentificationVariableDeclaration inner(String associationPathSpec);

    /**
     * join_association_path_expressionを内部結合します．
     * 
     * @param associationPathSpec
     *            join_association_path_expression
     * @return このインスタンス自身
     */
    IdentificationVariableDeclaration inner(PathExpression associationPathSpec);

    /**
     * identification_variableを指定してjoin_association_path_expressionを内部結合します．
     * 
     * @param associationPathSpec
     *            join_association_path_expression
     * @param identificationVariable
     *            identification_variable
     * @return このインスタンス自身
     */
    IdentificationVariableDeclaration inner(String associationPathSpec,
            String identificationVariable);

    /**
     * identification_variableを指定してjoin_association_path_expressionを内部結合します．
     * 
     * @param associationPathSpec
     *            join_association_path_expression
     * @param identificationVariable
     *            identification_variable
     * @return このインスタンス自身
     */
    IdentificationVariableDeclaration inner(PathExpression associationPathSpec,
            IdentificationVariable identificationVariable);

    /**
     * join_association_path_expressionを左外部結合します．
     * 
     * @param associationPathSpec
     *            join_association_path_expression
     * @return このインスタンス自身
     */
    IdentificationVariableDeclaration left(String associationPathSpec);

    /**
     * join_association_path_expressionを左外部結合します．
     * 
     * @param associationPathSpec
     *            join_association_path_expression
     * @return このインスタンス自身
     */
    IdentificationVariableDeclaration left(PathExpression associationPathSpec);

    /**
     * identification_variableを指定してjoin_association_path_expressionを左外部結合します．
     * 
     * @param associationPathSpec
     *            join_association_path_expression
     * @param identificationVariable
     *            identification_variable
     * @return このインスタンス自身
     */
    IdentificationVariableDeclaration left(String associationPathSpec,
            String identificationVariable);

    /**
     * identification_variableを指定してjoin_association_path_expressionを左外部結合します．
     * 
     * @param associationPathSpec
     *            join_association_path_expression
     * @param identificationVariable
     *            identification_variable
     * @return このインスタンス自身
     */
    IdentificationVariableDeclaration left(PathExpression associationPathSpec,
            IdentificationVariable identificationVariable);

    /**
     * join_association_path_expressionを内部フェッチ結合します．
     * 
     * @param associationPathSpec
     *            join_association_path_expression
     * @return このインスタンス自身
     */
    IdentificationVariableDeclaration innerFetch(String associationPathSpec);

    /**
     * join_association_path_expressionを内部フェッチ結合します．
     * 
     * @param associationPathSpec
     *            join_association_path_expression
     * @return このインスタンス自身
     */
    IdentificationVariableDeclaration innerFetch(
            PathExpression associationPathSpec);

    /**
     * join_association_path_expressionを左外部フェッチ結合します．
     * 
     * @param associationPathSpec
     *            join_association_path_expression
     * @return このインスタンス自身
     */
    IdentificationVariableDeclaration leftFetch(String associationPathSpec);

    /**
     * join_association_path_expressionを左外部フェッチ結合します．
     * 
     * @param associationPathSpec
     *            join_association_path_expression
     * @return このインスタンス自身
     */
    IdentificationVariableDeclaration leftFetch(
            PathExpression associationPathSpec);

    /**
     * joinを結合します．
     * 
     * @param join
     *            join
     * @return このインスタンス自身
     */
    IdentificationVariableDeclaration join(Join join);

    /**
     * fetch_joinを結合します．
     * 
     * @param fetchJoin
     *            fetch_join
     * @return このインスタンス自身
     */
    IdentificationVariableDeclaration fetchJoin(FetchJoin fetchJoin);

    /**
     * abstract_schema_nameを返します．
     * 
     * @return abstract_schema_name
     */
    String getAbstractSchemaName();

    /**
     * identification_variableを返します．
     * 
     * @return identification_variable
     */
    IdentificationVariable getIdentificationVariable();

    /**
     * ビジタを受け入れます．
     * 
     * @param visitor
     *            ビジタ
     */
    void accept(IdentificationVarialbleVisitor visitor);

}
