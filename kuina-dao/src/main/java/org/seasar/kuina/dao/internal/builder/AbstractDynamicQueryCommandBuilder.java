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
import java.util.ArrayList;
import java.util.List;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.ConditionalExpressionBuilder;
import org.seasar.kuina.dao.internal.condition.ConditionalExpressionBuilderFactory;
import org.seasar.kuina.dao.internal.condition.FirstResultBuilder;
import org.seasar.kuina.dao.internal.condition.MaxResultsBuilder;
import org.seasar.kuina.dao.internal.condition.OrderbyBuilder;

/**
 * 動的にJPQLを生成するコマンドを作成するビルダの抽象クラスです．
 * 
 * @author koichik
 */
public abstract class AbstractDynamicQueryCommandBuilder extends
        AbstractQueryCommandBuilder {

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

        return build(daoClass, method, entityClass);
    }

    /**
     * <code>daoClass</code>の<code>method</code>のオペレーションを提供する{@link Command コマンド}を作成します．
     * 
     * @param daoClass
     *            Daoクラス
     * @param method
     *            Daoのメソッド
     * @param entityClass
     *            問い合わせ対象のエンティティクラス
     * @return <code>daoClass</code>の<code>method</code>のオペレーションを提供する{@link Command コマンド}
     */
    protected abstract Command build(final Class<?> daoClass,
            final Method method, final Class<?> entityClass);

    /**
     * Daoメソッドの引数名の配列を返します．
     * 
     * @param daoClass
     *            Daoクラス
     * @param method
     *            Daoのメソッド
     * @return Daoメソッドの引数名の配列
     */
    protected String[] getParameterNames(final Class<?> daoClass,
            final Method method) {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(daoClass);
        return beanDesc.getMethodParameterNames(method);
    }

    /**
     * エンティティクラスまたはDtoクラスが持つプロパティのgetterメソッドの配列を返します．
     * 
     * @param beanDesc
     *            エンティティクラスまたはDtoクラスの{@link BeanDesc}
     * @return エンティティクラスまたはDtoクラスが持つプロパティのgetterメソッドの配列
     */
    protected Method[] getGetterMethods(final BeanDesc beanDesc) {
        final List<Method> getterMethods = new ArrayList<Method>(beanDesc
                .getPropertyDescSize());
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            final PropertyDesc propertyDesc = beanDesc.getPropertyDesc(i);
            if (propertyDesc.hasReadMethod()) {
                getterMethods.add(propertyDesc.getReadMethod());
            }
        }
        return getterMethods.toArray(new Method[getterMethods.size()]);
    }

    /**
     * Daoメソッドの引数またはDtoのプロパティから問い合わせ条件ビルダを作成して返します．
     * 
     * @param entityClass
     *            エンティティクラス
     * @param parameterName
     *            引数またはプロパティの名前
     * @param parameterType
     *            引数またはプロパティの型
     * @param annotations
     *            引数またはプロパティに付けられたアノテーションの配列
     * @return Daoメソッドの引数またはDtoのプロパティから問い合わせ条件ビルダ
     */
    protected ConditionalExpressionBuilder createBuilder(
            final Class<?> entityClass, final String parameterName,
            final Class<?> parameterType, final Annotation[] annotations) {
        if (isOrderby(parameterName, annotations)) {
            return new OrderbyBuilder(entityClass);
        }
        if (isFirstResult(parameterName, annotations)) {
            return new FirstResultBuilder();
        }
        if (isMaxResults(parameterName, annotations)) {
            return new MaxResultsBuilder();
        }
        return ConditionalExpressionBuilderFactory.createBuilder(entityClass,
                parameterName, parameterType);
    }

}
