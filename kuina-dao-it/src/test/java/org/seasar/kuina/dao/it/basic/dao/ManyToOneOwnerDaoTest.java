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
package org.seasar.kuina.dao.it.basic.dao;

import javax.persistence.EntityManager;

import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;
import org.seasar.kuina.dao.it.entity.OneToManyInverse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class ManyToOneOwnerDaoTest {

    private EntityManager em;

    private ManyToOneOwnerDao ownerDao;

    public void find() throws Exception {
        ManyToOneOwner owner = ownerDao.find(1);
        assertEquals("simagoro", owner.getName());
    }

    public void get() throws Exception {
        ManyToOneOwner owner = ownerDao.get(1);
        assertEquals("simagoro", owner.getName());
    }

    public void persist() throws Exception {
        OneToManyInverse inverse = em.find(OneToManyInverse.class, 1);
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setOneToManyInverse(inverse);
        owner.setSubOneToManyInverse(inverse);
        ownerDao.persist(owner);
        assertTrue(em.contains(owner));
        em.flush();
    }

    public void remove() throws Exception {
        ManyToOneOwner owner = ownerDao.find(1);
        ownerDao.remove(owner);
        assertFalse(em.contains(owner));
        em.flush();
    }

    public void merge() throws Exception {
        ManyToOneOwner owner = ownerDao.find(1);
        assertEquals("simagoro", owner.getName());
        em.clear();

        owner.setName("hoge");
        ManyToOneOwner owner2 = ownerDao.merge(owner);
        assertEquals("hoge", owner2.getName());
        em.flush();
    }

    public void contains() throws Exception {
        ManyToOneOwner owner = ownerDao.find(1);
        assertTrue(ownerDao.contains(owner));

        ManyToOneOwner owner2 = new ManyToOneOwner();
        assertFalse(ownerDao.contains(owner2));
    }

    public void refresh() throws Exception {
        ManyToOneOwner owner = ownerDao.find(1);
        assertEquals("simagoro", owner.getName());
        owner.setName("hoge");
        ownerDao.refresh(owner);
        assertEquals("simagoro", owner.getName());
    }

    public void readLock() throws Exception {
        ManyToOneOwner owner = ownerDao.find(1);
        ownerDao.readLock(owner);
    }

    public void writeLock() throws Exception {
        ManyToOneOwner owner = ownerDao.find(1);
        ownerDao.writeLock(owner);
    }

}