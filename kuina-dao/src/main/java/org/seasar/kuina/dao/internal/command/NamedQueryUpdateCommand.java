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
package org.seasar.kuina.dao.internal.command;

import java.lang.reflect.Method;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.binder.ParameterBinder;

/**
 * Named Query (更新・削除) を実行する{@link Command}です．
 * 
 * @author koichik
 */
public class NamedQueryUpdateCommand extends NamedQueryCommand {

    /**
     * インスタンスを構築します。
     * 
     * @param entityClass
     *            エンティティ・クラス
     * @param method
     *            Daoメソッド
     * @param queryName
     *            Named Query の名前
     * @param binders
     *            パラメータバインダの配列
     */
    public NamedQueryUpdateCommand(final Class<?> entityClass,
            final Method method, final String queryName,
            final ParameterBinder[] binders) {
        super(entityClass, method, false, queryName, binders);
    }

    @Override
    public Object execute(final EntityManager em, final Object[] parameters) {
        final Query query = createQuery(em, parameters);
        setupQuery(query);
        return query.executeUpdate();
    }

}
