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
package org.seasar.kuina.dao.criteria.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.criteria.CriteriaContext;

/**
 * 
 * @author koichik
 */
public class CriteriaContextImpl implements CriteriaContext {

    protected final StringBuilder stringBuilder = new StringBuilder(1000);

    protected final Set<Parameter> parameters = CollectionsUtil
            .newLinkedHashSet();

    /**
     * インスタンスを構築します。
     */
    public CriteriaContextImpl() {
    }

    public CriteriaContext append(boolean b) {
        stringBuilder.append(b);
        return this;
    }

    public CriteriaContext append(byte b) {
        stringBuilder.append(b);
        return this;
    }

    public CriteriaContext append(short s) {
        stringBuilder.append(s);
        return this;
    }

    public CriteriaContext append(int i) {
        stringBuilder.append(i);
        return this;
    }

    public CriteriaContext append(long l) {
        stringBuilder.append(l);
        return this;
    }

    public CriteriaContext append(float f) {
        stringBuilder.append(f);
        return this;
    }

    public CriteriaContext append(double d) {
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

    public CriteriaContext append(final Enum e) {
        stringBuilder.append(e.name());
        return this;
    }

    public CriteriaContext append(Object o) {
        stringBuilder.append(o);
        return this;
    }

    public CriteriaContext cutBack(final int number) {
        stringBuilder.setLength(stringBuilder.length() - number);
        return this;
    }

    /**
     * @see org.seasar.kuina.dao.criteria.CriteriaContext#setParameter(java.lang.String,
     *      java.lang.Object)
     */
    public void setParameter(final String name, final Object value) {
        parameters.add(new ObjectParameter(name, value));
    }

    public void setParameter(final String name, final Date value,
            TemporalType temporalType) {
        parameters.add(new DateParameter(name, value, temporalType));
    }

    public void setParameter(final String name, final Calendar value,
            TemporalType temporalType) {
        parameters.add(new CalendarParameter(name, value, temporalType));
    }

    public String getQueryString() {
        return new String(stringBuilder);
    }

    public void fillParameters(final Query query) {
        for (final Parameter parameter : parameters) {
            parameter.fill(query);
        }
    }

    protected interface Parameter {
        void fill(final Query query);
    }

    protected static class ObjectParameter implements Parameter {
        protected final String name;

        protected final Object value;

        public ObjectParameter(final String name, final Object value) {
            this.name = name;
            this.value = value;
        }

        public void fill(final Query query) {
            query.setParameter(name, value);
        }
    }

    protected static class DateParameter implements Parameter {
        protected final String name;

        protected final Date value;

        protected final TemporalType temporalType;

        public DateParameter(final String name, final Date value,
                final TemporalType temporalType) {
            this.name = name;
            this.value = value;
            this.temporalType = temporalType;
        }

        public void fill(final Query query) {
            query.setParameter(name, value, temporalType);
        }
    }

    protected static class CalendarParameter implements Parameter {
        protected final String name;

        protected final Calendar value;

        protected final TemporalType temporalType;

        public CalendarParameter(final String name, final Calendar value,
                final TemporalType temporalType) {
            this.name = name;
            this.value = value;
            this.temporalType = temporalType;
        }

        public void fill(final Query query) {
            query.setParameter(name, value, temporalType);
        }
    }

}
