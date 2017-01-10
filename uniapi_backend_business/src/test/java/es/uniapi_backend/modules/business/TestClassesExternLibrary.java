package es.uniapi_backend.modules.business;

import java.util.ArrayList;

import es.uniapi.modules.execution_enviroment.model.Proyect;
import es.uniapi.modules.execution_enviroment.model.ProyectType;
import es.uniapi.modules.execution_enviroment.model.exceptions.ServiceException;
import es.uniapi.modules.execution_enviroment.model.infoService.InfoPython;
import es.uniapi.modules.execution_enviroment.service.factory.Impl.ServiceProgrammingFactory;
import es.uniapi.modules.execution_enviroment.service.programming.Intf.ServicePython;


public class TestClassesExternLibrary {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServicePython python;
		ServiceProgrammingFactory factory=new ServiceProgrammingFactory();
		
		
		Proyect proyectoPython=new Proyect("ProyectoBasico1","C:\\pruebasUniApi\\ProyectoBasico1", ProyectType.PYTHON);
		
		ArrayList<String> arguments=new ArrayList<String>();
		String responsePath="C:\\pruebasUniApi\\ProyectoBasico1\\respuesta";
		arguments.add("10");
		InfoPython info=new InfoPython("main.py", "output_Triangule_Pascal_10", "response_Triangule_Pascal_10", arguments);
		proyectoPython.getServiceInfo().add(info);
		
		try {
			python=factory.requestServicePython();
			python.InicializateService(proyectoPython);
			python.executedProyect(responsePath);
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
