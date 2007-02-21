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
package org.seasar.kuina.dao.it.dto;

import java.util.Date;

import org.seasar.kuina.dao.it.entity.CompKeyOneToManyInverse;

/**
 * 
 * @author nakamura
 */
public class CompKeyManyToOneOwnerDto {

    protected String name_GT;

    protected String name_LT;

    protected String name_STARTS;

    protected Date info$birthday_GT;

    protected CompKeyOneToManyInverse compKeyOneToManyInverse_EQ;

    protected String compKeyOneToManyInverse$name_EQ;

    protected String[] orderby;

    protected int firstResult = -1;

    protected int maxResults = -1;

    public CompKeyOneToManyInverse getCompKeyOneToManyInverse_EQ() {
        return compKeyOneToManyInverse_EQ;
    }

    public void setCompKeyOneToManyInverse_EQ(
            CompKeyOneToManyInverse compKeyOneToManyInverse_EQ) {
        this.compKeyOneToManyInverse_EQ = compKeyOneToManyInverse_EQ;
    }

    public String getCompKeyOneToManyInverse$name_EQ() {
        return compKeyOneToManyInverse$name_EQ;
    }

    public void setCompKeyOneToManyInverse$name_EQ(
            String compKeyOneToManyInverse$name_EQ) {
        this.compKeyOneToManyInverse$name_EQ = compKeyOneToManyInverse$name_EQ;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public Date getInfo$birthday_GT() {
        return info$birthday_GT;
    }

    public void setInfo$birthday_GT(Date info$birthday_GT) {
        this.info$birthday_GT = info$birthday_GT;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public String getName_GT() {
        return name_GT;
    }

    public void setName_GT(String name_GT) {
        this.name_GT = name_GT;
    }

    public String getName_LT() {
        return name_LT;
    }

    public void setName_LT(String name_LT) {
        this.name_LT = name_LT;
    }

    public String getName_STARTS() {
        return name_STARTS;
    }

    public void setName_STARTS(String name_STARTS) {
        this.name_STARTS = name_STARTS;
    }

    public String[] getOrderby() {
        return orderby;
    }

    public void setOrderby(String... orderby) {
        this.orderby = orderby;
    }

}
