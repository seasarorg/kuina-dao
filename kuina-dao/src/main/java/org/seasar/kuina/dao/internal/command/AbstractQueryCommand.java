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

import java.lang.reflect.Method;

import javax.persistence.FlushModeType;
import javax.persistence.Query;

import org.seasar.kuina.dao.FlushMode;
import org.seasar.kuina.dao.criteria.SelectStatement;

/**
 * 
 * @author koichik
 */
public abstract class AbstractQueryCommand extends AbstractCommand {

    protected Class<?> entityClass;

    protected Method method;

    protected final boolean resultList;

    protected FlushModeType flushMode;

    /**
     * インスタンスを構築します。
     */
    public AbstractQueryCommand(final Class<?> entityClass,
            final Method method, final boolean resultList) {
        this.entityClass = entityClass;
        this.method = method;
        this.resultList = resultList;
        flushMode = detectFlushMode(method);
    }

    protected FlushModeType detectFlushMode(final Method method) {
        final FlushMode flushMode = method.getAnnotation(FlushMode.class);
        return flushMode == null ? null : flushMode.value();
    }

    protected void setupQuery(final Query query) {
        if (flushMode != null) {
            query.setFlushMode(flushMode);
        }
    }

    protected void setupStatement(final SelectStatement statement) {
        if (flushMode != null) {
            statement.setFlushMode(flushMode);
        }
    }

}
