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
package org.seasar.kuina.dao.it.parameter.dao;

import org.seasar.kuina.dao.it.entity.OneToManyInverse;

/**
 * 
 * @author nakamura
 */
public interface OneToManyInverseDao {

    OneToManyInverse find(int id);

    OneToManyInverse get(int id);

    void persist(OneToManyInverse inverse);

    void remove(OneToManyInverse inverse);

    boolean contains(OneToManyInverse inverse);

    void refresh(OneToManyInverse inverse);

    OneToManyInverse merge(OneToManyInverse inverse);

    void readLock(OneToManyInverse inverse);

    void writeLock(OneToManyInverse inverse);

}