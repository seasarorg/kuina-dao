/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.kuina.dao.it.conditional.dao;

import java.util.List;

import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.entity.ManyToManyInverse;

import static org.junit.Assert.*;

import static org.seasar.kuina.dao.criteria.CriteriaOperations.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class ManyToManyInverseDaoTest {

    private ManyToManyInverseDao dao;

    public void _eq() throws Exception {
        List<ManyToManyInverse> list = dao.findByCondition(eq(
                "manyToManyInverse.name", "Personnel"));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("Personnel", list.get(0).getName());
    }

    public void _size() throws Exception {
        List<ManyToManyInverse> list = dao.findByCondition(eq(
                size("manyToManyInverse.manyToManyOwners"), 4));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("Personnel", list.get(0).getName());
    }

    public void _isEmpty() throws Exception {
        List<ManyToManyInverse> list = dao
                .findByCondition(isEmpty("manyToManyInverse.manyToManyOwners"));
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    public void _isNotEmpty() throws Exception {
        List<ManyToManyInverse> list = dao
                .findByCondition(isNotEmpty("manyToManyInverse.manyToManyOwners"));
        assertNotNull(list);
        assertEquals(6, list.size());
    }

}
