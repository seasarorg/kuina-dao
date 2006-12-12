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
package org.seasar.kuina.dao.it.dto;

/**
 * 
 * @author nakamura
 */
public class OneToManyInverseDto {

    protected String name_EQ;

    protected String name_CONTAINS;

    protected String manyToOneOwners$bloodType_EQ;

    protected Integer manyToOneOwners$weight_GT;

    protected Integer manyToOneOwners$subOneToManyInverse$id_EQ;

    protected String manyToOneOwners$subOneToManyInverse$name_CONTAINS;

    protected String[] orderby;

    protected int firstResult = -1;

    protected int maxResults = -1;

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public String getManyToOneOwners$bloodType_EQ() {
        return manyToOneOwners$bloodType_EQ;
    }

    public void setManyToOneOwners$bloodType_EQ(
            String manyToOneOwners$bloodType_EQ) {
        this.manyToOneOwners$bloodType_EQ = manyToOneOwners$bloodType_EQ;
    }

    public Integer getManyToOneOwners$subOneToManyInverse$id_EQ() {
        return manyToOneOwners$subOneToManyInverse$id_EQ;
    }

    public void setManyToOneOwners$subOneToManyInverse$id_EQ(
            Integer manyToOneOwners$subOneToManyInverse$id_EQ) {
        this.manyToOneOwners$subOneToManyInverse$id_EQ = manyToOneOwners$subOneToManyInverse$id_EQ;
    }

    public String getManyToOneOwners$subOneToManyInverse$name_CONTAINS() {
        return manyToOneOwners$subOneToManyInverse$name_CONTAINS;
    }

    public void setManyToOneOwners$subOneToManyInverse$name_CONTAINS(
            String manyToOneOwners$subOneToManyInverse$name_CONTAINS) {
        this.manyToOneOwners$subOneToManyInverse$name_CONTAINS = manyToOneOwners$subOneToManyInverse$name_CONTAINS;
    }

    public Integer getManyToOneOwners$weight_GT() {
        return manyToOneOwners$weight_GT;
    }

    public void setManyToOneOwners$weight_GT(Integer manyToOneOwners$weight_GT) {
        this.manyToOneOwners$weight_GT = manyToOneOwners$weight_GT;
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
