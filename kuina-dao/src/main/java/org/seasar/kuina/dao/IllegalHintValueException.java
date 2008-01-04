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
package org.seasar.kuina.dao;

import java.lang.reflect.Method;

import org.seasar.framework.exception.SRuntimeException;

/**
 * {@link Hint}アノテーションで指定したヒントの値が不正であることを示すためにスローされます．
 * 
 * @author koichik
 */
public class IllegalHintValueException extends SRuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * インスタンスを構築します。
     * 
     * @param method
     *            注釈されたメソッド
     * @param name
     *            ヒントの名前
     * @param value
     *            ヒントの値を表現するOGNL式
     * @param e
     *            原因となった例外
     */
    public IllegalHintValueException(final Method method, final String name,
            final String value, final Exception e) {
        super("EKuinaDao2004", new Object[] { method, name, value }, e);
    }

}
