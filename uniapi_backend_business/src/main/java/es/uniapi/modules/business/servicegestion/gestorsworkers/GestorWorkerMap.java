package es.uniapi.modules.business.servicegestion.gestorsworkers;


import es.uniapi.modules.business.exception.GestorServiceException;
import es.uniapi.modules.execution_enviroment.model.ServiceException;
import es.uniapi.modules.execution_enviroment.service.factory.ServiceFactory;
import es.uniapi.modules.execution_enviroment.service.factory.Impl.ServiceFactoryImplOne;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService.ExecutionState;
import es.uniapi.modules.model.*;
import es.uniapi.modules.model.Project.ProjectType;
import es.uniapi.modules.model.config.AppConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.DateTime;
import org.neo4j.graphdb.event.KernelEventHandler.ExecutionOrder;

import es.uniapi.modules.business.servicegestion.GestorWork;
import es.uniapi.modules.business.servicegestion.gestorsworkers.tools.Bingo;
import es.uniapi.modules.business.servicegestion.gestorsworkers.tools.ComandasOfServices;
import es.uniapi.modules.business.servicegestion.gestorsworkers.tools.Dobby;

public class GestorWorkerMap implements GestorWork{
 
	//private final String INSTALATION_PROJECT_PATH="C:\\UniApi\\data\\FileExecutionHierarchy";
	private final String INSTALATION_PROJECT_PATH;
	
	private static GestorWorkerMap singleton;
	private static boolean created=true;
	
	private Dobby theDobby;
	private Bingo theBingo; 
	private ComandasOfServices servicesOrderByUsers;
	private ServiceFactory factoria;
	
	public static GestorWorkerMap getGestorWorkerMap(){
		if(created)
			return singleton;
		singleton=new GestorWorkerMap();
		return singleton;
	}
	
	private GestorWorkerMap(){
		
		created=true;
		this.theDobby=null;
		this.theDobby=null;
		this.servicesOrderByUsers=ComandasOfServices.getCommandasOfService();
		this.factoria=new ServiceFactoryImplOne();
		this.INSTALATION_PROJECT_PATH=AppConfiguration.getConfiguration().getExecutionSite();
	}
	
	@Override
	public UsingOne createService(UserLogin user, Project proyect) throws GestorServiceException {
		// TODO Auto-generated method stub
		UsingOne usingOne=null;
		
		String serviceSpace;
		//Inicialización de la carpeta del usuario
		String userSpace=this.createNewUserExecutionHierarchy(user.getUser());
		ProgrammingService service;
		
		//Habria que preguntarse si existe el proyecto, en caso negativo crear el proyecto
		
		try {
		//Inicialización del servicio
		
			
			service=this.factoria.getServiceForProject(proyect);
			service.inicializateService(proyect);
			//Inicialización de la carpeta del servicio
			serviceSpace=this.treatmentForNewService(userSpace, service);
			
			//gochada
			String inputs="";
			for(int i=0;i<proyect.getDefaultInputs().length;i++){
				inputs=inputs+proyect.getDefaultInputs()[i]+";";
			}
			
			usingOne=new UsingOne(serviceSpace+"/"+proyect.getResponseName(),serviceSpace+"/"+proyect.getName()+"_UniApi_Output",inputs,new DateTime().toDate());
			
			this.servicesOrderByUsers.addComanda(user.getUser(),service);
			service.executedService(proyect.getDefaultInputs(),serviceSpace);
			
			
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//To the up layer
		return usingOne;
	}

	@Override
	public void DestroyService(UserLogin user,ProgrammingService service) throws GestorServiceException {
		// TODO Auto-generated method stub
		ArrayList<ProgrammingService> services=this.servicesOrderByUsers.getUserServices(user.getUser());
		for(int i=0;i<services.size();i++){
			ProgrammingService aux=services.get(i);
			if(aux.getId()==service.getId())
				if(aux.isWorking()){
					try {
						aux.stopedCurrentService();
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally {
						services.remove(i);
					}
				}
		}
	}

	@Override
	public String treatmentForNewService(String userPath, ProgrammingService service) throws GestorServiceException {
		// TODO Auto-generated method stub
		/**
		 * Contruiremos las carpetas siguiendo esta  composicion
		 * 
		 *		userPath/
		 *				projectName-idService/
		 *									 main
		 *									 resources
		 *
		 * ejem: /data/FileExecutionHierarchy/email@email.com/HelloWorld-21
		 */
		String response=userPath+"/"+service.getProject().getName()+"-"+service.getId();
		File userSpace=new File(userPath);
		if(userSpace.isDirectory()){
			File newService=new File(response);
			newService.mkdir();
		}
		return response;
	}

	@Override
	public String createNewUserExecutionHierarchy(String email) throws GestorServiceException {
		// TODO Auto-generated method stub
		/**
		 * Contruiremos las carpetas siguiendo esta  composicion
		 * 
		 *		data/
		 *				FileExecutionHierarchy/
		 *										idUser
		 *							
		 *
		 * ejem: /data/FileExecutionHierarchy/12443/
		 */
		String response=this.INSTALATION_PROJECT_PATH+"/"+email;
		
		if(servicesOrderByUsers.existUser(email)){
			return response;
		}
		
		File userSpace=new File(response);
		userSpace.mkdir();
		return response;
	}

	@Override
	public String createFileExecutionHierarchy() throws GestorServiceException {
		// TODO Auto-generated method stub
		File fileExecutionHierarchy=new File(INSTALATION_PROJECT_PATH);
		fileExecutionHierarchy.mkdirs();
		return INSTALATION_PROJECT_PATH;
	}

	@Override
	public ProgrammingService[] destroyFileExecutionHierarchy(boolean destroyALL) throws GestorServiceException {
		// TODO Auto-generated method stb
		
		ProgrammingService[] response=getAllActiveServices();
		if(response.length > 0 && destroyALL != true)
			return response;
		
		File fileExecutionHierarchy=new File(INSTALATION_PROJECT_PATH);
		cleanDirs(fileExecutionHierarchy.listFiles());
		fileExecutionHierarchy.delete();
		
		
		return null;
	}
	
	public void cleanDirs(File[] fileSons){
		for(int i=0;i<fileSons.length;i++){
			if(fileSons[i].isDirectory()){
				cleanDirs(fileSons[i].listFiles());
				fileSons[i].delete();
			}else{
				fileSons[i].delete();
			}
		}
	}

	@Override
	public ProgrammingService[] getAllActiveServices() throws GestorServiceException {
		// TODO Auto-generated method stub
		ArrayList<ProgrammingService> services=this.servicesOrderByUsers.getAllServices();
		ArrayList<ProgrammingService> activesServices=new ArrayList<ProgrammingService>();
		for(int i=0;i<services.size();i++){
			if(services.get(i).getState()==ExecutionState.Running){
				activesServices.add(services.get(i));
			}
		}
		ProgrammingService[] response=new ProgrammingService[activesServices.size()];
		return activesServices.toArray(response); 
	}

	@Override
	public ProgrammingService[] getUserActiveServices(String email) throws GestorServiceException {
		// TODO Auto-generated method stub
		ArrayList<ProgrammingService> services=this.servicesOrderByUsers.getUserServices(email);
		ArrayList<ProgrammingService> activesServices=new ArrayList<ProgrammingService>();
		for(int i=0;i<services.size();i++){
			if(services.get(i).getState()==ExecutionState.Running){
				activesServices.add(services.get(i));
			}
		}
		ProgrammingService[] response=new ProgrammingService[activesServices.size()];
		return activesServices.toArray(response); 
	}

	@Override
	public ProgrammingService[] getAllUserServices(String email) throws GestorServiceException {
		// TODO Auto-generated method stub
		ArrayList<ProgrammingService> services=this.servicesOrderByUsers.getUserServices(email);
		ProgrammingService[] response=new ProgrammingService[services.size()];
		return services.toArray(response); 
	}


}
