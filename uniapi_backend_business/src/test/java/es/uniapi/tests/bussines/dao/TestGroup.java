package es.uniapi.tests.bussines.dao;

import java.util.Date;

import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.dao.neo4j.UniapiNeo4jDAO;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Group.GroupType;

public class TestGroup {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 
		/*Sharing Group Permissions:
		 *[0]:ALL;
		 *[1]:shareProjectsInGroup;
		 *[2]:removeProjectsInGroup;
		 *[3]:removeExternalProjectInGroup;
		*/
		String[] sharingGroupPermissions={"YES","YES","YES","YES"};
		
		/* Project Properties permisions:
		 *[0]:ALL
		 *[1]:executionProjects,
		 *[2]:modifyInputsParams
		 */
		String[] projectPropertiesPermissions={"YES","YES","YES"};
		
		/* Member Gestion permissions:
		 *[0]:ALL;
		 *[2]:addMember;
		 *[3]:removeMember;
		 */
		String[] memberGestionPermissions={"YES","YES","YES"};
		
		/* Group creation Permissions:
		 *[0]:ALL
		 *[1]:creationGroup;
		 *[2]:createRestrictedGroup;
		 *[3]:createPublicGroup;
		 */
		String[] groupCreationPermissions={"YES","YES","YES","YES"};
		
		Group publicGroup=new Group("Informatica", new Date(),GroupType.MAIN_GROUP
				,sharingGroupPermissions, 
				projectPropertiesPermissions, 
				memberGestionPermissions, 
				groupCreationPermissions,
				"Grupo del departamento de informatica");
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		
		try {
			dao.getUniApiDao().getGroupDAO().create(publicGroup);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		try{
			
			Group[] response=dao.getUniApiDao().getGroupDAO().findAll();
			for(int i=0;i<response.length;i++){
				System.out.println(response[i].toString());
			}
			System.out.flush();
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		try{
			Group response=dao.getUniApiDao().getGroupDAO().findByHashCode(publicGroup.hash());
			System.out.println(response.toString());
			System.out.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			Group[] response=dao.getUniApiDao().getGroupDAO().findByType(GroupType.MAIN_GROUP);
			for(int i=0;i<response.length;i++){
				System.out.println(response[i].toString());
			}
			System.out.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

}
