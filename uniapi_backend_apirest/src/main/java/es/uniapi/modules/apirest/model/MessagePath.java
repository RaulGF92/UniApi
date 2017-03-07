package es.uniapi.modules.apirest.model;

public class MessagePath extends Message {

	private String objetiveGroup;
	private String[] groupIDs;
	private String[] projectIDs;
	
	public MessagePath(int state,String tokenSession,String[] relatedIDs,String objetiveGroup,String[] groupIDs,String[] projectsIDs) {
		super(state, tokenSession, relatedIDs);
		// TODO Auto-generated constructor stub
		this.objetiveGroup=objetiveGroup;
		this.groupIDs=groupIDs;
		this.projectIDs=projectsIDs;
	}

	public String getObjetiveGroup() {
		return objetiveGroup;
	}

	public void setObjetiveGroup(String objetiveGroup) {
		this.objetiveGroup = objetiveGroup;
	}

	public String[] getGroupIDs() {
		return groupIDs;
	}

	public void setGroupIDs(String[] groupIDs) {
		this.groupIDs = groupIDs;
	}

	public String[] getProjectIDs() {
		return projectIDs;
	}

	public void setProjectIDs(String[] projectIDs) {
		this.projectIDs = projectIDs;
	}

}
