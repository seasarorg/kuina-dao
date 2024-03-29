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
package org.seasar.kuina.dao.criteria.grammar;

import org.seasar.kuina.dao.criteria.Criterion;

/**
 * JPQLのcomparison_expressionを表します．
 * <p>
 * JPQLの詳細はJPA仕様書「4.14 BNF」を参照してください．
 * </p>
 * 
 * <pre>
 * comparison_expression ::=
 *     string_expression comparison_operator {string_expression | all_or_any_expression} |
 *     boolean_expression { =|<>} {boolean_expression | all_or_any_expression} |
 *     enum_expression { =|<>} {enum_expression | all_or_any_expression} |
 *     datetime_expression comparison_operator
 *         {datetime_expression | all_or_any_expression} |
 *     entity_expression { = | <> } {entity_expression | all_or_any_expression} |
 *     arithmetic_expression comparison_operator
 *         {arithmetic_expression | all_or_any_expression}
 * </pre>
 * 
 * @author koichik
 */
public interface ComparisonExpression extends Criterion, SimpleCondExpression {
}
