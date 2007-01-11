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
package org.seasar.kuina.dao.internal.metadata;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import javax.persistence.EntityManager;

import org.seasar.extension.dao.helper.DaoHelper;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;
import org.seasar.framework.jpa.EntityManagerProvider;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.CommandBuilder;
import org.seasar.kuina.dao.internal.DaoMetadata;

/**
 * 
 * @author koichik
 */
@Component(instance = InstanceType.PROTOTYPE)
public class DaoMetadataImpl implements DaoMetadata {

    protected static final Logger logger = Logger
            .getLogger(DaoMetadataImpl.class);

    @Binding(bindingType = BindingType.MUST)
    protected S2Container container;

    @Binding(bindingType = BindingType.MUST)
    protected DaoHelper daoHelper;

    @Binding(bindingType = BindingType.MUST)
    protected EntityManagerProvider entityManagerProvider;

    @Binding(bindingType = BindingType.MUST)
    protected CommandBuilder[] builders;

    protected EntityManager entityManager;

    protected Map<Method, Command> commands = CollectionsUtil.newHashMap();

    public void initialize(final Class<?> daoClass) {
        final String dsName = daoHelper.getDataSourceName(daoClass);
        entityManager = entityManagerProvider.getEntityManger(dsName);

        for (final Method method : daoClass.getMethods()) {
            if (!Modifier.isAbstract(method.getModifiers())
                    || method.isBridge()) {
                continue;
            }
            final Command command = createCommand(daoClass, method);
            commands.put(method, command);
        }
    }

    public Command getCommand(final Method method) {
        return commands.get(method);
    }

    public Object execute(final Method method, final Object[] arguments) {
        final Command command = commands.get(method);
        if (command == null) {
            return NOT_INVOKED;
        }

        return command.execute(entityManager, arguments);
    }

    protected Command createCommand(final Class<?> daoClass, final Method method) {
        for (final CommandBuilder builder : builders) {
            final Command command = builder.build(daoClass, method);
            if (command != null) {
                if (logger.isDebugEnabled()) {
                    logger.log("DKuinaDao3001", new Object[] { daoClass,
                            method, command.getClass().getName() });
                }
                return command;
            }
        }
        logger.log("WKuinaDao3001", new Object[] { daoClass, method });
        return null;
    }

}
