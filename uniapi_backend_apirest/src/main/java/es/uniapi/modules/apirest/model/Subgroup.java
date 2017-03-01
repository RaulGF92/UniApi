package es.uniapi.modules.apirest.model;

import java.util.Date;

public class Subgroup {

	private String nameSubgroup;
	private String hashcode;
	private String Type;
	private Date since;
	
	public Subgroup(String nameSubgroup, String hashcode, String type, Date since) {
		super();
		this.nameSubgroup = nameSubgroup;
		this.hashcode = hashcode;
		Type = type;
		this.since = since;
	}
	
	public String getNameSubgroup() {
		return nameSubgroup;
	}
	public void setNameSubgroup(String nameSubgroup) {
		this.nameSubgroup = nameSubgroup;
	}
	public String getHashcode() {
		return hashcode;
	}
	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public Date getSince() {
		return since;
	}
	public void setSince(Date since) {
		this.since = since;
	}
}
