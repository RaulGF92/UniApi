package es.uniapi.modules.business.dao.intf.entities;

import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.Project.ProjectType;

public interface ProjectDAO {

	public void create(Project project) throws Exception;
	public void delete(Project project) throws Exception;
	public Project findByHashCode(String string) throws  Exception;
	public Project[] findAll() throws Exception;
	public Project[] findByName(String name)throws Exception;
	Project[] findByType(ProjectType type) throws Exception;

}
