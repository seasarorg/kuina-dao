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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.TemporalType;

import org.seasar.kuina.dao.annotation.Distinct;
import org.seasar.kuina.dao.annotation.FirstResult;
import org.seasar.kuina.dao.annotation.MaxResults;
import org.seasar.kuina.dao.annotation.NamedParameter;
import org.seasar.kuina.dao.annotation.PositionalParameter;
import org.seasar.kuina.dao.annotation.TargetEntity;
import org.seasar.kuina.dao.annotation.TemporalSpec;
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
public abstract class AbstractQueryCommandBuilder extends
        AbstractCommandBuilder {

    protected final boolean resultList;

    protected Pattern firstResultPattern = Pattern.compile("firstResult");

    protected Pattern maxResultsPattern = Pattern.compile("maxResults");

    public AbstractQueryCommandBuilder(final boolean resultList) {
        this.resultList = resultList;
    }

    public void setFirstResultPattern(final String firstResultPattern) {
        this.firstResultPattern = Pattern.compile(firstResultPattern);
    }

    public void setMaxResultsPattern(final String maxResultsPattern) {
        this.maxResultsPattern = Pattern.compile(maxResultsPattern);
    }

    protected boolean isResultList() {
        return resultList;
    }

    protected boolean isDistinct(final Method method) {
        return method.getAnnotation(Distinct.class) != null;
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

    protected Class<?> getElementTypeOfList(final Type parameterizedList) {
        if (!(parameterizedList instanceof ParameterizedType)) {
            return null;
        }

        final ParameterizedType parameterizedType = ParameterizedType.class
                .cast(parameterizedList);
        final Type rawType = parameterizedType.getRawType();
        if (!(rawType instanceof Class)) {
            return null;
        }

        final Class<?> rawClass = Class.class.cast(rawType);
        if (!rawClass.isAssignableFrom(List.class)) {
            return null;
        }

        final Type[] actualTypeArgument = parameterizedType
                .getActualTypeArguments();
        if (actualTypeArgument == null || actualTypeArgument.length != 1) {
            return null;
        }
        if (!(actualTypeArgument[0] instanceof Class)) {
            return null;
        }

        return Class.class.cast(actualTypeArgument[0]);
    }

    protected Class<?> getTargetClass(final Class<?> daoClass,
            final Method method) {
        TargetEntity targetEntity = method.getAnnotation(TargetEntity.class);
        if (targetEntity != null) {
            return targetEntity.value();
        }

        targetEntity = daoClass.getAnnotation(TargetEntity.class);
        if (targetEntity != null) {
            return targetEntity.value();
        }
        return method.getReturnType();
    }
}
