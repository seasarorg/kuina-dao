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
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.criteria.grammar.ConditionalExpression;
import org.seasar.kuina.dao.criteria.grammar.FromClause;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariableDeclaration;
import org.seasar.kuina.dao.criteria.grammar.OrderbyClause;
import org.seasar.kuina.dao.criteria.grammar.OrderbyItem;
import org.seasar.kuina.dao.criteria.grammar.SelectClause;
import org.seasar.kuina.dao.criteria.grammar.SelectExpression;
import org.seasar.kuina.dao.criteria.grammar.WhereClause;
import org.seasar.kuina.dao.criteria.impl.grammar.clause.FromClauseImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.clause.IdentificationVariableDeclarationImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.clause.OrderbyClauseImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.clause.SelectClauseImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.clause.WhereClauseImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.IdentificationVariableImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.PathExpressionImpl;

/**
 * 
 * @author koichik
 */
public class SelectStatementImpl implements SelectStatement {
    private static final Logger logger = Logger
            .getLogger(SelectStatementImpl.class);

    protected SelectClause selectClause;

    protected FromClause fromClause;

    protected WhereClause whereClause;

    protected OrderbyClause orderbyClause;

    /**
     * インスタンスを構築します。
     */
    public SelectStatementImpl() {
        this(false);
    }

    public SelectStatementImpl(final boolean distinct) {
        selectClause = new SelectClauseImpl(distinct);
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
            from(entityClass);
        }
        return this;
    }

    public SelectStatement from(final Class<?> entityClass) {
        return from(new IdentificationVariableDeclarationImpl(entityClass));
    }

    public SelectStatement from(final Class<?> entityClass, final String alias) {
        return from(new IdentificationVariableDeclarationImpl(entityClass,
                new IdentificationVariableImpl(alias)));
    }

    public SelectStatement from(
            final IdentificationVariableDeclaration... declarations) {
        for (final IdentificationVariableDeclaration declaration : declarations) {
            if (fromClause == null) {
                fromClause = new FromClauseImpl(declaration);
            } else {
                fromClause.add(declaration);
            }
        }
        return this;
    }

    public SelectStatement where(ConditionalExpression conditionalExpression) {
        if (whereClause == null) {
            whereClause = new WhereClauseImpl(conditionalExpression);
        } else {
            whereClause.and(conditionalExpression);
        }
        return this;
    }

    public SelectStatement where(
            ConditionalExpression... conditionalExpressions) {
        for (final ConditionalExpression expression : conditionalExpressions) {
            where(expression);
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
        if (orderbyClause == null) {
            orderbyClause = new OrderbyClauseImpl(orderbyItems);
        } else {
            orderbyClause.add(orderbyItems);
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getResultList(final EntityManager em) {
        return createQuery(em).getResultList();
    }

    @SuppressWarnings("unchecked")
    public <T> T getSingleResult(final EntityManager em) {
        return (T) createQuery(em).getSingleResult();
    }

    public Query getQuery(final EntityManager em) {
        final CriteriaContext context = new CriteriaContextImpl();
        evaluate(context);
        final String queryString = context.getQueryString().trim();
        logger.debug(queryString);
        return em.createQuery(queryString);
    }

    protected Query createQuery(final EntityManager em) {
        final CriteriaContext context = new CriteriaContextImpl();
        evaluate(context);
        final String queryString = context.getQueryString().trim();
        logger.debug(queryString);
        final Query query = em.createQuery(queryString);
        context.fillParameters(query);
        return query;
    }

    protected void evaluate(final CriteriaContext context) {
        evaluateSelectClause(context);
        evaluateFromClause(context);
        evaluateWhereClause(context);
        evaluateOrderbyClause(context);
    }

    protected void evaluateSelectClause(final CriteriaContext context) {
        if (selectClause.isEmpty()) {
            assert fromClause != null;
            assert fromClause.size() == 1;
            for (int i = 0; i < fromClause.size(); ++i) {
                select(fromClause.getIdentificationVariable(i));
            }
        }
        selectClause.evaluate(context);
    }

    protected void evaluateFromClause(final CriteriaContext context) {
        assert fromClause != null;
        fromClause.evaluate(context);
    }

    protected void evaluateWhereClause(final CriteriaContext context) {
        if (whereClause != null) {
            whereClause.evaluate(context);
        }
    }

    protected void evaluateOrderbyClause(final CriteriaContext context) {
        if (orderbyClause != null) {
            orderbyClause.evaluate(context);
        }
    }

    protected void setupParameters(final Map<String, Object> parameters,
            final Query query) {
        for (final Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
}
