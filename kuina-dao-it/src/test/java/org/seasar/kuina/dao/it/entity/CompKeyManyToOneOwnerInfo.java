/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.kuina.dao.it.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author nakamura
 */
@Embeddable
public class CompKeyManyToOneOwnerInfo {

    private Integer height;

    private Integer weight;

    private String email;

    private Integer hireFiscalYear;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    private java.sql.Time birthTime;

    private java.sql.Timestamp birthTimestamp;

    private java.sql.Date employmentDate;

    @Temporal(TemporalType.DATE)
    private Calendar weddingDay;

    private String bloodType;

    private EmployeeStatus employeeStatus;

    @Enumerated(EnumType.STRING)
    private SalaryRate salaryRate;

    private Boolean retired;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public java.sql.Time getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(java.sql.Time birthTime) {
        this.birthTime = birthTime;
    }

    public java.sql.Timestamp getBirthTimestamp() {
        return birthTimestamp;
    }

    public void setBirthTimestamp(java.sql.Timestamp birthTimestamp) {
        this.birthTimestamp = birthTimestamp;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public java.sql.Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(java.sql.Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getHireFiscalYear() {
        return hireFiscalYear;
    }

    public void setHireFiscalYear(Integer hireFiscalYear) {
        this.hireFiscalYear = hireFiscalYear;
    }

    public Boolean getRetired() {
        return retired;
    }

    public void setRetired(Boolean retired) {
        this.retired = retired;
    }

    public SalaryRate getSalaryRate() {
        return salaryRate;
    }

    public void setSalaryRate(SalaryRate salaryRate) {
        this.salaryRate = salaryRate;
    }

    public Calendar getWeddingDay() {
        return weddingDay;
    }

    public void setWeddingDay(Calendar weddingDay) {
        this.weddingDay = weddingDay;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

}
