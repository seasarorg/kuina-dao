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
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;
import org.seasar.kuina.dao.it.entity.OneToManyInverse;

import static org.junit.Assert.*;

import static org.seasar.kuina.dao.criteria.CriteriaOperations.*;

/**
 * 
 * @author nakamura
 */
@RunWith(Seasar2.class)
@Prerequisite("@org.seasar.kuina.dao.it.KuinaDaoItUtil@shouldRun(#method)")
public class ManyToOneOwnerTest {

    private EntityManager em;

    public void fromOnly() throws Exception {
        List<ManyToOneOwner> list = select().from(ManyToOneOwner.class)
                .orderby("manyToOneOwner.id").getResultList(em);
        assertNotNull(list);
        assertEquals(30, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void selectIdentificationVariable() throws Exception {
        List<ManyToOneOwner> list = select("manyToOneOwner").from(
                ManyToOneOwner.class).orderby("manyToOneOwner.id")
                .getResultList(em);
        assertNotNull(list);
        assertEquals(30, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void selectSpecifiedStates() throws Exception {
        List<Object[]> list = select("m.id", "m.name").from(
                ManyToOneOwner.class, "m").orderby("m.id").getResultList(em);
        assertNotNull(list);
        assertEquals(30, list.size());
        assertEquals(new Integer(1), list.get(0)[0]);
        assertEquals("simagoro", list.get(0)[1]);
    }

    public void crossJoin() throws Exception {
        List<Object[]> list = select().from(ManyToOneOwner.class,
                OneToManyInverse.class).orderby("manyToOneOwner.id",
                "oneToManyInverse.id").getResultList(em);
        assertNotNull(list);
        assertEquals(180, list.size());
        assertEquals("simagoro", ManyToOneOwner.class.cast(list.get(0)[0])
                .getName());
        assertEquals("Business", OneToManyInverse.class.cast(list.get(0)[1])
                .getName());
        assertEquals("simagoro", ManyToOneOwner.class.cast(list.get(1)[0])
                .getName());
        assertEquals("General Administration", OneToManyInverse.class.cast(
                list.get(1)[1]).getName());
        assertEquals("simagoro", ManyToOneOwner.class.cast(list.get(2)[0])
                .getName());
        assertEquals("Personnel", OneToManyInverse.class.cast(list.get(2)[1])
                .getName());
        assertEquals("simagoro", ManyToOneOwner.class.cast(list.get(3)[0])
                .getName());
        assertEquals("Account", OneToManyInverse.class.cast(list.get(3)[1])
                .getName());
        assertEquals("simagoro", ManyToOneOwner.class.cast(list.get(4)[0])
                .getName());
        assertEquals("Sales", OneToManyInverse.class.cast(list.get(4)[1])
                .getName());
        assertEquals("simagoro", ManyToOneOwner.class.cast(list.get(5)[0])
                .getName());
        assertEquals("Purchase", OneToManyInverse.class.cast(list.get(5)[1])
                .getName());
    }

    public void selectDistinctAndCrossJoinWithAlias() throws Exception {
        List<ManyToOneOwner> list = selectDistinct("m").from(
                alias(ManyToOneOwner.class, "m"),
                alias(OneToManyInverse.class, "o")).orderby("m.id")
                .getResultList(em);
        assertNotNull(list);
        assertEquals(30, list.size());
        assertEquals("simagoro", list.get(0).getName());
        assertEquals("gochin", list.get(1).getName());
    }

    public void _inner() throws Exception {
        List<ManyToOneOwner> list = select("m").from(
                join(ManyToOneOwner.class, "m")
                        .inner("m.oneToManyInverse", "o")).orderby("m.id")
                .getResultList(em);
        assertNotNull(list);
        assertEquals(30, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void _innerFetch() throws Exception {
        List<ManyToOneOwner> list = select("m").from(
                join(ManyToOneOwner.class, "m")
                        .innerFetch("m.oneToManyInverse")).orderby("m.id")
                .getResultList(em);
        assertNotNull(list);
        assertEquals(30, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void _left() throws Exception {
        List<ManyToOneOwner> list = select("m")
                .from(
                        join(ManyToOneOwner.class, "m").left(
                                "m.oneToManyInverse", "o")).orderby("m.id",
                        "o.id").getResultList(em);
        assertNotNull(list);
        assertEquals(30, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void _leftFetch() throws Exception {
        List<ManyToOneOwner> list = select()
                .from(
                        join(ManyToOneOwner.class, "m").leftFetch(
                                "m.oneToManyInverse")).orderby("m.id")
                .getResultList(em);
        assertNotNull(list);
        assertEquals(30, list.size());
        assertEquals("simagoro", list.get(0).getName());
    }

    public void _memberOf() throws Exception {
        List<ManyToOneOwner> list = select("m").from(
                join(ManyToOneOwner.class, "m")
                        .inner("m.oneToManyInverse", "o")).where(
                eq("o.name", literal("Personnel")),
                memberOf("m", "o.subManyToOneOwners")).orderby("m.id")
                .getResultList(em);
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    public void _notMemberOf() throws Exception {
        List<ManyToOneOwner> list = select("m").from(
                join(ManyToOneOwner.class, "m")
                        .inner("m.oneToManyInverse", "o")).where(
                eq("o.name", literal("Personnel")),
                notMemberOf("m", "o.subManyToOneOwners")).orderby("m.id")
                .getResultList(em);
        assertNotNull(list);
        assertEquals(3, list.size());
    }

    public void _count() throws Exception {
        List<Object[]> list = select(count("e"))
                .from(ManyToOneOwner.class, "e").getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(new Long(30), list.get(0));
    }

    public void _countDistinct() throws Exception {
        List<Object[]> list = select(countDistinct("e.weight")).from(
                ManyToOneOwner.class, "e").getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(new Long(24), list.get(0));
    }

    public void _max() throws Exception {
        List<Object[]> list = select(max("e.weight")).from(
                ManyToOneOwner.class, "e").getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(new Integer(115), list.get(0));
    }

    public void _maxDistinct() throws Exception {
        List<Object[]> list = select(maxDistinct("e.weight")).from(
                ManyToOneOwner.class, "e").getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(new Integer(115), list.get(0));
    }

    public void _min() throws Exception {
        List<Object[]> list = select(min("e.weight")).from(
                ManyToOneOwner.class, "e").getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(new Integer(38), list.get(0));
    }

    public void _minDistinct() throws Exception {
        List<Object[]> list = select(minDistinct("e.weight")).from(
                ManyToOneOwner.class, "e").getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(new Integer(38), list.get(0));
    }

    public void _avg() throws Exception {
        List<Object[]> list = select(avg("e.weight")).from(
                ManyToOneOwner.class, "e").getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(new Double(59), list.get(0));
    }

    public void _avgDistinct() throws Exception {
        List<Object[]> list = select(avgDistinct("e.weight")).from(
                ManyToOneOwner.class, "e").getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(new Double(60), list.get(0));
    }

    public void _sum() throws Exception {
        List<Object[]> list = select(sum("e.weight")).from(
                ManyToOneOwner.class, "e").getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(new Long(1770), list.get(0));
    }

    public void _sumDistinct() throws Exception {
        List<Object[]> list = select(sumDistinct("e.weight")).from(
                ManyToOneOwner.class, "e").getResultList(em);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(new Long(1442), list.get(0));
    }

    public void _groupby() throws Exception {
        List<Object[]> list = select("o.id", count("m")).from(
                join(ManyToOneOwner.class, "m")
                        .inner("m.oneToManyInverse", "o")).groupby("o.id")
                .orderby("o.id").getResultList(em);
        assertNotNull(list);
        assertEquals(6, list.size());
        assertEquals(new Integer(1), list.get(0)[0]);
        assertEquals(new Long(10), list.get(0)[1]);
        assertEquals(new Integer(2), list.get(1)[0]);
        assertEquals(new Long(2), list.get(1)[1]);
        assertEquals(new Integer(3), list.get(2)[0]);
        assertEquals(new Long(3), list.get(2)[1]);
        assertEquals(new Integer(4), list.get(3)[0]);
        assertEquals(new Long(4), list.get(3)[1]);
        assertEquals(new Integer(5), list.get(4)[0]);
        assertEquals(new Long(5), list.get(4)[1]);
        assertEquals(new Integer(6), list.get(5)[0]);
        assertEquals(new Long(6), list.get(5)[1]);
    }

    public void havingSingleCondition() throws Exception {
        List<Object[]> list = select("m.weight", count("m")).from(
                ManyToOneOwner.class, "m").groupby("m.weight").having(
                gt(count("m.weight"), 1)).orderby("m.weight").getResultList(em);
        assertNotNull(list);
        assertEquals(5, list.size());
        assertEquals(new Integer(45), list.get(0)[0]);
        assertEquals(new Long(2), list.get(0)[1]);
        assertEquals(new Integer(50), list.get(1)[0]);
        assertEquals(new Long(2), list.get(1)[1]);
        assertEquals(new Integer(51), list.get(2)[0]);
        assertEquals(new Long(2), list.get(2)[1]);
        assertEquals(new Integer(52), list.get(3)[0]);
        assertEquals(new Long(3), list.get(3)[1]);
        assertEquals(new Integer(78), list.get(4)[0]);
        assertEquals(new Long(2), list.get(4)[1]);
    }

    public void havingCompoundCondition() throws Exception {
        List<Object[]> list = select("m.weight", count("m")).from(
                ManyToOneOwner.class, "m").groupby("m.weight").having(
                and(gt(count("m.weight"), 1), gt(sum("m.weight"), 100)))
                .orderby("m.weight").getResultList(em);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals(new Integer(51), list.get(0)[0]);
        assertEquals(new Long(2), list.get(0)[1]);
        assertEquals(new Integer(52), list.get(1)[0]);
        assertEquals(new Long(3), list.get(1)[1]);
        assertEquals(new Integer(78), list.get(2)[0]);
        assertEquals(new Long(2), list.get(2)[1]);
    }

    public void whereSingleCondition() throws Exception {
        List<ManyToOneOwner> list = select().from(ManyToOneOwner.class).where(
                eq("manyToOneOwner.bloodType", literal("AB"))).orderby(
                "manyToOneOwner.id").getResultList(em);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("maru", list.get(0).getName());
        assertEquals("rasukal", list.get(1).getName());
        assertEquals("mikel", list.get(2).getName());
    }

    public void whereCompoundCondition() throws Exception {
        List<ManyToOneOwner> list = select().from(ManyToOneOwner.class, "m")
                .where(between("m.height", 150, 170),
                        or(lt("m.weight", 45), gt("m.weight", 70))).orderby(
                        "m.id").getResultList(em);
        assertNotNull(list);
        assertEquals(4, list.size());
        assertEquals("simagoro", list.get(0).getName());
        assertEquals("minami", list.get(1).getName());
        assertEquals("ma", list.get(2).getName());
        assertEquals("usa", list.get(3).getName());
    }

    public void paging() throws Exception {
        List<ManyToOneOwner> list = select().from(ManyToOneOwner.class)
                .setFirstResult(1).setMaxResults(2)
                .orderby("manyToOneOwner.id").getResultList(em);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("gochin", list.get(0).getName());
        assertEquals("maki", list.get(1).getName());
    }

}