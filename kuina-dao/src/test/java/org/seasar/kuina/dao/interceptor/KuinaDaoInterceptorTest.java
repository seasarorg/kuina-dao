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

import javax.transaction.TransactionManager;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.kuina.dao.Employee;

/**
 * 
 * @author koichik
 */
public class KuinaDaoInterceptorTest extends S2TestCase {

    TransactionManager tm;

    TestDao dao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include(getClass().getName().replace('.', '/') + ".dicon");
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

    public void testMerge() throws Exception {
        tm.begin();
        Employee emp = dao.get(1);
        assertEquals("シマゴロー", emp.getName());
        tm.rollback();

        tm.begin();
        Employee emp2 = dao.merge(emp);
        assertEquals("シマゴロー", emp2.getName());
        tm.rollback();
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
        dao.readLock(emp);
    }

    public static interface TestDao {
        boolean contains(Employee employee);

        Employee find(int id);

        Employee get(int id);

        Employee merge(Employee employee);

        void persist(Employee employee);

        void readLock(Employee employee);

        void refresh(Employee employee);

        void remove(Employee employee);

        void writeLock(Employee employee);
    }

}
