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
package org.seasar.kuina.dao.entity;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * 
 * @author koichik
 */
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Employee_Id_Generator")
    @SequenceGenerator(name = "Employee_Id_Generator", sequenceName = "Employee_Id_Sequence")
    private Integer id;

    private String name;

    private Integer height;

    private Integer weight;

    private String email;

    private Integer hireFiscalYear;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    private String bloodType;

    @SuppressWarnings("unused")
    @Version
    private Integer version;

    @OneToMany(mappedBy = "employee")
    private Collection<BelongTo> belongTo;

    @OneToMany(mappedBy = "employee")
    private Collection<Salary> salary;

    public Employee() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getHireFiscalYear() {
        return hireFiscalYear;
    }

    public void setHireFiscalYear(Integer hireFiscalYear) {
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

    public void addDepartment(Department department) {
        if (belongTo == null) {
            this.belongTo = new HashSet<BelongTo>();
        }
        BelongTo belongTo = new BelongTo();
        belongTo.setDepartment(department);
        this.belongTo.add(belongTo);
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
        return id == null ? 0 : id;
    }
}
