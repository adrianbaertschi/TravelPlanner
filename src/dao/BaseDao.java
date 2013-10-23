package dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Base class for all DAO Objects.
 * 
 * @author adrian
 *
 */
public class BaseDao {
	private String PERSISTENCE_UNIT = "travelDB";
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	
}