package es.uniapi.modules.sessiongestion;

import es.uniapi.modules.apirest.model.Message;
import es.uniapi.modules.apirest.model.SessionGestionException;
import es.uniapi.modules.model.UserLogin;

public interface SessionGestor {

	public Message getSession(String user,String pass,String IP) throws SessionGestionException;
	public UserLogin checkSession(String tokenSession) throws SessionGestionException;
	public String[] getSessions();

}
