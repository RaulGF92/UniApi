package es.uniapi.modules.model;

import java.util.Date;

import es.uniapi.modules.model.config.SHA1;

public class Execution {



	private String nameExecution;
	private String groupOfExecution;
	private ExecutionState stateOfExecution;
	private String inputJson;
	private Date creationDate;
	private Date finishDate;
	private String response;
	private String console;
	private String hashcode;
	
	public Execution(String nameExecution,String groupOfExecution,ExecutionState stateOfExecution, String inputJson, Date creationDate, Date finishDate,
			String response, String console) {
		super();
		this.nameExecution=nameExecution;
		this.groupOfExecution=groupOfExecution;
		this.stateOfExecution = stateOfExecution;
		this.inputJson = inputJson;
		this.creationDate = creationDate;
		this.finishDate = finishDate;
		this.response = response;
		this.console = console;
		this.hashcode=this.hash();
	}
	
	public enum ExecutionState{
		START,
		RUNNING,
		FINISH_WITH_ERROR,
		FINISH_SUCESS
		
	}
	
	public String hash(){
		return SHA1.encryptPassword(this.toString());
	}
	
	public ExecutionState getStateOfExecution() {
		return stateOfExecution;
	}

	public void setStateOfExecution(ExecutionState stateOfExecution) {
		this.stateOfExecution = stateOfExecution;
	}

	public String getInputJson() {
		return inputJson;
	}

	public void setInputJson(String inputJson) {
		this.inputJson = inputJson;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	
	@Override
	public String toString() {
		return "Execution [inputJson=" + inputJson + ", creationDate=" + creationDate + "]";
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getConsole() {
		return console;
	}

	public void setConsole(String console) {
		this.console = console;
	}

	public String getNameExecution() {
		return nameExecution;
	}

	public void setNameExecution(String nameExecution) {
		this.nameExecution = nameExecution;
	}

	public String getGroupOfExecution() {
		return groupOfExecution;
	}

	public void setGroupOfExecution(String groupOfExecution) {
		this.groupOfExecution = groupOfExecution;
	}

	public String getHashcode() {
		return hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	};
}
