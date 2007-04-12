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
package org.seasar.kuina.dao.criteria.grammar;

import org.seasar.kuina.dao.criteria.Criterion;

/**
 * JPQLのsubqueryを表します．
 * <p>
 * JPQLの詳細はJPA仕様書「4.14 BNF」を参照してください．
 * </p>
 * 
 * <pre>
 * subquery ::=
 *     simple_select_clause subquery_from_clause [where_clause]
 *         [groupby_clause] [having_clause]
 * </pre>
 * 
 * @author koichik
 */
public interface Subquery extends Criterion, ArithmeticExpression,
        StringExpression, DatetimeExpression, BooleanExpression, EnumExpression {

    /**
     * SELECT句にsimple_select_expressionを追加します．
     * 
     * @param simpleSelectExpression
     *            simple_select_expression
     * @return このインスタンス自身
     */
    Subquery select(String simpleSelectExpression);

    /**
     * SELECT句にsimple_select_expressionを追加します．
     * 
     * @param simpleSelectExpression
     *            simple_select_expression
     * @return このインスタンス自身
     */
    Subquery select(SimpleSelectExpression simpleSelectExpression);

    /**
     * SELECT句にsimple_select_expressionを追加します．
     * 
     * @param simpleSelectExpression
     *            simple_select_expressionの並び
     * @return このインスタンス自身
     */
    Subquery select(Object... simpleSelectExpression);

    /**
     * FROM句にidentification_variable_declarationを追加します．
     * 
     * @param entityClasses
     *            identification_variable_declarationの並び
     * @return このインスタンス自身
     */
    Subquery from(Class<?>... entityClasses);

    /**
     * FROM句にidentification_variable_declarationを追加します．
     * 
     * @param entityClass
     *            abstract_schema_names
     * @param alias
     *            identification_variable
     * @return このインスタンス自身
     */
    Subquery from(Class<?> entityClass, String alias);

    /**
     * FROM句にsubselect_identification_variable_declarationを追加します．
     * 
     * @param declarations
     *            subselect_identification_variable_declarationの並び
     * @return このインスタンス自身
     */
    Subquery from(SubselectIdentificationVariableDeclaration... declarations);

    /**
     * WHERE句にconditional_expressionを追加します．
     * 
     * @param conditionalExpressions
     *            conditional_expressionの並び
     * @return このインスタンス自身
     */
    Subquery where(ConditionalExpression... conditionalExpressions);

    /**
     * GROUP BY句にgroupby_itemを追加します．
     * 
     * @param groupbyItems
     *            groupby_itemの並び
     * @return このインスタンス自身
     */
    Subquery groupby(String... groupbyItems);

    /**
     * GROUP BY句にgroupby_itemを追加します．
     * 
     * @param groupbyItems
     *            groupby_itemの並び
     * @return このインスタンス自身
     */
    Subquery groupby(GroupbyItem... groupbyItems);

    /**
     * HAVING句にconditional_expressionを追加します．
     * 
     * @param conditionalExpressions
     *            conditional_expressionの並び
     * @return このインスタンス自身
     */
    Subquery having(ConditionalExpression... conditionalExpressions);

}
