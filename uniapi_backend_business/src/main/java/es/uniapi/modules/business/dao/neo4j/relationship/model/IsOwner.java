package es.uniapi.modules.business.dao.neo4j.relationship.model;

import java.util.Date;

public class IsOwner {

	private String userLoginHash;
	private String groupHash;
	private Date creationTime;
	
	public String getUserLoginHash() {
		return userLoginHash;
	}
	public void setUserLoginHash(String userLoginHash) {
		this.userLoginHash = userLoginHash;
	}
	public String getGroupHash() {
		return groupHash;
	}
	public void setGroupHash(String groupHash) {
		this.groupHash = groupHash;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	
	public IsOwner(String userLoginHash, String groupHash, Date creationTime) {
		super();
		this.userLoginHash = userLoginHash;
		this.groupHash = groupHash;
		this.creationTime = creationTime;
	}
	
	
}
