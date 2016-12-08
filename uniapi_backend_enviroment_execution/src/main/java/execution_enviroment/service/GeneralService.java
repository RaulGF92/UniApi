package execution_enviroment.service;

import execution_enviroment.model.Proyect;
import execution_enviroment.model.exceptions.ServiceException;

public interface GeneralService {

	public void InicializateService(Proyect proyect) throws ServiceException;
	public boolean ExistProyect();
}
