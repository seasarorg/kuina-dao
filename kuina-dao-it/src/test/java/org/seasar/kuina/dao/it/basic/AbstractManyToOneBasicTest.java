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
package org.seasar.kuina.dao.it.basic;

import javax.persistence.EntityManager;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.kuina.dao.it.dao.ManyToOneOwnerDao;
import org.seasar.kuina.dao.it.dao.OneToManyInverseDao;
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;
import org.seasar.kuina.dao.it.entity.OneToManyInverse;

/**
 * 
 * @author nakamura
 */
public abstract class AbstractManyToOneBasicTest extends S2TestCase {

    private EntityManager em;

    private ManyToOneOwnerDao ownerDao;

    private OneToManyInverseDao inverseDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("kuina-dao.dicon");
    }

    public void testFindTx() throws Exception {
        ManyToOneOwner owner = ownerDao.find(1);
        assertEquals("simagoro", owner.getName());
    }
    
    public void testGetTx() throws Exception {
        ManyToOneOwner owner = ownerDao.get(1);
        assertEquals("simagoro", owner.getName());
    }

    public void testPersistTx() throws Exception {
        OneToManyInverse inverse = inverseDao.find(1);
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setOneToManyInverse(inverse);
        ownerDao.persist(owner);
        assertTrue(em.contains(owner));
        em.flush();
    }

    public void testRemoveTx() throws Exception {
        ManyToOneOwner owner = ownerDao.find(1);
        ownerDao.remove(owner);
        assertFalse(em.contains(owner));
        em.flush();
    }

    public void testMergeTx() throws Exception {
        ManyToOneOwner owner = ownerDao.find(1);
        assertEquals("simagoro", owner.getName());
        em.clear();

        owner.setName("hoge");
        ManyToOneOwner owner2 = ownerDao.merge(owner);
        assertEquals("hoge", owner2.getName());
        em.flush();
    }

    public void testContainsTx() throws Exception {
        ManyToOneOwner owner = ownerDao.find(1);
        assertTrue(ownerDao.contains(owner));
        
        ManyToOneOwner owner2 = new ManyToOneOwner();
        assertFalse(ownerDao.contains(owner2));
    }

    public void testRefreshTx() throws Exception {
        ManyToOneOwner owner = ownerDao.find(1);
        assertEquals("simagoro", owner.getName());
        owner.setName("hoge");
        ownerDao.refresh(owner);
        assertEquals("simagoro", owner.getName());
    }

    public void testReadLockTx() throws Exception {
        ManyToOneOwner owner = ownerDao.find(1);
        ownerDao.readLock(owner);
    }

    public void testWriteLockTx() throws Exception {
        ManyToOneOwner owner = ownerDao.find(1);
        ownerDao.writeLock(owner);
    }
    
}
