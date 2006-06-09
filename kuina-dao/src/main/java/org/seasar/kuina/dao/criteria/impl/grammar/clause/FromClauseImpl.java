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
package org.seasar.kuina.dao.criteria.impl.grammar.clause;

import java.util.List;

import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.Criterion;
import org.seasar.kuina.dao.criteria.grammar.CollectionMemberDeclaration;
import org.seasar.kuina.dao.criteria.grammar.FromClause;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariable;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariableDeclaration;

/**
 * 
 * @author koichik
 */
public class FromClauseImpl implements FromClause {
    protected List<Criterion> declarations = CollectionsUtil.newArrayList();

    /**
     * インスタンスを構築します。
     */
    public FromClauseImpl(final IdentificationVariableDeclaration declaration) {
        declarations.add(declaration);
    }

    public FromClauseImpl(final IdentificationVariableDeclaration declaration,
            final IdentificationVariableDeclaration... declarations) {
        this.declarations.add(declaration);
        add(declarations);
    }

    public FromClauseImpl(final IdentificationVariableDeclaration declaration,
            final CollectionMemberDeclaration... declarations) {
        this.declarations.add(declaration);
        add(declarations);
    }

    public FromClause add(
            final IdentificationVariableDeclaration... declarations) {
        for (final IdentificationVariableDeclaration declaration : declarations) {
            this.declarations.add(declaration);
        }
        return this;
    }

    public FromClause add(final CollectionMemberDeclaration... declarations) {
        for (final CollectionMemberDeclaration declaration : declarations) {
            this.declarations.add(declaration);
        }
        return this;
    }

    public int size() {
        return declarations.size();
    }

    public IdentificationVariableDeclaration getIdentificationVariableDeclaration(
            final int index) {
        return IdentificationVariableDeclaration.class.cast(declarations
                .get(index));
    }

    public IdentificationVariable getIdentificationVariable(int index) {
        return getIdentificationVariableDeclaration(index)
                .getIdentificationVariable();
    }

    /**
     * @see org.seasar.kuina.dao.criteria.Criterion#evaluate(org.seasar.kuina.dao.criteria.CriteriaContext)
     */
    public void evaluate(final CriteriaContext context) {
        context.append(" FROM ");
        for (final Criterion criterion : declarations) {
            criterion.evaluate(context);
            context.append(", ");
        }
        context.cutBack(2);
    }

}
