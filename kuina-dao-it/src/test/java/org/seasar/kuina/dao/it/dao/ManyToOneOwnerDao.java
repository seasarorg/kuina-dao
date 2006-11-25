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
package org.seasar.kuina.dao.it.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import org.seasar.kuina.dao.TemporalSpec;
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;

/**
 * 
 * @author nakamura
 */
public interface ManyToOneOwnerDao {

    // Basic
    ManyToOneOwner find(int id);

    ManyToOneOwner get(int id);

    void persist(ManyToOneOwner owner);

    void remove(ManyToOneOwner owner);

    boolean contains(ManyToOneOwner owner);

    void refresh(ManyToOneOwner owner);

    ManyToOneOwner merge(ManyToOneOwner owner);

    void readLock(ManyToOneOwner owner);

    void writeLock(ManyToOneOwner owner);

    // Find All Query
    List<ManyToOneOwner> findAll();

    // Query by Example
    List<ManyToOneOwner> findByExample(ManyToOneOwner owner);

    List<ManyToOneOwner> findByExample2(ManyToOneOwner owner, String[] orderby);

    List<ManyToOneOwner> findByExample3(ManyToOneOwner owner, int firstResult,
            int maxResults);

    // Query by DTO

    // Query by Parameter
    List<ManyToOneOwner> findByBloodType(String... bloodType_IN);

    List<ManyToOneOwner> findByNameBloodType(String name, String bloodType);

    List<ManyToOneOwner> findByBloodTypeOrderbyHeightWeight(String bloodType,
            String[] orderby);

    List<ManyToOneOwner> findByBloodTypePaging(String bloodType,
            int firstResult, int maxResults);

    List<ManyToOneOwner> findByInverseName(String oneToManyInverse$name);

    List<ManyToOneOwner> findByBirthday(@TemporalSpec(TemporalType.DATE)
    Date birthday_GE);

    List<ManyToOneOwner> findByName(String name_CONTAINS);

    List<ManyToOneOwner> findByHireFiscalYear(boolean hireFiscalYear_IS_NULL);

    // Named Query

    // SQL Query

    // Conditional Query
}
