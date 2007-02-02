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
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.FlushModeType;
import javax.persistence.Query;

import org.seasar.framework.util.NumberConversionUtil;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.FlushMode;
import org.seasar.kuina.dao.Hint;
import org.seasar.kuina.dao.Hints;
import org.seasar.kuina.dao.IllegalHintTypeException;
import org.seasar.kuina.dao.criteria.SelectStatement;

/**
 * 
 * @author koichik
 */
public abstract class AbstractQueryCommand extends AbstractCommand {

    protected final Class<?> entityClass;

    protected final Method method;

    protected final boolean resultList;

    protected final FlushModeType flushMode;

    protected final Map<String, Object> hints = CollectionsUtil
            .newLinkedHashMap();

    /**
     * インスタンスを構築します。
     */
    public AbstractQueryCommand(final Class<?> entityClass,
            final Method method, final boolean resultList) {
        this.entityClass = entityClass;
        this.method = method;
        this.resultList = resultList;
        flushMode = detectFlushMode(method);
        detectHints(method);
    }

    protected FlushModeType detectFlushMode(final Method method) {
        final FlushMode flushMode = method.getAnnotation(FlushMode.class);
        return flushMode == null ? null : flushMode.value();
    }

    protected void detectHints(final Method method) {
        final Hints hints = method.getAnnotation(Hints.class);
        if (hints != null) {
            for (final Hint hint : hints.value()) {
                this.hints.put(hint.name(), getHintValue(hint));
            }
        }
        final Hint hint = method.getAnnotation(Hint.class);
        if (hint != null) {
            this.hints.put(hint.name(), getHintValue(hint));
        }
    }

    @SuppressWarnings("unchecked")
    protected Object getHintValue(final Hint hint) {
        final String value = hint.value();
        final Class<?> type = hint.type();
        if (type == String.class) {
            return value;
        } else if (type == Boolean.class) {
            return Boolean.valueOf(value);
        } else if (Number.class.isAssignableFrom(type)) {
            return NumberConversionUtil.convertNumber(type, value);
        } else if (Enum.class.isAssignableFrom(type)) {
            return Enum.valueOf((Class<? extends Enum>) type, value);
        }
        throw new IllegalHintTypeException(type);
    }

    protected void setupQuery(final Query query) {
        if (flushMode != null) {
            query.setFlushMode(flushMode);
        }
        for (final Entry<String, Object> entry : hints.entrySet()) {
            query.setHint(entry.getKey(), entry.getValue());
        }
    }

    protected void setupStatement(final SelectStatement statement) {
        if (flushMode != null) {
            statement.setFlushMode(flushMode);
        }
        for (final Entry<String, Object> entry : hints.entrySet()) {
            statement.addHint(entry.getKey(), entry.getValue());
        }
    }

}
