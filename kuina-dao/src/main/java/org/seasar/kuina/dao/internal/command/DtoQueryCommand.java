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
package org.seasar.kuina.dao.internal.command;

import java.lang.reflect.Method;

import org.seasar.framework.util.tiger.ReflectionUtil;
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariableDeclaration;
import org.seasar.kuina.dao.internal.condition.ConditionalExpressionBuilder;

/**
 * 
 * @author koichik
 */
public class DtoQueryCommand extends AbstractDynamicQueryCommand {

    protected Method[] getterMethods;

    protected ConditionalExpressionBuilder[] builders;

    public DtoQueryCommand(final Class<?> entityClass,
            final boolean resultList, final boolean distinct,
            final IdentificationVariableDeclaration fromDecl,
            final Method[] getterMethods,
            final ConditionalExpressionBuilder[] builders) {
        super(entityClass, resultList, distinct, fromDecl);
        this.getterMethods = getterMethods;
        this.builders = builders;
    }

    @Override
    protected void bindParameter(final SelectStatement statement,
            final Object[] arguments) {
        final Object dto = arguments[0];
        for (int i = 0; i < getterMethods.length; ++i) {
            final Object value = ReflectionUtil.invoke(getterMethods[i], dto);
            builders[i].appendCondition(statement, value);
        }
    }

}