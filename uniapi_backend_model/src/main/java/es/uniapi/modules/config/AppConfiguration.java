package es.uniapi.modules.config;

import es.uniapi.modules.model.exception.ConfigurationException;
import es.uniapi.modules.model.exception.CriticalAppExcepction;
import java.io.*;
import java.util.Date;

public class AppConfiguration {

	/**
	 * @author Raúl García Fdz
	 * 
	 *         Archivo que centraliza todas las opciones de configuración que
	 *         seran almacenadas en un properties este properties sera creado en
	 *         un proceso de instalación.
	 * 
	 *         Se entiende que el proceso de instalación fue correcto y no hay
	 *         fallos. No obtante se integra un proceso de evaluación de los
	 *         properties
	 * 
	 *         -NOTA: Properties tiene que estar en buildPath del jar
	 */

	private final static String NAME_APP_PROPERTIES = "uniapiConfig.properties";
	private final static int DEFAULT_SERVICE_PER_USER = 7;
	private String configPath;
	private OS os = OS.NOT_ASK;

	Configuration read;
	Configuration write;

	/* Properties variables */
	private Date LastModification;
	private String KEY_LAST_MODIFICATION = "LAST_MODIFICATION";
	// main app
	private String appSite;
	private String KEY_APP_SITE = "APP_SITE";

	// Gestion service
	private String proyectSite;
	private String KEY_PROYECT_SITE = "PROYECT_SITE";

	private String executionSite;
	private String KEY_EXECUTION_SITE = "EXECUTION_SITE";

	private int maxServicePerUser;
	private String KEY_SERVICE_PER_USER = "SERVICE_PER_USER";

	// DAO info
	private String urlDataBase;
	private String KEY_URL_DB = "URL_DB";

	private String passDataBase;
	private String KEY_PASS_DB = "PASS_DB";

	private String userDataBase;
	private String KEY_USER_DB = "USER_DB";

	/*------------------------------------------------------*/

	private static AppConfiguration configuration;
	private static boolean created = false;

	public AppConfiguration() {
		File file = new File(" ");
		this.configPath = file.getAbsolutePath();

	}

	// singleton
	public static AppConfiguration getConfiguration() {
		if (created)
			return configuration;

		created = true;
		configuration = new AppConfiguration();
		return configuration;
	}

	/**
	 * @throws ConfigurationException 
	 * @throws CriticalException
	 *             si existe una excepción de este calibre se recomienda, con la
	 *             aplicación.
	 */

	public void loadProperties() throws CriticalAppExcepction, ConfigurationException {
		System.out.println("Uniapi is loading properties...");
		try {

			read = new Configuration(this.getInput());

			this.appSite = this.read.getProperties(KEY_APP_SITE);
			this.executionSite = this.read.getProperties(KEY_EXECUTION_SITE);
			this.maxServicePerUser = Integer.parseInt(this.read.getProperties(KEY_SERVICE_PER_USER));
			this.passDataBase = this.read.getProperties(KEY_PASS_DB);
			this.userDataBase = this.read.getProperties(KEY_USER_DB);
			this.urlDataBase = this.read.getProperties(KEY_URL_DB);

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CriticalAppExcepction("Fail loading the properties. The app need stop now!!");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.maxServicePerUser = DEFAULT_SERVICE_PER_USER;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CriticalAppExcepction("Fail loading the properties. The app need stop now!!");
		}finally{
			try {
				this.read.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new ConfigurationException("Can't close the INPUT file");
			}
			
		}

		this.toString();
	}
	
	public void writeProperties(String path) throws ConfigurationException{
		
		System.out.println("Uniapi is saving the properties\n in a new file\n "+path+this.NAME_APP_PROPERTIES);
		
		try {
			
			this.write=new Configuration(new FileOutputStream(path+this.NAME_APP_PROPERTIES));
			
			this.write.setProperties(KEY_LAST_MODIFICATION,new Date().toGMTString());
			this.write.setProperties(KEY_APP_SITE, appSite);
			this.write.setProperties(KEY_EXECUTION_SITE, executionSite);
			this.write.setProperties(KEY_PROYECT_SITE,proyectSite);
			this.write.setProperties(KEY_SERVICE_PER_USER, ""+maxServicePerUser);
			this.write.setProperties(KEY_URL_DB, urlDataBase);
			this.write.setProperties(KEY_PASS_DB, passDataBase);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ConfigurationException("Can't save the news properties");
		} finally{
			try {
				this.write.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new ConfigurationException("Can't close the OUTPUT file");
			}
		}
		
	}

	public OS getOS() {
		if (this.os == os.NOT_ASK)
			askSystemForOS();
		return this.os;
	}

	private void askSystemForOS() {
		// TODO Auto-generated method stub
		String operativeSystem = System.getProperty("os.name");
		if (operativeSystem.contains("Windows")) {
			this.os = OS.WINDOWS;
		} else {
			this.os = OS.LINUX;
		}
	}

	public OS getOs() {
		return os;
	}

	public void setOs(OS os) {
		this.os = os;
	}

	public String getAppSite() {
		return appSite;
	}

	public void setAppSite(String appSite) {
		this.appSite = appSite;
	}

	public String getProyectSite() {
		return proyectSite;
	}

	public void setProyectSite(String proyectSite) {
		this.proyectSite = proyectSite;
	}

	public String getExecutionSite() {
		return executionSite;
	}

	public void setExecutionSite(String executionSite) {
		this.executionSite = executionSite;
	}

	public int getMaxServicePerUser() {
		return maxServicePerUser;
	}

	public void setMaxServicePerUser(int maxServicePerUser) {
		this.maxServicePerUser = maxServicePerUser;
	}

	public String getUrlDataBase() {
		return urlDataBase;
	}

	public void setUrlDataBase(String urlDataBase) {
		this.urlDataBase = urlDataBase;
	}

	public String getPassDataBase() {
		return passDataBase;
	}

	public void setPassDataBase(String passDataBase) {
		this.passDataBase = passDataBase;
	}

	public String getUserDataBase() {
		return userDataBase;
	}

	public void setUserDataBase(String userDataBase) {
		this.userDataBase = userDataBase;
	};

	public OutputStream getOutput() throws FileNotFoundException {
		OutputStream in = new FileOutputStream(this.appSite + this.NAME_APP_PROPERTIES);
		return in;
	}

	public InputStream getInput() throws FileNotFoundException {
		InputStream in = new FileInputStream(this.appSite + this.NAME_APP_PROPERTIES);
		return in;
	}

	public enum OS {
		WINDOWS, LINUX, NOT_ASK
	}
}
