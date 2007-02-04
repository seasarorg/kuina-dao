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

import java.util.List;

import org.seasar.kuina.dao.Distinct;
import org.seasar.kuina.dao.it.entity.OneToManyInverse;
import org.seasar.kuina.dao.it.entity.SalaryRate;

/**
 * 
 * @author nakamura
 */
public interface OneToManyInverseDao {

    List<OneToManyInverse> findByName(String name, String... orderby);

    OneToManyInverse findByManyToOneOwnerName(String manyToOneOwners$name,
            String... orderby);

    @Distinct
    List<OneToManyInverse> findByOwnerSalaryRateOwnerWeight(
            SalaryRate manyToOneOwners$salaryRate,
            int manyToOneOwners$weight_GT, String... orderby);

    @Distinct
    List<OneToManyInverse> findByOwnerSalaryRateSubOwnerWeight(
            SalaryRate manyToOneOwners$salaryRate,
            int subManyToOneOwners$weight_GT, String... orderby);

    @Distinct
    List<OneToManyInverse> findByRelationship(
            String manyToOneOwners$subOneToManyInverse$name, String... orderby);

}