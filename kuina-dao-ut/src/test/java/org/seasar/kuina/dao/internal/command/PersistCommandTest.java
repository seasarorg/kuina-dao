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
package org.seasar.kuina.dao.internal.command;

import javax.persistence.EntityManager;

import org.seasar.framework.unit.EasyMockTestCase;

/**
 * 
 * @author koichik
 */
public class PersistCommandTest extends EasyMockTestCase {
    private EntityManager em;

    @Override
    protected void setUp() throws Exception {
        em = createStrictMock(EntityManager.class);
    }

    public void test() throws Exception {
        final Object entity = new Object();
        new Subsequence() {
            @Override
            public void replay() throws Exception {
                PersistCommand command = new PersistCommand(Object.class);
                command.execute(em, new Object[] { entity });
            }

            @Override
            public void record() throws Exception {
                em.persist(entity);
            }
        }.doTest();
    }
}
