/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import javax.persistence.FlushModeType;

import org.seasar.extension.jdbc.StatementFactory;
import org.seasar.extension.sql.Node;
import org.seasar.extension.sql.SqlContext;
import org.seasar.extension.sql.context.SqlContextImpl;
import org.seasar.extension.sql.parser.SqlParserImpl;
import org.seasar.framework.jpa.DialectManager;

/**
 * SQLを実行する{Command}の共通機能を提供する抽象クラスです．
 * 
 * @author higa
 */
public abstract class AbstractSqlCommand extends AbstractCommand {

    // instance fields
    /** SQL */
    protected final String sql;

    /** SQLをパースしたノードツリーのルート */
    protected final Node node;

    /** パラメータ名の配列 */
    protected final String[] parameterNames;

    /** パラメータ型の配列 */
    protected final Class<?>[] parameterTypes;

    /** Dialectマネージャ */
    protected final DialectManager dialectManager;

    /** ステートメント・ファクトリ */
    protected final StatementFactory statementFactory;

    /** フラッシュ・モード */
    protected final FlushModeType flushMode;

    /**
     * インスタンスを構築します。
     * 
     * @param method
     *            Daoメソッド
     * @param sql
     *            SQL
     * @param parameterNames
     *            パラメータ名の配列
     * @param parameterTypes
     *            パラメータ型の配列
     * @param dialectManager
     *            Dialectマネージャ
     * @param statementFactory
     *            ステートメント・ファクトリ
     */
    public AbstractSqlCommand(final Method method, final String sql,
            final String[] parameterNames, final Class<?>[] parameterTypes,
            final DialectManager dialectManager,
            final StatementFactory statementFactory) {
        this.sql = sql;
        node = new SqlParserImpl(sql).parse();
        this.parameterNames = parameterNames;
        this.parameterTypes = parameterTypes;
        this.dialectManager = dialectManager;
        this.statementFactory = statementFactory;
        flushMode = detectFlushMode(method);
    }

    public Object execute(final EntityManager em, final Object[] parameters) {
        flushIfNeed(em);
        String query = sql;
        Object[] args = parameters;
        Class<?>[] argTypes = null;
        if (parameterNames != null) {
            final SqlContext sqlContext = new SqlContextImpl();
            for (int i = 0; i < parameterNames.length; ++i) {
                final String name = parameterNames[i];
                final Class<?> type = parameterTypes[i];
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

    /**
     * 必要に応じて永続コンテキストをフラッシュします．
     * 
     * @param em
     *            エンティティ・マネージャ
     * @see EntityManager#flush()
     */
    protected void flushIfNeed(final EntityManager em) {
        if (flushMode == FlushModeType.AUTO
                || (flushMode == null && em.getFlushMode() == FlushModeType.AUTO)) {
            em.flush();
        }
    }

    /**
     * SQLを実行します．
     * 
     * @param em
     *            エンティティ・マネージャ
     * @param query
     *            問い合わせ文字列
     * @param args
     *            Daoメソッドの引数の配列
     * @param argTypes
     *            パラメータ型の配列
     * @return SQLの実行結果
     */
    protected abstract Object execute(final EntityManager em,
            final String query, final Object[] args, Class<?>[] argTypes);

}
