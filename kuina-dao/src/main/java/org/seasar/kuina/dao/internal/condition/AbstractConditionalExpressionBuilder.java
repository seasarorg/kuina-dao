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

import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.kuina.dao.internal.util.JpqlUtil;
import org.seasar.kuina.dao.internal.util.KuinaDaoUtil;

/**
 * 
 * @author koichik
 */
public abstract class AbstractConditionalExpressionBuilder implements
        ConditionalExpressionBuilder {

    protected Class<?> entityClass;

    protected String propertyPath;

    protected String propertyName;

    protected String parameterName;

    protected Method parameterMethod;

    protected Method operationMethod;

    public AbstractConditionalExpressionBuilder(final Class<?> entityClass,
            final String propertyPath, final String parameterName,
            final Method parameterMethod, final Method operationMethod) {
        this.entityClass = entityClass;
        this.propertyPath = propertyPath;
        this.parameterName = parameterName;
        this.parameterMethod = parameterMethod;
        this.operationMethod = operationMethod;
        this.propertyName = createPropertyName();
    }

    protected String createPropertyName() {
        final String identificationVariable = JpqlUtil
                .toDefaultIdentificationVariable(entityClass);
        final int pos1 = propertyPath.indexOf('.');
        if (pos1 == -1) {
            return identificationVariable + "." + propertyPath;
        }

        final String firstPropertyName = propertyPath.substring(0, pos1);
        final EntityDesc entityDesc = KuinaDaoUtil.getEntityDesc(entityClass);
        final int pos2 = propertyPath.indexOf('.', pos1 + 1);
        if (pos2 == -1) {
            return KuinaDaoUtil.isAssociation(entityDesc, firstPropertyName) ? propertyPath
                    : identificationVariable + "." + propertyPath;
        }

        final String secondPropertyName = propertyPath
                .substring(pos1 + 1, pos2);
        final EntityDesc associationEntity = KuinaDaoUtil
                .getAssociationEntityDesc(entityDesc, firstPropertyName);
        return KuinaDaoUtil
                .isAssociation(associationEntity, secondPropertyName) ? propertyPath
                .substring(pos1 + 1)
                : propertyPath;
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
