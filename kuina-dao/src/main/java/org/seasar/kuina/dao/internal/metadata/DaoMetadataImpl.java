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
package org.seasar.kuina.dao.internal.metadata;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import javax.persistence.EntityManager;

import org.seasar.extension.dao.helper.DaoHelper;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;
import org.seasar.framework.jpa.EntityManagerProvider;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.CommandBuilder;
import org.seasar.kuina.dao.internal.DaoMetadata;

/**
 * Daoメタデータの実装クラスです．
 * 
 * @author koichik
 */
@Component(instance = InstanceType.PROTOTYPE)
public class DaoMetadataImpl implements DaoMetadata {

    // static fields
    private static final Logger logger = Logger
            .getLogger(DaoMetadataImpl.class);

    // instance fields
    /** このコンポーネントを定義しているS2コンテナ */
    @Binding(bindingType = BindingType.MUST)
    protected S2Container container;

    /** Daoヘルパー */
    @Binding(bindingType = BindingType.MUST)
    protected DaoHelper daoHelper;

    /** エンティティ・マネージャ・プロバイダ */
    @Binding(bindingType = BindingType.MUST)
    protected EntityManagerProvider entityManagerProvider;

    /** コマンドビルダの配列 */
    @Binding(bindingType = BindingType.MUST)
    protected CommandBuilder[] builders;

    /** Daoクラス */
    protected Class<?> daoClass;

    /** エンティティ・マネージャ */
    protected EntityManager entityManager;

    /** メソッドとコマンドのマッピング */
    protected Map<Method, CommandHolder> commands = CollectionsUtil
            .newHashMap();

    public void initialize(final Class<?> daoClass) {
        this.daoClass = daoClass;
        final String dsName = daoHelper.getDataSourceName(daoClass);
        entityManager = entityManagerProvider.getEntityManger(dsName);

        for (final Method method : daoClass.getMethods()) {
            if (!Modifier.isAbstract(method.getModifiers())
                    || method.isBridge() || method.isSynthetic()) {
                continue;
            }
            commands.put(method, new CommandHolder(method));
        }
    }

    public Object execute(final Method method, final Object[] arguments) {
        final CommandHolder holder = commands.get(method);
        final Command command = holder != null ? holder.get() : null;
        if (command == null) {
            return NOT_INVOKED;
        }

        return command.execute(entityManager, arguments);
    }

    /**
     * Daoメソッドに対応したコマンドを作成して返します．
     * <p>
     * Daoメソッドに対応したコマンドが作成できなかった場合は<code>null</code>を返します．
     * </p>
     * 
     * @param daoClass
     *            Daoクラス
     * @param method
     *            Daoのメソッド
     * @return Daoメソッドに対応したコマンド
     */
    protected Command createCommand(final Class<?> daoClass, final Method method) {
        for (final CommandBuilder builder : builders) {
            final Command command = builder.build(daoClass, method);
            if (command != null) {
                if (logger.isDebugEnabled()) {
                    logger.log("DKuinaDao3001", new Object[] { daoClass,
                            method, command.getClass().getName() });
                }
                return command;
            }
        }
        logger.log("WKuinaDao3001", new Object[] { daoClass, method });
        return null;
    }

    /**
     * コマンドを遅延初期化して保持するためのクラスです．
     * 
     * @author koichik
     */
    public class CommandHolder {

        /** メソッド */
        protected final Method method;

        /** 初期化済みなら<code>true</code> */
        protected volatile boolean initialized;

        /** コマンド */
        protected Command command;

        /**
         * インスタンスを構築します。
         * 
         * @param method
         *            メソッド
         */
        public CommandHolder(final Method method) {
            this.method = method;
        }

        /**
         * コマンドを返します。
         * 
         * @return コマンド
         */
        public Command get() {
            if (!initialized) {
                command = createCommand(daoClass, method);
                initialized = true;
            }
            return command;
        }

    }

}
