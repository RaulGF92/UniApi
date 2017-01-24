package es.uniapi.modules.business.dao.intf;

import es.uniapi.modules.business.dao.neo4j.UniapiNeo4jDAO;

public class UniApiFactoryDAO {

	UniapiDAO uniApiDAO;
	
	public UniApiFactoryDAO(){
		this.uniApiDAO=new UniapiNeo4jDAO();
		//new posible features
	}
	
	public UniapiDAO getUniApiDao(){
		return uniApiDAO;
	}
}
