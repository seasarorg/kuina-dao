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
package org.seasar.kuina.dao.internal.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.command.ParameterQueryCommand;
import org.seasar.kuina.dao.internal.condition.ConditionalExpressionBuilder;

/**
 * @author koichik
 */
public class ParameterQueryCommandBuilder extends
        AbstractDynamicQueryCommandBuilder {

    protected static final Class<?>[] ACCEPTABLE_TYPES = new Class<?>[] {
        Number.class, String.class, Boolean.class, Date.class, Calendar.class, Enum.class,
    };

    @Override
    public Command build(final Class<?> daoClass, final Method method,
            final Class<?> entityClass) {
        final String[] parameterNames = getParameterNames(daoClass, method);
        if (parameterNames == null) {
            return null;
        }

        for (final Class<?> parameterType : method.getParameterTypes()) {
            if (!isAcceptableType(ClassUtil
                    .getWrapperClassIfPrimitive(parameterType))) {
                return null;
            }
        }

        final ConditionalExpressionBuilder[] builders = createBuilders(
                entityClass, method, parameterNames);
        return new ParameterQueryCommand(entityClass, method, isResultList(method),
                parameterNames, builders);
    }

    protected boolean isAcceptableType(final Class<?> parameterType) {
        if (parameterType.isArray()) {
            return isAcceptableType(parameterType.getComponentType());
        }
        for (final Class<?> acceptableType : ACCEPTABLE_TYPES) {
            if (acceptableType.isAssignableFrom(parameterType)) {
                return true;
            }
        }
        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(parameterType);
        return entityDesc != null;
    }

    protected ConditionalExpressionBuilder[] createBuilders(
            final Class<?> entityClass, final Method method,
            final String[] parameterNames) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Annotation[][] parameterAnnotations = method
                .getParameterAnnotations();
        final List<ConditionalExpressionBuilder> builders = CollectionsUtil
                .newArrayList();
        for (int i = 0; i < parameterTypes.length; ++i) {
            builders.add(createBuilder(entityClass, parameterNames[i],
                    parameterTypes[i], parameterAnnotations[i]));
        }
        return builders.toArray(new ConditionalExpressionBuilder[builders
                .size()]);
    }

}
