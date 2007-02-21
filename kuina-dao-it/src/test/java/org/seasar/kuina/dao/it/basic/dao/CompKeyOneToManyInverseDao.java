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
package org.seasar.kuina.dao.it.basic.dao;

import org.seasar.kuina.dao.it.entity.CompKeyOneToManyInverse;
import org.seasar.kuina.dao.it.entity.CompKeyOneToManyInverseId;

/**
 * 
 * @author nakamura
 */
public interface CompKeyOneToManyInverseDao {

    CompKeyOneToManyInverse find(CompKeyOneToManyInverseId id);

    CompKeyOneToManyInverse getReference(CompKeyOneToManyInverseId id);

    void persist(CompKeyOneToManyInverse inverse);

    void remove(CompKeyOneToManyInverse inverse);

    boolean contains(CompKeyOneToManyInverse inverse);

    void refresh(CompKeyOneToManyInverse inverse);

    CompKeyOneToManyInverse merge(CompKeyOneToManyInverse inverse);

    void readLock(CompKeyOneToManyInverse inverse);

    void writeLock(CompKeyOneToManyInverse inverse);
}
