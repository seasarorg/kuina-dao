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

import org.seasar.kuina.dao.criteria.grammar.InputParameter;
import org.seasar.kuina.dao.criteria.grammar.NullComparisonExpression;
import org.seasar.kuina.dao.criteria.grammar.PathExpression;

/**
 * 
 * @author koichik
 */
public class IsNull extends AbstractUnaryPostfixOperator implements
        NullComparisonExpression {

    /**
     * インスタンスを構築します。
     */
    public IsNull(final PathExpression operand) {
        super(" IS NULL", operand);
    }

    /**
     * インスタンスを構築します。
     */
    public IsNull(final InputParameter operand) {
        super(" IS NULL", operand);
    }

}
