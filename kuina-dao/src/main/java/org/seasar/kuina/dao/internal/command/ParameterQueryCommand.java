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
package org.seasar.kuina.dao.internal.command;

import java.lang.reflect.Method;
import java.util.List;

import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.ConditionalExpressionBuilder;

/**
 * Daoメソッドの引数を検索条件として問い合わせを実行する{@link Command}です．
 * 
 * @author koichik
 */
public class ParameterQueryCommand extends AbstractDynamicQueryCommand {

    // instance fields
    /** 引数名の配列 */
    protected String[] parameterNames;

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
     * @param parameterNames
     *            引数名
     * @param builders
     *            検索条件を構築する{@link ConditionalExpressionBuilder}の配列
     */
    public ParameterQueryCommand(final Class<?> entityClass,
            final Method method, final boolean resultList,
            final String[] parameterNames,
            final ConditionalExpressionBuilder[] builders) {
        super(entityClass, method, resultList);
        this.parameterNames = parameterNames;
        this.builders = builders;
    }

    @Override
    protected List<String> bindParameter(final SelectStatement statement,
            final Object[] arguments) {
        final List<String> boundProperties = CollectionsUtil.newArrayList();
        for (int i = 0; i < arguments.length; ++i) {
            final String boundProperty = builders[i].appendCondition(statement,
                    arguments[i]);
            if (boundProperty != null) {
                boundProperties.add(boundProperty);
            }
        }
        return boundProperties;
    }

}
