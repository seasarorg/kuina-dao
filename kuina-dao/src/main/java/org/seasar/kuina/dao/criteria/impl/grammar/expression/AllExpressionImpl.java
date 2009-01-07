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
package org.seasar.kuina.dao.criteria.impl.grammar.expression;

/**
 * JPQLのALL演算子を使用したall_or_any_expressionを表すクラスです．
 * 
 * @author koichik
 */
public class AllExpressionImpl extends AbstractAllOrAnyExpression {

    /**
     * インスタンスを構築します。
     * 
     */
    public AllExpressionImpl() {
        super("ALL ");
    }

}
