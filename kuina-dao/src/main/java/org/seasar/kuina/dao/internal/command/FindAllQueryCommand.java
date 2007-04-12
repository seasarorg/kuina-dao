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
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.internal.Command;

/**
 * 全件検索を行う問い合わせを実行する{@link Command}です．
 * 
 * @author koichik
 */
public class FindAllQueryCommand extends AbstractDynamicQueryCommand {

    /**
     * インスタンスを構築します。
     * 
     * @param entityClass
     *            エンティティ・クラス
     * @param method
     *            Daoメソッド
     */
    public FindAllQueryCommand(final Class<?> entityClass, final Method method) {
        super(entityClass, method, true);
    }

    @Override
    protected List<String> bindParameter(final SelectStatement statement,
            final Object[] arguments) {
        return CollectionsUtil.newArrayList();
    }

}
