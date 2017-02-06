package es.uniapi.modules.model;

import java.util.Date;

import es.uniapi.modules.model.config.SHA1;

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
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getProyectId() {
		return proyectId;
	}

	public void setProyectId(long proyectId) {
		this.proyectId = proyectId;
	}

	
	private long id;
	
	private long userId;
	private long proyectId;
	
	private String responsePATH;
	private String outputPATH;
	
	private String input;
	
	private Date creationDate;
	private Date finalizationDate;

	@Override
	public String toString() {
		return "UsingOne [id=" + id + ", userId=" + userId + ", proyectId=" + proyectId + ", responsePATH="
				+ responsePATH + ", outputPATH=" + outputPATH + ", input=" + input + ", creationDate=" + creationDate
				+ ", finalizationDate=" + finalizationDate + "]";
	}


	public String hash() {
		return SHA1.encryptPassword(this.toString());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsingOne other = (UsingOne) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (finalizationDate == null) {
			if (other.finalizationDate != null)
				return false;
		} else if (!finalizationDate.equals(other.finalizationDate))
			return false;
		if (id != other.id)
			return false;
		if (input == null) {
			if (other.input != null)
				return false;
		} else if (!input.equals(other.input))
			return false;
		if (outputPATH == null) {
			if (other.outputPATH != null)
				return false;
		} else if (!outputPATH.equals(other.outputPATH))
			return false;
		if (proyectId != other.proyectId)
			return false;
		if (responsePATH == null) {
			if (other.responsePATH != null)
				return false;
		} else if (!responsePATH.equals(other.responsePATH))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	
}
