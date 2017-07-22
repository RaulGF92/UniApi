package es.uniapi.tests.bussines.dao;

import java.util.Date;

import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.business.projectgestion.ProjectGestionImpl;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.model.Project.ProjectType;

public class MainPath {

	public static void main(String args []){
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		
		UserLogin user;
		try {
			user = dao.getUniApiDao().getUserLoginDAO().findByEmail("raulgf92@uniapi.es");
			dao.getActions().getMainPath(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
