package es.uniapi.modules.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import es.uniapi.modules.model.exception.ConfigurationException;

public class Configuration {

	Properties prop;
	InputStream in;
	OutputStream out;
	
	public Configuration(InputStream in) throws IOException{
		prop=new Properties();
		this.in=in;
		prop.load(in);
		
	}
	
	public Configuration(OutputStream out) throws ConfigurationException{
		if(out==null)
			throw new ConfigurationException("The output isn't inicializated");
		prop=new Properties();
		this.out=out;
	}
	
	public String getProperties(String key) throws ConfigurationException{
		if(in == null)
			throw new ConfigurationException("the input isn't inicializated");
		return prop.getProperty(key);
	}
	
	public void setProperties(String key,String value) throws IOException{
		prop.put(key, value);
		prop.store(out, "Se ha realizado un cambio "+new Date().toString());
	}
	
	
	public void close() throws IOException{
		
		if(in!=null)
			in.close();
		if(out!=null)
			out.close();
		if(prop!=null)
			prop.clear();
	}
	
}
