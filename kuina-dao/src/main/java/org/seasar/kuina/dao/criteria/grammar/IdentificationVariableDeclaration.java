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
package org.seasar.kuina.dao.criteria.grammar;

import org.seasar.kuina.dao.criteria.Criterion;
import org.seasar.kuina.dao.criteria.IdentificationVarialbleVisitor;

/**
 * 
 * @author koichik
 */
public interface IdentificationVariableDeclaration extends Criterion,
        SubselectIdentificationVariableDeclaration {

    IdentificationVariableDeclaration inner(String associationPathSpec);

    IdentificationVariableDeclaration inner(PathExpression associationPathSpec);

    IdentificationVariableDeclaration inner(String associationPathSpec,
            String identificationVariable);

    IdentificationVariableDeclaration inner(PathExpression associationPathSpec,
            IdentificationVariable identificationVariable);

    IdentificationVariableDeclaration left(String associationPathSpec);

    IdentificationVariableDeclaration left(PathExpression associationPathSpec);

    IdentificationVariableDeclaration left(String associationPathSpec,
            String identificationVariable);

    IdentificationVariableDeclaration left(PathExpression associationPathSpec,
            IdentificationVariable identificationVariable);

    IdentificationVariableDeclaration innerFetch(String associationPathSpec);

    IdentificationVariableDeclaration innerFetch(
            PathExpression associationPathSpec);

    IdentificationVariableDeclaration leftFetch(String associationPathSpec);

    IdentificationVariableDeclaration leftFetch(
            PathExpression associationPathSpec);

    IdentificationVariableDeclaration join(Join join);

    IdentificationVariableDeclaration fetchJoin(FetchJoin fetchJoin);

    String getAbstractSchemaName();

    IdentificationVariable getIdentificationVariable();

    void accept(IdentificationVarialbleVisitor visitor);

}
