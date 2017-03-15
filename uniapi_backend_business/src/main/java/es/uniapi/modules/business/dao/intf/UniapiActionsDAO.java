package es.uniapi.modules.business.dao.intf;

import es.uniapi.modules.model.Execution;
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
	public Group[] getSubgroupsOfGroup(Group group) throws Exception;
	public void groupIsSubgroupOfGroup(Group group, Group subgroup) throws Exception;
	public void deleteGroupIsSubgroupOfGroup(Group group, Group subgroup)throws Exception;
	public Group[] getAllSubgroupsOfGroup(Group subgroup)throws Exception;
	
	public void putProjectIntoGroup(Group group,Project project)throws Exception;
	public void removeProjectOfGroup(Group group,Project project)throws Exception;
	public Project[] getAllProjectsIntoGroup(Group group)throws Exception;
	public Person getPersonOfUser(UserLogin user);
	
	public void userLoginCreateExecution(UserLogin userLogin,Execution execution)throws Exception;
	public Execution[] getAllExecutionGeneratedByUser(UserLogin user)throws Exception;
	public UserLogin getUserOfExecution(Execution execution)throws Exception;
	
	public void executionUseProject(Execution execution,Project project)throws Exception; 
	public Project getProjectUseForExecution(Execution execution)throws Exception;
	public Execution[] getExecutionsUsingProject(Project project)throws Exception;
	
}
