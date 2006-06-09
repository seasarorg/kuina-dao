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
package org.seasar.kuina.dao.criteria.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.kuina.dao.Department;
import org.seasar.kuina.dao.Employee;

import static org.seasar.kuina.dao.criteria.CriteriaOperations.alias;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.desc;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.eq;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.isNull;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.join;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.max;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.min;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.or;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.select;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.selectDistinct;

/**
 * 
 * @author koichik
 */
public class SelectStatementImplTest extends S2TestCase {

    private EntityManager em;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("s2hibernate-jpa.dicon");
    }

    public void testFromOnlyTx() throws Exception {
        List<Employee> list = select().from(Employee.class).getResultList(em);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("NEW YORK", list.get(0).getName());
        assertEquals("AMERICANA MANHASSET", list.get(1).getName());
        assertEquals("BEVERLY HILLS", list.get(2).getName());
    }

    public void testSelectTx() throws Exception {
        List<Department> list = select("department").from(Department.class)
                .getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Tiffany", list.get(0).getName());
    }

    public void testSelectListTx() throws Exception {
        List<Object[]> list = select("d.id", "d.name").from(Department.class,
                "d").getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(new Integer(1), list.get(0)[0]);
        assertEquals("Tiffany", list.get(0)[1]);
    }

    public void testCrossJoinTx() throws Exception {
        List<Object[]> list = select().from(Department.class, Employee.class)
                .getResultList(em);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("Tiffany", Department.class.cast(list.get(0)[0]).getName());
        assertEquals("NEW YORK", Employee.class.cast(list.get(0)[1]).getName());
        assertEquals("Tiffany", Department.class.cast(list.get(1)[0]).getName());
        assertEquals("AMERICANA MANHASSET", Employee.class.cast(list.get(1)[1])
                .getName());
        assertEquals("Tiffany", Department.class.cast(list.get(2)[0]).getName());
        assertEquals("BEVERLY HILLS", Employee.class.cast(list.get(2)[1])
                .getName());
    }

    public void testSelectDestinctAndCrossJoinWithAliasTx() throws Exception {
        List<Department> list = selectDistinct("d").from(
                alias(Department.class, "d"), alias(Employee.class, "e"))
                .getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Tiffany", list.get(0).getName());
    }

    public void testInnerJoinTx() throws Exception {
        List<Employee> list = select().from(
                join(Employee.class).inner("employee.manager")).getResultList(
                em);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("AMERICANA MANHASSET", list.get(0).getName());
        assertNotNull(list.get(0).getManager());
        assertEquals("BEVERLY HILLS", list.get(1).getName());
        assertNotNull(list.get(1).getManager());
    }

    public void testLeftOuterJoinTx() throws Exception {
        List<Employee> list = select().from(
                join(Employee.class, "e").left("e.manager")).getResultList(em);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("NEW YORK", list.get(0).getName());
        assertNull(list.get(0).getManager());
        assertEquals("AMERICANA MANHASSET", list.get(1).getName());
        assertNotNull(list.get(1).getManager());
        assertEquals("BEVERLY HILLS", list.get(2).getName());
        assertNotNull(list.get(2).getManager());
    }

    public void testWhereSingleConditionTx() throws Exception {
        List<Employee> list = select().from(Employee.class).where(
                eq("employee.name", "NEW YORK")).getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("NEW YORK", list.get(0).getName());
    }

    public void testWhereCompoundConditionTx() throws Exception {
        List<Employee> list = select().from(Employee.class).where(
                eq("employee.department.name", "Tiffany"),
                or(eq("employee.id", 1), isNull("employee.id"))).getResultList(
                em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("NEW YORK", list.get(0).getName());
        assertEquals("Tiffany", list.get(0).getDepartment().getName());
    }

    public void testAggregateFunctionTx() throws Exception {
        List<Object[]> list = select(max("e.salary"), min("e.salary")).from(
                Employee.class, "e").getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(new Integer(5000000), list.get(0)[0]);
        assertEquals(new Integer(3000000), list.get(0)[1]);
    }

    public void testOrderbyTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").orderby(
                "e.salary").getResultList(em);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("BEVERLY HILLS", list.get(0).getName());
        assertEquals("AMERICANA MANHASSET", list.get(1).getName());
        assertEquals("NEW YORK", list.get(2).getName());
    }

    public void testOrderbyDescTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").orderby(
                desc("e.salary")).getResultList(em);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("NEW YORK", list.get(0).getName());
        assertEquals("AMERICANA MANHASSET", list.get(1).getName());
        assertEquals("BEVERLY HILLS", list.get(2).getName());
    }
}
