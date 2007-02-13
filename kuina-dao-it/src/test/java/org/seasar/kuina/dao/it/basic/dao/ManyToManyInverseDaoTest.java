/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
public class ManyToManyInverseDaoTest {

    private EntityManager em;

    private ManyToManyInverseDao dao;

    public void find() throws Exception {
        ManyToManyInverse inverse = dao.find(1);
        assertEquals("Business", inverse.getName());
    }

    public void get() throws Exception {
        ManyToManyInverse inverse = dao.get(1);
        assertEquals("Business", inverse.getName());
    }

    public void persist() throws Exception {
        ManyToManyInverse inverse = new ManyToManyInverse();
        dao.persist(inverse);
        assertTrue(em.contains(inverse));
        em.flush();
    }

    public void remove() throws Exception {
        ManyToManyInverse inverse = dao.find(1);
        for (ManyToManyOwner owner : inverse.getManyToManyOwners()) {
            em.remove(owner);
        }
        dao.remove(inverse);
        assertFalse(em.contains(inverse));
        em.flush();
    }

    public void merge() throws Exception {
        ManyToManyInverse inverse = dao.find(1);
        assertEquals("Business", inverse.getName());
        em.clear();

        inverse.setName("hoge");
        ManyToManyInverse inverse2 = dao.merge(inverse);
        assertEquals("hoge", inverse2.getName());
        em.flush();
    }

    public void contains() throws Exception {
        ManyToManyInverse inverse = dao.find(1);
        assertTrue(dao.contains(inverse));

        ManyToManyInverse inverse2 = new ManyToManyInverse();
        assertFalse(dao.contains(inverse2));
    }

    public void refresh() throws Exception {
        ManyToManyInverse inverse = dao.find(1);
        assertEquals("Business", inverse.getName());
        inverse.setName("hoge");
        dao.refresh(inverse);
        assertEquals("Business", inverse.getName());
    }

    public void readLock() throws Exception {
        ManyToManyInverse inverse = dao.find(1);
        dao.readLock(inverse);
        em.flush();
    }

    public void writeLock() throws Exception {
        ManyToManyInverse inverse = dao.find(1);
        dao.writeLock(inverse);
        em.flush();
    }
}
