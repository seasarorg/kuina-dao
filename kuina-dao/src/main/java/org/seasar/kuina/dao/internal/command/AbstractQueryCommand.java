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
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.FlushModeType;
import javax.persistence.Query;

import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.internal.Command;

/**
 * 問い合わせを行う{@link Command}に共通の機能を提供する抽象クラスです．
 * 
 * @author koichik
 */
public abstract class AbstractQueryCommand extends AbstractCommand {

    // instance fields
    /** 問い合わせ対象のエンティティ・クラス */
    protected final Class<?> entityClass;

    /** Daoメソッド */
    protected final Method method;

    /** 問い合わせ結果を{@link List}で返す場合に<code>true</code> */
    protected final boolean resultList;

    /** Daoメソッドのフラッシュ・モード */
    protected final FlushModeType flushMode;

    /** Daoメソッドのヒントの{@link Map} */
    protected final Map<String, Object> hints;

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
    public AbstractQueryCommand(final Class<?> entityClass,
            final Method method, final boolean resultList) {
        this.entityClass = entityClass;
        this.method = method;
        this.resultList = resultList;
        flushMode = detectFlushMode(method);
        hints = detectHints(method);
    }

    /**
     * 問い合わせの実行前に{@link Query}の設定を行います．
     * <p>
     * {@link Query}にフラッシュ・モードとヒントを設定します．
     * </p>
     * <p>
     * サブクラスは必要に応じてメソッドをオーバーライドし，{@link Query}に様々な設定を行うことができます．
     * </p>
     * 
     * @param query
     *            {@link Query}
     * @see Query#setFlushMode(FlushModeType)
     * @see Query#setHint(String, Object)
     */
    protected void setupQuery(final Query query) {
        if (flushMode != null) {
            query.setFlushMode(flushMode);
        }
        for (final Entry<String, Object> entry : hints.entrySet()) {
            query.setHint(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 問い合わせの実行前に{@link SelectStatement}の設定を行います．
     * <p>
     * {@link SelectStatement}にフラッシュ・モードとヒントを設定します．
     * </p>
     * <p>
     * サブクラスは必要に応じてメソッドをオーバーライドし，{@link SelectStatement}に様々な設定を行うことができます．
     * </p>
     * 
     * @param statement
     *            問い合わせ文
     */
    protected void setupStatement(final SelectStatement statement) {
        if (flushMode != null) {
            statement.setFlushMode(flushMode);
        }
        for (final Entry<String, Object> entry : hints.entrySet()) {
            statement.addHint(entry.getKey(), entry.getValue());
        }
    }

}
