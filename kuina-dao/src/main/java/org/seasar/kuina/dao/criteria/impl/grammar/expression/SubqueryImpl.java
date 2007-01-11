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
package org.seasar.kuina.dao.criteria.impl.grammar.expression;

import org.seasar.framework.exception.SIllegalArgumentException;
import org.seasar.framework.exception.SIllegalStateException;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.IdentificationVarialbleVisitor;
import org.seasar.kuina.dao.criteria.grammar.ConditionalExpression;
import org.seasar.kuina.dao.criteria.grammar.GroupbyClause;
import org.seasar.kuina.dao.criteria.grammar.GroupbyItem;
import org.seasar.kuina.dao.criteria.grammar.HavingClause;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariable;
import org.seasar.kuina.dao.criteria.grammar.SelectExpression;
import org.seasar.kuina.dao.criteria.grammar.SimpleSelectClause;
import org.seasar.kuina.dao.criteria.grammar.SimpleSelectExpression;
import org.seasar.kuina.dao.criteria.grammar.Subquery;
import org.seasar.kuina.dao.criteria.grammar.SubqueryFromClause;
import org.seasar.kuina.dao.criteria.grammar.SubselectIdentificationVariableDeclaration;
import org.seasar.kuina.dao.criteria.grammar.WhereClause;
import org.seasar.kuina.dao.criteria.impl.grammar.clause.GroupbyClauseImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.clause.HavingClauseImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.clause.SimpleSelectClauseImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.clause.SubqueryFromClauseImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.clause.WhereClauseImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.declaration.IdentificationVariableDeclarationImpl;

/**
 * 
 * @author koichik
 */
public class SubqueryImpl implements Subquery {

    protected final SimpleSelectClause simpleSelectClause = new SimpleSelectClauseImpl();

    protected final SubqueryFromClause fromClause = new SubqueryFromClauseImpl();

    protected final WhereClause whereClause = new WhereClauseImpl();

    protected final GroupbyClause groupbyClause = new GroupbyClauseImpl();

    protected final HavingClause havingClause = new HavingClauseImpl();

    public SubqueryImpl() {
        this(false);
    }

    public SubqueryImpl(final boolean distinct) {
        simpleSelectClause.setDistinct(distinct);
    }

    public Subquery select(final String simpleSelectExpression) {
        return select(new IdentificationVariableImpl(simpleSelectExpression));
    }

    public Subquery select(final SimpleSelectExpression simpleSelectExpression) {
        simpleSelectClause.add(simpleSelectExpression);
        return this;
    }

    public Subquery select(final Object... selectExpressions) {
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

    public Subquery from(final Class<?>... entityClasses) {
        for (final Class<?> entityClass : entityClasses) {
            from(new IdentificationVariableDeclarationImpl(entityClass));
        }
        return this;
    }

    public Subquery from(final Class<?> entityClass, final String alias) {
        return from(new IdentificationVariableDeclarationImpl(entityClass,
                new IdentificationVariableImpl(alias)));
    }

    public Subquery from(
            final SubselectIdentificationVariableDeclaration... declarations) {
        for (final SubselectIdentificationVariableDeclaration declaration : declarations) {
            fromClause.add(declaration);
        }
        return this;
    }

    public Subquery where(final ConditionalExpression... conditionalExpressions) {
        for (final ConditionalExpression expression : conditionalExpressions) {
            whereClause.and(expression);
        }
        return this;
    }

    public Subquery groupby(final String... groupbyItems) {
        for (final String groupbyItem : groupbyItems) {
            groupby(new PathExpressionImpl(groupbyItem));
        }
        return this;
    }

    public Subquery groupby(final GroupbyItem... groupbyItems) {
        groupbyClause.add(groupbyItems);
        return this;
    }

    public Subquery having(
            final ConditionalExpression... conditionalExpressions) {
        for (final ConditionalExpression expression : conditionalExpressions) {
            havingClause.and(expression);
        }
        return this;
    }

    public void evaluate(final CriteriaContext context) {
        if (fromClause.isEmpty()) {
            throw new SIllegalStateException("EKuinaDao1003", new Object[] {});
        }

        if (simpleSelectClause.isEmpty()) {
            fromClause.accept(new IdentificationVarialbleVisitor() {
                public void identificationVariable(
                        final IdentificationVariable identificationVariable) {
                    select(identificationVariable);
                }
            });
        }
        context.append("(");
        simpleSelectClause.evaluate(context);
        fromClause.evaluate(context);
        whereClause.evaluate(context);
        groupbyClause.evaluate(context);
        havingClause.evaluate(context);
        context.append(")");
    }

}
