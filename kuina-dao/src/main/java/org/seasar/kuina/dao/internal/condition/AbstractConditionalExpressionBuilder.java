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

import org.seasar.kuina.dao.internal.util.JpqlUtil;

/**
 * 
 * @author koichik
 */
public abstract class AbstractConditionalExpressionBuilder implements
        ConditionalExpressionBuilder {

    protected String identificationVariable;

    protected String propertyPath;

    protected String propertyName;

    protected String parameterName;

    protected Method parameterMethod;

    protected Method operationMethod;

    public AbstractConditionalExpressionBuilder(final Class<?> entityClass,
            final String propertyName, final String parameterName,
            final Method parameterMethod, final Method operationMethod) {
        this(JpqlUtil.toDefaultIdentificationVariable(entityClass),
                propertyName, parameterName, parameterMethod, operationMethod);
    }

    public AbstractConditionalExpressionBuilder(
            final String identificationVariable, final String propertyPath,
            final String parameterName, final Method parameterMethod,
            final Method operationMethod) {
        this.identificationVariable = identificationVariable;
        this.propertyPath = propertyPath;
        this.parameterName = parameterName;
        this.parameterMethod = parameterMethod;
        this.operationMethod = operationMethod;

        final int pos1 = propertyPath.lastIndexOf('.');
        if (pos1 == -1) {
            this.propertyName = identificationVariable + "." + propertyPath;
        } else {
            final int pos2 = propertyPath.lastIndexOf('.', pos1 - 1);
            if (pos2 == -1) {
                this.propertyName = propertyPath;
            } else {
                this.propertyName = propertyPath.substring(pos2 + 1);
            }
        }
    }

    public String getPropertyPath() {
        return propertyPath;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getParameterName() {
        return parameterName;
    }

    public Method getParameterMethod() {
        return parameterMethod;
    }

    public Method getOperationMethod() {
        return operationMethod;
    }

}
