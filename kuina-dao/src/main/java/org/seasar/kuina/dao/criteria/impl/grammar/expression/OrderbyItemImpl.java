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
package org.seasar.kuina.dao.criteria.impl.grammar.expression;

import org.seasar.framework.exception.SIllegalArgumentException;
import org.seasar.kuina.dao.OrderingSpec;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.grammar.OrderbyItem;
import org.seasar.kuina.dao.criteria.grammar.PathExpression;

/**
 * JPQLのorderby_itemを表すクラスです．
 * 
 * @author koichik
 */
public class OrderbyItemImpl implements OrderbyItem {

    // instance fields
    /** path_expression */
    protected final PathExpression pathExpression;

    /** ordering_spec */
    protected final OrderingSpec orderingSpec;

    /**
     * インスタンスを構築します。
     * 
     * @param pathExpression
     *            path_expression
     */
    public OrderbyItemImpl(final PathExpression pathExpression) {
        this(pathExpression, OrderingSpec.ASC);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param pathExpression
     *            path_expression
     * @param orderingSpec
     *            ordering_spec
     */
    public OrderbyItemImpl(final PathExpression pathExpression,
            final OrderingSpec orderingSpec) {
        if (pathExpression == null) {
            throw new SIllegalArgumentException("EKuinaDao0001",
                    new Object[] { "pathExpression" });
        }
        this.pathExpression = pathExpression;
        this.orderingSpec = orderingSpec;
    }

    public void evaluate(final CriteriaContext context) {
        pathExpression.evaluate(context);
        if (OrderingSpec.DESC.equals(orderingSpec)) {
            context.append(" DESC");
        }
    }

}
