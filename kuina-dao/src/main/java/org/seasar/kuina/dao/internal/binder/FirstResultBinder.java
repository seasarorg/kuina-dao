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
package org.seasar.kuina.dao.internal.binder;

import javax.persistence.Query;

/**
 * 
 * @author koichik
 */
public class FirstResultBinder implements ParameterBinder {

    protected final Number value;

    public FirstResultBinder() {
        this(null);
    }

    public FirstResultBinder(final Number value) {
        this.value = value;
    }

    /**
     * @see org.seasar.kuina.dao.internal.binder.ParameterBinder#bind(javax.persistence.Query)
     */
    public void bind(final Query query) {
        bind(query, value);
    }

    public void bind(final Query query, final Object value) {
        final int firstResult = Number.class.cast(value).intValue();
        if (firstResult >= 0) {
            query.setFirstResult(firstResult);
        }
    }

}
