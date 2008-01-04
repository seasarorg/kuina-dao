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
package org.seasar.kuina.dao.internal.binder;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.seasar.framework.unit.EasyMockTestCase;
import org.seasar.framework.unit.annotation.EasyMock;
import org.seasar.framework.unit.annotation.EasyMockType;

import static org.easymock.EasyMock.*;

/**
 * {@link CalendarParameterBinder}のテスト．
 * 
 * @author koichik
 */
public class CalendarParameterBinderTest extends EasyMockTestCase {

    @EasyMock(EasyMockType.STRICT)
    Query query;

    Calendar calendar;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
    }

    /**
     * Named Parameter のテスト．
     * 
     * @throws Exception
     */
    public void testNamedParameter() throws Exception {
        CalendarParameterBinder binder = new CalendarParameterBinder("name",
                TemporalType.DATE);
        binder.bind(query, calendar);
    }

    /**
     * {@link #testFillParameters}のMockの動作を記録．
     * 
     * @throws Exception
     */
    public void recordNamedParameter() throws Exception {
        expect(query.setParameter("name", calendar, TemporalType.DATE))
                .andReturn(query);
    }

    /**
     * Positional Parameterのテスト．
     * 
     * @throws Exception
     */
    public void testPositionalParameter() throws Exception {
        CalendarParameterBinder binder = new CalendarParameterBinder(1,
                TemporalType.TIME);
        binder.bind(query, calendar);
    }

    /**
     * {@link #testPositionalParameter()}のMockの動作を記録．
     * 
     * @throws Exception
     */
    public void recordPositionalParameter() throws Exception {
        expect(query.setParameter(1, calendar, TemporalType.TIME)).andReturn(
                query);
    }

}
