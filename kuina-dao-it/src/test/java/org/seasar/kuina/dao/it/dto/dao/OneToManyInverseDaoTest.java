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

import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.dto.OneToManyInverseDto;
import org.seasar.kuina.dao.it.entity.OneToManyInverse;

import static org.junit.Assert.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class OneToManyInverseDaoTest {

    private OneToManyInverseDao dao;

    public void findByDto_name_EQ() throws Exception {
        OneToManyInverseDto dto = new OneToManyInverseDto();
        dto.setName_EQ("Account");
        List<OneToManyInverse> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Account", list.get(0).getName());
    }

    public void findByDto_name_CONTAINS_$weight_GT() throws Exception {
        OneToManyInverseDto dto = new OneToManyInverseDto();
        dto.setName_CONTAINS("Account");
        dto.setManyToOneOwners$weight_GT(100);
        List<OneToManyInverse> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Account", list.get(0).getName());
    }

    public void findByDto_$bloodType_EQ_$weight_GT() throws Exception {
        OneToManyInverseDto dto = new OneToManyInverseDto();
        dto.setManyToOneOwners$bloodType_EQ("A");
        dto.setManyToOneOwners$weight_GT(60);
        List<OneToManyInverse> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("Business", list.get(0).getName());
        assertEquals("Account", list.get(1).getName());
        assertEquals("Purchase", list.get(2).getName());
    }

    public void findByDto_$weight_GT_$$name_CONTAINS() throws Exception {
        OneToManyInverseDto dto = new OneToManyInverseDto();
        dto.setManyToOneOwners$weight_GT(70);
        dto.setManyToOneOwners$subOneToManyInverse$name_CONTAINS("c");
        List<OneToManyInverse> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Personnel", list.get(0).getName());
    }

    public void findByDto_$$id_EQ_$$name_CONTAINS() throws Exception {
        OneToManyInverseDto dto = new OneToManyInverseDto();
        dto.setManyToOneOwners$subOneToManyInverse$id_EQ(4);
        dto.setManyToOneOwners$subOneToManyInverse$name_CONTAINS("count");
        List<OneToManyInverse> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Personnel", list.get(0).getName());
    }

    public void findByDtoOrderbyPaging() throws Exception {
        OneToManyInverseDto dto = new OneToManyInverseDto();
        dto.setName_CONTAINS("s");
        dto.setFirstResult(2);
        dto.setMaxResults(3);
        dto.setOrderby("name");
        List<OneToManyInverse> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("Personnel", list.get(0).getName());
        assertEquals("Purchase", list.get(1).getName());
        assertEquals("Sales", list.get(2).getName());
    }
}
