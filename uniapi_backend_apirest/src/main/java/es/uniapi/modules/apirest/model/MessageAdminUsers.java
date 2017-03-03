package es.uniapi.modules.apirest.model;

import es.uniapi.modules.model.UserLogin;

public class MessageAdminUsers extends Message {

	UserLogin[] users;
	public MessageAdminUsers(int state, String tokenSession, String[] relatedIDs,UserLogin[] users) {
		super(state, tokenSession, relatedIDs);
		// TODO Auto-generated constructor stub
		this.users=users;
	}
	public UserLogin[] getUsers() {
		return users;
	}
	public void setUsers(UserLogin[] users) {
		this.users = users;
	}

}
