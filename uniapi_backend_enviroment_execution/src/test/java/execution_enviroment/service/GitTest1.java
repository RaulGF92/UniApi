package execution_enviroment.service;

import execution_enviroment.model.Proyect;
import execution_enviroment.model.ProyectType;
import execution_enviroment.model.exceptions.ServiceException;
import execution_enviroment.model.infoService.InfoGit;
import execution_enviroment.service.factory.Impl.ServiceFileFactory;
import execution_enviroment.service.file.Impl.ServiceGit;

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
