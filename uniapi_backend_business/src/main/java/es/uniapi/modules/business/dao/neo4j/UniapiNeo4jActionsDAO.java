package es.uniapi.modules.business.dao.neo4j;

import es.uniapi.modules.business.dao.intf.UniapiActionsDAO;
import es.uniapi.modules.business.dao.neo4j.relationship.MakeReferenceDAOImpl;
import es.uniapi.modules.business.dao.neo4j.relationship.model.MakeReference;
import es.uniapi.modules.model.Execution;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Group.GroupType;
import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.Project.ProjectType;
import es.uniapi.modules.model.UserLogin;

public class UniapiNeo4jActionsDAO implements UniapiActionsDAO {

	UniapiNeo4jDAO dao;
	
	@Override
	public void userLoginMakeReferenceToPerson(UserLogin user, Person person) throws Exception {
		// TODO Auto-generated method stub
		
		dao=new UniapiNeo4jDAO();
		
		UserLogin userTmp=dao.getUserLoginDAO().findByEmail(user.getUser());
		Person personTmp=dao.getPersonDAO().findByHashCode(person.hash());
		
		if(userTmp==null || personTmp==null)
			return;
		
		dao.getMakeReferenceDAO().create(userTmp, personTmp);
		
		return;
	}

	@Override
	public Person getPersonReferenceToUserLogin(UserLogin user) throws Exception {
		// TODO Auto-generated method stub
		
		dao=new UniapiNeo4jDAO();
		UserLogin userTmp=dao.getUserLoginDAO().findByEmail(user.getUser());
		if(userTmp==null)
			return null;
		
		return dao.getMakeReferenceDAO().getByUserLogin(userTmp).getPerson();
	}
	
	public void userLoginCreateProyect(UserLogin user,Project project) throws Exception{
		dao=new UniapiNeo4jDAO();
		dao.getIsCreatorDAO().create(user, project);
	}

	@Override
	public Project[] getAllProjectsCreatedByUser(UserLogin user) throws Exception {
		// TODO Auto-generated method stub
		dao=new UniapiNeo4jDAO();
		Project[] response=new Project[0];
		response=dao.getIsCreatorDAO().getAllProjectsCreateByUser(user);
		return response;
	}

	@Override
	public Project[] getTypeProjectsCreatedByUser(UserLogin user,ProjectType type) throws Exception {
		// TODO Auto-generated method stub
		dao=new UniapiNeo4jDAO();
		Project[] response=new Project[0];
		response=dao.getIsCreatorDAO().getTypeProjectsCreateByUser(user, type);
		return response;
	}

	@Override
	public Project[] getAllProjects() throws Exception {
		// TODO Auto-generated method stub
		dao=new UniapiNeo4jDAO();
		Project[] response=new Project[0];
		response=dao.getProjectDAO().findAll();
		return response;
	}
	public void deleteUserProjectProperty(UserLogin user,Project project) throws Exception{
		dao=new UniapiNeo4jDAO();
		dao.getIsCreatorDAO().delete(user, project);
	}

	@Override
	public void userKnowsGroup(UserLogin user, Group group) throws Exception {
		// TODO Auto-generated method stub
		dao=new UniapiNeo4jDAO();
		dao.getKnowsDAO().create(user, group);
	}

	@Override
	public Group[] getGroupsKnowsByUser(UserLogin user) throws Exception {
		// TODO Auto-generated method stub
		dao=new UniapiNeo4jDAO();
		return dao.getKnowsDAO().getAllGroupsKnowsByUser(user);
	}

	@Override
	public void userCreateGroup(UserLogin user, Group group) throws Exception {
		// TODO Auto-generated method stub
		dao=new UniapiNeo4jDAO();
		dao.getOwnerDAO().create(user, group);
	}

	@Override
	public Group[] getGroupsCreateByUser(UserLogin user) throws Exception {
		// TODO Auto-generated method stub
		dao=new UniapiNeo4jDAO();
		Group[] groups=dao.getOwnerDAO().getAllGroupsOwnerByUser(user);
		return groups;
	}

	@Override
	public UserLogin[] getAllUserKnowGroup(Group group) throws Exception {
		// TODO Auto-generated method stub
		dao=new UniapiNeo4jDAO();
		return dao.getKnowsDAO().getAllUserKnowsGroup(group);
	}

	@Override
	public Group[] getAllTypeGroups(GroupType type) throws Exception {
		// TODO Auto-generated method stub
		dao=new UniapiNeo4jDAO();
		return dao.getGroupDAO().findByType(type);
	}

	@Override
	public void deleteUserKnowsGroup(UserLogin user, Group group) throws Exception {
		// TODO Auto-generated method stub
		dao=new UniapiNeo4jDAO();
		dao.getKnowsDAO().deleteUserKnowsGroup(user, group);
	}

	@Override
	public UserLogin getUserOwnerOfGroup(Group gruop) throws Exception {
		// TODO Auto-generated method stub
		UserLogin response=null;
		dao=new UniapiNeo4jDAO();
		response=dao.getOwnerDAO().getOwnerOfGroup(gruop);
		return response;
	}

	@Override
	public void deleteUserOwnerGroup(UserLogin user, Group group) throws Exception {
		// TODO Auto-generated method stub
		dao=new UniapiNeo4jDAO();
		dao.getOwnerDAO().delete(user, group);
	}

	@Override
	public Group[] getSubgroupsOfGroup(Group group) throws Exception {
		// TODO Auto-generated method stub
		dao= new UniapiNeo4jDAO();
		return dao.getSubgroupDAO().getAllSubgroupOfGroup(group);
		
	}

	@Override
	public void groupIsSubgroupOfGroup(Group group, Group subgroup) throws Exception {
		// TODO Auto-generated method stub
		dao= new UniapiNeo4jDAO();
		dao.getSubgroupDAO().create(group, subgroup);
	}

	@Override
	public void deleteGroupIsSubgroupOfGroup(Group group, Group subgroup) throws Exception {
		// TODO Auto-generated method stub
		dao= new UniapiNeo4jDAO();
		dao.getSubgroupDAO().delete(group,subgroup);
	}

	@Override
	public Group[] getAllSubgroupsOfGroup(Group group) throws Exception {
		// TODO Auto-generated method stub
		dao= new UniapiNeo4jDAO();
		return dao.getSubgroupDAO().getAllSubgroupsOrderN(group);
	}

	@Override
	public void putProjectIntoGroup(Group group, Project project) throws Exception {
		// TODO Auto-generated method stub
		dao= new UniapiNeo4jDAO();
		dao.getContainsDAO().create(group, project);
	}

	@Override
	public void removeProjectOfGroup(Group group, Project project) throws Exception {
		// TODO Auto-generated method stub
		dao= new UniapiNeo4jDAO();
		dao.getContainsDAO().delete(group, project);		
	}

	@Override
	public Project[] getAllProjectsIntoGroup(Group group) throws Exception {
		// TODO Auto-generated method stub
		dao= new UniapiNeo4jDAO();
		return dao.getContainsDAO().findAllProjectsContainsInGroup(group);
	}

	@Override
	public Person getPersonOfUser(UserLogin user) {
		// TODO Auto-generated method stub
		dao= new UniapiNeo4jDAO();
		MakeReference reference=dao.getMakeReferenceDAO().getByUserLogin(user);
		return reference.getPerson();
	}

	@Override
	public void userLoginCreateExecution(UserLogin userLogin, Execution execution) throws Exception {
		// TODO Auto-generated method stub
		
		dao=new UniapiNeo4jDAO();
		dao.getGeneratorDAO().create(userLogin, execution);
	}

	@Override
	public Execution[] getAllExecutionGeneratedByUser(UserLogin user) throws Exception {
		// TODO Auto-generated method stub
		dao=new UniapiNeo4jDAO();
		return dao.getGeneratorDAO().getUserExecution(user);
	}

	@Override
	public UserLogin getUserOfExecution(Execution execution) throws Exception {
		// TODO Auto-generated method stub
		dao=new UniapiNeo4jDAO();
		return dao.getGeneratorDAO().getGeneratorOfExecution(execution);
	}

	@Override
	public void executionUseProject(Execution execution, Project project) throws Exception {
		// TODO Auto-generated method stub
		dao=new UniapiNeo4jDAO();
		dao.getUseDAO().create(execution, project);
	}

	@Override
	public Project getProjectUseForExecution(Execution execution) throws Exception {
		// TODO Auto-generated method stub
		dao=new UniapiNeo4jDAO();
		return dao.getUseDAO().getProjectUseForExecution(execution);
	}

	@Override
	public Execution[] getExecutionsUsingProject(Project project) throws Exception {
		// TODO Auto-generated method stub
		dao=new UniapiNeo4jDAO();
		return dao.getUseDAO().getExecutionsUsingProject(project);
	}


}
