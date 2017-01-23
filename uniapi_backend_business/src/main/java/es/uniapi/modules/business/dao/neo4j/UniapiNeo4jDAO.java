package es.uniapi.modules.business.dao.neo4j;

import es.uniapi.modules.business.dao.intf.AplicationDAO;
import es.uniapi.modules.business.dao.intf.GroupDAO;
import es.uniapi.modules.business.dao.intf.PersonDAO;
import es.uniapi.modules.business.dao.intf.ProyectDAO;
import es.uniapi.modules.business.dao.intf.UniapiDAO;
import es.uniapi.modules.business.dao.intf.UserLoginDAO;

public class UniapiNeo4jDAO implements UniapiDAO {

	@Override
	public PersonDAO getPersonDAO() {
		// TODO Auto-generated method stub
		return ;
	}

	@Override
	public UserLoginDAO getUserLoginDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProyectDAO getProyectDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupDAO getGroupDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AplicationDAO getAplicationDAO() {
		// TODO Auto-generated method stub
		return null;
	}

}
