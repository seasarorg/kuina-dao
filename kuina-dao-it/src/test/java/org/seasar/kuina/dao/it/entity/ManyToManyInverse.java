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
package org.seasar.kuina.dao.it.entity;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

/**
 * 
 * @author nakamura
 */
@Entity
public class ManyToManyInverse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ManyToManyInverse_Id_Generator")
    @SequenceGenerator(name = "ManyToManyInverse_Id_Generator", sequenceName = "ManyToManyInverse_Id_Sequence")
    private Integer id;

    private String name;

    @SuppressWarnings("unused")
    @Version
    private Integer version;

    @ManyToMany(mappedBy = "manyToManyInverses")
    private Collection<ManyToManyOwner> manyToManyOwners;

    public ManyToManyInverse() {
    }

    public Integer getId() {
        return id;
    }

    public Collection<ManyToManyOwner> getManyToManyOwners() {
        return manyToManyOwners;
    }

    public void setManyToManyOwners(Collection<ManyToManyOwner> manyToManyOwners) {
        this.manyToManyOwners = manyToManyOwners;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addManyToManyOwner(ManyToManyOwner owner) {
        if (manyToManyOwners == null) {
            manyToManyOwners = new HashSet<ManyToManyOwner>();
        }
        manyToManyOwners.add(owner);
    }

}
