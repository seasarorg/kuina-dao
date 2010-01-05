/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;

import org.seasar.framework.jpa.metadata.AttributeDesc;
import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;
import org.seasar.kuina.dao.internal.Command;
import org.seasar.kuina.dao.internal.ConditionalExpressionBuilder;

/**
 * 主キーとバージョンを検索条件として問い合わせを実行する{@link Command}です．
 * 
 * @author koichik
 */
public class FindVersionCommand extends ParameterQueryCommand {

    // instance fields
    /** バージョンプロパティ／フィールドを表す{@link AttributeDesc} */
    protected AttributeDesc attributeDesc;

    /**
     * インスタンスを構築します。
     * 
     * @param entityClass
     *            問い合わせ対象のエンティティ・クラス
     * @param method
     *            Daoメソッド
     * @param parameterNames
     *            引数名
     * @param builders
     *            検索条件を構築する{@link ConditionalExpressionBuilder}の配列
     */
    public FindVersionCommand(final Class<?> entityClass, final Method method,
            final String[] parameterNames,
            final ConditionalExpressionBuilder[] builders) {
        super(entityClass, method, false, parameterNames, builders);
        final EntityDesc entityDesc = EntityDescFactory
                .getEntityDesc(entityClass);
        attributeDesc = entityDesc.getAttributeDesc(parameterNames[1]);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object execute(final EntityManager em, final Object[] arguments) {
        try {
            final Object entity = super.execute(em, arguments);
            final Object version = attributeDesc.getValue(entity);
            if (!version.equals(arguments[1])) {
                throw new OptimisticLockException(entity);
            }
            return entity;
        } catch (final NoResultException ignore) {
            return null;
        }
    }

}
