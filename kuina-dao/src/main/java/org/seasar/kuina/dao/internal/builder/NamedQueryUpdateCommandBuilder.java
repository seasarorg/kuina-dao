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
package org.seasar.kuina.dao.internal.builder;

import java.lang.reflect.Method;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.command.NamedQueryUpdateCommand;

/**
 * {@link NamedQueryUpdateCommand}を作成するコマンドです．
 * 
 * @author koichik
 */
@Component
public class NamedQueryUpdateCommandBuilder extends AbstractCommandBuilder {

    /**
     * インスタンスを構築します。
     * 
     */
    public NamedQueryUpdateCommandBuilder() {
        setMethodNamePattern("(update|delete|remove).*");
    }

    public Command build(final Class<?> daoClass, final Method method) {
        if (!isMatched(method)) {
            return null;
        }

        final String[] queryNames = getQueryNames(daoClass, method);
        for (final String queryName : queryNames) {
            if (isExists(daoClass, queryName)) {
                final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(daoClass);
                return new NamedQueryUpdateCommand(daoClass, method, queryName,
                        getBinders(method, beanDesc));
            }
        }
        return null;
    }

}
