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

import org.seasar.extension.jdbc.ResultSetFactory;
import org.seasar.extension.jdbc.ResultSetHandler;
import org.seasar.extension.jdbc.StatementFactory;
import org.seasar.extension.jdbc.impl.BasicSelectHandler;
import org.seasar.extension.jdbc.impl.BeanListResultSetHandler;
import org.seasar.extension.jdbc.impl.BeanResultSetHandler;
import org.seasar.extension.sql.Node;
import org.seasar.extension.sql.SqlContext;
import org.seasar.extension.sql.context.SqlContextImpl;
import org.seasar.extension.sql.parser.SqlParserImpl;
import org.seasar.framework.jpa.Dialect;
import org.seasar.framework.jpa.DialectManager;

/**
 * 
 * @author higa
 */
public class SqlCommand extends AbstractCommand {

    protected final boolean resultList;

    protected final Class beanClass;

    protected final String sql;

    protected final Node node;

    protected final String[] parameterNames;

    protected final Class[] parameterTypes;

    protected final DialectManager dialectManager;

    protected final ResultSetFactory resultSetFactory;

    protected final StatementFactory statementFactory;

    public SqlCommand(final boolean resultList, final Class beanClass,
            final String sql, final String[] parameterNames,
            final Class[] parameterTypes, final DialectManager dialectManager,
            final ResultSetFactory resultSetFactory,
            final StatementFactory statementFactory) {
        this.resultList = resultList;
        this.beanClass = beanClass;
        this.sql = sql;
        this.node = new SqlParserImpl(sql).parse();
        this.parameterNames = parameterNames;
        this.parameterTypes = parameterTypes;
        this.dialectManager = dialectManager;
        this.resultSetFactory = resultSetFactory;
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
        final ResultSetHandler rsHandler = resultList ? new BeanListResultSetHandler(
                beanClass)
                : new BeanResultSetHandler(beanClass);
        final BasicSelectHandler handler = new BasicSelectHandler();
        handler.setResultSetHandler(rsHandler);
        if (resultSetFactory != null) {
            handler.setResultSetFactory(resultSetFactory);
        }
        if (statementFactory != null) {
            handler.setStatementFactory(statementFactory);
        }
        handler.setSql(query);
        final Dialect dialect = dialectManager.getDialect(em);
        return handler.execute(dialect.getConnection(em), args, argTypes);
    }
}