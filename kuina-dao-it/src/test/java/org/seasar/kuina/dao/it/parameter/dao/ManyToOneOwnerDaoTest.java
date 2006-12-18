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
package org.seasar.kuina.dao.it.parameter.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.runner.RunWith;
import org.seasar.extension.dxo.DateUtil;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.entity.EmployeeStatus;
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;
import org.seasar.kuina.dao.it.entity.OneToManyInverse;
import org.seasar.kuina.dao.it.entity.SalaryRate;

import static org.junit.Assert.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class ManyToOneOwnerDaoTest {

    private EntityManager em;

    private ManyToOneOwnerDao ownerDao;

    public void findByBloodType() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByBloodType("B", "AB");
        assertNotNull(list);
        assertEquals(11, list.size());
        assertEquals("gochin", list.get(0).getName());
        assertEquals("maru", list.get(1).getName());
        assertEquals("prin", list.get(2).getName());
        assertEquals("panda", list.get(3).getName());
        assertEquals("nekomaru", list.get(4).getName());
        assertEquals("nyantaro", list.get(5).getName());
        assertEquals("rasukal", list.get(6).getName());
        assertEquals("tasuke", list.get(7).getName());
        assertEquals("tonton", list.get(8).getName());
        assertEquals("mikel", list.get(9).getName());
        assertEquals("miya", list.get(10).getName());
    }

    public void findByNameBloodType() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByNameBloodType("simagoro",
                null);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void findByNameBloodType2() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByNameBloodType(null, "AB");
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("maru", list.get(0).getName());
        assertEquals("rasukal", list.get(1).getName());
        assertEquals("mikel", list.get(2).getName());
    }

    public void findByNameBloodType3() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByNameBloodType("maru", "AB");
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("maru", list.get(0).getName());
    }

    public void findByBloodTypeOrderbyHeightWeight() throws Exception {
        List<ManyToOneOwner> list = ownerDao
                .findByBloodTypeOrderbyHeightWeight("A", new String[] {
                        "height", "weight" });
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

    public void findByBirthday() throws Exception {
        Date date = DateUtil.newDate(1983, 1, 1);
        List<ManyToOneOwner> list = ownerDao.findByBirthday(date);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("mikel", list.get(0).getName());
        assertEquals("su", list.get(1).getName());
        assertEquals("miya", list.get(2).getName());
    }

    public void findByWeddingDay() throws Exception {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, 1998);
        date.set(Calendar.MONTH, 0);
        date.set(Calendar.DAY_OF_MONTH, 15);
        List<ManyToOneOwner> list = ownerDao.findByWeddingDay(date);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("maki", list.get(0).getName());
    }

    public void findByEmployeeDate() throws Exception {
        java.sql.Date date = java.sql.Date.valueOf("1984-04-01");
        List<ManyToOneOwner> list = ownerDao.findByEmploymentDate(date);
        assertNotNull(list);
        assertEquals(5, list.size());
        assertEquals("gochin", list.get(0).getName());
        assertEquals("maki", list.get(1).getName());
        assertEquals("maru", list.get(2).getName());
        assertEquals("michiro", list.get(3).getName());
        assertEquals("coo", list.get(4).getName());
    }

    public void findByBirthTime() throws Exception {
        java.sql.Time time = java.sql.Time.valueOf("08:00:00");
        List<ManyToOneOwner> list = ownerDao.findByBirthTime(time);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("minami", list.get(0).getName());
        assertEquals("kuma", list.get(1).getName());
        assertEquals("mikel", list.get(2).getName());
    }

    public void findByBirthTimestamp() throws Exception {
        java.sql.Timestamp timestamp = java.sql.Timestamp
                .valueOf("1959-09-03 08:00:00");
        List<ManyToOneOwner> list = ownerDao.findByBirthTimestamp(timestamp);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("minami", list.get(0).getName());
    }

    public void findByName() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByName("sa");
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("sara", list.get(0).getName());
        assertEquals("sary", list.get(1).getName());
        assertEquals("usa", list.get(2).getName());
    }

    public void findByHireFiscalYear() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByHireFiscalYear(true);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void findByEmployeeStatus() throws Exception {
        List<ManyToOneOwner> list = ownerDao
                .findByEmployeeStatus(EmployeeStatus.CONTRACT);
        assertNotNull(list);
        assertEquals(10, list.size());
        assertEquals("maki", list.get(0).getName());
        assertEquals("coo", list.get(1).getName());
        assertEquals("prin", list.get(2).getName());
        assertEquals("panda", list.get(3).getName());
        assertEquals("monchi", list.get(4).getName());
        assertEquals("kuma", list.get(5).getName());
        assertEquals("tasuke", list.get(6).getName());
        assertEquals("sary", list.get(7).getName());
        assertEquals("roly", list.get(8).getName());
        assertEquals("miya", list.get(9).getName());
    }

    public void findBySalaryRate() throws Exception {
        List<ManyToOneOwner> list = ownerDao
                .findBySalaryRate(SalaryRate.MANAGER);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("maki", list.get(0).getName());
        assertEquals("nekomaru", list.get(1).getName());
        assertEquals("ma", list.get(2).getName());
    }

    public void findByRetiredFlag() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByRetiredFlag(true);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("gochin", list.get(0).getName());
    }

    public void findByOneToManyInverse() throws Exception {
        OneToManyInverse inverse = em.find(OneToManyInverse.class, 3);
        List<ManyToOneOwner> list = ownerDao.findByOneToManyInverse(inverse);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("nekomaru", list.get(0).getName());
        assertEquals("nyantaro", list.get(1).getName());
        assertEquals("monchi", list.get(2).getName());
    }

    public void findByOneToManyInverseName() throws Exception {
        List<ManyToOneOwner> list = ownerDao
                .findByOneToManyInverseName("Personnel");
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("nekomaru", list.get(0).getName());
        assertEquals("nyantaro", list.get(1).getName());
        assertEquals("monchi", list.get(2).getName());
    }

    public void findByRelationship() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByRelationship("simagoro");
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("goma", list.get(0).getName());
        assertEquals("panda", list.get(1).getName());
    }
}
