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
 * {@link SqlCommand}を作成するビルダです．
 * 
 * @author higa
 * @author koichik
 */
public class SqlCommandBuilder extends AbstractQueryCommandBuilder {

    // instance fields
    /** Dialectマネージャ */
    protected DialectManager dialectManager;

    /** 結果セットファクトリ */
    protected ResultSetFactory resultSetFactory;

    /** ステートメントファクトリ */
    protected StatementFactory statementFactory;

    /**
     * インスタンスを構築します。
     */
    public SqlCommandBuilder() {
    }

    /**
     * Daoヘルパーを設定します．
     * 
     * @param daoHelper
     *            Daoヘルパー
     */
    public void setDaoHelper(final DaoHelper daoHelper) {
        this.daoHelper = daoHelper;
    }

    /**
     * Dialectマネージャを設定します．
     * 
     * @param dialectManager
     *            Dialectマネージャ
     */
    public void setDialectManager(final DialectManager dialectManager) {
        this.dialectManager = dialectManager;
    }

    /**
     * 結果セットファクトリを設定します．
     * 
     * @param resultSetFactory
     *            結果セットファクトリ
     */
    public void setResultSetFactory(final ResultSetFactory resultSetFactory) {
        this.resultSetFactory = resultSetFactory;
    }

    /**
     * ステートメントファクトリを設定します．
     * 
     * @param statementFactory
     *            ステートメントファクトリ
     */
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
        final Class<?> targetClass = getResultClass(daoClass, method);
        if (targetClass == null) {
            throw new IllegalStateException(daoClass.getName() + "#"
                    + method.getName() + "()");
        }
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(daoClass);
        return new SqlCommand(method, isResultList(method), targetClass, sql,
                beanDesc.getMethodParameterNames(method),
                getActualParameterClasses(daoClass, method), dialectManager,
                resultSetFactory, statementFactory);
    }

}
