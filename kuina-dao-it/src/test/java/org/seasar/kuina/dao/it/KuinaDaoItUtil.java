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
package org.seasar.kuina.dao.it;

import java.lang.reflect.Method;
import java.util.Properties;

import org.seasar.framework.env.Env;
import org.seasar.framework.exception.ResourceNotFoundRuntimeException;
import org.seasar.framework.util.ResourceUtil;

/**
 * 
 * @author nakamura
 */
public class KuinaDaoItUtil {

    protected static final String ENV_PATH = "env_ut.txt";

    private KuinaDaoItUtil() {
    }

    public static boolean shouldRun(final Method method) {
        if (!ResourceUtil.isExist(ENV_PATH)) {
            throw new ResourceNotFoundRuntimeException(ENV_PATH);
        }
        final String fileName = Env.getValue() + ".properties";
        if (ResourceUtil.isExist(fileName)) {
            final Properties props = ResourceUtil.getProperties(fileName);
            final String key =
                method.getDeclaringClass().getName() + "#" + method.getName();
            return props.get(key) == null;
        }
        return true;

    }

}
