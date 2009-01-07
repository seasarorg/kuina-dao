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
package org.seasar.kuina.dao.criteria.impl.grammar.clause;

import java.util.List;

import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.Criterion;
import org.seasar.kuina.dao.criteria.grammar.OrderbyClause;
import org.seasar.kuina.dao.criteria.grammar.OrderbyItem;

/**
 * JPQLのorderby_clauseを表すクラスです．
 * 
 * @author koichik
 */
public class OrderbyClauseImpl implements OrderbyClause {

    // instance fields
    /** orderby_itemのリスト */
    protected List<Criterion> orderbyItems = CollectionsUtil.newArrayList();

    /**
     * インスタンスを構築します。
     * 
     * @param orderbyItems
     *            orderby_itemの並び
     */
    public OrderbyClauseImpl(final OrderbyItem... orderbyItems) {
        add(orderbyItems);
    }

    public OrderbyClause add(final OrderbyItem... orderbyItems) {
        for (final OrderbyItem item : orderbyItems) {
            this.orderbyItems.add(item);
        }
        return this;
    }

    public void evaluate(final CriteriaContext context) {
        if (orderbyItems.isEmpty()) {
            return;
        }

        context.append(" ORDER BY ");
        for (final Criterion orderbyItem : orderbyItems) {
            orderbyItem.evaluate(context);
            context.append(", ");
        }
        context.cutBack(2);
    }

}
