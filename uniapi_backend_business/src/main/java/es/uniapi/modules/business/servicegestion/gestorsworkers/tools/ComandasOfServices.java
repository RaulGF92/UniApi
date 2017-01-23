package es.uniapi.modules.business.servicegestion.gestorsworkers.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import es.uniapi.modules.business.exception.GestorServiceException;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService;

public class ComandasOfServices {
	/**
	 * @author Raúl García Fdz
	 * 
	 * Comandas of services es una estructura de datos que organiza la siguiente
	 * relación.
	 * 			Usuario------------------>Servicio
	 * 						|
	 * 						|
	 * 						v
	 * 					Proyecto
	 * 
	 * Se mantiene en ejecución y no en persistencia, por que la modificación 
	 * de esta estructura debe ser rapida.

	 * Para que no sea creado varias veces por los demas hilos se creara un singleton.
	 * Tambien para la lectura y escritura se creara archivos de adicción con
	 * concurrencia.
	 */
	private Semaphore mutex;
	
	private int MAX_SERVICE_FOR_USER=5;
	private HashMap<Long, ArrayList<ProgrammingService>> comandas;
	private ArrayList<Long> usersActive;
	
	private static boolean create;
	private static ComandasOfServices singleton;
	
	public void addComanda(Long idUser,ProgrammingService service) throws GestorServiceException{
		//Concurrent
		try {
			  mutex.acquire();
			  try {
			    // do something
				  if(this.comandas.containsKey(idUser)){
					  ArrayList<ProgrammingService> services=comandas.get(idUser);
					  
					  if(services.size()<MAX_SERVICE_FOR_USER)
						  throw new GestorServiceException("Excedent of execution services");
					  
					  services.add(service);
					  comandas.put(idUser,services);
				  }else{
					  usersActive.add(idUser);
					  ArrayList<ProgrammingService>services=new ArrayList<ProgrammingService>();
					  services.add(service);
					  comandas.put(idUser,services);
				  }
					  
			  } finally {
			    mutex.release();
			  }
			} catch(InterruptedException ie) {
			  // ...
			}
	}
	
	public void updateServices(Long idUser,ArrayList<ProgrammingService> newServices){
		//Concurrent
		try {
			  mutex.acquire();
			  try {
			    // do something
				  comandas.put(idUser, newServices);
			  } finally {
			    mutex.release();
			  }
			} catch(InterruptedException ie) {
			  // ...
			}
	}
	
	public ArrayList<ProgrammingService> getAllServices(){
		ArrayList<ProgrammingService> response=new ArrayList<ProgrammingService>();
		//Concurrent
		try {
			  mutex.acquire();
			  try {
			    // do something
				  for(int i=0;i<this.usersActive.size();i++){
					response.addAll(this.comandas.get(usersActive.get(i)));  
				  }
				  
			  } finally {
			    mutex.release();
			  }
			} catch(InterruptedException ie) {
			  // ...
			}
		return response;
	}
	public ArrayList<ProgrammingService> getUserServices(Long id){
		ArrayList<ProgrammingService> response=new ArrayList<ProgrammingService>();
		//Concurrent
		try {
			  mutex.acquire();
			  try {
			    // do something
				  response=this.comandas.get(id);
				  
				  if(response == null)
					  response=new ArrayList<ProgrammingService>();
				  
			  } finally {
			    mutex.release();
			  }
			} catch(InterruptedException ie) {
			  // ...
			}
		
		return response;
	}
	
	public boolean existUser(Long id){
		
		if(this.getUserServices(id).size() > 0)
			return true;
		
		return false;
		
	}
	
	
	public void deleteAll(){
		try {
			  mutex.acquire();
			  try {
			    // do something
				  this.comandas=new HashMap<Long,ArrayList<ProgrammingService>>();
				  this.usersActive=new ArrayList<Long>();
			  } finally {
			    mutex.release();
			  }
			} catch(InterruptedException ie) {
			  // ...
			}
		
	}
	
	public static ComandasOfServices getCommandasOfService(){
		if(create)
			return singleton;
		singleton=new ComandasOfServices();
		create=true;
		return singleton;
	}
	
	private ComandasOfServices(){	
		initSemaphore();
		this.comandas=new HashMap<Long,ArrayList<ProgrammingService>>();
		this.usersActive=new ArrayList<Long>();
		create=false;
	}

	private void initSemaphore() {
		// TODO Auto-generated method stub
		this.mutex=new Semaphore(1);
	}
	
}