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
public class QueryByExampleCommandTest extends S2TestCase {

    private EntityManager em;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("s2hibernate-jpa.dicon");
    }

    public void testSimpleTx() throws Exception {
        QueryByExampleCommand command = new QueryByExampleCommand(
                Employee.class, true, false);
        Employee emp = new Employee();
        emp.setName("シマゴロー");
        List<Employee> list = (List) command.execute(em, new Object[] { emp });
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
    }

}
