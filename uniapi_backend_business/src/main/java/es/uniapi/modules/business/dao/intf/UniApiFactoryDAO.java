package es.uniapi.modules.business.dao.intf;

import es.uniapi.modules.business.dao.neo4j.UniapiNeo4jActionsDAO;
import es.uniapi.modules.business.dao.neo4j.UniapiNeo4jDAO;

public class UniApiFactoryDAO {

	UniapiDAO uniApiDAO;
	UniapiNeo4jActionsDAO uniApiActionsDAO;
	
	public UniApiFactoryDAO(){
		this.uniApiDAO=new UniapiNeo4jDAO();
		this.uniApiActionsDAO=new UniapiNeo4jActionsDAO();
		//new posible features
	}
	
	public UniapiDAO getUniApiDao(){
		return uniApiDAO;
	}
	
	public UniapiActionsDAO getActions(){
		return uniApiActionsDAO;
	}
}
