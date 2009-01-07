/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import java.util.List;

import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.entity.OneToOneInverse;
import org.seasar.kuina.dao.it.entity.OneToOneOwner;

import static org.junit.Assert.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class OneToOneInverseDaoTest {

    private OneToOneInverseDao dao;

    public void findByExampleEntity() throws Exception {
        OneToOneOwner owner = new OneToOneOwner();
        owner.setName("maki");
        OneToOneInverse inverse = new OneToOneInverse();
        inverse.setOneToOneOwner(owner);
        List<OneToOneInverse> list = dao.findByExample(inverse);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Personnel", list.get(0).getName());
    }

    public void findByExampleEmptyEntity() throws Exception {
        OneToOneOwner owner = new OneToOneOwner();
        OneToOneInverse inverse = new OneToOneInverse();
        inverse.setOneToOneOwner(owner);
        List<OneToOneInverse> list = dao.findByExample(inverse);
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
        OneToOneOwner owner = new OneToOneOwner();
        owner.setName("maki");
        OneToOneInverse inverse = new OneToOneInverse();
        inverse.setOneToOneOwner(owner);
        owner.setOneToOneInverse(inverse);
        List<OneToOneInverse> list = dao.findByExample(inverse);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Personnel", list.get(0).getName());
    }

}
