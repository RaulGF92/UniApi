package es.uniapi.modules.business.dao.intf;

import es.uniapi.modules.model.Person;

public interface PersonDAO {

	public void create(Person person) throws Exception;
	public void delete(Person person) throws Exception;
	public Person findByID(long id) throws  Exception;
	public Person[] findAll() throws Exception;
	public Person[] findByName(String name)throws Exception;

	
}
