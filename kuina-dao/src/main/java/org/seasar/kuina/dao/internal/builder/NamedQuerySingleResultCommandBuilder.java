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
import java.util.regex.Pattern;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.kuina.dao.annotation.QueryName;
import org.seasar.kuina.dao.annotation.TargetEntity;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.CommandBuilder;
import org.seasar.kuina.dao.internal.EntityDesc;
import org.seasar.kuina.dao.internal.command.NamedQuerySingleResultCommand;
import org.seasar.kuina.dao.internal.metadata.EntityDescFactory;
import org.seasar.kuina.dao.util.ObjectParameterBinder;
import org.seasar.kuina.dao.util.ParameterBinder;

/**
 * 
 * @author koichik
 */
public class NamedQuerySingleResultCommandBuilder implements CommandBuilder {
    protected Pattern methodNamePattern = Pattern.compile("get.+");

    public NamedQuerySingleResultCommandBuilder() {
    }

    public void setMethodNamePattern(final String methodNamePattern) {
        this.methodNamePattern = Pattern.compile(methodNamePattern);
    }

    public Command build(final Class<?> daoClass, final Method method) {
        final String methodName = method.getName();
        if (!methodNamePattern.matcher(methodName).matches()) {
            return null;
        }

        final QueryName queryName = method.getAnnotation(QueryName.class);
        if (queryName != null) {
            final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(daoClass);
            return new NamedQuerySingleResultCommand(
                    queryName.value(),
                    getBinders(method, beanDesc.getMethodParameterNames(method)));
        }

        final Class<?> entityClass = resolveEntityClass(daoClass, method);
        if (entityClass != null) {
            return createCommand(daoClass, method, entityClass);
        }
        return null;
    }

    protected Command createCommand(final Class<?> daoClass,
            final Method method, final Class<?> entityClass) {
        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(entityClass);
        if (!entityDesc.isEntity()) {
            return null;
        }
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(daoClass);
        return new NamedQuerySingleResultCommand(getQueryName(entityDesc,
                method), getBinders(method, beanDesc
                .getMethodParameterNames(method)));
    }

    protected Class<?> resolveEntityClass(final Class<?> daoClass,
            final Method method) {
        TargetEntity targetEntity = method.getAnnotation(TargetEntity.class);
        if (targetEntity != null) {
            return targetEntity.value();
        }

        targetEntity = daoClass.getAnnotation(TargetEntity.class);
        if (targetEntity != null) {
            return targetEntity.value();
        }
        return method.getReturnType();
    }

    protected String getQueryName(final EntityDesc entityDesc,
            final Method method) {
        final QueryName queryName = method.getAnnotation(QueryName.class);
        if (queryName != null) {
            return queryName.value();
        }

        return entityDesc.getName() + "." + method.getName();
    }

    protected ParameterBinder[] getBinders(final Method method,
            final String[] parameterNames) {
        if (parameterNames == null) {
            return getBindersForPositionalParameter(method);
        }

        final Class<?>[] parameterTypes = method.getParameterTypes();
        final ParameterBinder[] binders = new ParameterBinder[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            final String name = parameterNames[i];
            binders[i] = new ObjectParameterBinder(name);
        }
        return binders;
    }

    protected ParameterBinder[] getBindersForPositionalParameter(
            final Method method) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final ParameterBinder[] binders = new ParameterBinder[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            binders[i] = new ObjectParameterBinder(i);
        }
        return binders;
    }
}
