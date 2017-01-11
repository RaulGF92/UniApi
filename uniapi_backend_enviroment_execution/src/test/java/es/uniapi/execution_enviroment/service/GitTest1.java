package es.uniapi.execution_enviroment.service;

import es.uniapi.modules.execution_enviroment.model.Proyect;
import es.uniapi.modules.execution_enviroment.model.ProyectType;
import es.uniapi.modules.execution_enviroment.model.exceptions.ServiceException;
import es.uniapi.modules.execution_enviroment.model.infoService.InfoGit;
import es.uniapi.modules.execution_enviroment.service.especial.Impl.ServiceGit;
import es.uniapi.modules.execution_enviroment.service.factory.Impl.ServiceFileFactory;

public class GitTest1 {
/**
 * Vamos a probar si el init es correcto y el pull
 * url: https://github.com/RaulGF92/prueba.git
 */
	public static void main(String[] args){
		Proyect proyectoPython=new Proyect("ProyectoBasico3","C:\\pruebasUniApi\\ProyectoBasico3", ProyectType.PYTHON);
		proyectoPython.getServiceInfo().add(new InfoGit("raulgf92@gmail.com","CalleFalsa123","https://github.com/RaulGF92/prueba.git"));
		ServiceFileFactory factory= new ServiceFileFactory();
		try {
			ServiceGit service=(ServiceGit) factory.requestServiceGit();
			service.InicializateService(proyectoPython);
			service.newProyect();
			
			
			service.loadProject();
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
