package es.uniapi.modules.execution_enviroment.service.especial;

import es.uniapi.modules.execution_enviroment.service.GeneralService;
import es.uniapi.modules.model.Proyect;

public abstract class EspecialService implements GeneralService {

	public String getAbsoluteProyectPath(Proyect proyect){
		
		//Cojer del archivo de propiedades el path de los proyectos
		String instalationProyectPath="C:\\UniApi\\proyects";
		
		String proyectsPath=proyect.getName()+"id["+proyect.getId()+"]";
		String response=instalationProyectPath+"\\"+proyectsPath;
		
		return response;
	}
	
}
