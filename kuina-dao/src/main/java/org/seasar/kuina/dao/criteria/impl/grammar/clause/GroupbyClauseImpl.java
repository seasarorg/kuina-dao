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
package org.seasar.kuina.dao.criteria.impl.grammar.clause;

import java.util.List;

import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.Criterion;
import org.seasar.kuina.dao.criteria.grammar.GroupbyClause;
import org.seasar.kuina.dao.criteria.grammar.GroupbyItem;

/**
 * JPQLのgroupby_clauseを表現するクラスです．
 * 
 * @author koichik
 */
public class GroupbyClauseImpl implements GroupbyClause {

    // instance fields
    /** groupby_itemのリスト */
    protected List<Criterion> groupbyItems = CollectionsUtil.newArrayList();

    /**
     * インスタンスを構築します。
     * 
     * @param groupbyItems
     *            groupby_itemの並び
     */
    public GroupbyClauseImpl(final GroupbyItem... groupbyItems) {
        add(groupbyItems);
    }

    public GroupbyClause add(final GroupbyItem... groupbyItems) {
        for (final GroupbyItem item : groupbyItems) {
            this.groupbyItems.add(item);
        }
        return this;
    }

    public void evaluate(final CriteriaContext context) {
        if (groupbyItems.isEmpty()) {
            return;
        }

        context.append(" GROUP BY ");
        for (final Criterion groupbyItem : groupbyItems) {
            groupbyItem.evaluate(context);
            context.append(", ");
        }
        context.cutBack(2);
    }

}
