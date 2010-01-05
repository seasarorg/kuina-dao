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
package org.seasar.kuina.dao.it.dto.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.runner.RunWith;
import org.seasar.extension.dxo.DateUtil;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.dto.CompKeyManyToOneOwnerDto;
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
public class CompKeyManyToOneOwnerDaoTest {

    private EntityManager em;

    private CompKeyManyToOneOwnerDao dao;

    public void findByDto_name_GT_name_LT() throws Exception {
        CompKeyManyToOneOwnerDto dto = new CompKeyManyToOneOwnerDto();
        dto.setName_GT("ma");
        dto.setName_LT("michiro");
        dto.setOrderby("id.pk1");
        List<CompKeyManyToOneOwner> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("maki", list.get(0).getName());
        assertEquals("maru", list.get(1).getName());
    }

    public void findByDto_info$birthday_GT() throws Exception {
        CompKeyManyToOneOwnerDto dto = new CompKeyManyToOneOwnerDto();
        dto.setInfo$birthday_GT(DateUtil.newDate(1985, 12, 1));
        List<CompKeyManyToOneOwner> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("miya", list.get(0).getName());
    }

    public void findByDtoMultiCondition() throws Exception {
        CompKeyManyToOneOwnerDto dto = new CompKeyManyToOneOwnerDto();
        dto.setName_STARTS("m");
        dto.setFirstResult(4);
        dto.setMaxResults(3);
        dto.setOrderby("info.height DESC", "info.weight");
        List<CompKeyManyToOneOwner> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("ma", list.get(0).getName());
        assertEquals("maru", list.get(1).getName());
        assertEquals("maki", list.get(2).getName());
    }

    public void findByDto_compKeyOneToManyInverse_EQ() throws Exception {
        CompKeyOneToManyInverseId id =
            new CompKeyOneToManyInverseId(3, DateUtil.newDate(2007, 1, 1));
        CompKeyOneToManyInverse compKeyOneToManyInverse =
            em.find(CompKeyOneToManyInverse.class, id);
        CompKeyManyToOneOwnerDto dto = new CompKeyManyToOneOwnerDto();
        dto.setCompKeyOneToManyInverse_EQ(compKeyOneToManyInverse);
        dto.setOrderby("id.pk1");
        List<CompKeyManyToOneOwner> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("nekomaru", list.get(0).getName());
        assertEquals("nyantaro", list.get(1).getName());
        assertEquals("monchi", list.get(2).getName());
    }

    public void findByDto_oneToManyInverse$name_EQ() throws Exception {
        CompKeyManyToOneOwnerDto dto = new CompKeyManyToOneOwnerDto();
        dto.setCompKeyOneToManyInverse$name_EQ("Personnel");
        dto.setOrderby("id.pk1");
        List<CompKeyManyToOneOwner> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("nekomaru", list.get(0).getName());
        assertEquals("nyantaro", list.get(1).getName());
        assertEquals("monchi", list.get(2).getName());
    }

}
