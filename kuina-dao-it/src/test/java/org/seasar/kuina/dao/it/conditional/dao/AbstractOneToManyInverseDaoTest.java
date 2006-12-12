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
package org.seasar.kuina.dao.it.conditional.dao;

import java.util.List;

import org.seasar.kuina.dao.it.entity.OneToManyInverse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.seasar.kuina.dao.criteria.CriteriaOperations.eq;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.isEmpty;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.isNotEmpty;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.literal;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.size;

/**
 * 
 * @author nakamura
 */
public abstract class AbstractOneToManyInverseDaoTest {

    private OneToManyInverseDao dao;

    public void _eq() throws Exception {
        List<OneToManyInverse> list = dao.findByCondition(eq(
                "oneToManyInverse.name", literal("Account")));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("Account", list.get(0).getName());
    }

    public void _size() throws Exception {
        List<OneToManyInverse> list = dao.findByCondition(eq(
                size("oneToManyInverse.manyToOneOwners"), 3));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("Account", list.get(0).getName());
    }

    public void _isEmpty() throws Exception {
        List<OneToManyInverse> list = dao
                .findByCondition(isEmpty("oneToManyInverse.manyToOneOwners"));
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    public void _isNotEmpty() throws Exception {
        List<OneToManyInverse> list = dao
                .findByCondition(isNotEmpty("oneToManyInverse.manyToOneOwners"));
        assertNotNull(list);
        assertEquals(6, list.size());
    }

}
