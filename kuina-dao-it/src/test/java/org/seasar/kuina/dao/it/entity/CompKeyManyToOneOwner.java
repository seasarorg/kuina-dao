/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

/**
 * 
 * @author nakamura
 */
@Entity
public class CompKeyManyToOneOwner {

    @EmbeddedId
    private CompKeyManyToOneOwnerId id;

    private String name;

    @Version
    @SuppressWarnings("unused")
    private Integer version;

    @Embedded
    private CompKeyManyToOneOwnerInfo info;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns( {
        @JoinColumn(name = "COMPKEYONETOMANYINVERSE_PK1", referencedColumnName = "PK1"),
        @JoinColumn(name = "COMPKEYONETOMANYINVERSE_PK2", referencedColumnName = "PK2") })
    private CompKeyOneToManyInverse compKeyOneToManyInverse;

    public CompKeyManyToOneOwnerId getId() {
        return id;
    }

    public void setId(CompKeyManyToOneOwnerId id) {
        this.id = id;
    }

    public CompKeyManyToOneOwnerInfo getInfo() {
        return info;
    }

    public void setInfo(CompKeyManyToOneOwnerInfo info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public CompKeyOneToManyInverse getCompKeyOneToManyInverse() {
        return compKeyOneToManyInverse;
    }

    public void setCompKeyOneToManyInverse(
            CompKeyOneToManyInverse compKeyOneToManyInverse) {
        this.compKeyOneToManyInverse = compKeyOneToManyInverse;
    }

}
