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
import org.seasar.kuina.dao.TrimSpecification;
import org.seasar.kuina.dao.entity.BelongTo;
import org.seasar.kuina.dao.entity.Customer;
import org.seasar.kuina.dao.entity.Department;
import org.seasar.kuina.dao.entity.Employee;
import org.seasar.kuina.dao.entity.Prefectural;
import org.seasar.kuina.dao.entity.Product;

import static org.seasar.kuina.dao.criteria.CriteriaOperations.*;

/**
 * 
 * @author koichik
 */
public class SelectStatementImplTest extends S2TestCase {

    private EntityManager em;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("jpa.dicon");
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
        List<Employee> list = select("e").from(
                join(Employee.class, "e").inner("e.belongTo", "b").inner(
                        "b.department", "d")).getResultList(em);
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
                eq("employee.bloodType", literal("AB"))).getResultList(em);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("マル", list.get(0).getName());
        assertEquals("ラスカル", list.get(1).getName());
        assertEquals("マイケル", list.get(2).getName());
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

    public void testInTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").where(
                in("e.bloodType", literal("B"), literal("AB"))).getResultList(
                em);
        assertNotNull(list);
        assertEquals(11, list.size());
        assertEquals("ゴッチン", list.get(0).getName());
        assertEquals("マル", list.get(1).getName());
        assertEquals("プリン", list.get(2).getName());
    }

    public void testLikeTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").where(
                like("e.name", "%子")).getResultList(em);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("マキ子", list.get(0).getName());
        assertEquals("ぴー子", list.get(1).getName());
    }

    public void testEndsTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").where(
                like("e.name", "%子")).getResultList(em);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("マキ子", list.get(0).getName());
        assertEquals("ぴー子", list.get(1).getName());
    }

    public void testIsNotNullTx() throws Exception {
        List<BelongTo> list = selectDistinct().from(
                join(BelongTo.class, "b").innerFetch("b.employee")).where(
                isNotNull("b.endDate")).getResultList(em);
        assertNotNull(list);
        assertEquals(4, list.size());
        assertEquals("マキ子", list.get(0).getEmployee().getName());
        assertEquals("みなみ", list.get(1).getEmployee().getName());
        assertEquals("プリン", list.get(2).getEmployee().getName());
        assertEquals("ぱんだ", list.get(3).getEmployee().getName());
    }

    public void testIsNotEmptyTx() throws Exception {
        List<Prefectural> list = select().from(Prefectural.class, "p").where(
                isNotEmpty("p.customers")).getResultList(em);
        assertNotNull(list);
        assertEquals(8, list.size());
        assertEquals("北海道", list.get(0).getName());
        assertEquals("茨城県", list.get(1).getName());
        assertEquals("群馬県", list.get(2).getName());
        assertEquals("埼玉県", list.get(3).getName());
        assertEquals("東京都", list.get(4).getName());
        assertEquals("神奈川県", list.get(5).getName());
        assertEquals("大阪府", list.get(6).getName());
        assertEquals("愛媛県", list.get(7).getName());
    }

    public void testMemberOfTx() throws Exception {
        List<Employee> list = select("e").from(
                join(Employee.class, "e").inner("e.belongTo", "b").inner(
                        "b.department", "d")).where(
                eq("d.name", literal("営業")), memberOf("e", "b.employee"))
                .getResultList(em);
        assertNotNull(list);
        assertEquals(5, list.size());
        assertEquals("ミチロー", list.get(0).getName());
        assertEquals("サラ", list.get(1).getName());
        assertEquals("みなみ", list.get(2).getName());
        assertEquals("ぱんだ", list.get(3).getName());
        assertEquals("くま", list.get(4).getName());
    }

    public void testLengthTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").where(
                ge(length("e.name"), 5)).getResultList(em);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
        assertEquals("にゃん太郎", list.get(1).getName());
    }

    public void testLocateTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").where(
                gt(locate("go", "e.email"), 0)).getResultList(em);
        assertNotNull(list);
        assertEquals(4, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
        assertEquals("ゴッチン", list.get(1).getName());
        assertEquals("ごま", list.get(2).getName());
        assertEquals("ゴン", list.get(3).getName());
    }

    public void testAbsTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").where(
                ge(abs(subtract("e.height", 170)), 15)).getResultList(em);
        assertNotNull(list);
        assertEquals(9, list.size());
        assertEquals("マキ子", list.get(0).getName());
        assertEquals("みなみ", list.get(1).getName());
        assertEquals("プリン", list.get(2).getName());
        assertEquals("ぱんだ", list.get(3).getName());
        assertEquals("ぴよ", list.get(4).getName());
        assertEquals("くま", list.get(5).getName());
        assertEquals("トントン", list.get(6).getName());
        assertEquals("サリー", list.get(7).getName());
        assertEquals("ローリー", list.get(8).getName());
    }

    public void testSqrtTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").where(
                ge(sqrt(subtract("e.height", "e.weight")), 10.7))
                .getResultList(em);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("にゃん太郎", list.get(0).getName());
        assertEquals("うさぎ", list.get(1).getName());
        assertEquals("ミーヤ", list.get(2).getName());
    }

    public void testModTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").where(
                eq(mod("e.height", 10), 0)).getResultList(em);
        assertNotNull(list);
        assertEquals(5, list.size());
        assertEquals("ミチロー", list.get(0).getName());
        assertEquals("ごま", list.get(1).getName());
        assertEquals("くま", list.get(2).getName());
        assertEquals("うー太", list.get(3).getName());
        assertEquals("マイケル", list.get(4).getName());
    }

    public void testSizeTx() throws Exception {
        List<Department> list = select().from(Department.class, "d").where(
                ge(size("d.belongTo"), 10)).getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("販売", list.get(0).getName());
    }

    public void testConcatTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").where(
                ge(length(concat("e.name", path("e.email"))), 22))
                .getResultList(em);
        assertNotNull(list);
        assertEquals(4, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
        assertEquals("ミチロー", list.get(1).getName());
        assertEquals("にゃん太郎", list.get(2).getName());
        assertEquals("ラスカル", list.get(3).getName());
    }

    public void testSubstringTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").where(
                eq(substring("e.email", 2, 1), literal("@"))).getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("キュー", list.get(0).getName());
    }

    public void testTrimTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").where(
                eq(length(trim(TrimSpecification.TRAILING, 'ー', "e.name")), 1))
                .getResultList(em);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("クー", list.get(0).getName());
        assertEquals("マー", list.get(1).getName());
        assertEquals("スー", list.get(2).getName());
    }

    public void testLowerTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").where(
                eq(lower("e.bloodType"), literal("ab"))).getResultList(em);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("マル", list.get(0).getName());
        assertEquals("ラスカル", list.get(1).getName());
        assertEquals("マイケル", list.get(2).getName());
    }

    public void testUpperTx() throws Exception {
        List<Employee> list = select().from(Employee.class, "e").where(
                like(upper("e.email"), literal("GO%"))).getResultList(em);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("ゴッチン", list.get(0).getName());
        assertEquals("ごま", list.get(1).getName());
        assertEquals("ゴン", list.get(2).getName());
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

    public void testAggregateFunctionTx() throws Exception {
        List<Object[]> list = select(count("e"), max("e.height"),
                min("e.weight")).from(Employee.class, "e").getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(new Long(30), list.get(0)[0]);
        assertEquals(new Integer(190), list.get(0)[1]);
        assertEquals(new Integer(38), list.get(0)[2]);
    }

    public void testGroupbyTx() throws Exception {
        List<Object[]> list = select("p.id", count("c")).from(
                join(Customer.class, "c").inner("c.prefectural", "p")).groupby(
                "p.id").orderby("p.id").getResultList(em);
        assertNotNull(list);
        assertEquals(8, list.size());
        assertEquals(new Integer(1), list.get(0)[0]);
        assertEquals(new Long(1), list.get(0)[1]);
        assertEquals(new Integer(8), list.get(1)[0]);
        assertEquals(new Long(3), list.get(1)[1]);
        assertEquals(new Integer(10), list.get(2)[0]);
        assertEquals(new Long(1), list.get(2)[1]);
        assertEquals(new Integer(11), list.get(3)[0]);
        assertEquals(new Long(2), list.get(3)[1]);
        assertEquals(new Integer(13), list.get(4)[0]);
        assertEquals(new Long(19), list.get(4)[1]);
        assertEquals(new Integer(14), list.get(5)[0]);
        assertEquals(new Long(2), list.get(5)[1]);
        assertEquals(new Integer(27), list.get(6)[0]);
        assertEquals(new Long(1), list.get(6)[1]);
        assertEquals(new Integer(38), list.get(7)[0]);
        assertEquals(new Long(1), list.get(7)[1]);
    }

    public void testHavingTx() throws Exception {
        List<Object[]> list = select("p.id", count("c")).from(
                join(Customer.class, "c").inner("c.prefectural", "p")).groupby(
                "p.id").having(ge(count("c"), 3)).orderby("p.id")
                .getResultList(em);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals(new Integer(8), list.get(0)[0]);
        assertEquals(new Long(3), list.get(0)[1]);
        assertEquals(new Integer(13), list.get(1)[0]);
        assertEquals(new Long(19), list.get(1)[1]);
    }
}
