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

import javax.persistence.EntityManager;

import org.seasar.kuina.dao.internal.Command;

/**
 * {@link EntityManager#find(Class, Object)}を実行する{@link Command}です．
 * 
 * @author koichik
 */
public class FindCommand extends AbstractCommand {

    // instance fileds
    /** エンティティ・クラス */
    final Class<?> entityClass;

    /**
     * インスタンスを構築します。
     * 
     * @param entityClass
     *            エンティティ・クラス
     */
    public FindCommand(final Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public Object execute(final EntityManager em, final Object[] arguments) {
        assert arguments != null;
        assert arguments.length == 1;

        final Object primaryKey = arguments[0];
        return em.find(entityClass, primaryKey);
    }

}
