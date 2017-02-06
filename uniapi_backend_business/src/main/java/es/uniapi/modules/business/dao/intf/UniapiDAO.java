package es.uniapi.modules.business.dao.intf;

import es.uniapi.modules.business.dao.intf.entities.AplicationDAO;
import es.uniapi.modules.business.dao.intf.entities.GroupDAO;
import es.uniapi.modules.business.dao.intf.entities.PersonDAO;
import es.uniapi.modules.business.dao.intf.entities.ProyectDAO;
import es.uniapi.modules.business.dao.intf.entities.UserLoginDAO;

public interface UniapiDAO {

	/**
	 * @author raulgf92
	 * 
	 * DAO factory for access to the data
	 * 
	 */
	
	PersonDAO getPersonDAO();
	UserLoginDAO getUserLoginDAO();
	ProyectDAO getProyectDAO();
	GroupDAO getGroupDAO();
	AplicationDAO getAplicationDAO();
	
	
}
