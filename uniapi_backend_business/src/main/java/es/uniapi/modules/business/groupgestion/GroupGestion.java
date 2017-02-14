package es.uniapi.modules.business.groupgestion;

import es.uniapi.modules.model.Group;

public interface GroupGestion {

	public void createGroup() throws Exception;
	public Group findByHash() throws Exception;
	public Group[] findAllGroups() throws Exception;
	public void deleteGroup(Group group) throws Exception;
	public void updateGroup(Group group) throws Exception;
}
