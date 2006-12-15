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

import org.seasar.extension.jdbc.StatementFactory;
import org.seasar.extension.sql.Node;
import org.seasar.extension.sql.SqlContext;
import org.seasar.extension.sql.context.SqlContextImpl;
import org.seasar.extension.sql.parser.SqlParserImpl;
import org.seasar.framework.jpa.DialectManager;

/**
 * 
 * @author higa
 */
public abstract class AbstractSqlCommand extends AbstractCommand {

    protected final String sql;

    protected final Node node;

    protected final String[] parameterNames;

    protected final Class[] parameterTypes;

    protected final DialectManager dialectManager;

    protected final StatementFactory statementFactory;

    public AbstractSqlCommand(final String sql, final String[] parameterNames,
            final Class[] parameterTypes, final DialectManager dialectManager,
            final StatementFactory statementFactory) {
        this.sql = sql;
        node = new SqlParserImpl(sql).parse();
        this.parameterNames = parameterNames;
        this.parameterTypes = parameterTypes;
        this.dialectManager = dialectManager;
        this.statementFactory = statementFactory;
    }

    public Object execute(final EntityManager em, final Object[] parameters) {
        String query = sql;
        Object[] args = parameters;
        Class[] argTypes = null;
        if (parameterNames != null) {
            final SqlContext sqlContext = new SqlContextImpl();
            for (int i = 0; i < parameterNames.length; ++i) {
                final String name = parameterNames[i];
                final Class type = parameterTypes[i];
                final Object value = parameters[i];
                sqlContext.addArg(name, value, type);
            }
            node.accept(sqlContext);
            query = sqlContext.getSql();
            args = sqlContext.getBindVariables();
            argTypes = sqlContext.getBindVariableTypes();
        }

        return execute(em, query, args, argTypes);
    }

    protected abstract Object execute(final EntityManager em,
            final String query, final Object[] args, Class<?>[] argTypes);

}
