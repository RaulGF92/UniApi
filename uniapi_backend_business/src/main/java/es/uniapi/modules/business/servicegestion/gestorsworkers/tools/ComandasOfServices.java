package es.uniapi.modules.business.servicegestion.gestorsworkers.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import es.uniapi.modules.business.exception.GestorServiceException;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService;
import es.uniapi.modules.model.Execution;

public class ComandasOfServices {
	/**
	 * @author Raúl García Fdz
	 * 
	 *         Comandas of services es una estructura de datos que organiza la
	 *         siguiente relación. Usuario------------------>Servicio | | v
	 *         Execution
	 * 
	 *         Se mantiene en ejecución y no en persistencia, por que la
	 *         modificación de esta estructura debe ser rapida.
	 * 
	 *         Para que no sea creado varias veces por los demas hilos se creara
	 *         un singleton. Tambien para la lectura y escritura se creara
	 *         archivos de adicción con concurrencia.
	 */
	private Semaphore mutex;

	private HashMap<String, ProgrammingService> comandas;

	private static boolean create;
	private static ComandasOfServices singleton;

	public void addComanda(Execution execution, ProgrammingService service) throws GestorServiceException {
		// Concurrent
		try {
			getMutex().acquire();
			try {
				// do something
				String hash=execution.hash();
				comandas.put(hash, service);
				System.out.println("Se ha agregado execution:"+execution.hash());
				System.out.println(hash);

			} finally {
				getMutex().release();
			}
		} catch (InterruptedException ie) {
			getMutex().release();
			throw new GestorServiceException("Fallo en la sincronización de comandas");
		}
	}

	public void updateServices(Execution execution, ProgrammingService service) throws GestorServiceException {
		// Concurrent
		try {
			getMutex().acquire();
			// do something
			comandas.put(execution.hash(), service);
			getMutex().release();
		} catch (InterruptedException ie) {
			getMutex().release();
			throw new GestorServiceException("Fallo en la sincronización de comandas");
		}
	}

	public ProgrammingService[] getAllProgrammingService() throws GestorServiceException {
		ProgrammingService[] response;
		// Concurrent
		try {
			getMutex().acquire();

			// do something
			response = comandas.values().toArray(new ProgrammingService[comandas.values().size()]);
			getMutex().release();
		} catch (InterruptedException ie) {
			getMutex().release();
			throw new GestorServiceException("Fallo en la sincronización de comandas");
		}
		return response;
	}

	public String[] getAllExecution() throws GestorServiceException {
		String[] executions;
		// Concurrent
		try {
			getMutex().acquire();

			// do something
			executions = comandas.keySet().toArray(new String[comandas.keySet().size()]);

			getMutex().release();
			System.out.println("--------------executions--------------------");
			for(int i=0;i<executions.length;i++){
				System.out.println(executions[i]);
			}
			System.out.println("----------------------------------");
		} catch (InterruptedException ie) {
			getMutex().release();
			throw new GestorServiceException("Fallo en la sincronización de comandas");
		}

		return executions;
	}

	public void deleteAll() throws GestorServiceException {
		try {
			getMutex().acquire();

			// do something
			this.comandas = new HashMap<String, ProgrammingService>();
			getMutex().release();
		} catch (InterruptedException ie) {
			getMutex().release();
			throw new GestorServiceException("Fallo en la sincronización de comandas");
		}

	}

	public static ComandasOfServices getCommandasOfService() {
		if (create)
			return singleton;
		singleton = new ComandasOfServices();
		create = true;
		return singleton;
	}

	private ComandasOfServices() {
		initSemaphore();
		this.comandas = new HashMap<String, ProgrammingService>();
		create = false;
	}

	private void initSemaphore() {
		// TODO Auto-generated method stub
		this.setMutex(new Semaphore(1));
	}

	public ProgrammingService getProgrammingService(String string) throws GestorServiceException {
		// TODO Auto-generated method stub
		try {
			getMutex().acquire();

			// do something
				ProgrammingService response=this.comandas.get(string);
			getMutex().release();
			return response;
		} catch (InterruptedException ie) {
			getMutex().release();
			throw new GestorServiceException("Fallo en la sincronización de comandas");
		}

	}

	public void deleteExecution(Execution execution) throws GestorServiceException {
		// TODO Auto-generated method stub
		try {
			getMutex().acquire();

			// do something
			this.comandas.remove(execution.hash());
			System.out.println("Se ha eliminado execution:"+execution.hash());
			getMutex().release();
		} catch (InterruptedException ie) {
			getMutex().release();
			throw new GestorServiceException("Fallo en el borrado de executions");
		}

	}

	public Semaphore getMutex() {
		return this.mutex;
	}

	public void setMutex(Semaphore mutex) {
		this.mutex = mutex;
	}

}
