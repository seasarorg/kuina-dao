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
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.seasar.framework.jpa.metadata.AttributeDesc;
import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.internal.condition.ConditionalExpressionBuilder;
import org.seasar.kuina.dao.internal.condition.ConditionalExpressionBuilderFactory;
import org.seasar.kuina.dao.internal.util.KuinaDaoUtil;
import org.seasar.kuina.dao.internal.util.SelectStatementUtil;

/**
 * 
 * @author koichik
 */
public class ExampleQueryCommand extends AbstractDynamicQueryCommand {

    protected int orderby;

    protected int firstResult;

    protected int maxResults;

    /**
     * インスタンスを構築します。
     */
    public ExampleQueryCommand(final Class<?> entityClass, final Method method,
            final boolean resultList, final int orderby, final int firstResult,
            final int maxResults) {
        super(entityClass, method, resultList);
        this.orderby = orderby;
        this.firstResult = firstResult;
        this.maxResults = maxResults;
    }

    @Override
    protected List<String> bindParameter(SelectStatement statement,
            Object[] arguments) {
        if (orderby >= 0) {
            SelectStatementUtil.appendOrderbyClause(identificationVariable,
                    statement, arguments[orderby]);
        }
        if (firstResult >= 0) {
            statement.setFirstResult(Number.class.cast(arguments[firstResult])
                    .intValue());
        }
        if (maxResults >= 0) {
            statement.setMaxResults(Number.class.cast(arguments[maxResults])
                    .intValue());
        }

        final Context context = new Context();
        addCondition(statement, entityClass, arguments[0], null, context);
        return context.getBindParameters();
    }

    @SuppressWarnings("unchecked")
    protected void addCondition(final SelectStatement statement,
            final Class<?> entityClass, final Object entity,
            final String pathExpression, final Context context) {
        if (context.isResolvedEntity(entity)) {
            return;
        }
        context.addResolvedEntity(entity);

        final EntityDesc entityDesc = KuinaDaoUtil.getEntityDesc(entityClass);
        final AttributeDesc[] attributes = entityDesc.getAttributeDescs();
        for (final AttributeDesc attribute : attributes) {
            addCondition(statement, entityClass, entity, attribute,
                    pathExpression, context);
        }
    }

    @SuppressWarnings("unchecked")
    protected void addCondition(final SelectStatement statement,
            final Class<?> entityClass, final Object entity,
            final AttributeDesc attribute, final String pathExpression,
            final Context context) {
        final Object value = attribute.getValue(entity);
        if (value == null) {
            return;
        }

        final String name = attribute.getName();
        final Class<?> type = attribute.getType();
        final String associationPath = ClassUtil.concatName(pathExpression,
                name);
        if (attribute.isAssociation()) {
            if (attribute.isCollection()) {
                final Collection collection = Collection.class.cast(value);
                if (collection != null && collection.size() == 1) {
                    final Class<?> elementType = attribute.getElementType();
                    addCondition(statement, elementType, collection.iterator()
                            .next(), associationPath, context);
                }
            } else {
                addCondition(statement, type, value, associationPath, context);
            }
        } else {
            final String parameterName = associationPath.replace('.', '$');
            final ConditionalExpressionBuilder builder = ConditionalExpressionBuilderFactory
                    .createBuilder(entityClass, parameterName, type);
            builder.appendCondition(statement, value);
            context.addBindParamter(associationPath);
        }
    }

    public static class Context {

        protected Map<Object, Object> resolvedEntities = CollectionsUtil
                .newIdentityHashMap();

        protected List<String> bindParameters = CollectionsUtil.newArrayList();

        public boolean isResolvedEntity(final Object entity) {
            return resolvedEntities.containsKey(entity);
        }

        public void addResolvedEntity(final Object entity) {
            resolvedEntities.put(entity, null);
        }

        public void addBindParamter(final String parameterName) {
            bindParameters.add(parameterName);
        }

        public List<String> getBindParameters() {
            return bindParameters;
        }

    }

}
