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

import org.seasar.kuina.dao.criteria.IdentificationVarialbleVisitor;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariable;
import org.seasar.kuina.dao.criteria.grammar.Join;
import org.seasar.kuina.dao.criteria.grammar.PathExpression;

/**
 * 
 * @author koichik
 */
public class LeftOuterJoin extends AbstractJoin implements Join {

    public LeftOuterJoin(final PathExpression associationPathSpec) {
        this(associationPathSpec,
                getDefaultIdentificationVariable(associationPathSpec));
    }

    public LeftOuterJoin(final PathExpression associationPathSpec,
            IdentificationVariable identificationVariable) {
        super(" LEFT OUTER JOIN ", associationPathSpec, identificationVariable);
    }

    public void accept(final IdentificationVarialbleVisitor visitor) {
        visitor.identificationVariable(getIdentificationVariable());
    }

}
