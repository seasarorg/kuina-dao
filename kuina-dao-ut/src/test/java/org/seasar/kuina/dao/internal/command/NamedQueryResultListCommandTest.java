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
package org.seasar.kuina.dao.internal.command;

import java.lang.reflect.Method;

import javax.persistence.EntityManager;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.util.tiger.ReflectionUtil;
import org.seasar.kuina.dao.internal.binder.ParameterBinder;

/**
 * 
 * @author koichik
 */
@SuppressWarnings("unchecked")
public class NamedQueryResultListCommandTest extends S2TestCase {

    private EntityManager em;

    private Method method = ReflectionUtil
            .getMethod(DummyDao.class, "getCount");

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("jpa.dicon");
    }

    public void testCountTx() throws Exception {
        NamedQueryCommand command = new NamedQueryCommand(DummyDao.class,
                method, false, "Employee.getCount", new ParameterBinder[0]);
        int result = Integer.class.cast(command.execute(em, new Object[0]));
        assertEquals(30, result);
    }

    public interface DummyDao {

        Integer getCount();

    }

}
