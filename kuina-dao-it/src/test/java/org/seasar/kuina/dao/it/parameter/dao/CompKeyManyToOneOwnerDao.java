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
package org.seasar.kuina.dao.it.parameter.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.seasar.kuina.dao.Orderby;
import org.seasar.kuina.dao.it.entity.CompKeyManyToOneOwner;
import org.seasar.kuina.dao.it.entity.CompKeyOneToManyInverse;
import org.seasar.kuina.dao.it.entity.EmployeeStatus;
import org.seasar.kuina.dao.it.entity.SalaryRate;

/**
 * 
 * @author nakamura
 */
public interface CompKeyManyToOneOwnerDao {

    @Orderby("id.pk1")
    List<CompKeyManyToOneOwner> findByPk2(Date id$pk2);

    @Orderby("id.pk1")
    List<CompKeyManyToOneOwner> findByBloodType(String... info$bloodType_IN);

    @Orderby("id.pk1")
    List<CompKeyManyToOneOwner> findByNameBloodType(String name,
            String info$bloodType);

    List<CompKeyManyToOneOwner> findByBloodTypeOrderbyHeightWeight(
            String info$bloodType, String... orderby);

    @Orderby("id.pk1")
    List<CompKeyManyToOneOwner> findByBloodTypePaging(String info$bloodType,
            int firstResult, int maxResults);

    @Orderby("id.pk1")
    List<CompKeyManyToOneOwner> findByBirthday(Date info$birthday_GE);

    @Orderby("id.pk1")
    List<CompKeyManyToOneOwner> findByWeddingDay(Calendar info$weddingDay);

    @Orderby("id.pk1")
    List<CompKeyManyToOneOwner> findByEmploymentDate(
            java.sql.Date info$employmentDate);

    @Orderby("id.pk1")
    List<CompKeyManyToOneOwner> findByBirthTime(java.sql.Time info$birthTime);

    @Orderby("id.pk1")
    List<CompKeyManyToOneOwner> findByBirthTimestamp(
            java.sql.Timestamp info$birthTimestamp);

    @Orderby("id.pk1")
    List<CompKeyManyToOneOwner> findByName(String name_CONTAINS);

    @Orderby("id.pk1")
    List<CompKeyManyToOneOwner> findByHireFiscalYear(
            boolean info$hireFiscalYear_IS_NULL);

    @Orderby("id.pk1")
    List<CompKeyManyToOneOwner> findByEmployeeStatus(
            EmployeeStatus info$employeeStatus);

    @Orderby("id.pk1")
    List<CompKeyManyToOneOwner> findBySalaryRate(SalaryRate info$salaryRate);

    @Orderby("id.pk1")
    List<CompKeyManyToOneOwner> findByRetiredFlag(Boolean info$retired);

    @Orderby("id.pk1")
    List<CompKeyManyToOneOwner> findByCompKeyOneToManyInverse(
            CompKeyOneToManyInverse compKeyOneToManyInverse);

    @Orderby("id.pk1")
    List<CompKeyManyToOneOwner> findByCompKeyOneToManyInverseName(
            String compKeyOneToManyInverse$name);

}
