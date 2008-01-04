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
package org.seasar.kuina.dao.internal.util;

import org.seasar.framework.exception.SIllegalArgumentException;
import org.seasar.framework.jpa.metadata.AttributeDesc;
import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;

/**
 * Kuina-Daoのユーティリティ・クラスです．
 * 
 * @author koichik
 */
public class KuinaDaoUtil {

    /**
     * クラスに対応した{@link EntityDesc}を返します．
     * <p>
     * クラスがエンティティでない場合は<code>IllegalArgumentException</code>がスローされます．
     * </p>
     * 
     * @param clazz
     *            クラス
     * @return {@link EntityDesc}
     * @throws IllegalArgumentException
     */
    public static EntityDesc getEntityDesc(final Class<?> clazz) {
        final EntityDesc entityDesc = EntityDescFactory.getEntityDesc(clazz);
        if (entityDesc == null) {
            throw new SIllegalArgumentException("EKuinaDao0001",
                    new Object[] { clazz });
        }
        return entityDesc;
    }

    /**
     * クラスがエンティティなら<code>true</code>を返します．
     * 
     * @param clazz
     *            クラス
     * @return クラスがエンティティなら<code>true</code>
     */
    public static boolean isEntityClass(final Class<?> clazz) {
        final EntityDesc entityDesc = EntityDescFactory.getEntityDesc(clazz);
        return entityDesc != null;
    }

    /**
     * エンティティ<code>owner</code>が持つ関連<code>association</code>の
     * 端点であるエンティティの{@link EntityDesc}を返します．
     * 
     * @param owner
     *            関連を持つエンティティクラス
     * @param associationName
     *            関連を表すプロパティ名またはフィールド名
     * @return エンティティが持つ関連の端点であるエンティティの{@link EntityDesc}
     */
    public static EntityDesc getAssociationEntityDesc(final EntityDesc owner,
            final String associationName) {
        final AttributeDesc attribute = owner.getAttributeDesc(associationName);
        final Class<?> associationType = attribute.isCollection() ? attribute
                .getElementType() : attribute.getType();
        return getEntityDesc(associationType);
    }

    /**
     * <code>association</code>がエンティティ<code>owner</code>の関連であれば<code>true</code>を返します．
     * 
     * @param owner
     *            関連を持つエンティティクラス
     * @param associationName
     *            関連を表すプロパティ名またはフィールド名
     * @return <code>association</code>がエンティティ<code>owner</code>の関連であれば<code>true</code>
     */
    public static boolean isAssociation(final EntityDesc owner,
            final String associationName) {
        final AttributeDesc attribute = owner.getAttributeDesc(associationName);
        return attribute != null && attribute.isAssociation();
    }

}
