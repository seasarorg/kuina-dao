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
package org.seasar.kuina.dao.criteria.impl.grammar.function;

import org.seasar.kuina.dao.criteria.grammar.FunctionReturningStrings;
import org.seasar.kuina.dao.criteria.grammar.StringPrimary;

/**
 * JPQLのCONCAT関数を表すクラスです．
 * 
 * @author koichik
 */
public class Concat extends AbstractFunction implements
        FunctionReturningStrings {

    /**
     * インスタンスを構築します。
     * 
     * @param string1
     *            string_primary
     * @param string2
     *            <code>string1</code>に連結されるstring_primary
     */
    public Concat(final StringPrimary string1, final StringPrimary string2) {
        super("CONCAT", string1, string2);
    }

}
