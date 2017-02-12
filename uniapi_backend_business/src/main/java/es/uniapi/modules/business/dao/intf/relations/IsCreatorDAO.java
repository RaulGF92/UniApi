package es.uniapi.modules.business.dao.intf.relations;

import java.util.Date;

import es.uniapi.modules.business.dao.neo4j.relationship.model.IsCreator;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.Project.ProjectType;
import es.uniapi.modules.model.UserLogin;

public interface IsCreatorDAO {

	public void create(UserLogin user,Project project) throws Exception;
	public Project[] getAllProjectsCreateByUser(UserLogin user)throws Exception;
	public Project[] getTypeProjectsCreateByUser(UserLogin user,ProjectType type);
	public IsCreator getInfo(UserLogin user,Project project) throws Exception;
	public void delete(UserLogin user,Project project) throws Exception;
	
}
