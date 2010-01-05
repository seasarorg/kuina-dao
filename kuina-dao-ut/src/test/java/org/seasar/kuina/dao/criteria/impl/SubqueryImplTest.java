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
package org.seasar.kuina.dao.criteria.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.kuina.dao.criteria.grammar.StringExpression;
import org.seasar.kuina.dao.entity.BelongTo;
import org.seasar.kuina.dao.entity.Employee;

import static org.seasar.kuina.dao.criteria.CriteriaOperations.*;

/**
 * 
 * @author koichik
 */
public class SubqueryImplTest extends S2TestCase {

    private EntityManager em;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("jpa.dicon");
    }

    public void testEqTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").where(
                eq(StringExpression.class.cast(path("e.name")), subselect(
                        "e2.name").from(Employee.class, "e2").where(
                        eq("e2.id", 1)))).getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
    }

    public void testInTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").where(
                in("e.id", subselect("b.employee.id").from(BelongTo.class, "b")
                        .where(le("b.id", 10)))).getResultList(em);
        assertNotNull(list);
        assertEquals(10, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
        assertEquals("ゴッチン", list.get(1).getName());
        assertEquals("マキ子", list.get(2).getName());
    }

    public void testExistsTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").where(
                exists(subselect("b.id").from(BelongTo.class, "b").where(
                        lt("b.id", 10)))).getResultList(em);
        assertNotNull(list);
        assertEquals(30, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
        assertEquals("ゴッチン", list.get(1).getName());
        assertEquals("マキ子", list.get(2).getName());
    }

    public void testAllTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").where(
                le(path("e.id"), all(subselect("e2.id").from(Employee.class,
                        "e2")))).getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
    }

}
