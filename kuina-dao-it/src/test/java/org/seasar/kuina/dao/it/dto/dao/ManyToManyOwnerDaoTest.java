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
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.dto.ManyToManyOwnerDto;
import org.seasar.kuina.dao.it.entity.ManyToManyOwner;

import static org.junit.Assert.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class ManyToManyOwnerDaoTest {

    private ManyToManyOwnerDao dao;

    public void findByDto_name_CONTAINS$name_EQ() throws Exception {
        ManyToManyOwnerDto dto = new ManyToManyOwnerDto();
        dto.setName_CONTAINS("m");
        dto.setManyToManyInverses$name_EQ("Personnel");
        dto.setOrderby("id");
        List<ManyToManyOwner> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("maki", list.get(0).getName());
        assertEquals("maru", list.get(1).getName());
        assertEquals("michiro", list.get(2).getName());
    }

    public void findByDtoOrderbyPaging() throws Exception {
        ManyToManyOwnerDto dto = new ManyToManyOwnerDto();
        dto.setName_CONTAINS("m");
        dto.setManyToManyInverses$name_EQ("Personnel");
        dto.setOrderby("weight DESC");
        dto.setFirstResult(1);
        dto.setMaxResults(2);
        List<ManyToManyOwner> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("maki", list.get(0).getName());
        assertEquals("maru", list.get(1).getName());
    }

}
