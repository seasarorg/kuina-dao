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
import org.seasar.kuina.dao.criteria.grammar.ConditionalExpression;
import org.seasar.kuina.dao.internal.Command;

/**
 * Daoメソッドの引数で指定された{@link ConditionalExpression}を検索条件として問い合わせを行う{@link Command}です．
 * 
 * @author koichik
 */
public class ConditionalQueryCommand extends AbstractDynamicQueryCommand {

    // constants
    /** 空の{@link List} */
    protected static final List<String> EMPTY_LIST = CollectionsUtil
            .newArrayList();

    /**
     * インスタンスを構築します。
     * 
     * @param entityClass
     *            問い合わせ対象のエンティティ・クラス
     * @param method
     *            Daoメソッド
     * @param resultList
     *            問い合わせ結果を{@link List}で返す場合に<code>true</code>
     */
    public ConditionalQueryCommand(final Class<?> entityClass,
            final Method method, final boolean resultList) {
        super(entityClass, method, resultList);
    }

    @Override
    protected List<String> bindParameter(final SelectStatement statement,
            final Object[] arguments) {
        for (final ConditionalExpression condition : ConditionalExpression[].class
                .cast(arguments[0])) {
            statement.where(condition);
        }
        return EMPTY_LIST;
    }

}
