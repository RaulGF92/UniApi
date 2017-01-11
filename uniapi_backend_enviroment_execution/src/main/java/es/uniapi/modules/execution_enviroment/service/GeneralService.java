package es.uniapi.modules.execution_enviroment.service;

import es.uniapi.modules.execution_enviroment.model.ServiceException;
import es.uniapi.modules.model.Proyect;

public interface GeneralService {

	public void inicializateService(Proyect proyect) throws ServiceException;
	boolean existProyect() throws ServiceException;
	public String getAbsoluteProyectPath();
	
}
