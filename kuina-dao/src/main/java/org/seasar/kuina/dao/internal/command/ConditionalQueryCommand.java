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
package org.seasar.kuina.dao.internal.command;

import java.lang.reflect.Method;
import java.util.List;

import org.seasar.framework.log.Logger;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.criteria.grammar.ConditionalExpression;

/**
 * 
 * @author koichik
 */
public class ConditionalQueryCommand extends AbstractDynamicQueryCommand {

    protected static final Logger logger = Logger
            .getLogger(ConditionalQueryCommand.class);

    public ConditionalQueryCommand(final Class<?> entityClass,
            final Method method, final boolean resultList,
            final boolean distinct) {
        super(entityClass, method, resultList, distinct);
    }

    @Override
    protected List<String> bindParameter(final SelectStatement statement,
            final Object[] arguments) {
        for (final ConditionalExpression condition : ConditionalExpression[].class
                .cast(arguments[0])) {
            statement.where(condition);
        }
        return CollectionsUtil.newArrayList();
    }

}
