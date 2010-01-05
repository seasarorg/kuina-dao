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

import javax.persistence.TemporalType;

import org.seasar.framework.util.tiger.ReflectionUtil;
import org.seasar.kuina.dao.criteria.CriteriaOperations;
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.criteria.grammar.ConditionalExpression;

/**
 * <code>=</code>等の二項演算子を<code>java.util.Date</code>または<code>java.util.Calendar</code>型の
 * パラメータに適用した問い合わせ条件を作成し，SELECT文に追加するビルダです．
 * <p>
 * このビルダは，次のようなCriteria API呼び出しを行います．
 * </p>
 * 
 * <pre>
 * statement.where(<var>eq</var>("pathExpression", <var>parameter</var>("parameterName", vale, <var>temporalType</var>)));
 * </pre>
 * 
 * <p>
 * <code><var>eq</var></code>は<code>operationMethod</code>で表されるメソッドです．
 * <code><var>parameter</var></code>は<code>parameterMethod</code>で表されるメソッドです．
 * </p>
 * 
 * @author koichik
 */
public class TemporalBuilder extends AbstractConditionalExpressionBuilder {

    // instance fields
    /** パラメータの時制 */
    protected TemporalType temporalType;

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
     * @param tempralType
     *            パラメータの時制
     */
    public TemporalBuilder(final Class<?> entityClass,
            final String propertyPath, final String parameterName,
            final Method parameterMethod, final Method operationMethod,
            final TemporalType tempralType) {
        super(entityClass, propertyPath, parameterName, parameterMethod,
                operationMethod);
        this.temporalType = tempralType;
    }

    public String appendCondition(final SelectStatement statement,
            final Object value) {
        if (value == null) {
            return null;
        }

        final Object parameter = ReflectionUtil.invokeStatic(
                getParameterMethod(), getParameterName(), value, temporalType);
        final Object expression = ReflectionUtil.invokeStatic(
                getOperationMethod(), getPathExpression(), parameter);
        statement.where(ConditionalExpression.class.cast(expression));
        return getPropertyPath();
    }

}
