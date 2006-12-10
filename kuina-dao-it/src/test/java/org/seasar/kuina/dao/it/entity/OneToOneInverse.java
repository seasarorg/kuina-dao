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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

/**
 * 
 * @author nakamura
 */
@Entity
public class OneToOneInverse {

    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "OneToOneInverse_Id_Generator")
//    @TableGenerator(name = "OneToOneInverse_Id_Generator", pkColumnValue = "OneToOneInverse_Id_Sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OneToOneInverse_Id_Generator")
    @SequenceGenerator(name = "OneToOneInverse_Id_Generator", sequenceName = "OneToOneInverse_Id_Sequence")
    private Integer id;

    private String name;

    @SuppressWarnings("unused")
    @Version
    private Integer version;
    
    @OneToOne(mappedBy = "oneToOneInverse")
    private OneToOneOwner oneToOneOwner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OneToOneOwner getOneToOneOwner() {
        return oneToOneOwner;
    }

    public void setOneToOneOwner(OneToOneOwner oneToOneOwner) {
        this.oneToOneOwner = oneToOneOwner;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof OneToOneInverse))
            return false;
        OneToOneInverse castOther = (OneToOneInverse) other;
        return this.id == castOther.id;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }

}
