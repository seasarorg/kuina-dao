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

import java.util.concurrent.ConcurrentMap;

import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.internal.EntityDesc;

/**
 * 
 * @author koichik
 */
public class EntityDescFactory {
    protected static ConcurrentMap<Class<?>, EntityDesc> entityDescs = CollectionsUtil
            .newConcurrentHashMap();

    public static EntityDesc getEntityDesc(final Class<?> clazz) {
        final EntityDesc entityDesc = entityDescs.get(clazz);
        if (entityDesc != null) {
            return entityDesc;
        }
        return createEntityDesc(clazz);
    }

    protected static EntityDesc createEntityDesc(final Class<?> clazz) {
        final EntityDesc entityDesc = new EntityDescImpl(clazz);
        final EntityDesc old = entityDescs.putIfAbsent(clazz, entityDesc);
        return old != null ? old : entityDesc;
    }
}
