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
package org.seasar.kuina.dao.criteria.impl.grammar.clause;

import java.util.List;

import org.seasar.framework.exception.SIllegalArgumentException;
import org.seasar.framework.exception.SIllegalStateException;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.Criterion;
import org.seasar.kuina.dao.criteria.grammar.SimpleSelectClause;
import org.seasar.kuina.dao.criteria.grammar.SimpleSelectExpression;

/**
 * JPQLのsimple_select_clauseを表すクラスです．
 * 
 * @author koichik
 */
public class SimpleSelectClauseImpl implements SimpleSelectClause {

    // instance fields
    /** 問い合わせがDISTINCTの場合は<code>true</code>，それ以外の場合は<code>false</code> */
    protected boolean distinct;

    /** simple_select_expressionのリスト */
    protected final List<Criterion> simpleSelectExpressions = CollectionsUtil
            .newArrayList();

    /**
     * インスタンスを構築します。
     * 
     */
    public SimpleSelectClauseImpl() {
        this(false);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param distinct
     *            問い合わせがDISTINCTの場合は<code>true</code>，それ以外の場合は<code>false</code>
     */
    public SimpleSelectClauseImpl(final boolean distinct) {
        this.distinct = distinct;
    }

    public SimpleSelectClause setDistinct(final boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    public SimpleSelectClause add(
            final SimpleSelectExpression... simpleSelectExpressions) {
        if (simpleSelectExpressions == null
                || simpleSelectExpressions.length == 0) {
            throw new SIllegalArgumentException("EKuinaDao0001",
                    new Object[] { "simpleSelectExpressions" });
        }

        for (final SimpleSelectExpression simpleSelectExpression : simpleSelectExpressions) {
            this.simpleSelectExpressions.add(simpleSelectExpression);
        }
        return this;
    }

    public boolean isEmpty() {
        return simpleSelectExpressions.isEmpty();
    }

    public void evaluate(final CriteriaContext context) {
        if (simpleSelectExpressions == null
                || simpleSelectExpressions.size() == 0) {
            throw new SIllegalStateException("EKuinaDao1003", new Object[] {});
        }

        context.append("SELECT ");
        if (distinct) {
            context.append("DISTINCT ");
        }
        for (final Criterion criterion : simpleSelectExpressions) {
            criterion.evaluate(context);
            context.append(", ");
        }
        context.cutBack(2);
    }

}
