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
package org.seasar.kuina.dao.it.criteria;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.entity.ManyToManyInverse;
import org.seasar.kuina.dao.it.entity.ManyToManyOwner;

import static org.junit.Assert.*;

import static org.seasar.kuina.dao.criteria.CriteriaOperations.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class ManyToManyOwnerTest {

    private EntityManager em;

    public void _inner() throws Exception {
        List<ManyToManyOwner> list = selectDistinct("mo").from(
                join(ManyToManyOwner.class, "mo").inner(
                        "mo.manyToManyInverses", "mi")).getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    public void _innerFetch() throws Exception {
        List<ManyToManyOwner> list = selectDistinct("mo").from(
                join(ManyToManyOwner.class, "mo").innerFetch(
                        "mo.manyToManyInverses")).getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    public void _left() throws Exception {
        List<ManyToManyOwner> list = selectDistinct("mo").from(
                join(ManyToManyOwner.class, "mo").left("mo.manyToManyInverses",
                        "mi")).getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    public void _leftFetch() throws Exception {
        List<ManyToManyOwner> list = selectDistinct("mo").from(
                join(ManyToManyOwner.class, "mo").leftFetch(
                        "mo.manyToManyInverses")).getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    @SuppressWarnings("unchecked")
    public void _memberOf() throws Exception {
        ManyToManyInverse inverse = em.find(ManyToManyInverse.class, 5);
        List<ManyToManyOwner> list = selectDistinct("mo").from(
                ManyToManyOwner.class, "mo").where(
                memberOf(":inverse", "mo.manyToManyInverses")).orderby("mo.id")
                .getQuery(em).setParameter("inverse", inverse).getResultList();
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("michiro", list.get(0).getName());
        assertEquals("coo", list.get(1).getName());
    }

    @SuppressWarnings("unchecked")
    public void _notMemberOf() throws Exception {
        ManyToManyInverse inverse = em.find(ManyToManyInverse.class, 5);
        List<ManyToManyOwner> list = selectDistinct("mo").from(
                ManyToManyOwner.class, "mo").where(
                notMemberOf(":inverse", "mo.manyToManyInverses")).orderby(
                "mo.id").getQuery(em).setParameter("inverse", inverse)
                .getResultList();
        assertNotNull(list);
        assertEquals(4, list.size());
        assertEquals("simagoro", list.get(0).getName());
        assertEquals("gochin", list.get(1).getName());
        assertEquals("maki", list.get(2).getName());
        assertEquals("maru", list.get(3).getName());
    }

    public void crossJoin() throws Exception {
        List<Object[]> list = select().from(ManyToManyOwner.class,
                ManyToManyInverse.class).orderby("manyToManyOwner.id",
                "manyToManyInverse.id").getResultList(em);
        assertNotNull(list);
        assertEquals(36, list.size());
        assertEquals("simagoro", ManyToManyOwner.class.cast(list.get(0)[0])
                .getName());
        assertEquals("Business", ManyToManyInverse.class.cast(list.get(0)[1])
                .getName());
    }
}
