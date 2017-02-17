package es.uniapi.modules.business.dao.intf.relations;

import es.uniapi.modules.business.dao.neo4j.relationship.model.IsOwner;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.UserLogin;

public interface IsOwnerDAO {

	public void create(UserLogin user,Group group)throws Exception;
	public Group[] getAllGroupsOwnerByUser(UserLogin userLogin)throws Exception;
	public UserLogin getOwnerOfGroup(Group group)throws Exception;
	public IsOwner getInfo(UserLogin user,Group group) throws Exception;
	public void delete(UserLogin user,Group group)throws Exception;
}
