package es.uniapi.modules.apirest.model;

import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.UserLogin;

public class MessageWhoAmi extends Message {

	private UserLogin userLogin;
	@Override
	public String toString() {
		return "MessageWhoAmi [userLogin=" + userLogin + ", person=" + person + ", toString()=" + super.toString()
				+ "]";
	}

	private Person person;
	
	public MessageWhoAmi(int state, String tokenSession,String[] relatedIDs,UserLogin userLogin,Person person) {
		super(state, tokenSession,relatedIDs);
		// TODO Auto-generated constructor stub
		this.userLogin=userLogin;
		this.person=person;
		
	}

	public UserLogin getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((person == null) ? 0 : person.hashCode());
		result = prime * result + ((userLogin == null) ? 0 : userLogin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageWhoAmi other = (MessageWhoAmi) obj;
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
			return false;
		if (userLogin == null) {
			if (other.userLogin != null)
				return false;
		} else if (!userLogin.equals(other.userLogin))
			return false;
		return true;
	}
	
	

}
