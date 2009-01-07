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
package org.seasar.kuina.dao.criteria;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 * JPQLを構築するためのコンテキスト情報です．
 * 
 * @author koichik
 */
public interface CriteriaContext {

    /**
     * JPQL文字列バッファに<code>boolean</code>引数の文字列表現を追加します．
     * 
     * @param b
     *            <code>boolean</code>
     * @return このインスタンス自身
     */
    CriteriaContext append(boolean b);

    /**
     * JPQL文字列バッファに<code>byte</code>引数の文字列表現を追加します．
     * 
     * @param b
     *            <code>byte</code>
     * @return このインスタンス自身
     */
    CriteriaContext append(byte b);

    /**
     * JPQL文字列バッファに<code>short</code>引数の文字列表現を追加します．
     * 
     * @param s
     *            <code>short</code>
     * @return このインスタンス自身
     */
    CriteriaContext append(short s);

    /**
     * JPQL文字列バッファに<code>int</code>引数の文字列表現を追加します．
     * 
     * @param i
     *            <code>int</code>
     * @return このインスタンス自身
     */
    CriteriaContext append(int i);

    /**
     * JPQL文字列バッファに<code>long</code>引数の文字列表現を追加します．
     * 
     * @param l
     *            <code>long</code>
     * @return このインスタンス自身
     */
    CriteriaContext append(long l);

    /**
     * JPQL文字列バッファに<code>float</code>引数の文字列表現を追加します．
     * 
     * @param f
     *            <code>float</code>
     * @return このインスタンス自身
     */
    CriteriaContext append(float f);

    /**
     * JPQL文字列バッファに<code>double</code>引数の文字列表現を追加します．
     * 
     * @param d
     *            <code>double</code>
     * @return このインスタンス自身
     */
    CriteriaContext append(double d);

    /**
     * JPQL文字列バッファに<code>char</code>引数の文字列表現を追加します．
     * 
     * @param ch
     *            <code>char</code>
     * @return このインスタンス自身
     */
    CriteriaContext append(char ch);

    /**
     * JPQL文字列バッファに指定された文字を追加します．
     * 
     * @param s
     *            文字列
     * @return このインスタンス自身
     */
    CriteriaContext append(String s);

    /**
     * JPQL文字列バッファに指定された列挙の文字列表現 (FQN + '.' + 要素名) を追加します．
     * 
     * @param e
     *            列挙
     * @return このインスタンス自身
     */
    CriteriaContext append(Enum<?> e);

    /**
     * JPQL文字列バッファに<code>Object</code>引数の文字列表現を追加します．
     * 
     * @param o
     *            <code>Object</code>
     * @return このインスタンス自身
     */
    CriteriaContext append(Object o);

    /**
     * JPQL文字列バッファの長さを指定されたサイズ分切り詰めます．
     * 
     * @param length
     *            切り詰める長さ
     * @return このインスタンス自身
     */
    CriteriaContext cutBack(int length);

    /**
     * JPQLの名前パラメータを追加します．
     * 
     * @param name
     *            パラメータの名前
     * @param value
     *            パラメータの値
     */
    void setParameter(String name, Object value);

    /**
     * JPQLの名前パラメータを追加します．
     * 
     * @param name
     *            パラメータの名前
     * @param value
     *            パラメータの値
     * @param temporalType
     *            パラメータの時制
     */
    void setParameter(String name, Date value, TemporalType temporalType);

    /**
     * JPQLの名前パラメータを追加します．
     * 
     * @param name
     *            パラメータの名前
     * @param value
     *            パラメータの値
     * @param temporalType
     *            パラメータの時制
     */
    void setParameter(String name, Calendar value, TemporalType temporalType);

    /**
     * {@link javax.persistence.Query}に全てのパラメータを設定します．
     * 
     * @param query
     *            {@link javax.persistence.Query}
     */
    void fillParameters(Query query);

    /**
     * JPQL問い合わせ文字列を返します．
     * 
     * @return JPQL問い合わせ文字列
     */
    String getQueryString();

}
