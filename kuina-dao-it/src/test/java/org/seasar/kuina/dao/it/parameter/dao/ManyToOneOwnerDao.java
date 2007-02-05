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
package org.seasar.kuina.dao.it.parameter.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;

import org.seasar.kuina.dao.FlushMode;
import org.seasar.kuina.dao.Orderby;
import org.seasar.kuina.dao.it.entity.EmployeeStatus;
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;
import org.seasar.kuina.dao.it.entity.OneToManyInverse;
import org.seasar.kuina.dao.it.entity.SalaryRate;

/**
 * 
 * @author nakamura
 */
public interface ManyToOneOwnerDao {

    @Orderby("id")
    List<ManyToOneOwner> findByBloodType(String... bloodType_IN);

    @Orderby("id")
    List<ManyToOneOwner> findByNameBloodType(String name, String bloodType);

    List<ManyToOneOwner> findByBloodTypeOrderbyHeightWeight(String bloodType,
            String... orderby);

    @Orderby("id")
    List<ManyToOneOwner> findByBloodTypePaging(String bloodType,
            int firstResult, int maxResults);

    @Orderby("id")
    List<ManyToOneOwner> findByBirthday(Date birthday_GE);

    @Orderby("id")
    List<ManyToOneOwner> findByWeddingDay(Calendar weddingDay);

    @Orderby("id")
    List<ManyToOneOwner> findByEmploymentDate(java.sql.Date employmentDate);

    @Orderby("id")
    List<ManyToOneOwner> findByBirthTime(java.sql.Time birthTime);

    @Orderby("id")
    List<ManyToOneOwner> findByBirthTimestamp(java.sql.Timestamp birthTimestamp);

    @Orderby("id")
    List<ManyToOneOwner> findByName(String name_CONTAINS);

    @Orderby("id")
    List<ManyToOneOwner> findByHireFiscalYear(boolean hireFiscalYear_IS_NULL);

    @Orderby("id")
    List<ManyToOneOwner> findByEmployeeStatus(EmployeeStatus employeeStatus);

    @Orderby("id")
    List<ManyToOneOwner> findBySalaryRate(SalaryRate salaryRate);

    @Orderby("id")
    List<ManyToOneOwner> findByRetiredFlag(Boolean retired);

    @Orderby("id")
    List<ManyToOneOwner> findByOneToManyInverse(
            OneToManyInverse oneToManyInverse);

    @Orderby("id")
    List<ManyToOneOwner> findByOneToManyInverseName(String oneToManyInverse$name);

    @Orderby("id")
    List<ManyToOneOwner> findByRelationship(
            String oneToManyInverse$subManyToOneOwners$name);

    @Orderby("id")
    @FlushMode(FlushModeType.COMMIT)
    List<ManyToOneOwner> findByBloodTypeNoFlush(String... bloodType_IN);

}
