/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.command.ExampleQueryCommand;

/**
 * {@link ExampleQueryCommand}を作成するビルダです．
 * 
 * @author koichik
 */
public class ExampleQueryCommandBuilder extends AbstractQueryCommandBuilder {

    /**
     * インスタンスを構築します。
     */
    public ExampleQueryCommandBuilder() {
    }

    public Command build(final Class<?> daoClass, final Method method) {
        if (!isMatched(method)) {
            return null;
        }

        final Class<?> entityClass = getResultClass(daoClass, method);
        if (entityClass == null) {
            return null;
        }

        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(entityClass);
        if (entityDesc == null) {
            return null;
        }

        final Class<?>[] parameterTypes = getActualParameterClasses(daoClass,
                method);
        final int parameterSize = parameterTypes.length;
        if (parameterSize == 0 || parameterTypes[0] != entityClass) {
            return null;
        }

        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(daoClass);
        final String[] parameterNames = beanDesc
                .getMethodParameterNames(method);
        final Annotation[][] annotations = method.getParameterAnnotations();

        return new ExampleQueryCommand(entityClass, method,
                isResultList(method), getOrderbyParameter(parameterNames,
                        annotations), getFirstResultParameter(parameterNames,
                        annotations), getMaxResultsParameter(parameterNames,
                        annotations));
    }

}
