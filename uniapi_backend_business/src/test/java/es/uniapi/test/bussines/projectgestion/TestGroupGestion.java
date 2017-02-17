package es.uniapi.test.bussines.projectgestion;

import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.dao.neo4j.UniapiNeo4jDAO;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Group.GroupType;
import es.uniapi.modules.model.UserLogin;

public class TestGroupGestion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		try {
			UserLogin raul=dao.getUniApiDao().getUserLoginDAO().findByEmail("raulgf92@gmail.com");
			UserLogin ludy=dao.getUniApiDao().getUserLoginDAO().findByEmail("ludyff@gmail.com");
			Group[] gruops=dao.getActions().getAllTypeGroups(GroupType.MAIN_GROUP);
			Group publicGroup=null;
			for(int i=0;i<gruops.length;i++){
				if(gruops[i].getName().compareTo("Public")==0)
					publicGroup=gruops[i];
			}
			if(publicGroup!=null){
				//dao.getActions().userKnowsGroup(raul,publicGroup);
				//dao.getActions().userKnowsGroup(ludy,publicGroup);
				
			}
			UserLogin[] users=dao.getActions().getAllUserKnowGroup(publicGroup);
			System.out.println(users.length);
			System.out.println(dao.getActions().getGroupsKnowsByUser(raul)[0].especialtoString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
