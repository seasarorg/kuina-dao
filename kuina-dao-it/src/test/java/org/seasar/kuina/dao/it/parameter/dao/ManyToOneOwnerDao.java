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
import org.seasar.kuina.dao.it.entity.EmployeeStatus;
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;
import org.seasar.kuina.dao.it.entity.OneToManyInverse;
import org.seasar.kuina.dao.it.entity.SalaryRate;

/**
 * 
 * @author nakamura
 */
public interface ManyToOneOwnerDao {

    List<ManyToOneOwner> findByBloodType(String[] bloodType_IN,
            String... orderby);

    List<ManyToOneOwner> findByNameBloodType(String name, String bloodType,
            String... orderby);

    List<ManyToOneOwner> findByBloodTypeOrderbyHeightWeight(String bloodType,
            String... orderby);

    List<ManyToOneOwner> findByBloodTypePaging(String bloodType,
            int firstResult, int maxResults, String... orderby);

    List<ManyToOneOwner> findByBirthday(Date birthday_GE, String... orderby);

    List<ManyToOneOwner> findByWeddingDay(Calendar weddingDay,
            String... orderby);

    List<ManyToOneOwner> findByEmploymentDate(java.sql.Date employmentDate,
            String... orderby);

    List<ManyToOneOwner> findByBirthTime(java.sql.Time birthTime,
            String... orderby);

    List<ManyToOneOwner> findByBirthTimestamp(
            java.sql.Timestamp birthTimestamp, String... orderby);

    List<ManyToOneOwner> findByName(String name_CONTAINS, String... orderby);

    List<ManyToOneOwner> findByHireFiscalYear(boolean hireFiscalYear_IS_NULL,
            String... orderby);

    List<ManyToOneOwner> findByEmployeeStatus(EmployeeStatus employeeStatus,
            String... orderby);

    List<ManyToOneOwner> findBySalaryRate(SalaryRate salaryRate,
            String... orderby);

    List<ManyToOneOwner> findByRetiredFlag(Boolean retired, String... orderby);

    List<ManyToOneOwner> findByOneToManyInverse(
            OneToManyInverse oneToManyInverse, String... orderby);

    List<ManyToOneOwner> findByOneToManyInverseName(
            String oneToManyInverse$name, String... orderby);

    List<ManyToOneOwner> findByRelationship(
            String oneToManyInverse$subManyToOneOwners$name, String... orderby);

    @FlushMode(FlushModeType.COMMIT)
    List<ManyToOneOwner> findByBloodTypeNoFlush(String[] bloodType_IN,
            String... orderby);

}
