package es.uniapi.modules.apirest.model;

import es.uniapi.modules.model.Group;

public class MessageGroup extends Message {

	private Group[] groups;
	
	public MessageGroup(int state, String tokenSession, String[] relatedIDs,Group[] groups) {
		super(state, tokenSession, relatedIDs);
		// TODO Auto-generated constructor stub
		this.groups=groups;
	}

	public Group[] getGroups() {
		return groups;
	}

	public void setGroups(Group[] groups) {
		this.groups = groups;
	}

}
