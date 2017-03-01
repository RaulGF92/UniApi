package es.uniapi.modules.business.dao.intf.relations;

import es.uniapi.modules.business.dao.neo4j.relationship.model.IsSubGroup;
import es.uniapi.modules.model.Group;

public interface IsSubgroupDAO {

	public void create(Group group,Group subgroup)throws Exception;
	public Group[] getAllSubgroupOfGroup(Group group)throws Exception;
	public Group getParentGroupOfSubgroup(Group group)throws Exception;
	public IsSubGroup getInfo(Group group,Group subgroup)throws Exception;
	public Group[] getAllSubgroupsOrderN(Group group)throws Exception;
	public void delete(Group group, Group subgroup)throws Exception;
	
}
