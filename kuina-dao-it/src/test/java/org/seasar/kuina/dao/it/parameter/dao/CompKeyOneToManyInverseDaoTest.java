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
package org.seasar.kuina.dao.it.parameter.dao;

import java.util.List;

import org.junit.runner.RunWith;
import org.seasar.extension.dxo.DateUtil;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.entity.CompKeyOneToManyInverse;
import org.seasar.kuina.dao.it.entity.SalaryRate;

import static org.junit.Assert.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class CompKeyOneToManyInverseDaoTest {

    protected CompKeyOneToManyInverseDao dao;

    public void findByPk2() throws Exception {
        List<CompKeyOneToManyInverse> list =
            dao.findByPk2(DateUtil.newDate(2007, 1, 1));
        assertNotNull(list);
        assertEquals(6, list.size());
        assertEquals("Business", list.get(0).getName());
    }

    public void findByName() throws Exception {
        List<CompKeyOneToManyInverse> list = dao.findByName("Personnel");
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Personnel", list.get(0).getName());
    }

    public void findByManyToOneOwnerName() throws Exception {
        CompKeyOneToManyInverse inverse =
            dao.findByCompKeyManyToOneOwnerName("simagoro");
        assertNotNull(inverse);
        assertEquals("Business", inverse.getName());
    }

    public void findByOwnerSalaryRateOwnerWeight() throws Exception {
        List<CompKeyOneToManyInverse> list =
            dao.findByOwnerSalaryRateOwnerWeight(SalaryRate.SENIOR, 60);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Business", list.get(0).getName());
    }

}
