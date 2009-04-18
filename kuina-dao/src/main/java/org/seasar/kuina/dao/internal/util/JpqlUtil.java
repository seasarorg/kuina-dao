/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.util.StringUtil;

/**
 * JPQLを扱うためのユーティリティ・クラスです．
 * 
 * @author koichik
 */
public class JpqlUtil {

    private static final Set<String> reservedIdentifiers;
    static {
        Set<String> set = new HashSet<String>(55);
        set.add("SELECT");
        set.add("FROM");
        set.add("WHERE");
        set.add("UPDATE");
        set.add("DELETE");
        set.add("JOIN");
        set.add("OUTER");
        set.add("INNER");
        set.add("LEFT");
        set.add("GROUP");
        set.add("BY");
        set.add("HAVING");
        set.add("FETCH");
        set.add("DISTINCT");
        set.add("OBJECT");
        set.add("NULL");
        set.add("TRUE");
        set.add("FALSE");
        set.add("NOT");
        set.add("AND");
        set.add("OR");
        set.add("BETWEEN");
        set.add("LIKE");
        set.add("IN");
        set.add("AS");
        set.add("UNKNOWN");
        set.add("EMPTY");
        set.add("MEMBER");
        set.add("OF");
        set.add("IS");
        set.add("AVG");
        set.add("MAX");
        set.add("MIN");
        set.add("SUM");
        set.add("COUNT");
        set.add("ORDER");
        set.add("BY");
        set.add("ASC");
        set.add("DESC");
        set.add("MOD");
        set.add("UPPER");
        set.add("LOWER");
        set.add("TRIM");
        set.add("POSITION");
        set.add("CHARACTER_LENGTH");
        set.add("CHAR_LENGTH");
        set.add("BIT_LENGTH");
        set.add("CURRENT_TIME");
        set.add("CURRENT_DATE");
        set.add("CURRENT_TIMESTAMP");
        set.add("NEW");
        set.add("EXISTS");
        set.add("ALL");
        set.add("ANY");
        set.add("SOME");
        reservedIdentifiers = Collections.unmodifiableSet(set);
    }

    private static IdentificationVariableStrategy strategy = new DefaultIdentificationVariableStrategy();

    /**
     * {@link IdentificationVariableStrategy}を設定します．
     * 
     * @param strategy
     *            strategy
     * @throws NullPointerException
     *             {@code strategy}が{@literal null}の場合
     */
    public static void setStrategy(IdentificationVariableStrategy strategy) {
        if (strategy == null) {
            throw new NullPointerException("strategy");
        }
        JpqlUtil.strategy = strategy;
    }

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
        return strategy.toIdentificationVariable(abstractSchemaName);
    }

    /**
     * abstract_schema_nameからidentification_variableを求める方法を提供します．
     * 
     * @author koichik
     */
    public interface IdentificationVariableStrategy {

        /**
         * abstract_schema_nameからidentification_variableを求めて返します．
         * 
         * @param abstractSchemaName
         *            abstract_schema_name
         * @return identification_variable
         */
        String toIdentificationVariable(String abstractSchemaName);
    }

    /**
     * デフォルトの{@link IdentificationVariableStrategy}です．
     * 
     * @author koichik
     */
    public static class DefaultIdentificationVariableStrategy implements
            IdentificationVariableStrategy {

        public String toIdentificationVariable(final String abstractSchemaName) {
            final boolean reserved = reservedIdentifiers
                    .contains(abstractSchemaName.toUpperCase());
            return StringUtil.decapitalize(abstractSchemaName
                    .substring(abstractSchemaName.lastIndexOf('.') + 1))
                    + (reserved ? "_" : "");
        }

    }

    /**
     * Kuina-Dao 1.0.2以前と同じidentification_variableを返す
     * {@link IdentificationVariableStrategy}です．
     * 
     * @author koichik
     */
    public static class CompatibleIdentificationVariableStrategy implements
            IdentificationVariableStrategy {

        public String toIdentificationVariable(final String abstractSchemaName) {
            return StringUtil.decapitalize(abstractSchemaName
                    .substring(abstractSchemaName.lastIndexOf('.') + 1));
        }

    }

}
