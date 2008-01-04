/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
 * JPQLのfunctions_returning_numericsを表します．
 * <p>
 * JPQLの詳細はJPA仕様書「4.14 BNF」を参照してください．
 * </p>
 * 
 * <pre>
 * functions_returning_numerics::=
 *     LENGTH(string_primary) |
 *     LOCATE(string_primary, string_primary[, simple_arithmetic_expression]) |
 *     ABS(simple_arithmetic_expression) |
 *     SQRT(simple_arithmetic_expression) |
 *     MOD(simple_arithmetic_expression, simple_arithmetic_expression) |
 *     SIZE(collection_valued_path_expression)
 * </pre>
 * 
 * @author koichik
 */
public interface FunctionReturningNumerics extends Criterion, ArithmeticPrimary {
}
