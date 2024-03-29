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
package org.seasar.kuina.dao.criteria.impl.grammar.operator;

import org.seasar.kuina.dao.criteria.grammar.CollectionMemberExpression;
import org.seasar.kuina.dao.criteria.grammar.EntityExpression;
import org.seasar.kuina.dao.criteria.grammar.PathExpression;

/**
 * JPQLのNOT MEMBER OFを表すクラスです．
 * 
 * @author koichik
 */
public class NotMemberOf extends AbstractBinaryOperator implements
        CollectionMemberExpression {

    /**
     * インスタンスを構築します。
     * 
     * @param lhs
     *            左辺を表すentity_expression
     * @param rhs
     *            右辺を表すentity_expression
     */
    public NotMemberOf(final EntityExpression lhs, final PathExpression rhs) {
        super(" NOT MEMBER OF ", lhs, rhs);
    }

}
