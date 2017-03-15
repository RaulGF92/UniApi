package es.uniapi.modules.business.servicegestion.gestorsworkers.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.json.JSONObject;

import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.business.exception.GestorServiceException;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService;
import es.uniapi.modules.model.Execution;
import es.uniapi.modules.model.Execution.ExecutionState;
import es.uniapi.modules.model.config.AppConfiguration;
import es.uniapi.modules.model.UserLogin;

/**
 * 
 * @author raulgf92
 *	Dobby es uno de los fieles ayudantes del gestor, incansable y optimista.
 *  sera un hilo que mirara si los servicios han acabado y realizara modificaciones.
 */
public class Dobby extends Thread {

	private final String INSTALATION_PROJECT_PATH;
	public void run(){
		
		try {
			//Thread.sleep(1000*60*1);
			Thread.sleep(1000*2);
			System.out.println("[Dobby]Dobby ha despertado.");
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		String[] executions;
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		while(true){
			try {
				executions=this.servicesOrderByUsers.getAllExecution();
				System.out.println("[Dobby] Executions:"+executions.length);
			} catch (GestorServiceException e1) {
				// TODO Auto-generated catch block
				System.out.println("[Dobby]:No he podido recoger las excepciones");
				executions=new String[0];
			}
			for(int i=0;i<executions.length;i++){
				try {
					makeExecution(
								dao.getUniApiDao()
								.getExecution()
								.findByHash(executions[i]));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("[Dobby]:No he podido realizar limpieza\n "
							+ "Execution:"+executions[i]);
					e.printStackTrace();
				}
			}
			
			try {
				Thread.sleep(1000*60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void makeExecution(Execution execution) throws GestorServiceException {
		// TODO Auto-generated method stub
		ProgrammingService service=this.servicesOrderByUsers.getProgrammingService(execution.hash());
		if(service==null){
			System.out.println("----------------------------------");
			System.out.println("Service null");
			System.out.println("execution hash:"+execution.hash());
			System.out.println("----------------------------------");
			return; 
		}
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		if(!service.isWorking()){
			service.setFinishServiceDate(new Date());
			service.setState(es.uniapi.modules.execution_enviroment.service
					.programming.ProgrammingService.ExecutionState.Stopped);
			try{
				UserLogin user=dao.getActions().getUserOfExecution(execution);
				File[] response=service.getResponse();
				execution.setResponse(saveResponse(response[0]));
				try{
					execution.setConsole(saveConsole(response[1]));
					execution.setStateOfExecution(ExecutionState.FINISH_SUCESS);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					execution.setConsole("");
					execution.setStateOfExecution(ExecutionState.FINISH_WITH_ERROR);
				}
				execution.setFinishDate(new Date());
				dao.getUniApiDao().getExecution().update(execution.hash(), execution);
				this.servicesOrderByUsers.deleteExecution(execution);
			}catch (Exception e) {
				// TODO: handle exception
				throw new GestorServiceException("No se ha podido obtener response");
			}
		}
	}
	
	private String saveConsole(File file) throws IOException {
		// TODO Auto-generated method stub
		String response="";
		FileReader reader=new FileReader(file);
		BufferedReader bf=new BufferedReader(reader);
		String cadena;
		while((cadena = bf.readLine())!=null) {
			 response=response.concat(cadena);	 
	    }
		bf.close();
		reader.close();
		return response;
	}
	private String saveResponse(File file) throws IOException {
		// TODO Auto-generated method stub
		String response="";
		FileReader reader=new FileReader(file);
		BufferedReader bf=new BufferedReader(reader);
		String cadena;
		while((cadena = bf.readLine())!=null) {
	          response=response.concat(cadena);	      
	    }
		bf.close();
		reader.close();
		return response;
	}
	public String getServiceSpace(UserLogin user, ProgrammingService service) throws GestorServiceException {
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
		
		String userPath=this.getUserSpace(user);
		String response=userPath+"/"+service.getProject().getName()+"-"+service.getId();
		File userSpace=new File(userPath);
		if(userSpace.exists()){
			return response;
		}
		throw new GestorServiceException("No se ha podido acceder a los recursos obtenidos por las ejecuciones");
		
	}
	
	
	public String getUserSpace(UserLogin user) throws GestorServiceException {
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
		String response=this.INSTALATION_PROJECT_PATH+"/"+user.getUser();
		
		File userSpace=new File(response);
		if(userSpace.exists())
			return response;
		throw new GestorServiceException("No existe el espacio del usuario");
	}
	
	public Dobby(){
		this.servicesOrderByUsers=ComandasOfServices.getCommandasOfService();
		this.INSTALATION_PROJECT_PATH=AppConfiguration.getConfiguration().getExecutionSite();
	}
	
	private ComandasOfServices servicesOrderByUsers;
}
