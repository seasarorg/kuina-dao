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
package org.seasar.kuina.dao.interceptor;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.kuina.dao.Employee;
import org.seasar.kuina.dao.EmployeeDao;

/**
 * 
 * @author koichik
 */
public class KuinaDaoInterceptorTest extends S2TestCase {

    private EntityManager em;

    private EmployeeDao dao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("s2hibernate-jpa.dicon");
        include(EmployeeDao.class.getName().replace('.', '/') + ".dicon");
    }

    public void testFindNameAndOrBloodTx() throws Exception {
        List<Employee> list = dao.findByNameAndOrBloodType("シマゴロー", null);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("シマゴロー", list.get(0).getName());

        list = dao.findByNameAndOrBloodType(null, "AB");
        assertEquals(3, list.size());
        assertEquals("マル", list.get(0).getName());
        assertEquals("ラスカル", list.get(1).getName());
        assertEquals("マイケル", list.get(2).getName());

        list = dao.findByNameAndOrBloodType("マル", "AB");
        assertEquals(1, list.size());
        assertEquals("マル", list.get(0).getName());
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

    public void testFindByBirthdayTx() throws Exception {
        List<Employee> list = dao.findByBirthday(new SimpleDateFormat(
                "yyyy-MM-dd").parse("1953-10-01"));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
    }

    public void testGetEmployeeTx() throws Exception {
        Employee emp = dao.getEmployee(1, "シマゴロー");
        assertNotNull(emp);
        assertEquals("シマゴロー", emp.getName());
    }

    public void testGetNameTx() throws Exception {
        String name = dao.getName(1);
        assertEquals("シマゴロー", name);
    }

    public void testContainsTx() throws Exception {
        Employee emp = dao.find(1);
        assertTrue(dao.contains(emp));
    }

    public void testFindTx() throws Exception {
        Employee emp = dao.find(1);
        assertNotNull(emp);
        assertEquals("シマゴロー", emp.getName());
    }

    public void testGetTx() throws Exception {
        Employee emp = dao.get(1);
        assertNotNull(emp);
        assertEquals("シマゴロー", emp.getName());
    }

    public void testMergeTx() throws Exception {
        Employee emp = dao.get(1);
        assertEquals("シマゴロー", emp.getName());
        em.clear();

        Employee emp2 = dao.merge(emp);
        assertEquals("シマゴロー", emp2.getName());
    }

    public void testPersistTx() throws Exception {
        Employee emp = new Employee();
        dao.persist(emp);
    }

    public void testReadLockTx() throws Exception {
        Employee emp = dao.find(1);
        dao.readLock(emp);
    }

    public void testRefreshTx() throws Exception {
        Employee emp = dao.find(1);
        dao.refresh(emp);
    }

    public void testRemoveTx() throws Exception {
        Employee emp = dao.find(1);
        dao.remove(emp);
    }

    public void testWriteLockTx() throws Exception {
        Employee emp = dao.find(1);
        dao.writeLock(emp);
    }

}
