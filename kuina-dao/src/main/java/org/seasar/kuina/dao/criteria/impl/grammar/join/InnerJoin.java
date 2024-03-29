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
package org.seasar.kuina.dao.criteria.impl.grammar.join;

import org.seasar.framework.exception.SIllegalArgumentException;
import org.seasar.kuina.dao.criteria.IdentificationVarialbleVisitor;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariable;
import org.seasar.kuina.dao.criteria.grammar.Join;
import org.seasar.kuina.dao.criteria.grammar.PathExpression;

/**
 * 内部結合を表すクラスです．
 * 
 * @author koichik
 */
public class InnerJoin extends AbstractJoin implements Join {

    /**
     * インスタンスを構築します。
     * 
     * @param associationPathSpec
     *            association_path_expression
     */
    public InnerJoin(final PathExpression associationPathSpec) {
        this(associationPathSpec,
                getDefaultIdentificationVariable(associationPathSpec));
    }

    /**
     * インスタンスを構築します。
     * 
     * @param associationPathSpec
     *            association_path_expression
     * @param identificationVariable
     *            identification_variable
     */
    public InnerJoin(final PathExpression associationPathSpec,
            final IdentificationVariable identificationVariable) {
        super(" INNER JOIN ", associationPathSpec, identificationVariable);
        if (identificationVariable == null) {
            throw new SIllegalArgumentException("EKuinaDao0001",
                    new Object[] { "identificationVariable" });
        }
    }

    public void accept(final IdentificationVarialbleVisitor visitor) {
        visitor.identificationVariable(getIdentificationVariable());
    }

}
