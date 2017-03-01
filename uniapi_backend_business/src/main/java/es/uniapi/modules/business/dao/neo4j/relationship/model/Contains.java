package es.uniapi.modules.business.dao.neo4j.relationship.model;

import java.util.Date;

public class Contains {

	private String groupHash;
	private String projectHash;
	private Date since;
	
	public Contains(String groupHash, String projectHash, Date since) {
		super();
		this.groupHash = groupHash;
		this.projectHash = projectHash;
		this.since = since;
	}
	
	public String getGroupHash() {
		return groupHash;
	}
	public void setGroupHash(String groupHash) {
		this.groupHash = groupHash;
	}
	public String getProjectHash() {
		return projectHash;
	}
	
	public void setProjectHash(String projectHash) {
		this.projectHash = projectHash;
	}
	public Date getSince() {
		return since;
	}
	public void setSince(Date since) {
		this.since = since;
	}
}
