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

import java.util.Calendar;
import java.util.Date;

import javax.persistence.TemporalType;

import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.grammar.InputParameter;

/**
 * 
 * @author koichik
 */
public class InputParameterImpl implements InputParameter {
    protected final String identificationVariable;

    protected final Object value;

    protected final TemporalType temporalType;

    /**
     * インスタンスを構築します。
     */
    public InputParameterImpl(final String identificationVariable,
            final Object value) {
        this.identificationVariable = identificationVariable;
        this.value = value;
        this.temporalType = null;
    }

    public InputParameterImpl(final String identificationVariable,
            final Date value, final TemporalType temporalType) {
        assert temporalType != null;
        this.identificationVariable = identificationVariable;
        this.value = value;
        this.temporalType = temporalType;
    }

    public InputParameterImpl(final String identificationVariable,
            final Calendar value, final TemporalType temporalType) {
        assert temporalType != null;
        this.identificationVariable = identificationVariable;
        this.value = value;
        this.temporalType = temporalType;
    }

    /**
     * @see org.seasar.kuina.dao.criteria.Criterion#evaluate(org.seasar.kuina.dao.criteria.CriteriaContext)
     */
    public void evaluate(final CriteriaContext context) {
        context.append(':').append(identificationVariable);

        if (temporalType == null) {
            context.setParameter(identificationVariable, value);
        } else if (value instanceof Date) {
            context.setParameter(identificationVariable,
                    Date.class.cast(value), temporalType);
        } else {
            context.setParameter(identificationVariable, Calendar.class
                    .cast(value), temporalType);
        }
    }

}
