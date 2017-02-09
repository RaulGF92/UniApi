package es.uniapi.modules.business.dao.neo4j.relationship.model;

import java.util.Date;

public class IsCreator {

	private String userLogin;
	private String project;
	private Date dateFrom;
	
	public IsCreator(String userLogin,String project,Date dateFrom){
		this.userLogin=userLogin;
		this.project=project;
		this.dateFrom=dateFrom;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
}
