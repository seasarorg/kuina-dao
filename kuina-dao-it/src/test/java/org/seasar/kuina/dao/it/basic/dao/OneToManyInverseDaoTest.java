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
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;
import org.seasar.kuina.dao.it.entity.OneToManyInverse;

import static org.junit.Assert.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class OneToManyInverseDaoTest {

    private EntityManager em;

    private OneToManyInverseDao dao;

    public void find() throws Exception {
        OneToManyInverse inverse = dao.find(1);
        assertEquals("Business", inverse.getName());
    }

    public void get() throws Exception {
        OneToManyInverse inverse = dao.getReference(1);
        assertEquals("Business", inverse.getName());
    }

    public void persist() throws Exception {
        OneToManyInverse inverse = new OneToManyInverse();
        dao.persist(inverse);
        assertTrue(em.contains(inverse));
        em.flush();
    }

    public void remove() throws Exception {
        OneToManyInverse inverse = dao.find(1);
        for (final ManyToOneOwner owner : inverse.getManyToOneOwners()) {
            em.remove(owner);
        }
        for (final ManyToOneOwner owner : inverse.getSubManyToOneOwners()) {
            em.remove(owner);
        }
        dao.remove(inverse);
        assertFalse(em.contains(inverse));
        em.flush();
    }

    public void merge() throws Exception {
        OneToManyInverse inverse = dao.find(1);
        assertEquals("Business", inverse.getName());
        em.clear();

        inverse.setName("hoge");
        OneToManyInverse inverse2 = dao.merge(inverse);
        assertEquals("hoge", inverse2.getName());
        em.flush();
    }

    public void contains() throws Exception {
        OneToManyInverse inverse = dao.find(1);
        assertTrue(dao.contains(inverse));

        OneToManyInverse inverse2 = new OneToManyInverse();
        assertFalse(dao.contains(inverse2));
    }

    public void refresh() throws Exception {
        OneToManyInverse inverse = dao.find(1);
        assertEquals("Business", inverse.getName());
        inverse.setName("hoge");
        dao.refresh(inverse);
        assertEquals("Business", inverse.getName());
    }

    public void readLock() throws Exception {
        OneToManyInverse inverse = dao.find(1);
        dao.readLock(inverse);
    }

    public void writeLock() throws Exception {
        OneToManyInverse inverse = dao.find(1);
        dao.writeLock(inverse);
    }
}
