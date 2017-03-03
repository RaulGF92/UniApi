package es.uniapi.tests.bussines.dao;

import java.util.Date;

import org.joda.time.DateTime;

import es.uniapi.modules.business.dao.neo4j.UniapiNeo4jDAO;
import es.uniapi.modules.business.dao.neo4j.entities.UserLoginNeo4j;
import es.uniapi.modules.business.dao.neo4j.relationship.MakeReferenceDAOImpl;
import es.uniapi.modules.business.dao.neo4j.relationship.model.MakeReference;
import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.UserLogin;

public class TestRelationship {

	public static void main(String[] args) {
		
		MakeReferenceDAOImpl dao=new MakeReferenceDAOImpl();
		UserLoginNeo4j userDao=new UserLoginNeo4j();
		UserLogin user;
		/*
		try {
			user = userDao.findByEmail("raulgf92@gmail.com");
			MakeReference makeReference=dao.getByUserLogin(user);
			System.out.println(makeReference.toString());
			System.out.println("-----------------------------");
			MakeReference[] makeReferences=dao.getAll();
			for(int i=0;i<makeReferences.length;i++){
				System.out.println(makeReferences[i].toString());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		System.out.flush();
		UserLogin user1=new UserLogin("jose@jose.com","jose@jose.com", new Date(),"user");
		Person person=new Person("Jose","Pringao Pargela",new Date(),"España","Asturias","Almeria","fdsf","https://pbs.twimg.com/profile_images/574787476599926786/Y7EItFYE.jpeg",new Date());
		UniapiNeo4jDAO dao1=new UniapiNeo4jDAO();
		
		UserLogin userTmp;
		try {
			
			dao1.getUserLoginDAO().create(user1);
			dao1.getPersonDAO().create(person);
		
			userTmp = dao1.getUserLoginDAO().findByEmail(user1.getUser());
			Person personTmp=dao1.getPersonDAO().findByHashCode(person.hash());
			if(userTmp==null || personTmp==null)
				return;
			
			dao1.getMakeReferenceDAO().create(userTmp, person);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
