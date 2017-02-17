package es.uniapi.modules.business.groupgestion;

import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.UserLogin;

public interface GroupGestion {

	public void createGroup(UserLogin userLogin,Group group) throws Exception;
	public Group findByHash(String hash) throws Exception;
	public Group[] findAllGroups() throws Exception;
	public void deleteGroup(UserLogin user,Group group) throws Exception;
	public void updateGroup(UserLogin user,String hash,Group group) throws Exception;
	public Group[] findAllUserGroup(UserLogin userLogin)throws Exception;

}
