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
package org.seasar.kuina.dao.criteria;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.TemporalType;

import org.seasar.kuina.dao.OrderingSpec;
import org.seasar.kuina.dao.TrimSpecification;
import org.seasar.kuina.dao.criteria.grammar.AggregateExpression;
import org.seasar.kuina.dao.criteria.grammar.AllOrAnyExpression;
import org.seasar.kuina.dao.criteria.grammar.ArithmeticExpression;
import org.seasar.kuina.dao.criteria.grammar.ArithmeticFactor;
import org.seasar.kuina.dao.criteria.grammar.ArithmeticPrimary;
import org.seasar.kuina.dao.criteria.grammar.ArithmeticTerm;
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
import org.seasar.kuina.dao.criteria.grammar.ExistsExpression;
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
import org.seasar.kuina.dao.criteria.grammar.Subquery;
import org.seasar.kuina.dao.criteria.impl.SelectStatementImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.aggregate.Avg;
import org.seasar.kuina.dao.criteria.impl.grammar.aggregate.Count;
import org.seasar.kuina.dao.criteria.impl.grammar.aggregate.Max;
import org.seasar.kuina.dao.criteria.impl.grammar.aggregate.Min;
import org.seasar.kuina.dao.criteria.impl.grammar.aggregate.Sum;
import org.seasar.kuina.dao.criteria.impl.grammar.conditional.And;
import org.seasar.kuina.dao.criteria.impl.grammar.conditional.Or;
import org.seasar.kuina.dao.criteria.impl.grammar.declaration.IdentificationVariableDeclarationImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.AllExpressionImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.AnyExpressionImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.BooleanLiteralImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.EnumLiteralImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.ExistsExpressionImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.IdentificationVariableImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.InExpressionImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.InputParameterImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.NotExistsExpressionImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.NotInExpressionImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.NumericLiteralImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.OrderbyItemImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.Parenthesis;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.PathExpressionImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.SomeExpressionImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.StringLiteralImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.SubqueryImpl;
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
import org.seasar.kuina.dao.criteria.impl.grammar.operator.NotLike;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.NotMemberOf;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.Subtraction;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.UnaryMinus;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.UnaryPlus;

/**
 * JPQLを構築するための操作を定義したクラスです．
 * <p>
 * このクラスはメソッドを<code>static import</code>して使われることを想定しています．
 * </p>
 * <p>
 * 以下に例を示します．太字になっているのが本クラスの提供するメソッドです．
 * </p>
 * 
 * <pre>
 * List&lt;Employee&gt; list = 
 *     <b>select()</b>.from(Employee.class).getResultList(em);
 * 
 * List&lt;Employee&gt; list = 
 *     <b>select("e")</b>.from(<b>join(Employee.class, "e")</b>.inner("e.department", "")).getResultList(em);
 * 
 * Long result = 
 *     <b>select(count("e"))</b>.from(Employee.class, "e").getSingleResult(em);
 * 
 * List&lt;Employee&gt; list = 
 *     <b>select()</b>.from(Employee.class, "e")
 *             .where(<b>between("e.height", 150, 170)</b>,
 *                    <b>or(lt("e.weight", 45), gt("e.weight", 70))</b>)
 *             .getResultList(em);
 * </pre>
 * 
 * @author koichik
 */
public abstract class CriteriaOperations {

    private CriteriaOperations() {
    }

    /**
     * select_statementを作成します．
     * 
     * @return select_statement
     */
    public static SelectStatement select() {
        return new SelectStatementImpl();
    }

    /**
     * select_expressionを指定してselect_statementを作成します．
     * 
     * @param selectExpressions
     *            select_expressionの並び
     * @return select_statement
     */
    public static SelectStatement select(final Object... selectExpressions) {
        return select().select(selectExpressions);
    }

    /**
     * DISTINCT付きのselect_statementを作成します．
     * 
     * @return select_sattement
     */
    public static SelectStatement selectDistinct() {
        return new SelectStatementImpl(true);
    }

    /**
     * select_expressionを指定してDISTINCT付きのselect_statementを作成します．
     * 
     * @param selectExpressions
     *            select_expressionの並び
     * @return select_statement
     */
    public static SelectStatement selectDistinct(
            final Object... selectExpressions) {
        return selectDistinct().select(selectExpressions);
    }

    /**
     * subqueryを作成します．
     * 
     * @return subquery
     */
    public static Subquery subselect() {
        return new SubqueryImpl();
    }

    /**
     * select_expressionを指定してsubqueryを作成します．
     * 
     * @param selectExpressions
     *            select_expressionの並び
     * @return subquery
     */
    public static Subquery subselect(final Object... selectExpressions) {
        return subselect().select(selectExpressions);
    }

    /**
     * DISTINCT付きのsubqueryを作成します．
     * 
     * @return subquery
     */
    public static Subquery subselectDistinct() {
        return new SubqueryImpl(true);
    }

    /**
     * select_expressionを指定してDISTINCT付きのsubqueryを作成します．
     * 
     * @param selectExpressions
     *            select_expressionの並び
     * @return subquery
     */
    public static Subquery subselectDistinct(final Object... selectExpressions) {
        return subselectDistinct().select(selectExpressions);
    }

    /**
     * 引数で指定されたパス式を持つpath_expressionを作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @return path_expression
     */
    public static PathExpression path(final String path) {
        return new PathExpressionImpl(path);
    }

    /**
     * 引数で指定された値を持つboolean_literalを作成します．
     * 
     * @param literal
     *            <code>boolean</code>
     * @return boolean_expression
     */
    public static BooleanLiteral literal(final boolean literal) {
        return new BooleanLiteralImpl(literal);
    }

    /**
     * 引数で指定された値を持つstring_literalを作成します．
     * 
     * @param literal
     *            文字列
     * @return string_expression
     */
    public static StringLiteral literal(final String literal) {
        return new StringLiteralImpl(literal);
    }

    /**
     * 引数で指定された値を持つnumeric_literalを作成します．
     * 
     * @param literal
     *            <code>Number</code>
     * @return numeric_literal
     */
    public static NumericLiteral literal(final Number literal) {
        return new NumericLiteralImpl(literal);
    }

    /**
     * 引数で指定された値を持つenum_literalを作成します．
     * 
     * @param literal
     *            <code>Enum</code>
     * @return enum_literal
     */
    public static EnumLiteral literal(final Enum<?> literal) {
        return new EnumLiteralImpl(literal);
    }

    /**
     * 引数で指定された値を持つstring_literalの配列を作成します．
     * 
     * @param literal
     *            文字列の並び
     * @return string_literalの配列
     */
    public static StringLiteral[] literal(final String... literal) {
        final StringLiteral[] result = new StringLiteral[literal.length];
        for (int i = 0; i < result.length; ++i) {
            result[i] = literal(literal[i]);
        }
        return result;
    }

    /**
     * 引数で指定された値を持つnumeric_literalの配列を作成します．
     * 
     * @param literal
     *            <code>Number</code>の並び
     * @return numeric_literalの配列
     */
    public static NumericLiteral[] literal(final Number... literal) {
        final NumericLiteral[] result = new NumericLiteral[literal.length];
        for (int i = 0; i < result.length; ++i) {
            result[i] = literal(literal[i]);
        }
        return result;
    }

    /**
     * 引数で指定された名前と値を持つinput_parameterを作成します．
     * 
     * @param name
     *            パラメータの名前
     * @param value
     *            パラメータの値
     * @return aritumetic_primary
     */
    public static ArithmeticPrimary parameter(final String name,
            final Number value) {
        return new InputParameterImpl(name, value);
    }

    /**
     * 引数で指定された名前と値を持つinput_parameterを作成します．
     * 
     * @param name
     *            パラメータの名前
     * @param value
     *            パラメータの値
     * @return string_primary
     */
    public static StringPrimary parameter(final String name, final String value) {
        return new InputParameterImpl(name, value);
    }

    /**
     * 引数で指定された名前と値を持つinput_parameterを作成します．
     * 
     * @param name
     *            パラメータの名前
     * @param value
     *            パラメータの値
     * @return boolean_primary
     */
    public static BooleanPrimary parameter(final String name,
            final boolean value) {
        return new InputParameterImpl(name, value);
    }

    /**
     * 引数で指定された名前と値を持つinput_parameterを作成します．
     * 
     * @param name
     *            パラメータの名前
     * @param value
     *            パラメータの値
     * @return datetime_primary
     */
    public static DatetimePrimary parameter(final String name,
            final java.sql.Date value) {
        return new InputParameterImpl(name, value);
    }

    /**
     * 引数で指定された名前と値を持つinput_parameterを作成します．
     * 
     * @param name
     *            パラメータの名前
     * @param value
     *            パラメータの値
     * @return datetime_primary
     */
    public static DatetimePrimary parameter(final String name,
            final java.sql.Time value) {
        return new InputParameterImpl(name, value);
    }

    /**
     * 引数で指定された名前と値を持つinput_parameterを作成します．
     * 
     * @param name
     *            パラメータの名前
     * @param value
     *            パラメータの値
     * @return datetime_primary
     */
    public static DatetimePrimary parameter(final String name,
            final java.sql.Timestamp value) {
        return new InputParameterImpl(name, value);
    }

    /**
     * 引数で指定された名前と値を持つinput_parameterを作成します．
     * 
     * @param name
     *            パラメータの名前
     * @param value
     *            パラメータの値
     * @param temporalType
     *            パラメータの時制
     * @return datetime_primary
     */
    public static DatetimePrimary parameter(final String name,
            final Date value, final TemporalType temporalType) {
        return new InputParameterImpl(name, value, temporalType);
    }

    /**
     * 引数で指定された名前と値を持つinput_parameterを作成します．
     * 
     * @param name
     *            パラメータの名前
     * @param value
     *            パラメータの値
     * @param temporalType
     *            パラメータの時制
     * @return datetime_primary
     */
    public static DatetimePrimary parameter(final String name,
            final Calendar value, final TemporalType temporalType) {
        return new InputParameterImpl(name, value, temporalType);
    }

    /**
     * 引数で指定された名前と値を持つinput_parameterを作成します．
     * 
     * @param name
     *            パラメータの名前
     * @param value
     *            パラメータの値
     * @return enum_primary
     */
    public static EnumPrimary parameter(final String name, final Enum<?> value) {
        return new InputParameterImpl(name, value);
    }

    /**
     * 引数で指定された名前と値を持つinput_parameterを作成します．
     * 
     * @param name
     *            パラメータの名前
     * @param value
     *            パラメータの値 (エンティティ)
     * @return simple_entity_expression
     */
    public static SimpleEntityExpression parameter(final String name,
            final Object value) {
        return new InputParameterImpl(name, value);
    }

    /**
     * 引数で指定された名前を持つidentification_variableを作成します．
     * 
     * @param variable
     *            名前
     * @return identification_variable
     */
    public static IdentificationVariable variable(final String variable) {
        return new IdentificationVariableImpl(variable);
    }

    /**
     * 集計関数<code>COUNT</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @return aggregate_expression
     */
    public static AggregateExpression count(final String path) {
        return count(path(path));
    }

    /**
     * 集計関数<code>COUNT</code>を適用した式を作成します．
     * 
     * @param variable
     *            identification_variable
     * @return aggregate_expression
     */
    public static AggregateExpression count(
            final IdentificationVariable variable) {
        return new Count(variable);
    }

    /**
     * 集計関数<code>COUNT</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expression
     * @return aggregate_expression
     */
    public static AggregateExpression count(final PathExpression path) {
        return new Count(path);
    }

    /**
     * <code>DISTINCT</code>を含んだ集計関数<code>COUNT</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @return aggregate_expression
     */
    public static AggregateExpression countDistinct(final String path) {
        return countDistinct(path(path));
    }

    /**
     * <code>DISTINCT</code>を含んだ集計関数<code>COUNT</code>を適用した式を作成します．
     * 
     * @param variable
     *            identification_variable
     * @return aggregate_expression
     */
    public static AggregateExpression countDistinct(
            final IdentificationVariable variable) {
        return new Count(true, variable);
    }

    /**
     * <code>DISTINCT</code>を含んだ集計関数<code>COUNT</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expression
     * @return aggregate_expression
     */
    public static AggregateExpression countDistinct(final PathExpression path) {
        return new Count(true, path);
    }

    /**
     * 集計関数<code>AVG</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @return aggregate_expression
     */
    public static AggregateExpression avg(final String path) {
        return avg(path(path));
    }

    /**
     * 集計関数<code>AVG</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expression
     * @return aggregate_expression
     */
    public static AggregateExpression avg(final PathExpression path) {
        return new Avg(path);
    }

    /**
     * <code>DISTINCT</code>を含んだ集計関数<code>AVG</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @return aggregate_expression
     */
    public static AggregateExpression avgDistinct(final String path) {
        return avgDistinct(path(path));
    }

    /**
     * <code>DISTINCT</code>を含んだ集計関数<code>AVG</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expression
     * @return aggregate_expression
     */
    public static AggregateExpression avgDistinct(final PathExpression path) {
        return new Avg(true, path);
    }

    /**
     * 集計関数<code>MAX</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @return aggregate_expression
     */
    public static AggregateExpression max(final String path) {
        return max(path(path));
    }

    /**
     * 集計関数<code>MAX</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expression
     * @return aggregate_expression
     */
    public static AggregateExpression max(final PathExpression path) {
        return new Max(path);
    }

    /**
     * <code>DISTINCT</code>を含んだ集計関数<code>MAX</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @return aggregate_expression
     */
    public static AggregateExpression maxDistinct(final String path) {
        return maxDistinct(path(path));
    }

    /**
     * <code>DISTINCT</code>を含んだ集計関数<code>MAX</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expression
     * @return aggregate_expression
     */
    public static AggregateExpression maxDistinct(final PathExpression path) {
        return new Max(true, path);
    }

    /**
     * 集計関数<code>MIN</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @return aggregate_expression
     */
    public static AggregateExpression min(final String path) {
        return min(path(path));
    }

    /**
     * 集計関数<code>MIN</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expression
     * @return aggregate_expression
     */
    public static AggregateExpression min(final PathExpression path) {
        return new Min(path);
    }

    /**
     * <code>DISTINCT</code>を含んだ集計関数<code>MIN</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @return aggregate_expression
     */
    public static AggregateExpression minDistinct(final String path) {
        return minDistinct(path(path));
    }

    /**
     * <code>DISTINCT</code>を含んだ集計関数<code>MIN</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expression
     * @return aggregate_expression
     */
    public static AggregateExpression minDistinct(final PathExpression path) {
        return new Min(true, path);
    }

    /**
     * 集計関数<code>SUM</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @return aggregate_expression
     */
    public static AggregateExpression sum(final String path) {
        return sum(path(path));
    }

    /**
     * 集計関数<code>SUM</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expression
     * @return aggregate_expression
     */
    public static AggregateExpression sum(final PathExpression path) {
        return new Sum(path);
    }

    /**
     * <code>DISTINCT</code>を含んだ集計関数<code>SUM</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @return aggregate_expression
     */
    public static AggregateExpression sumDistinct(final String path) {
        return sumDistinct(path(path));
    }

    /**
     * <code>DISTINCT</code>を含んだ集計関数<code>SUM</code>を適用した式を作成します．
     * 
     * @param path
     *            path_expression
     * @return aggregate_expression
     */
    public static AggregateExpression sumDistinct(final PathExpression path) {
        return new Sum(true, path);
    }

    /**
     * 指定されたabstract_schema_nameとidentification_variableを持つidentification_variable_declarationを作成します
     * ．
     * 
     * @param entityClass
     *            abstract_schema_name
     * @param alias
     *            identification_variable
     * @return identification_variable_declaration
     */
    public static IdentificationVariableDeclaration alias(
            final Class<?> entityClass, final String alias) {
        return new IdentificationVariableDeclarationImpl(entityClass,
                variable(alias));
    }

    /**
     * 指定されたabstract_schema_nameを持つidentification_variable_declarationを作成します．
     * 
     * @param joinedClass
     *            abstract_schema_name
     * @return identification_variable_declaration
     */
    public static IdentificationVariableDeclaration join(
            final Class<?> joinedClass) {
        return new IdentificationVariableDeclarationImpl(joinedClass);
    }

    /**
     * 指定されたabstract_schema_nameとidentification_variableを持つidentification_variable_declarationを作成します
     * ．
     * 
     * @param joinedClass
     *            abstract_schema_name
     * @param alias
     *            identification_variable
     * @return identification_variable_declaration
     */
    public static IdentificationVariableDeclaration join(
            final Class<?> joinedClass, final String alias) {
        return new IdentificationVariableDeclarationImpl(joinedClass,
                variable(alias));
    }

    /**
     * 指定されたconditional_expressionを<code>OR</code>
     * 演算子で連結したconditional_expressionを作成します．
     * 
     * @param expressions
     *            conditional_expressionの並び
     * @return conditional_expression
     */
    public static ConditionalExpression or(
            final ConditionalExpression... expressions) {
        return new Or(expressions);
    }

    /**
     * 指定されたconditional_expressionを<code>AND</code>
     * 演算子で連結したconditional_expressionを作成します．
     * 
     * @param expressions
     *            conditional_expressionの並び
     * @return conditional_expression
     */
    public static ConditionalTerm and(
            final ConditionalExpression... expressions) {
        return new And(expressions);
    }

    /**
     * 指定されたconditional_primaryに<code>NOT</code>を付加したconditional_factorを作成します．
     * 
     * @param primary
     *            conditional_primary
     * @return conditional_factor
     */
    public static ConditionalFactor not(final ConditionalPrimary primary) {
        return new Not(primary);
    }

    /**
     * 指定されたconditional_expressionをカッコで囲んだconditional_primaryを作成します．
     * 
     * @param expression
     *            conditional_expression
     * @return conditional_primary
     */
    public static ConditionalPrimary parenthesis(
            final ConditionalExpression expression) {
        return new Parenthesis(expression);
    }

    /**
     * <code>BETWEEN</code>を使った式を作成します．
     * 
     * @param operand
     *            path_expressionを表す文字列
     * @param from
     *            string_literalとなる文字列
     * @param to
     *            string_literalとなる文字列
     * @return between_expression
     */
    public static BetweenExpression between(final String operand,
            final String from, final String to) {
        return new Between(path(operand), literal(from), literal(to));
    }

    /**
     * <code>BETWEEN</code>を使った式を作成します．
     * 
     * @param operand
     *            path_expressionを表す文字列
     * @param from
     *            numeric_literalとなる数値
     * @param to
     *            numeric_literalとなる数値
     * @return between_expression
     */
    public static BetweenExpression between(final String operand,
            final Number from, final Number to) {
        return between(operand, literal(from), literal(to));
    }

    /**
     * <code>BETWEEN</code>を使った式を作成します．
     * 
     * @param operand
     *            path_expressionを表す文字列
     * @param from
     *            arithmetic_expression
     * @param to
     *            arithmetic_expression
     * @return between_expression
     */
    public static BetweenExpression between(final String operand,
            final ArithmeticExpression from, final ArithmeticExpression to) {
        return new Between(path(operand), from, to);
    }

    /**
     * <code>BETWEEN</code>を使った式を作成します．
     * 
     * @param operand
     *            path_expressionを表す文字列
     * @param from
     *            string_expression
     * @param to
     *            string_expression
     * @return between_expression
     */
    public static BetweenExpression between(final String operand,
            final StringExpression from, final StringExpression to) {
        return new Between(path(operand), from, to);
    }

    /**
     * <code>BETWEEN</code>を使った式を作成します．
     * 
     * @param operand
     *            path_expressionを表す文字列
     * @param from
     *            datetime_expression
     * @param to
     *            datetime_expression
     * @return between_expression
     */
    public static BetweenExpression between(final String operand,
            final DatetimeExpression from, final DatetimeExpression to) {
        return new Between(path(operand), from, to);
    }

    /**
     * <code>LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression like(final String string, final String pattern) {
        return like(path(string), literal(pattern));
    }

    /**
     * <code>LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression like(final String string,
            final StringExpression pattern) {
        return like(path(string), pattern);
    }

    /**
     * <code>LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalを表す文字列
     * @return like_expression
     */
    public static LikeExpression like(final StringExpression string,
            final String pattern) {
        return like(string, literal(pattern));
    }

    /**
     * <code>LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression like(final StringExpression string,
            final StringExpression pattern) {
        return new Like(string, pattern);
    }

    /**
     * <code>LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression like(final String string,
            final String pattern, final String escape) {
        return like(path(string), literal(pattern), literal(escape));
    }

    /**
     * <code>LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression like(final String string,
            final String pattern, final StringExpression escape) {
        return like(path(string), literal(pattern), escape);
    }

    /**
     * <code>LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_expression
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression like(final String string,
            final StringExpression pattern, final String escape) {
        return like(path(string), pattern, literal(escape));
    }

    /**
     * <code>LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_expression
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression like(final String string,
            final StringExpression pattern, final StringExpression escape) {
        return like(path(string), pattern, escape);
    }

    /**
     * <code>LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression like(final StringExpression string,
            final String pattern, final String escape) {
        return like(string, literal(pattern), literal(escape));
    }

    /**
     * <code>LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression like(final StringExpression string,
            final String pattern, final StringExpression escape) {
        return like(string, literal(pattern), escape);
    }

    /**
     * <code>LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_expression
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression like(final StringExpression string,
            final StringExpression pattern, final String escape) {
        return like(string, pattern, literal(escape));
    }

    /**
     * <code>LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_expression
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression like(final StringExpression string,
            final StringExpression pattern, final StringExpression escape) {
        return new Like(string, pattern, escape);
    }

    /**
     * <code>NOT LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression notLike(final String string,
            final String pattern) {
        return notLike(path(string), literal(pattern));
    }

    /**
     * <code>NOT LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression notLike(final String string,
            final StringExpression pattern) {
        return notLike(path(string), pattern);
    }

    /**
     * <code>NOT LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression notLike(final StringExpression string,
            final String pattern) {
        return notLike(string, literal(pattern));
    }

    /**
     * <code>NOT LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression notLike(final StringExpression string,
            final StringExpression pattern) {
        return new NotLike(string, pattern);
    }

    /**
     * <code>NOT LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression notLike(final String string,
            final String pattern, final String escape) {
        return notLike(path(string), literal(pattern), literal(escape));
    }

    /**
     * <code>NOT LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression notLike(final String string,
            final String pattern, final StringExpression escape) {
        return notLike(path(string), literal(pattern), escape);
    }

    /**
     * <code>NOT LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_expression
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression notLike(final String string,
            final StringExpression pattern, final String escape) {
        return notLike(path(string), pattern, literal(escape));
    }

    /**
     * <code>NOT LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_expression
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression notLike(final String string,
            final StringExpression pattern, final StringExpression escape) {
        return notLike(path(string), pattern, escape);
    }

    /**
     * <code>NOT LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression notLike(final StringExpression string,
            final String pattern, final String escape) {
        return notLike(string, literal(pattern), literal(escape));
    }

    /**
     * <code>NOT LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression notLike(final StringExpression string,
            final String pattern, final StringExpression escape) {
        return notLike(string, literal(pattern), escape);
    }

    /**
     * <code>NOT LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_expression
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression notLike(final StringExpression string,
            final StringExpression pattern, final String escape) {
        return notLike(string, pattern, literal(escape));
    }

    /**
     * <code>NOT LIKE</code>を使った式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_expression
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression notLike(final StringExpression string,
            final StringExpression pattern, final StringExpression escape) {
        return new NotLike(string, pattern, escape);
    }

    /**
     * <code>LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列 (末尾にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression starts(final String string,
            final String pattern) {
        return like(path(string), literal(pattern + "%"));
    }

    /**
     * <code>LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_primary (末尾にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression starts(final String string,
            final StringPrimary pattern) {
        return like(path(string), concat(pattern, "%"));
    }

    /**
     * <code>LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalを表す文字列 (末尾にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression starts(final StringExpression string,
            final String pattern) {
        return like(string, literal(pattern + "%"));
    }

    /**
     * <code>LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_primary (末尾にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression starts(final StringExpression string,
            final StringPrimary pattern) {
        return new Like(string, concat(pattern, "%"));
    }

    /**
     * <code>LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列 (末尾にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression starts(final String string,
            final String pattern, final String escape) {
        return like(path(string), literal(pattern + "%"), literal(escape));
    }

    /**
     * <code>LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列 (末尾にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression starts(final String string,
            final String pattern, final StringExpression escape) {
        return like(path(string), literal(pattern + "%"), escape);
    }

    /**
     * <code>LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_primary (末尾にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression starts(final String string,
            final StringPrimary pattern, final String escape) {
        return like(path(string), concat(pattern, "%"), literal(escape));
    }

    /**
     * <code>LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_primary (末尾にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression starts(final String string,
            final StringPrimary pattern, final StringExpression escape) {
        return like(path(string), concat(pattern, "%"), escape);
    }

    /**
     * <code>LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列 (末尾にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression starts(final StringExpression string,
            final String pattern, final String escape) {
        return like(string, literal(pattern + "%"), literal(escape));
    }

    /**
     * <code>LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列 (末尾にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression starts(final StringExpression string,
            final String pattern, final StringExpression escape) {
        return like(string, literal(pattern + "%"), escape);
    }

    /**
     * <code>LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_primary (末尾にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression starts(final StringExpression string,
            final StringPrimary pattern, final String escape) {
        return like(string, concat(pattern, "%"), literal(escape));
    }

    /**
     * <code>LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_primary (末尾にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression starts(final StringExpression string,
            final StringPrimary pattern, final StringExpression escape) {
        return new Like(string, concat(pattern, "%"), escape);
    }

    /**
     * <code>NOT LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列 (末尾にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression notStarts(final String string,
            final String pattern) {
        return notLike(path(string), literal(pattern + "%"));
    }

    /**
     * <code>NOT LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_primary (末尾にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression notStarts(final String string,
            final StringPrimary pattern) {
        return notLike(path(string), concat(pattern, "%"));
    }

    /**
     * <code>NOT LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列 (末尾にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression notStarts(final StringExpression string,
            final String pattern) {
        return notLike(string, literal(pattern + "%"));
    }

    /**
     * <code>NOT LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_primary (末尾にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression notStarts(final StringExpression string,
            final StringPrimary pattern) {
        return new NotLike(string, concat(pattern, "%"));
    }

    /**
     * <code>NOT LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列 (末尾にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression notStarts(final String string,
            final String pattern, final String escape) {
        return notLike(path(string), literal(pattern + "%"), literal(escape));
    }

    /**
     * <code>NOT LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列 (末尾にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression notStarts(final String string,
            final String pattern, final StringExpression escape) {
        return notLike(path(string), literal(pattern + "%"), escape);
    }

    /**
     * <code>NOT LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_primary (末尾にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression notStarts(final String string,
            final StringPrimary pattern, final String escape) {
        return notLike(path(string), concat(pattern, "%"), literal(escape));
    }

    /**
     * <code>NOT LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_primary (末尾にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression notStarts(final String string,
            final StringPrimary pattern, final StringExpression escape) {
        return notLike(path(string), concat(pattern, "%"), escape);
    }

    /**
     * <code>NOT LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列 (末尾にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression notStarts(final StringExpression string,
            final String pattern, final String escape) {
        return notLike(string, literal(pattern + "%"), literal(escape));
    }

    /**
     * <code>NOT LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列 (末尾にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression notStarts(final StringExpression string,
            final String pattern, final StringExpression escape) {
        return notLike(string, literal(pattern + "%"), escape);
    }

    /**
     * <code>NOT LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_primary (末尾にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression notStarts(final StringExpression string,
            final StringPrimary pattern, final String escape) {
        return notLike(string, concat(pattern, "%"), literal(escape));
    }

    /**
     * <code>NOT LIKE</code>を使って前方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_primary (末尾にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression notStarts(final StringExpression string,
            final StringPrimary pattern, final StringExpression escape) {
        return new NotLike(string, concat(pattern, "%"), escape);
    }

    /**
     * <code>LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列 (先頭にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression ends(final String string, final String pattern) {
        return like(path(string), literal("%" + pattern));
    }

    /**
     * <code>LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_primary (先頭にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression ends(final String string,
            final StringPrimary pattern) {
        return like(path(string), concat(literal("%"), pattern));
    }

    /**
     * <code>LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalを表す文字列 (先頭にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression ends(final StringExpression string,
            final String pattern) {
        return like(string, literal("%" + pattern));
    }

    /**
     * <code>LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_primary (先頭にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression ends(final StringExpression string,
            final StringPrimary pattern) {
        return new Like(string, concat(literal("%"), pattern));
    }

    /**
     * <code>LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列 (先頭にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression ends(final String string,
            final String pattern, final String escape) {
        return like(path(string), literal("%" + pattern), literal(escape));
    }

    /**
     * <code>LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列 (先頭にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression ends(final String string,
            final String pattern, final StringExpression escape) {
        return like(path(string), literal("%" + pattern), escape);
    }

    /**
     * <code>LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_primary (先頭にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression ends(final String string,
            final StringPrimary pattern, final String escape) {
        return like(path(string), concat(literal("%"), pattern),
                literal(escape));
    }

    /**
     * <code>LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_primary (先頭にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression ends(final String string,
            final StringPrimary pattern, final StringExpression escape) {
        return like(path(string), concat(literal("%"), pattern), escape);
    }

    /**
     * <code>LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列 (先頭にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression ends(final StringExpression string,
            final String pattern, final String escape) {
        return like(string, literal("%" + pattern), literal(escape));
    }

    /**
     * <code>LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列 (先頭にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression ends(final StringExpression string,
            final String pattern, final StringExpression escape) {
        return like(string, literal("%" + pattern), escape);
    }

    /**
     * <code>LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_primary (先頭にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression ends(final StringExpression string,
            final StringPrimary pattern, final String escape) {
        return like(string, concat(literal("%"), pattern), literal(escape));
    }

    /**
     * <code>LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_primary (先頭にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression ends(final StringExpression string,
            final StringPrimary pattern, final StringExpression escape) {
        return new Like(string, concat(literal("%"), pattern), escape);
    }

    /**
     * <code>NOT LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列 (先頭にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression notEnds(final String string,
            final String pattern) {
        return notLike(path(string), literal("%" + pattern));
    }

    /**
     * <code>NOT LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_primary (先頭にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression notEnds(final String string,
            final StringPrimary pattern) {
        return notLike(path(string), concat(literal("%"), pattern));
    }

    /**
     * <code>NOT LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列 (先頭にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression notEnds(final StringExpression string,
            final String pattern) {
        return notLike(string, literal("%" + pattern));
    }

    /**
     * <code>NOT LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_primary (先頭にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression notEnds(final StringExpression string,
            final StringPrimary pattern) {
        return new NotLike(string, concat(literal("%"), pattern));
    }

    /**
     * <code>NOT LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列 (先頭にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression notEnds(final String string,
            final String pattern, final String escape) {
        return notLike(path(string), literal("%" + pattern), literal(escape));
    }

    /**
     * <code>NOT LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列 (先頭にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression notEnds(final String string,
            final String pattern, final StringExpression escape) {
        return notLike(path(string), literal("%" + pattern), escape);
    }

    /**
     * <code>NOT LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_primary (先頭にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression notEnds(final String string,
            final StringPrimary pattern, final String escape) {
        return notLike(path(string), concat(literal("%"), pattern),
                literal(escape));
    }

    /**
     * <code>NOT LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_primary (先頭にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression notEnds(final String string,
            final StringPrimary pattern, final StringExpression escape) {
        return notLike(path(string), concat(literal("%"), pattern), escape);
    }

    /**
     * <code>NOT LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列 (先頭にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression notEnds(final StringExpression string,
            final String pattern, final String escape) {
        return notLike(string, literal("%" + pattern), literal(escape));
    }

    /**
     * <code>NOT LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列 (先頭にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression notEnds(final StringExpression string,
            final String pattern, final StringExpression escape) {
        return notLike(string, literal("%" + pattern), escape);
    }

    /**
     * <code>NOT LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_primary (先頭にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression notEnds(final StringExpression string,
            final StringPrimary pattern, final String escape) {
        return notLike(string, concat(literal("%"), pattern), literal(escape));
    }

    /**
     * <code>NOT LIKE</code>を使って後方一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_primary (先頭にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression notEnds(final StringExpression string,
            final StringPrimary pattern, final StringExpression escape) {
        return new NotLike(string, concat(literal("%"), pattern), escape);
    }

    /**
     * <code>LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列 (先頭と末尾にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression contains(final String string,
            final String pattern) {
        return like(path(string), literal("%" + pattern + "%"));
    }

    /**
     * <code>LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_primary (先頭と末尾にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression contains(final String string,
            final StringPrimary pattern) {
        return like(path(string), concat(literal("%"), pattern, literal("%")));
    }

    /**
     * <code>LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalを表す文字列 (先頭と末尾にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression contains(final StringExpression string,
            final String pattern) {
        return like(string, literal("%" + pattern + "%"));
    }

    /**
     * <code>LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_primary (先頭と末尾にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression contains(final StringExpression string,
            final StringPrimary pattern) {
        return new Like(string, concat(literal("%"), pattern, literal("%")));
    }

    /**
     * <code>LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列 (先頭と末尾にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression contains(final String string,
            final String pattern, final String escape) {
        return like(path(string), literal("%" + pattern + "%"), literal(escape));
    }

    /**
     * <code>LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列 (先頭と末尾にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression contains(final String string,
            final String pattern, final StringExpression escape) {
        return like(path(string), literal("%" + pattern + "%"), escape);
    }

    /**
     * <code>LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_primary (先頭と末尾にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression contains(final String string,
            final StringPrimary pattern, final String escape) {
        return like(path(string), concat(literal("%"), pattern, literal("%")),
                literal(escape));
    }

    /**
     * <code>LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_primary (先頭と末尾にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression contains(final String string,
            final StringPrimary pattern, final StringExpression escape) {
        return like(path(string), concat(literal("%"), pattern, literal("%")),
                escape);
    }

    /**
     * <code>LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列 (先頭と末尾にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression contains(final StringExpression string,
            final String pattern, final String escape) {
        return like(string, literal("%" + pattern + "%"), literal(escape));
    }

    /**
     * <code>LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列 (先頭と末尾にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression contains(final StringExpression string,
            final String pattern, final StringExpression escape) {
        return like(string, literal("%" + pattern + "%"), escape);
    }

    /**
     * <code>LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_primary (先頭と末尾にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression contains(final StringExpression string,
            final StringPrimary pattern, final String escape) {
        return like(string, concat(literal("%"), pattern, literal("%")),
                literal(escape));
    }

    /**
     * <code>LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_primary (先頭と末尾にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression contains(final StringExpression string,
            final StringPrimary pattern, final StringExpression escape) {
        return new Like(string, concat(literal("%"), pattern, literal("%")),
                escape);
    }

    /**
     * <code>NOT LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列 (先頭と末尾にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression notContains(final String string,
            final String pattern) {
        return notLike(path(string), literal("%" + pattern + "%"));
    }

    /**
     * <code>NOT LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_primary (先頭と末尾にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression notContains(final String string,
            final StringPrimary pattern) {
        return notLike(path(string),
                concat(literal("%"), pattern, literal("%")));
    }

    /**
     * <code>NOT LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列 (先頭と末尾にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression notContains(final StringExpression string,
            final String pattern) {
        return notLike(string, literal("%" + pattern + "%"));
    }

    /**
     * <code>NOT LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_primary (先頭と末尾にワイルドカードが付加されます)
     * @return like_expression
     */
    public static LikeExpression notContains(final StringExpression string,
            final StringPrimary pattern) {
        return new NotLike(string, concat(literal("%"), pattern, literal("%")));
    }

    /**
     * <code>NOT LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列 (先頭と末尾にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression notContains(final String string,
            final String pattern, final String escape) {
        return notLike(path(string), literal("%" + pattern + "%"),
                literal(escape));
    }

    /**
     * <code>NOT LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_literalとなる文字列 (先頭と末尾にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression notContains(final String string,
            final String pattern, final StringExpression escape) {
        return notLike(path(string), literal("%" + pattern + "%"), escape);
    }

    /**
     * <code>NOT LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_primary (先頭と末尾にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression notContains(final String string,
            final StringPrimary pattern, final String escape) {
        return notLike(path(string),
                concat(literal("%"), pattern, literal("%")), literal(escape));
    }

    /**
     * <code>NOT LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param pattern
     *            string_primary (先頭と末尾にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression notContains(final String string,
            final StringPrimary pattern, final StringExpression escape) {
        return notLike(path(string),
                concat(literal("%"), pattern, literal("%")), escape);
    }

    /**
     * <code>NOT LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列 (先頭と末尾にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression notContains(final StringExpression string,
            final String pattern, final String escape) {
        return notLike(string, literal("%" + pattern + "%"), literal(escape));
    }

    /**
     * <code>NOT LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_literalとなる文字列 (先頭と末尾にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression notContains(final StringExpression string,
            final String pattern, final StringExpression escape) {
        return notLike(string, literal("%" + pattern + "%"), escape);
    }

    /**
     * <code>NOT LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_primary (先頭と末尾にワイルドカードが付加されます)
     * @param escape
     *            string_literalとなる文字列
     * @return like_expression
     */
    public static LikeExpression notContains(final StringExpression string,
            final StringPrimary pattern, final String escape) {
        return notLike(string, concat(literal("%"), pattern, literal("%")),
                literal(escape));
    }

    /**
     * <code>NOT LIKE</code>を使って部分一致をテストする式を作成します．
     * 
     * @param string
     *            string_expression
     * @param pattern
     *            string_primary (先頭と末尾にワイルドカードが付加されます)
     * @param escape
     *            string_expression
     * @return like_expression
     */
    public static LikeExpression notContains(final StringExpression string,
            final StringPrimary pattern, final StringExpression escape) {
        return new NotLike(string, concat(literal("%"), pattern, literal("%")),
                escape);
    }

    /**
     * <code>IN</code>を使った式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @param inItems
     *            in_itemの並び
     * @return in_expression
     */
    public static InExpression in(final String path, final boolean... inItems) {
        final InExpression inExpression = new InExpressionImpl(path(path));
        for (final boolean inItem : inItems) {
            inExpression.add(literal(inItem));
        }
        return inExpression;
    }

    /**
     * <code>IN</code>を使った式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @param inItems
     *            numeric_literalとなる数値の並び
     * @return in_expression
     */
    public static InExpression in(final String path, final Number... inItems) {
        final InExpression inExpression = new InExpressionImpl(path(path));
        for (final Number inItem : inItems) {
            inExpression.add(literal(inItem));
        }
        return inExpression;
    }

    /**
     * <code>IN</code>を使った式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @param inItems
     *            string_literalとなる文字列の並び
     * @return in_expression
     */
    public static InExpression in(final String path, final String... inItems) {
        final InExpression inExpression = new InExpressionImpl(path(path));
        for (final String inItem : inItems) {
            inExpression.add(literal(inItem));
        }
        return inExpression;
    }

    /**
     * <code>IN</code>を使った式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @param inItems
     *            enum_literalとなる列挙の並び
     * @return in_expression
     */
    public static InExpression in(final String path, final Enum<?>... inItems) {
        final InExpression inExpression = new InExpressionImpl(path(path));
        for (final Enum<?> inItem : inItems) {
            inExpression.add(literal(inItem));
        }
        return inExpression;
    }

    /**
     * <code>IN</code>を使った式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @param inItems
     *            literalの並び
     * @return in_expression
     */
    public static InExpression in(final String path, final Literal... inItems) {
        final InExpression inExpression = new InExpressionImpl(path(path));
        for (final Literal inItem : inItems) {
            inExpression.add(inItem);
        }
        return inExpression;
    }

    /**
     * <code>IN</code>を使った式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @param inItems
     *            input_parameterの並び
     * @return in_expression
     */
    public static InExpression in(final String path,
            final InputParameter... inItems) {
        final InExpression inExpression = new InExpressionImpl(path(path));
        for (final InputParameter inItem : inItems) {
            inExpression.add(inItem);
        }
        return inExpression;
    }

    /**
     * <code>IN</code>を使った式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @param subquery
     *            subquery
     * @return in_expression
     */
    public static InExpression in(final String path, final Subquery subquery) {
        final InExpression inExpression = new InExpressionImpl(path(path));
        inExpression.setSubquery(subquery);
        return inExpression;
    }

    /**
     * <code>NOT IN</code>を使った式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @param inItems
     *            in_itemの並び
     * @return in_expression
     */
    public static InExpression notIn(final String path,
            final boolean... inItems) {
        final InExpression notInExpression = new NotInExpressionImpl(path(path));
        for (final boolean inItem : inItems) {
            notInExpression.add(literal(inItem));
        }
        return notInExpression;
    }

    /**
     * <code>NOT IN</code>を使った式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @param inItems
     *            numeric_literalとなる数値の並び
     * @return in_expression
     */
    public static InExpression notIn(final String path, final Number... inItems) {
        final InExpression notInExpression = new NotInExpressionImpl(path(path));
        for (final Number inItem : inItems) {
            notInExpression.add(literal(inItem));
        }
        return notInExpression;
    }

    /**
     * <code>NOT IN</code>を使った式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @param inItems
     *            string_literalとなる文字列の並び
     * @return in_expression
     */
    public static InExpression notIn(final String path, final String... inItems) {
        final InExpression notInExpression = new InExpressionImpl(path(path));
        for (final String inItem : inItems) {
            notInExpression.add(literal(inItem));
        }
        return notInExpression;
    }

    /**
     * <code>NOT IN</code>を使った式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @param inItems
     *            enum_literalとなる列挙の並び
     * @return in_expression
     */
    public static InExpression notIn(final String path,
            final Enum<?>... inItems) {
        final InExpression notInExpression = new NotInExpressionImpl(path(path));
        for (final Enum<?> inItem : inItems) {
            notInExpression.add(literal(inItem));
        }
        return notInExpression;
    }

    /**
     * <code>NOT IN</code>を使った式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @param inItems
     *            literalの並び
     * @return in_expression
     */
    public static InExpression notIn(final String path,
            final Literal... inItems) {
        final InExpression notInExpression = new NotInExpressionImpl(path(path));
        for (final Literal inItem : inItems) {
            notInExpression.add(inItem);
        }
        return notInExpression;
    }

    /**
     * <code>NOT IN</code>を使った式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @param inItems
     *            input_parameterの並び
     * @return in_expression
     */
    public static InExpression notIn(final String path,
            final InputParameter... inItems) {
        final InExpression notInExpression = new NotInExpressionImpl(path(path));
        for (final InputParameter inItem : inItems) {
            notInExpression.add(inItem);
        }
        return notInExpression;
    }

    /**
     * <code>NOT IN</code>を使った式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @param subquery
     *            subquery
     * @return in_expression
     */
    public static InExpression notIn(final String path, final Subquery subquery) {
        final InExpression notInExpression = new NotInExpressionImpl(path(path));
        notInExpression.setSubquery(subquery);
        return notInExpression;
    }

    /**
     * <code>IS NULL</code>を使った式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @return null_comparison_expression
     */
    public static NullComparisonExpression isNull(final String path) {
        return isNull(path(path));
    }

    /**
     * <code>IS NULL</code>を使った式を作成します．
     * 
     * @param pathExpression
     *            path_expression
     * @return null_comparison_expression
     */
    public static NullComparisonExpression isNull(
            final PathExpression pathExpression) {
        return new IsNull(pathExpression);
    }

    /**
     * <code>IS NULL</code>を使った式を作成します．
     * 
     * @param inputParameter
     *            input_parameter
     * @return null_comparison_expression
     */
    public static NullComparisonExpression isNull(
            final InputParameter inputParameter) {
        return new IsNull(inputParameter);
    }

    /**
     * <code>IS NOT NULL</code>を使った式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @return null_comparison_expression
     */
    public static NullComparisonExpression isNotNull(final String path) {
        return isNotNull(path(path));
    }

    /**
     * <code>IS NOT NULL</code>を使った式を作成します．
     * 
     * @param pathExpression
     *            path_expression
     * @return null_comparison_expression
     */
    public static NullComparisonExpression isNotNull(
            final PathExpression pathExpression) {
        return new IsNotNull(pathExpression);
    }

    /**
     * <code>IS NOT NULL</code>を使った式を作成します．
     * 
     * @param inputParameter
     *            input_parameter
     * @return null_comparison_expression
     */
    public static NullComparisonExpression isNotNull(
            final InputParameter inputParameter) {
        return new IsNotNull(inputParameter);
    }

    /**
     * <code>IS EMPTY</code>を使った式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @return empty_collection_comparison_expression
     */
    public static EmptyCollectionComparisonExpression isEmpty(final String path) {
        return isEmpty(path(path));
    }

    /**
     * <code>IS EMPTY</code>を使った式を作成します．
     * 
     * @param pathExpression
     *            path_expression
     * @return empty_collection_comparison_expression
     */
    public static EmptyCollectionComparisonExpression isEmpty(
            final PathExpression pathExpression) {
        return new IsEmpty(pathExpression);
    }

    /**
     * <code>IS NOT EMPTY</code>を使った式を作成します．
     * 
     * @param inputParameter
     *            input_parameter
     * @return empty_collection_comparison_expression
     */
    public static EmptyCollectionComparisonExpression isEmpty(
            final InputParameter inputParameter) {
        return new IsEmpty(inputParameter);
    }

    /**
     * <code>IS NOT EMPTY</code>を使った式を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @return empty_collection_comparison_expression
     */
    public static EmptyCollectionComparisonExpression isNotEmpty(
            final String path) {
        return isNotEmpty(path(path));
    }

    /**
     * <code>IS NOT EMPTY</code>を使った式を作成します．
     * 
     * @param pathExpression
     *            path_expression
     * @return empty_collection_comparison_expression
     */
    public static EmptyCollectionComparisonExpression isNotEmpty(
            final PathExpression pathExpression) {
        return new IsNotEmpty(pathExpression);
    }

    /**
     * <code>IS NOT EMPTY</code>を使った式を作成します．
     * 
     * @param inputParameter
     *            input_parameter
     * @return empty_collection_comparison_expression
     */
    public static EmptyCollectionComparisonExpression isNotEmpty(
            final InputParameter inputParameter) {
        return new IsNotEmpty(inputParameter);
    }

    /**
     * <code>MEMBEDR OF</code>を使った式を作成します．
     * 
     * @param lhs
     *            entity_expressionを表す文字列
     * @param rhs
     *            collection_valued_path_expressionを表す文字列
     * @return collection_member_expression
     */
    public static CollectionMemberExpression memberOf(final String lhs,
            final String rhs) {
        return memberOf(variable(lhs), path(rhs));
    }

    /**
     * <code>MEMBEDR OF</code>を使った式を作成します．
     * 
     * @param lhs
     *            entity_expression
     * @param rhs
     *            collection_valued_path_expressionを表す文字列
     * @return collection_member_expression
     */
    public static CollectionMemberExpression memberOf(final PathExpression lhs,
            final String rhs) {
        return memberOf(lhs, path(rhs));
    }

    /**
     * <code>MEMBEDR OF</code>を使った式を作成します．
     * 
     * @param lhs
     *            entity_expression
     * @param rhs
     *            collection_valued_path_expression
     * @return collection_member_expression
     */
    public static CollectionMemberExpression memberOf(final PathExpression lhs,
            final PathExpression rhs) {
        return new MemberOf(lhs, rhs);
    }

    /**
     * <code>MEMBEDR OF</code>を使った式を作成します．
     * 
     * @param lhs
     *            entity_expression
     * @param rhs
     *            collection_valued_path_expressionを表す文字列
     * @return collection_member_expression
     */
    public static CollectionMemberExpression memberOf(
            final IdentificationVariable lhs, final String rhs) {
        return memberOf(lhs, path(rhs));
    }

    /**
     * <code>MEMBEDR OF</code>を使った式を作成します．
     * 
     * @param lhs
     *            entity_expression
     * @param rhs
     *            collection_valued_path_expression
     * @return collection_member_expression
     */
    public static CollectionMemberExpression memberOf(
            final IdentificationVariable lhs, final PathExpression rhs) {
        return new MemberOf(lhs, rhs);
    }

    /**
     * <code>MEMBEDR OF</code>を使った式を作成します．
     * 
     * @param lhs
     *            entity_expression
     * @param rhs
     *            collection_valued_path_expressionを表す文字列
     * @return collection_member_expression
     */
    public static CollectionMemberExpression memberOf(final InputParameter lhs,
            final String rhs) {
        return memberOf(lhs, path(rhs));
    }

    /**
     * <code>MEMBEDR OF</code>を使った式を作成します．
     * 
     * @param lhs
     *            entity_expression
     * @param rhs
     *            collection_valued_path_expression
     * @return collection_member_expression
     */
    public static CollectionMemberExpression memberOf(final InputParameter lhs,
            final PathExpression rhs) {
        return new MemberOf(lhs, rhs);
    }

    /**
     * <code>NOT MEMBEDR OF</code>を使った式を作成します．
     * 
     * @param lhs
     *            entity_expressionを表す文字列
     * @param rhs
     *            collection_valued_path_expressionを表す文字列
     * @return collection_member_expression
     */
    public static CollectionMemberExpression notMemberOf(final String lhs,
            final String rhs) {
        return notMemberOf(path(lhs), path(rhs));
    }

    /**
     * <code>NOT MEMBEDR OF</code>を使った式を作成します．
     * 
     * @param lhs
     *            entity_expression
     * @param rhs
     *            collection_valued_path_expressionを表す文字列
     * @return collection_member_expression
     */
    public static CollectionMemberExpression notMemberOf(
            final PathExpression lhs, final String rhs) {
        return notMemberOf(lhs, path(rhs));
    }

    /**
     * <code>NOT MEMBEDR OF</code>を使った式を作成します．
     * 
     * @param lhs
     *            entity_expression
     * @param rhs
     *            collection_valued_path_expression
     * @return collection_member_expression
     */
    public static CollectionMemberExpression notMemberOf(
            final PathExpression lhs, final PathExpression rhs) {
        return new NotMemberOf(lhs, rhs);
    }

    /**
     * <code>NOT MEMBEDR OF</code>を使った式を作成します．
     * 
     * @param lhs
     *            entity_expression
     * @param rhs
     *            collection_valued_path_expressionを表す文字列
     * @return collection_member_expression
     */
    public static CollectionMemberExpression notMemberOf(
            final IdentificationVariable lhs, final String rhs) {
        return notMemberOf(lhs, path(rhs));
    }

    /**
     * <code>NOT MEMBEDR OF</code>を使った式を作成します．
     * 
     * @param lhs
     *            entity_expression
     * @param rhs
     *            collection_valued_path_expression
     * @return collection_member_expression
     */
    public static CollectionMemberExpression notMemberOf(
            final IdentificationVariable lhs, final PathExpression rhs) {
        return new NotMemberOf(lhs, rhs);
    }

    /**
     * <code>NOT MEMBEDR OF</code>を使った式を作成します．
     * 
     * @param lhs
     *            entity_expression
     * @param rhs
     *            collection_valued_path_expressionを表す文字列
     * @return collection_member_expression
     */
    public static CollectionMemberExpression notMemberOf(
            final InputParameter lhs, final String rhs) {
        return notMemberOf(lhs, path(rhs));
    }

    /**
     * <code>NOT MEMBEDR OF</code>を使った式を作成します．
     * 
     * @param lhs
     *            entity_expression
     * @param rhs
     *            collection_valued_path_expression
     * @return collection_member_expression
     */
    public static CollectionMemberExpression notMemberOf(
            final InputParameter lhs, final PathExpression rhs) {
        return new NotMemberOf(lhs, rhs);
    }

    /**
     * <code>EXISTS</code>を使った式を作成します．
     * 
     * @param subquery
     *            subquery
     * @return exists_expression
     */
    public static ExistsExpression exists(final Subquery subquery) {
        final ExistsExpression existsExpression = new ExistsExpressionImpl();
        existsExpression.setSubquery(subquery);
        return existsExpression;
    }

    /**
     * <code>NOT EXISTS</code>を使った式を作成します．
     * 
     * @param subquery
     *            subquery
     * @return exists_expression
     */
    public static ExistsExpression notExists(final Subquery subquery) {
        final ExistsExpression existsExpression = new NotExistsExpressionImpl();
        existsExpression.setSubquery(subquery);
        return existsExpression;
    }

    /**
     * <code>ALL</code>を使った式を作成します．
     * 
     * @param subquery
     *            subquery
     * @return all_or_any_expression
     */
    public static AllOrAnyExpression all(final Subquery subquery) {
        final AllOrAnyExpression allOrAnyExpression = new AllExpressionImpl();
        allOrAnyExpression.setSubquery(subquery);
        return allOrAnyExpression;
    }

    /**
     * <code>ANY</code>を使った式を作成します．
     * 
     * @param subquery
     *            subquery
     * @return all_or_any_expression
     */
    public static AllOrAnyExpression any(final Subquery subquery) {
        final AllOrAnyExpression allOrAnyExpression = new AnyExpressionImpl();
        allOrAnyExpression.setSubquery(subquery);
        return allOrAnyExpression;
    }

    /**
     * <code>SOME</code>を使った式を作成します．
     * 
     * @param subquery
     *            subquery
     * @return all_or_any_expression
     */
    public static AllOrAnyExpression some(final Subquery subquery) {
        final AllOrAnyExpression allOrAnyExpression = new SomeExpressionImpl();
        allOrAnyExpression.setSubquery(subquery);
        return allOrAnyExpression;
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            numeric_literalとなる数値
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final String lhs, final Number rhs) {
        return eq(path(lhs), literal(rhs));
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            arithmetic_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final String lhs,
            final ArithmeticExpression rhs) {
        return eq(path(lhs), rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            string_literalとなる文字列
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final String lhs, final String rhs) {
        return eq(path(lhs), literal(rhs));
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            string_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final String lhs,
            final StringExpression rhs) {
        return eq(path(lhs), rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            datetime_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final String lhs,
            final DatetimeExpression rhs) {
        return eq(path(lhs), rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            boolean_literalを表す<code>boolean</code>
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final String lhs, final boolean rhs) {
        return eq(path(lhs), literal(rhs));
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            boolean_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final String lhs,
            final BooleanExpression rhs) {
        return eq(path(lhs), rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            enum_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final String lhs,
            final EnumExpression rhs) {
        return eq(path(lhs), rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            entity_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final String lhs,
            final EntityExpression rhs) {
        return eq(path(lhs), rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            path_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final String lhs,
            final PathExpression rhs) {
        return eq(path(lhs), rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final String lhs,
            final AllOrAnyExpression rhs) {
        return eq(path(lhs), rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            numeric_literalとなる数値
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final PathExpression lhs,
            final Number rhs) {
        return eq(lhs, literal(rhs));
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            arithmetic_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final PathExpression lhs,
            final ArithmeticExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            string_literalとなる文字列
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final PathExpression lhs,
            final String rhs) {
        return eq(lhs, literal(rhs));
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            string_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final PathExpression lhs,
            final StringExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            datetime_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final PathExpression lhs,
            final DatetimeExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            boolean_literalを表す<code>boolean</code>
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final PathExpression lhs,
            final boolean rhs) {
        return eq(lhs, literal(rhs));
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            boolean_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final PathExpression lhs,
            final BooleanExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            enum_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final PathExpression lhs,
            final EnumExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            entity_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final PathExpression lhs,
            final EntityExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            path_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final PathExpression lhs,
            final PathExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final PathExpression lhs,
            final AllOrAnyExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_expression
     * @param rhs
     *            numeric_literalとなる数値
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final ArithmeticExpression lhs,
            final Number rhs) {
        return eq(lhs, literal(rhs));
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_expression
     * @param rhs
     *            arithmetic_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final ArithmeticExpression lhs,
            final ArithmeticExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final ArithmeticExpression lhs,
            final AllOrAnyExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            string_expression
     * @param rhs
     *            string_literalとなる文字列
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final StringExpression lhs,
            final String rhs) {
        return eq(lhs, literal(rhs));
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            string_expression
     * @param rhs
     *            string_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final StringExpression lhs,
            final StringExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            string_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final StringExpression lhs,
            final AllOrAnyExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            datetime_expressionを表す文字列
     * @param rhs
     *            datetime_expressionを表す文字列
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final DatetimeExpression lhs,
            final DatetimeExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            datetime_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final DatetimeExpression lhs,
            final AllOrAnyExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            boolean_expression
     * @param rhs
     *            boolean_literalを表す<code>boolean</code>
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final BooleanExpression lhs,
            final boolean rhs) {
        return eq(lhs, literal(rhs));
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            boolean_expression
     * @param rhs
     *            boolean_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final BooleanExpression lhs,
            final BooleanExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            boolean_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final BooleanExpression lhs,
            final AllOrAnyExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            enum_expression
     * @param rhs
     *            enum_literalとなる列挙
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final EnumExpression lhs,
            final Enum<?> rhs) {
        return eq(lhs, literal(rhs));
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            enum_expression
     * @param rhs
     *            enum_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final EnumExpression lhs,
            final EnumExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            enum_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final EnumExpression lhs,
            final AllOrAnyExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            entity_expression
     * @param rhs
     *            entity_expression
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final EntityExpression lhs,
            final EntityExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>=</code>を使った式を作成します．
     * 
     * @param lhs
     *            entity_expressionを表す文字列
     * @param rhs
     *            all_or_any_expressionを表す文字列
     * @return compairson_expression
     */
    public static ComparisonExpression eq(final EntityExpression lhs,
            final AllOrAnyExpression rhs) {
        return new Equal(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            numeric_literalとなる数値
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final String lhs, final Number rhs) {
        return ne(path(lhs), literal(rhs));
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            arithmetic_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final String lhs,
            final ArithmeticExpression rhs) {
        return ne(path(lhs), rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            string_literalとなる文字列
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final String lhs, final String rhs) {
        return ne(path(lhs), literal(rhs));
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            string_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final String lhs,
            final StringExpression rhs) {
        return ne(path(lhs), rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            datetime_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final String lhs,
            final DatetimeExpression rhs) {
        return ne(path(lhs), rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            boolean_literalを表す<code>boolean</code>
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final String lhs, final boolean rhs) {
        return ne(path(lhs), literal(rhs));
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            boolean_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final String lhs,
            final BooleanExpression rhs) {
        return ne(path(lhs), rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            enum_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final String lhs,
            final EnumExpression rhs) {
        return ne(path(lhs), rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            entity_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final String lhs,
            final EntityExpression rhs) {
        return ne(path(lhs), rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            path_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final String lhs,
            final PathExpression rhs) {
        return ne(path(lhs), rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final String lhs,
            final AllOrAnyExpression rhs) {
        return ne(path(lhs), rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            numeric_literalとなる数値
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final PathExpression lhs,
            final Number rhs) {
        return ne(lhs, literal(rhs));
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            arithmetic_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final PathExpression lhs,
            final ArithmeticExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            string_literalとなる文字列
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final PathExpression lhs,
            final String rhs) {
        return ne(lhs, literal(rhs));
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            string_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final PathExpression lhs,
            final StringExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            datetime_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final PathExpression lhs,
            final DatetimeExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            boolean_literalを表す<code>boolean</code>
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final PathExpression lhs,
            final boolean rhs) {
        return ne(lhs, literal(rhs));
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            boolean_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final PathExpression lhs,
            final BooleanExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            enum_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final PathExpression lhs,
            final EnumExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            entity_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final PathExpression lhs,
            final EntityExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            path_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final PathExpression lhs,
            final PathExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final PathExpression lhs,
            final AllOrAnyExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_expression
     * @param rhs
     *            numeric_literalとなる数値
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final ArithmeticExpression lhs,
            final Number rhs) {
        return new NotEqual(lhs, literal(rhs));
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_expression
     * @param rhs
     *            arithmetic_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final ArithmeticExpression lhs,
            final ArithmeticExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final ArithmeticExpression lhs,
            final AllOrAnyExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            string_expression
     * @param rhs
     *            string_literalとなる文字列
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final StringExpression lhs,
            final String rhs) {
        return ne(lhs, literal(rhs));
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            string_expression
     * @param rhs
     *            string_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final StringExpression lhs,
            final StringExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            string_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final StringExpression lhs,
            final AllOrAnyExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            datetime_expressionを表す文字列
     * @param rhs
     *            datetime_expressionを表す文字列
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final DatetimeExpression lhs,
            final DatetimeExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            datetime_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final DatetimeExpression lhs,
            final AllOrAnyExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            boolean_expression
     * @param rhs
     *            boolean_literalを表す<code>boolean</code>
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final BooleanExpression lhs,
            final boolean rhs) {
        return ne(lhs, literal(rhs));
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            boolean_expression
     * @param rhs
     *            boolean_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final BooleanExpression lhs,
            final BooleanExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            boolean_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final BooleanExpression lhs,
            final AllOrAnyExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            enum_expression
     * @param rhs
     *            enum_literalとなる列挙
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final EnumExpression lhs,
            final Enum<?> rhs) {
        return ne(lhs, literal(rhs));
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            enum_expression
     * @param rhs
     *            enum_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final EnumExpression lhs,
            final EnumExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            enum_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final EnumExpression lhs,
            final AllOrAnyExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            entity_expression
     * @param rhs
     *            entity_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final EntityExpression lhs,
            final EntityExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&lt;&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            entity_expressionを表す文字列
     * @param rhs
     *            all_or_any_expressionを表す文字列
     * @return compairson_expression
     */
    public static ComparisonExpression ne(final EntityExpression lhs,
            final AllOrAnyExpression rhs) {
        return new NotEqual(lhs, rhs);
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            numeric_literalとなる数値
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final String lhs, final Number rhs) {
        return gt(path(lhs), literal(rhs));
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            arithmetic_expression
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final String lhs,
            final ArithmeticExpression rhs) {
        return gt(path(lhs), rhs);
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            string_literalとなる文字列
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final String lhs, final String rhs) {
        return gt(path(lhs), literal(rhs));
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            string_expression
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final String lhs,
            final StringExpression rhs) {
        return gt(path(lhs), rhs);
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            datetime_expression
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final String lhs,
            final DatetimeExpression rhs) {
        return gt(path(lhs), rhs);
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            path_expression
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final String lhs,
            final PathExpression rhs) {
        return gt(path(lhs), rhs);
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final String lhs,
            final AllOrAnyExpression rhs) {
        return gt(path(lhs), rhs);
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            numeric_literalとなる数値
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final PathExpression lhs,
            final Number rhs) {
        return gt(lhs, literal(rhs));
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            arithmetic_expression
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final PathExpression lhs,
            final ArithmeticExpression rhs) {
        return new GreaterThan(lhs, rhs);
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            string_literalとなる文字列
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final PathExpression lhs,
            final String rhs) {
        return gt(lhs, literal(rhs));
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            string_expression
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final PathExpression lhs,
            final StringExpression rhs) {
        return new GreaterThan(lhs, rhs);
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            datetime_expression
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final PathExpression lhs,
            final DatetimeExpression rhs) {
        return new GreaterThan(lhs, rhs);
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            path_expression
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final PathExpression lhs,
            final PathExpression rhs) {
        return new GreaterThan(lhs, rhs);
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final PathExpression lhs,
            final AllOrAnyExpression rhs) {
        return new GreaterThan(lhs, rhs);
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_expression
     * @param rhs
     *            numeric_literalとなる数値
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final ArithmeticExpression lhs,
            final Number rhs) {
        return gt(lhs, literal(rhs));
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_expression
     * @param rhs
     *            arithmetic_expression
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final ArithmeticExpression lhs,
            final ArithmeticExpression rhs) {
        return new GreaterThan(lhs, rhs);
    }

    /**
     * <code>gt&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final ArithmeticExpression lhs,
            final AllOrAnyExpression rhs) {
        return new GreaterThan(lhs, rhs);
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            string_expression
     * @param rhs
     *            string_literalとなる文字列
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final StringExpression lhs,
            final String rhs) {
        return gt(lhs, literal(rhs));
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            string_expression
     * @param rhs
     *            string_expression
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final StringExpression lhs,
            final StringExpression rhs) {
        return new GreaterThan(lhs, rhs);
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            string_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final StringExpression lhs,
            final AllOrAnyExpression rhs) {
        return new GreaterThan(lhs, rhs);
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            datetime_expressionを表す文字列
     * @param rhs
     *            datetime_expressionを表す文字列
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final DatetimeExpression lhs,
            final DatetimeExpression rhs) {
        return new GreaterThan(lhs, rhs);
    }

    /**
     * <code>&gt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            datetime_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression gt(final DatetimeExpression lhs,
            final AllOrAnyExpression rhs) {
        return new GreaterThan(lhs, rhs);
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            numeric_literalとなる数値
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final String lhs, final Number rhs) {
        return ge(path(lhs), literal(rhs));
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            arithmetic_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final String lhs,
            final ArithmeticExpression rhs) {
        return ge(path(lhs), rhs);
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            string_literalとなる文字列
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final String lhs, final String rhs) {
        return ge(path(lhs), literal(rhs));
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            string_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final String lhs,
            final StringExpression rhs) {
        return ge(path(lhs), rhs);
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            datetime_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final String lhs,
            final DatetimeExpression rhs) {
        return ge(path(lhs), rhs);
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            path_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final String lhs,
            final PathExpression rhs) {
        return ge(path(lhs), rhs);
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final String lhs,
            final AllOrAnyExpression rhs) {
        return ge(path(lhs), rhs);
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            numeric_literalとなる数値
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final PathExpression lhs,
            final Number rhs) {
        return ge(lhs, literal(rhs));
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            arithmetic_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final PathExpression lhs,
            final ArithmeticExpression rhs) {
        return new GreaterOrEqual(lhs, rhs);
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            string_literalとなる文字列
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final PathExpression lhs,
            final String rhs) {
        return ge(lhs, literal(rhs));
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            string_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final PathExpression lhs,
            final StringExpression rhs) {
        return new GreaterOrEqual(lhs, rhs);
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            datetime_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final PathExpression lhs,
            final DatetimeExpression rhs) {
        return new GreaterOrEqual(lhs, rhs);
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            path_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final PathExpression lhs,
            final PathExpression rhs) {
        return new GreaterOrEqual(lhs, rhs);
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final PathExpression lhs,
            final AllOrAnyExpression rhs) {
        return new GreaterOrEqual(lhs, rhs);
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_expression
     * @param rhs
     *            numeric_literalとなる数値
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final ArithmeticExpression lhs,
            final Number rhs) {
        return ge(lhs, literal(rhs));
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_expression
     * @param rhs
     *            arithmetic_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final ArithmeticExpression lhs,
            final ArithmeticExpression rhs) {
        return new GreaterOrEqual(lhs, rhs);
    }

    /**
     * <code>gt&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final ArithmeticExpression lhs,
            final AllOrAnyExpression rhs) {
        return new GreaterOrEqual(lhs, rhs);
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            string_expression
     * @param rhs
     *            string_literalとなる文字列
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final StringExpression lhs,
            final String rhs) {
        return ge(lhs, literal(rhs));
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            string_expression
     * @param rhs
     *            string_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final StringExpression lhs,
            final StringExpression rhs) {
        return new GreaterOrEqual(lhs, rhs);
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            string_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final StringExpression lhs,
            final AllOrAnyExpression rhs) {
        return new GreaterOrEqual(lhs, rhs);
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            datetime_expressionを表す文字列
     * @param rhs
     *            datetime_expressionを表す文字列
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final DatetimeExpression lhs,
            final DatetimeExpression rhs) {
        return new GreaterOrEqual(lhs, rhs);
    }

    /**
     * <code>&gt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            datetime_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression ge(final DatetimeExpression lhs,
            final AllOrAnyExpression rhs) {
        return new GreaterOrEqual(lhs, rhs);
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            numeric_literalとなる数値
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final String lhs, final Number rhs) {
        return lt(path(lhs), literal(rhs));
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            arithmetic_expression
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final String lhs,
            final ArithmeticExpression rhs) {
        return lt(path(lhs), rhs);
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            string_literalとなる文字列
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final String lhs, final String rhs) {
        return lt(path(lhs), literal(rhs));
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            string_expression
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final String lhs,
            final StringExpression rhs) {
        return lt(path(lhs), rhs);
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            datetime_expression
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final String lhs,
            final DatetimeExpression rhs) {
        return lt(path(lhs), rhs);
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            path_expression
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final String lhs,
            final PathExpression rhs) {
        return lt(path(lhs), rhs);
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final String lhs,
            final AllOrAnyExpression rhs) {
        return lt(path(lhs), rhs);
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            numeric_literalとなる数値
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final PathExpression lhs,
            final Number rhs) {
        return lt(lhs, literal(rhs));
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            arithmetic_expression
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final PathExpression lhs,
            final ArithmeticExpression rhs) {
        return new LessThan(lhs, rhs);
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            string_literalとなる文字列
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final PathExpression lhs,
            final String rhs) {
        return lt(lhs, literal(rhs));
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            string_expression
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final PathExpression lhs,
            final StringExpression rhs) {
        return new LessThan(lhs, rhs);
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            datetime_expression
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final PathExpression lhs,
            final DatetimeExpression rhs) {
        return new LessThan(lhs, rhs);
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            path_expression
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final PathExpression lhs,
            final PathExpression rhs) {
        return new LessThan(lhs, rhs);
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final PathExpression lhs,
            final AllOrAnyExpression rhs) {
        return new LessThan(lhs, rhs);
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_expression
     * @param rhs
     *            numeric_literalとなる数値
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final ArithmeticExpression lhs,
            final Number rhs) {
        return lt(lhs, literal(rhs));
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_expression
     * @param rhs
     *            arithmetic_expression
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final ArithmeticExpression lhs,
            final ArithmeticExpression rhs) {
        return new LessThan(lhs, rhs);
    }

    /**
     * <code>lt&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final ArithmeticExpression lhs,
            final AllOrAnyExpression rhs) {
        return new LessThan(lhs, rhs);
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            string_expression
     * @param rhs
     *            string_literalとなる文字列
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final StringExpression lhs,
            final String rhs) {
        return lt(lhs, literal(rhs));
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            string_expression
     * @param rhs
     *            string_expression
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final StringExpression lhs,
            final StringExpression rhs) {
        return new LessThan(lhs, rhs);
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            string_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final StringExpression lhs,
            final AllOrAnyExpression rhs) {
        return new LessThan(lhs, rhs);
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            datetime_expressionを表す文字列
     * @param rhs
     *            datetime_expressionを表す文字列
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final DatetimeExpression lhs,
            final DatetimeExpression rhs) {
        return new LessThan(lhs, rhs);
    }

    /**
     * <code>&lt;</code>を使った式を作成します．
     * 
     * @param lhs
     *            datetime_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression lt(final DatetimeExpression lhs,
            final AllOrAnyExpression rhs) {
        return new LessThan(lhs, rhs);
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            numeric_literalとなる数値
     * @return compairson_expression
     */
    public static ComparisonExpression le(final String lhs, final Number rhs) {
        return le(path(lhs), literal(rhs));
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            arithmetic_expression
     * @return compairson_expression
     */
    public static ComparisonExpression le(final String lhs,
            final ArithmeticExpression rhs) {
        return le(path(lhs), rhs);
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            string_literalとなる文字列
     * @return compairson_expression
     */
    public static ComparisonExpression le(final String lhs, final String rhs) {
        return le(path(lhs), literal(rhs));
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            string_expression
     * @return compairson_expression
     */
    public static ComparisonExpression le(final String lhs,
            final StringExpression rhs) {
        return le(path(lhs), rhs);
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            datetime_expression
     * @return compairson_expression
     */
    public static ComparisonExpression le(final String lhs,
            final DatetimeExpression rhs) {
        return le(path(lhs), rhs);
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            path_expression
     * @return compairson_expression
     */
    public static ComparisonExpression le(final String lhs,
            final PathExpression rhs) {
        return le(path(lhs), rhs);
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression le(final String lhs,
            final AllOrAnyExpression rhs) {
        return le(path(lhs), rhs);
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            numeric_literalとなる数値
     * @return compairson_expression
     */
    public static ComparisonExpression le(final PathExpression lhs,
            final Number rhs) {
        return le(lhs, literal(rhs));
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            arithmetic_expression
     * @return compairson_expression
     */
    public static ComparisonExpression le(final PathExpression lhs,
            final ArithmeticExpression rhs) {
        return new LessOrEqual(lhs, rhs);
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            string_literalとなる文字列
     * @return compairson_expression
     */
    public static ComparisonExpression le(final PathExpression lhs,
            final String rhs) {
        return le(lhs, literal(rhs));
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            string_expression
     * @return compairson_expression
     */
    public static ComparisonExpression le(final PathExpression lhs,
            final StringExpression rhs) {
        return new LessOrEqual(lhs, rhs);
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            datetime_expression
     * @return compairson_expression
     */
    public static ComparisonExpression le(final PathExpression lhs,
            final DatetimeExpression rhs) {
        return new LessOrEqual(lhs, rhs);
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            path_expression
     * @return compairson_expression
     */
    public static ComparisonExpression le(final PathExpression lhs,
            final PathExpression rhs) {
        return new LessOrEqual(lhs, rhs);
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression le(final PathExpression lhs,
            final AllOrAnyExpression rhs) {
        return new LessOrEqual(lhs, rhs);
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_expression
     * @param rhs
     *            numeric_literalとなる数値
     * @return compairson_expression
     */
    public static ComparisonExpression le(final ArithmeticExpression lhs,
            final Number rhs) {
        return le(lhs, literal(rhs));
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_expression
     * @param rhs
     *            arithmetic_expression
     * @return compairson_expression
     */
    public static ComparisonExpression le(final ArithmeticExpression lhs,
            final ArithmeticExpression rhs) {
        return new LessOrEqual(lhs, rhs);
    }

    /**
     * <code>lt&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression le(final ArithmeticExpression lhs,
            final AllOrAnyExpression rhs) {
        return new LessOrEqual(lhs, rhs);
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            string_expression
     * @param rhs
     *            string_literalとなる文字列
     * @return compairson_expression
     */
    public static ComparisonExpression le(final StringExpression lhs,
            final String rhs) {
        return lt(lhs, literal(rhs));
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            string_expression
     * @param rhs
     *            string_expression
     * @return compairson_expression
     */
    public static ComparisonExpression le(final StringExpression lhs,
            final StringExpression rhs) {
        return new LessOrEqual(lhs, rhs);
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            string_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression le(final StringExpression lhs,
            final AllOrAnyExpression rhs) {
        return new LessOrEqual(lhs, rhs);
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            datetime_expressionを表す文字列
     * @param rhs
     *            datetime_expressionを表す文字列
     * @return compairson_expression
     */
    public static ComparisonExpression le(final DatetimeExpression lhs,
            final DatetimeExpression rhs) {
        return new LessOrEqual(lhs, rhs);
    }

    /**
     * <code>&lt;=</code>を使った式を作成します．
     * 
     * @param lhs
     *            datetime_expression
     * @param rhs
     *            all_or_any_expression
     * @return compairson_expression
     */
    public static ComparisonExpression le(final DatetimeExpression lhs,
            final AllOrAnyExpression rhs) {
        return new LessOrEqual(lhs, rhs);
    }

    /**
     * 単項の<code>+</code>を使った式を作成します．
     * 
     * @param operand
     *            path_expressionを表す文字列
     * @return arithmetic_factor
     */
    public static ArithmeticFactor plus(final String operand) {
        return plus(path(operand));
    }

    /**
     * 単項の<code>+</code>を使った式を作成します．
     * 
     * @param operand
     *            arithmetic_primary
     * @return arithmetic_factor
     */
    public static ArithmeticFactor plus(final ArithmeticPrimary operand) {
        return new UnaryPlus(operand);
    }

    /**
     * 単項の<code>-</code>を使った式を作成します．
     * 
     * @param operand
     *            path_expressionを表す文字列
     * @return arithmetic_factor
     */
    public static ArithmeticFactor minus(final String operand) {
        return minus(path(operand));
    }

    /**
     * 単項の<code>-</code>を使った式を作成します．
     * 
     * @param operand
     *            arithmetic_primary
     * @return arithmetic_factor
     */
    public static ArithmeticFactor minus(final ArithmeticPrimary operand) {
        return new UnaryMinus(operand);
    }

    /**
     * 二項の<code>+</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            path_expressionを表す文字列
     * @return simple_arithmetic_expression
     */
    public static SimpleArithmeticExpression add(final String lhs,
            final String rhs) {
        return add(path(lhs), path(rhs));
    }

    /**
     * 二項の<code>+</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            numeric_literalとなる数値
     * @return simple_arithmetic_expression
     */
    public static SimpleArithmeticExpression add(final String lhs,
            final Number rhs) {
        return add(path(lhs), literal(rhs));
    }

    /**
     * 二項の<code>+</code>を使った式を作成します．
     * 
     * @param lhs
     *            numeric_literalとなる数値
     * @param rhs
     *            path_expressionを表す文字列
     * @return simple_arithmetic_expression
     */
    public static SimpleArithmeticExpression add(final Number lhs,
            final String rhs) {
        return add(literal(lhs), path(rhs));
    }

    /**
     * 二項の<code>+</code>を使った式を作成します．
     * 
     * @param lhs
     *            numeric_literalとなる数値
     * @param rhs
     *            numeric_literalとなる数値
     * @return simple_arithmetic_expression
     */
    public static SimpleArithmeticExpression add(final Number lhs,
            final Number rhs) {
        return add(literal(lhs), literal(rhs));
    }

    /**
     * 二項の<code>+</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            simple_arithmetic_expression
     * @return simple_arithmetic_expression
     */
    public static SimpleArithmeticExpression add(final String lhs,
            final SimpleArithmeticExpression rhs) {
        return add(path(lhs), rhs);
    }

    /**
     * 二項の<code>+</code>を使った式を作成します．
     * 
     * @param lhs
     *            simple_arithmetic_expression
     * @param rhs
     *            path_expressionを表す文字列
     * @return simple_arithmetic_expression
     */
    public static SimpleArithmeticExpression add(
            final SimpleArithmeticExpression lhs, final String rhs) {
        return add(lhs, path(rhs));
    }

    /**
     * 二項の<code>+</code>を使った式を作成します．
     * 
     * @param lhs
     *            numeric_literalとなる数値
     * @param rhs
     *            simple_arithmetic_expression
     * @return simple_arithmetic_expression
     */
    public static SimpleArithmeticExpression add(final Number lhs,
            final SimpleArithmeticExpression rhs) {
        return add(literal(lhs), rhs);
    }

    /**
     * 二項の<code>+</code>を使った式を作成します．
     * 
     * @param lhs
     *            simple_arithmetic_expression
     * @param rhs
     *            numeric_literalとなる数値
     * @return simple_arithmetic_expression
     */
    public static SimpleArithmeticExpression add(
            final SimpleArithmeticExpression lhs, final Number rhs) {
        return add(lhs, literal(rhs));
    }

    /**
     * 二項の<code>+</code>を使った式を作成します．
     * 
     * @param lhs
     *            simple_arithmetic_expression
     * @param rhs
     *            simple_arithmetic_expression
     * @return simple_arithmetic_expression
     */
    public static SimpleArithmeticExpression add(
            final SimpleArithmeticExpression lhs,
            final SimpleArithmeticExpression rhs) {
        return new Addition(lhs, rhs);
    }

    /**
     * 二項の<code>-</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            path_expressionを表す文字列
     * @return simple_arithmetic_expression
     */
    public static SimpleArithmeticExpression subtract(final String lhs,
            final String rhs) {
        return subtract(path(lhs), path(rhs));
    }

    /**
     * 二項の<code>-</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            numeric_literalとなる数値
     * @return simple_arithmetic_expression
     */
    public static SimpleArithmeticExpression subtract(final String lhs,
            final Number rhs) {
        return subtract(path(lhs), literal(rhs));
    }

    /**
     * 二項の<code>-</code>を使った式を作成します．
     * 
     * @param lhs
     *            numeric_literalとなる数値
     * @param rhs
     *            path_expressionを表す文字列
     * @return simple_arithmetic_expression
     */
    public static SimpleArithmeticExpression subtract(final Number lhs,
            final String rhs) {
        return subtract(literal(lhs), path(rhs));
    }

    /**
     * 二項の<code>-</code>を使った式を作成します．
     * 
     * @param lhs
     *            numeric_literalとなる数値
     * @param rhs
     *            numeric_literalとなる数値
     * @return simple_arithmetic_expression
     */
    public static SimpleArithmeticExpression subtract(final Number lhs,
            final Number rhs) {
        return subtract(literal(lhs), literal(rhs));
    }

    /**
     * 二項の<code>-</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            simple_arithmetic_expression
     * @return simple_arithmetic_expression
     */
    public static SimpleArithmeticExpression subtract(final String lhs,
            final SimpleArithmeticExpression rhs) {
        return subtract(path(lhs), rhs);
    }

    /**
     * 二項の<code>-</code>を使った式を作成します．
     * 
     * @param lhs
     *            simple_arithmetic_expression
     * @param rhs
     *            path_expressionを表す文字列
     * @return simple_arithmetic_expression
     */
    public static SimpleArithmeticExpression subtract(
            final SimpleArithmeticExpression lhs, final String rhs) {
        return subtract(lhs, path(rhs));
    }

    /**
     * 二項の<code>-</code>を使った式を作成します．
     * 
     * @param lhs
     *            numeric_literalとなる数値
     * @param rhs
     *            simple_arithmetic_expression
     * @return simple_arithmetic_expression
     */
    public static SimpleArithmeticExpression subtract(final Number lhs,
            final SimpleArithmeticExpression rhs) {
        return subtract(literal(lhs), rhs);
    }

    /**
     * 二項の<code>-</code>を使った式を作成します．
     * 
     * @param lhs
     *            simple_arithmetic_expression
     * @param rhs
     *            numeric_literalとなる数値
     * @return simple_arithmetic_expression
     */
    public static SimpleArithmeticExpression subtract(
            final SimpleArithmeticExpression lhs, final Number rhs) {
        return subtract(lhs, literal(rhs));
    }

    /**
     * 二項の<code>-</code>を使った式を作成します．
     * 
     * @param lhs
     *            simple_arithmetic_expression
     * @param rhs
     *            simple_arithmetic_expression
     * @return simple_arithmetic_expression
     */
    public static SimpleArithmeticExpression subtract(
            final SimpleArithmeticExpression lhs,
            final SimpleArithmeticExpression rhs) {
        return new Subtraction(lhs, rhs);
    }

    /**
     * 二項の<code>*</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            path_expressionを表す文字列
     * @return arithmetic_term
     */
    public static ArithmeticTerm multiply(final String lhs, final String rhs) {
        return multiply(path(lhs), path(rhs));
    }

    /**
     * 二項の<code>*</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            numeric_literalとなる数値
     * @return arithmetic_term
     */
    public static ArithmeticTerm multiply(final String lhs, final Number rhs) {
        return multiply(path(lhs), literal(rhs));
    }

    /**
     * 二項の<code>*</code>を使った式を作成します．
     * 
     * @param lhs
     *            numeric_literalとなる数値
     * @param rhs
     *            path_expressionを表す文字列
     * @return arithmetic_term
     */
    public static ArithmeticTerm multiply(final Number lhs, final String rhs) {
        return multiply(literal(lhs), path(rhs));
    }

    /**
     * 二項の<code>*</code>を使った式を作成します．
     * 
     * @param lhs
     *            numeric_literalとなる数値
     * @param rhs
     *            numeric_literalとなる数値
     * @return arithmetic_term
     */
    public static ArithmeticTerm multiply(final Number lhs, final Number rhs) {
        return multiply(literal(lhs), literal(rhs));
    }

    /**
     * 二項の<code>*</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            arithmetic_factor
     * @return arithmetic_term
     */
    public static ArithmeticTerm multiply(final String lhs,
            final ArithmeticFactor rhs) {
        return multiply(path(lhs), rhs);
    }

    /**
     * 二項の<code>*</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_term
     * @param rhs
     *            path_expressionを表す文字列
     * @return arithmetic_term
     */
    public static ArithmeticTerm multiply(final ArithmeticTerm lhs,
            final String rhs) {
        return multiply(lhs, path(rhs));
    }

    /**
     * 二項の<code>*</code>を使った式を作成します．
     * 
     * @param lhs
     *            numeric_literalとなる数値
     * @param rhs
     *            arithmetic_factor
     * @return arithmetic_term
     */
    public static ArithmeticTerm multiply(final Number lhs,
            final ArithmeticFactor rhs) {
        return multiply(literal(lhs), rhs);
    }

    /**
     * 二項の<code>*</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_term
     * @param rhs
     *            numeric_literalとなる数値
     * @return arithmetic_term
     */
    public static ArithmeticTerm multiply(final ArithmeticTerm lhs,
            final Number rhs) {
        return multiply(lhs, literal(rhs));
    }

    /**
     * 二項の<code>*</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_term
     * @param rhs
     *            arithmetic_factor
     * @return arithmetic_term
     */
    public static ArithmeticTerm multiply(final ArithmeticTerm lhs,
            final ArithmeticFactor rhs) {
        return new Multiplication(lhs, rhs);
    }

    /**
     * 二項の<code>/</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            path_expressionを表す文字列
     * @return arithmetic_term
     */
    public static ArithmeticTerm divide(final String lhs, final String rhs) {
        return divide(path(lhs), path(rhs));
    }

    /**
     * 二項の<code>/</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            numeric_literalとなる数値
     * @return arithmetic_term
     */
    public static ArithmeticTerm divide(final String lhs, final Number rhs) {
        return divide(path(lhs), literal(rhs));
    }

    /**
     * 二項の<code>/</code>を使った式を作成します．
     * 
     * @param lhs
     *            numeric_literalとなる数値
     * @param rhs
     *            path_expressionを表す文字列
     * @return arithmetic_term
     */
    public static ArithmeticTerm divide(final Number lhs, final String rhs) {
        return divide(literal(lhs), path(rhs));
    }

    /**
     * 二項の<code>/</code>を使った式を作成します．
     * 
     * @param lhs
     *            numeric_literalとなる数値
     * @param rhs
     *            numeric_literalとなる数値
     * @return arithmetic_term
     */
    public static ArithmeticTerm divide(final Number lhs, final Number rhs) {
        return divide(literal(lhs), literal(rhs));
    }

    /**
     * 二項の<code>/</code>を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            arithmetic_factor
     * @return arithmetic_term
     */
    public static ArithmeticTerm divide(final String lhs,
            final ArithmeticFactor rhs) {
        return divide(path(lhs), rhs);
    }

    /**
     * 二項の<code>/</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_term
     * @param rhs
     *            path_expressionを表す文字列
     * @return arithmetic_term
     */
    public static ArithmeticTerm divide(final ArithmeticTerm lhs,
            final String rhs) {
        return divide(lhs, path(rhs));
    }

    /**
     * 二項の<code>/</code>を使った式を作成します．
     * 
     * @param lhs
     *            numeric_literalとなる数値
     * @param rhs
     *            arithmetic_factor
     * @return arithmetic_term
     */
    public static ArithmeticTerm divide(final Number lhs,
            final ArithmeticFactor rhs) {
        return divide(literal(lhs), rhs);
    }

    /**
     * 二項の<code>/</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_term
     * @param rhs
     *            numeric_literalとなる数値
     * @return arithmetic_term
     */
    public static ArithmeticTerm divide(final ArithmeticTerm lhs,
            final Number rhs) {
        return divide(lhs, literal(rhs));
    }

    /**
     * 二項の<code>/</code>を使った式を作成します．
     * 
     * @param lhs
     *            arithmetic_term
     * @param rhs
     *            arithmetic_factor
     * @return arithmetic_term
     */
    public static ArithmeticTerm divide(final ArithmeticTerm lhs,
            final ArithmeticFactor rhs) {
        return new Division(lhs, rhs);
    }

    /**
     * <code>LENGTH</code>関数を使った式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @return function_returning_numerics
     */
    public static FunctionReturningNumerics length(final String string) {
        return length(path(string));
    }

    /**
     * <code>LENGTH</code>関数を使った式を作成します．
     * 
     * @param string
     *            string_primary
     * @return function_returning_numerics
     */
    public static FunctionReturningNumerics length(final StringPrimary string) {
        return new Length(string);
    }

    /**
     * <code>LOCATE</code>関数を使った式を作成します．
     * 
     * @param located
     *            string_literalとなる文字列
     * @param searched
     *            path_expressionを表す文字列
     * @return function_returning_numerics
     */
    public static FunctionReturningNumerics locate(final String located,
            final String searched) {
        return locate(literal(located), path(searched));
    }

    /**
     * <code>LOCATE</code>関数を使った式を作成します．
     * 
     * @param located
     *            string_literalとなる文字列
     * @param searched
     *            string_primary
     * @return function_returning_numerics
     */
    public static FunctionReturningNumerics locate(final String located,
            final StringPrimary searched) {
        return locate(literal(located), searched);
    }

    /**
     * <code>LOCATE</code>関数を使った式を作成します．
     * 
     * @param located
     *            string_primary
     * @param searched
     *            path_expressionを表す文字列
     * @return function_returning_numerics
     */
    public static FunctionReturningNumerics locate(final StringPrimary located,
            final String searched) {
        return locate(located, path(searched));
    }

    /**
     * <code>LOCATE</code>関数を使った式を作成します．
     * 
     * @param located
     *            string_primary
     * @param searched
     *            string_primary
     * @return function_returning_numerics
     */
    public static FunctionReturningNumerics locate(final StringPrimary located,
            final StringPrimary searched) {
        return new Locate(located, searched);
    }

    /**
     * <code>LOCATE</code>関数を使った式を作成します．
     * 
     * @param located
     *            path_expressionを表す文字列
     * @param searched
     *            string_literalとなる数値文字列
     * @param start
     *            numeric_literalとなる数値
     * @return function_returning_numerics
     */
    public static FunctionReturningNumerics locate(final String located,
            final String searched, final int start) {
        return locate(literal(located), path(searched), literal(start));
    }

    /**
     * <code>LOCATE</code>関数を使った式を作成します．
     * 
     * @param located
     *            path_expressionを表す文字列
     * @param searched
     *            string_literalとなる数値文字列
     * @param start
     *            simple_arithmetic_expression
     * @return function_returning_numerics
     */
    public static FunctionReturningNumerics locate(final String located,
            final String searched, final SimpleArithmeticExpression start) {
        return locate(literal(located), path(searched), start);
    }

    /**
     * <code>LOCATE</code>関数を使った式を作成します．
     * 
     * @param located
     *            string_literalとなる文字列
     * @param searched
     *            string_primary
     * @param start
     *            numeric_literalとなる数値
     * @return function_returning_numerics
     */
    public static FunctionReturningNumerics locate(final String located,
            final StringPrimary searched, final int start) {
        return locate(literal(located), searched, literal(start));
    }

    /**
     * <code>LOCATE</code>関数を使った式を作成します．
     * 
     * @param located
     *            string_literalとなる文字列
     * @param searched
     *            string_primary
     * @param start
     *            simple_arithmetic_expression
     * @return function_returning_numerics
     */
    public static FunctionReturningNumerics locate(final String located,
            final StringPrimary searched, final SimpleArithmeticExpression start) {
        return locate(literal(located), searched, start);
    }

    /**
     * <code>LOCATE</code>関数を使った式を作成します．
     * 
     * @param located
     *            string_primary
     * @param searched
     *            string_literalとなる文字列
     * @param start
     *            numeric_literalとなる数値
     * @return function_returning_numerics
     */
    public static FunctionReturningNumerics locate(final StringPrimary located,
            final String searched, final int start) {
        return locate(located, path(searched), literal(start));
    }

    /**
     * <code>LOCATE</code>関数を使った式を作成します．
     * 
     * @param located
     *            string_primary
     * @param searched
     *            string_literalとなる文字列
     * @param start
     *            simple_arithmetic_expression
     * @return function_returning_numerics
     */
    public static FunctionReturningNumerics locate(final StringPrimary located,
            final String searched, final SimpleArithmeticExpression start) {
        return locate(located, path(searched), start);
    }

    /**
     * <code>LOCATE</code>関数を使った式を作成します．
     * 
     * @param located
     *            string_primary
     * @param searched
     *            string_primary
     * @param start
     *            simple_arithmetic_expression
     * @return function_returning_numerics
     */
    public static FunctionReturningNumerics locate(final StringPrimary located,
            final StringPrimary searched, final int start) {
        return locate(located, searched, literal(start));
    }

    /**
     * <code>LOCATE</code>関数を使った式を作成します．
     * 
     * @param located
     *            string_primary
     * @param searched
     *            string_primary
     * @param start
     *            simple_arithmetic_expression
     * @return function_returning_numerics
     */
    public static FunctionReturningNumerics locate(final StringPrimary located,
            final StringPrimary searched, final SimpleArithmeticExpression start) {
        return new Locate(located, searched, start);
    }

    /**
     * <code>ABS</code>関数を使った式を作成します．
     * 
     * @param number
     *            path_expressionを表す文字列
     * @return funtion_returning_numerics
     */
    public static FunctionReturningNumerics abs(final String number) {
        return abs(path(number));
    }

    /**
     * <code>ABS</code>関数を使った式を作成します．
     * 
     * @param number
     *            simple_arithmetic_numerics
     * @return funtion_returning_numerics
     */
    public static FunctionReturningNumerics abs(
            final SimpleArithmeticExpression number) {
        return new Abs(number);
    }

    /**
     * <code>SQRT</code>関数を使った式を作成します．
     * 
     * @param number
     *            path_expressionを表す文字列
     * @return funtion_returning_numerics
     */
    public static FunctionReturningNumerics sqrt(final String number) {
        return sqrt(path(number));
    }

    /**
     * <code>SQRT</code>関数を使った式を作成します．
     * 
     * @param number
     *            simple_arithmetic_expression
     * @return funtion_returning_numerics
     */
    public static FunctionReturningNumerics sqrt(
            final SimpleArithmeticExpression number) {
        return new Sqrt(number);
    }

    /**
     * <code>MOD</code>関数を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            path_expressionを表す文字列
     * @return funtion_returning_numerics
     */
    public static FunctionReturningNumerics mod(final String lhs,
            final String rhs) {
        return mod(path(lhs), path(rhs));
    }

    /**
     * <code>MOD</code>関数を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            numeric_literalとなる数値
     * @return funtion_returning_numerics
     */
    public static FunctionReturningNumerics mod(final String lhs,
            final Number rhs) {
        return mod(path(lhs), literal(rhs));
    }

    /**
     * <code>MOD</code>関数を使った式を作成します．
     * 
     * @param lhs
     *            numeric_literalとなる数値
     * @param rhs
     *            path_expressionを表す文字列
     * @return funtion_returning_numerics
     */
    public static FunctionReturningNumerics mod(final Number lhs,
            final String rhs) {
        return mod(literal(lhs), path(rhs));
    }

    /**
     * <code>MOD</code>関数を使った式を作成します．
     * 
     * @param lhs
     *            numeric_literalとなる数値
     * @param rhs
     *            numeric_literalとなる数値
     * @return funtion_returning_numerics
     */
    public static FunctionReturningNumerics mod(final Number lhs,
            final Number rhs) {
        return mod(literal(lhs), literal(rhs));
    }

    /**
     * <code>MOD</code>関数を使った式を作成します．
     * 
     * @param lhs
     *            path_expressionを表す文字列
     * @param rhs
     *            simple_arithmetic_expression
     * @return funtion_returning_numerics
     */
    public static FunctionReturningNumerics mod(final String lhs,
            final SimpleArithmeticExpression rhs) {
        return mod(path(lhs), rhs);
    }

    /**
     * <code>MOD</code>関数を使った式を作成します．
     * 
     * @param lhs
     *            simple_arithmetic_expression
     * @param rhs
     *            path_expressionを表す文字列
     * @return funtion_returning_numerics
     */
    public static FunctionReturningNumerics mod(
            final SimpleArithmeticExpression lhs, final String rhs) {
        return mod(lhs, path(rhs));
    }

    /**
     * <code>MOD</code>関数を使った式を作成します．
     * 
     * @param lhs
     *            numeric_literalとなる数値
     * @param rhs
     *            simple_arithmetic_expression
     * @return funtion_returning_numerics
     */
    public static FunctionReturningNumerics mod(final Number lhs,
            final SimpleArithmeticExpression rhs) {
        return mod(literal(lhs), rhs);
    }

    /**
     * <code>MOD</code>関数を使った式を作成します．
     * 
     * @param lhs
     *            simple_arithmetic_expression
     * @param rhs
     *            numeric_literalとなる数値
     * @return funtion_returning_numerics
     */
    public static FunctionReturningNumerics mod(
            final SimpleArithmeticExpression lhs, final Number rhs) {
        return mod(lhs, literal(rhs));
    }

    /**
     * <code>MOD</code>関数を使った式を作成します．
     * 
     * @param lhs
     *            simple_arithmetic_expression
     * @param rhs
     *            simple_arithmetic_expression
     * @return funtion_returning_numerics
     */
    public static FunctionReturningNumerics mod(
            final SimpleArithmeticExpression lhs,
            final SimpleArithmeticExpression rhs) {
        return new Mod(lhs, rhs);
    }

    /**
     * <code>SIZE</code>関数を使った式を作成します．
     * 
     * @param collection
     *            path_expressionを表す文字列
     * @return funtion_returning_numerics
     */
    public static FunctionReturningNumerics size(final String collection) {
        return size(path(collection));
    }

    /**
     * <code>SIZE</code>関数を使った式を作成します．
     * 
     * @param collection
     *            path_expression
     * @return funtion_returning_numerics
     */
    public static FunctionReturningNumerics size(final PathExpression collection) {
        return new Size(collection);
    }

    /**
     * <code>CURRENT_DATE</code>関数を使った式を作成します．
     * 
     * @return funtion_returning_datetime
     */
    public static FunctionReturningDatetime currentDate() {
        return new CurrentDate();
    }

    /**
     * <code>CURRENT_TIME</code>関数を使った式を作成します．
     * 
     * @return funtion_returning_datetime
     */
    public static FunctionReturningDatetime currentTime() {
        return new CurrentTime();
    }

    /**
     * <code>CURRENT_TIMESTAMP</code>関数を使った式を作成します．
     * 
     * @return funtion_returning_datetime
     */
    public static FunctionReturningDatetime currentTimestamp() {
        return new CurrentTimestamp();
    }

    /**
     * <code>CONCAT</code>関数を使った式を作成します．
     * 
     * @param string1
     *            path_expressionを表す文字列
     * @param string2
     *            string_literalとなる文字列
     * @return function_returing_strings
     */
    public static FunctionReturningStrings concat(final String string1,
            final String string2) {
        return concat(path(string1), literal(string2));
    }

    /**
     * <code>CONCAT</code>関数を使った式を作成します．
     * 
     * @param string1
     *            path_expressionを表す文字列
     * @param string2
     *            string_primary
     * @param rest
     *            string_primary
     * @return function_returing_strings
     */
    public static FunctionReturningStrings concat(final String string1,
            final StringPrimary string2, final StringPrimary... rest) {
        return concat(path(string1), string2, rest);
    }

    /**
     * <code>CONCAT</code>関数を使った式を作成します．
     * 
     * @param string1
     *            string_primary
     * @param string2
     *            string_literalとなる文字列
     * @return function_returing_strings
     */
    public static FunctionReturningStrings concat(final StringPrimary string1,
            final String string2) {
        return new Concat(string1, literal(string2));
    }

    /**
     * <code>CONCAT</code>関数を使った式を作成します．
     * 
     * @param string1
     *            string_primary
     * @param string2
     *            string_primary
     * @param rest
     *            string_primary
     * @return function_returing_strings
     */
    public static FunctionReturningStrings concat(final StringPrimary string1,
            final StringPrimary string2, final StringPrimary... rest) {
        Concat concat = new Concat(string1, string2);
        for (final StringPrimary primary : rest) {
            concat = new Concat(concat, primary);
        }
        return concat;
    }

    /**
     * <code>SUBSTRING</code>関数を使った式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param start
     *            numeric_literalとなる数値
     * @param length
     *            numeric_literalとなる数値
     * @return function_returing_strings
     */
    public static FunctionReturningStrings substring(final String string,
            final Number start, final Number length) {
        return new Substring(path(string), literal(start), literal(length));
    }

    /**
     * <code>SUBSTRING</code>関数を使った式を作成します．
     * 
     * @param string
     *            path_expressionを表す文字列
     * @param start
     *            simple_arithmetic_expression
     * @param length
     *            simple_arithmetic_expression
     * @return function_returing_strings
     */
    public static FunctionReturningStrings substring(final String string,
            final SimpleArithmeticExpression start,
            final SimpleArithmeticExpression length) {
        return new Substring(path(string), start, length);
    }

    /**
     * <code>SUBSTRING</code>関数を使った式を作成します．
     * 
     * @param string
     *            string_primary
     * @param start
     *            numeric_literalとなる数値
     * @param length
     *            numeric_literalとなる数値
     * @return function_returing_strings
     */
    public static FunctionReturningStrings substring(
            final StringPrimary string, final Number start, final Number length) {
        return new Substring(string, literal(start), literal(length));
    }

    /**
     * <code>SUBSTRING</code>関数を使った式を作成します．
     * 
     * @param string
     *            string_primary
     * @param start
     *            simple_arithmetic_expression
     * @param length
     *            simple_arithmetic_expression
     * @return function_returing_strings
     */
    public static FunctionReturningStrings substring(
            final StringPrimary string, final SimpleArithmeticExpression start,
            final SimpleArithmeticExpression length) {
        return new Substring(string, start, length);
    }

    /**
     * <code>TRIM</code>関数を使った式を作成します．
     * 
     * @param trimSource
     *            path_expressionを表す文字列
     * @return function_returing_strings
     */
    public static FunctionReturningStrings trim(final String trimSource) {
        return trim(path(trimSource));
    }

    /**
     * <code>TRIM</code>関数を使った式を作成します．
     * 
     * @param trimSource
     *            string_primary
     * @return function_returing_strings
     */
    public static FunctionReturningStrings trim(final StringPrimary trimSource) {
        return new Trim(trimSource);
    }

    /**
     * <code>TRIM</code>関数を使った式を作成します．
     * 
     * @param trimSpecification
     *            trim_specification
     * @param trimSource
     *            path_expressionを表す文字列
     * @return function_returing_strings
     */
    public static FunctionReturningStrings trim(
            final TrimSpecification trimSpecification, final String trimSource) {
        return trim(trimSpecification, path(trimSource));
    }

    /**
     * <code>TRIM</code>関数を使った式を作成します．
     * 
     * @param trimSpecification
     *            trim_specification
     * @param trimSource
     *            string_primary
     * @return function_returing_strings
     */
    public static FunctionReturningStrings trim(
            final TrimSpecification trimSpecification,
            final StringPrimary trimSource) {
        return new Trim(trimSpecification, trimSource);
    }

    /**
     * <code>TRIM</code>関数を使った式を作成します．
     * 
     * @param trimCharacter
     *            trim_character
     * @param trimSource
     *            path_expressionを表す文字列
     * @return function_returing_strings
     */
    public static FunctionReturningStrings trim(final char trimCharacter,
            final String trimSource) {
        return trim(trimCharacter, path(trimSource));
    }

    /**
     * <code>TRIM</code>関数を使った式を作成します．
     * 
     * @param trimCharacter
     *            trim_character
     * @param trimSource
     *            string_primary
     * @return function_returing_strings
     */
    public static FunctionReturningStrings trim(final char trimCharacter,
            final StringPrimary trimSource) {
        return new Trim(trimCharacter, trimSource);
    }

    /**
     * <code>TRIM</code>関数を使った式を作成します．
     * 
     * @param trimSpecification
     *            trim_specification
     * @param trimCharacter
     *            trim_character
     * @param trimSource
     *            path_expressionを表す文字列
     * @return function_returing_strings
     */
    public static FunctionReturningStrings trim(
            final TrimSpecification trimSpecification,
            final char trimCharacter, final String trimSource) {
        return trim(trimSpecification, trimCharacter, path(trimSource));
    }

    /**
     * <code>TRIM</code>関数を使った式を作成します．
     * 
     * @param trimSpecification
     *            trim_specification
     * @param trimCharacter
     *            trim_character
     * @param trimSource
     *            string_primary
     * @return function_returing_strings
     */
    public static FunctionReturningStrings trim(
            final TrimSpecification trimSpecification,
            final char trimCharacter, final StringPrimary trimSource) {
        return new Trim(trimSpecification, trimCharacter, trimSource);
    }

    /**
     * <code>LOWER</code>関数を使った式を作成します．
     * 
     * @param source
     *            path_expressionを表す文字列
     * @return function_returing_strings
     */
    public static FunctionReturningStrings lower(final String source) {
        return lower(path(source));
    }

    /**
     * <code>LOWER</code>関数を使った式を作成します．
     * 
     * @param source
     *            string_primary
     * @return function_returing_strings
     */
    public static FunctionReturningStrings lower(final StringPrimary source) {
        return new Lower(source);
    }

    /**
     * <code>LOWER</code>関数を使った式を作成します．
     * 
     * @param source
     *            path_expressionを表す文字列
     * @return function_returing_strings
     */
    public static FunctionReturningStrings upper(final String source) {
        return upper(path(source));
    }

    /**
     * <code>LOWER</code>関数を使った式を作成します．
     * 
     * @param source
     *            string_primary
     * @return function_returing_strings
     */
    public static FunctionReturningStrings upper(final StringPrimary source) {
        return new Upper(source);
    }

    /**
     * 昇順の<code>ORDER BY</code>項目を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @return orderby_item
     */
    public static OrderbyItem asc(final String path) {
        return asc(path(path));
    }

    /**
     * 昇順の<code>ORDER BY</code>項目を作成します．
     * 
     * @param path
     *            path_expression
     * @return orderby_item
     */
    public static OrderbyItem asc(final PathExpression path) {
        return new OrderbyItemImpl(path);
    }

    /**
     * 降順の<code>ORDER BY</code>項目を作成します．
     * 
     * @param path
     *            path_expressionを表す文字列
     * @return orderby_item
     */
    public static OrderbyItem desc(final String path) {
        return desc(path(path));
    }

    /**
     * 降順の<code>ORDER BY</code>項目を作成します．
     * 
     * @param path
     *            path_expression
     * @return orderby_item
     */
    public static OrderbyItem desc(final PathExpression path) {
        return new OrderbyItemImpl(path, OrderingSpec.DESC);
    }

}
