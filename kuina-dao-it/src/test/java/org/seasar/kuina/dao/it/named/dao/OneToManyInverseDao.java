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
package org.seasar.kuina.dao.it.named.dao;

import java.util.List;

import org.seasar.kuina.dao.PositionalParameter;
import org.seasar.kuina.dao.QueryName;
import org.seasar.kuina.dao.it.entity.OneToManyInverse;

/**
 * 
 * @author nakamura
 */
public interface OneToManyInverseDao {

    @PositionalParameter
    @QueryName("named.dao_OneToManyInverse.updateNameById")
    void updateNameById(Integer id, String name);

    @QueryName("named.dao_OneToManyInverse.findByName")
    List<OneToManyInverse> findByName(String name);

}