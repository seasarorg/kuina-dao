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
package org.seasar.kuina.dao.internal.binder;

import java.util.Date;

import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 * <code>Date</code>型のパラメータ値を{@link javax.persistence.Query}にバインドします．
 * 
 * @author koichik
 * @see javax.persistence.Query#setParameter(String, java.util.Date,
 *      javax.persistence.TemporalType)
 * @see javax.persistence.Query#setParameter(int, java.util.Date,
 *      javax.persistence.TemporalType)
 */
public class DateParameterBinder implements ParameterBinder {

    /** パラメータ名 (Named Parameterの場合) */
    protected final String name;

    /** パラメータの位置 (Positional Parameterの場合) */
    protected final int position;

    /** パラメータの値 */
    protected final Date value;

    /** パラメータの時制 */
    protected TemporalType temporalType;

    /**
     * Named Parameterをバインドするインスタンスを構築します。
     * <p>
     * パラメータの値は{@link #bind(Query, Object)}によって与えられます．
     * </p>
     * 
     * @param name
     *            パラメータの名前
     * @param temporalType
     *            パラメータの時制
     */
    public DateParameterBinder(final String name,
            final TemporalType temporalType) {
        this(name, null, temporalType);
    }

    /**
     * Positional Parameterをバインドするインスタンスを構築します。
     * <p>
     * パラメータの値は{@link #bind(Query, Object)}によって与えられます．
     * </p>
     * 
     * @param position
     *            パラメータの位置
     * @param temporalType
     *            パラメータの時制
     */
    public DateParameterBinder(final int position,
            final TemporalType temporalType) {
        this(position, null, temporalType);
    }

    /**
     * Named Parameterのインスタンスを構築します。
     * 
     * @param name
     *            パラメータの名前
     * @param value
     *            パラメータの値
     * @param temporalType
     *            パラメータの時制
     */
    public DateParameterBinder(final String name, final Date value,
            final TemporalType temporalType) {
        this.name = name;
        this.position = 0;
        this.value = value;
        this.temporalType = temporalType;
    }

    /**
     * Positional Parameterのインスタンスを構築します。
     * 
     * @param position
     *            パラメータの位置
     * @param value
     *            パラメータの値
     * @param temporalType
     *            パラメータの時制
     */
    public DateParameterBinder(final int position, final Date value,
            final TemporalType temporalType) {
        this.name = null;
        this.position = position;
        this.value = value;
        this.temporalType = temporalType;
    }

    public void bind(final Query query) {
        bind(query, value);
    }

    public void bind(final Query query, final Object value) {
        final Date date = Date.class.cast(value);
        if (name != null) {
            query.setParameter(name, date, temporalType);
        } else {
            query.setParameter(position, date, temporalType);
        }
    }

}
