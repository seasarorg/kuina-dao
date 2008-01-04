/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import javax.persistence.FlushModeType;
import javax.persistence.Query;

import org.seasar.framework.exception.SIllegalArgumentException;
import org.seasar.framework.exception.SIllegalStateException;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.tiger.CollectionsUtil;
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
 * JPQLのslelect_statementの実装クラスです．
 * 
 * @author koichik
 */
public class SelectStatementImpl implements SelectStatement {

    // static fields
    private static final Logger logger = Logger
            .getLogger(SelectStatementImpl.class);

    // instance fields
    /** SELECT句 */
    protected final SelectClause selectClause = new SelectClauseImpl();

    /** FROM句 */
    protected final FromClause fromClause = new FromClauseImpl();

    /** WHERE句 */
    protected final WhereClause whereClause = new WhereClauseImpl();

    /** GROUP BY句 */
    protected final GroupbyClause groupbyClause = new GroupbyClauseImpl();

    /** HAVING句 */
    protected final HavingClause havingClause = new HavingClauseImpl();

    /** ORDER BY句 */
    protected final OrderbyClause orderbyClause = new OrderbyClauseImpl();

    /**
     * 取得する結果セットの最初の位置
     * 
     * @see javax.persistence.Query#setFirstResult(int)
     */
    protected Integer firstResult;

    /**
     * 取得する結果セットの最大件数
     * 
     * @see javax.persistence.Query#setMaxResults(int)
     */
    protected Integer maxResults;

    /**
     * 問い合わせを実行する際のフラッシュモード
     * 
     * @see javax.persistence.Query#setFlushMode(javax.persistence.FlushModeType)
     */
    protected FlushModeType flushMode;

    /**
     * 問い合わせのヒント
     * 
     * @see javax.persistence.Query#setHint(String, Object)
     */
    protected Map<String, Object> hints = CollectionsUtil.newLinkedHashMap();

    /**
     * インスタンスを構築します。
     */
    public SelectStatementImpl() {
        this(false);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param distinct
     *            DISTINCTを指定する場合は<code>true</code>，それ以外の場合は<code>false</code>
     */
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
                throw new SIllegalArgumentException("EKuinaDao1002",
                        new Object[] { selectExpression });
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
            final ConditionalExpression... conditionalExpressions) {
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
            final ConditionalExpression... conditionalExpressions) {
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

    public SelectStatement setFlushMode(final FlushModeType flushMode) {
        this.flushMode = flushMode;
        return this;
    }

    public SelectStatement addHint(final String name, final Object value) {
        hints.put(name, value);
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

    /**
     * このSELECT文を実行するための{@link javax.persistence.Query}を作成します．
     * 
     * @param em
     *            エンティティ・マネージャ
     * @param fillParameter
     *            {@link javax.persistence.Query}にパラメータをバインドするなら<code>true</code>，それ以外の場合は<code>false</code>
     * @return このSELECT文を実行するための{@link javax.persistence.Query}
     */
    protected Query createQuery(final EntityManager em,
            final boolean fillParameter) {
        final CriteriaContext context = createContext();
        final String queryString = context.getQueryString();
        if (logger.isInfoEnabled()) {
            logger.log("IKuinaDao1000", new Object[] { queryString });
        }
        final Query query = em.createQuery(queryString);
        if (firstResult != null && firstResult >= 0) {
            query.setFirstResult(firstResult);
        }
        if (maxResults != null && maxResults >= 0) {
            query.setMaxResults(maxResults);
        }
        if (flushMode != null) {
            query.setFlushMode(flushMode);
        }
        for (final Entry<String, Object> entry : hints.entrySet()) {
            query.setHint(entry.getKey(), entry.getValue());
        }
        if (fillParameter) {
            context.fillParameters(query);
        }
        return query;
    }

    /**
     * JPQLを作成するためのコンテキスト情報を作成して返します．
     * 
     * @return JPQLを作成するためのコンテキスト情報
     */
    protected CriteriaContext createContext() {
        final CriteriaContext context = new CriteriaContextImpl();
        evaluate(context);
        return context;
    }

    /**
     * この問い合わせを評価し，コンテキスト情報に反映します．
     * 
     * @param context
     *            JPQLを作成するためのコンテキスト情報
     */
    protected void evaluate(final CriteriaContext context) {
        if (fromClause.isEmpty()) {
            throw new SIllegalStateException("EKuinaDao1003", new Object[] {});
        }

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

    /**
     * {@link javax.persistence.Query}にパラメータをバインドします．
     * 
     * @param parameters
     *            バインドパラメータの{@link java.util.Map}
     * @param query
     *            {@link javax.persistence.Query}
     */
    protected void setupParameters(final Map<String, Object> parameters,
            final Query query) {
        for (final Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }

}
