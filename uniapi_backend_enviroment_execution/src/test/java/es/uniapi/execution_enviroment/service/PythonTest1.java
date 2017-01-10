package es.uniapi.execution_enviroment.service;

import java.util.ArrayList;

import es.uniapi.modules.execution_enviroment.model.Proyect;
import es.uniapi.modules.execution_enviroment.model.ProyectType;
import es.uniapi.modules.execution_enviroment.model.exceptions.ServiceException;
import es.uniapi.modules.execution_enviroment.model.infoService.InfoPython;
import es.uniapi.modules.execution_enviroment.service.ProgrammingService.ExecutionState;
import es.uniapi.modules.execution_enviroment.service.factory.Impl.ServiceProgrammingFactory;
import es.uniapi.modules.execution_enviroment.service.programming.Intf.ServicePython;

public class PythonTest1 {
	
	/**
	 * Prueba uno creacion de proyecto pyhton y ejecucion del programa
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		Proyect proyectoPython=new Proyect("ProyectoBasico1","C:\\pruebasUniApi\\ProyectoBasico1", ProyectType.PYTHON);
		
		ArrayList<String> arguments=new ArrayList<String>();
		String responsePath="C:\\pruebasUniApi\\ProyectoBasico1\\respuesta";
		arguments.add("900");
		InfoPython info=new InfoPython("main.py", "output_Triangule_Pascal_10", "response_Triangule_Pascal_10", arguments);
		proyectoPython.getServiceInfo().add(info);
		
		ServiceProgrammingFactory factory=new ServiceProgrammingFactory();
		try {
			ServicePython service=factory.requestServicePython();
			service.InicializateService(proyectoPython);
			service.executedProyect(responsePath);
			while(service.getState()==ExecutionState.Running){
				System.out.println("do something");
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}