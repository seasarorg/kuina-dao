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

import org.seasar.extension.unit.S2TestCase;
import org.seasar.kuina.dao.Department;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.impl.CriteriaContextImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.declaration.IdentificationVariableDeclarationImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.IdentificationVariableImpl;

/**
 * 
 * @author koichik
 */
public class IdentificationVariableDeclarationImplTest extends S2TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("s2hibernate-jpa.dicon");
    }

    public void test() throws Exception {
        IdentificationVariableDeclarationImpl range = new IdentificationVariableDeclarationImpl(
                Department.class);
        CriteriaContext context = new CriteriaContextImpl();
        range.evaluate(context);
        String jpql = context.getQueryString();
        assertEquals("Department AS department", jpql);
    }

    public void testWithIdentification() throws Exception {
        IdentificationVariableDeclarationImpl range = new IdentificationVariableDeclarationImpl(
                Department.class, new IdentificationVariableImpl("d"));
        CriteriaContext context = new CriteriaContextImpl();
        range.evaluate(context);
        String jpql = context.getQueryString();
        assertEquals("Department AS d", jpql);
    }

}
