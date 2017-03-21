package es.uniapi.modules.business.servicegestion.gestorsworkers.tools;

import java.util.Date;

import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.execution_enviroment.service.programming.ProgrammingService.ExecutionState;
import es.uniapi.modules.model.Execution;

/**
 * 
 * @author raulgf92
 *	Bingo es uno de los fieles ayudantes del gestor, incansable y optimista.
 *  sera un hilo que husmeara entre las jerarquia de ejecuciones, buscando los archivos de ejecucion.
 *  Limpiara su basura,lo que mas le gusta. ^^
 * 
 */
public class Bingo extends Thread {

	private ComandasOfServices servicesOrderByUsers;
	private UniApiFactoryDAO dao=new UniApiFactoryDAO();
	private long timeToSleep=1000*60*60*60*24;
	public void run(){
		
		Execution[] exes;
		
		while(true){
			
			//Coje las ejecuciones finalizadas
			try {
				exes=dao.getUniApiDao()
				.getExecution()
				.findAllExecutionByState(
						es.uniapi.modules.model.Execution.ExecutionState.FINISH_SUCESS);
				cleanExecutions(exes);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//Si ha pasado un tiempo considerable
				//borra espacio de ejecucion
			
			//-----------------
			try {
				Thread.sleep(timeToSleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void cleanExecutions(Execution[] exes) {
		// TODO Auto-generated method stub
		for(int i=0;i<exes.length;i++){
			long diference=new Date().getTime()-exes[i].getFinishDate().getTime();
			if(diference>(timeToSleep*7))
				eraseTheSpace(exes[i]);
		}
	}

	private void eraseTheSpace(Execution execution) {
		// TODO Auto-generated method stub
		
	}
}
