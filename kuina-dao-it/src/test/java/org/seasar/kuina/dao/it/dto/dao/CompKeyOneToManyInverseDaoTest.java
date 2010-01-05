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

import org.junit.runner.RunWith;
import org.seasar.extension.dxo.DateUtil;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.dto.CompKeyOneToManyInverseDto;
import org.seasar.kuina.dao.it.entity.CompKeyOneToManyInverse;

import static org.junit.Assert.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class CompKeyOneToManyInverseDaoTest {

    private CompKeyOneToManyInverseDao dao;

    public void findByDto_name_EQ() throws Exception {
        CompKeyOneToManyInverseDto dto = new CompKeyOneToManyInverseDto();
        dto.setName_EQ("Account");
        List<CompKeyOneToManyInverse> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Account", list.get(0).getName());
    }

    public void findByDto_name_CONTAINS_$$weight_GT() throws Exception {
        CompKeyOneToManyInverseDto dto = new CompKeyOneToManyInverseDto();
        dto.setName_CONTAINS("Account");
        dto.setCompKeyManyToOneOwners$info$weight_GT(100);
        List<CompKeyOneToManyInverse> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Account", list.get(0).getName());
    }

    public void findByDto_$$bloodType_EQ_$$weight_GT() throws Exception {
        CompKeyOneToManyInverseDto dto = new CompKeyOneToManyInverseDto();
        dto.setCompKeyManyToOneOwners$id$pk2_EQ(DateUtil.newDate(2007, 1, 1));
        dto.setCompKeyManyToOneOwners$info$bloodType_EQ("A");
        dto.setCompKeyManyToOneOwners$info$weight_GT(60);
        dto.setOrderby("id.pk1");
        List<CompKeyOneToManyInverse> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("Business", list.get(0).getName());
        assertEquals("Account", list.get(1).getName());
        assertEquals("Purchase", list.get(2).getName());
    }

    public void findByDtoOrderbyPaging() throws Exception {
        CompKeyOneToManyInverseDto dto = new CompKeyOneToManyInverseDto();
        dto.setName_CONTAINS("s");
        dto.setFirstResult(2);
        dto.setMaxResults(3);
        dto.setOrderby("name");
        List<CompKeyOneToManyInverse> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("Personnel", list.get(0).getName());
        assertEquals("Purchase", list.get(1).getName());
        assertEquals("Sales", list.get(2).getName());
    }
}
