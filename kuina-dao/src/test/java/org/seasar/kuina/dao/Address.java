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
package org.seasar.kuina.dao;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @author koichik
 */
@Entity
public class Address {

    @Id
    int id;

    String street;

    String city;

    String state;

    String zip;

    public Address() {
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Address))
            return false;
        Address castOther = (Address) other;
        return this.id == castOther.id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}
