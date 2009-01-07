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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.ConditionalExpressionBuilder;
import org.seasar.kuina.dao.internal.command.ParameterQueryCommand;

/**
 * {@link ParameterQueryCommand}を作成するビルダです．
 * 
 * @author koichik
 */
public class ParameterQueryCommandBuilder extends
        AbstractDynamicQueryCommandBuilder {

    /** パラメータとして受け入れ可能な型の配列 */
    protected static final Class<?>[] ACCEPTABLE_TYPES = new Class<?>[] {
        Number.class, String.class, Boolean.class, Date.class, Calendar.class, Enum.class,
    };

    /**
     * インスタンスを構築します。
     */
    public ParameterQueryCommandBuilder() {
    }

    @Override
    public Command build(final Class<?> daoClass, final Method method,
            final Class<?> entityClass) {
        final String[] parameterNames = getParameterNames(daoClass, method);
        if (parameterNames == null) {
            return null;
        }

        final Class<?>[] parameterTypes = getActualParameterClasses(daoClass, method);
        for (final Class<?> parameterType : parameterTypes) {
            if (!isAcceptableType(ClassUtil
                    .getWrapperClassIfPrimitive(parameterType))) {
                return null;
            }
        }

        final ConditionalExpressionBuilder[] builders = createBuilders(
                entityClass, method, parameterTypes, parameterNames);
        return new ParameterQueryCommand(entityClass, method, isResultList(method),
                parameterNames, builders);
    }

    /**
     * Daoメソッドの引数型がJPQLのパラメータとして受け入れ可能なら<code>true</code>を返します．
     * 
     * @param parameterType
     *            Daoメソッドの引数の型
     * @return Daoメソッドの引数型がJPQLのパラメータとして受け入れ可能なら<code>true</code>
     */
    protected boolean isAcceptableType(final Class<?> parameterType) {
        if (parameterType.isArray()) {
            return isAcceptableType(parameterType.getComponentType());
        }
        for (final Class<?> acceptableType : ACCEPTABLE_TYPES) {
            if (acceptableType.isAssignableFrom(parameterType)) {
                return true;
            }
        }
        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(parameterType);
        return entityDesc != null;
    }

    /**
     * 問い合わせ条件を作成する{@link ConditionalExpressionBuilder}の配列を作成して返します．
     * 
     * @param entityClass
     *            エンティティクラス
     * @param method
     *            Daoメソッド
     * @param parameterTypes
     *            Daoメソッドの型
     * @param parameterNames
     *            Daoメソッドの引数
     * @return 問い合わせ条件を作成する{@link ConditionalExpressionBuilder}の配列
     */
    protected ConditionalExpressionBuilder[] createBuilders(
            final Class<?> entityClass, final Method method,
            final Class<?>[] parameterTypes, final String[] parameterNames) {
        final Annotation[][] parameterAnnotations = method
                .getParameterAnnotations();
        final List<ConditionalExpressionBuilder> builders = CollectionsUtil
                .newArrayList();
        for (int i = 0; i < parameterTypes.length; ++i) {
            builders.add(createBuilder(entityClass, parameterNames[i],
                    parameterTypes[i], parameterAnnotations[i]));
        }
        return builders.toArray(new ConditionalExpressionBuilder[builders
                .size()]);
    }

}
