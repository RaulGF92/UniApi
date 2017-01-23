package test;

import es.uniapi.modules.model.config.AppConfiguration;
import es.uniapi.modules.model.exception.ConfigurationException;

public class prueba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AppConfiguration config=new AppConfiguration();
		
		config.setAppSite(config.getConfigPath());
		config.setExecutionSite(config.getConfigPath()+"data/tmpExecution");
		config.setMaxServicePerUser(7);
		config.setProyectSite(config.getConfigPath()+"data/projects");
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
