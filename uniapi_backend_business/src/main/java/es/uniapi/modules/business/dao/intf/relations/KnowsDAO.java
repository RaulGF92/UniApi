package es.uniapi.modules.business.dao.intf.relations;

import es.uniapi.modules.business.dao.neo4j.relationship.model.Knows;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Group.GroupType;
import es.uniapi.modules.model.UserLogin;

public interface KnowsDAO {

	public void create(UserLogin user,Group group) throws Exception;
	public Group[] getAllGroupsKnowsByUser(UserLogin user) throws Exception;
	public Group[] getAllTypeGroupsKnowsByUser(UserLogin user,GroupType type) throws Exception;
	public Knows getInfoGroupKnowByUser(UserLogin user,Group group) throws Exception;
	public UserLogin[] getAllUserKnowsGroup(Group group)throws Exception;
	public void deleteUserKnowsGroup(UserLogin user,Group group) throws Exception;
	
	
}
