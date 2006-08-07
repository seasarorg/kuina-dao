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

import javax.persistence.EntityManager;

import org.seasar.framework.jpa.EntityDesc;
import org.seasar.framework.jpa.EntityDescFactory;
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariableDeclaration;
import org.seasar.kuina.dao.criteria.impl.JpqlUtil;

import static org.seasar.kuina.dao.criteria.CriteriaOperations.path;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.select;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.selectDistinct;

/**
 * 
 * @author koichik
 */
public abstract class AbstractDynamicQueryCommand extends AbstractQueryCommand {

    protected Class<?> entityClass;

    protected boolean resultList;

    protected boolean distinct;

    protected IdentificationVariableDeclaration fromDecl;

    public AbstractDynamicQueryCommand(final Class<?> entityClass,
            final boolean resultList, final boolean distinct,
            final IdentificationVariableDeclaration fromDecl) {
        this.entityClass = entityClass;
        this.resultList = resultList;
        this.distinct = distinct;
        this.fromDecl = fromDecl;
    }

    public Object execute(final EntityManager em, final Object[] arguments) {
        final SelectStatement statement = createSelectStatement(arguments);
        System.out.println(statement.getQueryString()); // TODO
        return resultList ? statement.getResultList(em) : statement
                .getSingleResult(em);
    }

    protected SelectStatement createSelectStatement(final Object[] arguments) {
        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(entityClass);
        assert entityDesc != null;
        final String alias = JpqlUtil
                .toDefaultIdentificationVariable(entityDesc.getEntityName());
        final SelectStatement statement = distinct ? selectDistinct(path(alias))
                : select(path(alias));
        statement.from(fromDecl);
        bindParameter(statement, arguments);
        return statement;
    }

    protected abstract void bindParameter(SelectStatement statement,
            Object[] arguments);

}
