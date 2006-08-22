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

import org.seasar.extension.jdbc.ResultSetHandler;
import org.seasar.extension.jdbc.impl.BasicSelectHandler;
import org.seasar.extension.jdbc.impl.BeanListResultSetHandler;
import org.seasar.extension.jdbc.impl.BeanResultSetHandler;
import org.seasar.extension.sql.Node;
import org.seasar.extension.sql.SqlContext;
import org.seasar.extension.sql.context.SqlContextImpl;
import org.seasar.extension.sql.parser.SqlParserImpl;
import org.seasar.framework.jpa.Dialect;

/**
 * 
 * @author higa
 */
public class SqlCommand extends AbstractCommand {

    protected final Class beanClass;

    protected final boolean resultList;

    protected final String sql;

    protected final Node node;

    protected final String[] parameterNames;

    protected final Class[] parameterTypes;

    protected final Dialect dialect;

    public SqlCommand(final Class beanClass, final boolean resultList,
            final String sql, final String[] parameterNames,
            final Class[] parameterTypes, final Dialect dialect) {
        this.beanClass = beanClass;
        this.resultList = resultList;
        this.sql = sql;
        this.node = new SqlParserImpl(sql).parse();
        this.parameterNames = parameterNames;
        this.parameterTypes = parameterTypes;
        this.dialect = dialect;
    }

    public Object execute(EntityManager em, Object[] parameters) {
        String query = sql;
        Object[] args = parameters;
        Class[] argTypes = null;
        if (parameterNames != null) {
            SqlContext sqlContext = new SqlContextImpl();
            for (int i = 0; i < parameterNames.length; ++i) {
                String name = parameterNames[i];
                Class type = parameterTypes[i];
                Object value = parameters[i];
                sqlContext.addArg(name, value, type);
            }
            node.accept(sqlContext);
            query = sqlContext.getSql();
            args = sqlContext.getBindVariables();
            argTypes = sqlContext.getBindVariableTypes();
        }
        ResultSetHandler rsHandler = resultList ? new BeanListResultSetHandler(
                beanClass) : new BeanResultSetHandler(beanClass);
        BasicSelectHandler handler = new BasicSelectHandler();
        handler.setResultSetHandler(rsHandler);
        handler.setSql(query);
        return handler.execute(dialect.getConnection(em), args, argTypes);
    }
}