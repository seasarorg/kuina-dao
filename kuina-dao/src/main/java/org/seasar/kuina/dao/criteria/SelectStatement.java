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
package org.seasar.kuina.dao.criteria;

import java.util.List;

import javax.persistence.EntityManager;

import org.seasar.kuina.dao.criteria.grammar.ConditionalExpression;
import org.seasar.kuina.dao.criteria.grammar.GroupbyItem;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariableDeclaration;
import org.seasar.kuina.dao.criteria.grammar.OrderbyItem;
import org.seasar.kuina.dao.criteria.grammar.SelectExpression;

/**
 * 
 * @author koichik
 */
public interface SelectStatement extends Criteria {
    SelectStatement select(String selectExpression);

    SelectStatement select(SelectExpression selectExpression);

    SelectStatement select(Object... selectExpression);

    SelectStatement from(Class<?>... entityClasses);

    SelectStatement from(Class<?> entityClass, String alias);

    SelectStatement from(IdentificationVariableDeclaration... declarations);

    SelectStatement where(ConditionalExpression... conditionalExpressions);

    SelectStatement groupby(String... groupbyItems);

    SelectStatement groupby(GroupbyItem... groupbyItems);

    SelectStatement having(ConditionalExpression... conditionalExpressions);

    SelectStatement orderby(String... orderbyItems);

    SelectStatement orderby(OrderbyItem... orderbyItems);

    SelectStatement setFirstResult(int startPosition);

    SelectStatement setMaxResults(int maxResult);

    <T> List<T> getResultList(EntityManager em);

    <T> T getSingleResult(EntityManager em);
}
