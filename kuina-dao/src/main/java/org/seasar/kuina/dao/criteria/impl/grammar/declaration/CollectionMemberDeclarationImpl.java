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
package org.seasar.kuina.dao.criteria.impl.grammar.declaration;

import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.IdentificationVarialbleVisitor;
import org.seasar.kuina.dao.criteria.grammar.CollectionMemberDeclaration;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariable;
import org.seasar.kuina.dao.criteria.grammar.PathExpression;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.IdentificationVariableImpl;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.PathExpressionImpl;

/**
 * JPQLのcollection_member_declarationを表す実装クラスです．
 * 
 * @author koichik
 */
public class CollectionMemberDeclarationImpl implements
        CollectionMemberDeclaration {

    // instance fields
    /** path_expression */
    protected final PathExpression pathExpression;

    /** identification_variable */
    protected final IdentificationVariable identificationVariable;

    /**
     * インスタンスを構築します。
     * 
     * @param pathExpression
     *            path_expressionを表す文字列
     * @param identificationVariable
     *            identification_variable
     */
    public CollectionMemberDeclarationImpl(final String pathExpression,
            final String identificationVariable) {
        this(new PathExpressionImpl(pathExpression),
                new IdentificationVariableImpl(identificationVariable));
    }

    /**
     * インスタンスを構築します。
     * 
     * @param pathExpression
     *            path_expression
     * @param identificationVariable
     *            identification_variable
     */
    public CollectionMemberDeclarationImpl(final PathExpression pathExpression,
            final IdentificationVariable identificationVariable) {
        this.pathExpression = pathExpression;
        this.identificationVariable = identificationVariable;
    }

    /**
     * path_expressionを返します．
     * 
     * @return path_expression
     */
    public PathExpression getPathExpression() {
        return pathExpression;
    }

    /**
     * identification_variableを返します．
     * 
     * @return identification_variable
     */
    public IdentificationVariable getIdentificationVariable() {
        return identificationVariable;
    }

    public void accept(final IdentificationVarialbleVisitor visitor) {
        visitor.identificationVariable(identificationVariable);
    }

    public void evaluate(final CriteriaContext context) {
        context.append(" IN(");
        pathExpression.evaluate(context);
        context.append(") AS ");
        identificationVariable.evaluate(context);
    }

}
