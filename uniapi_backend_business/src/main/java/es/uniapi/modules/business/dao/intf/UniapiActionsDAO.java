package es.uniapi.modules.business.dao.intf;

import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.UserLogin;

public interface UniapiActionsDAO {

	public void userLoginMakeReferenceToPerson(UserLogin user,Person person) throws Exception;
	public Person getPersonReferenceToUserLogin(UserLogin user) throws Exception;
	
	
	
}
