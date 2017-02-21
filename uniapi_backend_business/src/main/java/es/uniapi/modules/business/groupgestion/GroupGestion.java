package es.uniapi.modules.business.groupgestion;

import java.util.Date;

import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.UserLogin;

public interface GroupGestion {

	public void createGroup(UserLogin userLogin,Group group) throws Exception;
	public Group findByHash(String hash) throws Exception;
	public Group[] findAllGroups() throws Exception;
	public void deleteGroup(UserLogin user,Group group) throws Exception;
	public void updateGroup(UserLogin user,String hash,Group group) throws Exception;
	public Group[] findAllUserGroup(UserLogin userLogin)throws Exception;
	public UserLogin[] findAllMemberOfGroup(Group group)throws BussinessException;
	public void makeUserMemberOfGroup(UserLogin user,UserLogin userToAdd,Group group)throws BussinessException;
	public void deleteUserMemberOfGroup(UserLogin user,UserLogin userToDelete,Group group)throws BussinessException;
	public GroupRole getUserRoleOnGruop(UserLogin user,Group group)throws BussinessException;
	public Date getInfoMember(UserLogin user,Group group)throws BussinessException;
	
	public enum GroupRole{
		ADMIN,
		OWNER,
		Member,
		NONE
	};
}
