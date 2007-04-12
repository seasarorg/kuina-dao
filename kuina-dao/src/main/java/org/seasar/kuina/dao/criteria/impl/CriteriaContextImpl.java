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
package org.seasar.kuina.dao.criteria.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.internal.binder.CalendarParameterBinder;
import org.seasar.kuina.dao.internal.binder.DateParameterBinder;
import org.seasar.kuina.dao.internal.binder.ObjectParameterBinder;
import org.seasar.kuina.dao.internal.binder.ParameterBinder;

/**
 * JPQLを構築するためのコンテキスト情報の実装クラスです．
 * 
 * @author koichik
 */
public class CriteriaContextImpl implements CriteriaContext {

    // instance fields
    /** 構築したJPQL文字列を保持するバッファ */
    protected final StringBuilder stringBuilder = new StringBuilder(1000);

    /** JPQLに含まれるバインドパラメータを保持する<code>Set</code> */
    protected final Set<ParameterBinder> binders = CollectionsUtil
            .newLinkedHashSet();

    /**
     * インスタンスを構築します。
     */
    public CriteriaContextImpl() {
    }

    public CriteriaContext append(final boolean b) {
        stringBuilder.append(b);
        return this;
    }

    public CriteriaContext append(final byte b) {
        stringBuilder.append(b);
        return this;
    }

    public CriteriaContext append(final short s) {
        stringBuilder.append(s);
        return this;
    }

    public CriteriaContext append(final int i) {
        stringBuilder.append(i);
        return this;
    }

    public CriteriaContext append(final long l) {
        stringBuilder.append(l);
        return this;
    }

    public CriteriaContext append(final float f) {
        stringBuilder.append(f);
        return this;
    }

    public CriteriaContext append(final double d) {
        stringBuilder.append(d);
        return this;
    }

    public CriteriaContext append(final char ch) {
        stringBuilder.append(ch);
        return this;
    }

    public CriteriaContext append(final String s) {
        stringBuilder.append(s);
        return this;
    }

    public CriteriaContext append(final Enum<?> e) {
        stringBuilder.append(e.getClass().getName()).append(".").append(
                e.name());
        return this;
    }

    public CriteriaContext append(final Object o) {
        stringBuilder.append(o);
        return this;
    }

    public CriteriaContext cutBack(final int number) {
        stringBuilder.setLength(stringBuilder.length() - number);
        return this;
    }

    public void setParameter(final String name, final Object value) {
        binders.add(new ObjectParameterBinder(name, value));
    }

    public void setParameter(final String name, final Date value,
            final TemporalType temporalType) {
        binders.add(new DateParameterBinder(name, value, temporalType));
    }

    public void setParameter(final String name, final Calendar value,
            final TemporalType temporalType) {
        binders.add(new CalendarParameterBinder(name, value, temporalType));
    }

    public String getQueryString() {
        return new String(stringBuilder);
    }

    public void fillParameters(final Query query) {
        for (final ParameterBinder binder : binders) {
            binder.bind(query);
        }
    }

}
