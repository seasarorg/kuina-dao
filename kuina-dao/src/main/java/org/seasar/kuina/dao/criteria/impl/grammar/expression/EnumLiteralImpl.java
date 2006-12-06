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

import javax.persistence.EnumType;

import org.seasar.framework.exception.SIllegalArgumentException;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.grammar.EnumLiteral;

/**
 * 
 * @author koichik
 */
public class EnumLiteralImpl implements EnumLiteral {
    protected final Enum literal;

    protected final EnumType enumType;

    /**
     * インスタンスを構築します。
     */
    public EnumLiteralImpl(final Enum literal) {
        this(literal, EnumType.ORDINAL);
    }

    public EnumLiteralImpl(final Enum literal, final EnumType enumType) {
        if (literal == null) {
            throw new SIllegalArgumentException("EKuinaDao0001",
                    new Object[] { "literal" });
        }
        this.literal = literal;
        this.enumType = enumType;
    }

    public void evaluate(final CriteriaContext context) {
        context.append(literal, enumType);
    }

}
