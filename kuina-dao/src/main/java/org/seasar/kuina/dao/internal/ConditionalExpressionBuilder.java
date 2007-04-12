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
package org.seasar.kuina.dao.internal;

import org.seasar.kuina.dao.criteria.SelectStatement;

/**
 * Criteria APIを動的に呼び出して問い合わせ条件等を作成し，SELECT文に追加するビルダです．
 * 
 * @author koichik
 */
public interface ConditionalExpressionBuilder {

    /**
     * 問い合わせ条件等を作成してSELECT文に追加します．
     * <p>
     * <code>value</code>をパラメータにバインドした場合はパラメータ名を返します． パラメータをバインドしなかった場合は<code>null</code>を返します．
     * 多くのビルダ実装クラスでは<code>value</code>が<code>null</code>の場合はパラメータをバインドしません．
     * </p>
     * 
     * @param statement
     *            SELECT文
     * @param value
     *            問い合わせ条件にバインドするパラメータの値
     * @return <code>value</code>をパラメータにバインドした場合はパラメータ名
     */
    String appendCondition(SelectStatement statement, Object value);

}
