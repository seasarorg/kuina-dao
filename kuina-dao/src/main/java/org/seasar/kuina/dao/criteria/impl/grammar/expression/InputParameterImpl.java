/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import org.seasar.framework.exception.SIllegalArgumentException;
import org.seasar.framework.exception.SIllegalStateException;
import org.seasar.framework.util.StringUtil;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.grammar.InputParameter;

/**
 * JPQLのinput_parameterを表す文字列．
 * 
 * @author koichik
 */
public class InputParameterImpl implements InputParameter {

    // instance fields
    /** input_parameterの名前 */
    protected final String name;

    /** input_parameterの値 */
    protected final Object value;

    /** input_parameterの時制 */
    protected final TemporalType temporalType;

    /**
     * インスタンスを構築します。
     * 
     * @param name
     *            パラメータの名前
     * @param value
     *            パラメータの値
     */
    public InputParameterImpl(final String name, final Object value) {
        assertName(name);
        this.name = name;
        this.value = value;
        this.temporalType = null;
    }

    /**
     * インスタンスを構築します。
     * 
     * @param name
     *            パラメータの名前
     * @param value
     *            パラメータの値
     * @param temporalType
     *            パラメータの時制
     */
    public InputParameterImpl(final String name, final Date value,
            final TemporalType temporalType) {
        assertName(name);
        assertTemporalType(temporalType);
        this.name = name;
        this.value = value;
        this.temporalType = temporalType;
    }

    /**
     * インスタンスを構築します。
     * 
     * @param name
     *            パラメータの名前
     * @param value
     *            パラメータの値
     * @param temporalType
     *            パラメータの時制
     */
    public InputParameterImpl(final String name, final Calendar value,
            final TemporalType temporalType) {
        assertName(name);
        assertTemporalType(temporalType);
        this.name = name;
        this.value = value;
        this.temporalType = temporalType;
    }

    public void evaluate(final CriteriaContext context) {
        context.append(':').append(name);

        if (temporalType == null || value == null) {
            context.setParameter(name, value);
        } else if (Date.class.isInstance(value)) {
            context.setParameter(name, Date.class.cast(value), temporalType);
        } else if (Calendar.class.isInstance(value)) {
            context
                    .setParameter(name, Calendar.class.cast(value),
                            temporalType);
        } else {
            throw new SIllegalStateException("EKuinaDao1007", new Object[] {
                    name, value });
        }
    }

    /**
     * パラメータの名前が<code>null</code>または空文字列の場合に例外をスローします．
     * 
     * @param name
     *            パラメータの文字列
     * @throws SIllegalStateException
     *             パラメータの名前が<code>null</code>または空文字列の場合にスローされます
     */
    protected void assertName(final String name) {
        if (StringUtil.isEmpty(name)) {
            throw new SIllegalArgumentException("EKuinaDao0001",
                    new Object[] { "name" });
        }
    }

    /**
     * パラメータの時制が<code>null</code>の場合に例外をスローします．
     * 
     * @param temporalType
     *            パラメータの時制
     * @throws SIllegalStateException
     *             パラメータの時制が<code>null</code>の場合にスローされます
     */
    protected void assertTemporalType(final TemporalType temporalType) {
        if (temporalType == null) {
            throw new SIllegalStateException("EKuinaDao1006", new Object[] {});
        }
    }

}
