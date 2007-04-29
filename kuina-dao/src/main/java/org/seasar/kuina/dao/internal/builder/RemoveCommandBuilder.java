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
package org.seasar.kuina.dao.internal.builder;

import java.lang.reflect.Method;

import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.command.RemoveCommand;

/**
 * {@link RemoveCommand}を作成するビルダです．
 * 
 * @author koichik
 */
public class RemoveCommandBuilder extends AbstractCommandBuilder {

    /**
     * インスタンスを構築します。
     */
    public RemoveCommandBuilder() {
        setMethodNamePattern("remove");
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

        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(parameterTypes[0]);
        if (entityDesc == null) {
            return null;
        }

        return new RemoveCommand();
    }

}
