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
package org.seasar.kuina.dao.criteria.impl.grammar.clause;

import junit.framework.TestCase;

import org.seasar.kuina.dao.Department;
import org.seasar.kuina.dao.Employee;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.impl.CriteriaContextImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.IdentificationVariableImpl;

/**
 * 
 * @author koichik
 */
public class FromClauseImplTest extends TestCase {

    public void test1() throws Exception {
        FromClauseImpl from = new FromClauseImpl(
                new IdentificationVariableDeclarationImpl(Department.class));
        CriteriaContext context = new CriteriaContextImpl();
        from.evaluate(context);
        String jpql = context.getQueryString();
        assertEquals(" FROM Department AS department", jpql);
    }

    public void test2() throws Exception {
        FromClauseImpl from = new FromClauseImpl(
                new IdentificationVariableDeclarationImpl(Department.class));
        from.add(new IdentificationVariableDeclarationImpl(Employee.class));
        CriteriaContext context = new CriteriaContextImpl();
        from.evaluate(context);
        String jpql = context.getQueryString();
        assertEquals(" FROM Department AS department, Employee AS employee",
                jpql);
    }

    public void test4() throws Exception {
        FromClauseImpl from = new FromClauseImpl(
                new IdentificationVariableDeclarationImpl(Department.class,
                        new IdentificationVariableImpl("d")).inner(
                        "d.employee", "e").leftFetch("d.hogehoge"));
        CriteriaContext context = new CriteriaContextImpl();
        from.evaluate(context);
        String jpql = context.getQueryString();
        assertEquals(
                " FROM Department AS d INNER JOIN d.employee AS e LEFT OUTER JOIN FETCH d.hogehoge",
                jpql);
    }

}
