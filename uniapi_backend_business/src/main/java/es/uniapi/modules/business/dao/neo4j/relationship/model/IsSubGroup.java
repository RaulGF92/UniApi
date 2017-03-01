package es.uniapi.modules.business.dao.neo4j.relationship.model;

import java.util.Date;

public class IsSubGroup {

	public IsSubGroup(String asString, String asString2, Date date) {
		// TODO Auto-generated constructor stub
		this.since=date;
		this.groupHash=asString;
		this.subgroupHash=asString2;
	}
	
	private String groupHash;
	private String subgroupHash;
	private Date since;
	
	public String getGroupHash() {
		return groupHash;
	}
	public void setGroupHash(String groupHash) {
		this.groupHash = groupHash;
	}
	public String getSubgroupHash() {
		return subgroupHash;
	}
	public void setSubgroupHash(String subgroupHash) {
		this.subgroupHash = subgroupHash;
	}
	public Date getSince() {
		return since;
	}
	public void setSince(Date since) {
		this.since = since;
	}
	@Override
	public String toString() {
		return "IsSubGroup [groupHash=" + groupHash + ", subgroupHash=" + subgroupHash + ", since=" + since + "]";
	}
}
