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
package org.seasar.kuina.dao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * この注釈は二つの用途で使われます．
 * <p>
 * DAOのメソッドに注釈された場合，この注釈は注釈されたメソッドによるJPQL問い合わせのORDER BY句を指定します．
 * 
 * <pre>
 * interface EmpDao {
 *     &#x0040Orderby("e.salary DESC, e.name ASC")
 *     List<Emp> findEmpOrderbySalary();
 *     ...
 * }
 * </pre>
 * 
 * </p>
 * <p>
 * DAOのメソッド引数またはDTOプロパティのgetterメソッドに注釈された場合は，メソッド引数またはDTOのプロパティがORDER
 * BY句に含まれることを指定します． デフォルトでは，メソッド引数またはDTOプロパティの名前が<code>orderby</code>であれば注釈する必要はありません．
 * ORDER BY句を指定するメソッド引数またはDTOプロパティ名が<code>orderby</code>以外の場合にこの注釈を使用します．
 * </p>
 * <p>
 * DAOメソッドにこの注釈が付けられ，メソッド引数またはDTOのプロパティでもORDER
 * BYが指定された場合は，DAOメソッドの注釈で指定されたorder_by_specの後ろにメソッド引数またはDTOプロパティで指定されたorder_by_specが続くJPQLが生成されます．
 * 
 * <pre>
 * interface EmpDao {
 *     &#x0040Orderby("e.salary DESC")
 *     List<Emp> findEmpOrderbySalary(OrderbySpec... orderby);
 *     ...
 * }
 * </pre>
 * <pre>
 * dao.findEmpOrderbySalary(new OrderbySpec("name", OrderingSpec.ASC));
 * </pre>
 * <pre>
 * SELECT e FROM Emp e ... ORDER BY e.salary DESC, e.name ASC
 * </pre>
 * 
 * </p>
 * 
 * @author koichik
 */
@Target( { ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Orderby {

    /** ORDER BY句に含めるorder_by_spec (DAOメソッドに注釈する場合のみ) */
    String value() default "";

}
