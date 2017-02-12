package es.uniapi.modules.apirest.model;

import java.util.Arrays;

public class Message {

	private int state;
	private String tokenSession;
	private String[] relatedIDs;

	public Message(int state, String tokenSession,String[] relatedIDs) {
		super();
		this.state = state;
		this.tokenSession = tokenSession;
	}
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getTokenSession() {
		return tokenSession;
	}
	public void setTokenSession(String tokenSession) {
		this.tokenSession = tokenSession;
	}
	

	@Override
	public String toString() {
		return "Message [state=" + state + ", tokenSession=" + tokenSession + ", relatedIDs="
				+ Arrays.toString(relatedIDs) + "]";
	}

	public String[] getRelatedIDs() {
		return relatedIDs;
	}

	public void setRelatedIDs(String[] relatedIDs) {
		this.relatedIDs = relatedIDs;
	}
	
}
