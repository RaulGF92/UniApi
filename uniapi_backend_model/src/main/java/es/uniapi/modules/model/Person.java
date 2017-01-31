package es.uniapi.modules.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Person {

	
	public Person(String name, String subname, Date birthday, String country, String province,
			String birthplace, String biografy, String profileImageUrl) {
		super();
		this.name = name;
		this.subname = subname;
		this.birthday = birthday;
		this.country = country;
		this.province = province;
		this.birthplace = birthplace;
		this.biografy = biografy;
		this.profileImageUrl = profileImageUrl;
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
	@Override
	public String toString() {
		return "Person [name=" + name + ", subname=" + subname + ", birthday=" + birthday + ", country="
				+ country + ", province=" + province + ", birthplace=" + birthplace + ", biografy=" + biografy
				+ ", profileImageUrl=" + profileImageUrl + "]";
	}
	
	
	
}
