/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.kuina.dao.criteria.impl.grammar.clause;

import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.grammar.ConditionalExpression;
import org.seasar.kuina.dao.criteria.grammar.WhereClause;
import org.seasar.kuina.dao.criteria.impl.grammar.conditional.And;

/**
 * 
 * @author koichik
 */
public class WhereClauseImpl implements WhereClause {
    protected ConditionalExpression conditionalExpression;

    public WhereClauseImpl() {
    }

    public WhereClauseImpl(final ConditionalExpression... expressions) {
        and(expressions);
    }

    public WhereClause and(final ConditionalExpression... expressions) {
        if (conditionalExpression == null && expressions.length == 1) {
            conditionalExpression = expressions[0];
        } else if (conditionalExpression instanceof And) {
            And.class.cast(conditionalExpression).add(expressions);
        } else {
            conditionalExpression = new And(conditionalExpression);
            And.class.cast(conditionalExpression).add(expressions);
        }
        return this;
    }

    public void evaluate(final CriteriaContext context) {
        if (conditionalExpression == null) {
            return;
        }

        context.append(" WHERE ");
        conditionalExpression.evaluate(context);
    }

}
