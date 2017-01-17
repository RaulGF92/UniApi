package es.uniapi.modules.execution_enviroment.service.factory.Impl;

import java.util.concurrent.atomic.AtomicLong;

import es.uniapi.modules.execution_enviroment.model.ServiceException;
import es.uniapi.modules.execution_enviroment.service.especial.EspecialService;
import es.uniapi.modules.execution_enviroment.service.factory.ServiceFactory;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService;
import es.uniapi.modules.model.Proyect;

public class ServiceFactoryImplOne implements ServiceFactory{

	private AtomicLong servicesID=new AtomicLong();
	
	@Override
	public ProgrammingService getServiceForProject(Proyect proyect) throws ServiceException {
		// TODO Auto-generated method stub
			
		ProgrammingService response=null;
		
		switch(proyect.getType()){
			case PYTHON:
				response=new es.uniapi.modules.execution_enviroment.service.programming.Impl.ServicePython(servicesID.incrementAndGet());
				break;
			case OCTAVE:
				response=new es.uniapi.modules.execution_enviroment.service.programming.Impl.ServiceOctave(servicesID.incrementAndGet());
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
