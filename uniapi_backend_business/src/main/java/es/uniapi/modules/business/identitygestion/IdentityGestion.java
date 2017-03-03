package es.uniapi.modules.business.identitygestion;

import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.UserLogin;

public interface IdentityGestion {

	public void createAccount(UserLogin login,Person person) throws BussinessException;
	public Person getPerson(UserLogin user) throws BussinessException;
	public UserLogin findUserLoginByUser(String user)throws BussinessException;
	public void changePassword(UserLogin user,String pass)throws BussinessException;
	public void changeBio(UserLogin user,Person person)throws BussinessException;
	public UserLogin[] getAllUsers()throws BussinessException;
 
}
