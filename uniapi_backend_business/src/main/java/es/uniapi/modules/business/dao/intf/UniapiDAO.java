package es.uniapi.modules.business.dao.intf;

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
