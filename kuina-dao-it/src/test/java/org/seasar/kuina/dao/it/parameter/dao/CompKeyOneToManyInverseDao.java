/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import java.util.Date;
import java.util.List;

import org.seasar.kuina.dao.Distinct;
import org.seasar.kuina.dao.Orderby;
import org.seasar.kuina.dao.it.entity.CompKeyOneToManyInverse;
import org.seasar.kuina.dao.it.entity.SalaryRate;

/**
 * 
 * @author nakamura
 */
public interface CompKeyOneToManyInverseDao {

    @Orderby("id.pk1")
    List<CompKeyOneToManyInverse> findByPk2(Date id$pk2);

    @Orderby("id.pk1")
    List<CompKeyOneToManyInverse> findByName(String name);

    @Orderby("id.pk1")
    CompKeyOneToManyInverse findByCompKeyManyToOneOwnerName(
            String compKeyManyToOneOwners$name);

    @Distinct
    @Orderby("id.pk1")
    List<CompKeyOneToManyInverse> findByOwnerSalaryRateOwnerWeight(
            SalaryRate compKeyManyToOneOwners$info$salaryRate,
            int compKeyManyToOneOwners$info$weight_GT);

}