package es.uniapi.modules.business.servicegestion;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.business.servicegestion.gestorsworkers.GestorWorkerMap;
import es.uniapi.modules.model.Execution;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Execution.ExecutionState;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.UserLogin;


public class ServiceGestionImpl implements ServiceGestion {
	
	private GestorWork gestorWork;
	
	public ServiceGestionImpl(){
		gestorWork=GestorWorkerMap.getGestorWorkerMap();
	}

	@Override
	public String makeExecutionOfProject(Group group,Project project, UserLogin user,JSONObject inputs) throws BussinessException {
		// TODO Auto-generated method stub
		if(group.getProjectProperties()[1].compareTo("NO")==0)
			throw new BussinessException("No se puede ejecutar este projecto, no tiene permisos");
		
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		
		if(group.getProjectProperties()[1].compareTo("NO")==0 
				&& group.getProjectProperties()[2].compareTo("NO")==0){
			JSONObject json=new JSONObject();
			JSONArray defaultInputs=new JSONArray(project.getDefaultInputs());
			json.put("inputs", defaultInputs);
			inputs=json;
		}
		
		Execution execution=new Execution(
				ExecutionState.RUNNING, 
				inputs.toString(), 
				new Date(), 
				null, 
				"", 
				"");
		
		//Creamos Ejecucion
		try {
			dao.getUniApiDao().getExecution().create(execution);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("No se puede crear la ejecucion");
		}
		
		//Enlazamos con su usuario
		try {
			dao.getActions().userLoginCreateExecution(user, execution);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("No se puede enlazar el usuario con la ejecucion");
		}
		
		//Enlazamos con el projecto
		try {
			dao.getActions().executionUseProject(execution, project);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("No se puede enlazar la ejecucion con el projecto");
		}
		
		
		//Se lo pasamos al gestor que trabajara con el
		gestorWork.reciveExecution(execution);
		
		
		return execution.hash();
	}

	@Override
	public Execution[] getAllExecutionsRunning() throws BussinessException {
		// TODO Auto-generated method stub
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		
		try {
			return dao.getUniApiDao().getExecution().findAllExecutionByState(ExecutionState.RUNNING);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en la obtención de ejecuciones");
			
		}
		
	}

	@Override
	public Execution[] getAllExecutionsFinish() throws BussinessException {
		// TODO Auto-generated method stub
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		Execution[] finishSetError;
		Execution[] finishSetSucess;
		
		try {
			finishSetSucess=dao.getUniApiDao().getExecution().findAllExecutionByState(ExecutionState.FINISH_SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en la obtención de ejecuciones");
		}
		
		try {
			finishSetError = dao.getUniApiDao().getExecution().findAllExecutionByState(ExecutionState.FINISH_WITH_ERROR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en la obtención de ejecuciones");
		}
		
		ArrayList<Execution> response=new ArrayList<Execution>();
		response.addAll(Arrays.asList(finishSetError));
		response.addAll(Arrays.asList(finishSetError));
		
		return response.toArray(new Execution[response.size()]);
		
	}

	@Override
	public Execution[] getAllUserRunningExecutions(UserLogin user) throws BussinessException {
		// TODO Auto-generated method stub
		ArrayList<Execution> executions;
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		Execution[] setWork;
		try {
			setWork = dao.getActions().getAllExecutionGeneratedByUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en la obtención de las ejecuciones del usuario");
		}
		executions=new ArrayList<Execution>(Arrays.asList(setWork));
		for(int i=0;i<executions.size();i++){
			if(executions.get(i).getStateOfExecution().compareTo(ExecutionState.RUNNING)!=0){
				executions.remove(i);
			}
		}
		
		return executions.toArray(new Execution[executions.size()]);
	}

	@Override
	public Execution[] getAllUserExecutionsByState(UserLogin user,ExecutionState state) throws BussinessException {
		// TODO Auto-generated method stub
		ArrayList<Execution> executions;
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		Execution[] setWork;
		try {
			setWork = dao.getActions().getAllExecutionGeneratedByUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en la obtención de las ejecuciones del usuario");
		}
		executions=new ArrayList<Execution>(Arrays.asList(setWork));
		for(int i=0;i<executions.size();i++){
			if(executions.get(i).getStateOfExecution().compareTo(state)!=0){
				executions.remove(i);
			}
		}
		
		return executions.toArray(new Execution[executions.size()]);
	}

	@Override
	public Execution[] getAllUserExecutionsFinish(UserLogin user) throws BussinessException {
		// TODO Auto-generated method stub
		ArrayList<Execution> executions;
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		Execution[] setWork;
		try {
			setWork = dao.getActions().getAllExecutionGeneratedByUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en la obtención de las ejecuciones del usuario");
		}
		executions=new ArrayList<Execution>(Arrays.asList(setWork));
		for(int i=0;i<executions.size();i++){
			if(executions.get(i).getStateOfExecution().compareTo(ExecutionState.FINISH_SUCESS)!=0){
				executions.remove(i);
			}
		}
		
		return executions.toArray(new Execution[executions.size()]);
	}

	@Override
	public boolean isCreatorOfTheExecution(UserLogin user,Execution execution) throws BussinessException {
		// TODO Auto-generated method stub
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		UserLogin userToCheck;
		
		try {
			userToCheck = dao.getActions().getUserOfExecution(execution);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en la busqueda del propietario de la ejecución");
		}
		
		if(userToCheck.getUser().compareTo(user.getUser())==0)
			return true;
		return false;
	}

	@Override
	public void cleanTheRepositories() throws BussinessException {
		// TODO Auto-generated method stub
		this.gestorWork.destroyFileExecutionHierarchy(true);
	}

	@Override
	public void stopedExecutionInCurrent(UserLogin user, Execution execution) throws BussinessException {
		// TODO Auto-generated method stub
		execution.setFinishDate(new Date());
		execution.setStateOfExecution(ExecutionState.FINISH_WITH_ERROR);
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		try {
			this.gestorWork.destroyExecution(execution);this.gestorWork.destroyExecution(execution);
			dao.getUniApiDao().getExecution().update(execution.hash(), execution);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en la destrucción de la ejecución en concreto");
		}
		
	}

	@Override
	public Execution getExecution(UserLogin user, String hash) throws BussinessException {
		// TODO Auto-generated method stub
		Execution execution;
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		try {
			execution=dao.getUniApiDao()
					.getExecution()
					.findByHash(hash);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en la obtención de ejecucion");
		}
		return execution;
	}

}
