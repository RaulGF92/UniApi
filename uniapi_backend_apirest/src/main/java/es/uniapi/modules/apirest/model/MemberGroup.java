package es.uniapi.modules.apirest.model;

import java.util.Date;

public class MemberGroup {

	
	public MemberGroup(String user, String rol, Date since) {
		super();
		this.user = user;
		this.rol = rol;
		this.since = since;
	}
	
	private String user;
	private String rol;
	private Date since;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public Date getSince() {
		return since;
	}
	public void setSince(Date since) {
		this.since = since;
	}
}
