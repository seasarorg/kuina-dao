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
package org.seasar.kuina.dao.internal.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;
import org.seasar.framework.util.tiger.GenericUtil;
import org.seasar.kuina.dao.FirstResult;
import org.seasar.kuina.dao.MaxResults;
import org.seasar.kuina.dao.NamedParameter;
import org.seasar.kuina.dao.Orderby;
import org.seasar.kuina.dao.internal.binder.FirstResultBinder;
import org.seasar.kuina.dao.internal.binder.MaxResultsBinder;
import org.seasar.kuina.dao.internal.binder.NullBinder;
import org.seasar.kuina.dao.internal.binder.ParameterBinder;

/**
 * 問い合わせを実行するコマンドを作成するビルダの抽象クラスです．
 * 
 * @author koichik
 */
public abstract class AbstractQueryCommandBuilder extends
        AbstractCommandBuilder {

    // instance fields
    /** <code>orderby</code>を指定する引数名の正規表現パターン */
    protected Pattern orderbyPattern = Pattern.compile("order[bB]y");

    /** <code>firstResult</code>を指定する引数名の正規表現パターン */
    protected Pattern firstResultPattern = Pattern.compile("firstResult");

    /** <code>maxResults</code>を指定する引数名の正規表現パターン */
    protected Pattern maxResultsPattern = Pattern.compile("maxResults");

    /**
     * インスタンスを構築します。
     */
    public AbstractQueryCommandBuilder() {
        setMethodNamePattern("(get|find).*");
    }

    /**
     * <code>orderby</code>を指定する引数名の正規表現パターンを文字列で設定します．
     * 
     * @param orderbyPattern
     *            <code>orderby</code>を指定する引数名の正規表現パターン
     */
    public void setOrderbyPattern(final String orderbyPattern) {
        this.orderbyPattern = Pattern.compile(orderbyPattern);
    }

    /**
     * <code>firstResult</code>を指定する引数名の正規表現パターンを文字列で設定します．
     * 
     * @param firstResultPattern
     *            <code>firstResult</code>を指定する引数名の正規表現パターン
     */
    public void setFirstResultPattern(final String firstResultPattern) {
        this.firstResultPattern = Pattern.compile(firstResultPattern);
    }

    /**
     * <code>maxResults</code>を指定する引数名の正規表現パターンを文字列で設定します．
     * 
     * @param maxResultsPattern
     *            <code>maxResults</code>を指定する引数名の正規表現パターン
     */
    public void setMaxResultsPattern(final String maxResultsPattern) {
        this.maxResultsPattern = Pattern.compile(maxResultsPattern);
    }

    /**
     * Daoの問い合わせメソッドが{@link java.util.List}を返す場合は<code>true</code>を返します．
     * 
     * @param method
     *            Daoの問い合わせメソッド
     * @return Daoの問い合わせメソッドが{@link java.util.List}を返す場合は<code>true</code>
     */
    protected boolean isResultList(final Method method) {
        return List.class.isAssignableFrom(method.getReturnType());
    }

    /**
     * 操作対象のエンティティクラスを返します．
     * <p>
     * Daoのメソッドがエンティティクラスまたはその{@link List}を返す場合はそのクラスを返します．
     * それ以外の場合は親クラスのメソッドに委譲します．
     * </p>
     * 
     * @param daoClass
     *            Daoクラス
     * @param method
     *            Daoのメソッド
     * @return 操作対象のエンティティクラス
     * @see AbstractCommandBuilder#getTargetClass(Class, Method)
     */
    @Override
    protected Class<?> getTargetClass(final Class<?> daoClass,
            final Method method) {
        final Class<?> resultClass = getResultClass(daoClass, method);
        if (resultClass != null) {
            final EntityDesc entityDesc = EntityDescFactory
                    .getEntityDesc(resultClass);
            if (entityDesc != null) {
                return resultClass;
            }
        }
        return super.getTargetClass(daoClass, method);
    }

    /**
     * メソッドの戻り値型が{@link List}ならその要素型を，それ以外の場合は戻り値型を返します．
     * 
     * @param daoClass
     *            Daoクラス
     * @param method
     *            メソッド
     * @return メソッドの戻り値型が{@link List}ならその要素型を，それ以外の場合は戻り値型
     */
    protected Class<?> getResultClass(final Class<?> daoClass,
            final Method method) {
        if (isResultList(method)) {
            return GenericUtil.getActualElementClassOfList(method
                    .getGenericReturnType(), getTypeVariableMap(daoClass));
        }
        final Class<?> returnType = getActualReturnClass(daoClass, method);
        return returnType == void.class ? null : returnType;
    }

    @Override
    protected ParameterBinder getBinderForNamedParameter(final Class<?> type,
            final String name, final Annotation[] annotations) {
        if (isOrderby(name, annotations)) {
            return new NullBinder();
        } else if (isFirstResult(name, annotations)) {
            return new FirstResultBinder();
        } else if (isMaxResults(name, annotations)) {
            return new MaxResultsBinder();
        }
        return super.getBinderForNamedParameter(type, name, annotations);
    }

    @Override
    protected ParameterBinder getBinderForPositionalParameter(
            final Class<?> type, final int position,
            final Annotation[] annotations) {
        if (isOrderby(null, annotations)) {
            return new NullBinder();
        } else if (isFirstResult(null, annotations)) {
            return new FirstResultBinder();
        } else if (isMaxResults(null, annotations)) {
            return new MaxResultsBinder();
        }
        return super.getBinderForPositionalParameter(type, position,
                annotations);
    }

    /**
     * 引数が<code>orderby</code>指定なら<code>true</code>を返します．
     * <p>
     * 引数に{@link Orderby}アノテーションが付けられているか， 引数名が{@link #orderbyPattern}にマッチすれば，
     * その引数は<code>orderby</code>指定です．
     * </p>
     * 
     * @param name
     *            引数名
     * @param annotations
     *            引数に付けられたアノテーションの配列
     * @return 引数が<code>orderby</code>指定なら<code>true</code>
     */
    protected boolean isOrderby(final String name,
            final Annotation[] annotations) {
        for (final Annotation annotation : annotations) {
            if (annotation instanceof NamedParameter) {
                return false;
            } else if (annotation instanceof Orderby)
                return true;
        }
        return name != null && orderbyPattern.matcher(name).matches();
    }

    /**
     * 引数及び引数に付けられたアノテーションの配列から，<code>orderby</code>を指定する引数のインデックスを返します．
     * <p>
     * 配列に<code>orderby</code>指定が含まれていない場合は<code>-1</code>を返します．
     * </p>
     * 
     * @param parameterNames
     *            引数名の配列
     * @param annotations
     *            引数に付けられたアノテーションの配列の配列
     * @return 引数及び引数に付けられたアノテーションの配列から，<code>orderby</code>を指定する引数のインデックス
     */
    protected int getOrderbyParameter(final String[] parameterNames,
            final Annotation[][] annotations) {
        if (parameterNames == null) {
            return -1;
        }
        for (int i = 0; i < parameterNames.length; ++i) {
            if (isOrderby(parameterNames[i], annotations[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 引数が<code>firstResult</code>指定なら<code>true</code>を返します．
     * <p>
     * 引数に{@link FirstResult}アノテーションが付けられているか， 引数名が{@link #firstResultPattern}にマッチすれば，
     * その引数は<code>firstResult</code>指定です．
     * </p>
     * 
     * @param name
     *            引数名
     * @param annotations
     *            引数に付けられたアノテーションの配列
     * @return 引数が<code>firstResult</code>指定なら<code>true</code>
     */
    protected boolean isFirstResult(final String name,
            final Annotation[] annotations) {
        for (final Annotation annotation : annotations) {
            if (annotation instanceof NamedParameter) {
                return false;
            } else if (annotation instanceof FirstResult)
                return true;
        }
        return name != null && firstResultPattern.matcher(name).matches();
    }

    /**
     * 引数及び引数に付けられたアノテーションの配列から，<code>firstResult</code>を指定する引数のインデックスを返します．
     * <p>
     * 配列に<code>firstResult</code>指定が含まれていない場合は<code>-1</code>を返します．
     * </p>
     * 
     * @param parameterNames
     *            引数名の配列
     * @param annotations
     *            引数に付けられたアノテーションの配列の配列
     * @return 引数及び引数に付けられたアノテーションの配列から，<code>firstResult</code>を指定する引数のインデックス
     */
    protected int getFirstResultParameter(final String[] parameterNames,
            final Annotation[][] annotations) {
        if (parameterNames == null) {
            return -1;
        }
        for (int i = 0; i < parameterNames.length; ++i) {
            if (isFirstResult(parameterNames[i], annotations[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 引数が<code>maxResults</code>指定なら<code>true</code>を返します．
     * <p>
     * 引数に{@link MaxResults}アノテーションが付けられているか， 引数名が{@link #maxResultsPattern}にマッチすれば，
     * その引数は<code>maxResults</code>指定です．
     * </p>
     * 
     * @param name
     *            引数名
     * @param annotations
     *            引数に付けられたアノテーションの配列
     * @return 引数が<code>maxResults</code>指定なら<code>true</code>
     */
    protected boolean isMaxResults(final String name,
            final Annotation[] annotations) {
        for (final Annotation annotation : annotations) {
            if (annotation instanceof NamedParameter) {
                return false;
            } else if (annotation instanceof MaxResults) {
                return true;
            }
        }
        return name != null && maxResultsPattern.matcher(name).matches();
    }

    /**
     * 引数及び引数に付けられたアノテーションの配列から，<code>maxResults</code>を指定する引数のインデックスを返します．
     * <p>
     * 配列に<code>maxResults</code>指定が含まれていない場合は<code>-1</code>を返します．
     * </p>
     * 
     * @param parameterNames
     *            引数名の配列
     * @param annotations
     *            引数に付けられたアノテーションの配列の配列
     * @return 引数及び引数に付けられたアノテーションの配列から，<code>maxResults</code>を指定する引数のインデックス
     */
    protected int getMaxResultsParameter(final String[] parameterNames,
            final Annotation[][] annotations) {
        if (parameterNames == null) {
            return -1;
        }
        for (int i = 0; i < parameterNames.length; ++i) {
            if (isMaxResults(parameterNames[i], annotations[i])) {
                return i;
            }
        }
        return -1;
    }

}
