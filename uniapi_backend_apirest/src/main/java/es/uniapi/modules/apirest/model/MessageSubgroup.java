package es.uniapi.modules.apirest.model;

public class MessageSubgroup extends Message {

	private Subgroup[] subgroups;

	public MessageSubgroup(int state, String tokenSession, String[] relatedIDs,Subgroup[] subgroups) {
		super(state, tokenSession, relatedIDs);
		// TODO Auto-generated constructor stub
		this.subgroups=subgroups;
	}

	public Subgroup[] getSubgroups() {
		return subgroups;
	}

	public void setSubgroups(Subgroup[] subgroups) {
		this.subgroups = subgroups;
	}

}
