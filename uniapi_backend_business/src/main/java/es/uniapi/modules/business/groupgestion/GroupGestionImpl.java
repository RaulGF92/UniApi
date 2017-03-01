package es.uniapi.modules.business.groupgestion;

import java.util.Date;

import es.uniapi.modules.business.Modules;
import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.dao.neo4j.UniapiNeo4jActionsDAO;
import es.uniapi.modules.business.dao.neo4j.relationship.IsSubgroupDAOImpl;
import es.uniapi.modules.business.dao.neo4j.relationship.KnowsDAOImpl;
import es.uniapi.modules.business.dao.neo4j.relationship.model.IsSubGroup;
import es.uniapi.modules.business.dao.neo4j.relationship.model.Knows;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Project;
import es.uniapi.modules.model.UserLogin;

public class GroupGestionImpl implements GroupGestion {

	

	@Override
	public Group findByHash(String hash) throws Exception {
		// TODO Auto-generated method stub
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		Group response=null;
		try{
			response=dao.getUniApiDao().getGroupDAO().findByHashCode(hash);
		}catch(Exception e){
			throw new BussinessException("Fallo en la bsuqueda de grupo por hash");
		}
		return response;
	}

	@Override
	public Group[] findAllGroups() throws Exception {
		// TODO Auto-generated method stub
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		Group[] response=null;
		try{
			response=dao.getUniApiDao().getGroupDAO().findAll();
		}catch(Exception e){
			throw new BussinessException("Fallo en la busqueda de todos los grupo");
		}
		return response;
	}
	@Override
	public void deleteGroup(UserLogin userLogin,Group group) throws Exception {
		// TODO Auto-generated method stub
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		
		boolean isAdmin=false;
		boolean knows=false;
		UserLogin[] users;
		
		if(userLogin.getRol().compareTo("admin")==0)
			isAdmin=true;
		
		try{
			users=dao.getActions().getAllUserKnowGroup(group);
		}catch(Exception e){
			throw new BussinessException("Fallo en la recoleccion de todos los usuarios que conocen el grupo");
		}
		
		
		if(isAdmin == false){
			for(int i=0;i<users.length;i++){
				if(users[i].getUser()==userLogin.getUser()){
					knows=true;
				}
			}
		}
		
		if(!isAdmin && !knows)
			throw new BussinessException("El usuario esta intentando eliminar un grupo que no conoce");
		
		try{
			//dao.getActions().deleteUserKnowsGroup(userLogin, group);
		}catch(Exception e){
			throw new BussinessException("Fallo en la eliminación de la relación Know del usuario principal:"+userLogin.toString());
		}
		
		
		if(dao.getActions().getUserOwnerOfGroup(group).getUser()==userLogin.getUser() || isAdmin){
			//Si es el propietario del grupo debera eliminar el grupo
			//Eliminamos todas los propietarios del grupo
			for(int i=0;i<users.length;i++){
				try{
					dao.getActions().deleteUserKnowsGroup(users[i], group);
				}catch(Exception e){
					throw new BussinessException("Fallo en la eliminación de la relación Know del usuario:"+users[i].toString());
				}
				
			}
			try{
				dao.getActions().deleteUserOwnerGroup(userLogin, group);
			}catch(Exception e){
				throw new BussinessException("Fallo en la eliminación de la relación de propiedad del usuario");
			}
			
			//eliminamos todos los hijos si tiene alguno
			
			
			//por ultimo eliminamos el grupo
			try{
				dao.getUniApiDao().getGroupDAO().delete(group);
			}catch(Exception e){
				throw new BussinessException("Fallo en la eliminación del grupo");
			}
		
		}
		
	}

	@Override
	public void updateGroup(UserLogin userLogin,String hash,Group group) throws Exception {
		// TODO Auto-generated method stub
		boolean isAdmin=false;
		boolean isOwner=false;
		
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		
		if(userLogin.getRol().compareTo("admin")==0)
			isAdmin=true;
		
		try{
			if(!isAdmin){
				if(dao.getActions().getUserOwnerOfGroup(group).getUser().compareTo(userLogin.getUser())==0)
					isOwner=true;
			}
		}catch(Exception e){
			new BussinessException("Fallo en la busqueda del propietario del grupo");
		}
		
		if(isAdmin || isOwner)
			dao.getUniApiDao().getGroupDAO().update(hash, group);
	}

	@Override
	public void createGroup(UserLogin userLogin, Group group) throws Exception {
		// TODO Auto-generated method stub
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		
		try{
			dao.getUniApiDao().getGroupDAO().create(group);
		}catch(Exception e){
			System.out.println("fallo en la creación de grupo");
			throw new BussinessException("Fallo en la creación del grupo");
		}
		
		try{
			dao.getActions().userCreateGroup(userLogin, group);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("fallo en el enlazado de propiedad");
			throw new BussinessException("Fallo en la creación del grupo");
		}
		
		/*
		 * Falta darle un padre al grupo que se acaba de crear es VITAL
		 * 
		 * Tambien modificar los permisos del grupo en función de los permisos del padre
		 * 
		 */
		
		try{
			dao.getActions().userKnowsGroup(userLogin, group);
		}catch(Exception e){
			System.out.println("fallo en el enlazado de propiedad");
			throw new BussinessException("Fallo en la creación del grupo");
		}
		
	}

	@Override
	public Group[] findAllUserGroup(UserLogin userLogin) throws Exception {
		// TODO Auto-generated method stub
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		Group[] groups=new Group[0];
		
		try{
			groups=dao.getActions().getGroupsCreateByUser(userLogin);
		}catch (Exception e) {
			// TODO: handle exception
			throw new BussinessException("Fallo en la busqueda de nodos");
		}
		
		return groups; 
	}

	@Override
	public UserLogin[] findAllMemberOfGroup(Group group) throws BussinessException {
		// TODO Auto-generated method stub
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		UserLogin[] users=new UserLogin[0];
		
		try {
			users=dao.getActions().getAllUserKnowGroup(group);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en la busqueda de miembos del grupo");
		}
		
		return users;
	}

	@Override
	public void makeUserMemberOfGroup(UserLogin user,UserLogin userToMake, Group group) throws BussinessException {
		// TODO Auto-generated method stub
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		GroupRole rol = this.getUserRoleOnGruop(user, group);
		
		if(rol!= GroupRole.NONE){
			if(group.getMemberGestion()[2].compareTo("YES")==0 || rol!=GroupRole.Member ){
				try {
					dao.getActions().userKnowsGroup(userToMake, group);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new BussinessException("Fallo en la eliminación de miembos del grupo");
				}
			}
		}
		
	}

	@Override
	public void deleteUserMemberOfGroup(UserLogin user,UserLogin userToDelete, Group group) throws BussinessException {
		// TODO Auto-generated method stub
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		GroupRole rol = this.getUserRoleOnGruop(user, group);
		
		if(rol!= GroupRole.NONE){
			if(group.getMemberGestion()[3].compareTo("YES")==0 || rol!=GroupRole.Member ){
				try {
					dao.getActions().deleteUserKnowsGroup(userToDelete, group);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new BussinessException("Fallo en la eliminación de miembos del grupo");
				}
			}
		}
		
	}
	@Override
	public GroupRole getUserRoleOnGruop(UserLogin user, Group group) throws BussinessException {
		// TODO Auto-generated method stub
		GroupRole role=GroupRole.VISITOR;
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		if(user.getRol().compareTo("admin")==0){
			role=GroupRole.ADMIN;
			return role;
		}
		
		try{
			if(dao.getActions().getUserOwnerOfGroup(group).getUser().compareTo(user.getUser())==0){
				role=GroupRole.OWNER;
				return role;
			}
			
		}catch(Exception e){
			new BussinessException("Fallo en la busqueda del propietario del grupo");
		}
		
		try {
			UserLogin[] users;
			users = dao.getActions().getAllUserKnowGroup(group);
		
			for(int i=0;i<users.length;i++){
				if(users[i].getUser().compareTo(user.getUser()) == 0){
					role=GroupRole.Member;
					return role;
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			new BussinessException("Fallo en la busqueda del conocedor del grupo");
		}
		
		return role;
	}

	@Override
	public Date getInfoMember(UserLogin user, Group group) throws BussinessException {
		KnowsDAOImpl dao=new KnowsDAOImpl();
		Knows know = null;
		try {
			know = dao.getInfoGroupKnowByUser(user, group);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			new BussinessException("Fallo en la busqueda de la información");
		}
		return know.getCreationTime();
	}

	@Override
	public IsSubGroup getInfoSubgroup(Group father, Group group) throws BussinessException {
		// TODO Auto-generated method stub
			IsSubgroupDAOImpl dao=new IsSubgroupDAOImpl();
			IsSubGroup isgroup=null;
			try{
				isgroup=dao.getInfo(father,group);
			}catch(Exception e){
				new BussinessException("Fallo en la busqueda de la información");
			}
		return isgroup;
	}

	@Override
	public Group[] getSubgroupsOfGroup(Group father) throws BussinessException {
		// TODO Auto-generated method stub
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		Group[] groups=null;
		
		try {
			groups=dao.getActions().getSubgroupsOfGroup(father);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en la busqueda de los subgrupos de un grupo");
		}
		
		return groups;
	}

	@Override
	public void groupIsSubgroupOfGroup(Group group, Group subgroup) throws BussinessException {
		// TODO Auto-generated method stub
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		
		try {
			dao.getActions().groupIsSubgroupOfGroup(group,subgroup);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en la creación de subgrupos");
		}
		
	}

	@Override
	public void deleteGroupIsSubgroupOfGroup(UserLogin userLogin,Group group, Group subgroup,GroupRole role) throws BussinessException {
		// TODO Auto-generated method stub
		GroupRole rol = null;
		if(role ==null || role.compareTo(GroupRole.Member)!=0){
			role=this.getUserRoleOnGruop(userLogin, group);
			if(role.compareTo(GroupRole.Member)==0)
				return;
		}
		
		rol=role;
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		
		Group[] subGroups;
		try {
			subGroups = dao.getActions().getAllSubgroupsOfGroup(subgroup);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en la busqueda de subgrupos");
		}
		
		for(int i=0;i<subGroups.length;i++){
			deleteGroupIsSubgroupOfGroup(userLogin,subgroup, subGroups[i],rol);
		}
		
		//Solo se puede hacer eso cuando sepamos de todos los subgrupos del subgrupo a eliminar
		try {
			dao.getActions().deleteGroupIsSubgroupOfGroup(group,subgroup);
			UserLogin owner=dao.getActions().getUserOwnerOfGroup(subgroup);
			Modules.getGroupModule().deleteGroup(owner, subgroup);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("Fallo en la creación de subgrupos");
		}
		
	}

	@Override
	public void putProjectIntoGroup(UserLogin user,Group group, Project project) throws BussinessException {
		// TODO Auto-generated method stub
		//Para poner un proyecto en un grupo debemos tener los permisos pertienentes
		
		/*Sharing Group Permissions:
		 *[0]:ALL;
		 *[1]:shareProjectsInGroup;
		 *[2]:removeProjectsInGroup;
		 *[3]:removeExternalProjectInGroup;
		*/
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		GroupRole rol=this.getUserRoleOnGruop(user, group);
		if((group.getSharingGroup()[0].compareTo("YES")==0 && group.getSharingGroup()[1].compareTo("YES")==0)
				||
				(rol.compareTo(GroupRole.ADMIN)==0 || rol.compareTo(GroupRole.OWNER)==0)){
				try {
					dao.getActions().putProjectIntoGroup(group, project);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new BussinessException("Fallo en el enlazado de projecto con grupos");
				}
		}
		
	}

	@Override
	public void deleteProjectIntoGroup(UserLogin user,Group group, Project project) throws BussinessException {
		// TODO Auto-generated method stub
		/*Sharing Group Permissions:
		 *[0]:ALL;
		 *[1]:shareProjectsInGroup;
		 *[2]:removeProjectsInGroup;
		 *[3]:removeExternalProjectInGroup;
		*/
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		GroupRole rol=this.getUserRoleOnGruop(user, group);
		if((group.getSharingGroup()[0].compareTo("YES")==0 && group.getSharingGroup()[2].compareTo("YES")==0)
				||
				(rol.compareTo(GroupRole.ADMIN)==0 || rol.compareTo(GroupRole.OWNER)==0 || rol.compareTo(GroupRole.VISITOR)==0)){
				try {
					if(rol.compareTo(GroupRole.VISITOR)==0 && group.getSharingGroup()[3].compareTo("NO")==0)
						return;
					dao.getActions().removeProjectOfGroup(group, project);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new BussinessException("Fallo en el borrado de projecto con grupos");
				}
		}
	}

	@Override
	public Project[] getAllProjectsIntoGroup(UserLogin user,Group group) throws BussinessException {
		// TODO Auto-generated method stub
		/*Sharing Group Permissions:
		 *[0]:ALL;
		 *[1]:shareProjectsInGroup;
		 *[2]:removeProjectsInGroup;
		 *[3]:removeExternalProjectInGroup;
		*/
		GroupRole rol=this.getUserRoleOnGruop(user, group);
		UniApiFactoryDAO dao=new UniApiFactoryDAO();
		try {
			return dao.getActions().getAllProjectsIntoGroup(group);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BussinessException("fallo en la devolución de projectos en grupos");
		}
	}


}
