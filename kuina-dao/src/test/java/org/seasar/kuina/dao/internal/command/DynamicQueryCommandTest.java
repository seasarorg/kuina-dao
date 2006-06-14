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
import org.seasar.kuina.dao.criteria.impl.grammar.declaration.IdentificationVariableDeclarationImpl;
import org.seasar.kuina.dao.internal.binder.ObjectParameterBinder;
import org.seasar.kuina.dao.internal.binder.ParameterBinder;

import static org.seasar.kuina.dao.criteria.CriteriaOperations.path;

/**
 * 
 * @author koichik
 */
@SuppressWarnings("unchecked")
public class DynamicQueryCommandTest extends S2TestCase {

    private EntityManager em;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("s2hibernate-jpa.dicon");
    }

    public void testFindByNameTx() throws Exception {
        DynamicQueryCommand command = new DynamicQueryCommand(Employee.class,
                true, false, new IdentificationVariableDeclarationImpl(
                        Employee.class), new String[] { "name" },
                new ParameterBinder[] { new ObjectParameterBinder("name") });
        List<Employee> list = (List) command.execute(em,
                new Object[] { "シマゴロー" });
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
    }

    public void testFindByNameAndBloodtypeTx() throws Exception {
        DynamicQueryCommand command = new DynamicQueryCommand(Employee.class,
                true, false, new IdentificationVariableDeclarationImpl(
                        Employee.class), new String[] { "name", "bloodType" },
                new ParameterBinder[] { new ObjectParameterBinder("name"),
                        new ObjectParameterBinder("bloodType") });
        List<Employee> list = (List) command.execute(em, new Object[] { null,
                "AB" });
        assertEquals(3, list.size());
        assertEquals("マル", list.get(0).getName());
        assertEquals("ラスカル", list.get(1).getName());
        assertEquals("マイケル", list.get(2).getName());
    }

    public void testFindByDeparmentNameTx() throws Exception {
        DynamicQueryCommand command = new DynamicQueryCommand(Employee.class,
                true, false, new IdentificationVariableDeclarationImpl(
                        Employee.class).inner(path("employee.belongTo")).inner(
                        path("belongTo.department")),
                new String[] { "department$name" },
                new ParameterBinder[] { new ObjectParameterBinder(
                        "department$name") });
        List<Employee> list = (List) command.execute(em, new Object[] { "営業" });
        assertNotNull(list);
        assertEquals(5, list.size());
        assertEquals("ミチロー", list.get(0).getName());
        assertEquals("サラ", list.get(1).getName());
        assertEquals("みなみ", list.get(2).getName());
        assertEquals("ぱんだ", list.get(3).getName());
        assertEquals("くま", list.get(4).getName());
    }
}
