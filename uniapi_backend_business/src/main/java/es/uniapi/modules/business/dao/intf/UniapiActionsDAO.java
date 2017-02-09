package es.uniapi.modules.business.dao.intf;

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

	
	
}
