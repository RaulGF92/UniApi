package es.uniapi.modules.execution_enviroment.service.factory.Impl;

import es.uniapi.modules.execution_enviroment.model.exceptions.ServiceException;
import es.uniapi.modules.execution_enviroment.service.programming.Intf.ServiceOctave;
import es.uniapi.modules.execution_enviroment.service.programming.Intf.ServicePython;
import es.uniapi.modules.execution_enviroment.service.programming.Intf.ServiceR;

public class ServiceProgrammingFactory implements es.uniapi.modules.execution_enviroment.service.factory.Intf.ServiceProgrammingFactory {

	public ServicePython requestServicePython() throws ServiceException {
		// TODO Auto-generated method stub
		return new es.uniapi.modules.execution_enviroment.service.programming.Impl.ServicePython();
	}

	public ServiceOctave requestServiceOctave() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public ServiceR requestServiceR() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
