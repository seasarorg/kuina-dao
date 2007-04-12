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

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.binder.ParameterBinder;

/**
 * Named Query (問い合わせ) を実行する{@link Command}です．
 * 
 * @author koichik
 */
public class NamedQueryCommand extends AbstractQueryCommand {

    // instance fields
    /** Named Query の名前 */
    protected final String queryName;

    /** パラメータバインダの配列 */
    protected final ParameterBinder[] binders;

    /**
     * インスタンスを構築します。
     * 
     * @param entityClass
     *            エンティティ・クラス
     * @param method
     *            Daoメソッド
     * @param resultList
     *            問い合わせ結果を{@link List}で返す場合に<code>true</code>
     * @param queryName
     *            Named Query の名前
     * @param binders
     *            パラメータバインダの配列
     */
    public NamedQueryCommand(final Class<?> entityClass, final Method method,
            final boolean resultList, final String queryName,
            final ParameterBinder[] binders) {
        super(entityClass, method, resultList);
        this.queryName = queryName;
        this.binders = binders;
    }

    public Object execute(final EntityManager em, final Object[] parameters) {
        final Query query = createQuery(em, parameters);
        setupQuery(query);
        return resultList ? query.getResultList() : query.getSingleResult();
    }

    /**
     * {@link Query}を作成して返します．
     * 
     * @param em
     *            エンティティ・マネージャ
     * @param arguments
     *            Daoメソッドの引数
     * @return {@link Query}
     */
    protected Query createQuery(final EntityManager em, final Object[] arguments) {
        final Query query = em.createNamedQuery(queryName);
        for (int i = 0; i < binders.length; ++i) {
            final ParameterBinder binder = binders[i];
            binder.bind(query, arguments[i]);
        }
        return query;
    }

}
