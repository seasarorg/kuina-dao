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
package org.seasar.kuina.dao.internal.util;

import org.seasar.kuina.dao.OrderbySpec;
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.OrderbyItemImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.PathExpressionImpl;

/**
 * JPQLのSELECT文を扱うユーティリティ・クラスです．
 * 
 * @author koichik
 */
public class SelectStatementUtil {

    /**
     * SELECT文のORDERBY句にorderby_itemを追加します．
     * 
     * @param identificationVariable
     *            orderby_itemのidentification_variable
     * @param selectStatement
     *            orderby_itemを追加するselect_statement
     * @param orderbySpec
     *            orderby_spec
     */
    public static void appendOrderbyClause(final String identificationVariable,
            final SelectStatement selectStatement, final Object orderbySpec) {
        if (String.class.isInstance(orderbySpec)) {
            appendOrderbyClause(identificationVariable, selectStatement,
                    String.class.cast(orderbySpec));
        } else if (String[].class.isInstance(orderbySpec)) {
            appendOrderbyClause(identificationVariable, selectStatement,
                    String[].class.cast(orderbySpec));
        } else if (OrderbySpec.class.isInstance(orderbySpec)) {
            appendOrderbyClause(identificationVariable, selectStatement,
                    OrderbySpec.class.cast(orderbySpec));
        } else if (OrderbySpec[].class.isInstance(orderbySpec)) {
            appendOrderbyClause(identificationVariable, selectStatement,
                    OrderbySpec[].class.cast(orderbySpec));
        }
    }

    /**
     * SELECT文のORDERBY句にorderby_itemを追加します．
     * 
     * @param identificationVariable
     *            orderby_itemのidentification_variable
     * @param selectStatement
     *            orderby_itemを追加するselect_statement
     * @param orderbySpec
     *            orderby_spec
     */
    public static void appendOrderbyClause(final String identificationVariable,
            final SelectStatement selectStatement, final String orderbySpec) {
        selectStatement.orderby(identificationVariable + "." + orderbySpec);
    }

    /**
     * SELECT文のORDERBY句にorderby_itemを追加します．
     * 
     * @param identificationVariable
     *            orderby_itemのidentification_variable
     * @param selectStatement
     *            orderby_itemを追加するselect_statement
     * @param orderbySpecs
     *            orderby_specの配列
     */
    public static void appendOrderbyClause(final String identificationVariable,
            final SelectStatement selectStatement, final String[] orderbySpecs) {
        for (final String orderbySpec : orderbySpecs) {
            appendOrderbyClause(identificationVariable, selectStatement,
                    orderbySpec);
        }
    }

    /**
     * SELECT文のORDERBY句にorderby_itemを追加します．
     * 
     * @param identificationVariable
     *            orderby_itemのidentification_variable
     * @param selectStatement
     *            orderby_itemを追加するselect_statement
     * @param orderbySpec
     *            orderby_spec
     */
    public static void appendOrderbyClause(final String identificationVariable,
            final SelectStatement selectStatement, final OrderbySpec orderbySpec) {
        selectStatement.orderby(new OrderbyItemImpl(
                new PathExpressionImpl(identificationVariable + "."
                        + orderbySpec.getPathExpression()), orderbySpec
                        .getOrderingSpec()));
    }

    /**
     * SELECT文のORDERBY句にorderby_itemを追加します．
     * 
     * @param identificationVariable
     *            orderby_itemのidentification_variable
     * @param selectStatement
     *            orderby_itemを追加するselect_statement
     * @param orderbySpecs
     *            orderby_specの配列
     */
    public static void appendOrderbyClause(final String identificationVariable,
            final SelectStatement selectStatement,
            final OrderbySpec[] orderbySpecs) {
        for (final OrderbySpec orderbySpec : orderbySpecs) {
            appendOrderbyClause(identificationVariable, selectStatement,
                    orderbySpec);
        }
    }

}
