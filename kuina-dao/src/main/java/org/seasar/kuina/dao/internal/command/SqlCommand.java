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
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.seasar.extension.jdbc.ResultSetFactory;
import org.seasar.extension.jdbc.ResultSetHandler;
import org.seasar.extension.jdbc.StatementFactory;
import org.seasar.extension.jdbc.impl.BasicSelectHandler;
import org.seasar.extension.jdbc.impl.BeanListResultSetHandler;
import org.seasar.extension.jdbc.impl.BeanResultSetHandler;
import org.seasar.extension.jdbc.impl.MapListResultSetHandler;
import org.seasar.extension.jdbc.impl.MapResultSetHandler;
import org.seasar.framework.jpa.Dialect;
import org.seasar.framework.jpa.DialectManager;
import org.seasar.kuina.dao.internal.Command;

/**
 * SQL (問い合わせ) を実行する{@link Command}です．
 * 
 * @author higa
 */
public class SqlCommand extends AbstractSqlCommand {

    // instance fields
    /** 問い合わせ結果を{@link List}で返す場合に<code>true</code> */
    protected final boolean resultList;

    /** 結果セットを受け取るJavaBeansのクラス */
    protected final Class<?> beanClass;

    /** 結果セットファクトリ */
    protected final ResultSetFactory resultSetFactory;

    /**
     * インスタンスを構築します。
     * 
     * @param method
     *            Daoメソッド
     * @param resultList
     *            問い合わせ結果を{@link List}で返す場合に<code>true</code>
     * @param beanClass
     *            結果セットを受け取るJavaBeansのクラス
     * @param sql
     *            SQL
     * @param parameterNames
     *            パラメータ名の配列
     * @param parameterTypes
     *            パラメータ型の配列
     * @param dialectManager
     *            Dialectマネージャ
     * @param resultSetFactory
     *            リザルトセット・ファクトリ
     * @param statementFactory
     *            ステートメント・ファクトリ
     */
    public SqlCommand(final Method method, final boolean resultList,
            final Class<?> beanClass, final String sql,
            final String[] parameterNames, final Class<?>[] parameterTypes,
            final DialectManager dialectManager,
            final ResultSetFactory resultSetFactory,
            final StatementFactory statementFactory) {
        super(method, sql, parameterNames, parameterTypes, dialectManager,
                statementFactory);
        this.resultList = resultList;
        this.beanClass = beanClass;
        this.resultSetFactory = resultSetFactory;
    }

    @Override
    protected Object execute(final EntityManager em, final String query,
            final Object[] args, final Class<?>[] argTypes) {
        final BasicSelectHandler handler = new BasicSelectHandler();
        handler.setResultSetHandler(createResultSetHandler());
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

    /**
     * {@link ResultSetHandler}を作成して返します。
     * 
     * @return {@link ResultSetHandler}
     */
    protected ResultSetHandler createResultSetHandler() {
        if (Map.class.isAssignableFrom(beanClass)) {
            return resultList ? new MapListResultSetHandler()
                    : new MapResultSetHandler();
        }
        return resultList ? new BeanListResultSetHandler(beanClass)
                : new BeanResultSetHandler(beanClass);
    }

}
