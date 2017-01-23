package es.uniapi.tests.bussines.gestorworker;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import es.uniapi.modules.execution_enviroment.execution.Execution;
import es.uniapi.modules.execution_enviroment.model.TicketExecution;
import es.uniapi.modules.model.config.AppConfiguration;
import es.uniapi.modules.model.exception.ConfigurationException;

public class ComunciaciónConfiguración {
/**
 * Para este caso vamos a hacer un enlace en el archivo configuración para ver si 
 * accede correctamente al archivo.
 * 
 */
	public ComunciaciónConfiguración(){
		
	}
	
	public void make(){
		AppConfiguration config=new AppConfiguration();
		
		config.setAppSite(config.getConfigPath());
		config.setExecutionSite(config.getConfigPath()+"/data/tmpExecution");
		config.setMaxServicePerUser(7);
		config.setProyectSite(config.getConfigPath()+"/data/projects");
		config.setUrlDataBase("localhost:3030");
		config.setUserDataBase("neo4j");
		config.setPassDataBase("13101992");
		
		try {
			config.writeProperties(config.getConfigPath()+"data/conf/");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	 
}
