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
package org.seasar.kuina.dao;

import java.util.List;

import org.seasar.framework.beans.annotation.ParameterName;

/**
 * アプリケーションで作成するDaoインタフェースの基礎となるgenericなDaoインタフェースです。
 * <p>
 * アプリケーションでは，エンティティ及びそのIdプロパティ／フィールドの型を指定してこのインタフェースを拡張したDaoインタフェースを作成します。
 * </p>
 * 
 * <pre>
 * public interface EmployeeDao extends GenericDao&lt;Employee, Long&gt; {
 *   // some methods
 * }
 * </pre>
 * 
 * 
 * @param <ENTITY>
 *            Daoインタフェースが扱う対象となるエンティティの型
 * @param <ID>
 *            Daoインタフェースが扱う対象となるエンティティのIdプロパティの型
 * @author koichik
 */
public interface GenericDao<ENTITY, ID> {

    /**
     * エンティティを全件取得してその{@link List}を返します．
     * 
     * @return エンティティのリスト
     */
    List<ENTITY> findAll();

    /**
     * Idプロパティ (主キー) を指定してエンティティを検索します．
     * <p>
     * Idプロパティにマッチするエンティティが存在しない場合は<code>null</code>を返します．
     * </p>
     * 
     * @param id
     *            Idプロパティ (主キー)
     * @return 見つかったエンティティ
     * @see javax.persistence.EntityManager#find(Class, Object)
     */
    ENTITY find(@ParameterName("id")
    ID id);

    /**
     * Idプロパティ (主キー) を指定してエンティティを検索します．
     * <p>
     * エンティティの状態は遅延ロードされます。
     * </p>
     * 
     * @param id
     *            Idプロパティ (主キー)
     * @return エンティティ
     * @see javax.persistence.EntityManager#getReference(Class, Object)
     */
    ENTITY getReference(@ParameterName("id")
    ID id);

    /**
     * エンティティを永続化します。
     * 
     * @param entity
     *            エンティティ
     * @see javax.persistence.EntityManager#persist(Object)
     */
    void persist(@ParameterName("entity")
    ENTITY entity);

    /**
     * 管理されたエンティティを削除します。
     * 
     * @param entity
     *            管理されたエンティティ
     * @see javax.persistence.EntityManager#remove(Object)
     */
    void remove(@ParameterName("entity")
    ENTITY entity);

    /**
     * 永続コンテキストにエンティティが含まれていれば<code>true</code>を返します。
     * 
     * @param entity
     *            エンティティ
     * @return 永続コンテキストにエンティティが含まれていれば<code>true</code>
     * @see javax.persistence.EntityManager#contains(Object)
     */
    boolean contains(@ParameterName("entity")
    ENTITY entity);

    /**
     * 管理されたエンティティを永続コンテキストから分離します。
     * 
     * @param entity
     *            管理されたエンティティ
     */
    void detach(@ParameterName("entity")
    ENTITY entity);

    /**
     * 新規または分離されたエンティティを永続コンテキストに加えます。
     * 
     * @param entity
     *            新規または分離されたエンティティ
     * @return マージされたエンティティ
     * @see javax.persistence.EntityManager#merge(Object)
     */
    ENTITY merge(@ParameterName("entity")
    ENTITY entity);

    /**
     * データベースからエンティティの最新の状態を取得し、エンティティに反映します。
     * 
     * @param entity
     *            管理されたエンティティ
     * @see javax.persistence.EntityManager#refresh(Object)
     */
    void refresh(@ParameterName("entity")
    ENTITY entity);

    /**
     * 管理されたエンティティを{@link javax.persistence.LockModeType.READ}でロックします。
     * 
     * @param entity
     *            管理されたエンティティ
     * @see javax.persistence.EntityManager#lock(Object,
     *      javax.persistence.LockModeType)
     */
    void readLock(@ParameterName("entity")
    ENTITY entity);

    /**
     * 管理されたエンティティを{@link javax.persistence.LockModeType.WRITE}でロックします。
     * 
     * @param entity
     *            管理されたエンティティ
     * @see javax.persistence.EntityManager#lock(Object,
     *      javax.persistence.LockModeType)
     */
    void writeLock(@ParameterName("entity")
    ENTITY entity);

}
