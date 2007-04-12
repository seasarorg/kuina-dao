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
package org.seasar.kuina.dao.criteria.impl.grammar.expression;

import org.seasar.framework.exception.SIllegalArgumentException;
import org.seasar.framework.util.StringUtil;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.grammar.IdentificationVariable;

/**
 * JPQLのidentification_variableを表すクラスです．
 * 
 * @author koichik
 */
public class IdentificationVariableImpl implements IdentificationVariable {

    // instance fields
    /** identification_variable */
    protected final String identificationVariable;

    /**
     * インスタンスを構築します。
     * 
     * @param identificationVariable
     *            identification_variable
     */
    public IdentificationVariableImpl(final String identificationVariable) {
        if (StringUtil.isEmpty(identificationVariable)) {
            throw new SIllegalArgumentException("EKuinaDao0001",
                    new Object[] { "identificationVariable" });
        }
        this.identificationVariable = identificationVariable;
    }

    public void evaluate(final CriteriaContext context) {
        context.append(identificationVariable);
    }

    /**
     * オブジェクトの文字列表現を返します．
     * 
     * @return このオブジェクトの文字列表現
     */
    @Override
    public String toString() {
        return identificationVariable;
    }

}
