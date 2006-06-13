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

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.seasar.framework.util.StringUtil;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.internal.EntityDesc;

/**
 * 
 * @author koichik
 */
public class EntityDescImpl implements EntityDesc {

    protected Class<?> entityClass;

    protected boolean entity;

    protected String name;

    protected Map<String, NamedQuery> namedQueries = CollectionsUtil
            .newHashMap();

    public EntityDescImpl(final Class<?> entityClass) {
        this.entityClass = entityClass;
        setup();
    }

    public boolean isEntity() {
        return entity;
    }

    public String getName() {
        return name;
    }

    public NamedQuery getNamedQuery(final String name) {
        return namedQueries.get(name);
    }

    protected void setup() {
        final Entity annotation = entityClass.getAnnotation(Entity.class);
        if (annotation == null) {
            return;
        }
        this.entity = true;

        final String name = annotation.name();
        this.name = StringUtil.isEmpty(name) ? getDefaultName() : name;

        setupQueryNames();
    }

    protected String getDefaultName() {
        final String className = entityClass.getName();
        return className.substring(className.lastIndexOf('.') + 1);
    }

    protected void setupQueryNames() {
        Class<?> clazz = entityClass;
        while (clazz != null) {
            final NamedQueries namedQueries = clazz
                    .getAnnotation(NamedQueries.class);
            if (namedQueries != null) {
                for (final NamedQuery namedQuery : namedQueries.value()) {
                    addNamedQuery(namedQuery);
                }
            }
            final NamedQuery namedQuery = clazz.getAnnotation(NamedQuery.class);
            if (namedQuery != null) {
                addNamedQuery(namedQuery);
            }
            clazz = clazz.getSuperclass();
        }
    }

    protected void addNamedQuery(final NamedQuery namedQuery) {
        final String name = namedQuery.name();
        if (!namedQueries.containsKey(name)) {
            namedQueries.put(name, namedQuery);
        }
    }
}
