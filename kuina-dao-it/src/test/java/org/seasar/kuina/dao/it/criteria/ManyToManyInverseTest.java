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
public class ManyToManyInverseTest {

    private EntityManager em;

    public void _inner() throws Exception {
        List<ManyToManyInverse> list = selectDistinct("mi").from(
                join(ManyToManyInverse.class, "mi").inner(
                        "mi.manyToManyOwners", "mo")).getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    public void _innerFetch() throws Exception {
        List<ManyToManyInverse> list = selectDistinct("mi").from(
                join(ManyToManyInverse.class, "mi").innerFetch(
                        "mi.manyToManyOwners")).getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    public void _left() throws Exception {
        List<ManyToManyInverse> list = selectDistinct("mi").from(
                join(ManyToManyInverse.class, "mi").left("mi.manyToManyOwners",
                        "mo")).getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    public void _leftFetch() throws Exception {
        List<ManyToManyInverse> list = selectDistinct("mi").from(
                join(ManyToManyInverse.class, "mi").leftFetch(
                        "mi.manyToManyOwners")).getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    @SuppressWarnings("unchecked")
    public void _memberOf() throws Exception {
        ManyToManyOwner owner = em.find(ManyToManyOwner.class, 3);
        List<ManyToManyInverse> list = selectDistinct("mi").from(
                ManyToManyInverse.class, "mi").where(
                memberOf(":owner", "mi.manyToManyOwners")).orderby("mi.id")
                .getQuery(em).setParameter("owner", owner).getResultList();
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("Business", list.get(0).getName());
        assertEquals("General Administration", list.get(1).getName());
        assertEquals("Personnel", list.get(2).getName());
    }

    @SuppressWarnings("unchecked")
    public void _notMemberOf() throws Exception {
        ManyToManyOwner owner = em.find(ManyToManyOwner.class, 3);
        List<ManyToManyInverse> list = selectDistinct("mi").from(
                ManyToManyInverse.class, "mi").where(
                notMemberOf(":owner", "mi.manyToManyOwners")).orderby("mi.id")
                .getQuery(em).setParameter("owner", owner).getResultList();
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("Account", list.get(0).getName());
        assertEquals("Sales", list.get(1).getName());
        assertEquals("Purchase", list.get(2).getName());
    }

    public void crossJoin() throws Exception {
        List<Object[]> list = select().from(ManyToManyInverse.class,
                ManyToManyOwner.class).orderby("manyToManyOwner.id",
                "manyToManyInverse.id").getResultList(em);
        assertNotNull(list);
        assertEquals(36, list.size());
        assertEquals("Business", ManyToManyInverse.class.cast(list.get(0)[0])
                .getName());
        assertEquals("simagoro", ManyToManyOwner.class.cast(list.get(0)[1])
                .getName());
    }
}
