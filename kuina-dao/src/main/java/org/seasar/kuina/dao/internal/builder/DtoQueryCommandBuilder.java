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
import java.util.ArrayList;
import java.util.List;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.kuina.dao.criteria.impl.grammar.declaration.IdentificationVariableDeclarationImpl;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.command.DtoQueryCommand;
import org.seasar.kuina.dao.internal.condition.ConditionalExpressionBuilder;
import org.seasar.kuina.dao.internal.condition.ConditionalExpressionBuilderFactory;

/**
 * 
 * @author koichik
 */
public class DtoQueryCommandBuilder extends AbstractDynamicQueryCommandBuilder {

    @Override
    public Command build(final Class<?> daoClass, final Method method,
            final Class<?> entityClass) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1) {
            return null;
        }
        final Class<?> parameterType = parameterTypes[0];
        if (!parameterType.getName().endsWith(convention.getDtoSuffix())) {
            return null;
        }

        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(parameterType);
        final List<Method> getterMethods = new ArrayList<Method>(beanDesc
                .getPropertyDescSize());
        final List<ConditionalExpressionBuilder> builders = new ArrayList<ConditionalExpressionBuilder>(
                beanDesc.getPropertyDescSize());
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            final PropertyDesc propertyDesc = beanDesc.getPropertyDesc(i);
            if (propertyDesc.hasReadMethod()) {
                getterMethods.add(propertyDesc.getReadMethod());
                builders.add(ConditionalExpressionBuilderFactory.createBuilder(
                        entityClass, propertyDesc.getPropertyName(),
                        propertyDesc.getPropertyType()));
            }
        }

        return new DtoQueryCommand(entityClass, isResultList(method),
                isDistinct(method), new IdentificationVariableDeclarationImpl(
                        entityClass), getterMethods
                        .toArray(new Method[getterMethods.size()]), builders
                        .toArray(new ConditionalExpressionBuilder[builders
                                .size()]));
    }

}
