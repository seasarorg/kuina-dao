/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import junit.framework.TestCase;

import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.impl.CriteriaContextImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.conditional.And;
import org.seasar.kuina.dao.criteria.impl.grammar.conditional.Or;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.PathExpressionImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.StringLiteralImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.Equal;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.GreaterThan;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.IsNull;
import org.seasar.kuina.dao.criteria.impl.grammar.operator.LessOrEqual;

/**
 * 
 * @author koichik
 */
public class WhereClauseImplTest extends TestCase {

    public void test1() throws Exception {
        WhereClauseImpl where = new WhereClauseImpl(
                new Equal(new PathExpressionImpl("d.name"),
                        new StringLiteralImpl("hoge")));
        CriteriaContext context = new CriteriaContextImpl();
        where.evaluate(context);
        String jpql = context.getQueryString();
        System.out.println(jpql);
        assertEquals(" WHERE (d.name = 'hoge')", jpql);
    }

    public void test2() throws Exception {
        WhereClauseImpl where = new WhereClauseImpl(new And(
                new Equal(new PathExpressionImpl("d.name"),
                        new StringLiteralImpl("hoge")), new IsNull(
                        new PathExpressionImpl("d.id"))));
        CriteriaContext context = new CriteriaContextImpl();
        where.evaluate(context);
        String jpql = context.getQueryString();
        System.out.println(jpql);
        assertEquals(" WHERE ((d.name = 'hoge') AND (d.id IS NULL))", jpql);
    }

    public void test3() throws Exception {
        WhereClauseImpl where = new WhereClauseImpl(new And(new And(
                new Equal(new PathExpressionImpl("d.name"),
                        new StringLiteralImpl("hoge")), new IsNull(
                        new PathExpressionImpl("d.id"))), new Or(
                new GreaterThan(new PathExpressionImpl("d.foo"),
                        new StringLiteralImpl("foo")), new LessOrEqual(
                        new PathExpressionImpl("d.bar"), new StringLiteralImpl(
                                "bar")))));
        CriteriaContext context = new CriteriaContextImpl();
        where.evaluate(context);
        String jpql = context.getQueryString();
        System.out.println(jpql);
        assertEquals(
                " WHERE (((d.name = 'hoge') AND (d.id IS NULL)) AND ((d.foo > 'foo') OR (d.bar <= 'bar')))",
                jpql);
    }

}
