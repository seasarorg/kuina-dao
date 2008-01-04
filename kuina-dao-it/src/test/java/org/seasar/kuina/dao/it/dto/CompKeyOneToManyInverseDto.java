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
package org.seasar.kuina.dao.it.dto;

import java.util.Date;

/**
 * 
 * @author nakamura
 */
public class CompKeyOneToManyInverseDto {

    protected String name_EQ;

    protected String name_CONTAINS;

    protected Date compKeyManyToOneOwners$id$pk2_EQ;

    protected String compKeyManyToOneOwners$info$bloodType_EQ;

    protected Integer compKeyManyToOneOwners$info$weight_GT;

    protected String[] orderby;

    protected int firstResult = -1;

    protected int maxResults = -1;

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public Date getCompKeyManyToOneOwners$id$pk2_EQ() {
        return compKeyManyToOneOwners$id$pk2_EQ;
    }

    public void setCompKeyManyToOneOwners$id$pk2_EQ(
            Date compKeyManyToOneOwners$id$pk2_EQ) {
        this.compKeyManyToOneOwners$id$pk2_EQ =
            compKeyManyToOneOwners$id$pk2_EQ;
    }

    public String getCompKeyManyToOneOwners$info$bloodType_EQ() {
        return compKeyManyToOneOwners$info$bloodType_EQ;
    }

    public void setCompKeyManyToOneOwners$info$bloodType_EQ(
            String compKeyManyToOneOwners$info$bloodType_EQ) {
        this.compKeyManyToOneOwners$info$bloodType_EQ =
            compKeyManyToOneOwners$info$bloodType_EQ;
    }

    public Integer getCompKeyManyToOneOwners$info$weight_GT() {
        return compKeyManyToOneOwners$info$weight_GT;
    }

    public void setCompKeyManyToOneOwners$info$weight_GT(
            Integer compKeyManyToOneOwners$info$weight_GT) {
        this.compKeyManyToOneOwners$info$weight_GT =
            compKeyManyToOneOwners$info$weight_GT;
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

    public String getName_EQ() {
        return name_EQ;
    }

    public void setName_EQ(String name_EQ) {
        this.name_EQ = name_EQ;
    }

    public String[] getOrderby() {
        return orderby;
    }

    public void setOrderby(String... orderby) {
        this.orderby = orderby;
    }

}