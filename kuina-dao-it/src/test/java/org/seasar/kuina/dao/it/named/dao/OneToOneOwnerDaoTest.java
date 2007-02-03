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
package org.seasar.kuina.dao.it.named.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.entity.OneToOneOwner;

import static org.junit.Assert.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class OneToOneOwnerDaoTest {

    private EntityManager em;

    private OneToOneOwnerDao dao;

    public void updateNameById() throws Exception {
        dao.updateNameById(1, "aaa");
        OneToOneOwner owner = em.find(OneToOneOwner.class, 1);
        assertEquals("aaa", owner.getName());
    }

    public void findByName() throws Exception {
        List<OneToOneOwner> list = dao.findByName("%hi%");
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("gochin", list.get(0).getName());
        assertEquals("michiro", list.get(1).getName());
    }

}