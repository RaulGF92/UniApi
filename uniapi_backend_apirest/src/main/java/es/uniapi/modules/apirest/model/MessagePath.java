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

}
