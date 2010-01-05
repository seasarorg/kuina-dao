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
package org.seasar.kuina.dao.internal.command;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;

import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.Distinct;
import org.seasar.kuina.dao.FetchJoin;
import org.seasar.kuina.dao.FetchJoins;
import org.seasar.kuina.dao.IllegalOrderbyException;
import org.seasar.kuina.dao.JoinSpec;
import org.seasar.kuina.dao.Orderby;
import org.seasar.kuina.dao.OrderbySpec;
import org.seasar.kuina.dao.OrderingSpec;
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariableDeclaration;
import org.seasar.kuina.dao.criteria.impl.grammar.declaration.IdentificationVariableDeclarationImpl;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.util.JpqlUtil;
import org.seasar.kuina.dao.internal.util.KuinaDaoUtil;
import org.seasar.kuina.dao.internal.util.SelectStatementUtil;

import static org.seasar.kuina.dao.criteria.CriteriaOperations.*;

/**
 * 動的にJPQLを生成して問い合わせを行う{@link Command}の共通機能を提供する抽象クラスです．
 * 
 * @author koichik
 */
public abstract class AbstractDynamicQueryCommand extends AbstractQueryCommand {

    // constants
    /** 空の{@link Map} */
    protected static final Map<String, JoinSpec> EMPTY_MAP = CollectionsUtil
            .newHashMap();

    // instance fields
    /** Daoメソッドに{@link Distinct}アノテーションが指定されていれば<code>true</code> */
    protected boolean distinct;

    /** 問い合わせ対象エンティティのidentification_variable */
    protected String identificationVariable;

    /** フェッチ結合指定の配列 */
    protected FetchJoin[] fetchJoins;

    /** ORDER BY指定の配列 */
    protected OrderbySpec[] orderbySpecs;

    /**
     * インスタンスを構築します。
     * 
     * @param entityClass
     *            問い合わせ対象のエンティティ・クラス
     * @param method
     *            Daoメソッド
     * @param resultList
     *            問い合わせ結果を{@link List}で返す場合に<code>true</code>
     */
    public AbstractDynamicQueryCommand(final Class<?> entityClass,
            final Method method, final boolean resultList) {
        super(entityClass, method, resultList);
        this.distinct = detectDistinct();
        this.identificationVariable = JpqlUtil
                .toDefaultIdentificationVariable(entityClass);
        this.fetchJoins = detectFetchJoins();
        this.orderbySpecs = detectOrderbySpec();
    }

    public Object execute(final EntityManager em, final Object[] arguments) {
        final SelectStatement statement = createSelectStatement(arguments);
        setupStatement(statement);
        return resultList ? statement.getResultList(em) : statement
                .getSingleResult(em);
    }

    /**
     * Daoメソッドに{@link Distinct}アノテーションが指定されていれば<code>true</code>を返します．
     * 
     * @return Daoメソッドに{@link Distinct}アノテーションが指定されていれば<code>true</code>
     */
    protected boolean detectDistinct() {
        return method.getAnnotation(Distinct.class) != null;
    }

    /**
     * Daoメソッドに{@link FetchJoins}アノテーションまたは{@link FetchJoin}アノテーションが指定されていれば，フェッチ結合指定の配列を返します．
     * <p>
     * Daoメソッドに{@link FetchJoins}アノテーションも{@link FetchJoin}アノテーションお指定されていなければ<code>null</code>を返します．
     * </p>
     * 
     * @return フェッチ結合指定の配列
     */
    protected FetchJoin[] detectFetchJoins() {
        final FetchJoins joins = method.getAnnotation(FetchJoins.class);
        if (joins != null) {
            return joins.value();
        }
        final FetchJoin join = method.getAnnotation(FetchJoin.class);
        if (join != null) {
            return new FetchJoin[] { join };
        }
        return null;
    }

    /**
     * Daoメソッドに{@link Orderby}アノテーションが指定されていれば，ORDER BY指定の配列を返します．
     * <p>
     * Daoメソッドに{@link Orderby}アノテーションが指定されていなければ<code>null</code>を返します．
     * </p>
     * 
     * @return ORDER BY指定の配列
     * @throws IllegalOrderbyException
     *             {@link Orderby}アノテーションの<code>value</code>要素の指定が不正な場合にスローされます
     */
    protected OrderbySpec[] detectOrderbySpec() {
        final Orderby annotation = method.getAnnotation(Orderby.class);
        if (annotation == null) {
            return null;
        }
        final String text = annotation.value();
        if (StringUtil.isEmpty(text)) {
            throw new IllegalOrderbyException(method, "");
        }
        final List<OrderbySpec> list = CollectionsUtil.newArrayList();
        final String[] orderbyItems = text.split(", *");
        for (final String orderbyItem : orderbyItems) {
            final String[] tokens = orderbyItem.split(" +");
            if (tokens.length == 1) {
                list.add(new OrderbySpec(tokens[0]));
            } else if (tokens.length == 2) {
                try {
                    list.add(new OrderbySpec(tokens[0], OrderingSpec
                            .valueOf(tokens[1].toUpperCase())));
                } catch (final IllegalArgumentException e) {
                    throw new IllegalOrderbyException(method, text, e);
                }
            } else {
                throw new IllegalOrderbyException(method, text);
            }
        }
        return list.toArray(new OrderbySpec[list.size()]);
    }

    /**
     * SELECT文を作成して返します．
     * <p>
     * 作成したSELECT文にDaoメソッドの引数をパラメータ値としてバインドします．
     * </p>
     * 
     * @param arguments
     *            Daoメソッドの引数
     * @return SELECT文
     */
    protected SelectStatement createSelectStatement(final Object[] arguments) {
        final SelectStatement statement = distinct ? selectDistinct(path(identificationVariable))
                : select(path(identificationVariable));
        final List<String> boundProperties = bindParameter(statement, arguments);
        statement
                .from(createIdentificationVariableDeclaration(boundProperties));
        if (orderbySpecs != null) {
            SelectStatementUtil.appendOrderbyClause(identificationVariable,
                    statement, orderbySpecs);
        }
        return statement;
    }

    /**
     * Daoメソッドの引数をパラメータ値としてSELECT文にバインドします．
     * 
     * @param statement
     *            SELECT文
     * @param arguments
     *            Daoメソッドの引数
     * @return バインドしたパラメータ名の{@link List}
     */
    protected abstract List<String> bindParameter(SelectStatement statement,
            Object[] arguments);

    /**
     * SELECT文のFROM句 (identification_variable_declaration) を作成して返します．
     * 
     * @param boundProperties
     *            SELECT文にバインドしたパラメータ名の{@link List}
     * @return SELECT文のFROM句 (identification_variable_declaration)
     */
    protected IdentificationVariableDeclaration createIdentificationVariableDeclaration(
            final List<String> boundProperties) {
        final IdentificationVariableDeclaration fromDecl = new IdentificationVariableDeclarationImpl(
                entityClass);
        for (final Entry<String, JoinSpec> entry : createFetchJoinAssociations(
                fromDecl.getIdentificationVariable().toString()).entrySet()) {
            if (entry.getValue() == JoinSpec.INNER_JOIN) {
                fromDecl.innerFetch(entry.getKey());
            } else {
                fromDecl.leftFetch(entry.getKey());
            }
        }
        for (final String association : createJoinAssociations(boundProperties)) {
            final int pos1 = association.lastIndexOf('.');
            if (pos1 == -1) {
                fromDecl.inner(identificationVariable + "." + association,
                        association);
                continue;
            }
            final int pos2 = association.lastIndexOf('.', pos1 - 1);
            if (pos2 == -1) {
                fromDecl.inner(association, association.substring(pos1 + 1));
                continue;
            }
            fromDecl.inner(association.substring(pos2 + 1), association
                    .substring(pos1 + 1));
        }
        return fromDecl;
    }

    /**
     * フェッチ結合する関連の{@link Map}を返します．
     * 
     * @param abstractSchemaName
     *            問い合わせの基点となるエンティティのabstract_schema_name
     * @return フェッチ結合する関連の{@link Map}
     */
    protected Map<String, JoinSpec> createFetchJoinAssociations(
            final String abstractSchemaName) {
        if (fetchJoins == null) {
            return EMPTY_MAP;
        }
        final Map<String, JoinSpec> associations = CollectionsUtil.newTreeMap();
        for (final FetchJoin join : fetchJoins) {
            associations.put(abstractSchemaName + "." + join.value(), join
                    .joinSpec());
        }
        return associations;
    }

    /**
     * 結合する関連の{@link Set}を返します．
     * 
     * @param boundProperties
     *            SELECT文にバインドしたパラメータ名の{@link List}
     * @return 結合する関連の{@link Set}
     */
    protected Set<String> createJoinAssociations(
            final List<String> boundProperties) {
        final Set<String> associations = CollectionsUtil.newTreeSet();
        for (int i = 0; i < boundProperties.size(); ++i) {
            final String propertyName = boundProperties.get(i);
            EntityDesc owner = EntityDescFactory.getEntityDesc(entityClass);
            int pos1 = 0;
            int pos2 = 0;
            while ((pos2 = propertyName.indexOf('.', pos1)) > -1) {
                final String path = propertyName.substring(0, pos2);
                final String maybeAssociationPropName = propertyName.substring(
                        pos1, pos2);
                pos1 = pos2 + 1;
                if (!KuinaDaoUtil
                        .isAssociation(owner, maybeAssociationPropName)) {
                    break;
                }
                associations.add(path);
                owner = KuinaDaoUtil.getAssociationEntityDesc(owner,
                        maybeAssociationPropName);
            }
        }
        return associations;
    }

}
