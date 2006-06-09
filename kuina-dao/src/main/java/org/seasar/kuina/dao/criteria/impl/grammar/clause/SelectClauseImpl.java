/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
import org.seasar.kuina.dao.criteria.grammar.SelectClause;
import org.seasar.kuina.dao.criteria.grammar.SelectExpression;

/**
 * 
 * @author koichik
 */
public class SelectClauseImpl implements SelectClause {
    protected final boolean distinct;

    protected final List<Criterion> selectExpressions = CollectionsUtil
            .newArrayList();

    public SelectClauseImpl() {
        this(false);
    }

    public SelectClauseImpl(final boolean distinct) {
        this.distinct = distinct;
    }

    public SelectClause add(final SelectExpression... selectExpressions) {
        assert selectExpressions != null;
        assert selectExpressions.length > 0;

        for (final SelectExpression selectExpression : selectExpressions) {
            this.selectExpressions.add(selectExpression);
        }
        return this;
    }

    public boolean isEmpty() {
        return selectExpressions.isEmpty();
    }

    /**
     * @see org.seasar.kuina.dao.criteria.Criterion#evaluate(org.seasar.kuina.dao.criteria.CriteriaContext)
     */
    public void evaluate(final CriteriaContext context) {
        assert !selectExpressions.isEmpty();

        context.append("SELECT ");
        if (distinct) {
            context.append("DISTINCT ");
        }
        for (final Criterion criterion : selectExpressions) {
            criterion.evaluate(context);
            context.append(", ");
        }
        context.cutBack(2);
    }

}
