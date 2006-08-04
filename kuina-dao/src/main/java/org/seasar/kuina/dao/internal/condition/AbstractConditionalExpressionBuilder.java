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

/**
 * 
 * @author koichik
 */
public abstract class AbstractConditionalExpressionBuilder implements
        ConditionalExpressionBuilder {

    protected String name;

    protected Method parameterMethod;

    protected Method operationMethod;

    public AbstractConditionalExpressionBuilder(final String name,
            final Method parameterMethod, final Method operationMethod) {
        this.name = name;
        this.parameterMethod = parameterMethod;
        this.operationMethod = operationMethod;
    }

    public String getName() {
        return name;
    }

    public Method getParameterMethod() {
        return parameterMethod;
    }

    public Method getOperationMethod() {
        return operationMethod;
    }

}
