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
import org.seasar.kuina.dao.it.entity.OneToOneInverse;
import org.seasar.kuina.dao.it.entity.OneToOneOwner;

import static org.junit.Assert.*;

import static org.seasar.kuina.dao.criteria.CriteriaOperations.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class OneToOneOwnerTest {

    private EntityManager em;

    public void _inner() throws Exception {
        List<OneToOneOwner> list = select("oo")
            .from(
                join(OneToOneOwner.class, "oo").inner(
                    "oo.oneToOneInverse",
                    "oi"))
            .getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    public void _innerFetch() throws Exception {
        List<OneToOneOwner> list = select("oo")
            .from(
                join(OneToOneOwner.class, "oo")
                    .innerFetch("oo.oneToOneInverse"))
            .getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    public void _left() throws Exception {
        List<OneToOneOwner> list = select("oo")
            .from(
                join(OneToOneOwner.class, "oo")
                    .left("oo.oneToOneInverse", "oi"))
            .getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    public void _leftFetch() throws Exception {
        List<OneToOneOwner> list = select("oo")
            .from(
                join(OneToOneOwner.class, "oo").leftFetch("oo.oneToOneInverse"))
            .getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    public void crossJoin() throws Exception {
        List<Object[]> list = select().from(
            OneToOneOwner.class,
            OneToOneInverse.class).orderby(
            "oneToOneOwner.id",
            "oneToOneInverse.id").getResultList(em);
        assertNotNull(list);
        assertEquals(36, list.size());
        assertEquals("simagoro", OneToOneOwner.class
            .cast(list.get(0)[0])
            .getName());
        assertEquals("Business", OneToOneInverse.class
            .cast(list.get(0)[1])
            .getName());
    }
}
