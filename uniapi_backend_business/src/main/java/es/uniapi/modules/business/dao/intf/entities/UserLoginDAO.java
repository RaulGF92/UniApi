package es.uniapi.modules.business.dao.intf.entities;

import es.uniapi.modules.model.UserLogin;;

public interface UserLoginDAO {

	public void create(UserLogin userLogin) throws Exception;
	public void delete(UserLogin userLogin) throws Exception;
	public UserLogin findByEmail(String Email)throws Exception;
	public UserLogin findByHashCode(String string) throws  Exception;
	public UserLogin[] findAll() throws Exception;
}
