package es.uniapi.modules.business.dao.intf.relations;

import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Project;

public interface ContainsDAO {

	public void create(Group group,Project project) throws Exception;
	public void delete(Group group,Project project) throws Exception;
	public Project[] findAllProjectsContainsInGroup(Group group) throws Exception;
}
