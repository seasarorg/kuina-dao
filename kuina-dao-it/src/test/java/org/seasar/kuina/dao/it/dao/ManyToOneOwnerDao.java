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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.seasar.kuina.dao.it.entity.EmployeeStatus;
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;
import org.seasar.kuina.dao.it.entity.OneToManyInverse;
import org.seasar.kuina.dao.it.entity.SalaryRate;

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

    List<ManyToOneOwner> findByExampleOrderby(ManyToOneOwner owner,
            String[] orderby);

    List<ManyToOneOwner> findByExamplePaging(ManyToOneOwner owner,
            int firstResult, int maxResults);

    // Query by DTO

    // Query by Parameter
    List<ManyToOneOwner> findByBloodType(String... bloodType_IN);

    List<ManyToOneOwner> findByNameBloodType(String name, String bloodType);

    List<ManyToOneOwner> findByBloodTypeOrderbyHeightWeight(String bloodType,
            String[] orderby);

    List<ManyToOneOwner> findByBloodTypePaging(String bloodType,
            int firstResult, int maxResults);

    List<ManyToOneOwner> findByInverseName(String oneToManyInverse$name);

    List<ManyToOneOwner> findByBirthday(Date birthday_GE);

    List<ManyToOneOwner> findByWeddingDay(Calendar weddingDay);

    List<ManyToOneOwner> findByEmploymentDate(java.sql.Date employmentDate);

    List<ManyToOneOwner> findByBirthTime(java.sql.Time birthTime);

    List<ManyToOneOwner> findByBirthTimestamp(java.sql.Timestamp birthTimestamp);

    List<ManyToOneOwner> findByName(String name_CONTAINS);

    List<ManyToOneOwner> findByHireFiscalYear(boolean hireFiscalYear_IS_NULL);

    List<ManyToOneOwner> findByEmployeeStatus(EmployeeStatus employeeStatus);

    List<ManyToOneOwner> findBySalaryRate(SalaryRate salaryRate);

    List<ManyToOneOwner> findByOneToManyInverse(
            OneToManyInverse oneToManyInverse);

    List<ManyToOneOwner> findByRetiredFlag(Boolean retired);

    // Named Query

    // SQL Query

    // Conditional Query
}
