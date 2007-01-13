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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;

import org.seasar.extension.dao.helper.DaoHelper;
import org.seasar.extension.tx.annotation.RequiresNewTx;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.jpa.EntityManagerProvider;
import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ClassUtil;
import org.seasar.kuina.dao.PositionalParameter;
import org.seasar.kuina.dao.QueryName;
import org.seasar.kuina.dao.TargetEntity;
import org.seasar.kuina.dao.TemporalSpec;
import org.seasar.kuina.dao.internal.CommandBuilder;
import org.seasar.kuina.dao.internal.binder.CalendarParameterBinder;
import org.seasar.kuina.dao.internal.binder.DateParameterBinder;
import org.seasar.kuina.dao.internal.binder.ObjectParameterBinder;
import org.seasar.kuina.dao.internal.binder.ParameterBinder;

/**
 * 
 * @author koichik
 */
public abstract class AbstractCommandBuilder implements CommandBuilder {

    private static final Logger logger = Logger
            .getLogger(AbstractCommandBuilder.class);

    @Binding(bindingType = BindingType.MUST)
    protected NamingConvention convention;

    @Binding(bindingType = BindingType.MUST)
    protected DaoHelper daoHelper;

    @Binding(bindingType = BindingType.MUST)
    protected EntityManagerProvider entityManagerProvider;

    protected Pattern methodNamePattern;

    public void setMethodNamePattern(final String methodNamePattern) {
        this.methodNamePattern = Pattern.compile(methodNamePattern);
    }

    protected boolean isMatched(final Method method) {
        return methodNamePattern.matcher(method.getName()).matches();
    }

    protected Class<?> getTargetClass(final Class<?> daoClass,
            final Method method) {
        final TargetEntity methodAnnotatedTargetEntity = method
                .getAnnotation(TargetEntity.class);
        if (methodAnnotatedTargetEntity != null) {
            return methodAnnotatedTargetEntity.value();
        }

        final TargetEntity classAnnotatedTargetEntity = daoClass
                .getAnnotation(TargetEntity.class);
        if (classAnnotatedTargetEntity != null) {
            return classAnnotatedTargetEntity.value();
        }

        return getTargetClassFromDaoName(daoClass);
    }

    protected Class<?> getTargetClassFromDaoName(final Class<?> daoClass) {
        final String daoClassShortName = ClassUtil.getShortClassName(convention
                .toInterfaceClassName(daoClass.getName()));
        final String partOfEntityClassName = ClassUtil.concatName(convention
                .getEntityPackageName(), daoClassShortName
                .substring(0, daoClassShortName.length()
                        - convention.getDaoSuffix().length()));
        final String[] rootPackageNames = convention.getRootPackageNames();
        for (int i = 0; i < rootPackageNames.length; ++i) {
            final String entityClassName = ClassUtil.concatName(
                    rootPackageNames[i], partOfEntityClassName);
            try {
                return ClassUtil.forName(entityClassName);
            } catch (final Exception ignore) {
            }
        }
        return null;
    }

    protected ParameterBinder[] getBinders(final Method method,
            final String[] parameterNames) {
        final PositionalParameter positional = method
                .getAnnotation(PositionalParameter.class);
        if (positional != null || parameterNames == null) {
            return getBindersForPositionalParameter(method);
        }
        return getBindersForNamedParameter(method, parameterNames);
    }

    protected ParameterBinder[] getBindersForNamedParameter(
            final Method method, final String[] parameterNames) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Annotation[][] parameterAnnotations = method
                .getParameterAnnotations();
        final ParameterBinder[] binders = new ParameterBinder[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            final Class<?> type = parameterTypes[i];
            final String name = parameterNames[i];
            final Annotation[] annotations = parameterAnnotations[i];
            binders[i] = getBinderForNamedParameter(type, name, annotations);
        }
        return binders;
    }

    protected ParameterBinder getBinderForNamedParameter(final Class<?> type,
            final String name, final Annotation[] annotations) {
        if (java.sql.Date.class.isAssignableFrom(type)) {
            return new ObjectParameterBinder(name);
        }
        if (java.sql.Time.class.isAssignableFrom(type)) {
            return new ObjectParameterBinder(name);
        }
        if (java.sql.Timestamp.class.isAssignableFrom(type)) {
            return new ObjectParameterBinder(name);
        }
        if (Date.class.isAssignableFrom(type)) {
            return new DateParameterBinder(name, getTemporalType(annotations));
        }
        if (Calendar.class.isAssignableFrom(type)) {
            return new CalendarParameterBinder(name,
                    getTemporalType(annotations));
        }
        return new ObjectParameterBinder(name);
    }

    protected ParameterBinder[] getBindersForPositionalParameter(
            final Method method) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Annotation[][] parameterAnnotations = method
                .getParameterAnnotations();
        final ParameterBinder[] binders = new ParameterBinder[parameterTypes.length];
        int position = 0;
        for (int i = 0; i < parameterTypes.length; ++i) {
            final Class<?> type = parameterTypes[i];
            final Annotation[] annotations = parameterAnnotations[i];
            binders[i] = getBinderForPositionalParameter(type, ++position,
                    annotations);
        }
        return binders;
    }

    protected ParameterBinder getBinderForPositionalParameter(
            final Class<?> type, final int position,
            final Annotation[] annotations) {
        if (java.sql.Date.class.isAssignableFrom(type)) {
            return new ObjectParameterBinder(position);
        }
        if (java.sql.Time.class.isAssignableFrom(type)) {
            return new ObjectParameterBinder(position);
        }
        if (java.sql.Timestamp.class.isAssignableFrom(type)) {
            return new ObjectParameterBinder(position);
        }
        if (Date.class.isAssignableFrom(type)) {
            return new DateParameterBinder(position,
                    getTemporalType(annotations));
        }
        if (Calendar.class.isAssignableFrom(type)) {
            return new CalendarParameterBinder(position,
                    getTemporalType(annotations));
        }
        return new ObjectParameterBinder(position);
    }

    protected TemporalType getTemporalType(final Annotation[] annotations) {
        for (final Annotation annotation : annotations) {
            if (annotation instanceof TemporalSpec) {
                final TemporalSpec spec = TemporalSpec.class.cast(annotation);
                return spec.value();
            }
        }
        return TemporalType.DATE;
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
                logger.log("DKuinaDao3002", new Object[] { queryName });
            }
            return true;
        } catch (final Exception ignore) {
        }
        return false;
    }

}
