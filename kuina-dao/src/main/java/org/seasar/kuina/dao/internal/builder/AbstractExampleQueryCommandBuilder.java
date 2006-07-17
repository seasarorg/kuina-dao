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

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.command.ExampleQueryCommand;

/**
 * 
 * @author koichik
 */
public abstract class AbstractExampleQueryCommandBuilder extends
        AbstractQueryCommandBuilder {

    public AbstractExampleQueryCommandBuilder(final boolean resultList) {
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

        final Class<?>[] parameterTypes = method.getParameterTypes();
        final int parameterSize = parameterTypes.length;
        if (parameterSize < 1 || parameterSize > 3
                || parameterTypes[0] != entityClass) {
            return null;
        }

        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(daoClass);
        final String[] parameterNames = beanDesc
                .getMethodParameterNames(method);
        final Annotation[][] annotations = method.getParameterAnnotations();

        return new ExampleQueryCommand(entityClass, isResultList(),
                isDistinct(method), getFirstResultParameter(parameterNames,
                        annotations), getMaxResultsParameter(parameterNames,
                        annotations));
    }

    protected abstract Class<?> resolveEntityClass(final Class<?> daoClass,
            final Method method);

    protected int getFirstResultParameter(final String[] parameterNames,
            final Annotation[][] annotations) {
        if (parameterNames == null) {
            return -1;
        }
        for (int i = 0; i < parameterNames.length; ++i) {
            if (isFirstResult(parameterNames[i], annotations[i])) {
                return i;
            }
        }
        return -1;
    }

    protected int getMaxResultsParameter(final String[] parameterNames,
            final Annotation[][] annotations) {
        if (parameterNames == null) {
            return -1;
        }
        for (int i = 0; i < parameterNames.length; ++i) {
            if (isMaxResults(parameterNames[i], annotations[i])) {
                return i;
            }
        }
        return -1;
    }
}
