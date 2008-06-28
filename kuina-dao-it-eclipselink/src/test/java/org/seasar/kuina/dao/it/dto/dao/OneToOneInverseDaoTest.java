/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import org.seasar.kuina.dao.it.dto.OneToOneInverseDto;
import org.seasar.kuina.dao.it.entity.OneToOneInverse;

import static org.junit.Assert.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class OneToOneInverseDaoTest {

    private OneToOneInverseDao dao;

    public void findByDto_name_CONTAINS$name_EQ() throws Exception {
        OneToOneInverseDto dto = new OneToOneInverseDto();
        dto.setName_CONTAINS("a");
        dto.setOneToOneOwner$name_EQ("michiro");
        List<OneToOneInverse> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Sales", list.get(0).getName());
    }

    public void findByDtoOrderbyPaging() throws Exception {
        OneToOneInverseDto dto = new OneToOneInverseDto();
        dto.setName_CONTAINS("a");
        dto.setOrderby("name DESC");
        dto.setFirstResult(0);
        dto.setMaxResults(1);
        List<OneToOneInverse> list = dao.findByDto(dto);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Sales", list.get(0).getName());
    }

}
