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

import org.seasar.kuina.dao.it.entity.EmployeeStatus;
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.seasar.kuina.dao.criteria.CriteriaOperations.abs;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.add;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.and;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.between;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.concat;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.currentDate;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.currentTime;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.currentTimestamp;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.divide;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.eq;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.ge;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.gt;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.in;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.isEmpty;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.isNotEmpty;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.isNotNull;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.isNull;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.le;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.length;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.like;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.literal;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.locate;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.lower;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.lt;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.minus;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.mod;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.multiply;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.ne;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.not;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.or;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.parenthesis;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.path;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.plus;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.size;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.sqrt;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.subtract;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.trim;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.upper;

import static org.seasar.kuina.dao.criteria.grammar.TrimSpecification.BOTH;
import static org.seasar.kuina.dao.criteria.grammar.TrimSpecification.LEADING;
import static org.seasar.kuina.dao.criteria.grammar.TrimSpecification.TRAILING;

/**
 * 
 * @author nakamura
 */
public abstract class AbstractManyToOneOwnerDaoTest {

    private ManyToOneOwnerDao ownerDao;

    public void _add() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(add(
                "manyToOneOwner.weight", "manyToOneOwner.height"), 240));
        assertNotNull(list);
        assertEquals(2, list.size());
        assertNotNull("simagoro", list.get(0).getName());
        assertNotNull("michiro", list.get(1).getName());
    }

    public void _add2() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(add(1, 2), 3));
        assertNotNull(list);
        assertEquals(30, list.size());
    }

    public void _subtract() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(subtract(
                "manyToOneOwner.weight", "manyToOneOwner.height"), -100));
        assertNotNull(list);
        assertEquals(2, list.size());
        assertNotNull("michiro", list.get(0).getName());
        assertNotNull("prin", list.get(1).getName());
    }

    public void _subtract2() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(subtract(2, 1),
                1));
        assertNotNull(list);
        assertEquals(30, list.size());
    }

    public void _multiply() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(multiply(
                "manyToOneOwner.weight", "manyToOneOwner.height"), 12096));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("simagoro", list.get(0).getName());
    }

    public void _multiply2() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(
                multiply(50, 3), 150));
        assertNotNull(list);
        assertEquals(30, list.size());
    }

    public void _divide() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(lt(divide(
                "manyToOneOwner.height", "manyToOneOwner.weight"), 2));
        assertNotNull(list);
        assertEquals(2, list.size());
        assertNotNull("panda", list.get(0).getName());
        assertNotNull("kuma", list.get(1).getName());
    }

    public void _divide2() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(divide(10, 2),
                5));
        assertNotNull(list);
        assertEquals(30, list.size());
    }

    public void _eq_string() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(
                "manyToOneOwner.name", literal("simagoro")));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("simagoro", list.get(0).getName());
    }

    public void _eq_number() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(
                "manyToOneOwner.weight", 72));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("simagoro", list.get(0).getName());
    }

    public void _eq_date() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(
                "manyToOneOwner.birthday", literal("1953-10-01")));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("simagoro", list.get(0).getName());
    }

    public void _eq_boolean() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(
                "manyToOneOwner.retired", true));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("gochin", list.get(0).getName());
    }

    public void _eq_enum() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(
                "manyToOneOwner.employeeStatus",
                literal(EmployeeStatus.FULL_TIME)));
        assertNotNull(list);
        assertEquals(10, list.size());
        assertNotNull("simagoro", list.get(0).getName());
        assertNotNull("maru", list.get(1).getName());
        assertNotNull("sara", list.get(2).getName());
        assertNotNull("pko", list.get(3).getName());
        assertNotNull("nekomaru", list.get(4).getName());
        assertNotNull("piyo", list.get(5).getName());
        assertNotNull("gon", list.get(6).getName());
        assertNotNull("tonton", list.get(7).getName());
        assertNotNull("usa", list.get(8).getName());
        assertNotNull("mikel", list.get(9).getName());
    }

    public void _eq_entity() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(
                "manyToOneOwner.oneToManyInverse",
                "manyToOneOwner.subOneToManyInverse"));
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    public void _ne() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(ne(
                "manyToOneOwner.name", literal("simagoro")));
        assertNotNull(list);
        assertEquals(29, list.size());
        assertNotNull("gochin", list.get(0).getName());
    }

    public void _ge_le() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(ge(
                "manyToOneOwner.weight", 52), le("manyToOneOwner.weight", 56));
        assertNotNull(list);
        assertEquals(5, list.size());
        assertNotNull("maki", list.get(0).getName());
        assertNotNull("pko", list.get(1).getName());
        assertNotNull("monchi", list.get(2).getName());
        assertNotNull("q", list.get(3).getName());
        assertNotNull("su", list.get(4).getName());
    }

    public void _gt_lt() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(gt(
                "manyToOneOwner.weight", 52), lt("manyToOneOwner.weight", 56));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("monchi", list.get(0).getName());
    }

    public void _like() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(like(
                "manyToOneOwner.name", literal("ma%")));
        assertNotNull(list);
        assertEquals(3, list.size());
        assertNotNull("maki", list.get(0).getName());
        assertNotNull("maru", list.get(1).getName());
        assertNotNull("ma", list.get(2).getName());
    }

    public void _like2() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(like(
                "manyToOneOwner.name", literal("ma%"), literal("\\")));
        assertNotNull(list);
        assertEquals(3, list.size());
        assertNotNull("maki", list.get(0).getName());
        assertNotNull("maru", list.get(1).getName());
        assertNotNull("ma", list.get(2).getName());
    }

    public void _between() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(between(
                "manyToOneOwner.weight", 52, 56));
        assertNotNull(list);
        assertEquals(5, list.size());
        assertNotNull("maki", list.get(0).getName());
        assertNotNull("pko", list.get(1).getName());
        assertNotNull("monchi", list.get(2).getName());
        assertNotNull("q", list.get(3).getName());
        assertNotNull("su", list.get(4).getName());
    }

    public void _in() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(in(
                "manyToOneOwner.weight", 50, 60, 70));
        assertNotNull(list);
        assertEquals(4, list.size());
        assertNotNull("gochin", list.get(0).getName());
        assertNotNull("michiro", list.get(1).getName());
        assertNotNull("sara", list.get(2).getName());
        assertNotNull("tasuke", list.get(3).getName());
    }

    public void _and() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(and(eq(
                "manyToOneOwner.weight", 52),
                isNotNull("manyToOneOwner.weddingDay")));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("maki", list.get(0).getName());
    }

    public void _or() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(or(eq(
                "manyToOneOwner.weight", 52),
                isNotNull("manyToOneOwner.weddingDay")));
        assertNotNull(list);
        assertEquals(7, list.size());
        assertNotNull("maki", list.get(0).getName());
        assertNotNull("pko", list.get(1).getName());
        assertNotNull("panda", list.get(2).getName());
        assertNotNull("nekomaru", list.get(3).getName());
        assertNotNull("q", list.get(4).getName());
        assertNotNull("ma", list.get(5).getName());
        assertNotNull("su", list.get(6).getName());
    }

    public void _not() throws Exception {
        List<ManyToOneOwner> list = ownerDao
                .findByCondition(not(parenthesis(and(gt(
                        "manyToOneOwner.weight", 40), gt(
                        "manyToOneOwner.height", 153)))));
        assertNotNull(list);
        assertEquals(4, list.size());
        assertNotNull("minami", list.get(0).getName());
        assertNotNull("prin", list.get(1).getName());
        assertNotNull("sary", list.get(2).getName());
        assertNotNull("roly", list.get(3).getName());
    }

    public void _isNull() throws Exception {
        List<ManyToOneOwner> list = ownerDao
                .findByCondition(isNull("manyToOneOwner.weddingDay"));
        assertNotNull(list);
        assertEquals(25, list.size());
    }

    public void _isNotNull() throws Exception {
        List<ManyToOneOwner> list = ownerDao
                .findByCondition(isNotNull("manyToOneOwner.weddingDay"));
        assertNotNull(list);
        assertEquals(5, list.size());
    }

    public void _isEmpty() throws Exception {
        List<ManyToOneOwner> list = ownerDao
                .findByCondition(isEmpty("manyToOneOwner.oneToManyInverse.subManyToOneOwners"));
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    public void _isNotEmpty() throws Exception {
        List<ManyToOneOwner> list = ownerDao
                .findByCondition(isNotEmpty("manyToOneOwner.oneToManyInverse.subManyToOneOwners"));
        assertNotNull(list);
        assertEquals(30, list.size());
    }

    public void _trim() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(
                trim("manyToOneOwner.name"), literal("simagoro")));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("simagoro", list.get(0).getName());
    }

    public void _trim2() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(trim('s',
                "manyToOneOwner.name"), literal("imagoro")));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("simagoro", list.get(0).getName());
    }

    public void _trim_leading() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(trim(LEADING,
                "manyToOneOwner.name"), literal("simagoro")));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("simagoro", list.get(0).getName());
    }

    public void _trim_leading2() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(trim(LEADING,
                's', "manyToOneOwner.name"), literal("imagoro")));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("simagoro", list.get(0).getName());
    }

    public void _trim_trailing() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(trim(TRAILING,
                "manyToOneOwner.name"), literal("simagoro")));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("simagoro", list.get(0).getName());
    }

    public void _trim_trailing2() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(trim(TRAILING,
                'o', "manyToOneOwner.name"), literal("simagor")));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("simagoro", list.get(0).getName());
    }

    public void _trim_both() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(trim(BOTH,
                "manyToOneOwner.name"), literal("simagoro")));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("simagoro", list.get(0).getName());
    }

    public void _trim_both2() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(trim(BOTH, 's',
                "manyToOneOwner.name"), literal("imagoro")));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("simagoro", list.get(0).getName());
    }

    public void _concat() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(concat(
                "manyToOneOwner.name", "manyToOneOwner.oneToManyInverse.name"),
                literal("simagoroBusiness")));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("simagoro", list.get(0).getName());
    }

    public void _lowner() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(
                lower("manyToOneOwner.oneToManyInverse.name"),
                literal("personnel")));
        assertNotNull(list);
        assertEquals(3, list.size());
        assertNotNull("nekomaru", list.get(0).getName());
        assertNotNull("nyantaro", list.get(1).getName());
        assertNotNull("monchi", list.get(2).getName());
    }

    public void _upper() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(
                upper("manyToOneOwner.oneToManyInverse.name"),
                literal("PERSONNEL")));
        assertNotNull(list);
        assertEquals(3, list.size());
        assertNotNull("nekomaru", list.get(0).getName());
        assertNotNull("nyantaro", list.get(1).getName());
        assertNotNull("monchi", list.get(2).getName());
    }

    public void _length() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(
                length("manyToOneOwner.name"), 5));
        assertNotNull(list);
        assertEquals(2, list.size());
        assertNotNull("panda", list.get(0).getName());
        assertNotNull("mikel", list.get(1).getName());
    }

    public void _locate() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(locate(
                literal("s"), "manyToOneOwner.name"), 3));
        assertNotNull(list);
        assertEquals(2, list.size());
        assertNotNull("rasukal", list.get(0).getName());
        assertNotNull("tasuke", list.get(1).getName());
    }

    public void _locate2() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(locate(
                literal("s"), path("manyToOneOwner.name"), literal(2)), 3));
        assertNotNull(list);
        assertEquals(2, list.size());
        assertNotNull("rasukal", list.get(0).getName());
        assertNotNull("tasuke", list.get(1).getName());
    }

    public void _abs() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(
                abs("manyToOneOwner.weight"), 50));
        assertNotNull(list);
        assertEquals(2, list.size());
        assertNotNull("sara", list.get(0).getName());
        assertNotNull("tasuke", list.get(1).getName());
    }

    public void _sqrt() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(
                sqrt("manyToOneOwner.weight"), 7d));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNotNull("mikel", list.get(0).getName());
    }

    public void _mod() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(mod(
                "manyToOneOwner.weight", 49), 1));
        assertNotNull(list);
        assertEquals(2, list.size());
        assertNotNull("sara", list.get(0).getName());
        assertNotNull("tasuke", list.get(1).getName());
    }

    public void _size() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(
                size("manyToOneOwner.oneToManyInverse.subManyToOneOwners"), 2));
        assertNotNull(list);
        assertEquals(3, list.size());
        assertNotNull("nekomaru", list.get(0).getName());
        assertNotNull("nyantaro", list.get(1).getName());
        assertNotNull("monchi", list.get(2).getName());
    }

    public void _currentDate() throws Exception {
        ownerDao.findByCondition(eq("manyToOneOwner.birthday", currentDate()));
    }

    public void _currentTime() throws Exception {
        ownerDao.findByCondition(eq("manyToOneOwner.birthTime", currentTime()));
    }

    public void _currentTimestamp() throws Exception {
        ownerDao.findByCondition(eq("manyToOneOwner.birthTimestamp",
                currentTimestamp()));
    }

    public void _parenthesis() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(parenthesis(or(
                like("manyToOneOwner.name", literal("u%")), gt(
                        "manyToOneOwner.hireFiscalYear", 2000))), lt(
                "manyToOneOwner.weight", 52));
        assertNotNull(list);
        assertEquals(3, list.size());
        assertNotNull("usa", list.get(0).getName());
        assertNotNull("mikel", list.get(1).getName());
        assertNotNull("miya", list.get(2).getName());
    }

    public void _plus() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(
                plus("manyToOneOwner.weight"), 50));
        assertNotNull(list);
        assertEquals(2, list.size());
        assertNotNull("sara", list.get(0).getName());
        assertNotNull("tasuke", list.get(1).getName());
    }

    public void _minus() throws Exception {
        List<ManyToOneOwner> list = ownerDao.findByCondition(eq(
                minus("manyToOneOwner.weight"), 50));
        assertNotNull(list);
        assertEquals(0, list.size());
    }

}
