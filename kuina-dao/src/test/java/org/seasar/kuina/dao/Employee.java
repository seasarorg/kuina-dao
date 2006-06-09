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

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * 
 * @author koichik
 */
@Entity
public class Employee {

    @Id
    private int id;

    private String name;

    private int height;

    private int weight;

    private String email;

    private int hireFiscalYear;

    private Date birthday;

    private String bloodType;

    @OneToMany(mappedBy = "employee")
    private Collection<BelongTo> belongTo;

    @OneToMany(mappedBy="employee")
    private Collection<Salary> salary;
    
    public Employee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHireFiscalYear() {
        return hireFiscalYear;
    }

    public void setHireFiscalYear(int hireFiscalYear) {
        this.hireFiscalYear = hireFiscalYear;
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

    public Collection<BelongTo> getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(Collection<BelongTo> belongTo) {
        this.belongTo = belongTo;
    }

    public Collection<Salary> getSalary() {
        return salary;
    }

    public void setSalary(Collection<Salary> salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Employee))
            return false;
        Employee castOther = (Employee) other;
        return this.id == castOther.id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}
