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
package org.seasar.kuina.dao.internal.builder;

import java.lang.reflect.Method;

import javax.persistence.EntityManager;

import org.seasar.extension.dao.helper.DaoHelper;
import org.seasar.extension.tx.annotation.RequiresNewTx;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.jpa.EntityManagerProvider;
import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ClassUtil;
import org.seasar.kuina.dao.QueryName;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.command.NamedQueryUpdateCommand;

/**
 * 
 * @author koichik
 */
@Component
public class NamedQueryUpdateCommandBuilder extends AbstractCommandBuilder {

    private static final Logger logger = Logger
            .getLogger(NamedQueryUpdateCommandBuilder.class);

    public NamedQueryUpdateCommandBuilder() {
        setMethodNamePattern("(update|delete|remove).+");
    }

    @Binding(bindingType = BindingType.MUST)
    protected DaoHelper daoHelper;

    @Binding(bindingType = BindingType.MUST)
    protected EntityManagerProvider entityManagerProvider;

    public Command build(final Class<?> daoClass, final Method method) {
        if (!isMatched(method)) {
            return null;
        }
        final Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length > 1) {
            return null;
        }
        if (parameterTypes.length == 1
                && !Integer.class.isAssignableFrom(ClassUtil
                        .getWrapperClassIfPrimitive(parameterTypes[0]))) {
            return null;
        }

        final String queryName = getQueryName(daoClass, method);
        if (queryName == null || !isExists(daoClass, queryName)) {
            return null;
        }

        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(daoClass);
        return new NamedQueryUpdateCommand(queryName, getBinders(method,
                beanDesc.getMethodParameterNamesNoException(method)));
    }

    protected String getQueryName(final Class<?> daoClass, final Method method) {
        final QueryName queryName = method.getAnnotation(QueryName.class);
        if (queryName != null) {
            return queryName.value();
        }

        final Class<?> targetClass = getTargetClass(daoClass, method);
        if (targetClass != null) {
            final EntityDesc entityDesc = EntityDescFactory
                    .getEntityDesc(targetClass);
            if (entityDesc != null) {
                return entityDesc.getEntityName() + "." + method.getName();
            }
        }

        return null;
    }

    @RequiresNewTx
    public boolean isExists(final Class<?> daoClass, final String queryName) {
        try {
            final String prefix = daoHelper.getDataSourceName(daoClass);
            final EntityManager em = entityManagerProvider
                    .getEntityManger(prefix);
            em.createNamedQuery(queryName);
            if (logger.isDebugEnabled()) {
                logger.log("DKuinaDao2002", new Object[] { queryName });
            }
            return true;
        } catch (final Exception ignore) {
        }
        return false;
    }

}
