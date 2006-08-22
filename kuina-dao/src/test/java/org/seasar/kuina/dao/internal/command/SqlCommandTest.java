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
package org.seasar.kuina.dao.internal.command;

import java.util.List;

import javax.persistence.EntityManager;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.jpa.Dialect;

/**
 * 
 * @author higa
 */
@SuppressWarnings("unchecked")
public class SqlCommandTest extends S2TestCase {

    private EntityManager em;

    private Dialect dialect;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("s2hibernate-jpa.dicon");
    }

    public void testExecute_resultListTx() throws Exception {
        SqlCommand command = new SqlCommand(true, EmpDto.class,
                "select id, name from employee", null, null, dialect, null,
                null);
        List<EmpDto> emps = (List<EmpDto>) command.execute(em, null);
        System.out.println(emps);
        assertEquals(30, emps.size());
    }

    public void testExecute_singleResultTx() throws Exception {
        SqlCommand command = new SqlCommand(false, EmpDto.class,
                "select name from employee where id = /*id*/0",
                new String[] { "id" }, new Class[] { Integer.class }, dialect,
                null, null);
        EmpDto emp = (EmpDto) command.execute(em,
                new Object[] { new Integer(1) });
        System.out.println(emp);
        assertEquals("シマゴロー", emp.getName());
    }

    public static class EmpDto {
        private Integer id;

        private String name;

        /**
         * @return idを返します。
         */
        public Integer getId() {
            return id;
        }

        /**
         * @param id
         *            idを設定します。
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         * @return nameを返します。
         */
        public String getName() {
            return name;
        }

        /**
         * @param name
         *            nameを設定します。
         */
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "{" + id + ", " + name + "}";
        }
    }
}
