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

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.entity.CompKeyManyToOneOwner;
import org.seasar.kuina.dao.it.entity.CompKeyManyToOneOwnerInfo;
import org.seasar.kuina.dao.it.entity.CompKeyOneToManyInverse;
import org.seasar.kuina.dao.it.entity.EmployeeStatus;
import org.seasar.kuina.dao.it.entity.SalaryRate;

import static org.junit.Assert.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class CompKeyManyToOneOwnerDaoTest {

    private CompKeyManyToOneOwnerDao dao;

    public void findByExampleString() throws Exception {
        CompKeyManyToOneOwner owner = new CompKeyManyToOneOwner();
        owner.setName("simagoro");
        List<CompKeyManyToOneOwner> list = dao.findByExample(owner);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void findByExampleDate() throws Exception {
        CompKeyManyToOneOwner owner = new CompKeyManyToOneOwner();
        CompKeyManyToOneOwnerInfo info = new CompKeyManyToOneOwnerInfo();
        info
            .setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("1953-10-01"));
        owner.setInfo(info);
        List<CompKeyManyToOneOwner> list = dao.findByExample(owner);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void findByExampleEntity() throws Exception {
        CompKeyOneToManyInverse inverse = new CompKeyOneToManyInverse();
        inverse.setName("Personnel");
        CompKeyManyToOneOwner owner = new CompKeyManyToOneOwner();
        owner.setCompKeyOneToManyInverse(inverse);
        List<CompKeyManyToOneOwner> list = dao.findByExample(owner);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("nekomaru", list.get(0).getName());
        assertEquals("nyantaro", list.get(1).getName());
        assertEquals("monchi", list.get(2).getName());
    }

    public void findByExampleEnumOrdinal() throws Exception {
        CompKeyManyToOneOwner owner = new CompKeyManyToOneOwner();
        CompKeyManyToOneOwnerInfo info = new CompKeyManyToOneOwnerInfo();
        info.setEmployeeStatus(EmployeeStatus.FULL_TIME);
        owner.setInfo(info);
        List<CompKeyManyToOneOwner> list = dao.findByExample(owner);
        assertNotNull(list);
        assertEquals(10, list.size());
        assertNotNull("simagoro", list.get(0).getName());
        assertNotNull("maru", list.get(1).getName());
        assertNotNull("sara", list.get(2).getName());
        assertNotNull("pko", list.get(3).getName());
        assertNotNull("nekomaru", list.get(4).getName());
        assertNotNull("piyo", list.get(5).getName());
        assertNotNull("gon", list.get(6).getName());
        assertNotNull("tonton", list.get(7).getName());
        assertNotNull("usa", list.get(8).getName());
        assertNotNull("mikel", list.get(9).getName());
    }

    public void findByExampleEnumString() throws Exception {
        CompKeyManyToOneOwner owner = new CompKeyManyToOneOwner();
        CompKeyManyToOneOwnerInfo info = new CompKeyManyToOneOwnerInfo();
        info.setSalaryRate(SalaryRate.MANAGER);
        owner.setInfo(info);
        List<CompKeyManyToOneOwner> list = dao.findByExample(owner);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("maki", list.get(0).getName());
        assertEquals("nekomaru", list.get(1).getName());
        assertEquals("ma", list.get(2).getName());
    }

    public void findByExampleOrderby() throws Exception {
        CompKeyManyToOneOwner owner = new CompKeyManyToOneOwner();
        CompKeyManyToOneOwnerInfo info = new CompKeyManyToOneOwnerInfo();
        info.setBloodType("AB");
        owner.setInfo(info);
        List<CompKeyManyToOneOwner> list =
            dao.findByExampleOrderby(owner, "info.birthday");
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("maru", list.get(0).getName());
        assertEquals("rasukal", list.get(1).getName());
        assertEquals("mikel", list.get(2).getName());
    }

    public void findByExampleOrderby2() throws Exception {
        CompKeyManyToOneOwner owner = new CompKeyManyToOneOwner();
        CompKeyManyToOneOwnerInfo info = new CompKeyManyToOneOwnerInfo();
        info.setBloodType("A");
        owner.setInfo(info);
        List<CompKeyManyToOneOwner> list =
            dao.findByExampleOrderby(owner, "info.height", "info.weight");
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

    public void findByExamplePaging() throws Exception {
        CompKeyManyToOneOwner owner = new CompKeyManyToOneOwner();
        CompKeyManyToOneOwnerInfo info = new CompKeyManyToOneOwnerInfo();
        info.setBloodType("A");
        owner.setInfo(info);
        List<CompKeyManyToOneOwner> list =
            dao.findByExamplePaging(owner, -1, -1);
        assertNotNull(list);
        assertEquals(11, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void findByExamplePaging2() throws Exception {
        CompKeyManyToOneOwner owner = new CompKeyManyToOneOwner();
        CompKeyManyToOneOwnerInfo info = new CompKeyManyToOneOwnerInfo();
        info.setBloodType("A");
        owner.setInfo(info);
        List<CompKeyManyToOneOwner> list =
            dao.findByExamplePaging(owner, 1, -1);
        assertNotNull(list);
        assertEquals(10, list.size());
        assertEquals("michiro", list.get(0).getName());
    }

    public void findByExamplePaging3() throws Exception {
        CompKeyManyToOneOwner owner = new CompKeyManyToOneOwner();
        CompKeyManyToOneOwnerInfo info = new CompKeyManyToOneOwnerInfo();
        info.setBloodType("A");
        owner.setInfo(info);
        List<CompKeyManyToOneOwner> list = dao.findByExamplePaging(owner, 5, 5);
        assertNotNull(list);
        assertEquals(5, list.size());
        assertEquals("piyo", list.get(0).getName());
        assertEquals("ma", list.get(1).getName());
        assertEquals("sary", list.get(2).getName());
        assertEquals("usa", list.get(3).getName());
        assertEquals("uta", list.get(4).getName());
    }
}
