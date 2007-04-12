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
package org.seasar.kuina.dao.criteria.impl.grammar.clause;

import java.util.List;

import org.seasar.framework.exception.SIllegalStateException;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.Criterion;
import org.seasar.kuina.dao.criteria.IdentificationVarialbleVisitor;
import org.seasar.kuina.dao.criteria.grammar.CollectionMemberDeclaration;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariable;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariableDeclaration;
import org.seasar.kuina.dao.criteria.grammar.SubqueryFromClause;
import org.seasar.kuina.dao.criteria.grammar.SubselectIdentificationVariableDeclaration;

/**
 * JPQLのsubquery_from_clauseを表します．
 * 
 * @author koichik
 */
public class SubqueryFromClauseImpl implements SubqueryFromClause {

    // instance fields
    /** 問い合わせがDISTINCTの場合は<code>true</code>，それ以外の場合は<code>false</code> */
    protected List<Criterion> declarations = CollectionsUtil.newArrayList();

    public SubqueryFromClause add(
            final SubselectIdentificationVariableDeclaration... declarations) {
        for (final SubselectIdentificationVariableDeclaration declaration : declarations) {
            this.declarations.add(declaration);
        }
        return this;
    }

    public SubqueryFromClause add(
            final CollectionMemberDeclaration... declarations) {
        for (final CollectionMemberDeclaration declaration : declarations) {
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
