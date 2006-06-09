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
import org.seasar.kuina.dao.Product;

import static org.seasar.kuina.dao.criteria.CriteriaOperations.alias;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.between;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.count;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.desc;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.eq;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.gt;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.join;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.lt;
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
        assertEquals(30, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
        assertEquals("ゴッチン", list.get(1).getName());
        assertEquals("マキ子", list.get(2).getName());
    }

    public void testSelectTx() throws Exception {
        List<Department> list = select("department").from(Department.class)
                .getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
        assertEquals("営業", list.get(0).getName());
        assertEquals("総務", list.get(1).getName());
        assertEquals("人事", list.get(2).getName());
        assertEquals("経理", list.get(3).getName());
        assertEquals("販売", list.get(4).getName());
        assertEquals("購買", list.get(5).getName());
    }

    public void testSelectListTx() throws Exception {
        List<Object[]> list = select("d.id", "d.name").from(Department.class,
                "d").getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
        assertEquals(new Integer(1), list.get(0)[0]);
        assertEquals("営業", list.get(0)[1]);
        assertEquals(new Integer(2), list.get(1)[0]);
        assertEquals("総務", list.get(1)[1]);
        assertEquals(new Integer(3), list.get(2)[0]);
        assertEquals("人事", list.get(2)[1]);
        assertEquals(new Integer(4), list.get(3)[0]);
        assertEquals("経理", list.get(3)[1]);
        assertEquals(new Integer(5), list.get(4)[0]);
        assertEquals("販売", list.get(4)[1]);
        assertEquals(new Integer(6), list.get(5)[0]);
        assertEquals("購買", list.get(5)[1]);
    }

    public void testCrossJoinTx() throws Exception {
        List<Object[]> list = select().from(Employee.class, Department.class)
                .getResultList(em);
        assertNotNull(list);
        assertEquals(180, list.size());
        assertEquals("シマゴロー", Employee.class.cast(list.get(0)[0]).getName());
        assertEquals("営業", Department.class.cast(list.get(0)[1]).getName());
        assertEquals("シマゴロー", Employee.class.cast(list.get(1)[0]).getName());
        assertEquals("総務", Department.class.cast(list.get(1)[1]).getName());
        assertEquals("シマゴロー", Employee.class.cast(list.get(2)[0]).getName());
        assertEquals("人事", Department.class.cast(list.get(2)[1]).getName());
        assertEquals("シマゴロー", Employee.class.cast(list.get(3)[0]).getName());
        assertEquals("経理", Department.class.cast(list.get(3)[1]).getName());
        assertEquals("シマゴロー", Employee.class.cast(list.get(4)[0]).getName());
        assertEquals("販売", Department.class.cast(list.get(4)[1]).getName());
        assertEquals("シマゴロー", Employee.class.cast(list.get(5)[0]).getName());
        assertEquals("購買", Department.class.cast(list.get(5)[1]).getName());
    }

    public void testSelectDestinctAndCrossJoinWithAliasTx() throws Exception {
        List<Department> list = selectDistinct("d").from(
                alias(Department.class, "d"), alias(Employee.class, "e"))
                .getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
        assertEquals("営業", list.get(0).getName());
        assertEquals("総務", list.get(1).getName());
        assertEquals("人事", list.get(2).getName());
        assertEquals("経理", list.get(3).getName());
        assertEquals("販売", list.get(4).getName());
        assertEquals("購買", list.get(5).getName());
    }

    public void testInnerJoinTx() throws Exception {
        List<Employee> list = select().from(
                join(Employee.class).inner("employee.belongTo").inner(
                        "belongTo.department")).getResultList(em);
        assertNotNull(list);
        assertEquals(34, list.size());
        Employee employee = list.get(0);
        assertEquals("シマゴロー", employee.getName());
        assertEquals(1, employee.getBelongTo().size());
        assertEquals("総務", employee.getBelongTo().iterator().next()
                .getDepartment().getName());
        employee = list.get(2);
        assertEquals("マキ子", employee.getName());
        assertEquals(2, employee.getBelongTo().size());
    }

    public void testLeftOuterJoinTx() throws Exception {
        List<Product> list = selectDistinct().from(
                join(Product.class, "p").leftFetch("p.sales"))
                .getResultList(em);
        assertNotNull(list);
        assertEquals(50, list.size());
        Product p = list.get(0);
        assertEquals("まぐろ", p.getName());
        assertEquals(14, p.getSales().size());
        p = list.get(44);
        assertEquals("フリスビー", p.getName());
        assertEquals(0, p.getSales().size());
    }

    public void testWhereSingleConditionTx() throws Exception {
        List<Employee> list = select().from(Employee.class).where(
                eq("employee.bloodType", "AB")).getResultList(em);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("マル", list.get(0).getName());
        assertEquals("ラスカル", list.get(1).getName());
        assertEquals("マイケル", list.get(1).getName());
    }

    public void testWhereCompoundConditionTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").where(
                between("e.height", 150, 170),
                or(lt("e.weight", 45), gt("e.weight", 70))).getResultList(em);
        assertNotNull(list);
        assertEquals(4, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
        assertEquals("みなみ", list.get(1).getName());
        assertEquals("マー", list.get(2).getName());
        assertEquals("うさぎ", list.get(3).getName());
    }

    public void testAggregateFunctionTx() throws Exception {
        List<Object[]> list = select(count("e"), max("e.height"),
                min("e.weight")).from(Employee.class, "e").getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(new Long(30), list.get(0)[0]);
        assertEquals(new Integer(190), list.get(0)[1]);
        assertEquals(new Integer(38), list.get(0)[2]);
    }

    public void testOrderbyTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").orderby(
                "e.birthday").getResultList(em);
        assertNotNull(list);
        assertEquals(30, list.size());
        assertEquals("ゴッチン", list.get(0).getName());
        assertEquals("マル", list.get(1).getName());
        assertEquals("シマゴロー", list.get(2).getName());
    }

    public void testOrderbyDescTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").orderby(
                desc("e.birthday")).getResultList(em);
        assertNotNull(list);
        assertEquals(30, list.size());
        assertEquals("ミーヤ", list.get(0).getName());
        assertEquals("スー", list.get(1).getName());
        assertEquals("マイケル", list.get(2).getName());
    }
}
