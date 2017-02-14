package es.uniapi.modules.business.dao.intf.entities;

import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Group.GroupType;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.Project.ProjectType;

public interface GroupDAO {

	public void create(Group group) throws Exception;
	public void delete(Group group) throws Exception;
	public Group findByHashCode(String string) throws  Exception;
	public Group[] findAll() throws Exception;
	Group[] findByType(GroupType type) throws Exception;
	public void update(String hash, Group group);
}
