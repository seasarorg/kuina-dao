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
package org.seasar.kuina.dao.criteria.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.seasar.framework.unit.EasyMockTestCase;
import org.seasar.framework.unit.annotation.EasyMock;
import org.seasar.framework.unit.annotation.EasyMockType;
import org.seasar.kuina.dao.OrderingSpec;

import static org.easymock.EasyMock.*;

/**
 * {@link CriteriaContextImpl}のテスト．
 * 
 * @author koichik
 */
public class CriteriaContextImplTest extends EasyMockTestCase {

    @EasyMock(EasyMockType.STRICT)
    Query query;

    Calendar calendar = Calendar.getInstance();

    public void testGetQueryString() throws Exception {
        CriteriaContextImpl context = new CriteriaContextImpl();
        context.append(false).append((byte) 1).append('b').append(0.1D).append(
                OrderingSpec.DESC).append(0.2F).append(100).append(200L)
                .append(new BigDecimal("100")).append((short) 10).append("aaa");
        assertEquals(
                "false1b0.1org.seasar.kuina.dao.OrderingSpec.DESC0.210020010010aaa",
                context.getQueryString());
    }

    public void testFillParameters() throws Exception {
        CriteriaContextImpl context = new CriteriaContextImpl();
        context.setParameter("string", "STRING");
        context.setParameter("int", 10);
        context.setParameter("boolean", true);
        context.setParameter("enum", TemporalType.DATE);
        context.setParameter("date", new Date(0), TemporalType.DATE);
        context.setParameter("calendar", calendar, TemporalType.TIMESTAMP);
        context.setParameter("sqlDate", new java.sql.Date(10));
        context.setParameter("sqlTime", new java.sql.Time(20));
        context.setParameter("sqlTimestamp", new java.sql.Timestamp(30));
        context.fillParameters(query);
    }

    public void recordFillParameters() throws Exception {
        expect(query.setParameter("string", "STRING")).andReturn(query);
        expect(query.setParameter("int", 10)).andReturn(query);
        expect(query.setParameter("boolean", true)).andReturn(query);
        expect(query.setParameter("enum", TemporalType.DATE)).andReturn(query);
        expect(query.setParameter("date", new Date(0), TemporalType.DATE))
                .andReturn(query);
        expect(query.setParameter("calendar", calendar, TemporalType.TIMESTAMP))
                .andReturn(query);
        expect(query.setParameter("sqlDate", new java.sql.Date(10))).andReturn(
                query);
        expect(query.setParameter("sqlTime", new java.sql.Time(20))).andReturn(
                query);
        expect(query.setParameter("sqlTimestamp", new java.sql.Timestamp(30)))
                .andReturn(query);
    }

}
