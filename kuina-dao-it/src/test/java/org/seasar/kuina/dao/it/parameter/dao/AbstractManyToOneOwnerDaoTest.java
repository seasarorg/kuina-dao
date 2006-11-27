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

import org.seasar.extension.dxo.DateUtil;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.kuina.dao.it.entity.EmployeeStatus;
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;
import org.seasar.kuina.dao.it.entity.OneToManyInverse;
import org.seasar.kuina.dao.it.entity.SalaryRate;

/**
 * 
 * @author nakamura
 */
public abstract class AbstractManyToOneOwnerDaoTest extends S2TestCase {

    private EntityManager em;

    private ManyToOneOwnerDao ownerDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("kuina-dao.dicon");
    }

    public void testFindByBloodTypeTx() throws Exception {
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

    public void testFindByNameBloodTypeTx() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByNameBloodType("simagoro",
                null);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void testFindByNameBloodType2Tx() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByNameBloodType(null, "AB");
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("maru", list.get(0).getName());
        assertEquals("rasukal", list.get(1).getName());
        assertEquals("mikel", list.get(2).getName());
    }

    public void testFindByNameBloodType3Tx() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByNameBloodType("maru", "AB");
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("maru", list.get(0).getName());
    }

    public void testFindByBloodTypeOrderbyHeightWeightTx() throws Exception {
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

    public void testFindByInverseNameTx() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByInverseName("Personnel");
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("nekomaru", list.get(0).getName());
        assertEquals("nyantaro", list.get(1).getName());
        assertEquals("monchi", list.get(2).getName());
    }

    public void testFindByBirthdayTx() throws Exception {
        Date date = DateUtil.newDate(1983, 1, 1);
        List<ManyToOneOwner> list = ownerDao.findByBirthday(date);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("mikel", list.get(0).getName());
        assertEquals("su", list.get(1).getName());
        assertEquals("miya", list.get(2).getName());
    }

    public void testFindByWeddingDayTx() throws Exception {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, 1998);
        date.set(Calendar.MONTH, 0);
        date.set(Calendar.DAY_OF_MONTH, 15);
        List<ManyToOneOwner> list = ownerDao.findByWeddingDay(date);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("maki", list.get(0).getName());
    }

    public void testFindByEmployeeDateTx() throws Exception {
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

    public void testFindByBirthTimeTx() throws Exception {
        java.sql.Time time = java.sql.Time.valueOf("08:00:00");
        List<ManyToOneOwner> list = ownerDao.findByBirthTime(time);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("minami", list.get(0).getName());
        assertEquals("kuma", list.get(1).getName());
        assertEquals("mikel", list.get(2).getName());
    }

    public void testFindByBirthTimestampTx() throws Exception {
        java.sql.Timestamp timestamp = java.sql.Timestamp
                .valueOf("1959-09-03 08:00:00");
        List<ManyToOneOwner> list = ownerDao.findByBirthTimestamp(timestamp);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("minami", list.get(0).getName());
    }

    public void testFindByNameTx() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByName("sa");
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("sara", list.get(0).getName());
        assertEquals("sary", list.get(1).getName());
        assertEquals("usa", list.get(2).getName());
    }

    public void testFindByHireFiscalYearTx() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByHireFiscalYear(true);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void testFindByEmployeeStatusTx() throws Exception {
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

    public void testFindBySalaryRateTx() throws Exception {
        List<ManyToOneOwner> list = ownerDao
                .findBySalaryRate(SalaryRate.MANAGER);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("maki", list.get(0).getName());
        assertEquals("nekomaru", list.get(1).getName());
        assertEquals("ma", list.get(2).getName());
    }

    public void testFindByOneToManyInverseTx() throws Exception {
        OneToManyInverse inverse = em.find(OneToManyInverse.class, 3);
        List<ManyToOneOwner> list = ownerDao.findByOneToManyInverse(inverse);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("nekomaru", list.get(0).getName());
        assertEquals("nyantaro", list.get(1).getName());
        assertEquals("monchi", list.get(2).getName());
    }

    public void testFindByRetiredFlagTx() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByRetiredFlag(true);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("gochin", list.get(0).getName());
    }
}
