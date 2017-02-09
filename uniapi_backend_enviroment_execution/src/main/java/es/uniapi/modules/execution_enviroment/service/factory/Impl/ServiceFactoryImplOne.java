package es.uniapi.modules.execution_enviroment.service.factory.Impl;

import java.util.concurrent.atomic.AtomicLong;

import es.uniapi.modules.execution_enviroment.model.ServiceException;
import es.uniapi.modules.execution_enviroment.service.especial.EspecialService;
import es.uniapi.modules.execution_enviroment.service.factory.ServiceFactory;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService;
import es.uniapi.modules.execution_enviroment.service.programming.Impl.ServiceOctave;
import es.uniapi.modules.execution_enviroment.service.programming.Impl.ServicePython;
import es.uniapi.modules.model.Project;

public class ServiceFactoryImplOne implements ServiceFactory{

	private AtomicLong servicesID=new AtomicLong();
	
	@Override
	public ProgrammingService getServiceForProject(Project project) throws ServiceException {
		// TODO Auto-generated method stub
			
		ProgrammingService response=null;
		long id=servicesID.incrementAndGet();
		switch(project.getType()){
			case PYTHON:
				response=new ServicePython(id);
				break;
			case OCTAVE:
				response=new ServiceOctave(id);
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
