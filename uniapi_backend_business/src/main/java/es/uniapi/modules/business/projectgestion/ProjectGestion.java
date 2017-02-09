package es.uniapi.modules.business.projectgestion;

import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.Project.ProjectType;
import es.uniapi.modules.model.UserLogin;

public interface ProjectGestion {

	void createProject(UserLogin user, Project project) throws BussinessException;
	Project getProject(String hash)throws BussinessException;
	Project[] getAllProjects(UserLogin user) throws BussinessException;
	Project[] getTypeProjects(UserLogin user, ProjectType project) throws BussinessException;
}
