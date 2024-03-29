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
package org.seasar.kuina.dao.internal.metadata;

import javax.persistence.EntityManager;

import org.seasar.framework.unit.S2TigerTestCase;
import org.seasar.kuina.dao.dao.EmployeeDao;
import org.seasar.kuina.dao.dao.aaa.CustomerDao;
import org.seasar.kuina.dao.internal.DaoMetadata;

/**
 * 
 * @author koichik
 */
public class DaoMetadataImplTest extends S2TigerTestCase {

    EntityManager entityManager;

    EntityManager aaaEntityManager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("kuina-dao.dicon");
        register(createStrictMock(EntityManager.class), "aaaEntityManager");
    }

    public void test() throws Exception {
        DaoMetadataImpl metadata = DaoMetadataImpl.class
                .cast(getComponent(DaoMetadata.class));
        metadata.initialize(EmployeeDao.class);
        assertSame(entityManager, metadata.entityManager);

        metadata = DaoMetadataImpl.class.cast(getComponent(DaoMetadata.class));
        metadata.initialize(CustomerDao.class);
        assertSame(aaaEntityManager, metadata.entityManager);
    }

}
