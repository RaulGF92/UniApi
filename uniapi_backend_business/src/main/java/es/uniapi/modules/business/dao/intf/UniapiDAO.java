package es.uniapi.modules.business.dao.intf;

import es.uniapi.modules.business.dao.intf.entities.AplicationDAO;
import es.uniapi.modules.business.dao.intf.entities.ExecutionDAO;
import es.uniapi.modules.business.dao.intf.entities.GroupDAO;
import es.uniapi.modules.business.dao.intf.entities.PersonDAO;
import es.uniapi.modules.business.dao.intf.entities.ProjectDAO;
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
	ProjectDAO getProjectDAO();
	GroupDAO getGroupDAO();
	AplicationDAO getAplicationDAO();
	ExecutionDAO getExecution();
	
	
}
