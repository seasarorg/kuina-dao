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
package org.seasar.kuina.dao;

/**
 * JPQLのORDER BY句に指定するorder_by_specを表すクラスです．
 * 
 * @author koichik
 */
public class OrderbySpec {

    // instance fields
    /** ソートに使用する項目を表すpath_expression */
    protected final String pathExpression;

    /** 昇順・降順 */
    protected final OrderingSpec orderingSpec;

    /**
     * インスタンスを構築します。
     * 
     * @param pathExpression
     *            ソートに使用する項目を表すpath_expression
     */
    public OrderbySpec(final String pathExpression) {
        this(pathExpression, OrderingSpec.ASC);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param pathExpression
     *            ソートに使用する項目を表すpath_expression
     * @param orderingSpec
     *            昇順・降順
     */
    public OrderbySpec(final String pathExpression,
            final OrderingSpec orderingSpec) {
        this.pathExpression = pathExpression;
        this.orderingSpec = orderingSpec;
    }

    /**
     * ソートに使用する項目を表すpath_expressionを返します．
     * 
     * @return ソートに使用する項目を表すpath_expression
     */
    public String getPathExpression() {
        return pathExpression;
    }

    /**
     * 昇順・降順を返します．
     * 
     * @return 昇順・降順
     */
    public OrderingSpec getOrderingSpec() {
        return orderingSpec;
    }

}
