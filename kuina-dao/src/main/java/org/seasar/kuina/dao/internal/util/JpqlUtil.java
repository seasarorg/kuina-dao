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
package org.seasar.kuina.dao.internal.util;

import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.util.StringUtil;

/**
 * JPQLを扱うためのユーティリティ・クラスです．
 * 
 * @author koichik
 */
public class JpqlUtil {

    /**
     * エンティティクラスをabstract_schema_nameに変換して返します．
     * <p>
     * abstract_schema_nameはエンティティ名です．
     * </p>
     * 
     * @param entityClass
     *            エンティティクラス
     * @return abstract_schema_name
     */
    public static String toAbstractSchemaName(final Class<?> entityClass) {
        final EntityDesc entityDesc = KuinaDaoUtil.getEntityDesc(entityClass);
        return entityDesc.getEntityName();
    }

    /**
     * エンティティクラスからデフォルトのidentification_variableに変換して返します．
     * 
     * @param entityClass
     *            エンティティクラス
     * @return デフォルトのidentification_variable
     */
    public static String toDefaultIdentificationVariable(
            final Class<?> entityClass) {
        return toDefaultIdentificationVariable(toAbstractSchemaName(entityClass));
    }

    /**
     * abstract_schema_nameからデフォルトのidentification_variableに変換して返します．
     * 
     * @param abstractSchemaName
     * @return デフォルトのidentification_variable
     */
    public static String toDefaultIdentificationVariable(
            final String abstractSchemaName) {
        return StringUtil.decapitalize(abstractSchemaName
                .substring(abstractSchemaName.lastIndexOf('.') + 1));
    }

}
