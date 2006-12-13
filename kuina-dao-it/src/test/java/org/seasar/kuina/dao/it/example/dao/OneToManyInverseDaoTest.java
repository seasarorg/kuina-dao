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
package org.seasar.kuina.dao.it.example.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;
import org.seasar.kuina.dao.it.entity.OneToManyInverse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
public class OneToManyInverseDaoTest {

    private OneToManyInverseDao dao;

    public void findByExampleEntity() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setName("simagoro");
        Set<ManyToOneOwner> owners = new HashSet<ManyToOneOwner>();
        owners.add(owner);
        OneToManyInverse inverse = new OneToManyInverse();
        inverse.setManyToOneOwners(owners);
        List<OneToManyInverse> list = dao.findByExample(inverse);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Business", list.get(0).getName());
    }

    public void findByExampleEmptyEntity() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        Set<ManyToOneOwner> owners = new HashSet<ManyToOneOwner>();
        owners.add(owner);
        OneToManyInverse inverse = new OneToManyInverse();
        inverse.setManyToOneOwners(owners);
        List<OneToManyInverse> list = dao.findByExample(inverse);
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
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setName("simagoro");
        OneToManyInverse inverse = new OneToManyInverse();
        inverse.addManyToOneOwner(owner);
        List<OneToManyInverse> list = dao.findByExample(inverse);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Business", list.get(0).getName());
    }

    public void findByExampleCrossReference2() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setBloodType("A");
        ManyToOneOwner subOwner = new ManyToOneOwner();
        subOwner.setBloodType("AB");
        OneToManyInverse example = new OneToManyInverse();
        example.addManyToOneOwner(owner);
        example.addSubManyToOneOwner(subOwner);
        List<OneToManyInverse> list = dao.findByExample(example);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("Business", list.get(0).getName());
        assertEquals("Sales", list.get(1).getName());
    }

    public void findByExampleCrossReference3() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setBloodType("A");
        ;
        OneToManyInverse subInverse = new OneToManyInverse();
        subInverse.setName("Sales");
        subInverse.addSubManyToOneOwner(owner);
        OneToManyInverse example = new OneToManyInverse();
        example.addManyToOneOwner(owner);
        List<OneToManyInverse> list = dao.findByExample(example);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Account", list.get(0).getName());
    }

    public void findByExampleEntities() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setName("simagoro");
        ManyToOneOwner owner2 = new ManyToOneOwner();
        owner2.setName("miya");
        List<ManyToOneOwner> owners = new ArrayList<ManyToOneOwner>();
        owners.add(owner);
        owners.add(owner2);
        OneToManyInverse inverse = new OneToManyInverse();
        inverse.setManyToOneOwners(owners);
        List<OneToManyInverse> list = dao.findByExample(inverse);
        assertNotNull(list);
        assertEquals(6, list.size());
        assertEquals("Business", list.get(0).getName());
        assertEquals("General Administration", list.get(1).getName());
        assertEquals("Personnel", list.get(2).getName());
        assertEquals("Account", list.get(3).getName());
        assertEquals("Sales", list.get(4).getName());
        assertEquals("Purchase", list.get(5).getName());
    }

    public void findByExampleOrderby() throws Exception {
        OneToManyInverse inverse = new OneToManyInverse();
        List<OneToManyInverse> list = dao.findByExampleOrderby(inverse, "name");
        assertNotNull(list);
        assertEquals(6, list.size());
        assertEquals("Account", list.get(0).getName());
        assertEquals("Business", list.get(1).getName());
        assertEquals("General Administration", list.get(2).getName());
        assertEquals("Personnel", list.get(3).getName());
        assertEquals("Purchase", list.get(4).getName());
        assertEquals("Sales", list.get(5).getName());
    }

    public void findByExamplePaging() throws Exception {
        OneToManyInverse inverse = new OneToManyInverse();
        List<OneToManyInverse> list = dao.findByExamplePaging(inverse, 3, 2);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("Account", list.get(0).getName());
        assertEquals("Sales", list.get(1).getName());
    }
}
