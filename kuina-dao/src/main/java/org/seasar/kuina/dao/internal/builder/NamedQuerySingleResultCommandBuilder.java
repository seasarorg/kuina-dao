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

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.kuina.dao.annotation.TargetEntity;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.command.NamedQuerySingleResultCommand;

/**
 * 
 * @author koichik
 */
public class NamedQuerySingleResultCommandBuilder extends
        AbstractNamedQueryCommandBuilder {

    public NamedQuerySingleResultCommandBuilder() {
        setMethodNamePattern("get.+");
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
        return new NamedQuerySingleResultCommand(queryName, getBinders(method,
                beanDesc.getMethodParameterNames(method)));
    }

    @Override
    protected Class<?> resolveEntityClass(final Class<?> daoClass,
            final Method method) {
        TargetEntity targetEntity = method.getAnnotation(TargetEntity.class);
        if (targetEntity != null) {
            return targetEntity.value();
        }

        targetEntity = daoClass.getAnnotation(TargetEntity.class);
        if (targetEntity != null) {
            return targetEntity.value();
        }
        return method.getReturnType();
    }
}
