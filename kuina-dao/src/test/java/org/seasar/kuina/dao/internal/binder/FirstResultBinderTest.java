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
package org.seasar.kuina.dao.internal.binder;

import javax.persistence.Query;

import org.seasar.framework.unit.EasyMockTestCase;
import org.seasar.framework.unit.annotation.EasyMock;
import org.seasar.framework.unit.annotation.EasyMockType;

import static org.easymock.EasyMock.*;

/**
 * {@link FirstResultBinder}のテスト．
 * 
 * @author koichik
 */
public class FirstResultBinderTest extends EasyMockTestCase {

    @EasyMock(EasyMockType.STRICT)
    Query query;

    /**
     * Positional Parameter のテスト．
     * 
     * @throws Exception
     */
    public void testPositionalParameter() throws Exception {
        FirstResultBinder binder = new FirstResultBinder();
        binder.bind(query, 1);
    }

    /**
     * {@link #testPositionalParameter()}のMockの動作を記録．
     * 
     * @throws Exception
     */
    public void recordPositionalParameter() throws Exception {
        expect(query.setFirstResult(1)).andReturn(query);
    }

}
