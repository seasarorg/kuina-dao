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
package org.seasar.kuina.dao.criteria.impl.grammar.conditional;

import org.seasar.kuina.dao.criteria.grammar.ConditionalExpression;
import org.seasar.kuina.dao.criteria.grammar.ConditionalTerm;

/**
 * 複数のconditional_expressionがANDで結合されたconditional_termを表すクラスです．
 * 
 * @author koichik
 */
public class And extends AbstractConditionalExpression implements
        ConditionalTerm {

    /**
     * インスタンスを構築します。
     * 
     * @param expressions
     *            conditional_expressionの並び
     */
    public And(final ConditionalExpression... expressions) {
        super("AND", expressions);
    }

    /**
     * conditional_expressionを追加します．
     * 
     * @param expressions
     *            conditional_expressionの並び
     * @return このインスタンス自身
     */
    public And and(final ConditionalExpression... expressions) {
        add(expressions);
        return this;
    }

}
