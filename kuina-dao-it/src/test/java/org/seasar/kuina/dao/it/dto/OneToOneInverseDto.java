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
package org.seasar.kuina.dao.it.dto;

/**
 * 
 * @author nakamura
 */
public class OneToOneInverseDto {

    protected String name_CONTAINS;

    protected String oneToOneOwner$name_EQ;

    protected String[] orderby;

    protected int firstResult = -1;

    protected int maxResults = -1;

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public String getOneToOneOwner$name_EQ() {
        return oneToOneOwner$name_EQ;
    }

    public void setOneToOneOwner$name_EQ(String oneToOneOwner$name_EQ) {
        this.oneToOneOwner$name_EQ = oneToOneOwner$name_EQ;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public String getName_CONTAINS() {
        return name_CONTAINS;
    }

    public void setName_CONTAINS(String name_CONTAINS) {
        this.name_CONTAINS = name_CONTAINS;
    }

    public String[] getOrderby() {
        return orderby;
    }

    public void setOrderby(String... orderby) {
        this.orderby = orderby;
    }

}
