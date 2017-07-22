package es.uniapi.execution_enviroment.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class PythonTest1 {
	
	/**
	 * Prueba uno creacion de proyecto pyhton y ejecucion del programa
	 * 
	 * 
	 * @param args
	 */
	/*
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
	*/
	public static void main(String[] args) {
		/*String[] command= {"octave","--no-gui","--quiet",
				"/home/raulgf92/Escritorio/Proyectos"
				+ "/uniapi/data/projects/MultiplicarMatricesOctave_id[495d8f9fc050ce64412344a34fffd8ee25758f65]/multiplicarMatrices.m",
				"100","100",
				"/home/raulgf92/Escritorio/Proyectos/uniapi/data/tmpExecution/raulgf92@uniapi.es/MultiplicarMatricesOctave-2/"};
		*/
		String command="octave --no-gui --quiet /home/raulgf92/Escritorio/Proyectos/uniapi/data/projects/MultiplicarMatricesOctave_id[495d8f9fc050ce64412344a34fffd8ee25758f65]/multiplicarMatrices.m 100 100 /home/raulgf92/Escritorio/Proyectos/uniapi/data/tmpExecution/raulgf92@uniapi.es/MultiplicarMatricesOctave-2/";
		try {
			Process process = Runtime.getRuntime().exec(command);
			try {
				while(process.waitFor() !=0) {
					
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}