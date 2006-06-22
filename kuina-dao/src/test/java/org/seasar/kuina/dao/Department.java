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
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * 
 * @author koichik
 */
@Entity
public class Department {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "department")
    private Collection<BelongTo> belongTo;

    public Department() {
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

    public Collection<BelongTo> getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(Collection<BelongTo> belongTo) {
        this.belongTo = belongTo;
    }

    public void addBelongTo(BelongTo belongTo) {
        if (belongTo == null) {
            this.belongTo = new HashSet<BelongTo>();
        }
        this.belongTo.add(belongTo);
    }

    public void addEmployee(Employee employee) {
        BelongTo belongTo = new BelongTo();
        belongTo.setEmployee(employee);
        addBelongTo(belongTo);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Department))
            return false;
        Department castOther = (Department) other;
        return this.id == castOther.id;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }
}
