package es.uniapi.modules.business.identitygestion;

import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.model.Group.GroupType;
import es.uniapi.modules.model.config.SHA1;

public class IdentityGestionImpl implements IdentityGestion {

	@Override
	public void createAccount(UserLogin login, Person person) throws BussinessException {
		// TODO Auto-generated method stub

		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		login.setPass(SHA1.encryptPassword(login.getPass()));
		try {
			
			dao.getUniApiDao().getUserLoginDAO().create(login);
			dao.getUniApiDao().getPersonDAO().create(person);
			dao.getActions().userLoginMakeReferenceToPerson(login, person);
			
			Group[] grupos=dao.getUniApiDao().getGroupDAO().findByType(GroupType.MAIN_GROUP);
			Group publicGroup = null;
			for(int i=0;i<grupos.length;i++){
				if(grupos[i].getName().compareTo("Public")==0){
					publicGroup=grupos[i];
				}
			}
			
			if(publicGroup!=null)
				dao.getActions().userKnowsGroup(login, publicGroup);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BussinessException("Fallo en la creación de la cuenta");
			
		}


	}

	@Override
	public Person getPerson(UserLogin user) throws BussinessException {
		// TODO Auto-generated method stub
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		try {
			return dao.getActions().getPersonReferenceToUserLogin(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en la creación de la cuenta");
		}
	}

}
