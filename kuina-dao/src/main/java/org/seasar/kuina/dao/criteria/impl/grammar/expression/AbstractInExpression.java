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
package org.seasar.kuina.dao.criteria.impl.grammar.expression;

import java.util.List;

import org.seasar.framework.exception.SIllegalArgumentException;
import org.seasar.framework.exception.SIllegalStateException;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.grammar.InExpression;
import org.seasar.kuina.dao.criteria.grammar.InItem;
import org.seasar.kuina.dao.criteria.grammar.PathExpression;
import org.seasar.kuina.dao.criteria.grammar.Subquery;

/**
 * JPQLのin_expressionを表す抽象クラスです．
 * 
 * @author koichik
 */
public abstract class AbstractInExpression implements InExpression {

    // instance fields
    /** 演算子 */
    protected final String operator;

    /** path_expression */
    protected final PathExpression pathExpression;

    /** in_itemsのリスト */
    protected final List<InItem> inItems = CollectionsUtil.newArrayList();

    /** subquery */
    protected Subquery subquery;

    /**
     * インスタンスを構築します。
     * 
     * @param operator
     *            演算子
     * @param pathExpression
     *            path_expression
     */
    public AbstractInExpression(final String operator,
            final PathExpression pathExpression) {
        if (pathExpression == null) {
            throw new SIllegalArgumentException("EKuinaDao0001",
                    new Object[] { "pathExpression" });
        }
        this.operator = operator;
        this.pathExpression = pathExpression;
    }

    /**
     * インスタンスを構築します。
     * 
     * @param operator
     *            演算子
     * @param pathExpression
     *            path_expression
     * @param inItems
     *            in_itemsの並び
     */
    public AbstractInExpression(final String operator,
            final PathExpression pathExpression, final InItem... inItems) {
        this(operator, pathExpression);
        add(inItems);
    }

    public InExpression add(final InItem... inItems) {
        if (inItems == null || inItems.length == 0) {
            throw new SIllegalArgumentException("EKuinaDao0001",
                    new Object[] { "inItems" });
        }
        for (final InItem inItem : inItems) {
            this.inItems.add(inItem);
        }
        return this;
    }

    public InExpression setSubquery(final Subquery subquery) {
        this.subquery = subquery;
        inItems.clear();
        return this;
    }

    public void evaluate(final CriteriaContext context) {
        pathExpression.evaluate(context);
        context.append(operator);
        if (!inItems.isEmpty()) {
            context.append("(");
            for (final InItem inItem : inItems) {
                inItem.evaluate(context);
                context.append(", ");
            }
            context.cutBack(2).append(")");
        } else if (subquery != null) {
            subquery.evaluate(context);
        } else {
            throw new SIllegalStateException("EKuinaDao1005",
                    new Object[] { operator });
        }
    }

}
