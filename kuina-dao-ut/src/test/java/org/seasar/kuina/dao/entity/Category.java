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
package org.seasar.kuina.dao.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 * 
 * @author koichik
 */
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Category_Id_Generator")
    @SequenceGenerator(name = "Category_Id_Generator", sequenceName = "Category_Id_Sequence")
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "category")
    private Collection<Product> products;

    public Category() {
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

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Category))
            return false;
        Category castOther = (Category) other;
        return this.id == castOther.id;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }
}
