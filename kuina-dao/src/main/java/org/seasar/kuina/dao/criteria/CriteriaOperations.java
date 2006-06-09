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
package org.seasar.kuina.dao.criteria;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.TemporalType;

import org.seasar.kuina.dao.criteria.grammar.AggregateExpression;
import org.seasar.kuina.dao.criteria.grammar.ArithmeticExpression;
import org.seasar.kuina.dao.criteria.grammar.ArithmeticFactor;
import org.seasar.kuina.dao.criteria.grammar.ArithmeticPrimary;
import org.seasar.kuina.dao.criteria.grammar.ArithmeticTerm;
import org.seasar.kuina.dao.criteria.grammar.BetweenExpression;
import org.seasar.kuina.dao.criteria.grammar.BooleanExpression;
import org.seasar.kuina.dao.criteria.grammar.BooleanPrimary;
import org.seasar.kuina.dao.criteria.grammar.ComparisonExpression;
import org.seasar.kuina.dao.criteria.grammar.ConditionalExpression;
import org.seasar.kuina.dao.criteria.grammar.ConditionalFactor;
import org.seasar.kuina.dao.criteria.grammar.ConditionalPrimary;
import org.seasar.kuina.dao.criteria.grammar.ConditionalTerm;
import org.seasar.kuina.dao.criteria.grammar.DatetimeExpression;
import org.seasar.kuina.dao.criteria.grammar.DatetimePrimary;
import org.seasar.kuina.dao.criteria.grammar.EntityExpression;
import org.seasar.kuina.dao.criteria.grammar.EnumExpression;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariable;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariableDeclaration;
import org.seasar.kuina.dao.criteria.grammar.InputParameter;
import org.seasar.kuina.dao.criteria.grammar.LikeExpression;
import org.seasar.kuina.dao.criteria.grammar.NullComparisonExpression;
import org.seasar.kuina.dao.criteria.grammar.NumericLiteral;
import org.seasar.kuina.dao.criteria.grammar.OrderbyItem;
import org.seasar.kuina.dao.criteria.grammar.PathExpression;
import org.seasar.kuina.dao.criteria.grammar.SimpleArithmeticExpression;
import org.seasar.kuina.dao.criteria.grammar.StringExpression;
import org.seasar.kuina.dao.criteria.grammar.StringLiteral;
import org.seasar.kuina.dao.criteria.grammar.StringPrimary;
import org.seasar.kuina.dao.criteria.impl.SelectStatementImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.aggregate.Avg;
import org.seasar.kuina.dao.criteria.impl.grammar.aggregate.Max;
import org.seasar.kuina.dao.criteria.impl.grammar.aggregate.Min;
import org.seasar.kuina.dao.criteria.impl.grammar.clause.IdentificationVariableDeclarationImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.conditional.And;
import org.seasar.kuina.dao.criteria.impl.grammar.conditional.Or;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.IdentificationVariableImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.InputParameterImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.NumericLiteralImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.OrderbyItemImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.Parenthesis;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.PathExpressionImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.StringLiteralImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.Between;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.BinaryMinus;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.BinaryPlus;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.Division;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.Equal;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.GreaterOrEqual;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.GreaterThan;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.IsNotNull;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.IsNull;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.LessOrEqual;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.LessThan;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.Like;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.Multiplication;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.Not;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.NotEqual;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.UnaryMinus;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.UnaryPlus;

/**
 * 
 * @author koichik
 */
public abstract class CriteriaOperations {
    public static SelectStatement select() {
        return new SelectStatementImpl();
    }

    public static SelectStatement select(final Object... selectExpressions) {
        return new SelectStatementImpl().select(selectExpressions);
    }

    public static SelectStatement selectDistinct() {
        return new SelectStatementImpl(true);
    }

    public static SelectStatement selectDistinct(
            final Object... selectExpressions) {
        return new SelectStatementImpl(true).select(selectExpressions);
    }

    public static PathExpression path(final String path) {
        return new PathExpressionImpl(path);
    }

    public static StringLiteral literal(final String literal) {
        return new StringLiteralImpl(literal);
    }

    public static NumericLiteral literal(final Number literal) {
        return new NumericLiteralImpl(literal);
    }

    public static ArithmeticPrimary parameter(final String name,
            final Number value) {
        return new InputParameterImpl(name, value);
    }

    public static StringPrimary parameter(final String name, final String value) {
        return new InputParameterImpl(name, value);
    }

    public static BooleanPrimary parameter(final String name,
            final boolean value) {
        return new InputParameterImpl(name, value);
    }

    public static DatetimePrimary parameter(final String name,
            final Date value, final TemporalType temporalType) {
        return new InputParameterImpl(name, value, temporalType);
    }

    public static DatetimePrimary parameter(final String name,
            final Calendar value, final TemporalType temporalType) {
        return new InputParameterImpl(name, value, temporalType);
    }

    public static IdentificationVariable variable(final String variable) {
        return new IdentificationVariableImpl(variable);
    }

    public static AggregateExpression avg(final String path) {
        return avg(path(path));
    }

    public static AggregateExpression avg(final PathExpression path) {
        return new Avg(path);
    }

    public static AggregateExpression max(final String path) {
        return max(path(path));
    }

    public static AggregateExpression max(final PathExpression path) {
        return new Max(path);
    }

    public static AggregateExpression min(final String path) {
        return min(path(path));
    }

    public static AggregateExpression min(final PathExpression path) {
        return new Min(path);
    }

    public static IdentificationVariableDeclaration alias(
            final Class<?> entityClass, final String alias) {
        return new IdentificationVariableDeclarationImpl(entityClass,
                variable(alias));
    }

    public static IdentificationVariableDeclaration join(
            final Class<?> joinedClass) {
        return new IdentificationVariableDeclarationImpl(joinedClass);
    }

    public static IdentificationVariableDeclaration join(
            final Class<?> joinedClass, final String alias) {
        return new IdentificationVariableDeclarationImpl(joinedClass,
                variable(alias));
    }

    public static ConditionalExpression or(
            final ConditionalExpression... expressions) {
        return new Or(expressions);
    }

    public static ConditionalTerm and(
            final ConditionalExpression... expressions) {
        return new And(expressions);
    }

    public static ConditionalFactor not(final ConditionalPrimary primary) {
        return new Not(primary);
    }

    public static ConditionalPrimary parenthesis(
            ConditionalExpression expression) {
        return new Parenthesis(expression);
    }

    public static Between between(final String operand, final String from,
            final String to) {
        return between(operand, literal(from), literal(to));
    }

    public static Between between(final String operand, final Number from,
            final Number to) {
        return between(operand, literal(from), literal(to));
    }

    public static Between between(final String operand,
            final ArithmeticExpression from, final ArithmeticExpression to) {
        return new Between(path(operand), from, to);
    }

    public static Between between(final String operand,
            final StringExpression from, final StringExpression to) {
        return new Between(path(operand), from, to);
    }

    public static BetweenExpression between(final String operand,
            final DatetimeExpression from, final DatetimeExpression to) {
        return new Between(path(operand), from, to);
    }

    public static LikeExpression like(final String string, final String pattern) {
        return new Like(path(string), literal(pattern));
    }

    public static LikeExpression like(final String string,
            final String pattern, final String escape) {
        return new Like(path(string), literal(pattern), literal(escape));
    }

    public static LikeExpression like(final String string,
            final StringExpression pattern) {
        return new Like(path(string), pattern);
    }

    public static LikeExpression like(final String string,
            final StringExpression pattern, final StringExpression escape) {
        return new Like(path(string), pattern, escape);
    }

    public static NullComparisonExpression isNull(final String path) {
        return isNull(path(path));
    }

    public static NullComparisonExpression isNull(
            final PathExpression pathExpression) {
        return new IsNull(pathExpression);
    }

    public static NullComparisonExpression isNull(
            final InputParameter inputParameter) {
        return new IsNull(inputParameter);
    }

    public static NullComparisonExpression isNotNull(final String path) {
        return isNotNull(path(path));
    }

    public static NullComparisonExpression isNotNull(
            final PathExpression pathExpression) {
        return new IsNotNull(pathExpression);
    }

    public static NullComparisonExpression isNotNull(
            final InputParameter inputParameter) {
        return new IsNotNull(inputParameter);
    }

    public static ComparisonExpression eq(final String lhs, final String rhs) {
        return eq(path(lhs), literal(rhs));
    }

    public static ComparisonExpression eq(final String lhs, final Number rhs) {
        return eq(path(lhs), literal(rhs));
    }

    public static ComparisonExpression eq(final StringExpression lhs,
            final StringExpression rhs) {
        return new Equal(lhs, rhs);
    }

    public static ComparisonExpression eq(final BooleanExpression lhs,
            final BooleanExpression rhs) {
        return new Equal(lhs, rhs);
    }

    public static ComparisonExpression eq(final EnumExpression lhs,
            final EnumExpression rhs) {
        return new Equal(lhs, rhs);
    }

    public static ComparisonExpression eq(final DatetimeExpression lhs,
            final DatetimeExpression rhs) {
        return new Equal(lhs, rhs);
    }

    public static ComparisonExpression eq(final EntityExpression lhs,
            final EntityExpression rhs) {
        return new Equal(lhs, rhs);
    }

    public static ComparisonExpression eq(final ArithmeticExpression lhs,
            final ArithmeticExpression rhs) {
        return new Equal(lhs, rhs);
    }

    public static ComparisonExpression ne(final String lhs, final String rhs) {
        return ne(path(lhs), literal(rhs));
    }

    public static ComparisonExpression ne(final String lhs, final Number rhs) {
        return ne(path(lhs), literal(rhs));
    }

    public static ComparisonExpression ne(final StringExpression lhs,
            final StringExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    public static ComparisonExpression ne(final BooleanExpression lhs,
            final BooleanExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    public static ComparisonExpression ne(final EnumExpression lhs,
            final EnumExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    public static ComparisonExpression ne(final DatetimeExpression lhs,
            final DatetimeExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    public static ComparisonExpression ne(final EntityExpression lhs,
            final EntityExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    public static ComparisonExpression ne(final ArithmeticExpression lhs,
            final ArithmeticExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    public static ComparisonExpression gt(final String lhs, final String rhs) {
        return gt(path(lhs), literal(rhs));
    }

    public static ComparisonExpression gt(final String lhs, final Number rhs) {
        return gt(path(lhs), literal(rhs));
    }

    public static ComparisonExpression gt(final StringExpression lhs,
            final StringExpression rhs) {
        return new GreaterThan(lhs, rhs);
    }

    public static ComparisonExpression gt(final ArithmeticExpression lhs,
            final ArithmeticExpression rhs) {
        return new GreaterThan(lhs, rhs);
    }

    public static ComparisonExpression gt(final DatetimeExpression lhs,
            final DatetimeExpression rhs) {
        return new GreaterThan(lhs, rhs);
    }

    public static ComparisonExpression ge(final String lhs, final String rhs) {
        return ge(path(lhs), literal(rhs));
    }

    public static ComparisonExpression ge(final String lhs, final Number rhs) {
        return ge(path(lhs), literal(rhs));
    }

    public static ComparisonExpression ge(final StringExpression lhs,
            final StringExpression rhs) {
        return new GreaterOrEqual(lhs, rhs);
    }

    public static ComparisonExpression ge(final ArithmeticExpression lhs,
            final ArithmeticExpression rhs) {
        return new GreaterOrEqual(lhs, rhs);
    }

    public static ComparisonExpression ge(final DatetimeExpression lhs,
            final DatetimeExpression rhs) {
        return new GreaterOrEqual(lhs, rhs);
    }

    public static ComparisonExpression lt(final String lhs, final String rhs) {
        return lt(path(lhs), literal(rhs));
    }

    public static ComparisonExpression lt(final String lhs, final Number rhs) {
        return lt(path(lhs), literal(rhs));
    }

    public static ComparisonExpression lt(final StringExpression lhs,
            final StringExpression rhs) {
        return new LessThan(lhs, rhs);
    }

    public static ComparisonExpression lt(final ArithmeticExpression lhs,
            final ArithmeticExpression rhs) {
        return new LessThan(lhs, rhs);
    }

    public static ComparisonExpression lt(final DatetimeExpression lhs,
            final DatetimeExpression rhs) {
        return new LessThan(lhs, rhs);
    }

    public static ComparisonExpression le(final String lhs, final String rhs) {
        return le(path(lhs), literal(rhs));
    }

    public static ComparisonExpression le(final String lhs, final Number rhs) {
        return le(path(lhs), literal(rhs));
    }

    public static ComparisonExpression le(final StringExpression lhs,
            final StringExpression rhs) {
        return new LessOrEqual(lhs, rhs);
    }

    public static ComparisonExpression le(final ArithmeticExpression lhs,
            final ArithmeticExpression rhs) {
        return new LessOrEqual(lhs, rhs);
    }

    public static ComparisonExpression le(final DatetimeExpression lhs,
            final DatetimeExpression rhs) {
        return new LessOrEqual(lhs, rhs);
    }

    public static ArithmeticFactor plus(final String operand) {
        return new UnaryPlus(path(operand));
    }

    public static ArithmeticFactor plus(final ArithmeticPrimary operand) {
        return new UnaryPlus(operand);
    }

    public static ArithmeticFactor minus(final String operand) {
        return new UnaryMinus(path(operand));
    }

    public static ArithmeticFactor minus(final ArithmeticPrimary operand) {
        return new UnaryMinus(operand);
    }

    public static SimpleArithmeticExpression add(
            final SimpleArithmeticExpression lhs,
            final SimpleArithmeticExpression rhs) {
        return new BinaryPlus(lhs, rhs);
    }

    public static SimpleArithmeticExpression subtract(
            final SimpleArithmeticExpression lhs,
            final SimpleArithmeticExpression rhs) {
        return new BinaryMinus(lhs, rhs);
    }

    public static ArithmeticTerm multiply(final SimpleArithmeticExpression lhs,
            final SimpleArithmeticExpression rhs) {
        return new Multiplication(lhs, rhs);
    }

    public static ArithmeticTerm divide(final SimpleArithmeticExpression lhs,
            final SimpleArithmeticExpression rhs) {
        return new Division(lhs, rhs);
    }

    public static OrderbyItem asc(final String path) {
        return asc(path(path));
    }

    public static OrderbyItem asc(final PathExpression path) {
        return new OrderbyItemImpl(path);
    }

    public static OrderbyItem desc(final String path) {
        return desc(path(path));
    }

    public static OrderbyItem desc(final PathExpression path) {
        return new OrderbyItemImpl(path, true);
    }
}
