package es.uniapi.modules.business.identitygestion;

import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.UserLogin;

public interface IdentityGestion {

	public void createAccount(UserLogin login,Person person);
	public Person getPerson(String email);

 
}
