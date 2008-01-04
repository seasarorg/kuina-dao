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
import org.seasar.kuina.dao.criteria.impl.grammar.expression.IdentificationVariableImpl;

/**
 * 
 * @author koichik
 */
public class SelectClauseImplTest extends TestCase {

    public void test1() throws Exception {
        SelectClauseImpl select = new SelectClauseImpl();
        select.add(new IdentificationVariableImpl("department"));
        CriteriaContext context = new CriteriaContextImpl();
        select.evaluate(context);
        String jpql = context.getQueryString();
        assertEquals("SELECT department", jpql);
    }

    public void test2() throws Exception {
        SelectClauseImpl select = new SelectClauseImpl(true);
        select.add(new IdentificationVariableImpl("department"),
                new IdentificationVariableImpl("employee"));
        CriteriaContext context = new CriteriaContextImpl();
        select.evaluate(context);
        String jpql = context.getQueryString();
        assertEquals("SELECT DISTINCT department, employee", jpql);
    }

    public void test3() throws Exception {
        SelectClauseImpl select = new SelectClauseImpl();
        select.add(new IdentificationVariableImpl("department"),
                new IdentificationVariableImpl("employee"),
                new IdentificationVariableImpl("address"));
        CriteriaContext context = new CriteriaContextImpl();
        select.evaluate(context);
        String jpql = context.getQueryString();
        assertEquals("SELECT department, employee, address", jpql);
    }

}
