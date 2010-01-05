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

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 * 
 * @author nakamura
 */
@Entity
public class CompKeyOneToManyInverse {

    @EmbeddedId
    private CompKeyOneToManyInverseId id;

    private String name;

    @Version
    private Integer version;

    @OneToMany(mappedBy = "compKeyOneToManyInverse")
    private Collection<CompKeyManyToOneOwner> compKeyManyToOneOwners;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompKeyOneToManyInverseId getId() {
        return id;
    }

    public void setId(CompKeyOneToManyInverseId id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Collection<CompKeyManyToOneOwner> getCompKeyManyToOneOwners() {
        return compKeyManyToOneOwners;
    }

    public void setCompKeyManyToOneOwners(
            Collection<CompKeyManyToOneOwner> compKeyManyToOneOwners) {
        this.compKeyManyToOneOwners = compKeyManyToOneOwners;
    }

    public void addCompKeyManyToOneOwner(CompKeyManyToOneOwner owner) {
        if (compKeyManyToOneOwners == null) {
            compKeyManyToOneOwners = new HashSet<CompKeyManyToOneOwner>();
        }
        compKeyManyToOneOwners.add(owner);
        owner.setCompKeyOneToManyInverse(this);
    }
}
