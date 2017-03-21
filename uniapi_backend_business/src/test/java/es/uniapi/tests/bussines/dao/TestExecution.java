package es.uniapi.tests.bussines.dao;

import java.util.Date;

import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.dao.intf.UniapiDAO;
import es.uniapi.modules.business.dao.neo4j.UniapiNeo4jDAO;
import es.uniapi.modules.model.Execution;
import es.uniapi.modules.model.Execution.ExecutionState;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.UserLogin;

public class TestExecution {

	public static void main(String[] args){
		UniApiFactoryDAO dao=
				new UniApiFactoryDAO();
		
		UserLogin raul=null;
		Project matrices=null;
		
		try{
			raul=dao.getUniApiDao().getUserLoginDAO()
					.findByEmail("raulgf92@gmail.com");
			matrices=dao.getUniApiDao().getProjectDAO()
					.findByHashCode("7dab52161cc549d94c3d63537ee9c51ab9ef61ef");
		}catch(Exception e){
			System.err.println("Error en la obtencion de clases auxiliares");
			
		}
		
		Execution execution=new Execution("prueba","prueba",ExecutionState.START,
				"", new Date(), null, "", "");
		try {
			dao.getUniApiDao().getExecution().create(execution);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			dao.getActions()
			.userLoginCreateExecution(raul, execution);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			dao.getActions()
			.executionUseProject(execution, matrices);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Execution[] executions=dao.getActions().
			getAllExecutionGeneratedByUser(raul);
			for(int i=0;i<executions.length;i++){
				System.out.println(executions[i].toString());
				System.out.println("Projecto utilizado por la ejecucion");
				System.out.println(dao.getActions()
						.getProjectUseForExecution(executions[i])
						.getName());
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
