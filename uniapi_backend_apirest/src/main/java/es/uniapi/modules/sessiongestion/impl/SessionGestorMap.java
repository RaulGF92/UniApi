package es.uniapi.modules.sessiongestion.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Semaphore;

import org.joda.time.DateTime;

import es.uniapi.modules.apirest.model.Message;
import es.uniapi.modules.apirest.model.SessionGestionException;
import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.dao.intf.UniapiDAO;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.model.config.SHA1;
import es.uniapi.modules.sessiongestion.SessionGestor;

public class SessionGestorMap implements SessionGestor {

	private static boolean create=false;
	private static SessionGestorMap gestor;
	
	public static SessionGestorMap getSessionGestor(){
		if(create)
			return gestor;
		gestor=new SessionGestorMap();
		create=true;
		return gestor;
	}
	
	HashMap<String,String[]> sessions; //o-> email 1-> date
	private final long TIME_LEAST_TO_RENOVE_ID=2000;
	private Limpito limpito;
	Semaphore semaphore;
	
	private SessionGestorMap() {
		// TODO Auto-generated constructor stub
		sessions=new HashMap<String,String[]>();
		semaphore=new Semaphore(1);
		limpito=new Limpito(sessions,TIME_LEAST_TO_RENOVE_ID,semaphore);
		limpito.start();
	}
	
	@Override
	public Message getSession(String user, String pass,String IP) throws SessionGestionException {
		// TODO Auto-generated method stub
		
		String token="Uniapi[TokenSession:"+IP+",CreationTime:"+new Date()+"]";
		String codificatedToken=SHA1.encryptPassword(token);
		
		//Call userLogin Exist
		UniapiDAO dao=new UniApiFactoryDAO().getUniApiDao();
		Message response;
		UserLogin userLogin;
		
		try {
			userLogin = dao.getUserLoginDAO().findByEmail(user);
			if(userLogin==null)
				return new Message(1,codificatedToken);
			System.out.println(userLogin.toString());
			System.out.println("pass:"+pass+" codificated:"+SHA1.encryptPassword(pass));
			if(userLogin.getPass().compareTo(SHA1.encryptPassword(pass))==0){
				//exist
				response=new Message(0,codificatedToken);
				semaphore.acquire();
				String[] info={user,new Date().toString()};
				sessions.put(token, info);
				semaphore.release();
			
			}else{
				//not exist
				response=new Message(2,codificatedToken);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response=new Message(1,token);
		}
		
		return response;
	}

	@Override
	public UserLogin checkSession(String tokenSession) throws SessionGestionException {
		// TODO Auto-generated method stub
		//Call userLogin Exist
		UniapiDAO dao=new UniApiFactoryDAO().getUniApiDao();
		UserLogin response=null;
		try {
			
		semaphore.acquire();
		if(!sessions.containsKey(tokenSession))
			return response;
		if(this.checkTimeToErase(new DateTime(sessions.get(tokenSession)[1]).toDate()))
			return response;
		String[] info=sessions.get(tokenSession);
		response=dao.getUserLoginDAO().findByEmail(info[0]);
		info[1]=new Date().toString();
		sessions.put(tokenSession, info);
		semaphore.release();
		return response;
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String[] getSessions() {
		// TODO Auto-generated method stub
		String[] response=null;
		try {
			semaphore.acquire();
			Set<String> setSessions=sessions.keySet();
			response=setSessions.toArray(new String[setSessions.size()]);
			semaphore.release();
			
			return response;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
		
	}
	public boolean checkTimeToErase(Date date) {

		long diferentTime = new Date().getTime() - date.getTime();
		if (diferentTime < (TIME_LEAST_TO_RENOVE_ID+(TIME_LEAST_TO_RENOVE_ID*0.002)))
			return true;
		return false;
	}

}
