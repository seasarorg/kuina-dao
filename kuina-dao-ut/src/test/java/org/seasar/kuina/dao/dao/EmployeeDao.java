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
package org.seasar.kuina.dao.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import org.seasar.kuina.dao.PositionalParameter;
import org.seasar.kuina.dao.TargetEntity;
import org.seasar.kuina.dao.TemporalSpec;
import org.seasar.kuina.dao.criteria.grammar.ConditionalExpression;
import org.seasar.kuina.dao.dto.EmployeeDto;
import org.seasar.kuina.dao.entity.Employee;

/**
 * 
 * @author koichik
 */
public interface EmployeeDao {

    List<Employee> findAll();

    List<Employee> findByExample(Employee employee);

    List<Employee> findByExample2(Employee employee, String[] orderby);

    List<Employee> findByExample3(Employee employee, int firstResult,
            int maxResults);

    List<Employee> findByName(String ename);

    List<Employee> findByBloodType(String... bloodType_IN);

    List<Employee> findByNameOrBloodType(String name, String bloodType);

    List<Employee> findByBloodTypeOrderbyHeightWeight(String bloodType,
            String[] orderby);

    List<Employee> findByBloodTypePaging(String bloodType, int firstResult,
            int maxResults);

    List<Employee> findCondition(ConditionalExpression... conditions);

    List<Employee> findByDepartmentName(String name, int firstResult,
            int maxResults);

    @PositionalParameter
    List<Employee> findByBirthday(@TemporalSpec(TemporalType.DATE)
    Date birthday);

    Integer getCount();

    @TargetEntity(Employee.class)
    @PositionalParameter
    Integer getCountByBloodType(String bloodType);

    List<Employee> findByDto(final EmployeeDto dto);

    Employee getEmployee(Integer id, String name);

    @TargetEntity(Employee.class)
    String getName(int id);

    Employee find(int id);

    Employee get(int id);

    void persist(Employee employee);

    void remove(Employee employee);

    @TargetEntity(Employee.class)
    int removeById(int id);

    boolean contains(Employee employee);

    void refresh(Employee employee);

    Employee merge(Employee employee);

    void readLock(Employee employee);

    void writeLock(Employee employee);

}
