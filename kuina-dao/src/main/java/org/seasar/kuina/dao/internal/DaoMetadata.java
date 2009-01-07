/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.kuina.dao.internal;

import java.lang.reflect.Method;

/**
 * Daoのメタデータです．
 * <p>
 * DaoメタデータはDaoインタフェース(またはクラス)ごとにインスタンス化され， Daoインタフェースのメソッドに対応した{@link Command コマンド}を保持します．
 * </p>
 * 
 * @author koichik
 */
public interface DaoMetadata {

    /** メソッドに対応した{@link Command コマンド}が実行されなかったことを示すために使われます */
    Object NOT_INVOKED = new Object();

    /**
     * このインスタンス化を初期化し，<code>daoClass</code>のメソッドに対応した{@link Command コマンド}を作成します．
     * 
     * @param daoClass
     *            Daoインタフェースまたはクラス
     */
    void initialize(Class<?> daoClass);

    /**
     * <code>method</code>に対応した{@link Command コマンド}を実行し，その結果を返します．
     * <p>
     * <code>method</code>に対応した{@link Command コマンド}がない場合は{@link #NOT_INVOKED}を返します．
     * </p>
     * 
     * @param method
     *            Daoのメソッド
     * @param arguments
     *            Daoのメソッド引数
     * @return <code>method</code>に対応した{@link Command コマンド}の実行結果．
     */
    Object execute(Method method, Object[] arguments);

}
