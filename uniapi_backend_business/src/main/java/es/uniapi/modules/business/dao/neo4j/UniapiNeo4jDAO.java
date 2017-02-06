package es.uniapi.modules.business.dao.neo4j;

import es.uniapi.modules.business.dao.intf.UniapiDAO;
import es.uniapi.modules.business.dao.intf.entities.AplicationDAO;
import es.uniapi.modules.business.dao.intf.entities.GroupDAO;
import es.uniapi.modules.business.dao.intf.entities.PersonDAO;
import es.uniapi.modules.business.dao.intf.entities.ProyectDAO;
import es.uniapi.modules.business.dao.intf.entities.UserLoginDAO;
import es.uniapi.modules.business.dao.neo4j.entities.PersonNeo4j;
import es.uniapi.modules.business.dao.neo4j.entities.UserLoginNeo4j;
import es.uniapi.modules.business.dao.neo4j.relationship.MakeReferenceDAO;

public class UniapiNeo4jDAO implements UniapiDAO {

	@Override
	public PersonDAO getPersonDAO() {
		// TODO Auto-generated method stub
		return new PersonNeo4j();
	}

	@Override
	public UserLoginDAO getUserLoginDAO() {
		// TODO Auto-generated method stub
		return new UserLoginNeo4j();
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
	
	public MakeReferenceDAO getMakeReferenceDAO(){
		return new MakeReferenceDAO();
	}

}
