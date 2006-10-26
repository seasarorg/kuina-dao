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
package org.seasar.kuina.dao.interceptor;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.kuina.dao.dao.EmpDao;
import org.seasar.kuina.dao.dao.EmployeeDao;
import org.seasar.kuina.dao.dao.ProductDao;
import org.seasar.kuina.dao.dto.EmpDto;
import org.seasar.kuina.dao.dto.EmployeeDto;
import org.seasar.kuina.dao.entity.BelongTo;
import org.seasar.kuina.dao.entity.Category;
import org.seasar.kuina.dao.entity.Department;
import org.seasar.kuina.dao.entity.Employee;
import org.seasar.kuina.dao.entity.Product;

import static org.seasar.kuina.dao.criteria.CriteriaOperations.between;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.gt;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.lt;
import static org.seasar.kuina.dao.criteria.CriteriaOperations.or;

/**
 * @author koichik
 */
public class KuinaDaoInterceptorTest extends S2TestCase {

    private EntityManager em;

    private EmployeeDao employeeDao;

    private ProductDao productDao;

    private EmpDao empDao;

    public KuinaDaoInterceptorTest() {
    }

    public KuinaDaoInterceptorTest(String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("entityManager.dicon");
        include("kuina-dao.dicon");
    }

    public void testFindAllTx() throws Exception {
        List<Employee> list = employeeDao.findAll();
        assertNotNull(list);
        assertEquals(30, list.size());
    }

    public void testFindByExampleTx() throws Exception {
        Employee emp = new Employee();
        emp.setName("シマゴロー");
        List<Employee> list = employeeDao.findByExample(emp);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("シマゴロー", list.get(0).getName());

        emp = new Employee();
        emp.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("1953-10-01"));
        list = employeeDao.findByExample(emp);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("シマゴロー", list.get(0).getName());

        Category category = new Category();
        category.setName("魚");
        Product product = new Product();
        product.setCategory(category);
        List<Product> products = productDao.findByExample(product);
        assertNotNull(products);
        assertEquals(5, products.size());
        assertEquals("まぐろ", products.get(0).getName());
        assertEquals("金魚", products.get(1).getName());
        assertEquals("ぶり", products.get(2).getName());
        assertEquals("あじ", products.get(3).getName());
        assertEquals("あなご", products.get(4).getName());

        Department dept = new Department();
        dept.setName("営業");
        BelongTo belongTo = new BelongTo();
        belongTo.setDepartment(dept);
        emp = new Employee();
        Set<BelongTo> set = new HashSet<BelongTo>();
        set.add(belongTo);
        emp.setBelongTo(set);
        list = employeeDao.findByExample(emp);
        assertNotNull(list);
        assertEquals(5, list.size());
        assertEquals("ミチロー", list.get(0).getName());
        assertEquals("サラ", list.get(1).getName());
        assertEquals("みなみ", list.get(2).getName());
        assertEquals("ぱんだ", list.get(3).getName());
        assertEquals("くま", list.get(4).getName());
    }

    public void testFindByExample2Tx() throws Exception {
        Employee emp = new Employee();
        emp.setBloodType("AB");
        List<Employee> list = employeeDao.findByExample2(emp,
                new String[] { "birthday" });
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("マル", list.get(0).getName());
        assertEquals("ラスカル", list.get(1).getName());
        assertEquals("マイケル", list.get(2).getName());

        emp.setBloodType("A");
        list = employeeDao.findByExample2(emp, new String[] { "height",
                "weight" });
        assertNotNull(list);
        assertEquals(11, list.size());
        assertEquals("ローリー", list.get(0).getName());
        assertEquals("サリー", list.get(1).getName());
        assertEquals("ぴよ", list.get(2).getName());
        assertEquals("マー", list.get(3).getName());
        assertEquals("うさぎ", list.get(4).getName());
        assertEquals("サラ", list.get(5).getName());
        assertEquals("シマゴロー", list.get(6).getName());
        assertEquals("モンチー", list.get(7).getName());
        assertEquals("うー太", list.get(8).getName());
        assertEquals("ミチロー", list.get(9).getName());
        assertEquals("クー", list.get(10).getName());
    }

    public void testFindByExample3Tx() throws Exception {
        Employee emp = new Employee();
        emp.setBloodType("A");
        List<Employee> list = employeeDao.findByExample3(emp, -1, -1);
        assertNotNull(list);
        assertEquals(11, list.size());
        assertEquals("シマゴロー", list.get(0).getName());

        list = employeeDao.findByExample3(emp, 1, -1);
        assertNotNull(list);
        assertEquals(10, list.size());
        assertEquals("ミチロー", list.get(0).getName());

        list = employeeDao.findByExample3(emp, 5, 5);
        assertNotNull(list);
        assertEquals(5, list.size());
        assertEquals("ぴよ", list.get(0).getName());
        assertEquals("マー", list.get(1).getName());
        assertEquals("サリー", list.get(2).getName());
        assertEquals("うさぎ", list.get(3).getName());
        assertEquals("うー太", list.get(4).getName());
    }

    public void testFindByDtoTx() throws Exception {
        EmployeeDto dto = new EmployeeDto();
        dto.setName_EQ("シマゴロー");
        List<Employee> list = employeeDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("シマゴロー", list.get(0).getName());

        dto = new EmployeeDto();
        dto.setName_NE("シマゴロー");
        list = employeeDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(29, list.size());
        assertEquals("ゴッチン", list.get(0).getName());

        dto = new EmployeeDto();
        dto.setName_GT("マー");
        dto.setName_LT("モンチー");
        list = employeeDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("ミチロー", list.get(0).getName());
        assertEquals("ミーヤ", list.get(1).getName());

        dto = new EmployeeDto();
        dto.setName_GE("マー");
        dto.setName_LE("モンチー");
        dto.setOrderby("name");
        list = employeeDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(4, list.size());
        assertEquals("マー", list.get(0).getName());
        assertEquals("ミチロー", list.get(1).getName());
        assertEquals("ミーヤ", list.get(2).getName());
        assertEquals("モンチー", list.get(3).getName());

        dto = new EmployeeDto();
        dto.setName_IN("マー", "ミチロー", "モンチー");
        list = employeeDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("ミチロー", list.get(0).getName());
        assertEquals("モンチー", list.get(1).getName());
        assertEquals("マー", list.get(2).getName());

        dto = new EmployeeDto();
        dto.setName_LIKE("%マ%ー");
        list = employeeDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
        assertEquals("マー", list.get(1).getName());

        dto = new EmployeeDto();
        dto.setName_STARTS("ミ");
        list = employeeDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("ミチロー", list.get(0).getName());
        assertEquals("ミーヤ", list.get(1).getName());

        dto = new EmployeeDto();
        dto.setName_ENDS("ロー");
        list = employeeDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
        assertEquals("ミチロー", list.get(1).getName());

        dto = new EmployeeDto();
        dto.setName_CONTAINS("ー");
        dto.setFirstResult(5);
        dto.setMaxResults(5);
        list = employeeDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(5, list.size());
        assertEquals("キュー", list.get(0).getName());
        assertEquals("マー", list.get(1).getName());
        assertEquals("サリー", list.get(2).getName());
        assertEquals("うー太", list.get(3).getName());
        assertEquals("ローリー", list.get(4).getName());

        dto = new EmployeeDto();
        dto.setName_IS_NULL(true);
        list = employeeDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(0, list.size());

        dto = new EmployeeDto();
        dto.setName_IS_NOT_NULL(true);
        list = employeeDao.findByDto(dto);
        assertNotNull(list);
        assertEquals(30, list.size());
    }

    public void testFindByNameTx() throws Exception {
        List<Employee> list = employeeDao.findByName("シマゴロー");
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
    }

    public void testFindByBloodTypeTx() throws Exception {
        List<Employee> list = employeeDao.findByBloodType("B", "AB");
        assertNotNull(list);
        assertEquals(11, list.size());
        assertEquals("ゴッチン", list.get(0).getName());
        assertEquals("マル", list.get(1).getName());
        assertEquals("プリン", list.get(2).getName());
        assertEquals("ぱんだ", list.get(3).getName());
        assertEquals("猫丸", list.get(4).getName());
        assertEquals("にゃん太郎", list.get(5).getName());
        assertEquals("ラスカル", list.get(6).getName());
        assertEquals("太助", list.get(7).getName());
        assertEquals("トントン", list.get(8).getName());
        assertEquals("マイケル", list.get(9).getName());
        assertEquals("ミーヤ", list.get(10).getName());
    }

    public void testFindNameOrBloodTx() throws Exception {
        List<Employee> list = employeeDao.findByNameOrBloodType("シマゴロー", null);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("シマゴロー", list.get(0).getName());

        list = employeeDao.findByNameOrBloodType(null, "AB");
        assertEquals(3, list.size());
        assertEquals("マル", list.get(0).getName());
        assertEquals("ラスカル", list.get(1).getName());
        assertEquals("マイケル", list.get(2).getName());

        list = employeeDao.findByNameOrBloodType("マル", "AB");
        assertEquals(1, list.size());
        assertEquals("マル", list.get(0).getName());
    }

    public void testFindByBloodTypeOrderbyHeightWeightTx() throws Exception {
        List<Employee> list = employeeDao.findByBloodTypeOrderbyHeightWeight(
                "A", new String[] { "height", "weight" });
        assertNotNull(list);
        assertEquals(11, list.size());
        assertEquals("ローリー", list.get(0).getName());
        assertEquals("サリー", list.get(1).getName());
        assertEquals("ぴよ", list.get(2).getName());
        assertEquals("マー", list.get(3).getName());
        assertEquals("うさぎ", list.get(4).getName());
        assertEquals("サラ", list.get(5).getName());
        assertEquals("シマゴロー", list.get(6).getName());
        assertEquals("モンチー", list.get(7).getName());
        assertEquals("うー太", list.get(8).getName());
        assertEquals("ミチロー", list.get(9).getName());
        assertEquals("クー", list.get(10).getName());
    }

    public void testFindByBlooeTypePagingTx() throws Exception {
        List<Employee> list = employeeDao.findByBloodTypePaging("A", 5, 5);
        assertNotNull(list);
        assertEquals(5, list.size());
        assertEquals("ぴよ", list.get(0).getName());
        assertEquals("マー", list.get(1).getName());
        assertEquals("サリー", list.get(2).getName());
        assertEquals("うさぎ", list.get(3).getName());
        assertEquals("うー太", list.get(4).getName());
    }

    public void testFindConditionTx() throws Exception {
        List<Employee> list = employeeDao.findCondition(between("height", 150,
                170), or(lt("weight", 45), gt("weight", 70)));
        assertNotNull(list);
        assertEquals(4, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
        assertEquals("みなみ", list.get(1).getName());
        assertEquals("マー", list.get(2).getName());
        assertEquals("うさぎ", list.get(3).getName());
    }

    public void testFindByDepartmentNameTx() throws Exception {
        List<Employee> list = employeeDao.findByDepartmentName("営業", 0, 2);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("ミチロー", list.get(0).getName());
        assertEquals("サラ", list.get(1).getName());

        list = employeeDao.findByDepartmentName("営業", 2, 2);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals("みなみ", list.get(0).getName());
        assertEquals("ぱんだ", list.get(1).getName());

        list = employeeDao.findByDepartmentName("営業", 4, 2);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("くま", list.get(0).getName());
    }

    public void testFindByBirthdayTx() throws Exception {
        List<Employee> list = employeeDao.findByBirthday(new SimpleDateFormat(
                "yyyy-MM-dd").parse("1953-10-01"));
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("シマゴロー", list.get(0).getName());
    }

    public void testGetCountTx() throws Exception {
        int count = employeeDao.getCount();
        assertEquals(30, count);
    }

    public void testGetCountByBloodTypeTx() throws Exception {
        int count = employeeDao.getCountByBloodType("AB");
        assertEquals(3, count);
    }

    public void testGetEmployeeTx() throws Exception {
        Employee emp = employeeDao.getEmployee(1, "シマゴロー");
        assertNotNull(emp);
        assertEquals("シマゴロー", emp.getName());

        try {
            employeeDao.getEmployee(1, "ゴッチン");
        } catch (NoResultException expected) {
        }
    }

    public void testGetNameTx() throws Exception {
        String name = employeeDao.getName(1);
        assertEquals("シマゴロー", name);

        try {
            employeeDao.getName(1234567890);
        } catch (NoResultException expected) {
        }
    }

    public void testContainsTx() throws Exception {
        Employee emp = employeeDao.find(1);
        assertTrue(employeeDao.contains(emp));
    }

    public void testFindEmpsTx() throws Exception {
        List<EmpDto> emps = empDao.findEmps();
        assertEquals(30, emps.size());
        assertEquals(new Integer(1), emps.get(0).getId());
        assertEquals("シマゴロー", emps.get(0).getName());
    }

    public void testGetEmpTx() throws Exception {
        EmpDto emp = empDao.getEmp(1);
        assertEquals("シマゴロー", emp.getName());
    }

    public void testFindTx() throws Exception {
        Employee emp = employeeDao.find(1);
        assertNotNull(emp);
        assertEquals("シマゴロー", emp.getName());
    }

    public void testGetTx() throws Exception {
        Employee emp = employeeDao.get(1);
        assertNotNull(emp);
        assertEquals("シマゴロー", emp.getName());
    }

    public void testMergeTx() throws Exception {
        Employee emp = employeeDao.get(1);
        assertEquals("シマゴロー", emp.getName());
        em.clear();

        Employee emp2 = employeeDao.merge(emp);
        assertEquals("シマゴロー", emp2.getName());
    }

    public void testPersistTx() throws Exception {
        Employee emp = new Employee();
        employeeDao.persist(emp);
    }

    public void testReadLockTx() throws Exception {
        Employee emp = employeeDao.find(1);
        employeeDao.readLock(emp);
    }

    public void testRefreshTx() throws Exception {
        Employee emp = employeeDao.find(1);
        employeeDao.refresh(emp);
    }

    public void testRemoveTx() throws Exception {
        Employee emp = employeeDao.find(1);
        employeeDao.remove(emp);
    }

    public void testRemoveByIdTx() throws Exception {
        Employee emp = new Employee();
        employeeDao.persist(emp);
        em.flush();

        employeeDao.removeById(emp.getId());
        em.flush();

        try {
            employeeDao.find(1);
        } catch (EntityNotFoundException expected) {
            System.out.println(expected);
        }
    }

    public void testWriteLockTx() throws Exception {
        Employee emp = employeeDao.find(1);
        employeeDao.writeLock(emp);
    }

}
