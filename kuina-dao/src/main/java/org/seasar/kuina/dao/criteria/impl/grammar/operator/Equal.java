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
package org.seasar.kuina.dao.criteria.impl.grammar.operator;

import org.seasar.kuina.dao.criteria.grammar.AllOrAnyExpression;
import org.seasar.kuina.dao.criteria.grammar.ArithmeticExpression;
import org.seasar.kuina.dao.criteria.grammar.BooleanExpression;
import org.seasar.kuina.dao.criteria.grammar.ComparisonExpression;
import org.seasar.kuina.dao.criteria.grammar.DatetimeExpression;
import org.seasar.kuina.dao.criteria.grammar.EntityExpression;
import org.seasar.kuina.dao.criteria.grammar.EnumExpression;
import org.seasar.kuina.dao.criteria.grammar.PathExpression;
import org.seasar.kuina.dao.criteria.grammar.StringExpression;

/**
 * JPQLの2項<code>=</code>演算子を表すクラスです．
 * 
 * @author koichik
 */
public class Equal extends AbstractBinaryOperator implements
        ComparisonExpression {

    /**
     * インスタンスを構築します。
     * 
     * @param lhs
     *            左辺を表すpath_expression
     * @param rhs
     *            右辺を表すpath_expression
     */
    public Equal(final PathExpression lhs, final PathExpression rhs) {
        super(" = ", lhs, rhs);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param lhs
     *            左辺を表すpath_expression
     * @param rhs
     *            右辺を表すall_or_any_expression
     */
    public Equal(final PathExpression lhs, final AllOrAnyExpression rhs) {
        super(" = ", lhs, rhs);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param lhs
     *            左辺を表すarithmetic_expression
     * @param rhs
     *            右辺を表すarithmetic_expression
     */
    public Equal(final ArithmeticExpression lhs, final ArithmeticExpression rhs) {
        super(" = ", lhs, rhs);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param lhs
     *            左辺を表すarithmetic_expression
     * @param rhs
     *            右辺を表すall_or_any_expression
     */
    public Equal(final ArithmeticExpression lhs, final AllOrAnyExpression rhs) {
        super(" = ", lhs, rhs);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param lhs
     *            左辺を表すstring_expression
     * @param rhs
     *            右辺を表すstring_expression
     */
    public Equal(final StringExpression lhs, final StringExpression rhs) {
        super(" = ", lhs, rhs);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param lhs
     *            左辺を表すstring_expression
     * @param rhs
     *            右辺を表すall_or_any_expression
     */
    public Equal(final StringExpression lhs, final AllOrAnyExpression rhs) {
        super(" = ", lhs, rhs);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param lhs
     *            左辺を表すboolean_expression
     * @param rhs
     *            右辺を表すboolean_expression
     */
    public Equal(final BooleanExpression lhs, final BooleanExpression rhs) {
        super(" = ", lhs, rhs);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param lhs
     *            左辺を表すboolean_expression
     * @param rhs
     *            右辺を表すall_or_any_expression
     */
    public Equal(final BooleanExpression lhs, final AllOrAnyExpression rhs) {
        super(" = ", lhs, rhs);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param lhs
     *            左辺を表すenum_expression
     * @param rhs
     *            右辺を表すenum_expression
     */
    public Equal(final EnumExpression lhs, final EnumExpression rhs) {
        super(" = ", lhs, rhs);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param lhs
     *            左辺を表すenum_expression
     * @param rhs
     *            右辺を表すall_or_any_expression
     */
    public Equal(final EnumExpression lhs, final AllOrAnyExpression rhs) {
        super(" = ", lhs, rhs);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param lhs
     *            左辺を表すdatetime_expression
     * @param rhs
     *            右辺を表すdatetime_expression
     */
    public Equal(final DatetimeExpression lhs, final DatetimeExpression rhs) {
        super(" = ", lhs, rhs);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param lhs
     *            左辺を表すdatetime_expression
     * @param rhs
     *            右辺を表すall_or_any_expression
     */
    public Equal(final DatetimeExpression lhs, final AllOrAnyExpression rhs) {
        super(" = ", lhs, rhs);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param lhs
     *            左辺を表すentity_expression
     * @param rhs
     *            右辺を表すentity_expression
     */
    public Equal(final EntityExpression lhs, final EntityExpression rhs) {
        super(" = ", lhs, rhs);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param lhs
     *            左辺を表すentity_expression
     * @param rhs
     *            右辺を表すall_or_any_expression
     */
    public Equal(final EntityExpression lhs, final AllOrAnyExpression rhs) {
        super(" = ", lhs, rhs);
    }

}
