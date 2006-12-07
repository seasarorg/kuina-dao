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
package org.seasar.kuina.dao.criteria;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 * 
 * @author koichik
 */
public interface CriteriaContext {

    CriteriaContext append(boolean b);

    CriteriaContext append(byte b);

    CriteriaContext append(short s);

    CriteriaContext append(int i);

    CriteriaContext append(long l);

    CriteriaContext append(float f);

    CriteriaContext append(double d);

    CriteriaContext append(char ch);

    CriteriaContext append(String s);

    CriteriaContext append(Enum e);

    CriteriaContext append(Object o);

    CriteriaContext cutBack(int length);

    void setParameter(String name, Object value);

    void setParameter(String name, Date value, TemporalType temporalType);

    void setParameter(String name, Calendar value, TemporalType temporalType);

    void fillParameters(Query query);

    String getQueryString();

}
