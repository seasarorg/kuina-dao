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
package org.seasar.kuina.dao.it.sql.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.FlushModeType;

import org.seasar.kuina.dao.FlushMode;
import org.seasar.kuina.dao.it.dto.EmpDto;

/**
 * 
 * @author nakamura
 */
public interface ManyToOneOwnerDao {

    EmpDto findById(Integer id);

    @FlushMode(FlushModeType.AUTO)
    EmpDto findByIdWithAutoFlushMode(Integer id);

    @FlushMode(FlushModeType.COMMIT)
    EmpDto findByIdWithCommitFlushMode(Integer id);

    List<EmpDto> findAll();

    List<Map<String, Object>> findAllAsMap();

    int deleteAll();
}
