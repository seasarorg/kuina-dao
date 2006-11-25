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
import java.util.Calendar;
import java.util.Date;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;
import org.seasar.framework.util.ClassUtil;
import org.seasar.kuina.dao.criteria.impl.grammar.declaration.IdentificationVariableDeclarationImpl;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.command.ParameterQueryCommand;
import org.seasar.kuina.dao.internal.condition.ConditionalExpressionBuilder;
import org.seasar.kuina.dao.internal.condition.ConditionalExpressionBuilderFactory;
import org.seasar.kuina.dao.internal.condition.FirstResultBuilder;
import org.seasar.kuina.dao.internal.condition.MaxResultsBuilder;
import org.seasar.kuina.dao.internal.condition.OrderbyBuilder;

/**
 * @author koichik
 */
public class ParameterQueryCommandBuilder extends
        AbstractDynamicQueryCommandBuilder {

    protected Class<?>[] ACCEPTABLE_TYPES = new Class<?>[] {
            Number.class, String.class, Date.class, Calendar.class, Boolean.class, Enum.class
    };

    @Override
    public Command build(final Class<?> daoClass, final Method method,
            final Class<?> entityClass) {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(daoClass);
        final String[] parameterNames = beanDesc
                .getMethodParameterNamesNoException(method);
        if (parameterNames == null) {
            return null;
        }

        for (final Class<?> parameterType : method.getParameterTypes()) {
            if (!isAcceptableType(ClassUtil
                    .getWrapperClassIfPrimitive(parameterType))) {
                return null;
            }
        }

        return new ParameterQueryCommand(entityClass, isResultList(method),
                isDistinct(method), new IdentificationVariableDeclarationImpl(
                        entityClass), parameterNames, getBuilders(
                        entityClass, method, parameterNames));
    }

    protected boolean isAcceptableType(final Class<?> parameterType) {
        for (final Class<?> acceptableType : ACCEPTABLE_TYPES) {
            if (acceptableType.isAssignableFrom(parameterType)) {
                return true;
            }
            if (parameterType.isArray()
                    && acceptableType.isAssignableFrom(parameterType
                            .getComponentType())) {
                return true;
            }
        }
        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(parameterType);
        if (entityDesc != null) {
            return true;
        }

        return false;
    }

    protected ConditionalExpressionBuilder[] getBuilders(
            final Class<?> entityClass, final Method method,
            final String[] parameterNames) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Annotation[][] parameterAnnotations = method
                .getParameterAnnotations();
        final ConditionalExpressionBuilder[] builders = 
            new ConditionalExpressionBuilder[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            final Class<?> type = parameterTypes[i];
            final String name = parameterNames[i];
            final Annotation[] annotations = parameterAnnotations[i];

            if (isOrderby(name, annotations)) {
                builders[i] = new OrderbyBuilder(entityClass);
            } else if (isFirstResult(name, annotations)) {
                builders[i] = new FirstResultBuilder();
            } else if (isMaxResults(name, annotations)) {
                builders[i] = new MaxResultsBuilder();
            } else {
                builders[i] = ConditionalExpressionBuilderFactory
                        .createBuilder(entityClass, name, type);
            }
        }
        return builders;
    }

}
