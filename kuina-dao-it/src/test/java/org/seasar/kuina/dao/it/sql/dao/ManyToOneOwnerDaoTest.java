/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;

import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.dto.EmpDto;
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

    public void findById() throws Exception {
        ManyToOneOwner owner = em.find(ManyToOneOwner.class, 1);
        owner.setName("hoge");
        EmpDto dto = dao.findById(1);
        assertNotNull(dto);
        assertEquals("hoge", dto.getName());
    }

    public void findByIdWithEntityManagerCommitFlushMode() throws Exception {
        em.setFlushMode(FlushModeType.COMMIT);
        ManyToOneOwner owner = em.find(ManyToOneOwner.class, 1);
        owner.setName("hoge");
        EmpDto dto = dao.findById(1);
        assertNotNull(dto);
        assertEquals("simagoro", dto.getName());
    }

    public void findByIdWithAutoFlushMode() throws Exception {
        ManyToOneOwner owner = em.find(ManyToOneOwner.class, 1);
        owner.setName("hoge");
        EmpDto dto = dao.findByIdWithAutoFlushMode(1);
        assertNotNull(dto);
        assertEquals("hoge", dto.getName());
    }

    public void findByIdWithCommitFlushMode() throws Exception {
        ManyToOneOwner owner = em.find(ManyToOneOwner.class, 1);
        owner.setName("hoge");
        EmpDto dto = dao.findByIdWithCommitFlushMode(1);
        assertNotNull(dto);
        assertEquals("simagoro", dto.getName());
    }

    public void findAll() throws Exception {
        List<EmpDto> list = dao.findAll();
        assertNotNull(list);
        assertEquals(30, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void findAllAsMap() throws Exception {
        List<Map<String, Object>> list = dao.findAllAsMap();
        assertNotNull(list);
        assertEquals(30, list.size());
        assertEquals("simagoro", list.get(0).get("name"));
    }

    public void deleteAll() throws Exception {
        int affected = dao.deleteAll();
        assertEquals(30, affected);
        assertNull(em.find(ManyToOneOwner.class, 1));
    }

}