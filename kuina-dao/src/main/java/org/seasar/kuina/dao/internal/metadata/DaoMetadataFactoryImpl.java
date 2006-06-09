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
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.persistence.EntityManager;

import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.CommandBuilder;
import org.seasar.kuina.dao.internal.DaoMetadata;
import org.seasar.kuina.dao.internal.DaoMetadataFactory;

/**
 * 
 * @author koichik
 */
@Component
public class DaoMetadataFactoryImpl implements DaoMetadataFactory {

    @Binding(bindingType = BindingType.MUST)
    protected EntityManager entityManager;

    protected final List<CommandBuilder> builders = CollectionsUtil
            .newArrayList();

    protected final ConcurrentMap<Class<?>, DaoMetadata> metadataCache = CollectionsUtil
            .newConcurrentHashMap();

    /**
     * インスタンスを構築します。
     */
    public DaoMetadataFactoryImpl() {
    }

    public void addCommandBuilder(final CommandBuilder builder) {
        builders.add(builder);
    }

    /**
     * @see org.seasar.kuina.dao.internal.DaoMetadataFactory#getMetadata(java.lang.Class)
     */
    public DaoMetadata getMetadata(final Class<?> daoClass) {
        final DaoMetadata metadata = metadataCache.get(daoClass);
        if (metadata != null) {
            return metadata;
        }
        return createMetadata(daoClass);
    }

    protected DaoMetadata createMetadata(final Class<?> daoClass) {
        final DaoMetadata metadata = new DaoMetadataImpl(this, daoClass);
        final DaoMetadata current = metadataCache.putIfAbsent(daoClass,
                metadata);
        return current != null ? current : metadata;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected Command createCommand(final Class<?> daoClass, final Method method) {
        for (final CommandBuilder builder : builders) {
            final Command command = builder.build(daoClass, method);
            if (command != null) {
                return command;
            }
        }
        return null;
    }
}
