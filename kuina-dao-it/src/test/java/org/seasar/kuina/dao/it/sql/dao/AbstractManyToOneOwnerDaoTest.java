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
package org.seasar.kuina.dao.it.sql.dao;

import java.util.List;

import org.seasar.kuina.dao.it.dto.EmpDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 
 * @author nakamura
 */
public abstract class AbstractManyToOneOwnerDaoTest {

    private ManyToOneOwnerDao ownerDao;

    public void findById() throws Exception {
        EmpDto dto = ownerDao.findById(1);
        assertNotNull(dto);
        assertEquals("simagoro", dto.getName());
    }

    public void findAll() throws Exception {
        List<EmpDto> list = ownerDao.findAll();
        assertNotNull(list);
        assertEquals(30, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

}