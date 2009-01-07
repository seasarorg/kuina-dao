/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
import org.seasar.kuina.dao.it.entity.CompKeyManyToOneOwnerId;
import org.seasar.kuina.dao.it.entity.CompKeyOneToManyInverse;
import org.seasar.kuina.dao.it.entity.CompKeyOneToManyInverseId;

import static org.junit.Assert.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class CompKeyManyToOneOwnerDaoTest {

    private EntityManager em;

    private CompKeyManyToOneOwnerDao dao;

    public void find() throws Exception {
        CompKeyManyToOneOwnerId id =
            new CompKeyManyToOneOwnerId(1, DateUtil.newDate(2007, 1, 1));
        CompKeyManyToOneOwner owner = dao.find(id);
        assertEquals("simagoro", owner.getName());
    }

    public void get() throws Exception {
        CompKeyManyToOneOwnerId id =
            new CompKeyManyToOneOwnerId(1, DateUtil.newDate(2007, 1, 1));
        CompKeyManyToOneOwner owner = dao.getReference(id);
        assertEquals("simagoro", owner.getName());
    }

    public void persist() throws Exception {
        CompKeyOneToManyInverse inverse =
            em.find(
                CompKeyOneToManyInverse.class,
                new CompKeyOneToManyInverseId(1, DateUtil.newDate(2007, 1, 1)));
        CompKeyManyToOneOwnerId id =
            new CompKeyManyToOneOwnerId(1, DateUtil.newDate(2007, 1, 2));
        CompKeyManyToOneOwner owner = new CompKeyManyToOneOwner();
        owner.setId(id);
        owner.setCompKeyOneToManyInverse(inverse);
        dao.persist(owner);
        assertTrue(em.contains(owner));
        em.flush();
    }

    public void remove() throws Exception {
        CompKeyManyToOneOwnerId id =
            new CompKeyManyToOneOwnerId(1, DateUtil.newDate(2007, 1, 1));
        CompKeyManyToOneOwner owner = dao.find(id);
        dao.remove(owner);
        assertFalse(em.contains(owner));
        em.flush();
    }

    public void merge() throws Exception {
        CompKeyManyToOneOwnerId id =
            new CompKeyManyToOneOwnerId(1, DateUtil.newDate(2007, 1, 1));
        CompKeyManyToOneOwner owner = dao.find(id);
        assertEquals("simagoro", owner.getName());
        em.clear();

        owner.setName("hoge");
        CompKeyManyToOneOwner owner2 = dao.merge(owner);
        assertEquals("hoge", owner2.getName());
        em.flush();
    }

    public void contains() throws Exception {
        CompKeyManyToOneOwnerId id =
            new CompKeyManyToOneOwnerId(1, DateUtil.newDate(2007, 1, 1));
        CompKeyManyToOneOwner owner = dao.find(id);
        assertTrue(dao.contains(owner));

        CompKeyManyToOneOwner owner2 = new CompKeyManyToOneOwner();
        assertFalse(dao.contains(owner2));
    }

    public void refresh() throws Exception {
        CompKeyManyToOneOwnerId id =
            new CompKeyManyToOneOwnerId(1, DateUtil.newDate(2007, 1, 1));
        CompKeyManyToOneOwner owner = dao.find(id);
        assertEquals("simagoro", owner.getName());
        owner.setName("hoge");
        dao.refresh(owner);
        assertEquals("simagoro", owner.getName());
    }

    public void readLock() throws Exception {
        CompKeyManyToOneOwnerId id =
            new CompKeyManyToOneOwnerId(1, DateUtil.newDate(2007, 1, 1));
        CompKeyManyToOneOwner owner = dao.find(id);
        dao.readLock(owner);
        em.flush();
    }

    public void writeLock() throws Exception {
        CompKeyManyToOneOwnerId id =
            new CompKeyManyToOneOwnerId(1, DateUtil.newDate(2007, 1, 1));
        CompKeyManyToOneOwner owner = dao.find(id);
        dao.writeLock(owner);
        em.flush();
    }

}
