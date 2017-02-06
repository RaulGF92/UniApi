package es.uniapi.modules.business.dao.intf.entities;

import es.uniapi.modules.model.Person;

public interface PersonDAO {

	public void create(Person person) throws Exception;
	public void delete(Person person) throws Exception;
	public Person findByHashCode(String string) throws  Exception;
	public Person[] findAll() throws Exception;
	public Person[] findByName(String name)throws Exception;

	
}
