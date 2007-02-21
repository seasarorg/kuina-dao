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
package org.seasar.kuina.dao.it.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author nakamura
 */
@Embeddable
public class CompKeyManyToOneOwnerId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pk1;

    @Temporal(TemporalType.DATE)
    private Date pk2;

    public CompKeyManyToOneOwnerId() {
    }

    public CompKeyManyToOneOwnerId(Integer pk1, Date pk2) {
        this.pk1 = pk1;
        this.pk2 = pk2;
    }

    public Integer getPk1() {
        return pk1;
    }

    public void setPk1(Integer pk1) {
        this.pk1 = pk1;
    }

    public Date getPk2() {
        return pk2;
    }

    public void setPk2(Date pk2) {
        this.pk2 = pk2;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CompKeyManyToOneOwnerId))
            return false;
        CompKeyManyToOneOwnerId castOther = CompKeyManyToOneOwnerId.class
                .cast(other);
        return getPk1().equals(castOther.getPk1())
                && getPk2().equals(castOther.getPk2());
    }

    @Override
    public int hashCode() {
        int result;
        result = getPk1().hashCode();
        result = 29 * result + getPk2().hashCode();
        return result;
    }
}
