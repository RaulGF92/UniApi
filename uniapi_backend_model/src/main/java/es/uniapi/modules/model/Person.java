package es.uniapi.modules.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.uniapi.modules.model.config.SHA1;

public class Person {

	
	private Date dateCreation;


	public Person(String name, String subname, Date birthday, String country, String province,
			String birthplace, String biografy, String profileImageUrl,Date dateCreation) {
		super();
		this.name = name;
		this.subname = subname;
		this.birthday = birthday;
		this.country = country;
		this.province = province;
		this.birthplace = birthplace;
		this.biografy = biografy;
		this.profileImageUrl = profileImageUrl;
		this.dateCreation=dateCreation;
		this.hashcode=this.hash();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubname() {
		return subname;
	}
	public void setSubname(String subname) {
		this.subname = subname;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getBirthplace() {
		return birthplace;
	}
	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}
	public String getBiografy() {
		return biografy;
	}
	public void setBiografy(String biografy) {
		this.biografy = biografy;
	}
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	
	private String name;
	private String subname;
	private Date birthday;
	private String country;
	private String province;
	private String birthplace;
	private String biografy;
	private String profileImageUrl;
	private String hashcode;
	
	
	@Override
	public String toString() {
		return "Person [dateCreation=" + dateCreation + ", name=" + name + ", subname=" + subname + ", birthday="
				+ birthday + ", country=" + country + ", province=" + province + ", birthplace=" + birthplace
				+ ", biografy=" + biografy + ", profileImageUrl=" + profileImageUrl + ", hashcode=" + hashcode + "]";
	}

	public String hash() {
		return SHA1.encryptPassword(this.toString());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (biografy == null) {
			if (other.biografy != null)
				return false;
		} else if (!biografy.equals(other.biografy))
			return false;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (birthplace == null) {
			if (other.birthplace != null)
				return false;
		} else if (!birthplace.equals(other.birthplace))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (profileImageUrl == null) {
			if (other.profileImageUrl != null)
				return false;
		} else if (!profileImageUrl.equals(other.profileImageUrl))
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		if (subname == null) {
			if (other.subname != null)
				return false;
		} else if (!subname.equals(other.subname))
			return false;
		return true;
	}

	public String getHashcode() {
		return hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	
	
	
}
