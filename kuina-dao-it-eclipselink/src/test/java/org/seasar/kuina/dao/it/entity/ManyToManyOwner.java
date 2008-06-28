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
package org.seasar.kuina.dao.it.entity;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * 
 * @author nakamura
 */
@Entity
public class ManyToManyOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ManyToManyOwner_Id_Generator")
    @SequenceGenerator(name = "ManyToManyOwner_Id_Generator", sequenceName = "ManyToManyOwner_Id_Sequence")
    private Integer id;

    private String name;

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

    @SuppressWarnings("unused")
    @Version
    private Integer version;

    @ManyToMany
    private Collection<ManyToManyInverse> manyToManyInverses;

    public ManyToManyOwner() {
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public Collection<ManyToManyInverse> getManyToManyInverses() {
        return manyToManyInverses;
    }

    public void setManyToManyInverses(
            Collection<ManyToManyInverse> manyToManyInverses) {
        this.manyToManyInverses = manyToManyInverses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public SalaryRate getSalaryRate() {
        return salaryRate;
    }

    public void setSalaryRate(SalaryRate salaryRate) {
        this.salaryRate = salaryRate;
    }

    public Boolean isRetired() {
        return retired;
    }

    public void setRetired(Boolean retired) {
        this.retired = retired;
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

    public java.sql.Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(java.sql.Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public Calendar getWeddingDay() {
        return weddingDay;
    }

    public void setWeddingDay(Calendar weddingDay) {
        this.weddingDay = weddingDay;
    }

    public void addManyToManyInverse(ManyToManyInverse inverse) {
        if (manyToManyInverses == null) {
            manyToManyInverses = new HashSet<ManyToManyInverse>();
        }
        manyToManyInverses.add(inverse);
        inverse.addManyToManyOwner(this);
    }

}
