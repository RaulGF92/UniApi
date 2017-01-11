package es.uniapi_backend.modules.model;

import java.util.Date;

public class UserLogin {

	public UserLogin(String user, String pass, Date creationDate, long id) {
		super();
		this.user = user;
		this.pass = pass;
		this.creationDate = creationDate;
		this.id = id;
	}

	private String user;
	private String pass;
	private Date creationDate;
	
	private long id;

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserLogin [user=" + user + ", pass=" + pass + ", creationDate=" + creationDate + ", id=" + id + "]";
	}
	
}
