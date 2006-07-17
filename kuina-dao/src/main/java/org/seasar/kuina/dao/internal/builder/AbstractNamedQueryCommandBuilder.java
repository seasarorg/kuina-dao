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
package org.seasar.kuina.dao.internal.builder;

import java.lang.reflect.Method;

import javax.persistence.EntityManager;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.annotation.tiger.Aspect;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.jpa.EntityDesc;
import org.seasar.framework.jpa.EntityDescFactory;
import org.seasar.kuina.dao.annotation.QueryName;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.command.NamedQueryCommand;

/**
 * 
 * @author koichik
 */
@Component
public abstract class AbstractNamedQueryCommandBuilder extends
        AbstractQueryCommandBuilder {

    public AbstractNamedQueryCommandBuilder(boolean resultList) {
        super(resultList);
    }

    @Binding(bindingType = BindingType.MUST)
    protected EntityManager em;

    public Command build(final Class<?> daoClass, final Method method) {
        if (!isMatched(method)) {
            return null;
        }

        final String queryName = getQueryName(daoClass, method, isResultList());
        if (queryName == null || !isExists(queryName)) {
            return null;
        }

        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(daoClass);
        return new NamedQueryCommand(isResultList(), queryName, getBinders(
                method, beanDesc.getMethodParameterNames(method)));
    }

    protected String getQueryName(final Class<?> daoClass, final Method method,
            final boolean resultList) {
        final QueryName queryName = method.getAnnotation(QueryName.class);
        if (queryName != null) {
            return queryName.value();
        }

        final Class<?> entityClass = resultList ? getElementTypeOfList(method
                .getGenericReturnType()) : getTargetClass(daoClass, method);
        if (entityClass == null) {
            return null;
        }

        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(entityClass);
        if (entityDesc == null) {
            return null;
        }

        return entityDesc.getEntityName() + "." + method.getName();
    }

    protected abstract Class<?> resolveEntityClass(final Class<?> daoClass,
            final Method method);

    @Aspect("j2ee.requiresNewTx")
    public boolean isExists(final String queryName) {
        try {
            em.createNamedQuery(queryName);
            return true;
        } catch (final Exception ignore) {
        }
        return false;
    }
}
