package es.uniapi.modules.apirest.model;

import es.uniapi.modules.model.Execution;

public class MessageExecution extends Message {

	private Execution[] executions;

	public MessageExecution(int state, String tokenSession, String[] relatedIDs,Execution[] executions) {
		super(state, tokenSession, relatedIDs);
		// TODO Auto-generated constructor stub
		this.executions=executions;
	}

	public Execution[] getExecutions() {
		return executions;
	}

	public void setExecutions(Execution[] executions) {
		this.executions = executions;
	}

}
