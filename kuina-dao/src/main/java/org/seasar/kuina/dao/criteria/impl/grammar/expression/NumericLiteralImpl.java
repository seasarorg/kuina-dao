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
package org.seasar.kuina.dao.criteria.impl.grammar.expression;

import org.seasar.framework.exception.SIllegalArgumentException;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.grammar.NumericLiteral;

/**
 * 
 * @author koichik
 */
public class NumericLiteralImpl implements NumericLiteral {
    protected final Number literal;

    /**
     * インスタンスを構築します。
     */
    public NumericLiteralImpl(final Number literal) {
        if (literal == null) {
            throw new SIllegalArgumentException("EKuinaDao0001",
                    new Object[] { "literal" });
        }
        this.literal = literal;
    }

    public void evaluate(final CriteriaContext context) {
        context.append(literal);
    }

}
