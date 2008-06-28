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

import static org.junit.Assert.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class OneToOneInverseDaoTest {

    private EntityManager em;

    private OneToOneInverseDao dao;

    public void find() throws Exception {
        OneToOneInverse inverse = dao.find(3);
        assertEquals("Personnel", inverse.getName());
    }

    public void get() throws Exception {
        OneToOneInverse inverse = dao.getReference(3);
        assertEquals("Personnel", inverse.getName());
    }

    public void persist() throws Exception {
        OneToOneInverse inverse = new OneToOneInverse();
        dao.persist(inverse);
        assertTrue(em.contains(inverse));
        em.flush();
    }

    public void remove() throws Exception {
        OneToOneInverse inverse = dao.find(3);
        em.remove(inverse.getOneToOneOwner());
        dao.remove(inverse);
        assertFalse(em.contains(inverse));
        em.flush();
    }

    public void merge() throws Exception {
        OneToOneInverse inverse = dao.find(3);
        assertEquals("Personnel", inverse.getName());
        em.clear();

        inverse.setName("hoge");
        OneToOneInverse inverse2 = dao.merge(inverse);
        assertEquals("hoge", inverse2.getName());
        em.flush();
    }

    public void contains() throws Exception {
        OneToOneInverse inverse = dao.find(3);
        assertTrue(dao.contains(inverse));

        OneToOneInverse inverse2 = new OneToOneInverse();
        assertFalse(dao.contains(inverse2));
    }

    public void refresh() throws Exception {
        OneToOneInverse inverse = dao.find(3);
        assertEquals("Personnel", inverse.getName());
        inverse.setName("hoge");
        dao.refresh(inverse);
        assertEquals("Personnel", inverse.getName());
    }

    public void readLock() throws Exception {
        OneToOneInverse inverse = dao.find(3);
        dao.readLock(inverse);
        em.flush();
    }

    public void writeLock() throws Exception {
        OneToOneInverse inverse = dao.find(3);
        dao.writeLock(inverse);
        em.flush();
    }

}
