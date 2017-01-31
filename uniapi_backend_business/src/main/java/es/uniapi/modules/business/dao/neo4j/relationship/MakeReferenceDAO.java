package es.uniapi.modules.business.dao.neo4j.relationship;

import java.util.ArrayList;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;

import es.uniapi.modules.business.dao.neo4j.relationship.model.MakeReference;
import es.uniapi.modules.model.Person;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.model.config.AppConfiguration;

import static org.neo4j.driver.v1.Values.parameters;

public class MakeReferenceDAO {

	Driver driver;
	
	public MakeReferenceDAO() {
		// TODO Auto-generated constructor stub
		AppConfiguration conf=AppConfiguration.getConfiguration();
		this.driver= GraphDatabase.driver( "bolt://localhost:7687", 
				AuthTokens.basic( conf.getUserDataBase(), conf.getPassDataBase() ) );
	}
	
	public MakeReference getByUserLogin(UserLogin user){
		MakeReference makeReference=null;
		
		return makeReference;
	}
	
	public void create(UserLogin user,Person person){
		
	}
	
	public MakeReference[] getAll(){
		ArrayList<MakeReference> makeReferences=new ArrayList<MakeReference>();
		
		return makeReferences.toArray(new MakeReference[makeReferences.size()]);
	}

}
