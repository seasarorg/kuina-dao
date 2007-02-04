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
import java.util.List;
import java.util.regex.Pattern;

import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;
import org.seasar.framework.util.tiger.ReflectionUtil;
import org.seasar.kuina.dao.FirstResult;
import org.seasar.kuina.dao.MaxResults;
import org.seasar.kuina.dao.NamedParameter;
import org.seasar.kuina.dao.Orderby;
import org.seasar.kuina.dao.internal.binder.FirstResultBinder;
import org.seasar.kuina.dao.internal.binder.MaxResultsBinder;
import org.seasar.kuina.dao.internal.binder.NullBinder;
import org.seasar.kuina.dao.internal.binder.ParameterBinder;

/**
 * 
 * @author koichik
 */
public abstract class AbstractQueryCommandBuilder extends
        AbstractCommandBuilder {

    protected Pattern orderbyPattern = Pattern.compile("order[bB]y");

    protected Pattern firstResultPattern = Pattern.compile("firstResult");

    protected Pattern maxResultsPattern = Pattern.compile("maxResults");

    public AbstractQueryCommandBuilder() {
        setMethodNamePattern("(get|find).+");
    }

    public void setOrderbyPattern(final String orderbyPattern) {
        this.orderbyPattern = Pattern.compile(orderbyPattern);
    }

    public void setFirstResultPattern(final String firstResultPattern) {
        this.firstResultPattern = Pattern.compile(firstResultPattern);
    }

    public void setMaxResultsPattern(final String maxResultsPattern) {
        this.maxResultsPattern = Pattern.compile(maxResultsPattern);
    }

    protected boolean isResultList(final Method method) {
        return List.class.isAssignableFrom(method.getReturnType());
    }

    @Override
    protected Class<?> getTargetClass(final Class<?> daoClass,
            final Method method) {
        final Class<?> resultClass = getResultClass(method);
        if (resultClass != null) {
            final EntityDesc entityDesc = EntityDescFactory
                    .getEntityDesc(resultClass);
            if (entityDesc != null) {
                return resultClass;
            }
        }
        return super.getTargetClass(daoClass, method);
    }

    protected Class<?> getResultClass(final Method method) {
        if (isResultList(method)) {
            return ReflectionUtil.getElementTypeOfList(method
                    .getGenericReturnType());
        }
        final Class<?> returnType = method.getReturnType();
        return returnType == void.class ? null : returnType;
    }

    @Override
    protected ParameterBinder getBinderForNamedParameter(final Class<?> type,
            final String name, final Annotation[] annotations) {
        if (isOrderby(name, annotations)) {
            return new NullBinder();
        } else if (isFirstResult(name, annotations)) {
            return new FirstResultBinder();
        } else if (isMaxResults(name, annotations)) {
            return new MaxResultsBinder();
        }
        return super.getBinderForNamedParameter(type, name, annotations);
    }

    @Override
    protected ParameterBinder getBinderForPositionalParameter(
            final Class<?> type, final int position,
            final Annotation[] annotations) {
        if (isOrderby(null, annotations)) {
            return new NullBinder();
        } else if (isFirstResult(null, annotations)) {
            return new FirstResultBinder();
        } else if (isMaxResults(null, annotations)) {
            return new MaxResultsBinder();
        }
        return super.getBinderForPositionalParameter(type, position,
                annotations);
    }

    protected boolean isOrderby(final String name,
            final Annotation[] annotations) {
        for (final Annotation annotation : annotations) {
            if (annotation instanceof NamedParameter) {
                return false;
            } else if (annotation instanceof Orderby)
                return true;
        }
        return name != null && orderbyPattern.matcher(name).matches();
    }

    protected int getOrderbyParameter(final String[] parameterNames,
            final Annotation[][] annotations) {
        if (parameterNames == null) {
            return -1;
        }
        for (int i = 0; i < parameterNames.length; ++i) {
            if (isOrderby(parameterNames[i], annotations[i])) {
                return i;
            }
        }
        return -1;
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

    protected int getFirstResultParameter(final String[] parameterNames,
            final Annotation[][] annotations) {
        if (parameterNames == null) {
            return -1;
        }
        for (int i = 0; i < parameterNames.length; ++i) {
            if (isFirstResult(parameterNames[i], annotations[i])) {
                return i;
            }
        }
        return -1;
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

    protected int getMaxResultsParameter(final String[] parameterNames,
            final Annotation[][] annotations) {
        if (parameterNames == null) {
            return -1;
        }
        for (int i = 0; i < parameterNames.length; ++i) {
            if (isMaxResults(parameterNames[i], annotations[i])) {
                return i;
            }
        }
        return -1;
    }

}
