package es.uniapi.modules.sessiongestion;

import es.uniapi.modules.apirest.model.Message;
import es.uniapi.modules.apirest.model.SessionGestionException;

public interface SessionGestor {

	public Message getSession(String user,String pass,String IP) throws SessionGestionException;
	public boolean checkSession(String tokenSession) throws SessionGestionException;
	
	public String[] getSessions();
}
