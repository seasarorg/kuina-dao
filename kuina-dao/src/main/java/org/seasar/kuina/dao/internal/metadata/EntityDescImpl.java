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

import javax.persistence.Entity;

import org.seasar.framework.util.StringUtil;
import org.seasar.kuina.dao.internal.EntityDesc;

/**
 * 
 * @author koichik
 */
public class EntityDescImpl implements EntityDesc {

    protected Class<?> entityClass;

    protected boolean entity;

    protected String name;

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

    protected void setup() {
        final Entity annotation = entityClass.getAnnotation(Entity.class);
        if (annotation == null) {
            return;
        }
        this.entity = true;

        final String name = annotation.name();
        this.name = StringUtil.isEmpty(name) ? getDefaultName() : name;
    }

    protected String getDefaultName() {
        final String className = entityClass.getName();
        return className.substring(className.lastIndexOf('.') + 1);
    }
}
