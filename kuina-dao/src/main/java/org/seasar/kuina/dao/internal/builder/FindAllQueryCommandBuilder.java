/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import java.lang.reflect.Method;

import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.command.FindAllQueryCommand;

/**
 * {@link FindAllQueryCommand}を作成するコマンドです．
 * 
 * @author koichik
 */
public class FindAllQueryCommandBuilder extends AbstractQueryCommandBuilder {

    /**
     * インスタンスを構築します。
     */
    public FindAllQueryCommandBuilder() {
        setMethodNamePattern("(get|find)All");
    }

    public Command build(final Class<?> daoClass, final Method method) {
        if (!isMatched(method) || method.getParameterTypes().length != 0) {
            return null;
        }

        final Class<?> entityClass = getResultClass(daoClass, method);
        if (entityClass == null) {
            return null;
        }

        return new FindAllQueryCommand(entityClass, method);
    }

}
