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
package org.seasar.kuina.dao.criteria.impl.grammar.declaration;

import java.util.List;

import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.IdentificationVarialbleVisitor;
import org.seasar.kuina.dao.criteria.grammar.FetchJoin;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariable;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariableDeclaration;
import org.seasar.kuina.dao.criteria.grammar.Join;
import org.seasar.kuina.dao.criteria.grammar.JoinOrFetchJoin;
import org.seasar.kuina.dao.criteria.grammar.PathExpression;
import org.seasar.kuina.dao.criteria.grammar.RangeVarialbeDeclaration;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.IdentificationVariableImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.PathExpressionImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.join.InnerFetchJoin;
import org.seasar.kuina.dao.criteria.impl.grammar.join.InnerJoin;
import org.seasar.kuina.dao.criteria.impl.grammar.join.LeftOuterFetchJoin;
import org.seasar.kuina.dao.criteria.impl.grammar.join.LeftOuterJoin;
import org.seasar.kuina.dao.internal.util.JpqlUtil;

/**
 * JPQLのidentification_variable_declarationを表す実装クラスです．
 * 
 * @author koichik
 */
public class IdentificationVariableDeclarationImpl implements
        IdentificationVariableDeclaration, RangeVarialbeDeclaration {

    // instance fields
    /** abstract_schema_name */
    protected final String abstractSchemaName;

    /** identification_variable */
    protected final IdentificationVariable identificationVariable;

    /** join_or_fetch_joinのリスト */
    protected final List<JoinOrFetchJoin> joins = CollectionsUtil
            .newArrayList();

    /**
     * インスタンスを構築します。
     * 
     * @param abstractSchemaClass
     *            abstract_schema_nameを表すエンティティクラス
     * @param joins
     *            join_or_fetch_joinの並び
     */
    public IdentificationVariableDeclarationImpl(
            final Class<?> abstractSchemaClass, final JoinOrFetchJoin... joins) {
        this(JpqlUtil.toAbstractSchemaName(abstractSchemaClass), joins);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param abstractSchemaName
     *            abstract_schema_nameを表す文字列
     * @param joins
     *            join_or_fetch_joinの並び
     */
    public IdentificationVariableDeclarationImpl(
            final String abstractSchemaName, final JoinOrFetchJoin... joins) {
        this(abstractSchemaName, new IdentificationVariableImpl(JpqlUtil
                .toDefaultIdentificationVariable(abstractSchemaName)), joins);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param abstractSchemaClass
     *            abstract_schema_nameを表すエンティティクラス
     * @param identificationVariable
     *            identification_variableを表す文字列
     * @param joins
     *            join_or_fetch_joinの並び
     */
    public IdentificationVariableDeclarationImpl(
            final Class<?> abstractSchemaClass,
            final IdentificationVariable identificationVariable,
            final JoinOrFetchJoin... joins) {
        this(JpqlUtil.toAbstractSchemaName(abstractSchemaClass),
                identificationVariable, joins);
    }

    /**
     * インスタンスを構築します。
     * 
     * @param abstractSchemaName
     *            abstract_schema_nameを表す文字列
     * @param identificationVariable
     *            identification_variable
     * @param joins
     *            join_or_fetch_joinの並び
     */
    public IdentificationVariableDeclarationImpl(
            final String abstractSchemaName,
            final IdentificationVariable identificationVariable,
            final JoinOrFetchJoin... joins) {
        this.abstractSchemaName = abstractSchemaName;
        this.identificationVariable = identificationVariable;
        for (final JoinOrFetchJoin join : joins) {
            this.joins.add(join);
        }
    }

    public IdentificationVariableDeclaration inner(
            final String associationPathSpec) {
        return inner(new PathExpressionImpl(associationPathSpec));
    }

    public IdentificationVariableDeclaration inner(
            final PathExpression associationPathSpec) {
        return join(new InnerJoin(associationPathSpec));
    }

    public IdentificationVariableDeclaration inner(
            final String associationPathSpec,
            final String identificationVariable) {
        return inner(new PathExpressionImpl(associationPathSpec),
                new IdentificationVariableImpl(identificationVariable));
    }

    public IdentificationVariableDeclaration inner(
            final PathExpression associationPathSpec,
            final IdentificationVariable identificationVariable) {
        return join(new InnerJoin(associationPathSpec, identificationVariable));
    }

    public IdentificationVariableDeclaration left(
            final String associationPathSpec) {
        return left(new PathExpressionImpl(associationPathSpec));
    }

    public IdentificationVariableDeclaration left(
            final PathExpression associationPathSpec) {
        return join(new LeftOuterJoin(associationPathSpec));
    }

    public IdentificationVariableDeclaration left(
            final String associationPathSpec,
            final String identificationVariable) {
        return left(new PathExpressionImpl(associationPathSpec),
                new IdentificationVariableImpl(identificationVariable));
    }

    public IdentificationVariableDeclaration left(
            final PathExpression associationPathSpec,
            final IdentificationVariable identificationVariable) {
        return join(new LeftOuterJoin(associationPathSpec,
                identificationVariable));
    }

    public IdentificationVariableDeclaration innerFetch(
            final String associationPathSpec) {
        return innerFetch(new PathExpressionImpl(associationPathSpec));
    }

    public IdentificationVariableDeclaration innerFetch(
            final PathExpression associationPathSpec) {
        return fetchJoin(new InnerFetchJoin(associationPathSpec));
    }

    public IdentificationVariableDeclaration leftFetch(
            final String associationPathSpec) {
        return leftFetch(new PathExpressionImpl(associationPathSpec));
    }

    public IdentificationVariableDeclaration leftFetch(
            final PathExpression associationPathSpec) {
        return fetchJoin(new LeftOuterFetchJoin(associationPathSpec));
    }

    public IdentificationVariableDeclaration join(final Join join) {
        joins.add(join);
        return this;
    }

    public IdentificationVariableDeclaration fetchJoin(final FetchJoin fetchJoin) {
        joins.add(fetchJoin);
        return this;
    }

    public String getAbstractSchemaName() {
        return abstractSchemaName;
    }

    public IdentificationVariable getIdentificationVariable() {
        return identificationVariable;
    }

    public void accept(final IdentificationVarialbleVisitor visitor) {
        visitor.identificationVariable(identificationVariable);
        for (final JoinOrFetchJoin join : joins) {
            join.accept(visitor);
        }
    }

    public void evaluate(final CriteriaContext context) {
        context.append(abstractSchemaName);
        if (identificationVariable != null) {
            context.append(" AS ");
            identificationVariable.evaluate(context);
        }
        for (final JoinOrFetchJoin join : joins) {
            join.evaluate(context);
        }
    }

}
