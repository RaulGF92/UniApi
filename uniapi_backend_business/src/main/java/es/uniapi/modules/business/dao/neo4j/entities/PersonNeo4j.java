package es.uniapi.modules.business.dao.neo4j.entities;


import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import es.uniapi.modules.business.dao.intf.PersonDAO;
import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.config.AppConfiguration;

import static org.neo4j.driver.v1.Values.parameters;

public class PersonNeo4j implements PersonDAO {

	Driver driver;
	
	public PersonNeo4j() {
		// TODO Auto-generated constructor stub
		AppConfiguration conf=AppConfiguration.getConfiguration();
		this.driver= GraphDatabase.driver( "bolt://localhost:7687", 
				AuthTokens.basic( conf.getUserDataBase(), conf.getPassDataBase() ) );
	}
	
	@Override
	public void create(Person person) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		String statement="CREATE (a:Person {name: {name},subname:{subname}"
				+ ",birthday: {birthday},country:{country},province:{province}"
				+ ",birthplace: {birthplace},biografy:{biografy},profileImageUrl:{profileImageUrl}})";
		
		StatementResult result=session.run(statement,
				parameters("name",person.getName(),
						"subname",person.getSubname(),
						"birthday",person.getBirthday().getTime(),
						"country",person.getCountry(),
						"province",person.getProvince(),
						"birthplace",person.getBirthplace(),
						"biografy",person.getBiografy(),
						"profileImageUrl",person.getProfileImageUrl()));
		session.close();
	}

	@Override
	public void delete(Person person) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		String statement="MATCH (a:Person) WHERE a.name = {name} AND a.subname = {subname} AND a.birthday = {birthday}"
				+ "AND a.country = {country} AND a.province = {province} AND a.birthplace = {birthplace} AND a.biografy = {biografy}"
				+ "AND a.profileImageUrl = {profileImageUrl} DELETE a";
		
		StatementResult result=session.run(statement,
				parameters("name",person.getName(),
						"subname",person.getSubname(),
						"birthday",person.getBirthday().getTime(),
						"country",person.getCountry(),
						"province",person.getProvince(),
						"birthplace",person.getBirthplace(),
						"biografy",person.getBiografy(),
						"profileImageUrl",person.getProfileImageUrl()));
		session.close();
		
	}

	@Deprecated
	@Override
	public Person findByID(long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person[] findAll() throws Exception {
		// TODO Auto-generated method stub
		
		Session session=driver.session();
		ArrayList<Person> persons=new ArrayList<Person>();
		Person person=null;
		
		String statement="MATCH (a:Person) return  a.name AS name , a.subname AS subname, a.birthday AS birthday,"
				+ "a.country AS country , a.province AS province , a.birthplace AS birthplace , a.biografy AS biografy"
				+ ",a.profileImageUrl AS profileImageUrl";
		StatementResult result=session.run(statement);
		
		while(result.hasNext()){
			Record record=result.next();
			person=new Person(record.get("name").asString(), 
					record.get("subname").asString(),new DateTime(record.get("birthday").asLong()).toDate(),record.get("country").asString(), record.get("province").asString()
					,record.get("birthplace").asString(), record.get("biografy").asString(), record.get("profileImageUrl").asString());
			persons.add(person);
		}
		return persons.toArray(new Person[persons.size()]);
	}

	@Override
	public Person[] findByName(String name) throws Exception {
		// TODO Auto-generated method stub
		Session session=driver.session();
		ArrayList<Person> persons=new ArrayList<Person>();
		Person person=null;
		
		String statement="MATCH (a:Person) WHERE a.name = {name} return  a.name AS name , a.subname AS subname, a.birthday AS birthday,"
				+ "a.country AS country , a.province AS province , a.birthplace AS birthplace , a.biografy AS biografy"
				+ ",a.profileImageUrl AS profileImageUrl";
		StatementResult result=session.run(statement,parameters("name",name));
		
		while(result.hasNext()){
			Record record=result.next();
			person=new Person(record.get("name").asString(), 
					record.get("subname").asString(),new DateTime(record.get("birthday").asLong()).toDate(),record.get("country").asString(), record.get("province").asString()
					,record.get("birthplace").asString(), record.get("biografy").asString(), record.get("profileImageUrl").asString());
			persons.add(person);
		}
		return persons.toArray(new Person[persons.size()]);
	}

}
