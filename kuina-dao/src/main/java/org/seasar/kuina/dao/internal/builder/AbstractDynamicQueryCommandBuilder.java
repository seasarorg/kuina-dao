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

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.kuina.dao.criteria.impl.grammar.declaration.IdentificationVariableDeclarationImpl;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.command.DynamicQueryCommand;

/**
 * 
 * @author koichik
 */
public abstract class AbstractDynamicQueryCommandBuilder extends
        AbstractQueryCommandBuilder {

    public AbstractDynamicQueryCommandBuilder(final boolean resultList) {
        super(resultList);
    }

    public Command build(final Class<?> daoClass, final Method method) {
        if (!isMatched(method)) {
            return null;
        }

        final Class<?> entityClass = resolveEntityClass(daoClass, method);
        if (entityClass == null) {
            return null;
        }

        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(daoClass);
        final String[] parameterNames = beanDesc
                .getMethodParameterNames(method);
        if (parameterNames == null) {
            return null;
        }

        return new DynamicQueryCommand(entityClass, isResultList(),
                isDistinct(method), new IdentificationVariableDeclarationImpl(
                        entityClass), parameterNames, getBinders(method,
                        parameterNames));
    }

    protected abstract Class<?> resolveEntityClass(final Class<?> daoClass,
            final Method method);

}
