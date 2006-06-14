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

import org.seasar.kuina.dao.annotation.FirstResult;
import org.seasar.kuina.dao.annotation.MaxResults;
import org.seasar.kuina.dao.annotation.NamedParameter;
import org.seasar.kuina.dao.annotation.PositionalParameter;
import org.seasar.kuina.dao.annotation.QueryName;
import org.seasar.kuina.dao.annotation.TemporalSpec;
import org.seasar.kuina.dao.entity.EntityDesc;
import org.seasar.kuina.dao.entity.EntityDescFactory;
import org.seasar.kuina.dao.internal.binder.CalendarParameterBinder;
import org.seasar.kuina.dao.internal.binder.DateParameterBinder;
import org.seasar.kuina.dao.internal.binder.FirstResultBinder;
import org.seasar.kuina.dao.internal.binder.MaxResultsBinder;
import org.seasar.kuina.dao.internal.binder.ObjectParameterBinder;
import org.seasar.kuina.dao.internal.binder.ParameterBinder;

/**
 * 
 * @author koichik
 */
public abstract class AbstractNamedQueryCommandBuilder extends
        AbstractCommandBuilder {

    protected Pattern firstResultPattern = Pattern.compile("firstResult");

    protected Pattern maxResultsPattern = Pattern.compile("maxResults");

    public AbstractNamedQueryCommandBuilder() {
    }

    public void setFirstResultPattern(final String firstResultPattern) {
        this.firstResultPattern = Pattern.compile(firstResultPattern);
    }

    public void setMaxResultsPattern(final String maxResultsPattern) {
        this.maxResultsPattern = Pattern.compile(maxResultsPattern);
    }

    protected String getQueryName(final Class<?> daoClass, final Method method) {
        final QueryName queryName = method.getAnnotation(QueryName.class);
        if (queryName != null) {
            return queryName.value();
        }

        final Class<?> entityClass = resolveEntityClass(daoClass, method);
        if (entityClass == null) {
            return null;
        }

        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(entityClass);
        if (!entityDesc.isEntity()) {
            return null;
        }

        return entityDesc.getName() + "." + method.getName();
    }

    protected abstract Class<?> resolveEntityClass(final Class<?> daoClass,
            final Method method);

    protected ParameterBinder[] getBinders(final Method method,
            final String[] parameterNames) {
        final PositionalParameter positional = method
                .getAnnotation(PositionalParameter.class);
        if (positional != null || parameterNames == null) {
            return getBindersForPositionalParameter(method);
        }

        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Annotation[][] parameterAnnotations = method
                .getParameterAnnotations();
        final ParameterBinder[] binders = new ParameterBinder[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            final Class<?> type = parameterTypes[i];
            final String name = parameterNames[i];
            final Annotation[] annotations = parameterAnnotations[i];

            if (isFirstResult(name, annotations)) {
                binders[i] = new FirstResultBinder();
            } else if (isMaxResults(name, annotations)) {
                binders[i] = new MaxResultsBinder();
            } else if (Date.class.isAssignableFrom(type)) {
                binders[i] = new DateParameterBinder(name,
                        getTemporalType(annotations));
            } else if (Calendar.class.isAssignableFrom(type)) {
                binders[i] = new CalendarParameterBinder(name,
                        getTemporalType(annotations));
            } else {
                binders[i] = new ObjectParameterBinder(name);
            }
        }
        return binders;
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

            if (isFirstResult(null, annotations)) {
                binders[i] = new FirstResultBinder();
            } else if (isMaxResults(null, annotations)) {
                binders[i] = new MaxResultsBinder();
            } else if (Date.class.isAssignableFrom(type)) {
                binders[i] = new DateParameterBinder(++position,
                        getTemporalType(annotations));
            } else if (Calendar.class.isAssignableFrom(type)) {
                binders[i] = new CalendarParameterBinder(++position,
                        getTemporalType(annotations));
            } else {
                binders[i] = new ObjectParameterBinder(++position);
            }
        }
        return binders;
    }

    protected boolean isFirstResult(final String name,
            final Annotation[] annotations) {
        for (final Annotation annotation : annotations) {
            if (annotation instanceof NamedParameter) {
                return false;
            } else if (annotation instanceof FirstResult)
                return true;
        }
        return name != null && firstResultPattern.matcher(name).matches();
    }

    protected boolean isMaxResults(final String name,
            final Annotation[] annotations) {
        for (final Annotation annotation : annotations) {
            if (annotation instanceof NamedParameter) {
                return false;
            } else if (annotation instanceof MaxResults) {
                return true;
            }
        }
        return name != null && maxResultsPattern.matcher(name).matches();
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
