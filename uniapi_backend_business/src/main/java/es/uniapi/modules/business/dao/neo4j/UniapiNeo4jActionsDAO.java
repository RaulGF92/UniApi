package es.uniapi.modules.business.dao.neo4j;

import es.uniapi.modules.business.dao.intf.UniapiActionsDAO;
import es.uniapi.modules.business.dao.neo4j.relationship.MakeReferenceDAO;
import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.UserLogin;

public class UniapiNeo4jActionsDAO implements UniapiActionsDAO {

	UniapiNeo4jDAO dao;
	
	@Override
	public void userLoginMakeReferenceToPerson(UserLogin user, Person person) throws Exception {
		// TODO Auto-generated method stub
		
		dao=new UniapiNeo4jDAO();
		
		UserLogin userTmp=dao.getUserLoginDAO().findByEmail(user.getUser());
		Person personTmp=dao.getPersonDAO().findByHashCode(person.hash());
		
		if(userTmp==null || personTmp==null)
			return;
		
		dao.getMakeReferenceDAO().create(userTmp, personTmp);
		
		return;
	}

	@Override
	public Person getPersonReferenceToUserLogin(UserLogin user) throws Exception {
		// TODO Auto-generated method stub
		
		dao=new UniapiNeo4jDAO();
		UserLogin userTmp=dao.getUserLoginDAO().findByEmail(user.getUser());
		if(userTmp==null)
			return null;
		
		return dao.getMakeReferenceDAO().getByUserLogin(userTmp).getPerson();
	}

}
