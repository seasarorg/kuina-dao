/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
import java.util.Collection;

import org.seasar.framework.jpa.metadata.AttributeDesc;
import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;
import org.seasar.framework.util.ClassUtil;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.ConditionalExpressionBuilder;
import org.seasar.kuina.dao.internal.command.FindVersionCommand;
import org.seasar.kuina.dao.internal.condition.NullBuilder;

/**
 * {@link FindVersionCommand}を作成するビルダです．
 * 
 * @author koichik
 */
public class FindVersionCommandBuilder extends
        AbstractDynamicQueryCommandBuilder {

    /**
     * インスタンスを構築します。
     */
    public FindVersionCommandBuilder() {
        setMethodNamePattern("get|find");
    }

    @Override
    public Command build(final Class<?> daoClass, final Method method,
            final Class<?> entityClass) {
        final Class<?>[] parameterClasses = getActualParameterClasses(daoClass,
                method);
        if (parameterClasses.length != 2) {
            return null;
        }

        final Class<?> returnClass = getActualReturnClass(daoClass, method);
        if (returnClass.isPrimitive() || returnClass.isArray()
                || Collection.class.isInstance(returnClass)) {
            return null;
        }

        final String[] parameterNames = getParameterNames(daoClass, method);
        if (parameterNames == null) {
            return null;
        }

        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(returnClass);
        if (entityDesc == null) {
            return null;
        }

        final AttributeDesc idAttributeDesc = entityDesc
                .getAttributeDesc(parameterNames[0]);
        if (idAttributeDesc == null || !idAttributeDesc.isId()) {
            return null;
        }
        if (!equals(idAttributeDesc.getType(), parameterClasses[0])) {
            return null;
        }

        final AttributeDesc versionAttributeDesc = entityDesc
                .getAttributeDesc(parameterNames[1]);
        if (versionAttributeDesc == null || !versionAttributeDesc.isVersion()) {
            return null;
        }
        if (!equals(versionAttributeDesc.getType(), parameterClasses[1])) {
            return null;
        }

        final ConditionalExpressionBuilder[] builders = new ConditionalExpressionBuilder[2];
        builders[0] = createBuilder(entityClass, parameterNames[0],
                parameterClasses[0], method.getParameterAnnotations()[0]);
        builders[1] = new NullBuilder();

        return new FindVersionCommand(entityClass, method, parameterNames,
                builders);
    }

    /**
     * 二つのクラスが同じ場合は<code>true</code>，それ以外なら<code>false</code>を返します．
     * <p>
     * クラスがプリミティブ型の場合はラッパー型に変換して比較します．
     * </p>
     * 
     * @param clazz1
     *            クラス1
     * @param clazz2
     *            クラス2
     * @return 二つのクラスが同じ場合は<code>true</code>，それ以外なら<code>false</code>
     */
    protected boolean equals(final Class<?> clazz1, final Class<?> clazz2) {
        return ClassUtil.getWrapperClassIfPrimitive(clazz1).equals(
                ClassUtil.getWrapperClassIfPrimitive(clazz2));
    }

}
