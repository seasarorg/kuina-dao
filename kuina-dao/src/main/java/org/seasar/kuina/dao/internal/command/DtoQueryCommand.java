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
package org.seasar.kuina.dao.internal.command;

import java.lang.reflect.Method;
import java.util.List;

import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.ConditionalExpressionBuilder;

/**
 * Dtoのプロパティを検索条件として問い合わせを実行する{@link Command}です．
 * 
 * @author koichik
 */
public class DtoQueryCommand extends AbstractDynamicQueryCommand {

    // instance fields
    /** Dtoのプロパティのgetterメソッドの配列 */
    protected Method[] getterMethods;

    /** 検索条件を構築する{@link ConditionalExpressionBuilder}の配列 */
    protected ConditionalExpressionBuilder[] builders;

    /**
     * インスタンスを構築します。
     * 
     * @param entityClass
     *            問い合わせ対象のエンティティ・クラス
     * @param method
     *            Daoメソッド
     * @param resultList
     *            問い合わせ結果を{@link List}で返す場合に<code>true</code>
     * @param getterMethods
     *            Dtoのプロパティのgetterメソッドの配列
     * @param builders
     *            検索条件を構築する{@link ConditionalExpressionBuilder}の配列
     */
    public DtoQueryCommand(final Class<?> entityClass, final Method method,
            final boolean resultList, final Method[] getterMethods,
            final ConditionalExpressionBuilder[] builders) {
        super(entityClass, method, resultList);
        this.getterMethods = getterMethods;
        this.builders = builders;
    }

    @Override
    protected List<String> bindParameter(final SelectStatement statement,
            final Object[] arguments) {
        final List<String> boundProperties = CollectionsUtil.newArrayList();
        final Object dto = arguments[0];
        for (int i = 0; i < getterMethods.length; ++i) {
            final Object value = ReflectionUtil.invoke(getterMethods[i], dto);
            final String boundProperty = builders[i].appendCondition(statement,
                    value);
            if (boundProperty != null) {
                boundProperties.add(boundProperty);
            }
        }
        return boundProperties;
    }

}
