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
package org.seasar.kuina.dao.internal.command;

import java.util.List;

import javax.persistence.EntityManager;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.kuina.dao.Employee;

/**
 * 
 * @author koichik
 */
@SuppressWarnings("unchecked")
public class ExampleQueryCommandTest extends S2TestCase {

    private EntityManager em;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("s2hibernate-jpa.dicon");
    }

    public void testSimpleTx() throws Exception {
        ExampleQueryCommand command = new ExampleQueryCommand(
                Employee.class, true, false, -1, -1);
        Employee emp = new Employee();
        emp.setName("シマゴロー");
        List<Employee> list = (List) command.execute(em, new Object[] { emp });
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
    }

    public void testFirstResultMaxResultsTx() throws Exception {
        ExampleQueryCommand command = new ExampleQueryCommand(
                Employee.class, true, false, 1, 2);
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

}
