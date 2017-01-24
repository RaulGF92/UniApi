package es.uniapi.modules.apirest.model;

public class Message {


	public Message(int state, String tokenSession) {
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
	
	private int state;
	private String tokenSession;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + state;
		result = prime * result + ((tokenSession == null) ? 0 : tokenSession.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (state != other.state)
			return false;
		if (tokenSession == null) {
			if (other.tokenSession != null)
				return false;
		} else if (!tokenSession.equals(other.tokenSession))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Message [state=" + state + ", tokenSession=" + tokenSession + "]";
	}
	
}
