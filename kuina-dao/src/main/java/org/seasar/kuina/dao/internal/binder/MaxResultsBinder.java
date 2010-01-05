/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
 * 数値を{@link javax.persistence.Query#setMaxResults(int)}にバインドします．
 * 
 * @author koichik
 * @see javax.persistence.Query#setMaxResults(int)
 */
public class MaxResultsBinder implements ParameterBinder {

    /** 結果を取得する最大の件数 */
    protected final Number value;

    /**
     * インスタンスを構築します。
     * 
     */
    public MaxResultsBinder() {
        this(null);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param value
     *            結果を取得する最大の件数
     */
    public MaxResultsBinder(final Number value) {
        this.value = value;
    }

    public void bind(final Query query) {
        bind(query, value);
    }

    public void bind(final Query query, final Object value) {
        final int maxResults = Number.class.cast(value).intValue();
        if (maxResults >= 0) {
            query.setMaxResults(maxResults);
        }
    }

}
