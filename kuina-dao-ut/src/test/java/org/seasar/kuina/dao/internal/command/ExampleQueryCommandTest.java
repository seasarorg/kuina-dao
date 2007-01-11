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
package org.seasar.kuina.dao.internal.command;

import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.util.ClassUtil;
import org.seasar.kuina.dao.FetchJoin;
import org.seasar.kuina.dao.JoinSpec;
import org.seasar.kuina.dao.OrderbySpec;
import org.seasar.kuina.dao.OrderingSpec;
import org.seasar.kuina.dao.entity.Employee;

/**
 * 
 * @author koichik
 */
@SuppressWarnings("unchecked")
public class ExampleQueryCommandTest extends S2TestCase {

    private EntityManager em;

    private Method method = ClassUtil.getMethod(DummyDao.class, "findEmployee",
            null);

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("jpa.dicon");
    }

    public void testSimpleTx() throws Exception {
        AbstractQueryCommand command = new ExampleQueryCommand(Employee.class,
                method, true, false, -1, -1, -1);
        Employee emp = new Employee();
        emp.setName("シマゴロー");
        List<Employee> list = (List) command.execute(em, new Object[] { emp });
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
    }

    public void testOrderbyTx() throws Exception {
        AbstractQueryCommand command = new ExampleQueryCommand(Employee.class,
                method, true, false, 1, -1, -1);
        Employee emp = new Employee();
        emp.setBloodType("AB");
        List<Employee> list = (List) command.execute(em, new Object[] { emp,
                "birthday" });
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("マル", list.get(0).getName());
        assertEquals("ラスカル", list.get(1).getName());
        assertEquals("マイケル", list.get(2).getName());

        list = (List) command.execute(em, new Object[] { emp,
                new OrderbySpec("birthday", OrderingSpec.DESC) });
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("マイケル", list.get(0).getName());
        assertEquals("ラスカル", list.get(1).getName());
        assertEquals("マル", list.get(2).getName());

        emp.setBloodType("A");
        list = (List) command.execute(em, new Object[] { emp,
                new String[] { "height", "weight" } });
        assertNotNull(list);
        assertEquals(11, list.size());
        assertEquals("ローリー", list.get(0).getName());
        assertEquals("サリー", list.get(1).getName());
        assertEquals("ぴよ", list.get(2).getName());
        assertEquals("マー", list.get(3).getName());
        assertEquals("うさぎ", list.get(4).getName());
        assertEquals("サラ", list.get(5).getName());
        assertEquals("シマゴロー", list.get(6).getName());
        assertEquals("モンチー", list.get(7).getName());
        assertEquals("うー太", list.get(8).getName());
        assertEquals("ミチロー", list.get(9).getName());
        assertEquals("クー", list.get(10).getName());

        list = (List) command.execute(em, new Object[] {
                emp,
                new OrderbySpec[] {
                        new OrderbySpec("height", OrderingSpec.ASC),
                        new OrderbySpec("weight", OrderingSpec.DESC) } });
        assertNotNull(list);
        assertEquals(11, list.size());
        assertEquals("ローリー", list.get(0).getName());
        assertEquals("サリー", list.get(1).getName());
        assertEquals("ぴよ", list.get(2).getName());
        assertEquals("マー", list.get(3).getName());
        assertEquals("うさぎ", list.get(4).getName());
        assertEquals("サラ", list.get(5).getName());
        assertEquals("シマゴロー", list.get(6).getName());
        assertEquals("モンチー", list.get(7).getName());
        assertEquals("ミチロー", list.get(8).getName());
        assertEquals("うー太", list.get(9).getName());
        assertEquals("クー", list.get(10).getName());
    }

    public void testPagingTx() throws Exception {
        AbstractQueryCommand command = new ExampleQueryCommand(Employee.class,
                method, true, false, -1, 1, 2);
        Employee emp = new Employee();
        emp.setBloodType("A");
        List<Employee> list = (List) command.execute(em, new Object[] { emp, 5,
                5 });
        assertNotNull(list);
        assertEquals(5, list.size());
        assertEquals("ぴよ", list.get(0).getName());
        assertEquals("マー", list.get(1).getName());
        assertEquals("サリー", list.get(2).getName());
        assertEquals("うさぎ", list.get(3).getName());
        assertEquals("うー太", list.get(4).getName());
    }

    public interface DummyDao {
        @FetchJoin(value = "belongTo", joinSpec = JoinSpec.LEFT_OUTER_JOIN)
        List<Employee> findEmployee();
    }

}
