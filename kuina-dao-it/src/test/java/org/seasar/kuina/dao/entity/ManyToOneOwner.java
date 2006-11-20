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
	 * @return birthdayを返します。
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday
	 *            birthdayを設定します。
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return bloodTypeを返します。
	 */
	public String getBloodType() {
		return bloodType;
	}

	/**
	 * @param bloodType
	 *            bloodTypeを設定します。
	 */
	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	/**
	 * @return emailを返します。
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            emailを設定します。
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return heightを返します。
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            heightを設定します。
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * @return hireFiscalYearを返します。
	 */
	public Integer getHireFiscalYear() {
		return hireFiscalYear;
	}

	/**
	 * @param hireFiscalYear
	 *            hireFiscalYearを設定します。
	 */
	public void setHireFiscalYear(Integer hireFiscalYear) {
		this.hireFiscalYear = hireFiscalYear;
	}

	/**
	 * @return idを返します。
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            idを設定します。
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return nameを返します。
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            nameを設定します。
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return versionを返します。
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            versionを設定します。
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @return weightを返します。
	 */
	public Integer getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            weightを設定します。
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	/**
	 * @return oneToManyInverseを返します。
	 */
	public OneToManyInverse getOneToManyInverse() {
		return oneToManyInverse;
	}

	/**
	 * @param oneToManyInverse
	 *            oneToManyInverseを設定します。
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
