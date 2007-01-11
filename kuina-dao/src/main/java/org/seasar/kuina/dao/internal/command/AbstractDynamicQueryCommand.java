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
package org.seasar.kuina.dao.internal.command;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;

import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.FetchJoin;
import org.seasar.kuina.dao.FetchJoins;
import org.seasar.kuina.dao.JoinSpec;
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariableDeclaration;
import org.seasar.kuina.dao.criteria.impl.grammar.declaration.IdentificationVariableDeclarationImpl;
import org.seasar.kuina.dao.internal.util.JpqlUtil;

import static org.seasar.kuina.dao.criteria.CriteriaOperations.*;

/**
 * 
 * @author koichik
 */
public abstract class AbstractDynamicQueryCommand extends AbstractQueryCommand {

    protected Class<?> entityClass;

    protected Method method;

    protected boolean resultList;

    protected boolean distinct;

    protected String identificationVariable;

    public AbstractDynamicQueryCommand(final Class<?> entityClass,
            final Method method, final boolean resultList,
            final boolean distinct) {
        this.entityClass = entityClass;
        this.method = method;
        this.resultList = resultList;
        this.distinct = distinct;
        this.identificationVariable = JpqlUtil
                .toDefaultIdentificationVariable(entityClass);
    }

    public Object execute(final EntityManager em, final Object[] arguments) {
        final SelectStatement statement = createSelectStatement(arguments);
        return resultList ? statement.getResultList(em) : statement
                .getSingleResult(em);
    }

    protected SelectStatement createSelectStatement(final Object[] arguments) {
        final SelectStatement statement = distinct ? selectDistinct(path(identificationVariable))
                : select(path(identificationVariable));
        final List<String> boundProperties = bindParameter(statement, arguments);
        statement
                .from(createIdentificationVariableDeclaration(boundProperties));
        return statement;
    }

    protected abstract List<String> bindParameter(SelectStatement statement,
            Object[] arguments);

    protected IdentificationVariableDeclaration createIdentificationVariableDeclaration(
            final List<String> boundProperties) {
        final IdentificationVariableDeclaration fromDecl = new IdentificationVariableDeclarationImpl(
                entityClass);
        for (final Entry<String, JoinSpec> entry : createFetchJoinAssociations(
                fromDecl.getIdentificationVariable().toString()).entrySet()) {
            if (entry.getValue() == JoinSpec.INNER_JOIN) {
                fromDecl.innerFetch(entry.getKey());
            } else {
                fromDecl.leftFetch(entry.getKey());
            }
        }
        for (final String association : createJoinAssociations(boundProperties)) {
            final int pos1 = association.lastIndexOf('.');
            if (pos1 == -1) {
                fromDecl.inner(identificationVariable + "." + association,
                        association);
                continue;
            }
            final int pos2 = association.lastIndexOf('.', pos1 - 1);
            if (pos2 == -1) {
                fromDecl.inner(association, association.substring(pos1 + 1));
                continue;
            }
            fromDecl.inner(association.substring(pos2 + 1), association
                    .substring(pos1 + 1));
        }
        return fromDecl;
    }

    protected Map<String, JoinSpec> createFetchJoinAssociations(
            final String abstractSchemaName) {
        final Map<String, JoinSpec> associations = CollectionsUtil.newTreeMap();
        final FetchJoins joins = method.getAnnotation(FetchJoins.class);
        if (joins != null) {
            for (final FetchJoin join : joins.value()) {
                associations.put(abstractSchemaName + "." + join.value(), join
                        .joinSpec());
            }
        } else {
            final FetchJoin join = method.getAnnotation(FetchJoin.class);
            if (join != null) {
                associations.put(abstractSchemaName + "." + join.value(), join
                        .joinSpec());
            }
        }
        return associations;
    }

    protected Set<String> createJoinAssociations(
            final List<String> boundProperties) {
        final Set<String> associations = CollectionsUtil.newTreeSet();
        for (int i = 0; i < boundProperties.size(); ++i) {
            final String propertyName = boundProperties.get(i);
            int pos = propertyName.length();
            while ((pos = propertyName.lastIndexOf('.', pos - 1)) > -1) {
                final String path = propertyName.substring(0, pos);
                associations.add(path);
            }
        }
        return associations;
    }

}
