/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import org.seasar.kuina.dao.FetchJoin;
import org.seasar.kuina.dao.JoinSpec;
import org.seasar.kuina.dao.Orderby;
import org.seasar.kuina.dao.it.entity.OneToManyInverse;
import org.seasar.kuina.dao.it.entity.SalaryRate;

/**
 * 
 * @author nakamura
 */
public interface OneToManyInverseDao {

    @Orderby("id")
    List<OneToManyInverse> findByName(String name);

    @Orderby("id")
    OneToManyInverse findByManyToOneOwnerName(String manyToOneOwners$name);

    @Distinct
    @Orderby("id")
    List<OneToManyInverse> findByOwnerSalaryRateOwnerWeight(
            SalaryRate manyToOneOwners$salaryRate, int manyToOneOwners$weight_GT);

    @Distinct
    @Orderby("id")
    List<OneToManyInverse> findByOwnerSalaryRateSubOwnerWeight(
            SalaryRate manyToOneOwners$salaryRate,
            int subManyToOneOwners$weight_GT);

    @Distinct
    @Orderby("id")
    List<OneToManyInverse> findByRelationship(
            String manyToOneOwners$subOneToManyInverse$name);

    @Distinct
    @Orderby("id")
    @FetchJoin(joinSpec = JoinSpec.LEFT_OUTER_JOIN, value = "manyToOneOwners")
    List<OneToManyInverse> findByNameFetchJoin(String name);

}