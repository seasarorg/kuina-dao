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
package org.seasar.kuina.dao.criteria.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.seasar.framework.log.Logger;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.IdentificationVarialbleVisitor;
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.criteria.grammar.ConditionalExpression;
import org.seasar.kuina.dao.criteria.grammar.FromClause;
import org.seasar.kuina.dao.criteria.grammar.GroupbyClause;
import org.seasar.kuina.dao.criteria.grammar.GroupbyItem;
import org.seasar.kuina.dao.criteria.grammar.HavingClause;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariable;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariableDeclaration;
import org.seasar.kuina.dao.criteria.grammar.OrderbyClause;
import org.seasar.kuina.dao.criteria.grammar.OrderbyItem;
import org.seasar.kuina.dao.criteria.grammar.SelectClause;
import org.seasar.kuina.dao.criteria.grammar.SelectExpression;
import org.seasar.kuina.dao.criteria.grammar.WhereClause;
import org.seasar.kuina.dao.criteria.impl.grammar.clause.FromClauseImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.clause.GroupbyClauseImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.clause.HavingClauseImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.clause.OrderbyClauseImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.clause.SelectClauseImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.clause.WhereClauseImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.declaration.IdentificationVariableDeclarationImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.IdentificationVariableImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.PathExpressionImpl;

/**
 * 
 * @author koichik
 */
public class SelectStatementImpl implements SelectStatement {
    private static final Logger logger = Logger
            .getLogger(SelectStatementImpl.class);

    protected final SelectClause selectClause = new SelectClauseImpl();

    protected final FromClause fromClause = new FromClauseImpl();

    protected final WhereClause whereClause = new WhereClauseImpl();

    protected final GroupbyClause groupbyClause = new GroupbyClauseImpl();

    protected final HavingClause havingClause = new HavingClauseImpl();

    protected final OrderbyClause orderbyClause = new OrderbyClauseImpl();

    protected Integer firstResult;

    protected Integer maxResults;

    /**
     * インスタンスを構築します。
     */
    public SelectStatementImpl() {
        this(false);
    }

    public SelectStatementImpl(final boolean distinct) {
        selectClause.setDistinct(distinct);
    }

    public SelectStatement select(final String selectExpression) {
        return select(new IdentificationVariableImpl(selectExpression));
    }

    public SelectStatement select(final SelectExpression selectExpression) {
        selectClause.add(selectExpression);
        return this;
    }

    public SelectStatement select(final Object... selectExpressions) {
        for (final Object selectExpression : selectExpressions) {
            if (selectExpression instanceof String) {
                select(String.class.cast(selectExpression));
            } else if (selectExpression instanceof SelectExpression) {
                select(SelectExpression.class.cast(selectExpression));
            } else {
                // TODO
                throw new IllegalArgumentException(selectExpression.toString());
            }
        }
        return this;
    }

    public SelectStatement from(final Class<?>... entityClasses) {
        for (final Class<?> entityClass : entityClasses) {
            from(new IdentificationVariableDeclarationImpl(entityClass));
        }
        return this;
    }

    public SelectStatement from(final Class<?> entityClass, final String alias) {
        return from(new IdentificationVariableDeclarationImpl(entityClass,
                new IdentificationVariableImpl(alias)));
    }

    public SelectStatement from(
            final IdentificationVariableDeclaration... declarations) {
        for (final IdentificationVariableDeclaration declaration : declarations) {
            fromClause.add(declaration);
        }
        return this;
    }

    public SelectStatement where(
            ConditionalExpression... conditionalExpressions) {
        for (final ConditionalExpression expression : conditionalExpressions) {
            whereClause.and(expression);
        }
        return this;
    }

    public SelectStatement groupby(final String... groupbyItems) {
        for (final String groupbyItem : groupbyItems) {
            groupby(new PathExpressionImpl(groupbyItem));
        }
        return this;
    }

    public SelectStatement groupby(final GroupbyItem... groupbyItems) {
        groupbyClause.add(groupbyItems);
        return this;
    }

    public SelectStatement having(
            ConditionalExpression... conditionalExpressions) {
        for (final ConditionalExpression expression : conditionalExpressions) {
            havingClause.and(expression);
        }
        return this;
    }

    public SelectStatement orderby(final String... orderbyItems) {
        for (final String orderbyItem : orderbyItems) {
            orderby(new PathExpressionImpl(orderbyItem));
        }
        return this;
    }

    public SelectStatement orderby(final OrderbyItem... orderbyItems) {
        orderbyClause.add(orderbyItems);
        return this;
    }

    public SelectStatement setFirstResult(final int startPosition) {
        this.firstResult = startPosition;
        return this;
    }

    public SelectStatement setMaxResults(final int maxResults) {
        this.maxResults = maxResults;
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getResultList(final EntityManager em) {
        return createQuery(em, true).getResultList();
    }

    @SuppressWarnings("unchecked")
    public <T> T getSingleResult(final EntityManager em) {
        return (T) createQuery(em, true).getSingleResult();
    }

    public String getQueryString() {
        return createContext().getQueryString();
    }

    public Query getQuery(final EntityManager em) {
        return createQuery(em, false);
    }

    protected Query createQuery(final EntityManager em,
            final boolean fillParameter) {
        final CriteriaContext context = createContext();
        final String queryString = context.getQueryString();
        logger.debug(queryString); // TODO
        final Query query = em.createQuery(queryString);
        if (firstResult != null && firstResult >= 0) {
            query.setFirstResult(firstResult);
        }
        if (maxResults != null && maxResults >= 0) {
            query.setMaxResults(maxResults);
        }
        if (fillParameter) {
            context.fillParameters(query);
        }
        return query;
    }

    protected CriteriaContext createContext() {
        final CriteriaContext context = new CriteriaContextImpl();
        evaluate(context);
        return context;
    }

    protected void evaluate(final CriteriaContext context) {
        assert !fromClause.isEmpty();

        if (selectClause.isEmpty()) {
            fromClause.accept(new IdentificationVarialbleVisitor() {
                public void identificationVariable(
                        final IdentificationVariable identificationVariable) {
                    select(identificationVariable);
                }
            });
        }
        selectClause.evaluate(context);
        fromClause.evaluate(context);
        whereClause.evaluate(context);
        groupbyClause.evaluate(context);
        havingClause.evaluate(context);
        orderbyClause.evaluate(context);
    }

    protected void setupParameters(final Map<String, Object> parameters,
            final Query query) {
        for (final Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
}
