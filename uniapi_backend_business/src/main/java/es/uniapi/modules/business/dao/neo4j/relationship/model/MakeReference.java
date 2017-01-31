package es.uniapi.modules.business.dao.neo4j.relationship.model;

import java.util.Date;

import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.UserLogin;

public class MakeReference {

	private Person person;
	private UserLogin userLogin;
	private Date since;
	
	public MakeReference(Person person,UserLogin userLogin) {
		// TODO Auto-generated constructor stub
		this.person=person;
		this.userLogin=userLogin;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public UserLogin getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	@Override
	public String toString() {
		return "MakeReference [person=" + person + ", userLogin=" + userLogin + ", since=" + since + "]";
	}
	
	

}
