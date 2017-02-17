package es.uniapi.modules.business.groupgestion;

import es.uniapi.modules.business.dao.intf.UniApiFactoryDAO;
import es.uniapi.modules.business.exception.BussinessException;
import es.uniapi.modules.model.Group;
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
		
		if(!isAdmin || !knows)
			throw new BussinessException("El usuario esta intentando eliminar un grupo que no conoce");
		
		try{
			dao.getActions().deleteUserKnowsGroup(userLogin, group);
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

	

}
