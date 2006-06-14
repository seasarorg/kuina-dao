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
package org.seasar.kuina.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import org.seasar.kuina.dao.annotation.PositionalParameter;
import org.seasar.kuina.dao.annotation.TargetEntity;
import org.seasar.kuina.dao.annotation.TemporalSpec;

/**
 * 
 * @author koichik
 */
@TargetEntity(Employee.class)
public interface EmployeeDao {

    List<Employee> findByNameAndOrBloodType(String name, String bloodType);

    List<Employee> findByName(String name);

    List<Employee> findByDepartmentName(String name, int firstResult,
            int maxResults);

    @PositionalParameter
    List<Employee> findByBirthday(@TemporalSpec(TemporalType.DATE)
    Date birthday);

    String getName(int id);

    Employee find(int id);

    Employee get(int id);

    void persist(Employee employee);

    void remove(Employee employee);

    boolean contains(Employee employee);

    void refresh(Employee employee);

    Employee merge(Employee employee);

    void readLock(Employee employee);

    void writeLock(Employee employee);

}
