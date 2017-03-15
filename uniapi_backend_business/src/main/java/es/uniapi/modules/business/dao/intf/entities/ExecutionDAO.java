package es.uniapi.modules.business.dao.intf.entities;

import es.uniapi.modules.model.Execution;
import es.uniapi.modules.model.Execution.ExecutionState;

public interface ExecutionDAO {

	public void create(Execution execution)throws Exception;
	public void update(String hash,Execution execution)throws Exception;
	public Execution findByHash(String hash)throws Exception;
	public Execution[] findAllExecution()throws Exception;
	public Execution[] findAllExecutionByState(ExecutionState state)throws Exception;
	
	
}
