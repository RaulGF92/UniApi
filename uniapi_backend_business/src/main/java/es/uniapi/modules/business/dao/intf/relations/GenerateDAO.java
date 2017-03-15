package es.uniapi.modules.business.dao.intf.relations;

import es.uniapi.modules.model.Execution;
import es.uniapi.modules.model.UserLogin;

public interface GenerateDAO {

	public void create(UserLogin userLogin,Execution execution)throws Exception;
	public UserLogin getGeneratorOfExecution(Execution execution)throws Exception;
	Execution[] getUserExecution(UserLogin userLogin) throws Exception;
}
