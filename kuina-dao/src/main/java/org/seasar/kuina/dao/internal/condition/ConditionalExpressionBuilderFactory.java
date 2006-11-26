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

/**
 * 
 * @author koichik
 */
public class ConditionalExpressionBuilderFactory {

    protected static final String[][] BASIC_OPERATIONS = new String[][] {
            { "_EQ", "eq" }, { "_NE", "ne" }, { "_LT", "lt" }, { "_LE", "le" },
            { "_GT", "gt" }, { "_GE", "ge" } };

    protected static final String[][] IN_OPERATIONS = new String[][] {
            { "_IN", "in" }, { "_NOT_IN", "notIn" } };

    protected static final String[][] LIKE_OPERATIONS = new String[][] {
            { "_LIKE", "", "" }, { "_STARTS", "", "%" }, { "_ENDS", "%", "" },
            { "_CONTAINS", "%", "%" } };

    protected static final String[][] IS_NULL_OPERATIONS = new String[][] {
            { "_IS_NULL", "isNull" }, { "_IS_NOT_NULL", "isNotNull" } };

    protected static final Method ARITHMETIC_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    Number.class);

    protected static final Method STRING_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    String.class);

    protected static final Method BOOLEAN_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    boolean.class);

    protected static final Method DATE_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    Date.class, TemporalType.class);

    protected static final Method CALENDAR_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    Calendar.class, TemporalType.class);

    protected static final Method SQL_DATE_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    java.sql.Date.class);

    protected static final Method SQL_TIME_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    java.sql.Time.class);

    protected static final Method SQL_TIMESTAMP_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    java.sql.Timestamp.class);

    protected static final Method ENUM_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    Enum.class);

    protected static final Method ENTITY_PARAMETER_METHOD = ReflectionUtil
            .getMethod(CriteriaOperations.class, "parameter", String.class,
                    Object.class);

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

    public static ConditionalExpressionBuilder createBuilder(
            final Class<?> entityClass, final String name,
            final Class<?> parameterType) {
        for (String[] basicOperation : BASIC_OPERATIONS) {
            final String suffix = basicOperation[0];
            if (name.endsWith(suffix)) {
                return createBasicBuilder(entityClass, name, parameterType,
                        toPropertyName(name, suffix),
                        getParameterMethod(parameterType), basicOperation[1]);
            }
        }
        for (String[] inOperation : IN_OPERATIONS) {
            final String suffix = inOperation[0];
            if (name.endsWith(suffix)) {
                final Method operationMethod = ReflectionUtil.getMethod(
                        CriteriaOperations.class, inOperation[1], String.class,
                        InputParameter[].class);
                return new InBuilder(toPropertyName(name, suffix), name,
                        getParameterMethod(parameterType.getComponentType()),
                        operationMethod);
            }
        }
        for (String[] likeOperation : LIKE_OPERATIONS) {
            final String suffix = likeOperation[0];
            if (name.endsWith(suffix)) {
                final String starts = likeOperation[1];
                final String ends = likeOperation[2];
                return new LikeBuilder(toPropertyName(name, suffix), name,
                        getParameterMethod(parameterType), getOperationMethod(
                                "like", parameterType), starts, ends);
            }
        }
        for (String[] isNullOperation : IS_NULL_OPERATIONS) {
            final String suffix = isNullOperation[0];
            if (name.endsWith(suffix)) {
                return new IsNullBuilder(toPropertyName(name, suffix), name,
                        getOperationMethod(isNullOperation[1]));
            }
        }
        if (name.equals("orderby")) {
            return new OrderbyBuilder(entityClass);
        }
        if (name.equals("firstResult")) {
            return new FirstResultBuilder();
        }
        if (name.equals("maxResults")) {
            return new MaxResultsBuilder();
        }
        return createBasicBuilder(entityClass, name, parameterType, name
                .replace('$', '.'), getParameterMethod(parameterType), "eq");
    }

    protected static String toPropertyName(final String name,
            final String suffix) {
        return name.substring(0, name.length() - suffix.length()).replace('$',
                '.');
    }

    protected static ConditionalExpressionBuilder createBasicBuilder(
            final Class<?> entityClass, final String parameterName,
            final Class<?> parameterType, final String propertyName,
            final Method parameterMethod, final String operationName) {
        if (parameterMethod.getParameterTypes().length == 3) {
            return new TemporalBuilder(propertyName, parameterName,
                    parameterMethod, getOperationMethod(operationName,
                            parameterType), getTemporalType(entityClass,
                            propertyName));
        }
        return new BasicBuilder(propertyName, parameterName, parameterMethod,
                getOperationMethod(operationName, parameterType));
    }

    protected static Method getOperationMethod(final String name) {
        return ReflectionUtil.getMethod(CriteriaOperations.class, name,
                String.class);
    }

    protected static Method getOperationMethod(final String name,
            final Class<?> parameterType) {
        return ReflectionUtil.getMethod(CriteriaOperations.class, name,
                String.class, getOperationMethodParameterType(parameterType));
    }

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

    protected static TemporalType getTemporalType(final Class<?> entityClass,
            final String propertyName) {
        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(entityClass);
        final int pos = propertyName.indexOf('.');
        if (pos >= 0) {
            final AttributeDesc attributeDesc = entityDesc
                    .getAttributeDesc(propertyName.substring(0, pos));
            return getTemporalType(attributeDesc.getElementType(), propertyName
                    .substring(pos + 1));
        }
        final AttributeDesc attributeDesc = entityDesc
                .getAttributeDesc(propertyName);
        final TemporalType temporalType = attributeDesc.getTemporalType();
        return temporalType != null ? temporalType : TemporalType.DATE;
    }

}
