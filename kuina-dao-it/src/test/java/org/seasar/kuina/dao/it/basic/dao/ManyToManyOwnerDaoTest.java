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
import org.seasar.kuina.dao.it.entity.ManyToManyInverse;
import org.seasar.kuina.dao.it.entity.ManyToManyOwner;

import static org.junit.Assert.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class ManyToManyOwnerDaoTest {

    private EntityManager em;

    private ManyToManyOwnerDao dao;

    public void find() throws Exception {
        ManyToManyOwner owner = dao.find(3);
        assertEquals("maki", owner.getName());
    }

    public void get() throws Exception {
        ManyToManyOwner owner = dao.get(3);
        assertEquals("maki", owner.getName());
    }

    public void persist() throws Exception {
        ManyToManyInverse inverse = em.find(ManyToManyInverse.class, 1);
        ManyToManyOwner owner = new ManyToManyOwner();
        owner.addManyToManyInverse(inverse);
        dao.persist(owner);
        assertTrue(em.contains(owner));
        em.flush();
    }

    public void remove() throws Exception {
        ManyToManyOwner owner = dao.find(3);
        dao.remove(owner);
        assertFalse(em.contains(owner));
        em.flush();
    }

    public void merge() throws Exception {
        ManyToManyOwner owner = dao.find(3);
        assertEquals("maki", owner.getName());
        em.clear();

        owner.setName("hoge");
        ManyToManyOwner owner2 = dao.merge(owner);
        assertEquals("hoge", owner2.getName());
        em.flush();
    }

    public void contains() throws Exception {
        ManyToManyOwner owner = dao.find(3);
        assertTrue(dao.contains(owner));

        ManyToManyOwner owner2 = new ManyToManyOwner();
        assertFalse(dao.contains(owner2));
    }

    public void refresh() throws Exception {
        ManyToManyOwner owner = dao.find(3);
        assertEquals("maki", owner.getName());
        owner.setName("hoge");
        dao.refresh(owner);
        assertEquals("maki", owner.getName());
    }

    public void readLock() throws Exception {
        ManyToManyOwner owner = dao.find(3);
        dao.readLock(owner);
    }

    public void writeLock() throws Exception {
        ManyToManyOwner owner = dao.find(3);
        dao.writeLock(owner);
    }

}
