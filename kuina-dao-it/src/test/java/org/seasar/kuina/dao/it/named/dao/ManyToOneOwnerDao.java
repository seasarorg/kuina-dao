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
package org.seasar.kuina.dao.it.named.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;

import org.seasar.kuina.dao.FlushMode;
import org.seasar.kuina.dao.PositionalParameter;
import org.seasar.kuina.dao.QueryName;
import org.seasar.kuina.dao.it.entity.ManyToOneOwner;

/**
 * 
 * @author nakamura
 */
public interface ManyToOneOwnerDao {

    @QueryName("named.dao.ManyToOneOwner.updateRetiredFlagById")
    void updateRetiredFlagById(Integer id, boolean retired);

    @QueryName("named.dao.ManyToOneOwner.removeById")
    void removeById(Integer id);

    @QueryName("named.dao.ManyToOneOwner.findByName")
    List<ManyToOneOwner> findByName(String ownerName);

    @QueryName("named.dao.ManyToOneOwner.findByName")
    @FlushMode(FlushModeType.COMMIT)
    List<ManyToOneOwner> findByNameNoFlush(String ownerName);

    @PositionalParameter
    @QueryName("named.dao.ManyToOneOwner.findByBirthday")
    List<ManyToOneOwner> findByBirthday(Date date);

    @QueryName("named.dao.ManyToOneOwner.findByOneToManyInverseName")
    List<ManyToOneOwner> findByOneToManyInverseName(String name);

    @QueryName("named.dao.ManyToOneOwner.getName")
    String getName(Integer id);

    @QueryName("named.dao.ManyToOneOwner.getCount")
    Number getCount();

    @PositionalParameter
    @QueryName("named.dao.ManyToOneOwner.getCountByBloodType")
    Number getCountByBloodType(String bloodType);

    @PositionalParameter
    @QueryName("named.dao.ManyToOneOwner.getCountByBloodType")
    @FlushMode(FlushModeType.COMMIT)
    Number getCountByBloodTypeNoFlush(String bloodType);

    List<ManyToOneOwner> findByWeigth(Integer weight);

    List<ManyToOneOwner> findByHeigth(Integer height);

}
