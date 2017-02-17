package es.uniapi.modules.business.dao.intf;

import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Group.GroupType;
import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.Project.ProjectType;
import es.uniapi.modules.model.UserLogin;

public interface UniapiActionsDAO {

	public void userLoginMakeReferenceToPerson(UserLogin user,Person person) throws Exception;
	public Person getPersonReferenceToUserLogin(UserLogin user) throws Exception;
	
	public void userLoginCreateProyect(UserLogin user,Project project) throws Exception;
	public Project[] getAllProjectsCreatedByUser(UserLogin user) throws Exception;
	public Project[] getTypeProjectsCreatedByUser(UserLogin user, ProjectType project) throws Exception;
	public Project[] getAllProjects() throws Exception;
	public void deleteUserProjectProperty(UserLogin user,Project project) throws Exception;
	
	public void userKnowsGroup(UserLogin user,Group group)throws Exception;
	public Group[] getGroupsKnowsByUser(UserLogin user)throws Exception;
	public UserLogin[] getAllUserKnowGroup(Group group) throws Exception;
	public void deleteUserKnowsGroup(UserLogin user,Group group)throws Exception;

	public void userCreateGroup(UserLogin user,Group group) throws Exception;
	public Group[] getGroupsCreateByUser(UserLogin user)throws Exception;
	public Group[] getAllTypeGroups(GroupType type)throws Exception;
	public UserLogin getUserOwnerOfGroup(Group group)throws Exception;
	public void deleteUserOwnerGroup(UserLogin user,Group group)throws Exception;
	
	
	
}
