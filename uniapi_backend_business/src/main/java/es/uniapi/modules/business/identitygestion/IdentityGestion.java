package es.uniapi.modules.business.identitygestion;

import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.UserLogin;

public interface IdentityGestion {

	public void createAccount(UserLogin login,Person person) throws BussinessException;
	public Person getPerson(UserLogin user) throws BussinessException;

 
}
