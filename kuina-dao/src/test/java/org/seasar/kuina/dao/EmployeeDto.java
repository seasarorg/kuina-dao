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
package org.seasar.kuina.dao;

/**
 * 
 * @author koichik
 */
public class EmployeeDto {

    protected String name_EQ;

    protected String name_NE;

    protected String name_GT;

    protected String name_GE;

    protected String name_LT;

    protected String name_LE;

    protected String name_LIKE;

    protected String name_STARTS;

    protected String name_ENDS;

    protected String name_CONTAINS;

    protected boolean name_IS_NULL;

    protected boolean name_IS_NOT_NULL;

    protected String orderby;

    protected int firstResult = -1;

    protected int maxResults = -1;

    /**
     * @return name_CONTAINSを返します。
     */
    public String getName_CONTAINS() {
        return name_CONTAINS;
    }

    /**
     * @param name_CONTAINS
     *            name_CONTAINSを設定します。
     */
    public void setName_CONTAINS(String name_CONTAINS) {
        this.name_CONTAINS = name_CONTAINS;
    }

    /**
     * @return name_ENDSを返します。
     */
    public String getName_ENDS() {
        return name_ENDS;
    }

    /**
     * @param name_ENDS
     *            name_ENDSを設定します。
     */
    public void setName_ENDS(String name_ENDS) {
        this.name_ENDS = name_ENDS;
    }

    /**
     * @return name_EQを返します。
     */
    public String getName_EQ() {
        return name_EQ;
    }

    /**
     * @param name_EQ
     *            name_EQを設定します。
     */
    public void setName_EQ(String name_EQ) {
        this.name_EQ = name_EQ;
    }

    /**
     * @return name_GEを返します。
     */
    public String getName_GE() {
        return name_GE;
    }

    /**
     * @param name_GE
     *            name_GEを設定します。
     */
    public void setName_GE(String name_GE) {
        this.name_GE = name_GE;
    }

    /**
     * @return name_GTを返します。
     */
    public String getName_GT() {
        return name_GT;
    }

    /**
     * @param name_GT
     *            name_GTを設定します。
     */
    public void setName_GT(String name_GT) {
        this.name_GT = name_GT;
    }

    /**
     * @return name_IS_NOT_NULLを返します。
     */
    public boolean isName_IS_NOT_NULL() {
        return name_IS_NOT_NULL;
    }

    /**
     * @param name_IS_NOT_NULL
     *            name_IS_NOT_NULLを設定します。
     */
    public void setName_IS_NOT_NULL(boolean name_IS_NOT_NULL) {
        this.name_IS_NOT_NULL = name_IS_NOT_NULL;
    }

    /**
     * @return name_IS_NULLを返します。
     */
    public boolean isName_IS_NULL() {
        return name_IS_NULL;
    }

    /**
     * @param name_IS_NULL
     *            name_IS_NULLを設定します。
     */
    public void setName_IS_NULL(boolean name_IS_NULL) {
        this.name_IS_NULL = name_IS_NULL;
    }

    /**
     * @return name_LEを返します。
     */
    public String getName_LE() {
        return name_LE;
    }

    /**
     * @param name_LE
     *            name_LEを設定します。
     */
    public void setName_LE(String name_LE) {
        this.name_LE = name_LE;
    }

    /**
     * @return name_LIKEを返します。
     */
    public String getName_LIKE() {
        return name_LIKE;
    }

    /**
     * @param name_LIKE
     *            name_LIKEを設定します。
     */
    public void setName_LIKE(String name_LIKE) {
        this.name_LIKE = name_LIKE;
    }

    /**
     * @return name_LTを返します。
     */
    public String getName_LT() {
        return name_LT;
    }

    /**
     * @param name_LT
     *            name_LTを設定します。
     */
    public void setName_LT(String name_LT) {
        this.name_LT = name_LT;
    }

    /**
     * @return name_NEを返します。
     */
    public String getName_NE() {
        return name_NE;
    }

    /**
     * @param name_NE
     *            name_NEを設定します。
     */
    public void setName_NE(String name_NE) {
        this.name_NE = name_NE;
    }

    /**
     * @return name_STARTSを返します。
     */
    public String getName_STARTS() {
        return name_STARTS;
    }

    /**
     * @param name_STARTS
     *            name_STARTSを設定します。
     */
    public void setName_STARTS(String name_STARTS) {
        this.name_STARTS = name_STARTS;
    }

    /**
     * @return orderbyを返します。
     */
    public String getOrderby() {
        return orderby;
    }

    /**
     * @param orderby
     *            orderbyを設定します。
     */
    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    /**
     * @return firstResultを返します。
     */
    public int getFirstResult() {
        return firstResult;
    }

    /**
     * @param firstResult
     *            firstResultを設定します。
     */
    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    /**
     * @return maxResultsを返します。
     */
    public int getMaxResults() {
        return maxResults;
    }

    /**
     * @param maxResults
     *            maxResultsを設定します。
     */
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

}
