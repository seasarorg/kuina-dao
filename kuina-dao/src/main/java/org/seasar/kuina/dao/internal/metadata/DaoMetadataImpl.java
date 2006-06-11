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
package org.seasar.kuina.dao.internal.metadata;

import java.lang.reflect.Method;
import java.util.Map;

import javax.persistence.EntityManager;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.MethodNotFoundRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.DaoMetadata;

/**
 * 
 * @author koichik
 */
public class DaoMetadataImpl implements DaoMetadata {
    protected EntityManager em;

    protected Class<?> daoClass;

    protected BeanDesc beanDesc;

    protected Map<String, Command> commands = CollectionsUtil.newHashMap();

    /**
     * インスタンスを構築します。
     */
    public DaoMetadataImpl(final DaoMetadataFactoryImpl factory,
            final Class<?> daoClass) {
        this.em = factory.getEntityManager();
        this.daoClass = daoClass;
        this.beanDesc = BeanDescFactory.getBeanDesc(daoClass);
        setupCommands(factory);
    }

    /**
     * @see org.seasar.kuina.dao.internal.DaoMetadata#getCommand(java.lang.reflect.Method)
     */
    public Command getCommand(final Method method) {
        final String methodName = method.getName();
        final Command command = commands.get(methodName);
        if (command == null) {
            throw new MethodNotFoundRuntimeException(daoClass,
                    method.getName(), method.getParameterTypes());
        }
        return command;
    }

    protected void setupCommands(final DaoMetadataFactoryImpl factory) {
        for (final String methodName : beanDesc.getMethodNames()) {
            for (final Method method : beanDesc.getMethods(methodName)) {
                if (method.isBridge()) {
                    continue;
                }
                final Command command = factory.createCommand(daoClass, method);
                assert command != null;
                commands.put(methodName, command);
            }
        }
    }
}
