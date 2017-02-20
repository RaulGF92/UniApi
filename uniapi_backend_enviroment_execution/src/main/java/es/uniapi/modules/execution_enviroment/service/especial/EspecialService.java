package es.uniapi.modules.execution_enviroment.service.especial;

import es.uniapi.modules.execution_enviroment.service.GeneralService;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.config.AppConfiguration;

public abstract class EspecialService extends Thread implements GeneralService {

	public String getAbsoluteProjectPath(Project project){
		
		//Cojer del archivo de propiedades el path de los proyectos
		String instalationProjectPath=AppConfiguration.getConfiguration().getProyectSite();
		
		String projectsPath=project.getName()+"_id["+project.hash()+"]";
		String response=instalationProjectPath+"/"+projectsPath;
		
		return response;
	}
	
}
