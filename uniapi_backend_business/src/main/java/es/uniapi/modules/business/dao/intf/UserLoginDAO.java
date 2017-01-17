package es.uniapi.modules.business.dao.intf;

import es.uniapi.modules.model.UserLogin;;

public interface UserLoginDAO {

	public void create(UserLogin userLogin) throws Exception;
	public void delete(UserLogin userLogin) throws Exception;
	public UserLogin findByEmail(String Email)throws Exception;
	public UserLogin findByID(long id) throws  Exception;
	public UserLogin[] findAll() throws Exception;
}
