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
package org.seasar.kuina.dao.internal.util;

import org.seasar.framework.exception.SIllegalArgumentException;
import org.seasar.framework.jpa.metadata.EntityDesc;
import org.seasar.framework.jpa.metadata.EntityDescFactory;

/**
 * 
 * @author koichik
 */
public class KuinaDaoUtil {

    public static EntityDesc getEntityDesc(final Class<?> clazz) {
        final EntityDesc entityDesc = EntityDescFactory.getEntityDesc(clazz);
        if (entityDesc == null) {
            throw new SIllegalArgumentException("EKuinaDao0001",
                    new Object[] { clazz });
        }
        return entityDesc;
    }

    public static boolean isEntityClass(final Class<?> clazz) {
        final EntityDesc entityDesc = EntityDescFactory.getEntityDesc(clazz);
        return entityDesc != null;
    }

}
