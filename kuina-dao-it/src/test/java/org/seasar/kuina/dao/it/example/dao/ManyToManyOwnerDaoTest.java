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
package org.seasar.kuina.dao.it.example.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.entity.ManyToManyInverse;
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

    public void findByExampleEntity() throws Exception {
        ManyToManyInverse inverse = new ManyToManyInverse();
        inverse.setName("Personnel");
        Set<ManyToManyInverse> inverses = new HashSet<ManyToManyInverse>();
        inverses.add(inverse);
        ManyToManyOwner owner = new ManyToManyOwner();
        owner.setManyToManyInverses(inverses);
        List<ManyToManyOwner> list = dao.findByExample(owner);
        assertNotNull(list);
        assertEquals(4, list.size());
        assertEquals("maki", list.get(0).getName());
    }

    public void findByExampleEmptyEntity() throws Exception {
        ManyToManyInverse inverse = new ManyToManyInverse();
        Set<ManyToManyInverse> inverses = new HashSet<ManyToManyInverse>();
        inverses.add(inverse);
        ManyToManyOwner owner = new ManyToManyOwner();
        owner.setManyToManyInverses(inverses);
        List<ManyToManyOwner> list = dao.findByExample(owner);
        assertNotNull(list);
        assertEquals(6, list.size());
        assertEquals("simagoro", list.get(0).getName());
        assertEquals("gochin", list.get(1).getName());
        assertEquals("maki", list.get(2).getName());
        assertEquals("maru", list.get(3).getName());
        assertEquals("michiro", list.get(4).getName());
        assertEquals("coo", list.get(5).getName());
    }

    public void findByExampleCrossReference() throws Exception {
        ManyToManyInverse inverse = new ManyToManyInverse();
        inverse.setName("Personnel");
        ManyToManyOwner owner = new ManyToManyOwner();
        owner.addManyToManyInverse(inverse);
        List<ManyToManyOwner> list = dao.findByExample(owner);
        assertNotNull(list);
        assertEquals(4, list.size());
        assertEquals("maki", list.get(0).getName());
        assertEquals("maru", list.get(1).getName());
        assertEquals("michiro", list.get(2).getName());
        assertEquals("coo", list.get(3).getName());
    }

    public void findByExampleEntities() throws Exception {
        ManyToManyInverse inverse = new ManyToManyInverse();
        inverse.setName("Business");
        ManyToManyInverse inverse2 = new ManyToManyInverse();
        inverse.setName("Personnel");
        Set<ManyToManyInverse> inverses = new HashSet<ManyToManyInverse>();
        inverses.add(inverse);
        inverses.add(inverse2);
        ManyToManyOwner owner = new ManyToManyOwner();
        owner.setManyToManyInverses(inverses);
        List<ManyToManyOwner> list = dao.findByExample(owner);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

}
