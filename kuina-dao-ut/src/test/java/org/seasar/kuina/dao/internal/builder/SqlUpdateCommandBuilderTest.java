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
package org.seasar.kuina.dao.internal.builder;

import java.lang.reflect.Method;

import javax.persistence.EntityManager;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.util.ClassUtil;
import org.seasar.kuina.dao.dao.EmpDao;
import org.seasar.kuina.dao.internal.Command;

/**
 * 
 * @author higa
 */
@SuppressWarnings("unchecked")
public class SqlUpdateCommandBuilderTest extends S2TestCase {

    private EntityManager em;

    private SqlUpdateCommandBuilder builder;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("kuina-dao.dicon");
    }

    public void testUpdateEmpsTx() throws Exception {
        Method m = ClassUtil.getMethod(EmpDao.class, "updateEmps", null);

        Command command = builder.build(EmpDao.class, m);
        int rows = Integer.class.cast(command.execute(em, null));
        System.out.println(rows);
        assertEquals(30, rows);
    }

    public void testUpdateEmpTx() throws Exception {
        Method m = ClassUtil.getMethod(EmpDao.class, "updateEmp",
                new Class<?>[] { int.class });

        Command command = builder.build(EmpDao.class, m);
        int rows = Integer.class.cast(command.execute(em, new Object[] {1}));
        System.out.println(rows);
        assertEquals(1, rows);
    }
}