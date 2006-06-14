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
package org.seasar.kuina.dao.internal.command;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.seasar.kuina.dao.internal.binder.ParameterBinder;

/**
 * 
 * @author koichik
 */
public class NamedQueryCommand extends AbstractCommand {

    protected final boolean resultList;

    protected final String queryName;

    protected final ParameterBinder[] binders;

    public NamedQueryCommand(final boolean resultList, final String queryName,
            final ParameterBinder[] binders) {
        this.resultList = resultList;
        this.queryName = queryName;
        this.binders = binders;
    }

    /**
     * @see org.seasar.kuina.dao.internal.Command#execute(javax.persistence.EntityManager,
     *      java.lang.Object[])
     */
    public Object execute(EntityManager em, Object[] parameters) {
        final Query query = createQuery(em, parameters);
        return resultList ? query.getResultList() : query.getSingleResult();
    }

    /**
     * @see org.seasar.kuina.dao.internal.Command#execute(javax.persistence.EntityManager,
     *      java.lang.Object[])
     */
    protected Query createQuery(final EntityManager em, final Object[] arguments) {
        assert binders.length == arguments.length;

        final Query query = em.createNamedQuery(queryName);
        for (int i = 0; i < binders.length; ++i) {
            final ParameterBinder binder = binders[i];
            binder.bind(query, arguments[i]);
        }
        return query;
    }
}
