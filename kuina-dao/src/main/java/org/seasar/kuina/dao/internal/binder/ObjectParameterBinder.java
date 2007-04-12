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
package org.seasar.kuina.dao.internal.binder;

import javax.persistence.Query;

/**
 * <code>Object</code>型のパラメータ値を{@link javax.persistence.Query}にバインドします．
 * <p>
 * このクラスは<code>Date</code>および<code>Calendar</code>以外の型のパラメータを
 * {@link javax.persistence.Query}にバインドするために使われます．
 * </p>
 * 
 * @author koichik
 * @see javax.persistence.Query#setParameter(String, Object)
 * @see javax.persistence.Query#setParameter(int, Object)
 */
public class ObjectParameterBinder implements ParameterBinder {

    /** パラメータ名 (Named Parameterの場合) */
    protected final String name;

    /** パラメータの位置 (Positional Parameterの場合) */
    protected final int position;

    /** パラメータの値 */
    protected final Object value;

    /**
     * Named Parameterをバインドするインスタンスを構築します。
     * <p>
     * パラメータの値は{@link #bind(Query, Object)}によって与えられます．
     * </p>
     * 
     * @param name
     *            パラメータの名前
     */
    public ObjectParameterBinder(final String name) {
        this(name, 0, null);
    }

    /**
     * Positional Parameterをバインドするインスタンスを構築します。
     * <p>
     * パラメータの値は{@link #bind(Query, Object)}によって与えられます．
     * </p>
     * 
     * @param position
     *            パラメータの位置
     */
    public ObjectParameterBinder(final int position) {
        this(null, position, null);
    }

    /**
     * Named Parameterのインスタンスを構築します。
     * 
     * @param name
     *            パラメータの名前
     * @param value
     *            パラメータの値
     */
    public ObjectParameterBinder(final String name, final Object value) {
        this(name, 0, value);
    }

    /**
     * Positional Parameterのインスタンスを構築します。
     * 
     * @param position
     *            パラメータの位置
     * @param value
     *            パラメータの値
     */
    public ObjectParameterBinder(final int position, final Object value) {
        this(null, position, value);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param name
     *            パラメータの名前
     * @param position
     *            パラメータの位置
     * @param value
     *            パラメータの値
     */
    protected ObjectParameterBinder(final String name, final int position,
            final Object value) {
        this.name = name;
        this.position = position;
        this.value = value;
    }

    public void bind(final Query query) {
        bind(query, value);
    }

    public void bind(final Query query, final Object value) {
        if (name != null) {
            query.setParameter(name, value);
        } else {
            query.setParameter(position, value);
        }
    }

}
