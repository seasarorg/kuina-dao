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
package org.seasar.kuina.dao.internal.command;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;

import org.seasar.framework.jpa.metadata.AttributeDesc;
import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariableDeclaration;
import org.seasar.kuina.dao.criteria.impl.grammar.declaration.IdentificationVariableDeclarationImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.IdentificationVariableImpl;
import org.seasar.kuina.dao.internal.util.JpqlUtil;
import org.seasar.kuina.dao.internal.util.SelectStatementUtil;

import static org.seasar.kuina.dao.criteria.CriteriaOperations.eq;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.parameter;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.path;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.select;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.selectDistinct;

/**
 * 
 * @author koichik
 */
public class ExampleQueryCommand extends AbstractQueryCommand {

    protected Class<?> entityClass;

    protected EntityDesc entityDesc;

    protected boolean resultList;

    protected boolean distinct;

    protected int orderby;

    protected int firstResult;

    protected int maxResults;

    /**
     * インスタンスを構築します。
     */
    public ExampleQueryCommand(final Class<?> entityClass,
            final boolean resultList, final boolean distinct,
            final int orderby, final int firstResult, final int maxResults) {
        this.entityClass = entityClass;
        this.entityDesc = EntityDescFactory.getEntityDesc(entityClass);
        this.resultList = resultList;
        this.distinct = distinct;
        this.orderby = orderby;
        this.firstResult = firstResult;
        this.maxResults = maxResults;
    }

    public Object execute(final EntityManager em, final Object[] arguments) {
        final SelectStatement statement = createSelectStatement(arguments);
        return resultList ? statement.getResultList(em) : statement
                .getSingleResult(em);
    }

    @SuppressWarnings("unchecked")
    protected SelectStatement createSelectStatement(final Object[] arguments) {
        final String alias = JpqlUtil
                .toDefaultIdentificationVariable(entityDesc.getEntityName());
        final SelectStatement statement = distinct ? selectDistinct(path(alias))
                : select(path(alias));
        final IdentificationVariableDeclaration fromDecl = new IdentificationVariableDeclarationImpl(
                entityClass, new IdentificationVariableImpl(alias));

        final Object entity = arguments[0];
        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(entityClass);
        addCondition(statement, fromDecl, entityDesc, entity, alias + ".");

        statement.from(fromDecl);
        if (orderby >= 0) {
            SelectStatementUtil.appendOrderbyClause(statement,
                    arguments[orderby]);
        }
        if (firstResult >= 0) {
            statement.setFirstResult(Number.class.cast(arguments[firstResult])
                    .intValue());
        }
        if (maxResults >= 0) {
            statement.setMaxResults(Number.class.cast(arguments[maxResults])
                    .intValue());
        }

        return statement;
    }

    @SuppressWarnings("unchecked")
    protected void addCondition(final SelectStatement statement,
            final IdentificationVariableDeclaration fromDecl,
            final EntityDesc entityDesc, final Object entity, final String alias) {

        final AttributeDesc[] attributes = entityDesc.getAttributeDescs();
        for (final AttributeDesc attribute : attributes) {
            addCondition(statement, fromDecl, entity, attribute, alias);
        }
    }

    @SuppressWarnings("unchecked")
    protected void addCondition(final SelectStatement statement,
            final IdentificationVariableDeclaration fromDecl,
            final Object entity, final AttributeDesc attribute,
            final String alias) {
        final Object value = attribute.getValue(entity);
        if (value == null) {
            return;
        }

        final String name = attribute.getName();
        final String path = alias + name;
        final String parameterName = path.replace('.', '$');
        final Class<?> type = attribute.getType();
        if (String.class.isAssignableFrom(type)) {
            statement.where(eq(path, parameter(parameterName, String.class
                    .cast(value))));
        } else if (Number.class.isAssignableFrom(type)) {
            statement.where(eq(path, parameter(parameterName, Number.class
                    .cast(value))));
        } else if (Boolean.class.isAssignableFrom(type)) {
            statement.where(eq(path, parameter(parameterName, Boolean.class
                    .cast(value))));
        } else if (java.sql.Date.class.isAssignableFrom(type)) {
            statement.where(eq(path, parameter(parameterName,
                    java.sql.Date.class.cast(value))));
        } else if (java.sql.Time.class.isAssignableFrom(type)) {
            statement.where(eq(path, parameter(parameterName,
                    java.sql.Time.class.cast(value))));
        } else if (java.sql.Timestamp.class.isAssignableFrom(type)) {
            statement.where(eq(path, parameter(parameterName,
                    java.sql.Timestamp.class.cast(value))));
        } else if (Date.class.isAssignableFrom(type)) {
            statement.where(eq(path, parameter(parameterName, Date.class
                    .cast(value), attribute.getTemporalType())));
        } else if (Calendar.class.isAssignableFrom(type)) {
            statement.where(eq(path, parameter(parameterName, Calendar.class
                    .cast(value), attribute.getTemporalType())));
        } else if (attribute.isAssociation() && attribute.isCollection()) {
            final Collection collection = Collection.class.cast(value);
            if (collection != null && collection.size() == 1) {
                final Class<?> elementType = attribute.getElementType();
                final EntityDesc elementDesc = EntityDescFactory
                        .getEntityDesc(elementType);
                fromDecl.inner(path, name);
                addCondition(statement, fromDecl, elementDesc, collection
                        .iterator().next(), name + ".");
            }
        } else {
            final EntityDesc entityDesc = EntityDescFactory.getEntityDesc(type);
            if (entityDesc != null) {
                fromDecl.inner(path, name);
                addCondition(statement, fromDecl, entityDesc, value, name + ".");
            } else {
                throw new RuntimeException(); // TODO
            }
        }
    }

}
