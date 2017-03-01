package es.uniapi.modules.business.dao.neo4j.relationship;

import static org.neo4j.driver.v1.Values.parameters;

import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import es.uniapi.modules.business.dao.intf.relations.IsSubgroupDAO;
import es.uniapi.modules.business.dao.neo4j.relationship.model.IsSubGroup;
import es.uniapi.modules.business.dao.neo4j.relationship.model.Knows;
import es.uniapi.modules.model.Group;
import es.uniapi.modules.model.Group.GroupType;
import es.uniapi.modules.model.config.AppConfiguration;

public class IsSubgroupDAOImpl implements IsSubgroupDAO{

	Driver driver;
	public IsSubgroupDAOImpl(){
		AppConfiguration conf=AppConfiguration.getConfiguration();
		this.driver= GraphDatabase.driver( "bolt://localhost:7687", 
				AuthTokens.basic( conf.getUserDataBase(), conf.getPassDataBase() ) );
	}
	@Override
	public void create(Group group,Group subgroup) throws Exception {
		// TODO Auto-generated method stub
		Session session = driver.session();
		String statement="MATCH (g:Group),(s:Group) WHERE g.hashcode={hashcode1} "
				+ "AND s.hashcode={hashcode2} CREATE (g)-[ic:IS_SUBGROUP {dateFrom:{date}}]->(s)";
		
		StatementResult result=session.run(statement,parameters(
				"hashcode1",group.hash(),
				"hashcode2",subgroup.hash(),
				"date",new Date().getTime()));
		session.close();
	}
	@Override
	public Group[] getAllSubgroupOfGroup(Group group) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Group> response=new ArrayList<Group>();
		Group aux=null;
		Session session=driver.session();
		String statement="MATCH (g:Group {hashcode:{hash}}), "
				+ "h=(g)-[k:IS_SUBGROUP]->"
				+ "(s:Group) RETURN "
				+ "s.name AS name,"
				+ "s.creationDate AS creationDate,"
				+ "s.type AS type,"
				+ "s.hashcode AS hashcode,"
				+ "s.sharingGroupPermissions AS sharingGroupPermissions,"
				+ "s.projectPropertiesPermissions AS projectPropertiesPermissions,"
				+ "s.memberGestionPermissions AS memberGestionPermissions,"
				+ "s.groupCreationPermissions AS groupCreationPermissions,"
				+ "s.description AS description";
		
		StatementResult result=session.run(statement,parameters(
				"hash",group.hash()
				));
		
		while(result.hasNext()){
			Record record=result.next();
			aux=new Group(record.get("name").asString(),
					new DateTime(record.get("creationDate").asLong()).toDate(),
					GroupType.valueOf(record.get("type").asString()),
					record.get("sharingGroupPermissions").asList().toArray(new String[record.get("sharingGroupPermissions").asList().size()]),
					record.get("projectPropertiesPermissions").asList().toArray(new String[record.get("projectPropertiesPermissions").asList().size()]),
					record.get("memberGestionPermissions").asList().toArray(new String[record.get("memberGestionPermissions").asList().size()]),
					record.get("groupCreationPermissions").asList().toArray(new String[record.get("groupCreationPermissions").asList().size()]),
					record.get("description").asString());
			response.add(aux);
		}
		
		session.close();
		return response.toArray(new Group[response.size()]);
	}
	@Override
	public Group getParentGroupOfSubgroup(Group group) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Group> response=new ArrayList<Group>();
		Group aux=null;
		Session session=driver.session();
		String statement="MATCH (g:Group), "
				+ "h=(g)-[k:IS_SUBGROUP]->"
				+ "(s:Group {hashcode:{hash}}) RETURN "
				+ "g.name AS name,"
				+ "g.creationDate AS creationDate,"
				+ "g.type AS type,"
				+ "g.hashcode AS hashcode,"
				+ "g.sharingGroupPermissions AS sharingGroupPermissions,"
				+ "g.projectPropertiesPermissions AS projectPropertiesPermissions,"
				+ "g.memberGestionPermissions AS memberGestionPermissions,"
				+ "g.groupCreationPermissions AS groupCreationPermissions,"
				+ "g.description AS description";
		
		StatementResult result=session.run(statement,parameters(
				"hash",group.hash()
				));
		
		while(result.hasNext()){
			Record record=result.next();
			aux=new Group(record.get("name").asString(),
					new DateTime(record.get("creationDate").asLong()).toDate(),
					GroupType.valueOf(record.get("type").asString()),
					record.get("sharingGroupPermissions").asList().toArray(new String[record.get("sharingGroupPermissions").asList().size()]),
					record.get("projectPropertiesPermissions").asList().toArray(new String[record.get("projectPropertiesPermissions").asList().size()]),
					record.get("memberGestionPermissions").asList().toArray(new String[record.get("memberGestionPermissions").asList().size()]),
					record.get("groupCreationPermissions").asList().toArray(new String[record.get("groupCreationPermissions").asList().size()]),
					record.get("description").asString());
		}
		
		session.close();
		return aux;
	}
	@Override
	public IsSubGroup getInfo(Group group, Group subgroup) throws Exception {
		// TODO Auto-generated method stub
		IsSubGroup response=null;
		Session session=driver.session();
		
		String statement="MATCH (u:Group {hashcode:{hashcode1}}), "
				+ "h=(u)-[k:IS_SUBGROUP]->(g:Group {hashcode:{hashcode2}}) RETURN "
				+ "u.hashcode AS groupHash ,"
				+ "k.dateFrom as dateFrom,"
				+ "g.hashcode AS subgroupHash";
		StatementResult result=session.run(statement,parameters(
				"hashcode1",group.hash(),
				"hashcode2",subgroup.hash()
				));
		while(result.hasNext()){
			Record record=result.next();
			response=new IsSubGroup(record.get("groupHash").asString(), 
					record.get("subgroupHash").asString(), 
					new DateTime(record.get("dateFrom").asLong()).toDate());
			
		}
		
		session.close();
		return response;
	}
	@Override
	public Group[] getAllSubgroupsOrderN(Group group) throws Exception {
		// TODO Auto-generated method stub
		
		ArrayList<Group> response=new ArrayList<Group>();
		Group aux=null;
		Session session=driver.session();
		String statement="MATCH (g:Group {hashcode:{hash}})"
				+ "-[k:IS_SUBGROUP*]->"
				+ "(s:Group) RETURN "
				+ "s.name AS name,"
				+ "s.creationDate AS creationDate,"
				+ "s.type AS type,"
				+ "s.hashcode AS hashcode,"
				+ "s.sharingGroupPermissions AS sharingGroupPermissions,"
				+ "s.projectPropertiesPermissions AS projectPropertiesPermissions,"
				+ "s.memberGestionPermissions AS memberGestionPermissions,"
				+ "s.groupCreationPermissions AS groupCreationPermissions,"
				+ "s.description AS description";
		
		StatementResult result=session.run(statement,parameters(
				"hash",group.hash()
				));
		
		while(result.hasNext()){
			Record record=result.next();
			aux=new Group(record.get("name").asString(),
					new DateTime(record.get("creationDate").asLong()).toDate(),
					GroupType.valueOf(record.get("type").asString()),
					record.get("sharingGroupPermissions").asList().toArray(new String[record.get("sharingGroupPermissions").asList().size()]),
					record.get("projectPropertiesPermissions").asList().toArray(new String[record.get("projectPropertiesPermissions").asList().size()]),
					record.get("memberGestionPermissions").asList().toArray(new String[record.get("memberGestionPermissions").asList().size()]),
					record.get("groupCreationPermissions").asList().toArray(new String[record.get("groupCreationPermissions").asList().size()]),
					record.get("description").asString());
			response.add(aux);
		}
		
		session.close();
		return response.toArray(new Group[response.size()]);
		
	}
	@Override
	public void delete(Group group, Group subgroup) throws Exception {
		// TODO Auto-generated method stub
Session session=driver.session();
		
		String statement="MATCH (u:Group {hashcode:{hashcode1}}), "
				+ "h=(u)-[k:IS_SUBGROUP]->(g:Group {hashcode:{hashcode2}}) DELETE k";
		StatementResult result=session.run(statement,parameters(
				"hashcode1",group.hash(),
				"hashcode2",subgroup.hash()
				));
		
		session.close();
		System.out.println(result.toString());
	}
	
}
