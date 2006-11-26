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
package org.seasar.kuina.dao.it.query.parameter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.seasar.extension.dxo.DateUtil;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.kuina.dao.it.dao.ManyToOneOwnerDao;
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;

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

    public void testFindByBirthday_CalendarTx() throws Exception {
        Date date = DateUtil.newDate(1983, 1, 1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        List<ManyToOneOwner> list = ownerDao.findByBirthday(calendar);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("mikel", list.get(0).getName());
        assertEquals("su", list.get(1).getName());
        assertEquals("miya", list.get(2).getName());
    }

    public void testFindByBirthday_SqlDateTx() throws Exception {
        Date date = DateUtil.newDate(1983, 1, 1);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        List<ManyToOneOwner> list = ownerDao.findByBirthday(sqlDate);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("mikel", list.get(0).getName());
        assertEquals("su", list.get(1).getName());
        assertEquals("miya", list.get(2).getName());
    }

    public void testFindByBirthday_SqlTimeTx() throws Exception {
        Date date = DateUtil.newDate(1983, 1, 1);
        java.sql.Time time = new java.sql.Time(date.getTime());
        List<ManyToOneOwner> list = ownerDao.findByBirthday(time);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("mikel", list.get(0).getName());
        assertEquals("su", list.get(1).getName());
        assertEquals("miya", list.get(2).getName());
    }

    public void testFindByBirthday_SqlTimestampTx() throws Exception {
        Date date = DateUtil.newDate(1983, 1, 1);
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        List<ManyToOneOwner> list = ownerDao.findByBirthday(timestamp);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("mikel", list.get(0).getName());
        assertEquals("su", list.get(1).getName());
        assertEquals("miya", list.get(2).getName());
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

}
