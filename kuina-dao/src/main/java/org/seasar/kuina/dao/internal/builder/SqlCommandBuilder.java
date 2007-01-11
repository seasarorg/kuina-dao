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
package org.seasar.kuina.dao.internal.builder;

import java.lang.reflect.Method;

import org.seasar.extension.dao.helper.DaoHelper;
import org.seasar.extension.jdbc.ResultSetFactory;
import org.seasar.extension.jdbc.StatementFactory;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.jpa.DialectManager;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.command.SqlCommand;

/**
 * 
 * @author koichik
 */
public class SqlCommandBuilder extends AbstractQueryCommandBuilder {

    protected DialectManager dialectManager;

    protected ResultSetFactory resultSetFactory;

    protected StatementFactory statementFactory;

    public void setDaoHelper(final DaoHelper daoHelper) {
        this.daoHelper = daoHelper;
    }

    public void setDialectManager(final DialectManager dialectManager) {
        this.dialectManager = dialectManager;
    }

    public void setResultSetFactory(final ResultSetFactory resultSetFactory) {
        this.resultSetFactory = resultSetFactory;
    }

    public void setStatementFactory(final StatementFactory statementFactory) {
        this.statementFactory = statementFactory;
    }

    public Command build(final Class<?> daoClass, final Method method) {
        if (!isMatched(method)) {
            return null;
        }

        final String sql = daoHelper.getSqlBySqlFile(daoClass, method, null);
        if (sql == null) {
            return null;
        }
        final Class targetClass = getResultClass(method);
        if (targetClass == null) {
            throw new IllegalStateException(daoClass.getName() + "#"
                    + method.getName() + "()");
        }
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(daoClass);
        return new SqlCommand(isResultList(method), targetClass, sql, beanDesc
                .getMethodParameterNames(method), method.getParameterTypes(),
                dialectManager, resultSetFactory, statementFactory);
    }
}