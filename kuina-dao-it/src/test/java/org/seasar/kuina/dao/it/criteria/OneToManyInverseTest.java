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
package org.seasar.kuina.dao.it.criteria;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.unit.annotation.Prerequisite;
import org.seasar.kuina.dao.it.entity.OneToManyInverse;

import static org.junit.Assert.*;

import static org.seasar.kuina.dao.criteria.CriteriaOperations.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class OneToManyInverseTest {

    private EntityManager em;

    public void _inner() throws Exception {
        List<OneToManyInverse> list =
            selectDistinct("o").from(
                join(OneToManyInverse.class, "o").inner(
                    "o.manyToOneOwners",
                    "m")).getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    public void _innerFetch() throws Exception {
        List<OneToManyInverse> list =
            selectDistinct("o").from(
                join(OneToManyInverse.class, "o").innerFetch(
                    "o.manyToOneOwners")).getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    public void _left() throws Exception {
        List<OneToManyInverse> list =
            selectDistinct("o").from(
                join(OneToManyInverse.class, "o")
                    .left("o.manyToOneOwners", "m")).getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    public void _leftFetch() throws Exception {
        List<OneToManyInverse> list =
            selectDistinct("o").from(
                join(OneToManyInverse.class, "o")
                    .leftFetch("o.manyToOneOwners")).getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

    public void _memberOf() throws Exception {
        List<OneToManyInverse> list =
            selectDistinct("o")
                .from(
                    join(OneToManyInverse.class, "o").inner(
                        "o.manyToOneOwners",
                        "m"))
                .where(memberOf("m", "o.subManyToOneOwners"))
                .getResultList(em);
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    public void _notMemberOf() throws Exception {
        List<OneToManyInverse> list =
            selectDistinct("o")
                .from(
                    join(OneToManyInverse.class, "o").inner(
                        "o.manyToOneOwners",
                        "m"))
                .where(notMemberOf("m", "o.subManyToOneOwners"))
                .getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
    }

}
