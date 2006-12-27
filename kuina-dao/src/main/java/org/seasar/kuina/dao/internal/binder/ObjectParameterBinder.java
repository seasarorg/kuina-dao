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
public class ObjectParameterBinder implements ParameterBinder {

    protected final String name;

    protected final int position;

    protected final Object value;

    public ObjectParameterBinder(final String name) {
        this(name, 0, null);
    }

    public ObjectParameterBinder(final int position) {
        this(null, position, null);
    }

    public ObjectParameterBinder(final String name, final Object value) {
        this(name, 0, value);
    }

    public ObjectParameterBinder(final int position, final Object value) {
        this(null, position, value);
    }

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
