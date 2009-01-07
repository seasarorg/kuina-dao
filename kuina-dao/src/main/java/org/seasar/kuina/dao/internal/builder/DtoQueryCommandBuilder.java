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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.ConditionalExpressionBuilder;
import org.seasar.kuina.dao.internal.command.DtoQueryCommand;

/**
 * {@link ConditionalExpressionBuilder}を作成するビルダです．
 * 
 * @author koichik
 */
public class DtoQueryCommandBuilder extends AbstractDynamicQueryCommandBuilder {

    /**
     * インスタンスを構築します。
     */
    public DtoQueryCommandBuilder() {
    }

    @Override
    public Command build(final Class<?> daoClass, final Method method,
            final Class<?> entityClass) {
        final Class<?>[] parameterTypes = getActualParameterClasses(daoClass,
                method);
        if (parameterTypes.length != 1) {
            return null;
        }

        final Class<?> parameterType = parameterTypes[0];
        if (!parameterType.getName().endsWith(convention.getDtoSuffix())) {
            return null;
        }

        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(parameterType);
        final Method[] getterMethods = getGetterMethods(beanDesc);
        final ConditionalExpressionBuilder[] builders = createBuilders(
                entityClass, beanDesc);
        return new DtoQueryCommand(entityClass, method, isResultList(method),
                getterMethods, builders);
    }

    /**
     * 問い合わせ条件を作成する{@link ConditionalExpressionBuilder}の配列を作成して返します．
     * 
     * @param entityClass
     *            エンティティクラス
     * @param beanDesc
     *            エンティティクラスの{@link BeanDesc}
     * @return 問い合わせ条件を作成する{@link ConditionalExpressionBuilder}の配列
     */
    protected ConditionalExpressionBuilder[] createBuilders(
            final Class<?> entityClass, final BeanDesc beanDesc) {
        final List<ConditionalExpressionBuilder> builders = CollectionsUtil
                .newArrayList();
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            final PropertyDesc propertyDesc = beanDesc.getPropertyDesc(i);
            if (propertyDesc.hasReadMethod()) {
                final Annotation[] annotations = propertyDesc.getReadMethod()
                        .getAnnotations();
                builders.add(createBuilder(entityClass, propertyDesc
                        .getPropertyName(), propertyDesc.getPropertyType(),
                        annotations));
            }
        }
        return builders.toArray(new ConditionalExpressionBuilder[builders
                .size()]);
    }

}
