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
package org.seasar.kuina.dao.internal.condition;

import org.seasar.kuina.dao.criteria.SelectStatement;
import org.seasar.kuina.dao.internal.ConditionalExpressionBuilder;

/**
 * パラメータを{@link SelectStatement#setMaxResults(int)}にバインドするビルダです．
 * 
 * @author koichik
 */
public class MaxResultsBuilder implements ConditionalExpressionBuilder {

    /**
     * インスタンスを構築します。
     */
    public MaxResultsBuilder() {
    }

    public String appendCondition(final SelectStatement statement,
            final Object value) {
        if (value == null) {
            return null;
        }
        final int maxResults = Integer.class.cast(value).intValue();
        if (maxResults < 0) {
            return null;
        }

        statement.setMaxResults(maxResults);
        return null;
    }

}
