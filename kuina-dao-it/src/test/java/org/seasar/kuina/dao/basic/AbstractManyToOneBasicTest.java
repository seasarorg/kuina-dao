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
package org.seasar.kuina.dao.basic;

import java.util.List;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.kuina.dao.dao.ManyToOneOwnerDao;
import org.seasar.kuina.dao.entity.ManyToOneOwner;

/**
 * 
 * @author nakamura
 */
public abstract class AbstractManyToOneBasicTest extends S2TestCase {

	private ManyToOneOwnerDao dao;
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
    	include("kuina-dao.dicon");
    }
	
	public void testFindAllTx() throws Exception {
		List<ManyToOneOwner> list = dao.findAll();
        assertNotNull(list);
        assertEquals(30, list.size());
	}
	
}
