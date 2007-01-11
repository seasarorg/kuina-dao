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
package org.seasar.kuina.dao.internal.command;

import javax.persistence.EntityManager;

/**
 * 
 * @author koichik
 */
public class PersistCommand extends AbstractCommand {
    protected Class<?> entityType;

    /**
     * インスタンスを構築します。
     */
    public PersistCommand(final Class<?> entityType) {
        this.entityType = entityType;
    }

    /**
     * @see org.seasar.kuina.dao.internal.Command#execute()
     */
    public Object execute(final EntityManager em, final Object[] arguments) {
        assert arguments != null;
        assert arguments.length == 1;
        assert entityType.isAssignableFrom(arguments[0].getClass());

        final Object entity = arguments[0];
        em.persist(entity);
        return null;
    }
}
