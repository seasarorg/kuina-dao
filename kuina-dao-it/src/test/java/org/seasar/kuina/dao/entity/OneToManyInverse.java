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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

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

    @OneToMany(mappedBy = "oneToManyInverse")
    private Collection<ManyToOneOwner> manyToOneOwner;

    public OneToManyInverse() {
    }

	/**
	 * @return idを返します。
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id idを設定します。
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return manyToOneOwnerを返します。
	 */
	public Collection<ManyToOneOwner> getManyToOneOwner() {
		return manyToOneOwner;
	}

	/**
	 * @param manyToOneOwner manyToOneOwnerを設定します。
	 */
	public void setManyToOneOwner(Collection<ManyToOneOwner> manyToOneOwner) {
		this.manyToOneOwner = manyToOneOwner;
	}

	/**
	 * @return nameを返します。
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name nameを設定します。
	 */
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
