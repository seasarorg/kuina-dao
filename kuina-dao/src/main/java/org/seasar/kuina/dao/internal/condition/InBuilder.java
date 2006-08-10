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
package org.seasar.kuina.dao.internal.condition;

import java.lang.reflect.Method;
import java.util.Collection;

import org.seasar.framework.util.tiger.ReflectionUtil;
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.criteria.grammar.ConditionalExpression;
import org.seasar.kuina.dao.criteria.grammar.InputParameter;

/**
 * 
 * @author koichik
 */
public class InBuilder extends AbstractConditionalExpressionBuilder {

    public InBuilder(final String propertyName, final String parameterName,
            final Method parameterMethod, final Method operationMethod) {
        super(propertyName, parameterName, parameterMethod, operationMethod);
    }

    public void appendCondition(final SelectStatement statement,
            final Object value) {
        if (value == null) {
            return;
        }

        final Object parameter = createParameters(value);
        final Object expression = ReflectionUtil.invokeStatic(
                getOperationMethod(), getPropertyName(), parameter);
        statement.where(ConditionalExpression.class.cast(expression));
    }

    protected Object createParameters(final Object values) {
        if (Object[].class.isInstance(values)) {
            return createParametersFromArray(Object[].class.cast(values));
        }
        if (Collection.class.isInstance(values)) {
            return createParameterFromCollection(Collection.class.cast(values));
        }
        throw new IllegalArgumentException();// TODO
    }

    protected Object createParametersFromArray(final Object[] values) {
        final InputParameter[] parameters = new InputParameter[values.length];
        for (int i = 0; i < values.length; ++i) {
            parameters[i] = ReflectionUtil.invokeStatic(getParameterMethod(),
                    getParameterName() + i, values[i]);
        }
        return parameters;
    }

    protected Object createParameterFromCollection(final Collection values) {
        final Object[] parameters = new Object[values.size()];
        int i = 0;
        for (final Object value : values) {
            parameters[i++] = ReflectionUtil.invokeStatic(getParameterMethod(),
                    getParameterName(), value);
        }
        return parameters;
    }

}
