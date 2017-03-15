package es.uniapi.modules.business.dao.neo4j;

import es.uniapi.modules.business.dao.intf.UniapiDAO;
import es.uniapi.modules.business.dao.intf.entities.AplicationDAO;
import es.uniapi.modules.business.dao.intf.entities.ExecutionDAO;
import es.uniapi.modules.business.dao.intf.entities.GroupDAO;
import es.uniapi.modules.business.dao.intf.entities.PersonDAO;
import es.uniapi.modules.business.dao.intf.entities.ProjectDAO;
import es.uniapi.modules.business.dao.intf.entities.UserLoginDAO;
import es.uniapi.modules.business.dao.intf.relations.ContainsDAO;
import es.uniapi.modules.business.dao.intf.relations.IsOwnerDAO;
import es.uniapi.modules.business.dao.intf.relations.IsSubgroupDAO;
import es.uniapi.modules.business.dao.intf.relations.KnowsDAO;
import es.uniapi.modules.business.dao.neo4j.entities.ExecutioNeo4j;
import es.uniapi.modules.business.dao.neo4j.entities.GroupNeo4j;
import es.uniapi.modules.business.dao.neo4j.entities.PersonNeo4j;
import es.uniapi.modules.business.dao.neo4j.entities.ProjectNeo4j;
import es.uniapi.modules.business.dao.neo4j.entities.UserLoginNeo4j;
import es.uniapi.modules.business.dao.neo4j.relationship.ContainsDAOImpl;
import es.uniapi.modules.business.dao.neo4j.relationship.GenerateDAOImpl;
import es.uniapi.modules.business.dao.neo4j.relationship.IsCreatorDAOImpl;
import es.uniapi.modules.business.dao.neo4j.relationship.IsOwnerDAOImpl;
import es.uniapi.modules.business.dao.neo4j.relationship.IsSubgroupDAOImpl;
import es.uniapi.modules.business.dao.neo4j.relationship.KnowsDAOImpl;
import es.uniapi.modules.business.dao.neo4j.relationship.MakeReferenceDAOImpl;
import es.uniapi.modules.business.dao.neo4j.relationship.UseDAOImpl;
import es.uniapi.modules.model.Group;

public class UniapiNeo4jDAO implements UniapiDAO {

	@Override
	public PersonDAO getPersonDAO() {
		// TODO Auto-generated method stub
		return new PersonNeo4j();
	}

	@Override
	public UserLoginDAO getUserLoginDAO() {
		// TODO Auto-generated method stub
		return new UserLoginNeo4j();
	}

	@Override
	public ProjectDAO getProjectDAO() {
		// TODO Auto-generated method stub
		return new ProjectNeo4j();
	}

	@Override
	public GroupDAO getGroupDAO() {
		// TODO Auto-generated method stub
		return new GroupNeo4j();
	}

	@Override
	public AplicationDAO getAplicationDAO() {
		// TODO Auto-generated method stub
		return null;
	}
	public ExecutionDAO getExecutionDAO(){
		return new ExecutioNeo4j();
	}
	
	public MakeReferenceDAOImpl getMakeReferenceDAO(){
		return new MakeReferenceDAOImpl();
	}
	
	public IsCreatorDAOImpl getIsCreatorDAO(){
		return new IsCreatorDAOImpl();
	}
	public KnowsDAO getKnowsDAO(){
		return new KnowsDAOImpl();
	}
	public IsOwnerDAO getOwnerDAO(){
		return new IsOwnerDAOImpl();
	}

	public IsSubgroupDAO getSubgroupDAO() {
		// TODO Auto-generated method stub
		return new IsSubgroupDAOImpl();
	}

	public ContainsDAO getContainsDAO() {
		// TODO Auto-generated method stub
		return new ContainsDAOImpl();
	}

	@Override
	public ExecutionDAO getExecution() {
		// TODO Auto-generated method stub
		return new ExecutioNeo4j();
	}

	public GenerateDAOImpl getGeneratorDAO() {
		// TODO Auto-generated method stub
		return new GenerateDAOImpl();
	}

	public UseDAOImpl getUseDAO() {
		// TODO Auto-generated method stub
		return new UseDAOImpl();
	}

}
