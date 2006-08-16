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
package org.seasar.kuina.dao.criteria.impl.grammar.join;

import org.seasar.framework.exception.SIllegalArgumentException;
import org.seasar.framework.util.StringUtil;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariable;
import org.seasar.kuina.dao.criteria.grammar.JoinOrFetchJoin;
import org.seasar.kuina.dao.criteria.grammar.PathExpression;
import org.seasar.kuina.dao.criteria.impl.grammar.expression.IdentificationVariableImpl;

/**
 * 
 * @author koichik
 */
public abstract class AbstractJoin implements JoinOrFetchJoin {
    protected final String joinSpec;

    protected final PathExpression associationPathSpec;

    protected final IdentificationVariable identificationVariable;

    public AbstractJoin(final String joinSpec,
            final PathExpression associationPathSpec) {
        this(joinSpec, associationPathSpec, null);
    }

    public AbstractJoin(final String joinSpec,
            final PathExpression associationPathSpec,
            final IdentificationVariable identificationVariable) {
        if (StringUtil.isEmpty(joinSpec)) {
            throw new SIllegalArgumentException("EKuinaDao0001",
                    new Object[] { "joinSpec" });
        }
        if (associationPathSpec == null) {
            throw new SIllegalArgumentException("EKuinaDao0001",
                    new Object[] { "associationPathSpec" });
        }
        this.joinSpec = joinSpec;
        this.associationPathSpec = associationPathSpec;
        this.identificationVariable = identificationVariable;
    }

    public IdentificationVariable getIdentificationVariable() {
        return identificationVariable;
    }

    public PathExpression getAssociationPathSpec() {
        return associationPathSpec;
    }

    public void evaluate(final CriteriaContext context) {
        context.append(joinSpec);
        associationPathSpec.evaluate(context);
        if (identificationVariable != null) {
            context.append(" AS ");
            identificationVariable.evaluate(context);
        }
    }

    protected static IdentificationVariable getDefaultIdentificationVariable(
            final PathExpression associationPathSpec) {
        final String path = associationPathSpec.toString();
        final int pos = path.lastIndexOf('.');
        return new IdentificationVariableImpl(pos == -1 ? path : path
                .substring(pos + 1));
    }
}
