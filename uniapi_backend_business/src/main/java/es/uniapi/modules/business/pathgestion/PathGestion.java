package es.uniapi.modules.business.pathgestion;

import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.UserLogin;

public interface PathGestion {

	public Group[] getMainPath(UserLogin user);
	public Group[] getSubGroupsOfGroup(UserLogin user,Group father)throws BussinessException;
	
}
