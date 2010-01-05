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
package org.seasar.kuina.dao.criteria;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Query;

import org.seasar.kuina.dao.criteria.grammar.ConditionalExpression;
import org.seasar.kuina.dao.criteria.grammar.GroupbyItem;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariableDeclaration;
import org.seasar.kuina.dao.criteria.grammar.OrderbyItem;
import org.seasar.kuina.dao.criteria.grammar.SelectExpression;

/**
 * JPQLのselect_statementを表します．
 * <p>
 * JPQLの詳細はJPA仕様書「4.14 BNF」を参照してください．
 * </p>
 * 
 * <pre>
 * select_statement ::=
 *     select_clause from_clause [where_clause] [groupby_clause]
 *         [having_clause] [orderby_clause]
 * </pre>
 * 
 * @author koichik
 */
public interface SelectStatement extends Criteria {

    /**
     * SELECT句にselect_expressionを追加します．
     * 
     * @param selectExpression
     *            select_expression
     * @return このインスタンス自身
     */
    SelectStatement select(String selectExpression);

    /**
     * SELECT句にselect_expressionを追加します．
     * 
     * @param selectExpression
     *            select_expression
     * @return このインスタンス自身
     */
    SelectStatement select(SelectExpression selectExpression);

    /**
     * SELECT句にselect_expressionを追加します．
     * 
     * @param selectExpression
     *            select_expression
     * @return このインスタンス自身
     */
    SelectStatement select(Object... selectExpression);

    /**
     * FROM句にidentification_variable_declarationを追加します．
     * 
     * @param entityClasses
     *            identification_variable_declarationの並び
     * @return このインスタンス自身
     */
    SelectStatement from(Class<?>... entityClasses);

    /**
     * FROM句にidentification_variable_declarationを追加します．
     * 
     * @param entityClass
     *            abstract_schema_names
     * @param alias
     *            identification_variable
     * @return このインスタンス自身
     */
    SelectStatement from(Class<?> entityClass, String alias);

    /**
     * FROM句にidentification_variable_declarationを追加します．
     * 
     * @param declarations
     *            identification_variable_declarationの並び
     * @return このインスタンス自身
     */
    SelectStatement from(IdentificationVariableDeclaration... declarations);

    /**
     * WHERE句にconditional_expressionを追加します．
     * 
     * @param conditionalExpressions
     *            conditional_expressionの並び
     * @return このインスタンス自身
     */
    SelectStatement where(ConditionalExpression... conditionalExpressions);

    /**
     * GROUP BY句にgroupby_itemを追加します．
     * 
     * @param groupbyItems
     *            groupby_itemの並び
     * @return このインスタンス自身
     */
    SelectStatement groupby(String... groupbyItems);

    /**
     * GROUP BY句にgroupby_itemを追加します．
     * 
     * @param groupbyItems
     *            groupby_itemの並び
     * @return このインスタンス自身
     */
    SelectStatement groupby(GroupbyItem... groupbyItems);

    /**
     * HAVING句にconditional_expressionを追加します．
     * 
     * @param conditionalExpressions
     *            conditional_expressionの並び
     * @return このインスタンス自身
     */
    SelectStatement having(ConditionalExpression... conditionalExpressions);

    /**
     * ORDER BY句にorderby_itemを追加します．
     * 
     * @param orderbyItems
     *            orderby_itemの並び
     * @return このインスタンス自身
     */
    SelectStatement orderby(String... orderbyItems);

    /**
     * ORDER BY句にorderby_itemを追加します．
     * 
     * @param orderbyItems
     *            orderby_itemの並び
     * @return このインスタンス自身
     */
    SelectStatement orderby(OrderbyItem... orderbyItems);

    /**
     * 取得する結果セットの最初の位置を設定します．
     * <p>
     * 位置は0から始まります．
     * </p>
     * 
     * @param startPosition
     *            取得する結果セットの最初の位置
     * @return このインスタンス自身
     * @see javax.persistence.Query#setFirstResult(int)
     */
    SelectStatement setFirstResult(int startPosition);

    /**
     * 取得する結果セットの最大件数を設定します．
     * 
     * @param maxResult
     *            取得する結果セットの最大件数
     * @return このインスタンス自身
     * @see javax.persistence.Query#setMaxResults(int)
     */
    SelectStatement setMaxResults(int maxResult);

    /**
     * この問い合わせを実行する際のフラッシュモードを設定します．
     * 
     * @param flushMode
     *            フラッシュモード
     * @return このインスタンス自身
     * @see javax.persistence.Query#setFlushMode(javax.persistence.FlushModeType)
     */
    SelectStatement setFlushMode(FlushModeType flushMode);

    /**
     * 問い合わせのヒントを追加します．
     * 
     * @param name
     *            ヒントの名前
     * @param value
     *            ヒントの値
     * @return このインスタンス自身
     * @see javax.persistence.Query#setHint(String, Object)
     */
    SelectStatement addHint(String name, Object value);

    /**
     * 問い合わせを実行して結果を{@link java.util.List}で返します．
     * 
     * @param <T>
     *            戻り値のリストの要素型
     * @param em
     *            エンティティ・マネージャ
     * @return 問い合わせ結果のリスト
     * @see javax.persistence.Query#getResultList()
     */
    <T> List<T> getResultList(EntityManager em);

    /**
     * 問い合わせを実行して単一の結果を返します．
     * 
     * @param <T>
     *            戻り値のリストの要素型
     * @param em
     *            エンティティ・マネージャ
     * @return 問い合わせの結果
     * @see javax.persistence.Query#getSingleResult()
     */
    <T> T getSingleResult(EntityManager em);

    /**
     * 問い合わせ文字列 (JPQL) を返します．
     * 
     * @return 問い合わせ文字列 (JPQL)
     */
    String getQueryString();

    /**
     * {@link javax.persistence.Query}を作成して返します．
     * 
     * @param em
     *            エンティティ・マネージャ
     * @return {@link javax.persistence.Query}
     */
    Query getQuery(EntityManager em);

}
