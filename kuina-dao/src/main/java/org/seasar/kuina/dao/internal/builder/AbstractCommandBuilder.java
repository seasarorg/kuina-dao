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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.persistence.TemporalType;

import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.ClassUtil;
import org.seasar.kuina.dao.PositionalParameter;
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

    @Binding(bindingType = BindingType.MUST)
    protected NamingConvention convention;

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

        final String daoClassShortName = ClassUtil.getShortClassName(daoClass
                .getName());
        final String entityClassShortName = ClassUtil.concatName(convention
                .getEntityPackageName(), daoClassShortName
                .substring(0, daoClassShortName.length()
                        - convention.getDaoSuffix().length()));
        final String[] rootPackageNames = convention.getRootPackageNames();
        for (int i = 0; i < rootPackageNames.length; ++i) {
            final String entityClassName = ClassUtil.concatName(
                    rootPackageNames[i], entityClassShortName);
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
        if (Date.class.isAssignableFrom(type)) {
            return new DateParameterBinder(name, getTemporalType(annotations));
        } else if (Calendar.class.isAssignableFrom(type)) {
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
        if (Date.class.isAssignableFrom(type)) {
            return new DateParameterBinder(position,
                    getTemporalType(annotations));
        } else if (Calendar.class.isAssignableFrom(type)) {
            return new CalendarParameterBinder(position,
                    getTemporalType(annotations));
        } else {
            return new ObjectParameterBinder(position);
        }
    }

    protected TemporalType getTemporalType(final Annotation[] annotations) {
        for (final Annotation annotation : annotations) {
            if (annotation instanceof TemporalSpec) {
                final TemporalSpec spec = TemporalSpec.class.cast(annotation);
                return spec.value();
            }
        }
        return TemporalType.TIMESTAMP;
    }

}
