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
package org.seasar.kuina.dao.criteria.impl.grammar.function;

import org.seasar.framework.exception.SIllegalArgumentException;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.grammar.FunctionReturningStrings;
import org.seasar.kuina.dao.criteria.grammar.StringPrimary;
import org.seasar.kuina.dao.criteria.grammar.TrimSpecification;

/**
 * 
 * @author koichik
 */
public class Trim implements FunctionReturningStrings {
    public static final char DEFAULT_TRIM_CHARACTER = ' ';

    public static final TrimSpecification DEFAULT_TRIM_SPECIFICATION = TrimSpecification.BOTH;

    protected final TrimSpecification trimSpecificatin;

    protected final char trimCharacter;

    protected final StringPrimary trimSource;

    /**
     * インスタンスを構築します。
     */
    public Trim(final StringPrimary trimSource) {
        this(DEFAULT_TRIM_SPECIFICATION, DEFAULT_TRIM_CHARACTER, trimSource);
    }

    public Trim(final TrimSpecification trimSpecification,
            final StringPrimary trimSource) {
        this(trimSpecification, DEFAULT_TRIM_CHARACTER, trimSource);
    }

    public Trim(final char trimCharacter, final StringPrimary trimSource) {
        this(DEFAULT_TRIM_SPECIFICATION, trimCharacter, trimSource);
    }

    public Trim(final TrimSpecification trimSpecification,
            final char trimCharacter, final StringPrimary trimSource) {
        if (trimSpecification == null) {
            throw new SIllegalArgumentException("EKuinaDao0001",
                    new Object[] { "trimSpecification" });
        }
        if (trimSource == null) {
            throw new SIllegalArgumentException("EKuinaDao0001",
                    new Object[] { "trimSource" });
        }
        this.trimSpecificatin = trimSpecification;
        this.trimCharacter = trimCharacter;
        this.trimSource = trimSource;
    }

    public void evaluate(final CriteriaContext context) {
        context.append("TRIM(");
        if (DEFAULT_TRIM_SPECIFICATION != trimSpecificatin) {
            context.append(trimSpecificatin).append(" ");
        }
        if (DEFAULT_TRIM_CHARACTER != trimCharacter) {
            context.append(trimCharacter).append(" ");
        }
        if (DEFAULT_TRIM_SPECIFICATION != trimSpecificatin
                || DEFAULT_TRIM_CHARACTER != trimCharacter) {
            context.append("FROM ");
        }
        trimSource.evaluate(context);
        context.append(")");
    }

}
