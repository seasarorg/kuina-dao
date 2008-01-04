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
public class ManyToManyInverseDaoTest {

    private ManyToManyInverseDao dao;

    public void findByExampleEntity() throws Exception {
        ManyToManyOwner owner = new ManyToManyOwner();
        owner.setName("maki");
        Set<ManyToManyOwner> owners = new HashSet<ManyToManyOwner>();
        owners.add(owner);
        ManyToManyInverse inverse = new ManyToManyInverse();
        inverse.setManyToManyOwners(owners);
        List<ManyToManyInverse> list = dao.findByExample(inverse);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("Business", list.get(0).getName());
        assertEquals("General Administration", list.get(1).getName());
        assertEquals("Personnel", list.get(2).getName());
    }

    public void findByExampleEmptyEntity() throws Exception {
        ManyToManyOwner owner = new ManyToManyOwner();
        Set<ManyToManyOwner> owners = new HashSet<ManyToManyOwner>();
        owners.add(owner);
        ManyToManyInverse inverse = new ManyToManyInverse();
        inverse.setManyToManyOwners(owners);
        List<ManyToManyInverse> list = dao.findByExample(inverse);
        assertNotNull(list);
        assertEquals(6, list.size());
        assertEquals("Business", list.get(0).getName());
        assertEquals("General Administration", list.get(1).getName());
        assertEquals("Personnel", list.get(2).getName());
        assertEquals("Account", list.get(3).getName());
        assertEquals("Sales", list.get(4).getName());
        assertEquals("Purchase", list.get(5).getName());
    }

    public void findByExampleCrossReference() throws Exception {
        ManyToManyOwner owner = new ManyToManyOwner();
        owner.setName("maki");
        ManyToManyInverse inverse = new ManyToManyInverse();
        owner.addManyToManyInverse(inverse);
        List<ManyToManyInverse> list = dao.findByExample(inverse);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("Business", list.get(0).getName());
        assertEquals("General Administration", list.get(1).getName());
        assertEquals("Personnel", list.get(2).getName());
    }

    public void findByExampleEntities() throws Exception {
        ManyToManyOwner owner = new ManyToManyOwner();
        owner.setName("maki");
        ManyToManyOwner owner2 = new ManyToManyOwner();
        owner2.setName("maru");
        Set<ManyToManyOwner> owners = new HashSet<ManyToManyOwner>();
        owners.add(owner);
        owners.add(owner2);
        ManyToManyInverse inverse = new ManyToManyInverse();
        inverse.setManyToManyOwners(owners);
        List<ManyToManyInverse> list = dao.findByExample(inverse);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

}
