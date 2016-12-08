package execution_enviroment.service;

import java.util.ArrayList;

import execution_enviroment.model.Proyect;
import execution_enviroment.model.ProyectType;
import execution_enviroment.model.exceptions.ServiceException;
import execution_enviroment.model.infoService.InfoPython;
import execution_enviroment.service.ProgrammingService.ExecutionState;
import execution_enviroment.service.factory.Impl.ServiceProgrammingFactory;
import execution_enviroment.service.programming.Intf.ServicePython;

public class PythonTest2 {

	/**
	 * Prueba dos creacion de proyecto pyhton y ejecucion del programa.
	 * El programa es muy pesado, su duración sera muy alta. Probaremos si 
	 * el programa se pude para y tenemos control sobre el mismo.
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		Proyect proyectoPython=new Proyect("ProyectoBasico2","C:\\pruebasUniApi\\ProyectoBasico2", ProyectType.PYTHON);
		
		ArrayList<String> arguments=new ArrayList<String>();
		String responsePath="C:\\pruebasUniApi\\ProyectoBasico2\\respuesta";
		arguments.add("1000");
		InfoPython info=new InfoPython("main.py", "output_Program_Pesado_1000", "response_Program_Pesado_1000", arguments);
		proyectoPython.getServiceInfo().add(info);
		
		ServiceProgrammingFactory factory=new ServiceProgrammingFactory();
		try {
			ServicePython service=factory.requestServicePython();
			service.InicializateService(proyectoPython);
			service.executedProyect(responsePath);
			
			int contador=0;
			while(contador<1000){
				System.out.println("do something");
				contador++;
			}
			System.out.println("we gonna stoped the program");
			if(service.getState()==ExecutionState.Running){
				service.stopedCurrentExecution();
			}
				
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
