package es.uniapi.modules.business.servicegestion;

import java.io.BufferedReader;

import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Execution;
import es.uniapi.modules.model.Execution.ExecutionState;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.UserLogin;
import org.json.*;

public interface ServiceGestion {

	
	//Zona de gestion de ejecuciones
	public Execution getExecution(UserLogin user,String hash) throws BussinessException;
	public Execution[] getAllExecutionsRunning() throws BussinessException;
	public Execution[] getAllExecutionsFinish() throws BussinessException;
	public Project getProjectOfExecution(Execution execution) throws BussinessException;
	
	public Execution[] getAllUserRunningExecutions(UserLogin user) throws BussinessException;
	public Execution[] getAllUserExecutionsByState(UserLogin user,ExecutionState state) throws BussinessException;
	public Execution[] getAllUserExecutionsFinish(UserLogin user) throws BussinessException;
	
	public boolean isCreatorOfTheExecution(UserLogin user, Execution execution) throws BussinessException;
	
	/**
	 * Bajo una orden directa de un administrador, todos las ejecuciones seran paradas
	 * y las jerarquias de ejecuciones borradas.
	 * 
	 * @throws BussinessException
	 */
	public void cleanTheRepositories()throws BussinessException;
	
	/**
	 * Dada una orden de un adminsitrador o del propietario de la ejecucion. dicha ejecución podra ser parada
	 * 
	 * @param user
	 * @param execution
	 * @throws BussinessException
	 */
	public void stopedExecutionInCurrent(UserLogin user,Execution execution)throws BussinessException;
	
	/**
	 * 
	 * @param group
	 * @param project
	 * @param user
	 * @param inputs
	 * @return hash de la ejecucion
	 * @throws BussinessException
	 */
	String makeExecutionOfProject(Group group, Project project, UserLogin user, JSONObject inputs)
			throws BussinessException;
	
}
