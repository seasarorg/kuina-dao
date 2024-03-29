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
package org.seasar.kuina.dao.internal.command;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.seasar.kuina.dao.internal.Command;

/**
 * {@link EntityManager#lock(Object, LockModeType)}を{@link LockModeType#WRITE}で実行する{@link Command}です．
 * 
 * @author koichik
 */
public class WriteLockCommand extends AbstractCommand {

    /**
     * インスタンスを構築します。
     */
    public WriteLockCommand() {
    }

    public Object execute(final EntityManager em, final Object[] arguments) {
        final Object entity = arguments[0];
        em.lock(entity, LockModeType.WRITE);
        return null;
    }

}
