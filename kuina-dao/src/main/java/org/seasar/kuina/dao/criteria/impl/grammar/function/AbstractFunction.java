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
package org.seasar.kuina.dao.criteria.impl.grammar.function;

import java.util.List;

import org.seasar.framework.exception.SIllegalArgumentException;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.kuina.dao.criteria.CriteriaContext;
import org.seasar.kuina.dao.criteria.Criterion;

/**
 * 
 * @author koichik
 */
public class AbstractFunction {
    protected final String functor;

    protected final List<Criterion> arguments = CollectionsUtil.newArrayList();

    /**
     * インスタンスを構築します。
     */
    public AbstractFunction(final String functor) {
        if (StringUtil.isEmpty(functor)) {
            throw new SIllegalArgumentException("EKuinaDao0001",
                    new Object[] { "functor" });
        }
        this.functor = functor;
    }

    /**
     * インスタンスを構築します。
     */
    public AbstractFunction(final String functor, final Criterion... arguments) {
        this(functor);
        if (StringUtil.isEmpty(functor)) {
            throw new SIllegalArgumentException("EKuinaDao0001",
                    new Object[] { "functor" });
        }
        for (final Criterion argument : arguments) {
            this.arguments.add(argument);
        }
    }

    public void evaluate(final CriteriaContext context) {
        context.append(functor).append("(");
        for (final Criterion argument : arguments) {
            argument.evaluate(context);
            context.append(", ");
        }
        if (!arguments.isEmpty()) {
            context.cutBack(2);
        }
        context.append(")");
    }
}