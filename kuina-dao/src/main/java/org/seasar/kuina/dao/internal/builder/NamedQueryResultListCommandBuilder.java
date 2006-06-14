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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.command.NamedQueryResultListCommand;

/**
 * 
 * @author koichik
 */
public class NamedQueryResultListCommandBuilder extends
        AbstractNamedQueryCommandBuilder {

    public NamedQueryResultListCommandBuilder() {
        setMethodNamePattern("find.+");
    }

    public Command build(final Class<?> daoClass, final Method method) {
        if (!isMatch(method)) {
            return null;
        }

        final String queryName = getQueryName(daoClass, method);
        if (queryName == null) {
            return null;
        }

        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(daoClass);
        return new NamedQueryResultListCommand(queryName, getBinders(method,
                beanDesc.getMethodParameterNames(method)));
    }

    @Override
    protected Class<?> resolveEntityClass(final Class<?> daoClass,
            final Method method) {
        final Type type = method.getGenericReturnType();
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
        if (!rawClass.isAssignableFrom(List.class)) {
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
}
