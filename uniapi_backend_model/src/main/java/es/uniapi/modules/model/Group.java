package es.uniapi.modules.model;

import java.util.Arrays;
import java.util.Date;

import es.uniapi.modules.model.config.SHA1;

public class Group {

	public Group(String name, Date creationDate, GroupType type, String[] sharingGroup, String[] projectProperties,
			String[] memberGestion, String[] groupCreation) {
		super();
		this.name = name;
		this.creationDate = creationDate;
		this.type = type;
		this.sharingGroupPermissions = sharingGroup;
		this.projectPropertiesPermissions = projectProperties;
		this.memberGestionPermissions = memberGestion;
		this.groupCreationPermissions = groupCreation;
	}

	private String name;
	private Date creationDate;
	private GroupType type;
	
	private String[] sharingGroupPermissions; 
	//[0]:sharingGroup;[1]:shareProjectsInGroup;[2]:removeProjectsInGroup;
	private String[] projectPropertiesPermissions;
	//[0]:executionProjects,[1]:modifyInputsParams
	private String[] memberGestionPermissions;
	//[0]:memberGestion;[1]:addMember;[2]:removeMember;
	private String[] groupCreationPermissions;
	//[0]:creationGroup;[1]:createRestrictedGroup;[2]createPublicGroup;
	
	public enum GroupType{
		MAIN_GROUP,
		PUBLIC_GROUP,
		RESTRICTED_RESTRICTED,
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public GroupType getType() {
		return type;
	}

	public void setType(GroupType type) {
		this.type = type;
	}

	public String[] getSharingGroup() {
		return sharingGroupPermissions;
	}

	public void setSharingGroup(String[] sharingGroup) {
		this.sharingGroupPermissions = sharingGroup;
	}

	public String[] getProjectProperties() {
		return projectPropertiesPermissions;
	}

	public void setProjectProperties(String[] projectProperties) {
		this.projectPropertiesPermissions = projectProperties;
	}

	public String[] getMemberGestion() {
		return memberGestionPermissions;
	}

	public void setMemberGestion(String[] memberGestion) {
		this.memberGestionPermissions = memberGestion;
	}

	public String[] getGroupCreation() {
		return groupCreationPermissions;
	}

	public void setGroupCreation(String[] groupCreation) {
		this.groupCreationPermissions = groupCreation;
	}

	public String hash(){
		return SHA1.encryptPassword(especialtoString());
	}
	public String especialtoString(){
		return "Group [name=" + name + ", creationDate=" + creationDate + "]";
	}
	@Override
	public String toString() {
		return "Group [name=" + name + ", creationDate=" + creationDate + ", type=" + type + ", sharingGroup="
				+ Arrays.toString(sharingGroupPermissions) + ", projectProperties=" + Arrays.toString(projectPropertiesPermissions)
				+ ", memberGestion=" + Arrays.toString(memberGestionPermissions) + ", groupCreation="
				+ Arrays.toString(groupCreationPermissions) + "]";
	}
}
