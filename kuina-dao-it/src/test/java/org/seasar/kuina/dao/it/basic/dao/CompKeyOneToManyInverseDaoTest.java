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
import org.seasar.extension.dxo.DateUtil;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.entity.CompKeyManyToOneOwner;
import org.seasar.kuina.dao.it.entity.CompKeyOneToManyInverse;
import org.seasar.kuina.dao.it.entity.CompKeyOneToManyInverseId;

import static org.junit.Assert.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class CompKeyOneToManyInverseDaoTest {

    private EntityManager em;

    private CompKeyOneToManyInverseDao dao;

    public void find() throws Exception {
        CompKeyOneToManyInverseId id =
            new CompKeyOneToManyInverseId(1, DateUtil.newDate(2007, 1, 1));
        CompKeyOneToManyInverse inverse = dao.find(id);
        assertEquals("Business", inverse.getName());
    }

    public void get() throws Exception {
        CompKeyOneToManyInverseId id =
            new CompKeyOneToManyInverseId(1, DateUtil.newDate(2007, 1, 1));
        CompKeyOneToManyInverse inverse = dao.getReference(id);
        assertEquals("Business", inverse.getName());
    }

    public void persist() throws Exception {
        CompKeyOneToManyInverse inverse = new CompKeyOneToManyInverse();
        CompKeyOneToManyInverseId id =
            new CompKeyOneToManyInverseId(1, DateUtil.newDate(2007, 1, 2));
        inverse.setId(id);
        dao.persist(inverse);
        assertTrue(em.contains(inverse));
        em.flush();
    }

    public void remove() throws Exception {
        CompKeyOneToManyInverseId id =
            new CompKeyOneToManyInverseId(1, DateUtil.newDate(2007, 1, 1));
        CompKeyOneToManyInverse inverse = dao.find(id);
        for (final CompKeyManyToOneOwner owner : inverse
            .getCompKeyManyToOneOwners()) {
            em.remove(owner);
        }
        dao.remove(inverse);
        assertFalse(em.contains(inverse));
        em.flush();
    }

    public void merge() throws Exception {
        CompKeyOneToManyInverseId id =
            new CompKeyOneToManyInverseId(1, DateUtil.newDate(2007, 1, 1));
        CompKeyOneToManyInverse inverse = dao.find(id);
        assertEquals("Business", inverse.getName());
        em.clear();

        inverse.setName("hoge");
        CompKeyOneToManyInverse inverse2 = dao.merge(inverse);
        assertEquals("hoge", inverse2.getName());
        em.flush();
    }

    public void contains() throws Exception {
        CompKeyOneToManyInverseId id =
            new CompKeyOneToManyInverseId(1, DateUtil.newDate(2007, 1, 1));
        CompKeyOneToManyInverse inverse = dao.find(id);
        assertTrue(dao.contains(inverse));

        CompKeyOneToManyInverse inverse2 = new CompKeyOneToManyInverse();
        assertFalse(dao.contains(inverse2));
    }

    public void refresh() throws Exception {
        CompKeyOneToManyInverseId id =
            new CompKeyOneToManyInverseId(1, DateUtil.newDate(2007, 1, 1));
        CompKeyOneToManyInverse inverse = dao.find(id);
        assertEquals("Business", inverse.getName());
        inverse.setName("hoge");
        dao.refresh(inverse);
        assertEquals("Business", inverse.getName());
    }

    public void readLock() throws Exception {
        CompKeyOneToManyInverseId id =
            new CompKeyOneToManyInverseId(1, DateUtil.newDate(2007, 1, 1));
        CompKeyOneToManyInverse inverse = dao.find(id);
        dao.readLock(inverse);
        em.flush();
    }

    public void writeLock() throws Exception {
        CompKeyOneToManyInverseId id =
            new CompKeyOneToManyInverseId(1, DateUtil.newDate(2007, 1, 1));
        CompKeyOneToManyInverse inverse = dao.find(id);
        dao.writeLock(inverse);
        em.flush();
    }
}
