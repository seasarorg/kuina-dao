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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.entity.CompKeyManyToOneOwner;
import org.seasar.kuina.dao.it.entity.CompKeyManyToOneOwnerInfo;
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

    public void findByExampleEntity() throws Exception {
        CompKeyManyToOneOwner owner = new CompKeyManyToOneOwner();
        CompKeyManyToOneOwnerInfo info = new CompKeyManyToOneOwnerInfo();
        info.setBloodType("A");
        owner.setInfo(info);
        owner.setName("simagoro");
        Set<CompKeyManyToOneOwner> owners =
            new HashSet<CompKeyManyToOneOwner>();
        owners.add(owner);
        CompKeyOneToManyInverse inverse = new CompKeyOneToManyInverse();
        inverse.setCompKeyManyToOneOwners(owners);
        List<CompKeyOneToManyInverse> list = dao.findByExample(inverse);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Business", list.get(0).getName());
    }

    public void findByExampleEmptyEntity() throws Exception {
        CompKeyManyToOneOwner owner = new CompKeyManyToOneOwner();
        Set<CompKeyManyToOneOwner> owners =
            new HashSet<CompKeyManyToOneOwner>();
        owners.add(owner);
        CompKeyOneToManyInverse inverse = new CompKeyOneToManyInverse();
        inverse.setCompKeyManyToOneOwners(owners);
        List<CompKeyOneToManyInverse> list = dao.findByExample(inverse);
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
        CompKeyManyToOneOwner owner = new CompKeyManyToOneOwner();
        owner.setName("simagoro");
        CompKeyOneToManyInverse inverse = new CompKeyOneToManyInverse();
        inverse.addCompKeyManyToOneOwner(owner);
        List<CompKeyOneToManyInverse> list = dao.findByExample(inverse);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Business", list.get(0).getName());
    }

    public void findByExampleEntities() throws Exception {
        CompKeyManyToOneOwner owner = new CompKeyManyToOneOwner();
        owner.setName("simagoro");
        CompKeyManyToOneOwner owner2 = new CompKeyManyToOneOwner();
        owner2.setName("miya");
        List<CompKeyManyToOneOwner> owners =
            new ArrayList<CompKeyManyToOneOwner>();
        owners.add(owner);
        owners.add(owner2);
        CompKeyOneToManyInverse inverse = new CompKeyOneToManyInverse();
        inverse.setCompKeyManyToOneOwners(owners);
        List<CompKeyOneToManyInverse> list = dao.findByExample(inverse);
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
        CompKeyOneToManyInverse inverse = new CompKeyOneToManyInverse();
        List<CompKeyOneToManyInverse> list =
            dao.findByExampleOrderby(inverse, "name");
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
        CompKeyOneToManyInverse inverse = new CompKeyOneToManyInverse();
        List<CompKeyOneToManyInverse> list =
            dao.findByExamplePaging(inverse, 3, 2);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("Account", list.get(0).getName());
        assertEquals("Sales", list.get(1).getName());
    }
}
