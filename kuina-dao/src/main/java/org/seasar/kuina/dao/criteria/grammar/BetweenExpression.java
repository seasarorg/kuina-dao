/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

/**
 * JPQLのbetween_expressionを表します．
 * <p>
 * JPQLの詳細はJPA仕様書「4.14 BNF」を参照してください．
 * </p>
 * 
 * <pre>
 * between_expression ::=
 *     arithmetic_expression [NOT] BETWEEN
 *     arithmetic_expression AND arithmetic_expression |
 *     string_expression [NOT] BETWEEN string_expression AND string_expression |
 *     datetime_expression [NOT] BETWEEN
 *     datetime_expression AND datetime_expression
 * </pre>
 * 
 * @author koichik
 */
public interface BetweenExpression extends Criterion, SimpleCondExpression {
}
