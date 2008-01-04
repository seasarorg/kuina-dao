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

import javax.persistence.Query;

/**
 * {@link javax.persistence.Query}にパラメータをバインドします．
 * <p>
 * このインタフェースを実装したオブジェクトはパラメータの名前 (Named Parameterの場合) または位置 (Positional
 * Parameterの場合) を持ち，それらとパラメータ値をバインドします．
 * </p>
 * 
 * @author koichik
 */
public interface ParameterBinder {

    /**
     * このインスタンスが保持しているパラメータ値を{@link javax.persistence.Query}にバインドします．
     * 
     * @param query
     *            Query
     */
    void bind(Query query);

    /**
     * <code>value</code>を{@link javax.persistence.Query}にバインドします．
     * 
     * @param query
     * @param value
     */
    void bind(Query query, Object value);

}
