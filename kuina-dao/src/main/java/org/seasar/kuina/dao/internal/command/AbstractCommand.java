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

import javax.persistence.FlushModeType;

import org.seasar.framework.util.OgnlUtil;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.FlushMode;
import org.seasar.kuina.dao.Hint;
import org.seasar.kuina.dao.Hints;
import org.seasar.kuina.dao.IllegalHintValueException;
import org.seasar.kuina.dao.internal.Command;

/**
 * 
 * @author koichik
 */
public abstract class AbstractCommand implements Command {

    protected FlushModeType detectFlushMode(final Method method) {
        final FlushMode flushMode = method.getAnnotation(FlushMode.class);
        return flushMode == null ? null : flushMode.value();
    }

    protected Map<String, Object> detectHints(final Method method) {
        final Map<String, Object> result = CollectionsUtil.newHashMap();
        final Hints hints = method.getAnnotation(Hints.class);
        if (hints != null) {
            for (final Hint hint : hints.value()) {
                result.put(hint.name(), getHintValue(method, hint));
            }
        }
        final Hint hint = method.getAnnotation(Hint.class);
        if (hint != null) {
            result.put(hint.name(), getHintValue(method, hint));
        }
        return result;
    }

    protected Object getHintValue(final Method method, final Hint hint) {
        final String expression = hint.value();
        try {
            final Object parsedExpression = OgnlUtil
                    .parseExpression(expression);
            return OgnlUtil.getValue(parsedExpression, null);
        } catch (final Exception e) {
            throw new IllegalHintValueException(method, hint.name(),
                    expression, e);
        }
    }

}
