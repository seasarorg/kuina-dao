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
package org.seasar.kuina.dao.internal.builder;

import java.lang.reflect.Method;
import java.util.Collection;

import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.command.FindCommand;

/**
 * {@link FindCommand}を作成するビルダです．
 * 
 * @author koichik
 */
public class FindCommandBuilder extends AbstractCommandBuilder {

    /**
     * インスタンスを構築します。
     */
    public FindCommandBuilder() {
        setMethodNamePattern("get|find");
    }

    public Command build(final Class<?> daoClass, final Method method) {
        if (!isMatched(method)) {
            return null;
        }

        final Class<?>[] parameterTypes = getActualParameterClasses(daoClass,
                method);
        if (parameterTypes.length != 1) {
            return null;
        }

        final Class<?> returnClass = getActualReturnClass(daoClass, method);
        if (returnClass.isPrimitive() || returnClass.isArray()
                || Collection.class.isInstance(returnClass)) {
            return null;
        }

        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(returnClass);
        if (entityDesc == null) {
            return null;
        }

        return new FindCommand(returnClass);
    }

}
