package es.uniapi.modules.execution_enviroment.service;

import es.uniapi.modules.execution_enviroment.model.ServiceException;
import es.uniapi.modules.model.Project;


public interface GeneralService {

	public void inicializateService(Project project) throws ServiceException;
	boolean existProject() throws ServiceException;
	public String getAbsoluteProjectPath();
	public Project getProject();
	
}
