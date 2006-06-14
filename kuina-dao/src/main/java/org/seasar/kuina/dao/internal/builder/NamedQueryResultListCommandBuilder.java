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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.kuina.dao.annotation.FirstResult;
import org.seasar.kuina.dao.annotation.MaxResults;
import org.seasar.kuina.dao.annotation.NamedParameter;
import org.seasar.kuina.dao.annotation.QueryName;
import org.seasar.kuina.dao.entity.EntityDesc;
import org.seasar.kuina.dao.entity.EntityDescFactory;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.CommandBuilder;
import org.seasar.kuina.dao.internal.binder.FirstResultBinder;
import org.seasar.kuina.dao.internal.binder.MaxResultsBinder;
import org.seasar.kuina.dao.internal.binder.ObjectParameterBinder;
import org.seasar.kuina.dao.internal.binder.ParameterBinder;
import org.seasar.kuina.dao.internal.command.NamedQueryResultListCommand;

/**
 * 
 * @author koichik
 */
public class NamedQueryResultListCommandBuilder implements CommandBuilder {
    protected Pattern methodNamePattern = Pattern.compile("find.+");

    protected Pattern firstResultPattern = Pattern.compile("firstResult");

    protected Pattern maxResultsPattern = Pattern.compile("maxResults");

    public NamedQueryResultListCommandBuilder() {
    }

    public void setMethodNamePattern(final String methodNamePattern) {
        this.methodNamePattern = Pattern.compile(methodNamePattern);
    }

    public void setFirstResultPattern(final String firstResultPattern) {
        this.firstResultPattern = Pattern.compile(firstResultPattern);
    }

    public void setMaxResultsPattern(final String maxResultsPattern) {
        this.maxResultsPattern = Pattern.compile(maxResultsPattern);
    }

    public Command build(final Class<?> daoClass, final Method method) {
        final String methodName = method.getName();
        if (!methodNamePattern.matcher(methodName).matches()) {
            return null;
        }

        final Class<?> entityClass = getElementTypeIfList(method
                .getGenericReturnType(), List.class);
        if (entityClass == null) {
            return null;
        }

        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(entityClass);
        if (!entityDesc.isEntity()) {
            return null;
        }

        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(daoClass);
        return new NamedQueryResultListCommand(
                getQueryName(entityDesc, method), getBinders(method, beanDesc
                        .getMethodParameterNames(method)));
    }

    protected Class<?> getElementTypeIfList(final Type type,
            final Class<? extends Collection> collectionClass) {
        if (!(type instanceof ParameterizedType)) {
            return null;
        }
        final ParameterizedType parameterizedType = ParameterizedType.class
                .cast(type);
        final Type rawType = parameterizedType.getRawType();
        if (!(rawType instanceof Class)) {
            return null;
        }

        final Class<?> rawClass = Class.class.cast(rawType);
        if (!rawClass.isAssignableFrom(collectionClass)) {
            return null;
        }

        final Type[] actualTypeArgument = parameterizedType
                .getActualTypeArguments();
        if (actualTypeArgument == null || actualTypeArgument.length != 1) {
            return null;
        }
        if (!(actualTypeArgument[0] instanceof Class)) {
            return null;
        }

        return Class.class.cast(actualTypeArgument[0]);
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
        final Annotation[][] parameterAnnotations = method
                .getParameterAnnotations();
        final ParameterBinder[] binders = new ParameterBinder[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            final String name = parameterNames[i];
            final Annotation[] annotations = parameterAnnotations[i];
            if (isFirstResult(name, annotations)) {
                binders[i] = new FirstResultBinder();
            } else if (isMaxResults(name, annotations)) {
                binders[i] = new MaxResultsBinder();
            } else {
                binders[i] = new ObjectParameterBinder(name);
            }
        }
        return binders;
    }

    protected ParameterBinder[] getBindersForPositionalParameter(
            final Method method) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Annotation[][] parameterAnnotations = method
                .getParameterAnnotations();
        final ParameterBinder[] binders = new ParameterBinder[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            final Annotation[] annotations = parameterAnnotations[i];
            if (isFirstResult(null, annotations)) {
                binders[i] = new FirstResultBinder();
            } else if (isMaxResults(null, annotations)) {
                binders[i] = new MaxResultsBinder();
            } else {
                binders[i] = new ObjectParameterBinder(i);
            }
        }
        return binders;
    }

    protected boolean isFirstResult(final String name,
            final Annotation[] annotations) {
        for (final Annotation annotation : annotations) {
            if (annotation instanceof NamedParameter) {
                return false;
            } else if (annotation instanceof FirstResult)
                return true;
        }
        return name != null && firstResultPattern.matcher(name).matches();
    }

    protected boolean isMaxResults(final String name,
            final Annotation[] annotations) {
        for (final Annotation annotation : annotations) {
            if (annotation instanceof NamedParameter) {
                return false;
            } else if (annotation instanceof MaxResults) {
                return true;
            }
        }
        return name != null && maxResultsPattern.matcher(name).matches();
    }
}
