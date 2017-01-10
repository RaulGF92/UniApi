package es.uniapi.modules.execution_enviroment.service.factory.Impl;

import es.uniapi.modules.execution_enviroment.model.exceptions.ServiceException;
import es.uniapi.modules.execution_enviroment.service.file.Intf.ServiceGit;

public class ServiceFileFactory implements es.uniapi.modules.execution_enviroment.service.factory.Intf.ServiceFileFactory {

	public ServiceGit requestServiceGit() throws ServiceException {
		// TODO Auto-generated method stub
		return new es.uniapi.modules.execution_enviroment.service.file.Impl.ServiceGit();
	}

}
