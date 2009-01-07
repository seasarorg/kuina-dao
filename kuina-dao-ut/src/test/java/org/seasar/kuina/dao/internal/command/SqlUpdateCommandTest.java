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
package org.seasar.kuina.dao.internal.command;

import javax.persistence.EntityManager;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.jpa.DialectManager;

/**
 * 
 * @author higa
 */
@SuppressWarnings("unchecked")
public class SqlUpdateCommandTest extends S2TestCase {

    private EntityManager em;

    private DialectManager dialectManager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("jpa.dicon");
    }

    public void testUpdatesTx() throws Exception {
        SqlUpdateCommand command = new SqlUpdateCommand(EmpDao.class.getMethod("updateAll"),
                "update employee set name = null", null, null, dialectManager,
                null);
        int rows = Integer.class.cast(command.execute(em, null));
        System.out.println(rows);
        assertEquals(30, rows);
    }

    public void testUpdateTx() throws Exception {
        SqlUpdateCommand command = new SqlUpdateCommand(EmpDao.class.getMethod("updateById",
                Integer.class), "update employee set name = null where id=/*id*/0",
                new String[] {"id"}, new Class<?>[] {Integer.class},
                dialectManager, null);
        int rows = Integer.class.cast(command.execute(em, new Object[] { 1 }));
        System.out.println(rows);
        assertEquals(1, rows);
    }

    public interface EmpDao {

        int updateAll();

        int updateById(Integer id);

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
