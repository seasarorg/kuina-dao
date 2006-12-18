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
package org.seasar.kuina.dao.it.dto.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.dto.ManyToOneOwnerDto;
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;
import org.seasar.kuina.dao.it.entity.OneToManyInverse;

import static org.junit.Assert.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class ManyToOneOwnerDaoTest {

    private EntityManager em;

    private ManyToOneOwnerDao ownerDao;

    public void findByDto_name_EQ() throws Exception {
        ManyToOneOwnerDto dto = new ManyToOneOwnerDto();
        dto.setName_EQ("simagoro");
        List<ManyToOneOwner> list = ownerDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void findByDto_name_NE() throws Exception {
        ManyToOneOwnerDto dto = new ManyToOneOwnerDto();
        dto.setName_NE("simagoro");
        List<ManyToOneOwner> list = ownerDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(29, list.size());
        assertEquals("gochin", list.get(0).getName());
    }

    public void findByDto_name_GT_name__LT() throws Exception {
        ManyToOneOwnerDto dto = new ManyToOneOwnerDto();
        dto.setName_GT("ma");
        dto.setName_LT("michiro");
        List<ManyToOneOwner> list = ownerDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("maki", list.get(0).getName());
        assertEquals("maru", list.get(1).getName());
    }

    public void findByDto_name_GE_name_LE() throws Exception {
        ManyToOneOwnerDto dto = new ManyToOneOwnerDto();
        dto.setName_GE("ma");
        dto.setName_LE("michiro");
        List<ManyToOneOwner> list = ownerDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(4, list.size());
        assertEquals("maki", list.get(0).getName());
        assertEquals("maru", list.get(1).getName());
        assertEquals("michiro", list.get(2).getName());
        assertEquals("ma", list.get(3).getName());
    }

    public void findByDto_name_IN() throws Exception {
        ManyToOneOwnerDto dto = new ManyToOneOwnerDto();
        dto.setName_IN("ma", "michiro", "monchi");
        List<ManyToOneOwner> list = ownerDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("michiro", list.get(0).getName());
        assertEquals("monchi", list.get(1).getName());
        assertEquals("ma", list.get(2).getName());
    }

    public void findByDto_name_LIKE() throws Exception {
        ManyToOneOwnerDto dto = new ManyToOneOwnerDto();
        dto.setName_LIKE("%om%");
        List<ManyToOneOwner> list = ownerDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("goma", list.get(0).getName());
        assertEquals("nekomaru", list.get(1).getName());
    }

    public void findByDto_name_STARTS() throws Exception {
        ManyToOneOwnerDto dto = new ManyToOneOwnerDto();
        dto.setName_STARTS("sa");
        List<ManyToOneOwner> list = ownerDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("sara", list.get(0).getName());
        assertEquals("sary", list.get(1).getName());
    }

    public void findByDto_name_ENDS() throws Exception {
        ManyToOneOwnerDto dto = new ManyToOneOwnerDto();
        dto.setName_ENDS("ro");
        List<ManyToOneOwner> list = ownerDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("simagoro", list.get(0).getName());
        assertEquals("michiro", list.get(1).getName());
        assertEquals("nyantaro", list.get(2).getName());
    }

    public void findByDto_name_CONTAINS() throws Exception {
        ManyToOneOwnerDto dto = new ManyToOneOwnerDto();
        dto.setName_CONTAINS("om");
        List<ManyToOneOwner> list = ownerDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("goma", list.get(0).getName());
        assertEquals("nekomaru", list.get(1).getName());
    }

    public void findByDto_name_IS_NULL() throws Exception {
        ManyToOneOwnerDto dto = new ManyToOneOwnerDto();
        dto.setName_IS_NULL(true);
        List<ManyToOneOwner> list = ownerDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    public void findByDto_name_IS_NOT_NULL() throws Exception {
        ManyToOneOwnerDto dto = new ManyToOneOwnerDto();
        dto.setName_IS_NOT_NULL(true);
        List<ManyToOneOwner> list = ownerDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(30, list.size());
    }

    public void findByDtoMultiCondition() throws Exception {
        ManyToOneOwnerDto dto = new ManyToOneOwnerDto();
        dto.setName_STARTS("m");
        dto.setFirstResult(4);
        dto.setMaxResults(3);
        dto.setOrderby("height DESC", "weight");
        List<ManyToOneOwner> list = ownerDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("ma", list.get(0).getName());
        assertEquals("maru", list.get(1).getName());
        assertEquals("maki", list.get(2).getName());
    }

    public void findByDto_oneToManyInverse_EQ() throws Exception {
        OneToManyInverse oneToManyInverse = em.find(OneToManyInverse.class, 3);
        ManyToOneOwnerDto dto = new ManyToOneOwnerDto();
        dto.setOneToManyInverse_EQ(oneToManyInverse);
        List<ManyToOneOwner> list = ownerDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("nekomaru", list.get(0).getName());
        assertEquals("nyantaro", list.get(1).getName());
        assertEquals("monchi", list.get(2).getName());
    }

    public void findByDto_oneToManyInverse$name_EQ() throws Exception {
        ManyToOneOwnerDto dto = new ManyToOneOwnerDto();
        dto.setOneToManyInverse$name_EQ("Personnel");
        List<ManyToOneOwner> list = ownerDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("nekomaru", list.get(0).getName());
        assertEquals("nyantaro", list.get(1).getName());
        assertEquals("monchi", list.get(2).getName());
    }

    public void findByDto_oneToManyInverse$subManyToOneOwners$name_EQ()
            throws Exception {
        ManyToOneOwnerDto dto = new ManyToOneOwnerDto();
        dto.setOneToManyInverse$subManyToOneOwners$name_EQ("simagoro");
        List<ManyToOneOwner> list = ownerDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("goma", list.get(0).getName());
        assertEquals("panda", list.get(1).getName());
    }
}
