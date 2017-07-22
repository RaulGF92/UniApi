package es.uniapi.tests.bussines.dao;

import java.util.Date;

import org.apache.http.util.Args;

import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.UserLogin;

public class MakeAccountAdmin {

	public static void main(String[] args) {
		
		UserLogin user=new UserLogin(
				"admin@admin.es", 
				"d033e22ae348aeb5660fc2140aec35850c4da997", 
				new Date(), 
				"admin");
		Person person=new Person(
				"name", 
				"subname", 
				new Date(), 
				"country", 
				"province", 
				"birthplace", 
				"biografy", 
				"profileImageUrl", 
				new Date());
		UniApiFactoryDAO DAO=new UniApiFactoryDAO();
		
		
		try {
			DAO.getUniApiDao().getUserLoginDAO().create(user);
			DAO.getUniApiDao().getPersonDAO().create(person);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		try {
			DAO.getActions().userLoginMakeReferenceToPerson(user, person);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
