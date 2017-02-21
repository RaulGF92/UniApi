package es.uniapi.modules.apirest.model;

public class MessageMemberGroup extends Message{

	MemberGroup[] users;
	
	public MessageMemberGroup(int state, String tokenSession, String[] relatedIDs,MemberGroup[] users) {
		super(state, tokenSession, relatedIDs);
		// TODO Auto-generated constructor stub
		this.users=users;
	}

	public MemberGroup[] getUsers() {
		return users;
	}

	public void setUsers(MemberGroup[] users) {
		this.users = users;
	}

}
