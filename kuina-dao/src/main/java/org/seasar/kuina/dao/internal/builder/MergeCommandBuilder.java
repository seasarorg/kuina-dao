/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
import java.util.regex.Pattern;

import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.CommandBuilder;
import org.seasar.kuina.dao.internal.EntityDesc;
import org.seasar.kuina.dao.internal.command.MergeCommand;
import org.seasar.kuina.dao.internal.metadata.EntityDescFactory;

/**
 * 
 * @author koichik
 */
public class MergeCommandBuilder implements CommandBuilder {
    protected Pattern methodNamePattern = Pattern.compile("merge");

    public MergeCommandBuilder() {
    }

    public void setMethodNamePattern(final String methodNamePattern) {
        this.methodNamePattern = Pattern.compile(methodNamePattern);
    }

    public Command build(final Class<?> daoClass, final Method method) {
        final String methodName = method.getName();
        if (!methodNamePattern.matcher(methodName).matches()) {
            return null;
        }

        final Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1) {
            return null;
        }

        final Class<?> returnType = method.getReturnType();
        if (returnType != parameterTypes[0]) {
            return null;
        }

        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(returnType);
        if (!entityDesc.isEntity()) {
            return null;
        }

        return new MergeCommand();
    }

}
