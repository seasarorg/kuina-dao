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
package org.seasar.kuina.dao.internal.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;

import org.seasar.extension.dao.helper.DaoHelper;
import org.seasar.extension.tx.annotation.RequiresNewTx;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.jpa.EntityManagerProvider;
import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.Disposable;
import org.seasar.framework.util.DisposableUtil;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.framework.util.tiger.GenericUtil;
import org.seasar.kuina.dao.PositionalParameter;
import org.seasar.kuina.dao.QueryName;
import org.seasar.kuina.dao.TargetEntity;
import org.seasar.kuina.dao.TemporalSpec;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.CommandBuilder;
import org.seasar.kuina.dao.internal.binder.CalendarParameterBinder;
import org.seasar.kuina.dao.internal.binder.DateParameterBinder;
import org.seasar.kuina.dao.internal.binder.ObjectParameterBinder;
import org.seasar.kuina.dao.internal.binder.ParameterBinder;

/**
 * {@link Command コマンド}を作成するビルダの抽象クラスです．
 * 
 * @author koichik
 */
public abstract class AbstractCommandBuilder implements CommandBuilder {

    // static fields
    private static final Logger logger = Logger
            .getLogger(AbstractCommandBuilder.class);

    /** パラメータ化された型が持つ型変数をキー、型引数を値とする{@link Map}のキャッシュ */
    protected static final ConcurrentMap<Class<?>, Map<TypeVariable<?>, Type>> typeVariableMapCache = CollectionsUtil
            .newConcurrentHashMap();

    /** キャッシュが初期化済みであることを示します */
    protected static boolean initialized;

    // instance fields
    /** 命名規約 */
    @Binding(bindingType = BindingType.MUST)
    protected NamingConvention convention;

    /** Daoヘルパー */
    @Binding(bindingType = BindingType.MUST)
    protected DaoHelper daoHelper;

    /** エンティティ・マネージャ・プロバイダ */
    @Binding(bindingType = BindingType.MUST)
    protected EntityManagerProvider entityManagerProvider;

    /** メソッド名の正規表現パターン */
    protected Pattern methodNamePattern;

    /**
     * クラスを初期化します。
     * <p>
     * S2コンテナの終了時にキャッシュを破棄するよう構成します。
     * </p>
     */
    protected static synchronized void initialize() {
        if (!initialized) {
            DisposableUtil.add(new Disposable() {

                public void dispose() {
                    typeVariableMapCache.clear();
                    initialized = false;
                }

            });
        }
    }

    /**
     * インスタンスを構築します。
     */
    public AbstractCommandBuilder() {
    }

    /**
     * メソッド名の正規表現パターンを文字列で設定します．
     * 
     * @param methodNamePattern
     *            メソッド名の正規表現パターン文字列
     */
    public void setMethodNamePattern(final String methodNamePattern) {
        this.methodNamePattern = Pattern.compile(methodNamePattern);
    }

    /**
     * テスト対象のメソッドの名前が正規表現パターンとマッチする場合に<code>true</code>を返します．
     * 
     * @param method
     *            テスト対象のメソッド
     * @return テスト対象のメソッドの名前が正規表現パターンとマッチする場合に<code>true</code>
     */
    protected boolean isMatched(final Method method) {
        return methodNamePattern.matcher(method.getName()).matches();
    }

    /**
     * 操作対象のエンティティクラスを返します．
     * <p>
     * 操作対象のエンティティクラスは次の順で検索します．
     * </p>
     * <ol>
     * <li>Daoのメソッドに{@link TargetEntity}アノテーションが付けられていればその<code>value</code>要素の値</li>
     * <li>Daoクラスに{@link TargetEntity}アノテーションが付けられていればその<code>value</code>要素の値</li>
     * <li>Daoのクラス名から操作対象となるエンティティクラスを求めることができればそのクラス</li>
     * </ol>
     * <p>
     * 操作対象となるエンティティクラスが見つからない場合は<code>null</code>を返します．
     * </p>
     * 
     * @param daoClass
     *            Daoクラス
     * @param method
     *            Daoのメソッド
     * @return 操作対象のエンティティクラス
     */
    protected Class<?> getTargetClass(final Class<?> daoClass,
            final Method method) {
        final TargetEntity methodAnnotatedTargetEntity = method
                .getAnnotation(TargetEntity.class);
        if (methodAnnotatedTargetEntity != null) {
            return methodAnnotatedTargetEntity.value();
        }

        final TargetEntity classAnnotatedTargetEntity = daoClass
                .getAnnotation(TargetEntity.class);
        if (classAnnotatedTargetEntity != null) {
            return classAnnotatedTargetEntity.value();
        }

        return getTargetClassFromDaoName(daoClass);
    }

    /**
     * Daoのクラス名から操作対象となるエンティティクラスを求めて返します．
     * <p>
     * 操作対象のエンティティは，Daoのクラス名からDaoのサフィックスを取り除いてエンティティのサフィックスを付加したものをエンティティクラスの単純名とし，
     * <br>
     * <var>ルートパッケージ</var><code>.</code><var>Daoのサブパッケージ名</var><code>.</code><var>エンティティの単純名</var>
     * <br>
     * という名前のクラスが見つかればそれを操作対象のエンティティクラスとして返します．
     * </p>
     * <p>
     * 操作対象となるエンティティクラスが見つからない場合は<code>null</code>を返します．
     * </p>
     * 
     * @param daoClass
     *            Daoクラス
     * @return 操作対象となるエンティティクラス
     */
    protected Class<?> getTargetClassFromDaoName(final Class<?> daoClass) {
        final String daoClassShortName = ClassUtil.getShortClassName(convention
                .toInterfaceClassName(daoClass.getName()));
        final String partOfEntityClassName = ClassUtil.concatName(convention
                .getEntityPackageName(), daoClassShortName
                .substring(0, daoClassShortName.length()
                        - convention.getDaoSuffix().length()));
        final String[] rootPackageNames = convention.getRootPackageNames();
        for (int i = 0; i < rootPackageNames.length; ++i) {
            final String entityClassName = ClassUtil.concatName(
                    rootPackageNames[i], partOfEntityClassName);
            try {
                return ClassUtil.forName(entityClassName);
            } catch (final Exception ignore) {
            }
        }
        return null;
    }

    /**
     * パラメータをバインドする{@link ParameterBinder}の配列を作成して返します．
     * 
     * @param method
     *            Daoのメソッド
     * @param beanDesc
     *            Daoクラスの{@link BeanDesc}
     * @return パラメータをバインドする{@link ParameterBinder}の配列
     */
    protected ParameterBinder[] getBinders(final Method method,
            final BeanDesc beanDesc) {
        final PositionalParameter positional = method
                .getAnnotation(PositionalParameter.class);
        if (positional != null) {
            return getBindersForPositionalParameter(method);
        }
        return getBindersForNamedParameter(method, beanDesc
                .getMethodParameterNames(method));
    }

    /**
     * Named Parameterをバインドする{@link ParameterBinder}の配列を作成して返します．
     * 
     * @param method
     *            Daoのメソッド
     * @param parameterNames
     *            Daoメソッドの引数名の配列
     * @return パラメータをバインドする{@link ParameterBinder}の配列
     */
    protected ParameterBinder[] getBindersForNamedParameter(
            final Method method, final String[] parameterNames) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Annotation[][] parameterAnnotations = method
                .getParameterAnnotations();
        final ParameterBinder[] binders = new ParameterBinder[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            final Class<?> type = parameterTypes[i];
            final String name = parameterNames[i];
            final Annotation[] annotations = parameterAnnotations[i];
            binders[i] = getBinderForNamedParameter(type, name, annotations);
        }
        return binders;
    }

    /**
     * Named Parameterをバインドする{@link ParameterBinder}を作成して返します．
     * 
     * @param type
     *            引数の型
     * @param name
     *            引数の名前
     * @param annotations
     *            引数に付けられたアノテーションの配列
     * @return パラメータをバインドする{@link ParameterBinder}
     */
    protected ParameterBinder getBinderForNamedParameter(final Class<?> type,
            final String name, final Annotation[] annotations) {
        if (java.sql.Date.class.isAssignableFrom(type)) {
            return new ObjectParameterBinder(name);
        }
        if (java.sql.Time.class.isAssignableFrom(type)) {
            return new ObjectParameterBinder(name);
        }
        if (java.sql.Timestamp.class.isAssignableFrom(type)) {
            return new ObjectParameterBinder(name);
        }
        if (Date.class.isAssignableFrom(type)) {
            return new DateParameterBinder(name, getTemporalType(annotations));
        }
        if (Calendar.class.isAssignableFrom(type)) {
            return new CalendarParameterBinder(name,
                    getTemporalType(annotations));
        }
        return new ObjectParameterBinder(name);
    }

    /**
     * Positional Parameterをバインドする{@link ParameterBinder}の配列を作成して返します．
     * 
     * @param method
     *            Daoのメソッド
     * @return パラメータをバインドする{@link ParameterBinder}の配列
     */
    protected ParameterBinder[] getBindersForPositionalParameter(
            final Method method) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Annotation[][] parameterAnnotations = method
                .getParameterAnnotations();
        final ParameterBinder[] binders = new ParameterBinder[parameterTypes.length];
        int position = 0;
        for (int i = 0; i < parameterTypes.length; ++i) {
            final Class<?> type = parameterTypes[i];
            final Annotation[] annotations = parameterAnnotations[i];
            binders[i] = getBinderForPositionalParameter(type, ++position,
                    annotations);
        }
        return binders;
    }

    /**
     * Positional Parameterをバインドする{@link ParameterBinder}を作成して返します．
     * 
     * @param type
     *            引数の型
     * @param position
     *            引数の位置
     * @param annotations
     *            引数に付けられたアノテーションの配列
     * @return パラメータをバインドする{@link ParameterBinder}
     */
    protected ParameterBinder getBinderForPositionalParameter(
            final Class<?> type, final int position,
            final Annotation[] annotations) {
        if (java.sql.Date.class.isAssignableFrom(type)) {
            return new ObjectParameterBinder(position);
        }
        if (java.sql.Time.class.isAssignableFrom(type)) {
            return new ObjectParameterBinder(position);
        }
        if (java.sql.Timestamp.class.isAssignableFrom(type)) {
            return new ObjectParameterBinder(position);
        }
        if (Date.class.isAssignableFrom(type)) {
            return new DateParameterBinder(position,
                    getTemporalType(annotations));
        }
        if (Calendar.class.isAssignableFrom(type)) {
            return new CalendarParameterBinder(position,
                    getTemporalType(annotations));
        }
        return new ObjectParameterBinder(position);
    }

    /**
     * アノテーションで指定された時制を返します．
     * <p>
     * <code>annotations</code>の中に{@link TemporalSpec}アノテーションが含まれていれば， その<code>value</code>要素の値を返します．
     * <br>
     * <code>annotations</code>の中に{@link TemporalSpec}アノテーションが含まれていなければ{@link TemporalType#DATE}を返します．
     * </p>
     * 
     * @param annotations
     *            アノテーションの配列
     * @return 時制
     */
    protected TemporalType getTemporalType(final Annotation[] annotations) {
        for (final Annotation annotation : annotations) {
            if (annotation instanceof TemporalSpec) {
                final TemporalSpec spec = TemporalSpec.class.cast(annotation);
                return spec.value();
            }
        }
        return TemporalType.DATE;
    }

    /**
     * Named Queryの名前の候補を配列で返します．
     * <p>
     * 次の順でNamed Queryの候補を作成します．
     * </p>
     * <ol>
     * <li>{@link QueryName}アノテーションが指定されていれば，その<code>value</code>要素で指定された名前</li>
     * <li><var>DaoのFQN</var><code>.</code><var>Daoのメソッド名</var></li>
     * <li><var>Daoの単純名</var><code>.</code><var>Daoのメソッド名</var></li>
     * <li>対象のエンティティを求めることができれば，<var>エンティティ名</var><code>.</code><var>Daoのメソッド名</var></li>
     * </ol>
     * 
     * @param daoClass
     *            Daoクラス
     * @param method
     *            Daoのメソッド
     * @return Named Queryの名前の配列
     */
    protected String[] getQueryNames(final Class<?> daoClass,
            final Method method) {
        final List<String> names = CollectionsUtil.newArrayList();
        final QueryName queryName = method.getAnnotation(QueryName.class);
        if (queryName != null) {
            names.add(queryName.value());
        }

        final Class<?> daoInterface = daoClass.isInterface() ? daoClass
                : ClassUtil.forName(convention.toInterfaceClassName(daoClass
                        .getName()));
        names.add(daoInterface.getName() + "." + method.getName());
        names.add(daoInterface.getSimpleName() + "." + method.getName());

        final Class<?> targetClass = getTargetClass(daoClass, method);
        if (targetClass != null) {
            final EntityDesc entityDesc = EntityDescFactory
                    .getEntityDesc(targetClass);
            if (entityDesc != null) {
                names.add(entityDesc.getEntityName() + "." + method.getName());
            }
        }

        return names.toArray(new String[names.size()]);
    }

    /**
     * 指定された名前のNamed Queryが存在すれば<code>true</code>を返します．
     * <p>
     * Named Queryが存在するかチェックするために{@link EntityManager#createNamedQuery(String)}を呼び出します．
     * Named Queryが存在しない場合，<code>EntityManager</code>は例外をスローし，JPA実装によってはトランザクションを
     * ロールバックしてしまうため，トランザクション特性を<code>REQUIRES_NEW</code>にしています．
     * </p>
     * 
     * @param daoClass
     *            Daoクラス
     * @param queryName
     *            Named Queryの名前
     * @return 指定された名前のNamed Queryが存在すれば<code>true</code>
     */
    @RequiresNewTx
    public boolean isExists(final Class<?> daoClass, final String queryName) {
        try {
            final String prefix = daoHelper.getDataSourceName(daoClass);
            final EntityManager em = entityManagerProvider
                    .getEntityManger(prefix);
            em.createNamedQuery(queryName);
            if (logger.isDebugEnabled()) {
                logger.log("DKuinaDao3002", new Object[] { queryName });
            }
            return true;
        } catch (final Exception ignore) {
        }
        return false;
    }

    /**
     * クラスの定義に使用されている，パラメータ化された型(クラスまたはインタフェース)が持つ型変数をキー，型引数を値とする{@link Map}を返します．
     * 
     * @param clazz
     *            クラス
     * @return パラメータ化された型が持つ型変数をキー、型引数を値とする{@link Map}
     */
    protected Map<TypeVariable<?>, Type> getTypeVariableMap(final Class<?> clazz) {
        initialize();
        final Map<TypeVariable<?>, Type> typeVariableMap = typeVariableMapCache
                .get(clazz);
        if (typeVariableMap != null) {
            return typeVariableMap;
        }
        return CollectionsUtil.putIfAbsent(typeVariableMapCache, clazz,
                GenericUtil.getTypeVariableMap(clazz));
    }

    /**
     * メソッド引数の実際の型の配列を返します。
     * 
     * @param daoClass
     *            Daoクラス
     * @param method
     *            Daoメソッド
     * @return メソッド引数の実際の型の配列
     */
    protected Class<?>[] getActualParameterClasses(final Class<?> daoClass,
            final Method method) {
        final Map<TypeVariable<?>, Type> map = getTypeVariableMap(daoClass);
        final Type[] parameterTypes = method.getGenericParameterTypes();
        final Class<?>[] parameterClasses = new Class[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            parameterClasses[i] = GenericUtil.getActualClass(parameterTypes[i],
                    map);
        }
        return parameterClasses;
    }

    /**
     * メソッド戻り値の実際の型を返します。
     * 
     * @param daoClass
     *            Daoクラス
     * @param method
     *            Daoメソッド
     * @return メソッド戻り値の実際の型
     */
    protected Class<?> getActualReturnClass(final Class<?> daoClass,
            final Method method) {
        return GenericUtil.getActualClass(method.getGenericReturnType(),
                getTypeVariableMap(daoClass));
    }

}
