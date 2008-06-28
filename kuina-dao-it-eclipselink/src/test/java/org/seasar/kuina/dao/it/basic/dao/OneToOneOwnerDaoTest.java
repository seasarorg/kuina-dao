/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import org.seasar.kuina.dao.it.entity.OneToOneInverse;
import org.seasar.kuina.dao.it.entity.OneToOneOwner;

import static org.junit.Assert.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class OneToOneOwnerDaoTest {

    private EntityManager em;

    private OneToOneOwnerDao dao;

    public void find() throws Exception {
        OneToOneOwner owner = dao.find(3);
        assertEquals("maki", owner.getName());
    }

    public void get() throws Exception {
        OneToOneOwner owner = dao.getReference(3);
        assertEquals("maki", owner.getName());
    }

    public void persist() throws Exception {
        OneToOneInverse inverse = new OneToOneInverse();
        em.persist(inverse);
        OneToOneOwner owner = new OneToOneOwner();
        owner.setOneToOneInverse(inverse);
        dao.persist(owner);
        assertTrue(em.contains(owner));
        em.flush();
    }

    public void remove() throws Exception {
        OneToOneOwner owner = dao.find(3);
        dao.remove(owner);
        assertFalse(em.contains(owner));
        em.flush();
    }

    public void merge() throws Exception {
        OneToOneOwner owner = dao.find(3);
        assertEquals("maki", owner.getName());
        em.clear();

        owner.setName("hoge");
        OneToOneOwner owner2 = dao.merge(owner);
        assertEquals("hoge", owner2.getName());
        em.flush();
    }

    public void contains() throws Exception {
        OneToOneOwner owner = dao.find(3);
        assertTrue(dao.contains(owner));

        OneToOneOwner owner2 = new OneToOneOwner();
        assertFalse(dao.contains(owner2));
    }

    public void refresh() throws Exception {
        OneToOneOwner owner = dao.find(3);
        assertEquals("maki", owner.getName());
        owner.setName("hoge");
        dao.refresh(owner);
        assertEquals("maki", owner.getName());
    }

    public void readLock() throws Exception {
        OneToOneOwner owner = dao.find(3);
        dao.readLock(owner);
        em.flush();
    }

    public void writeLock() throws Exception {
        OneToOneOwner owner = dao.find(3);
        dao.writeLock(owner);
        em.flush();
    }

}
