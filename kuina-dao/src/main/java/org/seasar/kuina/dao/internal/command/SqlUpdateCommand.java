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

import javax.persistence.EntityManager;

import org.seasar.extension.jdbc.StatementFactory;
import org.seasar.extension.jdbc.impl.BasicUpdateHandler;
import org.seasar.framework.jpa.Dialect;
import org.seasar.framework.jpa.DialectManager;

/**
 * 
 * @author koichik
 */
public class SqlUpdateCommand extends AbstractSqlCommand {

    public SqlUpdateCommand(final Method method, final String sql,
            final String[] parameterNames, final Class[] parameterTypes,
            final DialectManager dialectManager,
            final StatementFactory statementFactory) {
        super(method, sql, parameterNames, parameterTypes, dialectManager,
                statementFactory);
    }

    @Override
    protected Object execute(final EntityManager em, final String query,
            final Object[] args, final Class<?>[] argTypes) {
        final BasicUpdateHandler handler = new BasicUpdateHandler();
        if (statementFactory != null) {
            handler.setStatementFactory(statementFactory);
        }
        handler.setSql(query);
        final Dialect dialect = dialectManager.getDialect(em);
        return handler.execute(dialect.getConnection(em), args, argTypes);
    }

}
