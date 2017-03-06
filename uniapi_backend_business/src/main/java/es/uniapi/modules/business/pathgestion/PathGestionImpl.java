package es.uniapi.modules.business.pathgestion;

import java.util.ArrayList;

import es.uniapi.modules.business.Modules;
import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.business.groupgestion.GroupGestion.GroupRole;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.model.Group.GroupType;

public class PathGestionImpl implements PathGestion {

	@Override
	public Group[] getMainPath(UserLogin user) {
		// TODO Auto-generated method stub
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		try {
			return dao.getActions().getGroupsKnowsByUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Group[0];
		}
		
	}

	@Override
	public Group[] getSubGroupsOfGroup(UserLogin user,Group father) throws BussinessException {
		// TODO Auto-generated method stub
		ArrayList<Group> response=new ArrayList<Group>();
		Group[] workSet=Modules.getGroupModule().getSubgroupsOfGroup(father);
		for(int i=0;i<workSet.length;i++){
			Group aux=workSet[i];
			if(aux.getType().compareTo(GroupType.RESTRICTED_GROUP)==0){
				//Mirar si el usuario puede mirarlo
				GroupRole rol=Modules.getGroupModule().getUserRoleOnGruop(user, aux);
				if(rol.compareTo(GroupRole.VISITOR)!=0){
					response.add(aux);
				}
			}else{
				response.add(aux);
			}
		}
		return response.toArray(new Group[response.size()]);
	}

	
	

}
