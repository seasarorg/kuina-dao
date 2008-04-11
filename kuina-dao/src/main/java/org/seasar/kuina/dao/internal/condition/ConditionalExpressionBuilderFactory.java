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
import java.util.Calendar;
import java.util.Date;

import javax.persistence.TemporalType;

import org.seasar.framework.jpa.metadata.AttributeDesc;
import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;
import org.seasar.kuina.dao.criteria.CriteriaOperations;
import org.seasar.kuina.dao.criteria.grammar.ArithmeticExpression;
import org.seasar.kuina.dao.criteria.grammar.BooleanExpression;
import org.seasar.kuina.dao.criteria.grammar.DatetimeExpression;
import org.seasar.kuina.dao.criteria.grammar.EntityExpression;
import org.seasar.kuina.dao.criteria.grammar.EnumExpression;
import org.seasar.kuina.dao.criteria.grammar.InputParameter;
import org.seasar.kuina.dao.criteria.grammar.StringExpression;
import org.seasar.kuina.dao.internal.ConditionalExpressionBuilder;

/**
 * {@link ConditionalExpressionBuilder}を作成するファクトリです．
 * 
 * @author koichik
 */
public abstract class ConditionalExpressionBuilderFactory {

    // constants
    /** 基本的な操作 (二項演算子) のサフィックスと対応する{@link CriteriaOperations}のメソッド名 */
    protected static final String[][] BASIC_OPERATIONS = new String[][] {
            { "_EQ", "eq" }, { "_NE", "ne" }, { "_LT", "lt" }, { "_LE", "le" },
            { "_GT", "gt" }, { "_GE", "ge" } };

    /** IN演算子のサフィックスと対応する{@link CriteriaOperations}のメソッド名 */
    protected static final String[][] IN_OPERATIONS = new String[][] {
            { "_NOT_IN", "notIn" }, { "_IN", "in" } };

    /** LIKE演算子のサフィックスと対応するパターンのプレフィックスおよびサフィックス */
    protected static final String[][] LIKE_OPERATIONS = new String[][] {
            { "_LIKE", "", "" }, { "_STARTS", "", "%" }, { "_ENDS", "%", "" },
            { "_CONTAINS", "%", "%" } };

    /** IS NULL演算子のサフィックスと対応する{@link CriteriaOperations}のメソッド名 */
    protected static final String[][] IS_NULL_OPERATIONS = new String[][] {
            { "_IS_NULL", "isNull" }, { "_IS_NOT_NULL", "isNotNull" } };

    /** サポートする操作の配列 */
    protected static final String[][][] OPERATIONS = new String[][][] {
            BASIC_OPERATIONS, IN_OPERATIONS, LIKE_OPERATIONS,
            IS_NULL_OPERATIONS };

    /**
     * {@link CriteriaOperations#parameter(String, Number)}
     */
    protected static final Method ARITHMETIC_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    Number.class);

    /**
     * {@link CriteriaOperations#parameter(String, String)}
     */
    protected static final Method STRING_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    String.class);

    /**
     * {@link CriteriaOperations#parameter(String, boolean)}
     */
    protected static final Method BOOLEAN_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    boolean.class);

    /**
     * @see CriteriaOperations#parameter(String, Date, TemporalType)
     */
    protected static final Method DATE_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    Date.class, TemporalType.class);

    /**
     * {@link CriteriaOperations#parameter(String, Calendar, TemporalType)}
     */
    protected static final Method CALENDAR_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    Calendar.class, TemporalType.class);

    /**
     * {@link CriteriaOperations#parameter(String, java.sql.Date)}
     */
    protected static final Method SQL_DATE_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    java.sql.Date.class);

    /**
     * {@link CriteriaOperations#parameter(String, java.sql.Time)}
     */
    protected static final Method SQL_TIME_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    java.sql.Time.class);

    /**
     * {@link CriteriaOperations#parameter(String, java.sql.Timestamp)}
     */
    protected static final Method SQL_TIMESTAMP_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    java.sql.Timestamp.class);

    /**
     * {@link CriteriaOperations#parameter(String, Enum)}
     */
    protected static final Method ENUM_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    Enum.class);

    /**
     * {@link CriteriaOperations#parameter(String, Object)}
     */
    protected static final Method ENTITY_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    Object.class);

    /**
     * {@link ConditionalExpressionBuilder}の配列を作成して返します．
     * 
     * @param entityClass
     *            エンティティ・クラス
     * @param names
     *            Daoメソッドの引数またはエンティティ・Dtoのプロパティ名の配列
     * @param parameterTypes
     *            Daoメソッドの引数またはエンティティ・Dtoのプロパティの型の配列
     * @return {@link ConditionalExpressionBuilder}の配列
     */
    public static ConditionalExpressionBuilder[] createBuilders(
            final Class<?> entityClass, final String[] names,
            final Class<?>[] parameterTypes) {
        final ConditionalExpressionBuilder[] builders = new ConditionalExpressionBuilder[names.length];
        for (int i = 0; i < names.length; ++i) {
            builders[i] = createBuilder(entityClass, names[i],
                    parameterTypes[i]);
        }
        return builders;
    }

    /**
     * {@link ConditionalExpressionBuilder}を作成して返します．
     * 
     * @param entityClass
     *            エンティティ・クラス
     * @param name
     *            Daoメソッドの引数またはエンティティ・Dtoのプロパティ名
     * @param parameterType
     *            Daoメソッドの引数またはエンティティ・Dtoのプロパティの型
     * @return {@link ConditionalExpressionBuilder}
     */
    public static ConditionalExpressionBuilder createBuilder(
            final Class<?> entityClass, final String name,
            final Class<?> parameterType) {
        for (final String[] basicOperation : BASIC_OPERATIONS) {
            final String suffix = basicOperation[0];
            if (name.endsWith(suffix)) {
                return createBasicBuilder(entityClass, name, parameterType,
                        toPropertyName(name, suffix),
                        getParameterMethod(parameterType), basicOperation[1]);
            }
        }
        for (final String[] inOperation : IN_OPERATIONS) {
            final String suffix = inOperation[0];
            if (name.endsWith(suffix)) {
                final Method operationMethod = ReflectionUtil.getMethod(
                        CriteriaOperations.class, inOperation[1], String.class,
                        InputParameter[].class);
                return new InBuilder(entityClass, toPropertyName(name, suffix),
                        name, getParameterMethod(parameterType
                                .getComponentType()), operationMethod);
            }
        }
        for (final String[] likeOperation : LIKE_OPERATIONS) {
            final String suffix = likeOperation[0];
            if (name.endsWith(suffix)) {
                final String starts = likeOperation[1];
                final String ends = likeOperation[2];
                return new LikeBuilder(entityClass,
                        toPropertyName(name, suffix), name,
                        getParameterMethod(parameterType), getOperationMethod(
                                "like", parameterType), starts, ends);
            }
        }
        for (final String[] isNullOperation : IS_NULL_OPERATIONS) {
            final String suffix = isNullOperation[0];
            if (name.endsWith(suffix)) {
                return new IsNullBuilder(entityClass, toPropertyName(name,
                        suffix), name, getOperationMethod(isNullOperation[1]));
            }
        }
        return createBasicBuilder(entityClass, name, parameterType, name
                .replace('$', '.'), getParameterMethod(parameterType), "eq");
    }

    /**
     * Daoメソッドの引数またはエンティティ・Dtoのプロパティ名から<code>_EQ</code>等のサフィックスを取り除いたプロパティ名を返します．
     * 
     * @param name
     *            Daoメソッドの引数またはエンティティ・Dtoのプロパティ名
     * @param suffix
     *            サフィックス
     * @return プロパティ名
     */
    protected static String toPropertyName(final String name,
            final String suffix) {
        return name.substring(0, name.length() - suffix.length()).replace('$',
                '.');
    }

    /**
     * {@link ConditionalExpressionBuilder}を作成して返します．
     * 
     * @param entityClass
     *            エンティティ・クラス
     * @param parameterName
     *            Daoメソッドの引数またはエンティティ・Dtoのプロパティ名
     * @param parameterType
     *            Daoメソッドの引数またはエンティティ・Dtoのプロパティの型
     * @param propertyName
     *            プロパティ名
     * @param parameterMethod
     *            {@link CriteriaOperations}の<code>parameter()</code>メソッド
     * @param operationName
     *            操作の名前
     * @return {@link ConditionalExpressionBuilder}
     */
    protected static ConditionalExpressionBuilder createBasicBuilder(
            final Class<?> entityClass, final String parameterName,
            final Class<?> parameterType, final String propertyName,
            final Method parameterMethod, final String operationName) {
        if (parameterMethod.getParameterTypes().length == 3) {
            return new TemporalBuilder(entityClass, propertyName,
                    parameterName, parameterMethod, getOperationMethod(
                            operationName, parameterType), getTemporalType(
                            entityClass, propertyName));
        }
        return new BasicBuilder(entityClass, propertyName, parameterName,
                parameterMethod, getOperationMethod(operationName,
                        parameterType));
    }

    /**
     * {@link CriteriaOperations}の引数のないメソッドを返します．
     * 
     * @param name
     *            メソッド名
     * @return {@link CriteriaOperations}のメソッド
     */
    protected static Method getOperationMethod(final String name) {
        return ReflectionUtil.getMethod(CriteriaOperations.class, name,
                String.class);
    }

    /**
     * {@link CriteriaOperations}の1引数のメソッドを返します．
     * 
     * @param name
     *            メソッド名
     * @param parameterType
     *            メソッドの引数の型
     * @return {@link CriteriaOperations}のメソッド
     */
    protected static Method getOperationMethod(final String name,
            final Class<?> parameterType) {
        return ReflectionUtil.getMethod(CriteriaOperations.class, name,
                String.class, getOperationMethodParameterType(parameterType));
    }

    /**
     * 指定された型に対応する{@link CriteriaOperations}のメソッドの引数型を返します．
     * 
     * @param parameterType
     *            Daoメソッドの引数またはエンティティ・Dtoのプロパティの型
     * @return {@link CriteriaOperations}のメソッドの引数型
     */
    protected static Class<?> getOperationMethodParameterType(
            final Class<?> parameterType) {
        if (Number.class.isAssignableFrom(ClassUtil
                .getWrapperClassIfPrimitive(parameterType))) {
            return ArithmeticExpression.class;
        }
        if (String.class.isAssignableFrom(parameterType)) {
            return StringExpression.class;
        }
        if (Boolean.class.isAssignableFrom(ClassUtil
                .getWrapperClassIfPrimitive(parameterType))) {
            return BooleanExpression.class;
        }
        if (Date.class.isAssignableFrom(parameterType)) {
            return DatetimeExpression.class;
        }
        if (Calendar.class.isAssignableFrom(parameterType)) {
            return DatetimeExpression.class;
        }
        if (Enum.class.isAssignableFrom(parameterType)) {
            return EnumExpression.class;
        }
        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(parameterType);
        if (entityDesc != null) {
            return EntityExpression.class;
        }
        throw new IllegalArgumentException();// ToDo
    }

    /**
     * 指定された型に対応する{@link CriteriaOperations}の<code>parameter()</code>メソッドを返します．
     * 
     * @param parameterType
     *            Daoメソッドの引数またはエンティティ・Dtoのプロパティの型
     * @return {@link CriteriaOperations}の<code>parameter()</code>メソッド
     */
    protected static Method getParameterMethod(final Class<?> parameterType) {
        if (Number.class.isAssignableFrom(ClassUtil
                .getWrapperClassIfPrimitive(parameterType))) {
            return ARITHMETIC_PARAMETER_METHOD;
        }
        if (String.class.isAssignableFrom(parameterType)) {
            return STRING_PARAMETER_METHOD;
        }
        if (Boolean.class.isAssignableFrom(ClassUtil
                .getWrapperClassIfPrimitive(parameterType))) {
            return BOOLEAN_PARAMETER_METHOD;
        }
        if (java.sql.Date.class.isAssignableFrom(parameterType)) {
            return SQL_DATE_PARAMETER_METHOD;
        }
        if (java.sql.Time.class.isAssignableFrom(parameterType)) {
            return SQL_TIME_PARAMETER_METHOD;
        }
        if (java.sql.Timestamp.class.isAssignableFrom(parameterType)) {
            return SQL_TIMESTAMP_PARAMETER_METHOD;
        }
        if (Date.class.isAssignableFrom(parameterType)) {
            return DATE_PARAMETER_METHOD;
        }
        if (Calendar.class.isAssignableFrom(parameterType)) {
            return CALENDAR_PARAMETER_METHOD;
        }
        if (Enum.class.isAssignableFrom(parameterType)) {
            return ENUM_PARAMETER_METHOD;
        }
        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(parameterType);
        if (entityDesc != null) {
            return ENTITY_PARAMETER_METHOD;
        }
        throw new IllegalArgumentException();// TODO
    }

    /**
     * 指定されたエンティティのプロパティの時制を返します．
     * 
     * @param entityClass
     *            エンティティ・クラス
     * @param propertyName
     *            プロパティ名
     * @return 指定されたエンティティのプロパティの時制
     */
    protected static TemporalType getTemporalType(final Class<?> entityClass,
            final String propertyName) {
        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(entityClass);
        final int pos = propertyName.indexOf('.');
        if (pos >= 0) {
            final AttributeDesc attribute = entityDesc
                    .getAttributeDesc(propertyName.substring(0, pos));
            final String subPropertyName = propertyName.substring(pos + 1);
            if (attribute.isAssociation()) {
                return getTemporalType(attribute.getElementType(),
                        subPropertyName);
            }
            if (attribute.isComponent()) {
                return getTemporalType(attribute
                        .getChildAttributeDesc(subPropertyName));
            }
            throw new IllegalArgumentException(propertyName);
        }
        return getTemporalType(entityDesc.getAttributeDesc(propertyName));
    }

    /**
     * 指定された属性の時制を返します．
     * 
     * @param attribute
     *            属性
     * @return 時制
     */
    protected static TemporalType getTemporalType(final AttributeDesc attribute) {
        final TemporalType temporalType = attribute.getTemporalType();
        return temporalType != null ? temporalType : TemporalType.DATE;
    }

}
