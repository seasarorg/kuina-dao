/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.kuina.dao.it.named.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.runner.RunWith;
import org.seasar.extension.dxo.DateUtil;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;

import static org.junit.Assert.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class ManyToOneOwnerDaoTest {

    private EntityManager em;

    private ManyToOneOwnerDao dao;

    public void updateRtiredFlagById() throws Exception {
        dao.updateRetiredFlagById(1, true);
        ManyToOneOwner owner = em.find(ManyToOneOwner.class, 1);
        assertTrue(owner.isRetired());
    }

    public void removeById() throws Exception {
        dao.removeById(1);
        assertNull(em.find(ManyToOneOwner.class, 1));
    }

    public void findByName() throws Exception {
        List<ManyToOneOwner> list = dao.findByName("simagoro");
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void findByNameNoFlush() throws Exception {
        ManyToOneOwner owner = em.find(ManyToOneOwner.class, 1);
        owner.setName("hoge");

        List<ManyToOneOwner> list = dao.findByNameNoFlush("simagoro");
        assertNotNull(list);
        assertEquals(1, list.size());
        assertSame(owner, list.get(0));

        list = dao.findByName("simagoro");
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    public void findByBirthday() throws Exception {
        List<ManyToOneOwner> list =
            dao.findByBirthday(DateUtil.newDate(1953, 10, 1));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void findByOneToManayInverseName() throws Exception {
        List<ManyToOneOwner> list = dao.findByOneToManyInverseName("Personnel");
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("nekomaru", list.get(0).getName());
        assertEquals("nyantaro", list.get(1).getName());
        assertEquals("monchi", list.get(2).getName());
    }

    public void getName() throws Exception {
        String name = dao.getName(1);
        assertEquals("simagoro", name);
    }

    public void getCount() throws Exception {
        Number count = dao.getCount();
        assertEquals(30, count.intValue());
    }

    public void getCountByBloodType() throws Exception {
        Number count = dao.getCountByBloodType("AB");
        assertEquals(3, count.intValue());
    }

    public void getCountByBloodTypeNoFlush() throws Exception {
        ManyToOneOwner owner = em.find(ManyToOneOwner.class, 4);
        owner.setBloodType("O");

        Number count = dao.getCountByBloodTypeNoFlush("AB");
        assertEquals(3, count.intValue());

        count = dao.getCountByBloodType("AB");
        assertEquals(2, count.intValue());
    }

    public void findByWeight() {
        List<ManyToOneOwner> list = dao.findByWeigth(95);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("panda", list.get(0).getName());
    }

    public void findByHeight() {
        List<ManyToOneOwner> list = dao.findByHeigth(185);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("panda", list.get(0).getName());
    }

}
