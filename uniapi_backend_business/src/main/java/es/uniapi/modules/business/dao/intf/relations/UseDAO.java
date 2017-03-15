package es.uniapi.modules.business.dao.intf.relations;

import es.uniapi.modules.model.Execution;
import es.uniapi.modules.model.Project;

public interface UseDAO {

	public void create(Execution execution,Project project)throws Exception;
	public Project getProjectUseForExecution(Execution execution)throws Exception;
	public Execution[] getExecutionsUsingProject(Project project)throws Exception;
	
}
