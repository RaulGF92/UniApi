package es.uniapi.modules.business.dao.neo4j.relationship.model;

import java.util.Date;

import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.UserLogin;

public class Knows {

	private String userLoginHash;
	private String groupHash;
	private Date creationTime;
	
	public Knows(String userLogin,String group,Date creationTime){
		this.userLoginHash=userLogin;
		this.groupHash=group;
		this.creationTime=creationTime;
	}

	public String getUserLogin() {
		return userLoginHash;
	}

	public void setUserLogin(String userLogin) {
		this.userLoginHash = userLogin;
	}

	public String getGroup() {
		return groupHash;
	}

	public void setGroup(String group) {
		this.groupHash = group;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	
	
}
