package es.uniapi_backend.modules.business.model;

import java.util.Date;

public class UsingOne {

	
	public UsingOne(String responsePATH, String outputPATH, String input, Date creationDate) {
		
		super();
		this.responsePATH = responsePATH;
		this.outputPATH = outputPATH;
		this.input = input;
		this.creationDate = creationDate;
		this.id=-1;
		this.finalizationDate=null;
	}

	public UsingOne(long id, String responsePATH, String outputPATH, String input, Date creationDate,
			Date finalizationDate) {
		super();
		this.id = id;
		this.responsePATH = responsePATH;
		this.outputPATH = outputPATH;
		this.input = input;
		this.creationDate = creationDate;
		this.finalizationDate = finalizationDate;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getResponsePATH() {
		return responsePATH;
	}
	public void setResponsePATH(String responsePATH) {
		this.responsePATH = responsePATH;
	}
	public String getOutputPATH() {
		return outputPATH;
	}
	public void setOutputPATH(String outputPATH) {
		this.outputPATH = outputPATH;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getFinalizationDate() {
		return finalizationDate;
	}
	public void setFinalizationDate(Date finalizationDate) {
		this.finalizationDate = finalizationDate;
	}
	
	private long id;
	
	private String responsePATH;
	private String outputPATH;
	
	private String input;
	
	private Date creationDate;
	private Date finalizationDate;
	
	@Override
	public String toString() {
		return "UsingOne [id=" + id + ", responsePATH=" + responsePATH + ", outputPATH=" + outputPATH + ", input="
				+ input + ", creationDate=" + creationDate + ", finalizationDate=" + finalizationDate + "]";
	}
	
}
