/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.kuina.dao.internal.command;

import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.util.tiger.ReflectionUtil;
import org.seasar.kuina.dao.Distinct;
import org.seasar.kuina.dao.FetchJoin;
import org.seasar.kuina.dao.FlushMode;
import org.seasar.kuina.dao.JoinSpec;
import org.seasar.kuina.dao.entity.Employee;
import org.seasar.kuina.dao.internal.ConditionalExpressionBuilder;
import org.seasar.kuina.dao.internal.condition.ConditionalExpressionBuilderFactory;
import org.seasar.kuina.dao.internal.condition.FirstResultBuilder;
import org.seasar.kuina.dao.internal.condition.MaxResultsBuilder;
import org.seasar.kuina.dao.internal.condition.OrderbyBuilder;

/**
 * 
 * @author koichik
 */
@SuppressWarnings("unchecked")
public class ParameterQueryCommandTest extends S2TestCase {

    private EntityManager em;

    private Method method = ReflectionUtil.getMethod(DummyDao.class, "findEmployee");

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("jpa.dicon");
    }

    public void testTx() throws Exception {
        ParameterQueryCommand command = new ParameterQueryCommand(
                Employee.class, method,
                true,
                new String[] { "name" },
                new ConditionalExpressionBuilder[] { ConditionalExpressionBuilderFactory
                        .createBuilder(Employee.class, "name", String.class) });
        List<Employee> list = (List) command.execute(em,
                new Object[] { "シマゴロー" });
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
    }

    public void testNeTx() throws Exception {
        ParameterQueryCommand command = new ParameterQueryCommand(
                Employee.class,method,
                true,
                new String[] { "blootType_NE" },
                new ConditionalExpressionBuilder[] { ConditionalExpressionBuilderFactory
                        .createBuilder(Employee.class, "bloodType_NE", String.class) });
        List<Employee> list = (List) command.execute(em, new Object[] { "A" });
        assertNotNull(list);
        assertEquals(19, list.size());
        assertEquals("ゴッチン", list.get(0).getName());
    }

    public void testInTx() throws Exception {
        ParameterQueryCommand command = new ParameterQueryCommand(
                Employee.class,method,
                true,
                new String[] { "name_IN" },
                new ConditionalExpressionBuilder[] { ConditionalExpressionBuilderFactory
                        .createBuilder(Employee.class, "name_IN", String[].class) });
        List<Employee> list = (List) command.execute(em, new Object[] { new String[] {"ミチロー", "にゃん太郎"} });
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("ミチロー", list.get(0).getName());
        assertEquals("にゃん太郎", list.get(1).getName());
    }

    public void testMultiParameterTx() throws Exception {
        ParameterQueryCommand command = new ParameterQueryCommand(Employee.class,method,
                true, new String[] { "name", "bloodType" },
                new ConditionalExpressionBuilder[] {
                        ConditionalExpressionBuilderFactory.createBuilder(
                                Employee.class, "name", String.class),
                        ConditionalExpressionBuilderFactory.createBuilder(
                                Employee.class, "bloodType", String.class) });
        List<Employee> list = (List) command.execute(em, new Object[] { null,
                "AB" });
        assertEquals(3, list.size());
        assertEquals("マル", list.get(0).getName());
        assertEquals("ラスカル", list.get(1).getName());
        assertEquals("マイケル", list.get(2).getName());
    }

    public void testRelationshipTx() throws Exception {
        ParameterQueryCommand command = new ParameterQueryCommand(
                Employee.class,method,
                true,
                new String[] { "belongTo$department$name" },
                ConditionalExpressionBuilderFactory
                        .createBuilders(Employee.class, new String[] {"belongTo$department$name"}, new Class<?>[] {String.class}));
        List<Employee> list = (List) command.execute(em, new Object[] { "営業" });
        assertNotNull(list);
        assertEquals(5, list.size());
        assertEquals("ミチロー", list.get(0).getName());
        assertEquals("サラ", list.get(1).getName());
        assertEquals("みなみ", list.get(2).getName());
        assertEquals("ぱんだ", list.get(3).getName());
        assertEquals("くま", list.get(4).getName());
    }

    public void testOrderbyTx() throws Exception {
        ParameterQueryCommand command = new ParameterQueryCommand(
                Employee.class, method,true, 
                new String[] { "bloodType", "orderby" },
                new ConditionalExpressionBuilder[] {
                        ConditionalExpressionBuilderFactory.createBuilder(
                                Employee.class, "bloodType", String.class),
                        new OrderbyBuilder(Employee.class) });
        List<Employee> list = (List) command.execute(em, new Object[] { "A",
                new String[] { "height", "weight" } });
        assertNotNull(list);
        assertEquals(11, list.size());
        assertEquals("ローリー", list.get(0).getName());
        assertEquals("サリー", list.get(1).getName());
        assertEquals("ぴよ", list.get(2).getName());
        assertEquals("マー", list.get(3).getName());
        assertEquals("うさぎ", list.get(4).getName());
        assertEquals("サラ", list.get(5).getName());
        assertEquals("シマゴロー", list.get(6).getName());
        assertEquals("モンチー", list.get(7).getName());
        assertEquals("うー太", list.get(8).getName());
        assertEquals("ミチロー", list.get(9).getName());
        assertEquals("クー", list.get(10).getName());
    }

    public void testPagingTx() throws Exception {
        ParameterQueryCommand command = new ParameterQueryCommand(
                Employee.class, method,true, 
                new String[] { "bloodType", "firstResult", "maxResults" },
                new ConditionalExpressionBuilder[] {
                        ConditionalExpressionBuilderFactory.createBuilder(
                                Employee.class, "bloodType", String.class),
                        new FirstResultBuilder(), new MaxResultsBuilder() });
        List<Employee> list = (List) command.execute(em, new Object[] { "A", 5,
                5 });
        assertNotNull(list);
        assertEquals(5, list.size());
        assertEquals("ぴよ", list.get(0).getName());
        assertEquals("マー", list.get(1).getName());
        assertEquals("サリー", list.get(2).getName());
        assertEquals("うさぎ", list.get(3).getName());
        assertEquals("うー太", list.get(4).getName());
    }

    public void testAutoFlushTx() throws Exception {
        Employee emp = em.find(Employee.class, 16);
        emp.setBloodType("B");
        
        ParameterQueryCommand command = new ParameterQueryCommand(
                Employee.class, method,true, 
                new String[] { "bloodType" },
                new ConditionalExpressionBuilder[] {
                        ConditionalExpressionBuilderFactory.createBuilder(
                                Employee.class, "bloodType", String.class) });
        List<Employee> list = (List) command.execute(em, new Object[] { "A" });
        assertNotNull(list);
        assertEquals(10, list.size());
    }

    public void testNoFlushTx() throws Exception {
        Employee emp = em.find(Employee.class, 16);
        emp.setBloodType("B");
        
        ParameterQueryCommand command = new ParameterQueryCommand(
                Employee.class, ReflectionUtil.getMethod(DummyDao.class, "findEmployeeNoFlush"),
                true, 
                new String[] { "bloodType" },
                new ConditionalExpressionBuilder[] {
                        ConditionalExpressionBuilderFactory.createBuilder(
                                Employee.class, "bloodType", String.class) });
        List<Employee> list = (List) command.execute(em, new Object[] { "A" });
        assertNotNull(list);
        assertEquals(11, list.size());
    }

    public interface DummyDao {
        @Distinct
        @FetchJoin(value = "belongTo", joinSpec = JoinSpec.LEFT_OUTER_JOIN)
        List<Employee> findEmployee();

        @Distinct
        @FetchJoin(value = "belongTo", joinSpec = JoinSpec.LEFT_OUTER_JOIN)
        @FlushMode(FlushModeType.COMMIT)
        List<Employee> findEmployeeNoFlush();
    }

}
