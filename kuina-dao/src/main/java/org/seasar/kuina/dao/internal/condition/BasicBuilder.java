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
package org.seasar.kuina.dao.internal.condition;

import java.lang.reflect.Method;

import org.seasar.framework.util.tiger.ReflectionUtil;
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.criteria.grammar.ConditionalExpression;

/**
 * 
 * @author koichik
 */
public class BasicBuilder extends AbstractConditionalExpressionBuilder {

    public BasicBuilder(final Class<?> entityClass, final String propertyName,
            final String parameterName, final Method parameterMethod,
            final Method operationMethod) {
        super(entityClass, propertyName, parameterName, parameterMethod,
                operationMethod);
    }

    public String appendCondition(final SelectStatement statement,
            final Object value) {
        if (value == null) {
            return null;
        }

        final Object parameter = ReflectionUtil.invokeStatic(
                getParameterMethod(), getParameterName(), value);
        final Object expression = ReflectionUtil.invokeStatic(
                getOperationMethod(), getPropertyName(), parameter);
        statement.where(ConditionalExpression.class.cast(expression));
        return getPropertyPath();
    }

}
