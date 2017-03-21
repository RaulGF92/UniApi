package es.uniapi.modules.business.servicegestion.gestorsworkers;


import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
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
import java.util.Date;
import java.util.HashMap;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.neo4j.graphdb.event.KernelEventHandler.ExecutionOrder;

import es.uniapi.modules.business.servicegestion.GestorWork;
import es.uniapi.modules.business.servicegestion.gestorsworkers.tools.Bingo;
import es.uniapi.modules.business.servicegestion.gestorsworkers.tools.ComandasOfServices;
import es.uniapi.modules.business.servicegestion.gestorsworkers.tools.Dobby;

public class GestorWorkerMap implements GestorWork{
 
	//private final String INSTALATION_PROJECT_PATH="C:\\UniApi\\data\\FileExecutionHierarchy";
	private final String INSTALATION_PROJECT_PATH;
	
	private static GestorWorkerMap singleton;
	private static boolean created=false;
	
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
		this.theDobby=new Dobby();
		theDobby.start();
		this.servicesOrderByUsers=ComandasOfServices.getCommandasOfService();
		this.factoria=new ServiceFactoryImplOne();
		this.INSTALATION_PROJECT_PATH=AppConfiguration.getConfiguration().getExecutionSite();
	}
	
	@Override
	public UsingOne createService(UserLogin user, Project proyect,Execution execution) throws GestorServiceException {
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
			
			String inputs="";
			JSONObject obj=new JSONObject(execution.getInputJson());
			JSONArray arr=obj.getJSONArray("inputs");
			String[] v=new String[arr.length()];
			for(int i=0;i<arr.length();i++){
				inputs=inputs+arr.get(i)+";";
				v[i]=""+arr.get(i);
			}
			
			usingOne=new UsingOne(serviceSpace+"/"+proyect.getResponseName(),serviceSpace+"/"+proyect.getName()+"_UniApi_Output",inputs,new DateTime().toDate());
			
			this.servicesOrderByUsers.addComanda(execution,service);
			service.executedService(v,serviceSpace);
			
			
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//To the up layer
		return usingOne;
	}

	@Override
	public void DestroyService(Execution execution) throws GestorServiceException {
		// TODO Auto-generated method stub
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		
		try {
			this.servicesOrderByUsers.getProgrammingService(execution.hash()).stopedCurrentService();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw new GestorServiceException("No se ha podido parar el servicio");
		}
		execution.setStateOfExecution(es.uniapi.modules.model.Execution.ExecutionState.FINISH_WITH_ERROR);
		execution.setFinishDate(new Date());
		
		try {
			dao.getUniApiDao().getExecution().update(execution.hash(), execution);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new GestorServiceException("No se ha podido modificar la ejecucion, pero si parar el servicio");
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
		
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		ArrayList<ProgrammingService> arr=new ArrayList<ProgrammingService>();
		try {
			if(dao.getUniApiDao().getExecution().findAllExecution().length<0){
				throw new GestorServiceException("No existe jerarquia que borrar");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			throw new GestorServiceException("Fallo en la actualización de las ejecuciones");
		}
		
		String[] executions=servicesOrderByUsers.getAllExecution();
		for(int i=0;i<executions.length;i++){
			ProgrammingService aux=servicesOrderByUsers.getProgrammingService(executions[i]);
			if(aux.isWorking())
				arr.add(aux);
			if(destroyALL){
				try{
					aux.stopedCurrentService();
					Execution exe=dao.getUniApiDao()
							.getExecution()
							.findByHash(executions[i]);
					exe.setFinishDate(new Date());
					es.uniapi.modules.model.Execution.ExecutionState state = null;
					exe.setStateOfExecution(state.FINISH_WITH_ERROR);
					dao.getUniApiDao().getExecution().update(executions[i], exe);
				}catch (Exception e) {
					// TODO: handle exception
					throw new GestorServiceException("Fallo en la actualización de las ejecuciones");
				}
			}
				
			
		}
		
		if(!destroyALL)
			return arr.toArray(new ProgrammingService[arr.size()]);
			
		
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
	public void reciveExecution(Execution execution) throws GestorServiceException {
		// TODO Auto-generated method stub
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		Project project;
		UserLogin user;
		
		try {
			project=dao.getActions().getProjectUseForExecution(execution);
			user=dao.getActions().getUserOfExecution(execution);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new GestorServiceException("Fallo en la busqueda de la información adicional de la ejecucion");
		}
		
		this.createService(user, project, execution);
	}

	@Override
	public void destroyExecution(Execution execution) {
		// TODO Auto-generated method stub
		
	}


}
