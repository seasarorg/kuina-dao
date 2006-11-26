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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

/**
 * 
 * @author nakamura
 */
@Entity
public class OneToManyInverse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OneToManyInverse_Id_Generator")
    @SequenceGenerator(name = "OneToManyInverse_Id_Generator", sequenceName = "OneToManyInverse_Id_Sequence")
    private Integer id;

    private String name;

    @SuppressWarnings("unused")
    @Version
    private Integer version;
    
    @OneToMany(mappedBy = "oneToManyInverse")
    private Collection<ManyToOneOwner> manyToOneOwners;

    public OneToManyInverse() {
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Collection<ManyToOneOwner> getManyToOneOwners() {
		return manyToOneOwners;
	}

	public void setManyToOneOwners(Collection<ManyToOneOwner> manyToOneOwners) {
		this.manyToOneOwners = manyToOneOwners;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof OneToManyInverse))
            return false;
        OneToManyInverse castOther = (OneToManyInverse) other;
        return this.id == castOther.id;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }
}