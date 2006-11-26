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
package org.seasar.kuina.dao.it.query.example;

import java.text.SimpleDateFormat;
import java.util.List;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.kuina.dao.it.dao.ManyToOneOwnerDao;
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;
import org.seasar.kuina.dao.it.entity.OneToManyInverse;
import org.seasar.kuina.dao.it.entity.SalaryRate;

/**
 * 
 * @author nakamura
 */
public abstract class AbstractManyToOneOwnerTest extends S2TestCase {

    private ManyToOneOwnerDao ownerDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("kuina-dao.dicon");
    }

    public void testFindByExampleTx() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setName("simagoro");
        List<ManyToOneOwner> list = ownerDao.findByExample(owner);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void testFindByExample2Tx() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setBirthday(new SimpleDateFormat("yyyy-MM-dd")
                .parse("1953-10-01"));
        List<ManyToOneOwner> list = ownerDao.findByExample(owner);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void testFindByExample3Tx() throws Exception {
        OneToManyInverse dept = new OneToManyInverse();
        dept.setName("Personnel");
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setOneToManyInverse(dept);
        List<ManyToOneOwner> list = ownerDao.findByExample(owner);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("nekomaru", list.get(0).getName());
        assertEquals("nyantaro", list.get(1).getName());
        assertEquals("monchi", list.get(2).getName());
    }

    public void testFindByExample4Tx() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setSalaryRate(SalaryRate.MANAGER);
        List<ManyToOneOwner> list = ownerDao.findByExample(owner);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("maki", list.get(0).getName());
        assertEquals("nekomaru", list.get(1).getName());
        assertEquals("ma", list.get(2).getName());
    }

    public void testFindByExample5Tx() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setRetired(true);
        List<ManyToOneOwner> list = ownerDao.findByExample(owner);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("gochin", list.get(0).getName());
    }

    public void testFindByExample6Tx() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setWeight(50);
        List<ManyToOneOwner> list = ownerDao.findByExample(owner);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("sara", list.get(0).getName());
        assertEquals("tasuke", list.get(1).getName());
    }

    public void testFindByExampleOrderbyTx() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setBloodType("AB");
        List<ManyToOneOwner> list = ownerDao.findByExampleOrderby(owner,
                new String[] { "birthday" });
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("maru", list.get(0).getName());
        assertEquals("rasukal", list.get(1).getName());
        assertEquals("mikel", list.get(2).getName());
    }

    public void testFindByExampleOrderby2Tx() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setBloodType("A");
        List<ManyToOneOwner> list = ownerDao.findByExampleOrderby(owner,
                new String[] { "height", "weight" });
        assertNotNull(list);
        assertEquals(11, list.size());
        assertEquals("roly", list.get(0).getName());
        assertEquals("sary", list.get(1).getName());
        assertEquals("piyo", list.get(2).getName());
        assertEquals("ma", list.get(3).getName());
        assertEquals("usa", list.get(4).getName());
        assertEquals("sara", list.get(5).getName());
        assertEquals("simagoro", list.get(6).getName());
        assertEquals("monchi", list.get(7).getName());
        assertEquals("uta", list.get(8).getName());
        assertEquals("michiro", list.get(9).getName());
        assertEquals("coo", list.get(10).getName());
    }

    public void testFindByExamplePagingTx() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setBloodType("A");
        List<ManyToOneOwner> list = ownerDao.findByExamplePaging(owner, -1, -1);
        assertNotNull(list);
        assertEquals(11, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void testFindByExamplePaging2Tx() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setBloodType("A");
        List<ManyToOneOwner> list = ownerDao.findByExamplePaging(owner, 1, -1);
        assertNotNull(list);
        assertEquals(10, list.size());
        assertEquals("michiro", list.get(0).getName());
    }

    public void testFindByExamplePaging3Tx() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setBloodType("A");
        List<ManyToOneOwner> list = ownerDao.findByExamplePaging(owner, 5, 5);
        assertNotNull(list);
        assertEquals(5, list.size());
        assertEquals("piyo", list.get(0).getName());
        assertEquals("ma", list.get(1).getName());
        assertEquals("sary", list.get(2).getName());
        assertEquals("usa", list.get(3).getName());
        assertEquals("uta", list.get(4).getName());
    }
}
