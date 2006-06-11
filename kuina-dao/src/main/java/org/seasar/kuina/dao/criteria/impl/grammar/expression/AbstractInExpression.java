/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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

import java.util.List;

import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.grammar.InExpression;
import org.seasar.kuina.dao.criteria.grammar.InItem;
import org.seasar.kuina.dao.criteria.grammar.PathExpression;

/**
 * 
 * @author koichik
 */
public abstract class AbstractInExpression implements InExpression {

    protected final String operator;

    protected final PathExpression pathExpression;

    protected final List<InItem> inItems = CollectionsUtil.newArrayList();

    public AbstractInExpression(final String operator,
            final PathExpression pathExpression, final InItem... inItems) {
        this.operator = operator;
        this.pathExpression = pathExpression;
        add(inItems);
    }

    public InExpression add(final InItem... inItems) {
        for (final InItem inItem : inItems) {
            this.inItems.add(inItem);
        }
        return this;
    }

    /**
     * @see org.seasar.kuina.dao.criteria.Criterion#evaluate(org.seasar.kuina.dao.criteria.CriteriaContext)
     */
    public void evaluate(final CriteriaContext context) {
        pathExpression.evaluate(context);
        context.append(operator).append("(");
        for (final InItem inItem : inItems) {
            inItem.evaluate(context);
            context.append(", ");
        }
        context.cutBack(2);
        context.append(")");

    }

}
