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

import org.seasar.kuina.dao.OrderingSpec;
import org.seasar.kuina.dao.TrimSpecification;
import org.seasar.kuina.dao.criteria.grammar.AggregateExpression;
import org.seasar.kuina.dao.criteria.grammar.ArithmeticExpression;
import org.seasar.kuina.dao.criteria.grammar.ArithmeticFactor;
import org.seasar.kuina.dao.criteria.grammar.ArithmeticPrimary;
import org.seasar.kuina.dao.criteria.grammar.BetweenExpression;
import org.seasar.kuina.dao.criteria.grammar.BooleanExpression;
import org.seasar.kuina.dao.criteria.grammar.BooleanLiteral;
import org.seasar.kuina.dao.criteria.grammar.BooleanPrimary;
import org.seasar.kuina.dao.criteria.grammar.CollectionMemberExpression;
import org.seasar.kuina.dao.criteria.grammar.ComparisonExpression;
import org.seasar.kuina.dao.criteria.grammar.ConditionalExpression;
import org.seasar.kuina.dao.criteria.grammar.ConditionalFactor;
import org.seasar.kuina.dao.criteria.grammar.ConditionalPrimary;
import org.seasar.kuina.dao.criteria.grammar.ConditionalTerm;
import org.seasar.kuina.dao.criteria.grammar.DatetimeExpression;
import org.seasar.kuina.dao.criteria.grammar.DatetimePrimary;
import org.seasar.kuina.dao.criteria.grammar.EmptyCollectionComparisonExpression;
import org.seasar.kuina.dao.criteria.grammar.EntityExpression;
import org.seasar.kuina.dao.criteria.grammar.EnumExpression;
import org.seasar.kuina.dao.criteria.grammar.EnumLiteral;
import org.seasar.kuina.dao.criteria.grammar.EnumPrimary;
import org.seasar.kuina.dao.criteria.grammar.FunctionReturningDatetime;
import org.seasar.kuina.dao.criteria.grammar.FunctionReturningNumerics;
import org.seasar.kuina.dao.criteria.grammar.FunctionReturningStrings;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariable;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariableDeclaration;
import org.seasar.kuina.dao.criteria.grammar.InExpression;
import org.seasar.kuina.dao.criteria.grammar.InputParameter;
import org.seasar.kuina.dao.criteria.grammar.LikeExpression;
import org.seasar.kuina.dao.criteria.grammar.Literal;
import org.seasar.kuina.dao.criteria.grammar.NullComparisonExpression;
import org.seasar.kuina.dao.criteria.grammar.NumericLiteral;
import org.seasar.kuina.dao.criteria.grammar.OrderbyItem;
import org.seasar.kuina.dao.criteria.grammar.PathExpression;
import org.seasar.kuina.dao.criteria.grammar.SimpleArithmeticExpression;
import org.seasar.kuina.dao.criteria.grammar.SimpleEntityExpression;
import org.seasar.kuina.dao.criteria.grammar.StringExpression;
import org.seasar.kuina.dao.criteria.grammar.StringLiteral;
import org.seasar.kuina.dao.criteria.grammar.StringPrimary;
import org.seasar.kuina.dao.criteria.impl.SelectStatementImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.aggregate.Avg;
import org.seasar.kuina.dao.criteria.impl.grammar.aggregate.Count;
import org.seasar.kuina.dao.criteria.impl.grammar.aggregate.Max;
import org.seasar.kuina.dao.criteria.impl.grammar.aggregate.Min;
import org.seasar.kuina.dao.criteria.impl.grammar.aggregate.Sum;
import org.seasar.kuina.dao.criteria.impl.grammar.conditional.And;
import org.seasar.kuina.dao.criteria.impl.grammar.conditional.Or;
import org.seasar.kuina.dao.criteria.impl.grammar.declaration.IdentificationVariableDeclarationImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.BooleanLiteralImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.EnumLiteralImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.IdentificationVariableImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.InExpressionImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.InputParameterImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.NumericLiteralImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.OrderbyItemImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.Parenthesis;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.PathExpressionImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.StringLiteralImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.function.Abs;
import org.seasar.kuina.dao.criteria.impl.grammar.function.Concat;
import org.seasar.kuina.dao.criteria.impl.grammar.function.CurrentDate;
import org.seasar.kuina.dao.criteria.impl.grammar.function.CurrentTime;
import org.seasar.kuina.dao.criteria.impl.grammar.function.CurrentTimestamp;
import org.seasar.kuina.dao.criteria.impl.grammar.function.Length;
import org.seasar.kuina.dao.criteria.impl.grammar.function.Locate;
import org.seasar.kuina.dao.criteria.impl.grammar.function.Lower;
import org.seasar.kuina.dao.criteria.impl.grammar.function.Mod;
import org.seasar.kuina.dao.criteria.impl.grammar.function.Size;
import org.seasar.kuina.dao.criteria.impl.grammar.function.Sqrt;
import org.seasar.kuina.dao.criteria.impl.grammar.function.Substring;
import org.seasar.kuina.dao.criteria.impl.grammar.function.Trim;
import org.seasar.kuina.dao.criteria.impl.grammar.function.Upper;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.Addition;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.Between;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.Division;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.Equal;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.GreaterOrEqual;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.GreaterThan;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.IsEmpty;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.IsNotEmpty;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.IsNotNull;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.IsNull;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.LessOrEqual;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.LessThan;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.Like;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.MemberOf;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.Multiplication;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.Not;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.NotEqual;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.NotMemberOf;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.Subtraction;
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

    public static BooleanLiteral literal(final boolean literal) {
        return new BooleanLiteralImpl(literal);
    }

    public static StringLiteral literal(final String literal) {
        return new StringLiteralImpl(literal);
    }

    public static NumericLiteral literal(final Number literal) {
        return new NumericLiteralImpl(literal);
    }

    public static EnumLiteral literal(final Enum literal) {
        return new EnumLiteralImpl(literal);
    }

    public static StringLiteral[] literal(final String... literal) {
        final StringLiteral[] result = new StringLiteral[literal.length];
        for (int i = 0; i < result.length; ++i) {
            result[i] = literal(literal[i]);
        }
        return result;
    }

    public static NumericLiteral[] literal(final Number... literal) {
        final NumericLiteral[] result = new NumericLiteral[literal.length];
        for (int i = 0; i < result.length; ++i) {
            result[i] = literal(literal[i]);
        }
        return result;
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
            final java.sql.Date value) {
        return new InputParameterImpl(name, value);
    }

    public static DatetimePrimary parameter(final String name,
            final java.sql.Time value) {
        return new InputParameterImpl(name, value);
    }

    public static DatetimePrimary parameter(final String name,
            final java.sql.Timestamp value) {
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

    public static EnumPrimary parameter(final String name, final Enum value) {
        return new InputParameterImpl(name, value);
    }

    public static SimpleEntityExpression parameter(final String name,
            final Object value) {
        return new InputParameterImpl(name, value);
    }

    public static IdentificationVariable variable(final String variable) {
        return new IdentificationVariableImpl(variable);
    }

    public static AggregateExpression count(final String path) {
        return count(path(path));
    }

    public static AggregateExpression count(final PathExpression path) {
        return new Count(path);
    }

    public static AggregateExpression countDistinct(final String path) {
        return countDistinct(path(path));
    }

    public static AggregateExpression countDistinct(final PathExpression path) {
        return new Count(true, path);
    }

    public static AggregateExpression avg(final String path) {
        return avg(path(path));
    }

    public static AggregateExpression avg(final PathExpression path) {
        return new Avg(path);
    }

    public static AggregateExpression avgDistinct(final String path) {
        return avgDistinct(path(path));
    }

    public static AggregateExpression avgDistinct(final PathExpression path) {
        return new Avg(true, path);
    }

    public static AggregateExpression max(final String path) {
        return max(path(path));
    }

    public static AggregateExpression max(final PathExpression path) {
        return new Max(path);
    }

    public static AggregateExpression maxDistinct(final String path) {
        return maxDistinct(path(path));
    }

    public static AggregateExpression maxDistinct(final PathExpression path) {
        return new Max(true, path);
    }

    public static AggregateExpression min(final String path) {
        return min(path(path));
    }

    public static AggregateExpression min(final PathExpression path) {
        return new Min(path);
    }

    public static AggregateExpression minDistinct(final String path) {
        return minDistinct(path(path));
    }

    public static AggregateExpression minDistinct(final PathExpression path) {
        return new Min(true, path);
    }

    public static AggregateExpression sum(final String path) {
        return sum(path(path));
    }

    public static AggregateExpression sum(final PathExpression path) {
        return new Sum(path);
    }

    public static AggregateExpression sumDistinct(final String path) {
        return sumDistinct(path(path));
    }

    public static AggregateExpression sumDistinct(final PathExpression path) {
        return new Sum(true, path);
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
            final ConditionalExpression expression) {
        return new Parenthesis(expression);
    }

    public static BetweenExpression between(final String operand,
            final String from, final String to) {
        return new Between(path(operand), path(from), path(to));
    }

    public static BetweenExpression between(final String operand,
            final Number from, final Number to) {
        return between(operand, literal(from), literal(to));
    }

    public static BetweenExpression between(final String operand,
            final ArithmeticExpression from, final ArithmeticExpression to) {
        return new Between(path(operand), from, to);
    }

    public static BetweenExpression between(final String operand,
            final StringExpression from, final StringExpression to) {
        return new Between(path(operand), from, to);
    }

    public static BetweenExpression between(final String operand,
            final DatetimeExpression from, final DatetimeExpression to) {
        return new Between(path(operand), from, to);
    }

    public static LikeExpression like(final String string, final String pattern) {
        return like(path(string), path(pattern));
    }

    public static LikeExpression like(final String string,
            final StringExpression pattern) {
        return like(path(string), pattern);
    }

    public static LikeExpression like(final StringExpression string,
            final String pattern) {
        return like(string, path(pattern));
    }

    public static LikeExpression like(final StringExpression string,
            final StringExpression pattern) {
        return new Like(string, pattern);
    }

    public static LikeExpression like(final String string,
            final String pattern, final String escape) {
        return like(path(string), path(pattern), path(escape));
    }

    public static LikeExpression like(final String string,
            final String pattern, final StringExpression escape) {
        return like(path(string), path(pattern), escape);
    }

    public static LikeExpression like(final String string,
            final StringExpression pattern, final String escape) {
        return like(path(string), pattern, path(escape));
    }

    public static LikeExpression like(final String string,
            final StringExpression pattern, final StringExpression escape) {
        return like(path(string), pattern, escape);
    }

    public static LikeExpression like(final StringExpression string,
            final String pattern, final String escape) {
        return like(string, path(pattern), path(escape));
    }

    public static LikeExpression like(final StringExpression string,
            final String pattern, final StringExpression escape) {
        return like(string, path(pattern), escape);
    }

    public static LikeExpression like(final StringExpression string,
            final StringExpression pattern, final String escape) {
        return like(string, pattern, path(escape));
    }

    public static LikeExpression like(final StringExpression string,
            final StringExpression pattern, final StringExpression escape) {
        return new Like(string, pattern, escape);
    }

    public static InExpression in(final String path, final boolean... inItems) {
        final InExpression inExpression = new InExpressionImpl(path(path));
        for (final boolean inItem : inItems) {
            inExpression.add(literal(inItem));
        }
        return inExpression;
    }

    public static InExpression in(final String path, final Number... inItems) {
        final InExpression inExpression = new InExpressionImpl(path(path));
        for (final Number inItem : inItems) {
            inExpression.add(literal(inItem));
        }
        return inExpression;
    }

    public static InExpression in(final String path, final String... inItems) {
        final InExpression inExpression = new InExpressionImpl(path(path));
        for (final String inItem : inItems) {
            inExpression.add(literal(inItem));
        }
        return inExpression;
    }

    public static InExpression in(final String path, final Enum... inItems) {
        final InExpression inExpression = new InExpressionImpl(path(path));
        for (final Enum inItem : inItems) {
            inExpression.add(literal(inItem));
        }
        return inExpression;
    }

    public static InExpression in(final String path, final Literal... inItems) {
        final InExpression inExpression = new InExpressionImpl(path(path));
        for (final Literal inItem : inItems) {
            inExpression.add(inItem);
        }
        return inExpression;
    }

    public static InExpression in(final String path,
            final InputParameter... inItems) {
        final InExpression inExpression = new InExpressionImpl(path(path));
        for (final InputParameter inItem : inItems) {
            inExpression.add(inItem);
        }
        return inExpression;
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

    public static EmptyCollectionComparisonExpression isEmpty(final String path) {
        return isEmpty(path(path));
    }

    public static EmptyCollectionComparisonExpression isEmpty(
            final PathExpression pathExpression) {
        return new IsEmpty(pathExpression);
    }

    public static EmptyCollectionComparisonExpression isEmpty(
            final InputParameter inputParameter) {
        return new IsEmpty(inputParameter);
    }

    public static EmptyCollectionComparisonExpression isNotEmpty(
            final String path) {
        return isNotEmpty(path(path));
    }

    public static EmptyCollectionComparisonExpression isNotEmpty(
            final PathExpression pathExpression) {
        return new IsNotEmpty(pathExpression);
    }

    public static EmptyCollectionComparisonExpression isNotEmpty(
            final InputParameter inputParameter) {
        return new IsNotEmpty(inputParameter);
    }

    public static CollectionMemberExpression memberOf(final String lhs,
            final String rhs) {
        return memberOf(variable(lhs), path(rhs));
    }

    public static CollectionMemberExpression memberOf(final PathExpression lhs,
            final String rhs) {
        return memberOf(lhs, path(rhs));
    }

    public static CollectionMemberExpression memberOf(final PathExpression lhs,
            final PathExpression rhs) {
        return new MemberOf(lhs, rhs);
    }

    public static CollectionMemberExpression memberOf(
            final IdentificationVariable lhs, final String rhs) {
        return memberOf(lhs, path(rhs));
    }

    public static CollectionMemberExpression memberOf(
            final IdentificationVariable lhs, final PathExpression rhs) {
        return new MemberOf(lhs, rhs);
    }

    public static CollectionMemberExpression memberOf(final InputParameter lhs,
            final String rhs) {
        return memberOf(lhs, path(rhs));
    }

    public static CollectionMemberExpression memberOf(final InputParameter lhs,
            final PathExpression rhs) {
        return new MemberOf(lhs, rhs);
    }

    public static CollectionMemberExpression notMemberOf(final String lhs,
            final String rhs) {
        return notMemberOf(path(lhs), path(rhs));
    }

    public static CollectionMemberExpression notMemberOf(
            final PathExpression lhs, final String rhs) {
        return notMemberOf(lhs, path(rhs));
    }

    public static CollectionMemberExpression notMemberOf(
            final PathExpression lhs, final PathExpression rhs) {
        return new NotMemberOf(lhs, rhs);
    }

    public static CollectionMemberExpression notMemberOf(
            final IdentificationVariable lhs, final String rhs) {
        return notMemberOf(lhs, path(rhs));
    }

    public static CollectionMemberExpression notMemberOf(
            final IdentificationVariable lhs, final PathExpression rhs) {
        return new NotMemberOf(lhs, rhs);
    }

    public static CollectionMemberExpression notMemberOf(
            final InputParameter lhs, final String rhs) {
        return notMemberOf(lhs, path(rhs));
    }

    public static CollectionMemberExpression notMemberOf(
            final InputParameter lhs, final PathExpression rhs) {
        return new NotMemberOf(lhs, rhs);
    }

    public static ComparisonExpression eq(final String lhs, final String rhs) {
        return eq(path(lhs), path(rhs));
    }

    public static ComparisonExpression eq(final String lhs,
            final PathExpression rhs) {
        return eq(path(lhs), rhs);
    }

    public static ComparisonExpression eq(final PathExpression lhs,
            final String rhs) {
        return eq(lhs, path(rhs));
    }

    public static ComparisonExpression eq(final PathExpression lhs,
            final PathExpression rhs) {
        return new Equal(lhs, rhs);
    }

    public static ComparisonExpression eq(final String lhs, final Number rhs) {
        return eq(path(lhs), literal(rhs));
    }

    public static ComparisonExpression eq(final Number lhs, final String rhs) {
        return eq(literal(lhs), path(rhs));
    }

    public static ComparisonExpression eq(final String lhs,
            final ArithmeticExpression rhs) {
        return eq(path(lhs), rhs);
    }

    public static ComparisonExpression eq(final ArithmeticExpression lhs,
            final String rhs) {
        return eq(lhs, path(rhs));
    }

    public static ComparisonExpression eq(final ArithmeticExpression lhs,
            final Number rhs) {
        return eq(lhs, literal(rhs));
    }

    public static ComparisonExpression eq(final Number lhs,
            final ArithmeticExpression rhs) {
        return eq(literal(lhs), rhs);
    }

    public static ComparisonExpression eq(final ArithmeticExpression lhs,
            final ArithmeticExpression rhs) {
        return new Equal(lhs, rhs);
    }

    public static ComparisonExpression eq(final String lhs,
            final StringExpression rhs) {
        return eq(path(lhs), rhs);
    }

    public static ComparisonExpression eq(final StringExpression lhs,
            final String rhs) {
        return eq(lhs, path(rhs));
    }

    public static ComparisonExpression eq(final StringExpression lhs,
            final StringExpression rhs) {
        return new Equal(lhs, rhs);
    }

    public static ComparisonExpression eq(final String lhs,
            final DatetimeExpression rhs) {
        return eq(path(lhs), rhs);
    }

    public static ComparisonExpression eq(final DatetimeExpression lhs,
            final String rhs) {
        return eq(lhs, path(rhs));
    }

    public static ComparisonExpression eq(final DatetimeExpression lhs,
            final DatetimeExpression rhs) {
        return new Equal(lhs, rhs);
    }

    public static ComparisonExpression eq(final String lhs, final boolean rhs) {
        return eq(path(lhs), literal(rhs));
    }

    public static ComparisonExpression eq(final boolean lhs, final String rhs) {
        return eq(literal(lhs), path(rhs));
    }

    public static ComparisonExpression eq(final String lhs,
            final BooleanExpression rhs) {
        return eq(path(lhs), rhs);
    }

    public static ComparisonExpression eq(final BooleanExpression lhs,
            final String rhs) {
        return eq(lhs, path(rhs));
    }

    public static ComparisonExpression eq(final BooleanExpression lhs,
            final BooleanExpression rhs) {
        return new Equal(lhs, rhs);
    }

    public static ComparisonExpression eq(final String lhs,
            final EnumExpression rhs) {
        return eq(path(lhs), rhs);
    }

    public static ComparisonExpression eq(final EnumExpression lhs,
            final String rhs) {
        return eq(lhs, path(rhs));
    }

    public static ComparisonExpression eq(final Enum lhs,
            final EnumExpression rhs) {
        return eq(literal(lhs), rhs);
    }

    public static ComparisonExpression eq(final EnumExpression lhs,
            final Enum rhs) {
        return eq(lhs, literal(rhs));
    }

    public static ComparisonExpression eq(final EnumExpression lhs,
            final EnumExpression rhs) {
        return new Equal(lhs, rhs);
    }

    public static ComparisonExpression eq(final String lhs,
            final EntityExpression rhs) {
        return eq(path(lhs), rhs);
    }

    public static ComparisonExpression eq(final EntityExpression lhs,
            final String rhs) {
        return eq(lhs, path(rhs));
    }

    public static ComparisonExpression eq(final EntityExpression lhs,
            final EntityExpression rhs) {
        return new Equal(lhs, rhs);
    }

    public static ComparisonExpression ne(final String lhs, final String rhs) {
        return ne(path(lhs), path(rhs));
    }

    public static ComparisonExpression ne(final String lhs,
            final PathExpression rhs) {
        return ne(path(lhs), rhs);
    }

    public static ComparisonExpression ne(final PathExpression lhs,
            final String rhs) {
        return ne(lhs, path(rhs));
    }

    public static ComparisonExpression ne(final PathExpression lhs,
            final PathExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    public static ComparisonExpression ne(final String lhs, final Number rhs) {
        return ne(path(lhs), literal(rhs));
    }

    public static ComparisonExpression ne(final Number lhs, final String rhs) {
        return ne(literal(lhs), path(rhs));
    }

    public static ComparisonExpression ne(final String lhs,
            final ArithmeticExpression rhs) {
        return ne(path(lhs), rhs);
    }

    public static ComparisonExpression ne(final ArithmeticExpression lhs,
            final String rhs) {
        return ne(lhs, path(rhs));
    }

    public static ComparisonExpression ne(final ArithmeticExpression lhs,
            final Number rhs) {
        return ne(lhs, literal(rhs));
    }

    public static ComparisonExpression ne(final Number lhs,
            final ArithmeticExpression rhs) {
        return ne(literal(lhs), rhs);
    }

    public static ComparisonExpression ne(final ArithmeticExpression lhs,
            final ArithmeticExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    public static ComparisonExpression ne(final String lhs,
            final StringExpression rhs) {
        return ne(path(lhs), rhs);
    }

    public static ComparisonExpression ne(final StringExpression lhs,
            final String rhs) {
        return ne(lhs, path(rhs));
    }

    public static ComparisonExpression ne(final StringExpression lhs,
            final StringExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    public static ComparisonExpression ne(final String lhs,
            final DatetimeExpression rhs) {
        return ne(path(lhs), rhs);
    }

    public static ComparisonExpression ne(final DatetimeExpression lhs,
            final String rhs) {
        return ne(lhs, path(rhs));
    }

    public static ComparisonExpression ne(final DatetimeExpression lhs,
            final DatetimeExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    public static ComparisonExpression ne(final String lhs, final boolean rhs) {
        return ne(path(lhs), literal(rhs));
    }

    public static ComparisonExpression ne(final boolean lhs, final String rhs) {
        return ne(literal(lhs), path(rhs));
    }

    public static ComparisonExpression ne(final String lhs,
            final BooleanExpression rhs) {
        return ne(path(lhs), rhs);
    }

    public static ComparisonExpression ne(final BooleanExpression lhs,
            final String rhs) {
        return ne(lhs, path(rhs));
    }

    public static ComparisonExpression ne(final BooleanExpression lhs,
            final BooleanExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    public static ComparisonExpression ne(final String lhs,
            final EnumExpression rhs) {
        return ne(path(lhs), rhs);
    }

    public static ComparisonExpression ne(final EnumExpression lhs,
            final String rhs) {
        return ne(lhs, path(rhs));
    }

    public static ComparisonExpression ne(final Enum lhs,
            final EnumExpression rhs) {
        return ne(literal(lhs), rhs);
    }

    public static ComparisonExpression ne(final EnumExpression lhs,
            final Enum rhs) {
        return ne(lhs, literal(rhs));
    }

    public static ComparisonExpression ne(final EnumExpression lhs,
            final EnumExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    public static ComparisonExpression ne(final String lhs,
            final EntityExpression rhs) {
        return ne(path(lhs), rhs);
    }

    public static ComparisonExpression ne(final EntityExpression lhs,
            final String rhs) {
        return ne(lhs, path(rhs));
    }

    public static ComparisonExpression ne(final EntityExpression lhs,
            final EntityExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    public static ComparisonExpression gt(final String lhs, final String rhs) {
        return new GreaterThan(path(lhs), path(rhs));
    }

    public static ComparisonExpression gt(final String lhs, final Number rhs) {
        return gt(path(lhs), literal(rhs));
    }

    public static ComparisonExpression gt(final Number lhs, final String rhs) {
        return gt(literal(lhs), path(rhs));
    }

    public static ComparisonExpression gt(final String lhs,
            final ArithmeticExpression rhs) {
        return gt(path(lhs), rhs);
    }

    public static ComparisonExpression gt(final ArithmeticExpression lhs,
            final String rhs) {
        return gt(lhs, path(rhs));
    }

    public static ComparisonExpression gt(final Number lhs,
            final ArithmeticExpression rhs) {
        return gt(literal(lhs), rhs);
    }

    public static ComparisonExpression gt(final ArithmeticExpression lhs,
            final Number rhs) {
        return gt(lhs, literal(rhs));
    }

    public static ComparisonExpression gt(final ArithmeticExpression lhs,
            final ArithmeticExpression rhs) {
        return new GreaterThan(lhs, rhs);
    }

    public static ComparisonExpression gt(final String lhs,
            final StringExpression rhs) {
        return gt(path(lhs), rhs);
    }

    public static ComparisonExpression gt(final StringExpression lhs,
            final String rhs) {
        return gt(lhs, path(rhs));
    }

    public static ComparisonExpression gt(final StringExpression lhs,
            final StringExpression rhs) {
        return new GreaterThan(lhs, rhs);
    }

    public static ComparisonExpression gt(final String lhs,
            final DatetimeExpression rhs) {
        return gt(path(lhs), rhs);
    }

    public static ComparisonExpression gt(final DatetimeExpression lhs,
            final String rhs) {
        return gt(lhs, path(rhs));
    }

    public static ComparisonExpression gt(final DatetimeExpression lhs,
            final DatetimeExpression rhs) {
        return new GreaterThan(lhs, rhs);
    }

    public static ComparisonExpression ge(final String lhs, final String rhs) {
        return new GreaterOrEqual(path(lhs), path(rhs));
    }

    public static ComparisonExpression ge(final String lhs, final Number rhs) {
        return ge(path(lhs), literal(rhs));
    }

    public static ComparisonExpression ge(final Number lhs, final String rhs) {
        return ge(literal(lhs), path(rhs));
    }

    public static ComparisonExpression ge(final String lhs,
            final ArithmeticExpression rhs) {
        return ge(path(lhs), rhs);
    }

    public static ComparisonExpression ge(final ArithmeticExpression lhs,
            final String rhs) {
        return ge(lhs, path(rhs));
    }

    public static ComparisonExpression ge(final Number lhs,
            final ArithmeticExpression rhs) {
        return ge(literal(lhs), rhs);
    }

    public static ComparisonExpression ge(final ArithmeticExpression lhs,
            final Number rhs) {
        return ge(lhs, literal(rhs));
    }

    public static ComparisonExpression ge(final ArithmeticExpression lhs,
            final ArithmeticExpression rhs) {
        return new GreaterOrEqual(lhs, rhs);
    }

    public static ComparisonExpression ge(final String lhs,
            final StringExpression rhs) {
        return ge(path(lhs), rhs);
    }

    public static ComparisonExpression ge(final StringExpression lhs,
            final String rhs) {
        return ge(lhs, path(rhs));
    }

    public static ComparisonExpression ge(final StringExpression lhs,
            final StringExpression rhs) {
        return new GreaterOrEqual(lhs, rhs);
    }

    public static ComparisonExpression ge(final String lhs,
            final DatetimeExpression rhs) {
        return ge(path(lhs), rhs);
    }

    public static ComparisonExpression ge(final DatetimeExpression lhs,
            final String rhs) {
        return ge(lhs, path(rhs));
    }

    public static ComparisonExpression ge(final DatetimeExpression lhs,
            final DatetimeExpression rhs) {
        return new GreaterOrEqual(lhs, rhs);
    }

    public static ComparisonExpression lt(final String lhs, final String rhs) {
        return new LessThan(path(lhs), path(rhs));
    }

    public static ComparisonExpression lt(final String lhs, final Number rhs) {
        return lt(path(lhs), literal(rhs));
    }

    public static ComparisonExpression lt(final Number lhs, final String rhs) {
        return lt(literal(lhs), path(rhs));
    }

    public static ComparisonExpression lt(final String lhs,
            final ArithmeticExpression rhs) {
        return lt(path(lhs), rhs);
    }

    public static ComparisonExpression lt(final ArithmeticExpression lhs,
            final String rhs) {
        return lt(lhs, path(rhs));
    }

    public static ComparisonExpression lt(final Number lhs,
            final ArithmeticExpression rhs) {
        return lt(literal(lhs), rhs);
    }

    public static ComparisonExpression lt(final ArithmeticExpression lhs,
            final Number rhs) {
        return lt(lhs, literal(rhs));
    }

    public static ComparisonExpression lt(final ArithmeticExpression lhs,
            final ArithmeticExpression rhs) {
        return new LessThan(lhs, rhs);
    }

    public static ComparisonExpression lt(final String lhs,
            final StringExpression rhs) {
        return lt(path(lhs), rhs);
    }

    public static ComparisonExpression lt(final StringExpression lhs,
            final String rhs) {
        return lt(lhs, path(rhs));
    }

    public static ComparisonExpression lt(final StringExpression lhs,
            final StringExpression rhs) {
        return new LessThan(lhs, rhs);
    }

    public static ComparisonExpression lt(final String lhs,
            final DatetimeExpression rhs) {
        return lt(path(lhs), rhs);
    }

    public static ComparisonExpression lt(final DatetimeExpression lhs,
            final String rhs) {
        return lt(lhs, path(rhs));
    }

    public static ComparisonExpression lt(final DatetimeExpression lhs,
            final DatetimeExpression rhs) {
        return new LessThan(lhs, rhs);
    }

    public static ComparisonExpression le(final String lhs, final String rhs) {
        return new LessOrEqual(path(lhs), path(rhs));
    }

    public static ComparisonExpression le(final String lhs, final Number rhs) {
        return le(path(lhs), literal(rhs));
    }

    public static ComparisonExpression le(final Number lhs, final String rhs) {
        return le(literal(lhs), path(rhs));
    }

    public static ComparisonExpression le(final String lhs,
            final ArithmeticExpression rhs) {
        return le(path(lhs), rhs);
    }

    public static ComparisonExpression le(final ArithmeticExpression lhs,
            final String rhs) {
        return le(lhs, path(rhs));
    }

    public static ComparisonExpression le(final Number lhs,
            final ArithmeticExpression rhs) {
        return le(literal(lhs), rhs);
    }

    public static ComparisonExpression le(final ArithmeticExpression lhs,
            final Number rhs) {
        return le(lhs, literal(rhs));
    }

    public static ComparisonExpression le(final ArithmeticExpression lhs,
            final ArithmeticExpression rhs) {
        return new LessOrEqual(lhs, rhs);
    }

    public static ComparisonExpression le(final String lhs,
            final StringExpression rhs) {
        return le(path(lhs), rhs);
    }

    public static ComparisonExpression le(final StringExpression lhs,
            final String rhs) {
        return le(lhs, path(rhs));
    }

    public static ComparisonExpression le(final StringExpression lhs,
            final StringExpression rhs) {
        return new LessOrEqual(lhs, rhs);
    }

    public static ComparisonExpression le(final String lhs,
            final DatetimeExpression rhs) {
        return le(path(lhs), rhs);
    }

    public static ComparisonExpression le(final DatetimeExpression lhs,
            final String rhs) {
        return le(lhs, path(rhs));
    }

    public static ComparisonExpression le(final DatetimeExpression lhs,
            final DatetimeExpression rhs) {
        return new LessOrEqual(lhs, rhs);
    }

    public static ArithmeticFactor plus(final String operand) {
        return plus(path(operand));
    }

    public static ArithmeticFactor plus(final ArithmeticPrimary operand) {
        return new UnaryPlus(operand);
    }

    public static ArithmeticFactor minus(final String operand) {
        return minus(path(operand));
    }

    public static ArithmeticFactor minus(final ArithmeticPrimary operand) {
        return new UnaryMinus(operand);
    }

    public static SimpleArithmeticExpression add(final String lhs,
            final String rhs) {
        return add(path(lhs), path(rhs));
    }

    public static SimpleArithmeticExpression add(final String lhs,
            final Number rhs) {
        return add(path(lhs), literal(rhs));
    }

    public static SimpleArithmeticExpression add(final Number lhs,
            final String rhs) {
        return add(literal(lhs), path(rhs));
    }

    public static SimpleArithmeticExpression add(final Number lhs,
            final Number rhs) {
        return add(literal(lhs), literal(rhs));
    }

    public static SimpleArithmeticExpression add(final String lhs,
            final SimpleArithmeticExpression rhs) {
        return add(path(lhs), rhs);
    }

    public static SimpleArithmeticExpression add(
            final SimpleArithmeticExpression lhs, final String rhs) {
        return add(lhs, path(rhs));
    }

    public static SimpleArithmeticExpression add(final Number lhs,
            final SimpleArithmeticExpression rhs) {
        return add(literal(lhs), rhs);
    }

    public static SimpleArithmeticExpression add(
            final SimpleArithmeticExpression lhs, final Number rhs) {
        return add(lhs, literal(rhs));
    }

    public static SimpleArithmeticExpression add(
            final SimpleArithmeticExpression lhs,
            final SimpleArithmeticExpression rhs) {
        return new Addition(lhs, rhs);
    }

    public static SimpleArithmeticExpression subtract(final String lhs,
            final String rhs) {
        return subtract(path(lhs), path(rhs));
    }

    public static SimpleArithmeticExpression subtract(final String lhs,
            final Number rhs) {
        return subtract(path(lhs), literal(rhs));
    }

    public static SimpleArithmeticExpression subtract(final Number lhs,
            final String rhs) {
        return subtract(literal(lhs), path(rhs));
    }

    public static SimpleArithmeticExpression subtract(final Number lhs,
            final Number rhs) {
        return subtract(literal(lhs), literal(rhs));
    }

    public static SimpleArithmeticExpression subtract(final String lhs,
            final SimpleArithmeticExpression rhs) {
        return subtract(path(lhs), rhs);
    }

    public static SimpleArithmeticExpression subtract(
            final SimpleArithmeticExpression lhs, final String rhs) {
        return subtract(lhs, path(rhs));
    }

    public static SimpleArithmeticExpression subtract(final Number lhs,
            final SimpleArithmeticExpression rhs) {
        return subtract(literal(lhs), rhs);
    }

    public static SimpleArithmeticExpression subtract(
            final SimpleArithmeticExpression lhs, final Number rhs) {
        return subtract(lhs, literal(rhs));
    }

    public static SimpleArithmeticExpression subtract(
            final SimpleArithmeticExpression lhs,
            final SimpleArithmeticExpression rhs) {
        return new Subtraction(lhs, rhs);
    }

    public static SimpleArithmeticExpression multiply(final String lhs,
            final String rhs) {
        return multiply(path(lhs), path(rhs));
    }

    public static SimpleArithmeticExpression multiply(final String lhs,
            final Number rhs) {
        return multiply(path(lhs), literal(rhs));
    }

    public static SimpleArithmeticExpression multiply(final Number lhs,
            final String rhs) {
        return multiply(literal(lhs), path(rhs));
    }

    public static SimpleArithmeticExpression multiply(final Number lhs,
            final Number rhs) {
        return multiply(literal(lhs), literal(rhs));
    }

    public static SimpleArithmeticExpression multiply(final String lhs,
            final SimpleArithmeticExpression rhs) {
        return multiply(path(lhs), rhs);
    }

    public static SimpleArithmeticExpression multiply(
            final SimpleArithmeticExpression lhs, final String rhs) {
        return multiply(lhs, path(rhs));
    }

    public static SimpleArithmeticExpression multiply(final Number lhs,
            final SimpleArithmeticExpression rhs) {
        return multiply(literal(lhs), rhs);
    }

    public static SimpleArithmeticExpression multiply(
            final SimpleArithmeticExpression lhs, final Number rhs) {
        return multiply(lhs, literal(rhs));
    }

    public static SimpleArithmeticExpression multiply(
            final SimpleArithmeticExpression lhs,
            final SimpleArithmeticExpression rhs) {
        return new Multiplication(lhs, rhs);
    }

    public static SimpleArithmeticExpression divide(final String lhs,
            final String rhs) {
        return divide(path(lhs), path(rhs));
    }

    public static SimpleArithmeticExpression divide(final String lhs,
            final Number rhs) {
        return divide(path(lhs), literal(rhs));
    }

    public static SimpleArithmeticExpression divide(final Number lhs,
            final String rhs) {
        return divide(literal(lhs), path(rhs));
    }

    public static SimpleArithmeticExpression divide(final Number lhs,
            final Number rhs) {
        return divide(literal(lhs), literal(rhs));
    }

    public static SimpleArithmeticExpression divide(final String lhs,
            final SimpleArithmeticExpression rhs) {
        return divide(path(lhs), rhs);
    }

    public static SimpleArithmeticExpression divide(
            final SimpleArithmeticExpression lhs, final String rhs) {
        return divide(lhs, path(rhs));
    }

    public static SimpleArithmeticExpression divide(final Number lhs,
            final SimpleArithmeticExpression rhs) {
        return divide(literal(lhs), rhs);
    }

    public static SimpleArithmeticExpression divide(
            final SimpleArithmeticExpression lhs, final Number rhs) {
        return divide(lhs, literal(rhs));
    }

    public static SimpleArithmeticExpression divide(
            final SimpleArithmeticExpression lhs,
            final SimpleArithmeticExpression rhs) {
        return new Division(lhs, rhs);
    }

    public static FunctionReturningNumerics length(final String string) {
        return length(path(string));
    }

    public static FunctionReturningNumerics length(final StringPrimary string) {
        return new Length(string);
    }

    public static FunctionReturningNumerics locate(final String located,
            final String searched) {
        return locate(path(located), path(searched));
    }

    public static FunctionReturningNumerics locate(final String located,
            final StringPrimary searched) {
        return locate(path(located), searched);
    }

    public static FunctionReturningNumerics locate(final StringPrimary located,
            final String searched) {
        return locate(located, path(searched));
    }

    public static FunctionReturningNumerics locate(final StringPrimary located,
            final StringPrimary searched) {
        return new Locate(located, searched);
    }

    public static FunctionReturningNumerics locate(final String located,
            final String searched, final int start) {
        return locate(path(located), path(searched), literal(start));
    }

    public static FunctionReturningNumerics locate(final StringPrimary located,
            final StringPrimary searched, final SimpleArithmeticExpression start) {
        return new Locate(located, searched, start);
    }

    public static FunctionReturningNumerics abs(final String number) {
        return abs(path(number));
    }

    public static FunctionReturningNumerics abs(
            final SimpleArithmeticExpression number) {
        return new Abs(number);
    }

    public static FunctionReturningNumerics sqrt(final String number) {
        return sqrt(path(number));
    }

    public static FunctionReturningNumerics sqrt(
            final SimpleArithmeticExpression number) {
        return new Sqrt(number);
    }

    public static FunctionReturningNumerics mod(final String lhs,
            final String rhs) {
        return mod(path(lhs), path(rhs));
    }

    public static FunctionReturningNumerics mod(final String lhs,
            final Number rhs) {
        return mod(path(lhs), literal(rhs));
    }

    public static FunctionReturningNumerics mod(final Number lhs,
            final String rhs) {
        return mod(literal(lhs), path(rhs));
    }

    public static FunctionReturningNumerics mod(final Number lhs,
            final Number rhs) {
        return mod(literal(lhs), literal(rhs));
    }

    public static FunctionReturningNumerics mod(final String lhs,
            final SimpleArithmeticExpression rhs) {
        return mod(path(lhs), rhs);
    }

    public static FunctionReturningNumerics mod(
            final SimpleArithmeticExpression lhs, final String rhs) {
        return mod(lhs, path(rhs));
    }

    public static FunctionReturningNumerics mod(final Number lhs,
            final SimpleArithmeticExpression rhs) {
        return mod(literal(lhs), rhs);
    }

    public static FunctionReturningNumerics mod(
            final SimpleArithmeticExpression lhs, final Number rhs) {
        return mod(lhs, literal(rhs));
    }

    public static FunctionReturningNumerics mod(
            final SimpleArithmeticExpression lhs,
            final SimpleArithmeticExpression rhs) {
        return new Mod(lhs, rhs);
    }

    public static FunctionReturningNumerics size(final String collection) {
        return size(path(collection));
    }

    public static FunctionReturningNumerics size(final PathExpression collection) {
        return new Size(collection);
    }

    public static FunctionReturningDatetime currentDate() {
        return new CurrentDate();
    }

    public static FunctionReturningDatetime currentTime() {
        return new CurrentTime();
    }

    public static FunctionReturningDatetime currentTimestamp() {
        return new CurrentTimestamp();
    }

    public static FunctionReturningStrings concat(final String string1,
            final String string2) {
        return concat(path(string1), path(string2));
    }

    public static FunctionReturningStrings concat(final StringPrimary string1,
            final StringPrimary string2) {
        return new Concat(string1, string2);
    }

    public static FunctionReturningStrings substring(final String string,
            final Number start, final Number length) {
        return new Substring(path(string), literal(start), literal(length));
    }

    public static FunctionReturningStrings substring(final String string,
            final SimpleArithmeticExpression start,
            final SimpleArithmeticExpression length) {
        return new Substring(path(string), start, length);
    }

    public static FunctionReturningStrings substring(
            final StringPrimary string, final Number start, final Number length) {
        return new Substring(string, literal(start), literal(length));
    }

    public static FunctionReturningStrings substring(
            final StringPrimary string, final SimpleArithmeticExpression start,
            final SimpleArithmeticExpression length) {
        return new Substring(string, start, length);
    }

    public static FunctionReturningStrings trim(final String trimSource) {
        return trim(path(trimSource));
    }

    public static FunctionReturningStrings trim(final StringPrimary trimSource) {
        return new Trim(trimSource);
    }

    public static FunctionReturningStrings trim(
            final TrimSpecification trimSpecification, final String trimSource) {
        return trim(trimSpecification, path(trimSource));
    }

    public static FunctionReturningStrings trim(
            final TrimSpecification trimSpecification,
            final StringPrimary trimSource) {
        return new Trim(trimSpecification, trimSource);
    }

    public static FunctionReturningStrings trim(final char trimCharacter,
            final String trimSource) {
        return trim(trimCharacter, path(trimSource));
    }

    public static FunctionReturningStrings trim(final char trimCharacter,
            final StringPrimary trimSource) {
        return new Trim(trimCharacter, trimSource);
    }

    public static FunctionReturningStrings trim(
            final TrimSpecification trimSpecification,
            final char trimCharacter, final String trimSource) {
        return trim(trimSpecification, trimCharacter, path(trimSource));
    }

    public static FunctionReturningStrings trim(
            final TrimSpecification trimSpecification,
            final char trimCharacter, final StringPrimary trimSource) {
        return new Trim(trimSpecification, trimCharacter, trimSource);
    }

    public static FunctionReturningStrings lower(final String source) {
        return lower(path(source));
    }

    public static FunctionReturningStrings lower(final StringPrimary source) {
        return new Lower(source);
    }

    public static FunctionReturningStrings upper(final String source) {
        return upper(path(source));
    }

    public static FunctionReturningStrings upper(final StringPrimary source) {
        return new Upper(source);
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
        return new OrderbyItemImpl(path, OrderingSpec.DESC);
    }
}
