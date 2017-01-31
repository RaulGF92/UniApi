package es.uniapi.modules.model;

import java.util.Date;

public class UserLogin {

	public UserLogin(String user, String pass, Date creationDate, String rol) {
		super();
		this.user = user;
		this.pass = pass;
		this.creationDate = creationDate;
		this.rol = rol;
	}

	private String user;
	private String pass;
	private Date creationDate;
	
	private String rol;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "UserLogin [user=" + user + ", pass=" + pass + ", creationDate=" + creationDate + ", rol=" + rol + "]";
	}

}
