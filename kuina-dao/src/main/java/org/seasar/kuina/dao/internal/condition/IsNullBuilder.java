/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import org.seasar.framework.util.tiger.ReflectionUtil;
import org.seasar.kuina.dao.criteria.CriteriaOperations;
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.criteria.grammar.ConditionalExpression;

/**
 * IS NULLを使用した問い合わせ条件を作成し，SELECT文に追加するビルダです．
 * <p>
 * このビルダは，次のようなCriteria API呼び出しを行います．
 * </p>
 * 
 * <pre>
 * statement.where(<var>isNull</var>("pathExpression"));
 * </pre>
 * 
 * <p>
 * <code><var>isNull</var></code>は<code>operationMethod</code>で表されるメソッドです．
 * </p>
 * 
 * @author koichik
 */
public class IsNullBuilder extends AbstractConditionalExpressionBuilder {

    /**
     * インスタンスを構築します。
     * 
     * @param entityClass
     *            エンティティ・クラス
     * @param propertyPath
     *            プロパティのパス
     * @param parameterName
     *            パラメータ名
     * @param operationMethod
     *            {@link CriteriaOperations}の問い合わせ条件作成メソッド
     */
    public IsNullBuilder(final Class<?> entityClass, final String propertyPath,
            final String parameterName, final Method operationMethod) {
        super(entityClass, propertyPath, parameterName, null, operationMethod);
    }

    public String appendCondition(final SelectStatement statement,
            final Object value) {
        if (value == null) {
            return null;
        }
        if (!Boolean.class.cast(value).booleanValue()) {
            return null;
        }

        final Object expression = ReflectionUtil.invokeStatic(
                getOperationMethod(), getPathExpression());
        statement.where(ConditionalExpression.class.cast(expression));
        return getPropertyPath();
    }

}
