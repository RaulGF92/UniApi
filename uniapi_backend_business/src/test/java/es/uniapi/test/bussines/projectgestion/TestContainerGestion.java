package es.uniapi.test.bussines.projectgestion;

import es.uniapi.modules.business.Modules;
import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.UserLogin;

public class TestContainerGestion {

	public static void main(String[] args){
		UserLogin raul = null;
		Group group = null;
		Project project = null;
		String groupHash="89b507dd5ddeb237c4f3b6da055eb2845db2dbd6";
		String projectHash="3b4bc773df5038c861d5d338e6fb1fdf4d894f73";
		
		try{
			UniApiFactoryDAO dao=new UniApiFactoryDAO();
			raul=dao.getUniApiDao().getUserLoginDAO().findByEmail("raulgf92@gmail.com");
			group=Modules.getGroupModule().findByHash(groupHash);
			project=Modules.getProjectModule().getProject(projectHash);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println(group.especialtoString());
		System.out.println(project.especialToString());
		
		try {
			Modules.getGroupModule().putProjectIntoGroup(raul, group, project);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.flush();
		System.out.println("--------------------");
		try {
			Project[] projects=Modules.getGroupModule().getAllProjectsIntoGroup(raul, group);
			for(int i=0;i<projects.length;i++){
				System.out.println(projects[i].especialToString());
			}
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
