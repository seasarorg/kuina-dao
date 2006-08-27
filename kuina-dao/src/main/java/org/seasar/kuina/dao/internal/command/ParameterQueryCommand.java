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

import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariableDeclaration;
import org.seasar.kuina.dao.internal.condition.ConditionalExpressionBuilder;

/**
 * 
 * @author koichik
 */
public class ParameterQueryCommand extends AbstractDynamicQueryCommand {

    protected String[] parameterNames;

    protected ConditionalExpressionBuilder[] builders;

    public ParameterQueryCommand(final Class<?> entityClass,
            final boolean resultList, final boolean distinct,
            final IdentificationVariableDeclaration fromDecl,
            final String[] parameterNames,
            final ConditionalExpressionBuilder[] builders) {
        super(entityClass, resultList, distinct, fromDecl);
        this.parameterNames = parameterNames;
        this.builders = builders;
    }

    @Override
    protected void bindParameter(final SelectStatement statement,
            final Object[] arguments) {
        for (int i = 0; i < arguments.length; ++i) {
            builders[i].appendCondition(statement, arguments[i]);
        }
    }

}