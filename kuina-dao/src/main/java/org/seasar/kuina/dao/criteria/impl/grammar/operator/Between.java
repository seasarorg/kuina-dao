/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import org.seasar.kuina.dao.criteria.grammar.ArithmeticExpression;
import org.seasar.kuina.dao.criteria.grammar.DatetimeExpression;
import org.seasar.kuina.dao.criteria.grammar.PathExpression;
import org.seasar.kuina.dao.criteria.grammar.StringExpression;

/**
 * 
 * @author koichik
 */
public class Between extends AbstractBetween {

    protected static final String OPERATOR = " BETWEEN ";

    /**
     * インスタンスを構築します。
     */
    public Between(final PathExpression operand, final PathExpression from,
            final PathExpression to) {
        super(OPERATOR, operand, from, to);
    }

    /**
     * インスタンスを構築します。
     */
    public Between(final ArithmeticExpression operand,
            final ArithmeticExpression from, final ArithmeticExpression to) {
        super(OPERATOR, operand, from, to);
    }

    /**
     * インスタンスを構築します。
     */
    public Between(final StringExpression operand, final StringExpression from,
            final StringExpression to) {
        super(OPERATOR, operand, from, to);
    }

    /**
     * インスタンスを構築します。
     */
    public Between(final DatetimeExpression operand,
            final DatetimeExpression from, final DatetimeExpression to) {
        super(OPERATOR, operand, from, to);
    }

}
