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
package org.seasar.kuina.dao.it.named.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.seasar.extension.dxo.DateUtil;
import org.seasar.kuina.dao.PositionalParameter;
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * 
 * @author nakamura
 */
public abstract class AbstractManyToOneOwnerDaoTest {

    private EntityManager em;

    private ManyToOneOwnerDao ownerDao;

    public void updateRtiredFlagById() throws Exception {
        ownerDao.updateRetiredFlagById(1, true);
        ManyToOneOwner owner = em.find(ManyToOneOwner.class, 1);
        assertTrue(owner.isRetired());
    }

    public void removeById() throws Exception {
        ownerDao.removeById(1);
        assertNull(em.find(ManyToOneOwner.class, 1));
    }

    public void findByName() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByName("simagoro");
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void findByBirthday() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByBirthday(DateUtil.newDate(
                1953, 10, 1));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void findByOneToManayInverseName() throws Exception {
        List<ManyToOneOwner> list = ownerDao
                .findByOneToManyInverseName("Personnel");
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("nekomaru", list.get(0).getName());
        assertEquals("nyantaro", list.get(1).getName());
        assertEquals("monchi", list.get(2).getName());
    }

    public void getName() throws Exception {
        String name = ownerDao.getName(1);
        assertEquals("simagoro", name);
    }

    public void getCount() throws Exception {
        int count = ownerDao.getCount();
        assertEquals(30, count);
    }

    @PositionalParameter
    public void getCountByBloodType() throws Exception {
        int count = ownerDao.getCountByBloodType("AB");
        assertEquals(3, count);
    }
}
