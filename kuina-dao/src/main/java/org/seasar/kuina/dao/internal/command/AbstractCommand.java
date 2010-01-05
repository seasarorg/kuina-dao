/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
 * Daoメソッドの操作を提供する{@link Command}の共通機能を提供する抽象クラスです．
 * 
 * @author koichik
 */
public abstract class AbstractCommand implements Command {

    /**
     * Daoメソッドのフラッシュ・モードを検出して返します．
     * <p>
     * Daoメソッドにフラッシュ・モードが指定されなかった場合は<code>null</code>を返します．
     * </p>
     * 
     * @param method
     *            Daoメソッド
     * @return Daoメソッドのフラッシュ・モード
     */
    protected FlushModeType detectFlushMode(final Method method) {
        final FlushMode flushMode = method.getAnnotation(FlushMode.class);
        return flushMode == null ? null : flushMode.value();
    }

    /**
     * Daoメソッドのヒントを検出してその<code>Map</code>を返します．
     * <p>
     * Daoメソッドにヒントが指定されていない場合は空の<code>Map</code>を返します．
     * </p>
     * 
     * @param method
     *            Daoメソッド
     * @return Daoメソッドのヒントのマップ
     */
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

    /**
     * Daoメソッドに付けられた{@link Hint}アノテーションの値として指定されたOGNL式を評価した結果を返します．
     * 
     * @param method
     *            Daoメソッド
     * @param hint
     *            {@link Hint}アノテーション
     * @return 値として指定されたOGNL式を評価した結果
     * @throws IllegalHintValueException
     *             ヒントの値がOGNL式として評価できなかった場合にスローされます
     */
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
