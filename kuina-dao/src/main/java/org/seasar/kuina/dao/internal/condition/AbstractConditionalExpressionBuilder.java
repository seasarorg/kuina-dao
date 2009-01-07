/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.kuina.dao.criteria.CriteriaOperations;
import org.seasar.kuina.dao.internal.ConditionalExpressionBuilder;
import org.seasar.kuina.dao.internal.util.JpqlUtil;
import org.seasar.kuina.dao.internal.util.KuinaDaoUtil;

/**
 * Criteria APIを動的に呼び出して問い合わせ条件等を作成し，SELECT文に追加するビルダの抽象クラスです．
 * 
 * @author koichik
 */
public abstract class AbstractConditionalExpressionBuilder implements
        ConditionalExpressionBuilder {

    // instance fields
    /** エンティティ・クラス */
    protected Class<?> entityClass;

    /** プロパティのパス */
    protected String propertyPath;

    /** パス式 */
    protected String pathExpression;

    /** パス式 (path_expression) を表す文字列 */
    protected String parameterName;

    /** {@link CriteriaOperations}の<code>parameter()</code>メソッド */
    protected Method parameterMethod;

    /** {@link CriteriaOperations}の問い合わせ条件作成メソッド */
    protected Method operationMethod;

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
    public AbstractConditionalExpressionBuilder(final Class<?> entityClass,
            final String propertyPath, final String parameterName,
            final Method parameterMethod, final Method operationMethod) {
        this.entityClass = entityClass;
        this.propertyPath = propertyPath;
        this.parameterName = parameterName;
        this.parameterMethod = parameterMethod;
        this.operationMethod = operationMethod;
        this.pathExpression = toPathExpression();
    }

    /**
     * プロパティのパスからプロパティ名を作成して返します．
     * 
     * @return プロパティ名
     */
    protected String toPathExpression() {
        final String identificationVariable = JpqlUtil
                .toDefaultIdentificationVariable(entityClass);
        final int pos1 = propertyPath.indexOf('.');
        if (pos1 == -1) {
            return identificationVariable + "." + propertyPath;
        }

        final String firstPropertyName = propertyPath.substring(0, pos1);
        final EntityDesc entityDesc = KuinaDaoUtil.getEntityDesc(entityClass);
        final int pos2 = propertyPath.indexOf('.', pos1 + 1);
        if (pos2 == -1) {
            return KuinaDaoUtil.isAssociation(entityDesc, firstPropertyName) ? propertyPath
                    : identificationVariable + "." + propertyPath;
        }

        final String secondPropertyName = propertyPath
                .substring(pos1 + 1, pos2);
        final EntityDesc associationEntity = KuinaDaoUtil
                .getAssociationEntityDesc(entityDesc, firstPropertyName);
        return KuinaDaoUtil
                .isAssociation(associationEntity, secondPropertyName) ? propertyPath
                .substring(pos1 + 1)
                : propertyPath;
    }

    /**
     * プロパティのパスを返します．
     * 
     * @return プロパティのパス
     */
    public String getPropertyPath() {
        return propertyPath;
    }

    /**
     * パス式 (path_expression) を表す文字列を返します．
     * 
     * @return パス式 (path_expression) を表す文字列
     */
    public String getPathExpression() {
        return pathExpression;
    }

    /**
     * パラメータ名を返します．
     * 
     * @return パラメータ名
     */
    public String getParameterName() {
        return parameterName;
    }

    /**
     * {@link CriteriaOperations}の<code>parameter()</code>メソッドを返します．
     * 
     * @return {@link CriteriaOperations}の<code>parameter()</code>メソッド
     */
    public Method getParameterMethod() {
        return parameterMethod;
    }

    /**
     * {@link CriteriaOperations}の問い合わせ条件作成メソッドを返します．
     * 
     * @return {@link CriteriaOperations}の問い合わせ条件作成メソッド
     */
    public Method getOperationMethod() {
        return operationMethod;
    }

}
