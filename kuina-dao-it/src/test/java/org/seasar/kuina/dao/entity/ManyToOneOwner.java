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
package org.seasar.kuina.dao.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * 
 * @author nakamura
 */
@Entity
public class ManyToOneOwner {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ManyToOneOwner_Id_Generator")
	@SequenceGenerator(name = "ManyToOneOwner_Id_Generator", sequenceName = "ManyToOneOwner_Id_Sequence")
	private Integer id;

	private String name;

	private Integer height;

	private Integer weight;

	private String email;

	private Integer hireFiscalYear;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	private String bloodType;

	@SuppressWarnings("unused")
	@Version
	private Integer version;

	@ManyToOne
	private OneToManyInverse oneToManyInverse;

	public ManyToOneOwner() {
	}

	/**
	 * @return birthday��Ԃ��܂��B
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday
	 *            birthday��ݒ肵�܂��B
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return bloodType��Ԃ��܂��B
	 */
	public String getBloodType() {
		return bloodType;
	}

	/**
	 * @param bloodType
	 *            bloodType��ݒ肵�܂��B
	 */
	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	/**
	 * @return email��Ԃ��܂��B
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            email��ݒ肵�܂��B
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return height��Ԃ��܂��B
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            height��ݒ肵�܂��B
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * @return hireFiscalYear��Ԃ��܂��B
	 */
	public Integer getHireFiscalYear() {
		return hireFiscalYear;
	}

	/**
	 * @param hireFiscalYear
	 *            hireFiscalYear��ݒ肵�܂��B
	 */
	public void setHireFiscalYear(Integer hireFiscalYear) {
		this.hireFiscalYear = hireFiscalYear;
	}

	/**
	 * @return id��Ԃ��܂��B
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            id��ݒ肵�܂��B
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return name��Ԃ��܂��B
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            name��ݒ肵�܂��B
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return version��Ԃ��܂��B
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            version��ݒ肵�܂��B
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @return weight��Ԃ��܂��B
	 */
	public Integer getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            weight��ݒ肵�܂��B
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	/**
	 * @return oneToManyInverse��Ԃ��܂��B
	 */
	public OneToManyInverse getOneToManyInverse() {
		return oneToManyInverse;
	}

	/**
	 * @param oneToManyInverse
	 *            oneToManyInverse��ݒ肵�܂��B
	 */
	public void setOneToManyInverse(OneToManyInverse oneToManyInverse) {
		this.oneToManyInverse = oneToManyInverse;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ManyToOneOwner))
			return false;
		ManyToOneOwner castOther = (ManyToOneOwner) other;
		return this.id == castOther.id;
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id;
	}

}
