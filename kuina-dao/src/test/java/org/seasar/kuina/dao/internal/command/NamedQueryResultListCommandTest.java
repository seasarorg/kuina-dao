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

import org.seasar.extension.unit.S2TestCase;
import org.seasar.kuina.dao.Employee;
import org.seasar.kuina.dao.EmployeeDao;

/**
 * 
 * @author koichik
 */
public class NamedQueryResultListCommandTest extends S2TestCase {

    private EmployeeDao dao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("s2hibernate-jpa.dicon");
        include(EmployeeDao.class.getName().replace('.', '/') + ".dicon");
    }

    public void testFindByNameTx() throws Exception {
        List<Employee> list = dao.findByName("シマゴロー");
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
    }

    public void testFindByDepartmentNameTx() throws Exception {
        List<Employee> list = dao.findByDepartmentName("営業", 0, 2);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("ミチロー", list.get(0).getName());
        assertEquals("サラ", list.get(1).getName());

        list = dao.findByDepartmentName("営業", 2, 2);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("みなみ", list.get(0).getName());
        assertEquals("ぱんだ", list.get(1).getName());

        list = dao.findByDepartmentName("営業", 4, 2);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("くま", list.get(0).getName());
    }
}
