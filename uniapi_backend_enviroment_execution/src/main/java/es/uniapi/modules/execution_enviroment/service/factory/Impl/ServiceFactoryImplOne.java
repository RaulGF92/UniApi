package es.uniapi.modules.execution_enviroment.service.factory.Impl;

import es.uniapi.modules.execution_enviroment.model.ServiceException;
import es.uniapi.modules.execution_enviroment.service.especial.EspecialService;
import es.uniapi.modules.execution_enviroment.service.factory.ServiceFactory;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService;
import es.uniapi.modules.model.Proyect;

public class ServiceFactoryImplOne implements ServiceFactory{

	@Override
	public ProgrammingService getServiceForProject(Proyect proyect) throws ServiceException {
		// TODO Auto-generated method stub
		ProgrammingService response=null;
		
		switch(proyect.getType()){
			case PYTHON:
				response=new es.uniapi.modules.execution_enviroment.service.programming.Impl.ServicePython();
				break;
			case OCTAVE:
				response=new es.uniapi.modules.execution_enviroment.service.programming.Impl.ServiceOctave();
				break;
		};
		
		return response;
	}

	@Override
	public EspecialService getEspecialService() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
