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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.entity.EmployeeStatus;
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;
import org.seasar.kuina.dao.it.entity.OneToManyInverse;
import org.seasar.kuina.dao.it.entity.SalaryRate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class ManyToOneOwnerDaoTest {

    private ManyToOneOwnerDao ownerDao;

    public void findByExampleString() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setName("simagoro");
        List<ManyToOneOwner> list = ownerDao.findByExample(owner);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void findByExampleDate() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setBirthday(new SimpleDateFormat("yyyy-MM-dd")
                .parse("1953-10-01"));
        List<ManyToOneOwner> list = ownerDao.findByExample(owner);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void findByExampleEntity() throws Exception {
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

    public void findByExampleEnumOrdinal() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setEmployeeStatus(EmployeeStatus.FULL_TIME);
        List<ManyToOneOwner> list = ownerDao.findByExample(owner);
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
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setSalaryRate(SalaryRate.MANAGER);
        List<ManyToOneOwner> list = ownerDao.findByExample(owner);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("maki", list.get(0).getName());
        assertEquals("nekomaru", list.get(1).getName());
        assertEquals("ma", list.get(2).getName());
    }

    public void findByExampleBoolean() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setRetired(true);
        List<ManyToOneOwner> list = ownerDao.findByExample(owner);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("gochin", list.get(0).getName());
    }

    public void findByExampleCalendar() throws Exception {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, 1998);
        date.set(Calendar.MONTH, 0);
        date.set(Calendar.DAY_OF_MONTH, 15);
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setWeddingDay(date);
        List<ManyToOneOwner> list = ownerDao.findByExample(owner);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("maki", list.get(0).getName());
    }

    public void findByExampleSqlDate() throws Exception {
        java.sql.Date date = java.sql.Date.valueOf("1984-04-01");
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setEmploymentDate(date);
        List<ManyToOneOwner> list = ownerDao.findByExample(owner);
        assertNotNull(list);
        assertEquals(5, list.size());
        assertEquals("gochin", list.get(0).getName());
        assertEquals("maki", list.get(1).getName());
        assertEquals("maru", list.get(2).getName());
        assertEquals("michiro", list.get(3).getName());
        assertEquals("coo", list.get(4).getName());
    }

    public void findByExampleSqlTime() throws Exception {
        java.sql.Time time = java.sql.Time.valueOf("08:00:00");
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setBirthTime(time);
        List<ManyToOneOwner> list = ownerDao.findByExample(owner);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("minami", list.get(0).getName());
        assertEquals("kuma", list.get(1).getName());
        assertEquals("mikel", list.get(2).getName());
    }

    public void findByExampleSqlTimestamp() throws Exception {
        java.sql.Timestamp timestamp = java.sql.Timestamp
                .valueOf("1959-09-03 08:00:00");
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setBirthTimestamp(timestamp);
        List<ManyToOneOwner> list = ownerDao.findByExample(owner);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("minami", list.get(0).getName());
    }

    public void findByExampleNumber() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setWeight(50);
        List<ManyToOneOwner> list = ownerDao.findByExample(owner);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("sara", list.get(0).getName());
        assertEquals("tasuke", list.get(1).getName());
    }

    public void findByExampleOrderby() throws Exception {
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

    public void findByExampleOrderby2() throws Exception {
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

    public void findByExamplePaging() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setBloodType("A");
        List<ManyToOneOwner> list = ownerDao.findByExamplePaging(owner, -1, -1);
        assertNotNull(list);
        assertEquals(11, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void findByExamplePaging2() throws Exception {
        ManyToOneOwner owner = new ManyToOneOwner();
        owner.setBloodType("A");
        List<ManyToOneOwner> list = ownerDao.findByExamplePaging(owner, 1, -1);
        assertNotNull(list);
        assertEquals(10, list.size());
        assertEquals("michiro", list.get(0).getName());
    }

    public void findByExamplePaging3() throws Exception {
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
