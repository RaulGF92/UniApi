package es.uniapi.modules.execution_enviroment.service.factory.Intf;

import es.uniapi.modules.execution_enviroment.model.exceptions.ServiceException;
import es.uniapi.modules.execution_enviroment.service.ServiceFactory;
import es.uniapi.modules.execution_enviroment.service.file.Intf.ServiceGit;

public interface ServiceFileFactory extends ServiceFactory {

	public ServiceGit requestServiceGit() throws ServiceException;
}
