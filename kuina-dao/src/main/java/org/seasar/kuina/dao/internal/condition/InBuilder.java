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
package org.seasar.kuina.dao.internal.condition;

import java.lang.reflect.Method;
import java.util.Collection;

import org.seasar.framework.exception.SIllegalArgumentException;
import org.seasar.framework.util.tiger.ReflectionUtil;
import org.seasar.kuina.dao.criteria.CriteriaOperations;
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.criteria.grammar.ConditionalExpression;
import org.seasar.kuina.dao.criteria.grammar.InputParameter;

/**
 * INを使用した問い合わせ条件を作成し，SELECT文に追加するビルダです．
 * <p>
 * このビルダは，次のようなCriteria API呼び出しを行います．
 * </p>
 * 
 * <pre>
 * statement.where(<var>in</var>("pathExpression", <var>parameter</var>("parameterName", vale), ...));
 * </pre>
 * 
 * <p>
 * <code><var>in</var></code>は<code>operationMethod</code>で表されるメソッドです．
 * <code><var>parameter</var></code>は<code>parameterMethod</code>で表されるメソッドです．
 * </p>
 * 
 * @author koichik
 */
public class InBuilder extends AbstractConditionalExpressionBuilder {

    /**
     * インスタンスを構築します。
     * 
     * @param entityClass
     *            エンティティ・クラス
     * @param propertyPath
     *            プロパティのパス
     * @param parameterName
     *            パラメータ名
     * @param parameterMethod
     *            {@link CriteriaOperations}の<code>parameter()</code>メソッド
     * @param operationMethod
     *            {@link CriteriaOperations}の問い合わせ条件作成メソッド
     */
    public InBuilder(final Class<?> entityClass, final String propertyPath,
            final String parameterName, final Method parameterMethod,
            final Method operationMethod) {
        super(entityClass, propertyPath, parameterName, parameterMethod,
                operationMethod);
    }

    public String appendCondition(final SelectStatement statement,
            final Object value) {
        if (value == null) {
            return null;
        }

        final Object parameter = createParameters(value);
        final Object expression = ReflectionUtil.invokeStatic(
                getOperationMethod(), getPathExpression(), parameter);
        statement.where(ConditionalExpression.class.cast(expression));
        return getPropertyPath();
    }

    /**
     * パラメータ式の配列を作成します．
     * 
     * @param values
     *            IN句のパラメータにバインドされる値の配列または{@link Collection}
     * @return パラメータ式の配列
     */
    protected Object createParameters(final Object values) {
        if (Object[].class.isInstance(values)) {
            return createParametersFromArray(Object[].class.cast(values));
        }
        if (Collection.class.isInstance(values)) {
            return createParameterFromCollection(Collection.class.cast(values));
        }
        throw new SIllegalArgumentException("EKuinaDao2002",
                new Object[] { values });
    }

    /**
     * IN句のパラメータにバインドされる値の配列からパラメータ式の配列を作成します．
     * 
     * @param values
     *            IN句のパラメータにバインドされる値の配列または{@link Collection}
     * @return パラメータの配列
     */
    protected Object createParametersFromArray(final Object[] values) {
        final InputParameter[] parameters = new InputParameter[values.length];
        for (int i = 0; i < values.length; ++i) {
            parameters[i] = ReflectionUtil.invokeStatic(getParameterMethod(),
                    getParameterName() + i, values[i]);
        }
        return parameters;
    }

    /**
     * IN句のパラメータにバインドされる値の{@link Collection}からパラメータ式の配列を作成します．
     * 
     * @param values
     *            IN句のパラメータにバインドされる値の配列または{@link Collection}
     * @return パラメータの配列
     */
    protected Object createParameterFromCollection(final Collection<?> values) {
        final InputParameter[] parameters = new InputParameter[values.size()];
        int i = 0;
        for (final Object value : values) {
            parameters[i++] = ReflectionUtil.invokeStatic(getParameterMethod(),
                    getParameterName(), value);
        }
        return parameters;
    }

}
