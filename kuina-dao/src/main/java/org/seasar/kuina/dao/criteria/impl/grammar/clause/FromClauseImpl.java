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
package org.seasar.kuina.dao.criteria.impl.grammar.clause;

import java.util.List;

import org.seasar.framework.exception.SIllegalStateException;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.Criterion;
import org.seasar.kuina.dao.criteria.IdentificationVarialbleVisitor;
import org.seasar.kuina.dao.criteria.grammar.CollectionMemberDeclaration;
import org.seasar.kuina.dao.criteria.grammar.FromClause;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariable;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariableDeclaration;

/**
 * JPQLのfrom_clauseを表現するクラスです．
 * 
 * @author koichik
 */
public class FromClauseImpl implements FromClause {

    // instance fields
    /** identification_variable_declaration および collection_member_delarationのリスト */
    protected List<Criterion> declarations = CollectionsUtil.newArrayList();

    /**
     * インスタンスを構築します。
     */
    public FromClauseImpl() {
    }

    public FromClause add(
            final IdentificationVariableDeclaration... identificationVariableDeclarations) {
        for (final IdentificationVariableDeclaration declaration : identificationVariableDeclarations) {
            this.declarations.add(declaration);
        }
        return this;
    }

    public FromClause add(
            final CollectionMemberDeclaration... collectionMemberDeclarations) {
        for (final CollectionMemberDeclaration declaration : collectionMemberDeclarations) {
            this.declarations.add(declaration);
        }
        return this;
    }

    public boolean isEmpty() {
        return declarations.isEmpty();
    }

    public int size() {
        return declarations.size();
    }

    public IdentificationVariableDeclaration getIdentificationVariableDeclaration(
            final int index) {
        return IdentificationVariableDeclaration.class.cast(declarations
                .get(index));
    }

    public IdentificationVariable getIdentificationVariable(final int index) {
        return getIdentificationVariableDeclaration(index)
                .getIdentificationVariable();
    }

    public void accept(final IdentificationVarialbleVisitor visitor) {
        for (final Criterion declaration : declarations) {
            if (declaration instanceof IdentificationVariableDeclaration) {
                IdentificationVariableDeclaration.class.cast(declaration)
                        .accept(visitor);
            } else {
                CollectionMemberDeclaration.class.cast(declaration).accept(
                        visitor);
            }
        }
    }

    public void evaluate(final CriteriaContext context) {
        if (declarations.isEmpty()) {
            throw new SIllegalStateException("EKuinaDao1004", new Object[] {});
        }

        context.append(" FROM ");
        for (final Criterion criterion : declarations) {
            criterion.evaluate(context);
            context.append(", ");
        }
        context.cutBack(2);
    }

}
