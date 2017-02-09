package es.uniapi.test.bussines.projectgestion;

import java.util.Date;

import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.business.projectgestion.ProjectGestionImpl;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.Project.ProjectType;
import es.uniapi.modules.model.UserLogin;

public class TestProjectGestion {

	public static void main(String args []){
		ProjectGestionImpl gest=new ProjectGestionImpl();
		Project project=new Project(new Date(),"Prueba1",
				ProjectType.PYTHON,"description", 
				"https://github.com/RaulGF92/prueba.git", 
				"raul@gmail.com", 
				"password", 
				null, "mainName", 
				"responseName", 
				null, 
				"inputDescription", 
				"outputDescription");
		UserLogin user=new UserLogin("raulgf92@gmail.com", "pass", new Date(), "admin");
		
		try {
			gest.createProject(user, project);
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
